package org.bharatvault.app.ui.common

// #region Imports
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.launch
import org.bharatvault.app.R
import org.bharatvault.app.data.VaultRepository
// #endregion

/**
 * §5.4: H-field reveal/copy needs fresh authentication, at most once per 60 s.
 * M3 wires the biometric path; passphrase re-entry is the universal fallback.
 */
object RevealAuth {
    private const val GRACE_MS = 60_000L
    private var lastAuthAtMs = 0L

    fun withinGrace(): Boolean = System.currentTimeMillis() - lastAuthAtMs < GRACE_MS

    fun markAuthenticated() {
        lastAuthAtMs = System.currentTimeMillis()
    }

    fun reset() {
        lastAuthAtMs = 0L
    }
}

@Composable
fun PassphraseAuthDialog(
    repository: VaultRepository,
    onSuccess: () -> Unit,
    onDismiss: () -> Unit,
) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.msg_auth_needed)) },
        text = {
            OutlinedTextField(
                value = passphrase,
                onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = wrong,
                supportingText = { if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) },
                singleLine = true,
                enabled = !busy,
            )
        },
        confirmButton = {
            TextButton(
                enabled = passphrase.isNotEmpty() && !busy,
                onClick = {
                    busy = true
                    scope.launch {
                        val bytes = passphrase.toByteArray()
                        val ok = repository.verifyPassphrase(bytes)
                        bytes.fill(0)
                        busy = false
                        if (ok) {
                            RevealAuth.markAuthenticated()
                            onSuccess()
                        } else {
                            wrong = true
                        }
                    }
                },
            ) { Text(stringResource(R.string.msg_ok)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) }
        },
    )
}
