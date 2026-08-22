/**
 * @file ImportBackup.kt
 * @description Picking a .kosh file and merging it into the open vault.
 *
 *              Two screens offer this — the empty vault's "Import an encrypted
 *              backup" row and Settings — so the picker, the passphrase prompt
 *              and the result live here once. [rememberImportBackup] emits its
 *              own dialogs and returns the function that starts the flow.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. THE FLOW
 * 3. PASSPHRASE PROMPT
 * 4. RESULT
 */
package org.zerokosh.app.ui.backup

// #region Imports
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.data.ImportOutcome
import org.zerokosh.app.ui.common.RevealToggle
// #endregion

// #region The flow
/**
 * A .kosh larger than this is not one of ours. The whole vault is held in
 * memory to decrypt it, so the cap is what stops a mistakenly picked video from
 * taking the process down.
 */
private const val MaxImportBytes = 32L * 1024 * 1024

/**
 * Emits the import dialogs and returns the function that opens the file picker.
 *
 * A composable may both emit and return, which keeps the whole flow in one
 * place: the caller just wires the returned lambda to its row or button.
 */
@Composable
fun rememberImportBackup(app: ZerokoshApp): () -> Unit {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var pending by remember { mutableStateOf<ByteArray?>(null) }
    var result by remember { mutableStateOf<ImportOutcome?>(null) }

    val picker = rememberLauncherForActivityResult(
        // .kosh has no registered MIME type, and providers disagree about what
        // to report for an unknown extension, so filtering would hide the file
        // the user came to pick.
        ActivityResultContracts.OpenDocument(),
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            val bytes = withContext(Dispatchers.IO) {
                runCatching {
                    context.contentResolver.openInputStream(uri)?.use { stream ->
                        // Bounded by hand: InputStream.readNBytes(int) is API 33
                        // and minSdk is 26.
                        val out = java.io.ByteArrayOutputStream()
                        val buf = ByteArray(64 * 1024)
                        var over = false
                        while (!over) {
                            val n = stream.read(buf)
                            if (n < 0) break
                            out.write(buf, 0, n)
                            over = out.size() > MaxImportBytes
                        }
                        if (over) null else out.toByteArray()
                    }
                }.getOrNull()
            }
            // Unreadable and undecryptable land in the same place: the user
            // picked a file that is not a vault they can import.
            if (bytes == null) result = ImportOutcome.NotAVault else pending = bytes
        }
    }

    pending?.let { bytes ->
        PassphrasePrompt(
            onDismiss = { pending = null },
            onConfirm = { passphrase ->
                pending = null
                scope.launch {
                    result = app.repository.importVaultFile(bytes, passphrase)
                    passphrase.fill(0)
                }
            },
        )
    }

    result?.let { ImportResultDialog(it) { result = null } }

    return { picker.launch(arrayOf("*/*")) }
}
// #endregion

// #region Passphrase prompt
/**
 * The backup's passphrase, which is not necessarily this vault's — hence a
 * prompt rather than reusing the unlocked key.
 */
@Composable
private fun PassphrasePrompt(onDismiss: () -> Unit, onConfirm: (ByteArray) -> Unit) {
    var text by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.import_title)) },
        text = {
            Column {
                Text(
                    stringResource(R.string.import_passphrase_hint),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { text = it },
                        singleLine = true,
                        label = { Text(stringResource(R.string.import_passphrase_label)) },
                        visualTransformation = if (visible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        modifier = Modifier.weight(1f),
                    )
                    RevealToggle(visible = visible, onToggle = { visible = !visible })
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = text.isNotEmpty(),
                onClick = { onConfirm(text.toByteArray()) },
            ) { Text(stringResource(R.string.import_action)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) }
        },
    )
}
// #endregion

// #region Result
@Composable
private fun ImportResultDialog(outcome: ImportOutcome, onDismiss: () -> Unit) {
    val message = when (outcome) {
        is ImportOutcome.Merged -> buildString {
            append(
                when {
                    outcome.added == 0 && outcome.updated == 0 ->
                        stringResource(R.string.import_nothing_new)
                    else -> stringResource(
                        R.string.import_merged,
                        outcome.added,
                        outcome.updated,
                    )
                },
            )
            if (outcome.conflicts > 0) {
                append("\n\n")
                append(stringResource(R.string.import_conflicts, outcome.conflicts))
            }
        }
        // §3.3: never a crypto error string.
        ImportOutcome.WrongPassphrase -> stringResource(R.string.scr_lock_wrong)
        ImportOutcome.NotAVault -> stringResource(R.string.import_not_a_vault)
        ImportOutcome.Locked -> stringResource(R.string.import_locked)
    }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.import_title)) },
        text = { Text(message, modifier = Modifier.fillMaxWidth()) },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_ok)) }
        },
    )
}
// #endregion
