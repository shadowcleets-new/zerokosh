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
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.ui.ZerokoshNav
import org.zerokosh.app.ui.theme.ZerokoshTheme
import java.util.Locale
// #endregion

// FragmentActivity: androidx BiometricPrompt requires it (§6.5); still a ComponentActivity for Compose.
class MainActivity : FragmentActivity() {

    private val app get() = application as ZerokoshApp
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
                DeliverAutofillWhenUnlocked()
            }
        }
    }

    /**
     * When this activity was opened by the autofill service to unlock the vault,
     * hand the response back the moment it is open and get out of the way.
     *
     * Without this the authentication dataset was a dead end: it launched the
     * app, the user unlocked, and the form they came from stayed empty — they
     * had to leave, come back, and tap the field again. The vault re-locks a
     * minute after you leave it, so that was the ordinary path, not the edge.
     */
    @Composable
    private fun DeliverAutofillWhenUnlocked() {
        val targets = remember { AutofillFill.targetsFrom(intent) } ?: return
        val state by app.repository.state.collectAsState()
        LaunchedEffect(state) {
            if (state != VaultState.Unlocked) return@LaunchedEffect
            AutofillFill.deliver(this@MainActivity, app, targets)
            // Consumed: a rotation must not re-deliver and re-finish.
            intent.removeExtra("org.zerokosh.app.autofill.AUTH")
            finish()
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
