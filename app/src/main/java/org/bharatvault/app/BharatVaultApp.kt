package org.bharatvault.app

// #region Imports
import android.app.Application
import android.net.Uri
import org.bharatvault.app.crypto.AndroidCrypto
import org.bharatvault.app.data.AssetCatalog
import org.bharatvault.app.data.LocalVaultStore
import org.bharatvault.app.data.Prefs
import org.bharatvault.app.data.SafVaultStore
import org.bharatvault.app.data.VaultRepository
import org.bharatvault.app.data.VaultStore
import org.bharatvault.app.reminders.ReminderWorker
// #endregion

/** Composition root. No DI framework — dependency budget (R0.7). */
class BharatVaultApp : Application() {

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

        // BV-02: nothing used to enqueue this, so expiry and renewal
        // reminders never fired. KEEP policy makes it idempotent.
        ReminderWorker.schedule(this)
    }
}
