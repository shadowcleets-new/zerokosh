package org.zerokosh.app

// #region Imports
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import org.zerokosh.app.autofill.AutofillFill
import org.zerokosh.app.data.Prefs
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.ui.ZerokoshNav
import org.zerokosh.app.ui.theme.ZerokoshTheme
import java.util.Locale
// #endregion

// FragmentActivity: androidx BiometricPrompt requires it (§6.5); still a ComponentActivity for Compose.
class MainActivity : FragmentActivity() {

    private val app get() = application as ZerokoshApp

    /**
     * BV-04: tell the process-level auto-lock that this backgrounding is ours —
     * a file picker, a document creator — and not the user leaving. Every
     * ActivityResultRegistry launch funnels through startActivityForResult, so
     * this catches them all.
     */
    override fun startActivityForResult(intent: android.content.Intent, requestCode: Int, options: Bundle?) {
        app.handingOffToPicker = true
        super.startActivityForResult(intent, requestCode, options)
    }

    override fun attachBaseContext(newBase: Context) {
        // S1 language choice without an extra dependency (§12)
        val tag = Prefs(newBase).languageTag
        if (tag.isEmpty()) {
            super.attachBaseContext(newBase)
            return
        }
        // -u-nu-latn pins %d to Latin digits. Bengali, Assamese, Nepali and
        // Marathi otherwise format it in native digits while every literal in
        // the same string stays Latin, so "Step 3 of 6" rendered as "৩ / 6".
        // Latin is also the right choice on its own: this vault shows account
        // numbers, PINs and OTPs, and every bank and UPI app writes those in
        // Latin — as do Argon2id, 64 MB and Poly1305 alongside them.
        val locale = Locale.forLanguageTag("$tag-u-nu-latn")
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
            ZerokoshTheme(darkTheme = darkTheme) {
                ZerokoshNav(app)
            }
        }
    }

    /**
     * §5.5: FLAG_SECURE by default; "Allow screenshots" (default OFF) disables it.
     *
     * First run is the exception. Setup hands the user a Recovery Key exactly
     * once, and a black rectangle where that key should be is not a security
     * win — it is how someone ends up locked out of their own vault forever.
     * So screenshots stay available until onboarding reports done, and clamp
     * shut the instant it does. After that the Settings toggle is the only way
     * back, which is the behaviour that was always documented.
     */
    fun applyScreenPrivacy() {
        if (app.prefs.allowScreenshots || !app.prefs.onboardingDone) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

}
