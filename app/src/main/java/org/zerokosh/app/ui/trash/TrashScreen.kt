/**
 * @file TrashScreen.kt
 * @description Recently deleted records, restorable for 30 days (§5.13).
 *
 *              Deletion used to be permanent the instant it happened, in an app
 *              that guarantees there is no reset link — so a mis-tap destroyed
 *              a credential with nothing to fall back on. Records now wait here.
 *              Without this screen they would wait invisibly, which is worse
 *              than either alternative.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. SCREEN
 * 3. ROW
 */
package org.zerokosh.app.ui.trash

// #region Imports
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.OnboardingTopBar
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.model.TRASH_TTL_DAYS
import org.zerokosh.core.model.TrashedRecord
// #endregion

// #region Screen
private const val DAY_MS = 86_400_000L

@Composable
fun TrashScreen(app: ZerokoshApp, onBack: () -> Unit) {
    val c = VaultTheme.colors
    val body by app.repository.body.collectAsState()
    val scope = rememberCoroutineScope()
    val trash = body?.trash.orEmpty()
    var confirmEmpty by remember { mutableStateOf(false) }
    var confirmPurge by remember { mutableStateOf<TrashedRecord?>(null) }
    val now = System.currentTimeMillis()

    Column(
        Modifier
            .fillMaxSize()
            .background(c.paper)
            .statusBarsPadding(),
    ) {
        OnboardingTopBar(stepLabel = "Recently deleted", onBack = onBack)

        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                "Deleted records wait here for $TRASH_TTL_DAYS days.",
                style = MaterialTheme.typography.bodyMedium,
                color = c.ink(0.6f),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "After that they are gone for good — there is no copy anywhere else.",
                fontSize = 12.sp,
                color = c.ink(0.45f),
            )
        }
        Spacer(Modifier.height(16.dp))

        if (trash.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nothing deleted.", color = c.ink(0.45f))
            }
            return@Column
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth().navigationBarsPadding(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 24.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(trash, key = { it.record.uuid }) { entry ->
                TrashRow(
                    entry = entry,
                    daysLeft = TRASH_TTL_DAYS - ((now - entry.deleted_at) / DAY_MS).toInt(),
                    onRestore = { scope.launch { app.repository.restoreRecord(entry.record.uuid) } },
                    onPurge = { confirmPurge = entry },
                )
            }
            item {
                Spacer(Modifier.height(12.dp))
                TextButton(onClick = { confirmEmpty = true }, modifier = Modifier.fillMaxWidth()) {
                    Text("Delete all permanently", color = MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (confirmEmpty) {
        DestructiveDialog(
            title = "Delete everything in the trash?",
            body = "${trash.size} record${if (trash.size == 1) "" else "s"} will be gone permanently. " +
                "This cannot be undone and there is no backup to restore from.",
            onDismiss = { confirmEmpty = false },
            onConfirm = {
                scope.launch { app.repository.emptyTrash() }
                confirmEmpty = false
            },
        )
    }

    confirmPurge?.let { entry ->
        DestructiveDialog(
            title = "Delete \"${entry.record.title}\" permanently?",
            body = "This cannot be undone.",
            onDismiss = { confirmPurge = null },
            onConfirm = {
                scope.launch { app.repository.purgeRecord(entry.record.uuid) }
                confirmPurge = null
            },
        )
    }
}

@Composable
private fun DestructiveDialog(
    title: String,
    body: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = { Text(body) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Delete", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}
// #endregion

// #region Row
@Composable
private fun TrashRow(
    entry: TrashedRecord,
    daysLeft: Int,
    onRestore: () -> Unit,
    onPurge: () -> Unit,
) {
    val c = VaultTheme.colors
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(c.surface)
            .padding(horizontal = 16.dp, vertical = 12.dp),
    ) {
        Text(
            entry.record.title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = c.ink,
        )
        Text(
            if (daysLeft <= 0) "gone today" else "$daysLeft day${if (daysLeft == 1) "" else "s"} left",
            fontSize = 11.sp,
            color = if (daysLeft <= 3) MaterialTheme.colorScheme.error else c.ink(0.45f),
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                "Restore",
                modifier = Modifier.clickable(onClick = onRestore),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = c.primary,
            )
            Text(
                "Delete now",
                modifier = Modifier.clickable(onClick = onPurge),
                fontSize = 13.sp,
                color = c.ink(0.55f),
            )
        }
    }
}
// #endregion
