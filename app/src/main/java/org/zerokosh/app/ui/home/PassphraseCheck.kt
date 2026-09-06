/**
 * @file PassphraseCheck.kt
 * @description The periodic "do you still remember it?" prompt, and the reason
 *              the whole recovery story needs one.
 */
package org.zerokosh.app.ui.home

// #region Imports
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.theme.VaultTheme
// #endregion
/**
 * Catches the passphrase going stale before a dead fingerprint reveals it.
 *
 * Quick unlock is the whole reason this is needed: someone can open the vault
 * every day for a year without ever typing the passphrase, and nothing tells
 * them it has slipped away. The day biometrics reset — new phone, re-enrolled
 * finger, factory reset — the Keystore copy is gone and the passphrase is the
 * only way in. Asking every thirty days finds that out while there is still a
 * fingerprint available to fix it with.
 */
@Composable
internal fun PassphraseCheck(app: ZerokoshApp, onForgot: () -> Unit, onDismiss: () -> Unit) {
    val correctMessage = stringResource(R.string.pc_correct)
    var typed by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.pc_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.pc_body), style = MaterialTheme.typography.bodyMedium)
                OutlinedTextField(
                    value = typed,
                    onValueChange = { typed = it; wrong = false },
                    label = { Text(stringResource(R.string.scr_lock_hint)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    isError = wrong,
                    supportingText = {
                        if (wrong) {
                            Text(stringResource(R.string.pc_wrong), color = MaterialTheme.colorScheme.error)
                        }
                    },
                )
                Text(
                    stringResource(R.string.pc_forgot),
                    style = MaterialTheme.typography.bodyMedium,
                    color = VaultTheme.colors.primary,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(role = Role.Button, onClick = onForgot)
                        .padding(vertical = 6.dp),
                )
            }
        },
        confirmButton = {
            Button(
                enabled = typed.isNotEmpty() && !busy,
                onClick = {
                    busy = true
                    scope.launch {
                        val ok = app.repository.verifyPassphrase(typed.toByteArray())
                        busy = false
                        if (ok) {
                            app.prefs.lastPassphraseUseMs = System.currentTimeMillis()
                            Toast.makeText(context, correctMessage, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        } else {
                            wrong = true
                        }
                    }
                },
            ) { Text(stringResource(R.string.pc_confirm)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.pc_later)) }
        },
    )
}

/** Thirty days without typing it is long enough to forget, short enough to fix. */
internal const val PASSPHRASE_CHECK_INTERVAL_MS = 30L * 24 * 60 * 60 * 1000
/**
 * Decides whether the check is due, so HomeScreen does not carry the branches.
 *
 * Nothing to ask of someone without quick unlock: they type the passphrase on
 * every single unlock and cannot have drifted away from it.
 */
@Composable
internal fun PassphraseCheckHost(app: ZerokoshApp, hasRecords: Boolean, onForgot: () -> Unit) {
    var dismissed by remember { mutableStateOf(false) }
    val lastUse = app.prefs.lastPassphraseUseMs
    val due = app.prefs.quickUnlockEnabled && lastUse > 0 &&
        System.currentTimeMillis() - lastUse > PASSPHRASE_CHECK_INTERVAL_MS
    if (!due || dismissed || !hasRecords) return
    PassphraseCheck(
        app = app,
        onForgot = { dismissed = true; onForgot() },
        onDismiss = {
            dismissed = true
            // Push the clock forward as well, or the dialog returns every time
            // the user comes back to Home.
            app.prefs.lastPassphraseUseMs =
                System.currentTimeMillis() - PASSPHRASE_CHECK_INTERVAL_MS + KIT_SNOOZE_MS
        },
    )
}

/** Seven quiet days after a dismissal, then the banner comes back. */
internal fun snoozeKitBanner(app: ZerokoshApp) {
    app.prefs.kitBannerSnoozedUntilMs = System.currentTimeMillis() + KIT_SNOOZE_MS
}

/**
 * Whether the missing-kit banner belongs on screen right now.
 *
 * Only once records exist: asking about recovery while the vault is empty is
 * the onboarding mistake this banner is here to correct.
 */
internal fun kitBannerDue(app: ZerokoshApp, recordCount: Int): Boolean =
    !app.prefs.recoveryKitSaved &&
        recordCount > 0 &&
        System.currentTimeMillis() >= app.prefs.kitBannerSnoozedUntilMs
