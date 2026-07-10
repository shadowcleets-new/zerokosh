package org.bharatvault.app

// #region Imports
import android.app.Application
import org.bharatvault.app.crypto.AndroidCrypto
import org.bharatvault.app.data.AssetCatalog
import org.bharatvault.app.data.LocalVaultStore
import org.bharatvault.app.data.Prefs
import org.bharatvault.app.data.VaultRepository
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
        repository = VaultRepository(AndroidCrypto(), LocalVaultStore(this), prefs)
    }
}
