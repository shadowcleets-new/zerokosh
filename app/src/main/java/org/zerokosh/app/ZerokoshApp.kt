package org.zerokosh.app

// #region Imports
import android.app.Application
import android.net.Uri
import org.zerokosh.app.crypto.AndroidCrypto
import org.zerokosh.app.data.AssetCatalog
import org.zerokosh.app.data.LocalVaultStore
import org.zerokosh.app.data.Prefs
import org.zerokosh.app.data.SafVaultStore
import org.zerokosh.app.data.SessionKeeper
import org.zerokosh.app.data.VaultRepository
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.data.VaultStore
import org.zerokosh.core.crypto.wipe
import org.zerokosh.app.reminders.ReminderWorker
// #endregion

/** Composition root. No DI framework — dependency budget (R0.7). */
class ZerokoshApp : Application() {

    lateinit var prefs: Prefs
        private set
    lateinit var catalog: AssetCatalog
        private set
    lateinit var repository: VaultRepository
        private set

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(this)
        catalog = AssetCatalog(this)
        
        val initialStore: VaultStore = if (prefs.syncFolderUri.isNotEmpty()) {
            runCatching { SafVaultStore(this, Uri.parse(prefs.syncFolderUri)) }.getOrDefault(LocalVaultStore(this))
        } else {
            LocalVaultStore(this)
        }
        
        repository = VaultRepository(AndroidCrypto(), initialStore, prefs)
        // The repository is built before the catalog can tell it which fields are
        // secret, so the lookup is handed over here rather than passed in.
        repository.secretKeysFor = { templateId ->
            catalog.templates.templates.firstOrNull { it.id == templateId }
                ?.fields.orEmpty()
                .filter { it.sensitivity == org.zerokosh.core.model.Sensitivity.H }
                .mapTo(mutableSetOf()) { it.k }
        }

        // An unlock outlives the process for as long as "Lock when I leave the
        // app" says it should. Without this the autofill service — which the
        // platform starts in a fresh process whenever it feels like it — found
        // a sealed vault and answered every password field with a prompt to
        // unlock, no matter what the user had chosen.
        repository.onUnlocked = { vaultKey ->
            SessionKeeper.remember(this, vaultKey, sessionExpiryMs())
        }
        repository.onLocked = { SessionKeeper.clear(this) }

        // BV-02: nothing used to enqueue this, so expiry and renewal
        // reminders never fired. KEEP policy makes it idempotent.
        ReminderWorker.schedule(this)
    }

    /** When the held session should stop being valid: the auto-lock window from now. */
    fun sessionExpiryMs(): Long {
        val minutes = prefs.autoLockMinutes
        // "Immediately" is not a zero-length window, it is no window: expiring
        // in the past makes SessionKeeper store nothing.
        if (minutes <= 0) return 0L
        return System.currentTimeMillis() + minutes * 60_000L
    }

    /**
     * Re-open the vault from a kept session, if there is one that is still good.
     *
     * Every entry point that can find a locked vault calls this first — the
     * activity on start, and the autofill service on both of its requests,
     * since that service is usually what wakes a fresh process.
     */
    suspend fun resumeSessionIfLive(): Boolean {
        if (repository.state.value == VaultState.Unlocked) return true
        val key = SessionKeeper.resume(this) ?: return false
        return try {
            repository.resumeWithVaultKey(key)
        } finally {
            key.wipe()
        }
    }
}
