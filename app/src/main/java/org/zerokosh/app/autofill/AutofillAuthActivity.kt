/**
 * @file AutofillAuthActivity.kt
 * @description The unlock the autofill service asks for, as a sheet over the
 *              form rather than the whole app.
 *
 * The authentication dataset used to open MainActivity. That works — it
 * unlocks, delivers the response and finishes — but what the user sees is the
 * entire password manager thrown up over the login page they were on, covering
 * the status bar and the form both, and then vanishing again. Google's manager
 * puts a small sheet at the bottom of the screen and leaves the page visible
 * behind it, which is what this does.
 *
 * When quick unlock is enrolled there is no sheet of our own at all: the system
 * biometric prompt is already exactly that, and it appears the moment this
 * activity opens.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. ACTIVITY
 * 3. DELIVERY
 * 4. SHEET UI
 */
package org.zerokosh.app.autofill

// #region Imports
import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.data.UnlockOutcome
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.core.vault.UnlockResult
import androidx.compose.ui.res.stringResource
import org.zerokosh.app.ui.UnlockSheet
import org.zerokosh.app.ui.theme.ZerokoshTheme
// #endregion

// #region Activity
class AutofillAuthActivity : FragmentActivity() {

    private val app: ZerokoshApp get() = application as ZerokoshApp
    private var targets: FieldTargets? = null

    override fun onDestroy() {
        app.exitProviderSheet()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Not the user returning to Zerokosh: this serves another app's form.
        app.enterProviderSheet()
        // The sheet floats over someone else's login form, and the fields it is
        // about to fill are on it. FLAG_SECURE regardless of the screenshot
        // setting, because this window is only ever about a password.
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        val parsed = AutofillFill.targetsFrom(intent)
        if (parsed == null) {
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }
        targets = parsed

        // The grace period may well cover this already, in which case there is
        // nothing to ask and no sheet worth drawing.
        lifecycleScope.launch {
            if (app.repository.state.value == VaultState.Unlocked || app.resumeSessionIfLive()) {
                deliver()
                return@launch
            }
            if (QuickUnlockManager.isEnrolled(this@AutofillAuthActivity)) {
                val result = QuickUnlockManager.unlock(this@AutofillAuthActivity, app)
                if (result is UnlockResult.Success) {
                    app.repository.adoptBiometricUnlock(result)
                    deliver()
                    return@launch
                }
            }
            showPassphraseSheet()
        }
    }
    // #endregion

    // #region Delivery
    /**
     * Hand the finished response back to the platform, which fills the form the
     * user was standing in. Never opens the app: that was the complaint.
     */
    private fun deliver() {
        val t = targets ?: return
        AutofillFill.deliver(this, app, t)
        finish()
    }

    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
    // #endregion

    // #region Sheet UI
    private fun showPassphraseSheet() {
        setContent {
            ZerokoshTheme {
                UnlockSheet(
                    title = stringResource(R.string.scr_autofill_unlock_first),
                    onUnlock = { entered, onWrong ->
                        lifecycleScope.launch {
                            val outcome = app.repository.unlockWithPassphrase(entered.toByteArray())
                            if (outcome == UnlockOutcome.SUCCESS) deliver() else onWrong()
                        }
                    },
                    onCancel = ::cancel,
                )
            }
        }
    }
}
