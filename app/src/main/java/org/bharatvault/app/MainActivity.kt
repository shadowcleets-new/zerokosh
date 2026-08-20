package org.bharatvault.app

// #region Imports
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import org.bharatvault.app.data.Prefs
import org.bharatvault.app.data.VaultState
import org.bharatvault.app.ui.BharatVaultNav
import org.bharatvault.app.ui.theme.BharatVaultTheme
import java.util.Locale
// #endregion

// FragmentActivity: androidx BiometricPrompt requires it (§6.5); still a ComponentActivity for Compose.
class MainActivity : FragmentActivity() {

    private val app get() = application as BharatVaultApp
    private var backgroundedAtMs: Long = 0

    /**
     * BV-04: set while we hand off to another activity of our own volition — a
     * file picker, a document creator. Every ActivityResultRegistry launch funnels
     * through startActivityForResult, so this catches them all.
     */
    private var awaitingActivityResult = false

    override fun startActivityForResult(intent: android.content.Intent, requestCode: Int, options: Bundle?) {
        awaitingActivityResult = true
        super.startActivityForResult(intent, requestCode, options)
    }

    override fun attachBaseContext(newBase: Context) {
        // S1 language choice (en/hi in v1) without an extra dependency (§12)
        val tag = Prefs(newBase).languageTag
        if (tag.isEmpty()) {
            super.attachBaseContext(newBase)
            return
        }
        val locale = Locale.forLanguageTag(tag)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)
        super.attachBaseContext(newBase.createConfigurationContext(config))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyScreenPrivacy()
        enableEdgeToEdge()
        setContent {
            val themeOption = app.prefs.themeOption
            val darkTheme = when (themeOption) {
                1 -> false
                2 -> true
                else -> androidx.compose.foundation.isSystemInDarkTheme()
            }
            BharatVaultTheme(darkTheme = darkTheme) {
                BharatVaultNav(app)
            }
        }
    }

    /** §5.5: FLAG_SECURE by default; "Allow screenshots" (default OFF) disables it. */
    fun applyScreenPrivacy() {
        if (app.prefs.allowScreenshots) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onStart() {
        super.onStart()
        // §5.2: lock after N minutes in background (0 = immediately)
        if (backgroundedAtMs > 0 && app.repository.state.value == VaultState.Unlocked) {
            val minutes = app.prefs.autoLockMinutes
            val elapsed = System.currentTimeMillis() - backgroundedAtMs
            if (elapsed >= minutes * 60_000L) app.repository.lock()
        }
        backgroundedAtMs = 0
        awaitingActivityResult = false
    }

    override fun onStop() {
        super.onStop()
        // BV-04: a rotation or a picker round-trip is not the user leaving. With
        // auto-lock on "Immediately" (elapsed >= 0 is always true) those would
        // lock the vault the moment they came back — and the picker's result
        // callback would then run against a locked repository, losing whatever
        // was half-typed into an edit form.
        backgroundedAtMs = if (isChangingConfigurations || awaitingActivityResult) {
            0
        } else {
            System.currentTimeMillis()
        }
    }
}
