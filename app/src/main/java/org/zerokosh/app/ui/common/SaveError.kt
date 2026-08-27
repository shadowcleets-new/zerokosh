/**
 * @file SaveError.kt
 * @description Telling the user a write did not land (§4.4).
 *
 *              Saves used to be launched and forgotten: the screen navigated
 *              away on the assumption the vault reached disk. It usually did.
 *              When it did not — a sync folder whose permission grant lapsed is
 *              the realistic case — the record was gone and nothing said so.
 *
 *              For an app that guarantees no copy exists anywhere else, a lost
 *              write is the worst thing that can happen quietly. This is the
 *              one place that turns a failure into something a user can act on.
 */
package org.zerokosh.app.ui.common

// #region Imports
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
// #endregion

/**
 * What to tell the user. Errors name what went wrong and what to do about it —
 * a stack trace is not an instruction, and "something went wrong" is not either.
 *
 * The raw message is appended because the sync-folder cases are varied enough
 * that hiding the detail would leave the user guessing between them.
 */
fun saveErrorMessage(cause: Throwable?): String {
    val detail = cause?.message?.takeIf { it.isNotBlank() }
    return buildString {
        append("Your changes were not saved, so nothing has been lost from the vault as it was.")
        append("\n\n")
        append(
            "The vault file could not be written. If you have set a backup and sync " +
                "folder, Android may have withdrawn permission to it — open Settings, " +
                "pick the folder again, and retry.",
        )
        if (detail != null) append("\n\n").append(detail)
    }
}

/**
 * Shown when [message] is non-null. Deliberately not a snackbar: a save that did
 * not happen is not a passing notice, and the user has to decide what to do next.
 */
@Composable
fun SaveErrorDialog(message: String?, onDismiss: () -> Unit) {
    if (message == null) return
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Not saved", color = MaterialTheme.colorScheme.error) },
        text = { Text(message) },
        confirmButton = { TextButton(onClick = onDismiss) { Text("OK") } },
    )
}
