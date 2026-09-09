package org.zerokosh.app

// #region Imports
import android.app.Application
import android.net.Uri
import org.zerokosh.app.crypto.AndroidCrypto
import org.zerokosh.app.data.AssetCatalog
import org.zerokosh.app.data.LocalVaultStore
import org.zerokosh.app.data.Prefs
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
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

        observeProcessLifecycle()

        // BV-02: nothing used to enqueue this, so expiry and renewal
        // reminders never fired. KEEP policy makes it idempotent.
        ReminderWorker.schedule(this)
    }

    // #region Auto-lock across the whole process
    /**
     * Set while we hand off to another activity of our own volition — a file
     * picker, a document creator.
     *
     * BV-04: that is not the user leaving. Locking on it meant the picker's
     * result callback ran against a locked repository and lost whatever was
     * half-typed into an edit form.
     */
    @Volatile
    var handingOffToPicker: Boolean = false

    /**
     * How many provider sheets are on screen — the autofill unlock, the
     * Credential Manager entry activity.
     *
     * They are ours, but they are not the user coming back to Zerokosh: they
     * float over another app's form to serve that app. Counting them keeps the
     * process observer from reading one as a return and locking the vault out
     * from under the very request it was opened to answer. What those sheets
     * should be governed by is the session window, which they consult
     * themselves.
     */
    private val sheetsOnScreen = java.util.concurrent.atomic.AtomicInteger(0)

    fun enterProviderSheet() {
        sheetsOnScreen.incrementAndGet()
    }

    fun exitProviderSheet() {
        sheetsOnScreen.updateAndGet { if (it > 0) it - 1 else 0 }
    }

    /** When the app as a whole went to the background; 0 while it is in front. */
    private var backgroundedAtMs: Long = 0L

    /**
     * Watch the process rather than one Activity.
     *
     * This was hand-rolled in MainActivity, which can only see itself. That is
     * the shape of bug the review of the session work found — the auto-lock
     * clock belongs to the app, not to a screen — and it also had to
     * special-case configuration changes, which ProcessLifecycleOwner already
     * debounces away.
     *
     * The autofill service is deliberately not covered here: it can run with no
     * Activity at all, so ProcessLifecycleOwner never moves for it. That case is
     * handled where it happens, in the service's own session-window check.
     */
    private fun observeProcessLifecycle() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onStop(owner: LifecycleOwner) {
                    if (handingOffToPicker || sheetsOnScreen.get() > 0) return
                    backgroundedAtMs = System.currentTimeMillis()
                    if (prefs.autoLockMinutes <= 0) {
                        // "Immediately" means immediately. Waiting until the
                        // user comes back to lock left the vault open in memory
                        // for anything that asked in between — autofill, a
                        // credential request — which is not what that setting
                        // says, and it raced with the sheets that serve them.
                        repository.lock()
                        return
                    }
                    // The setting is worded "lock when I leave", so leaving is
                    // when the clock starts.
                    repository.renewSession()
                }

                override fun onStart(owner: LifecycleOwner) {
                    if (sheetsOnScreen.get() > 0) return
                    val leftAt = backgroundedAtMs
                    backgroundedAtMs = 0L
                    handingOffToPicker = false
                    val overdue = leftAt > 0L &&
                        System.currentTimeMillis() - leftAt >= prefs.autoLockMinutes * 60_000L
                    if (overdue && repository.state.value == VaultState.Unlocked) {
                        repository.lock()
                    }
                    // Coming back to a process Android killed while we were
                    // away: the window is measured by SessionKeeper's own
                    // deadline, which outlived it.
                    if (repository.state.value == VaultState.Locked) {
                        ProcessLifecycleOwner.get().lifecycleScope.launch { resumeSessionIfLive() }
                    }
                }
            },
        )
    }
    // #endregion

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
