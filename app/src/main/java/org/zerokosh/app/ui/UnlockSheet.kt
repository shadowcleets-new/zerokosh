/**
 * @file UnlockSheet.kt
 * @description The compact unlock shown over somebody else's app.
 *
 * Shared by the autofill service's authentication activity and the Credential
 * Manager provider's entry activity, because it is the same moment in both:
 * the user has tapped a Zerokosh row on a form belonging to another app, and
 * the vault needs opening before anything can be handed back.
 *
 * Deliberately not the app's own unlock screen. That one is a full page with
 * the brand, the crypto line and a route to the recovery key — all of which
 * belong when you are opening the vault, and none of which belong on top of
 * someone's sign-in page.
 */
package org.zerokosh.app.ui

// #region Imports
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
// #endregion

/**
 * A card at the bottom and nothing else, so the form stays readable above it.
 *
 * @param title what the sheet is for, since the two callers arrive from
 *   different rows and should say so.
 * @param onUnlock given the typed passphrase and a callback to invoke when it
 *   was the wrong one. The caller owns what happens on success, because the
 *   two callers hand back different things.
 */
@Composable
fun UnlockSheet(
    title: String,
    onUnlock: (passphrase: String, onWrong: () -> Unit) -> Unit,
    onCancel: () -> Unit,
) {
    // Same rule as the lock screen: ask for what the user actually set. This
    // sheet floats over another app's form, where a wrong word is even more
    // confusing than it is at home.
    val isPin = (LocalContext.current.applicationContext as ZerokoshApp).prefs.secretIsPin
    var entered by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }

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
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = entered,
                    onValueChange = { entered = it; wrong = false },
                    singleLine = true,
                    isError = wrong,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = if (isPin) KeyboardType.NumberPassword else KeyboardType.Password,
                        imeAction = ImeAction.Go,
                    ),
                    keyboardActions = KeyboardActions(onGo = { submit() }),
                    label = {
                        Text(stringResource(if (isPin) R.string.ob_pass_tab_pin else R.string.scr_lock_hint))
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
                if (wrong) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = stringResource(
                            if (isPin) R.string.scr_lock_wrong_pin else R.string.scr_lock_wrong,
                        ),
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
                    TextButton(onClick = onCancel) {
                        Text(stringResource(android.R.string.cancel))
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(onClick = submit, enabled = entered.isNotEmpty() && !busy) {
                        Text(stringResource(R.string.scr_lock_unlock))
                    }
                }
            }
        }
    }
}
