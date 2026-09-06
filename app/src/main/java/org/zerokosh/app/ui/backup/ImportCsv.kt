/**
 * @file ImportCsv.kt
 * @description Bringing an export from another password manager into the vault.
 *
 *              The parsing has been in :core since M5 — Chrome/Google, Bitwarden
 *              CSV and JSON, LastPass, KeePass — and nothing referenced it, so
 *              the single biggest barrier to using this app (nobody retypes
 *              eighty credentials) was solved and unreachable. This is the file
 *              picker and the confirmation it was missing.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. THE FLOW
 * 3. PREVIEW
 * 4. RESULT
 */
package org.zerokosh.app.ui.backup

// #region Imports
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.saveErrorMessage
import org.zerokosh.core.import.CsvImport
import org.zerokosh.core.model.Record
// #endregion

// #region The flow
/**
 * A credential export is text. Anything past this is not one, and the whole
 * file is read into memory to parse it.
 */
private const val MAX_CSV_BYTES = 16 * 1024 * 1024

private class Pending(
    val insert: List<Record>,
    val update: List<Record>,
    val sourceLabel: String,
)

/**
 * Returns the function that starts the flow; the dialogs are emitted here.
 *
 * Mirrors [rememberImportBackup] deliberately — two import routes that behaved
 * differently would be two things to learn.
 */
@Composable
fun rememberImportCsv(app: ZerokoshApp): () -> Unit {
    val context = LocalContext.current
    val resources = LocalResources.current
    val scope = rememberCoroutineScope()

    var pending by remember { mutableStateOf<Pending?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var result by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var busy by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        // Not filtered to text/csv: exports arrive as text/comma-separated-values,
        // application/json, application/octet-stream and often no type at all,
        // and a filter that hides the user's own file is worse than none.
        ActivityResultContracts.OpenDocument(),
    ) { uri: Uri? ->
        if (uri == null) return@rememberLauncherForActivityResult
        scope.launch {
            val text = withContext(Dispatchers.IO) { readBounded(context, uri) }
            if (text == null) {
                error = resources.getString(R.string.ic_too_large)
                return@launch
            }
            val preview = runCatching { CsvImport.parseAuto(text, app.prefs.deviceId, System.currentTimeMillis()) }
                .getOrNull()
            if (preview == null || preview.records.isEmpty()) {
                error = resources.getString(R.string.ic_unrecognised)
                return@launch
            }
            val existing = app.repository.body.value?.records.orEmpty()
            val (insert, update) = CsvImport.mergeWithExisting(preview.records, existing)
            pending = Pending(insert, update, preview.sourceLabel)
        }
    }

    pending?.let { p ->
        ImportPreviewDialog(
            pending = p,
            busy = busy,
            onDismiss = { if (!busy) pending = null },
            onConfirm = {
                busy = true
                scope.launch {
                    app.repository.upsertRecords(p.insert + p.update)
                        .onSuccess { result = p.insert.size to p.update.size }
                        .onFailure { error = saveErrorMessage(context, it) }
                    busy = false
                    pending = null
                }
            },
        )
    }

    error?.let { message ->
        AlertDialog(
            onDismissRequest = { error = null },
            title = { Text(stringResource(R.string.ic_could_not)) },
            text = { Text(message) },
            confirmButton = { TextButton(onClick = { error = null }) { Text("OK") } },
        )
    }

    result?.let { (added, updated) ->
        AlertDialog(
            onDismissRequest = { result = null },
            title = { Text(stringResource(R.string.ic_imported)) },
            text = {
                Column {
                    Text(stringResource(R.string.ic_result, added, updated))
                    Spacer(Modifier.height(12.dp))
                    Text(
                        stringResource(R.string.ic_delete_file),
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            },
            confirmButton = { TextButton(onClick = { result = null }) { Text(stringResource(R.string.ic_done)) } },
        )
    }

    return { launcher.launch(arrayOf("*/*")) }
}

/**
 * Bounded by hand: readNBytes(int) is API 33 and minSdk here is 26, and an
 * unbounded readBytes() on a mistakenly picked video is an OOM.
 */
private fun readBounded(context: android.content.Context, uri: Uri): String? {
    context.contentResolver.openInputStream(uri).use { stream ->
        if (stream == null) return null
        val buffer = ByteArray(64 * 1024)
        val out = java.io.ByteArrayOutputStream()
        while (true) {
            val read = stream.read(buffer)
            if (read <= 0) break
            if (out.size() + read > MAX_CSV_BYTES) return null
            out.write(buffer, 0, read)
        }
        return out.toByteArray().decodeToString()
    }
}
// #endregion

// #region Preview
/**
 * Shown before anything is written. An import that silently rewrote existing
 * records would be indistinguishable from data loss.
 */
@Composable
private fun ImportPreviewDialog(
    pending: Pending,
    busy: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.ic_title, pending.sourceLabel)) },
        text = {
            Column {
                Text(
                    stringResource(
                        if (pending.insert.size == 1) R.string.ic_new_one else R.string.ic_new_many,
                        pending.insert.size,
                    ),
                )
                if (pending.update.isNotEmpty()) {
                    Spacer(Modifier.height(6.dp))
                    Text(
                        stringResource(
                            if (pending.update.size == 1) {
                                R.string.ic_update_one
                            } else {
                                R.string.ic_update_many
                            },
                            pending.update.size,
                        ),
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    stringResource(R.string.ic_local_only),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm, enabled = !busy) {
                Text(stringResource(if (busy) R.string.ic_importing else R.string.ic_import))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !busy) { Text(stringResource(R.string.msg_cancel)) }
        },
    )
}
// #endregion
