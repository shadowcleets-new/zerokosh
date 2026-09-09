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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.data.UnlockOutcome
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.app.ui.theme.ZerokoshTheme
// #endregion

// #region Activity
class AutofillAuthActivity : FragmentActivity() {

    private val app: ZerokoshApp get() = application as ZerokoshApp
    private var targets: FieldTargets? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                PassphraseSheet(
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

/**
 * A card at the bottom and nothing else, so the form stays readable above it.
 *
 * Deliberately not the app's unlock screen: that one is a full page with the
 * brand, the crypto line and a recovery route, all of which belong when opening
 * the vault and none of which belong on top of somebody else's sign-in page.
 */
@Composable
private fun PassphraseSheet(
    onUnlock: (String, onWrong: () -> Unit) -> Unit,
    onCancel: () -> Unit,
) {
    var entered by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    rememberCoroutineScope()

    val submit = {
        if (entered.isNotEmpty() && !busy) {
            busy = true
            wrong = false
            onUnlock(entered) {
                busy = false
                wrong = true
                entered = ""
            }
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Surface(
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
            ) {
                Text(
                    text = stringRes(R.string.scr_autofill_unlock_first),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = entered,
                    onValueChange = { entered = it; wrong = false },
                    singleLine = true,
                    isError = wrong,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(onGo = { submit() }),
                    label = { Text(stringRes(R.string.scr_lock_hint)) },
                    modifier = Modifier.fillMaxWidth(),
                )
                if (wrong) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = stringRes(R.string.scr_lock_wrong),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextButton(onClick = onCancel) { Text(stringRes(android.R.string.cancel)) }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = submit, enabled = entered.isNotEmpty() && !busy) {
                        Text(stringRes(R.string.scr_lock_unlock))
                    }
                }
            }
        }
    }
}

@Composable
private fun stringRes(id: Int): String = androidx.compose.ui.res.stringResource(id)
// #endregion
