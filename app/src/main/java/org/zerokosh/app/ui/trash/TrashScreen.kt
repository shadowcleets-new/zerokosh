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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.OnboardingTopBar
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.model.TRASH_TTL_DAYS
import org.zerokosh.core.model.TrashedRecord
// #endregion

// #region Screen
private const val DAY_MS = 86_400_000L

/** "gone today" / "1 day left" / "30 days left". */
@Composable
private fun trashCountdown(daysLeft: Int): String = when {
    daysLeft <= 0 -> stringResource(R.string.tr_gone_today)
    daysLeft == 1 -> stringResource(R.string.tr_days_one, daysLeft)
    else -> stringResource(R.string.tr_days_many, daysLeft)
}

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
        OnboardingTopBar(stepLabel = stringResource(R.string.st_recently_deleted), onBack = onBack)

        Column(Modifier.padding(horizontal = 24.dp)) {
            Text(
                stringResource(R.string.tr_ttl_note, TRASH_TTL_DAYS),
                style = MaterialTheme.typography.bodyMedium,
                color = c.ink(0.6f),
            )
            Spacer(Modifier.height(4.dp))
            Text(
                stringResource(R.string.tr_ttl_warn),
                fontSize = 12.sp,
                color = c.ink(0.45f),
            )
        }
        Spacer(Modifier.height(16.dp))

        if (trash.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.tr_empty), color = c.ink(0.45f))
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
                    Text(stringResource(R.string.tr_delete_all), color = MaterialTheme.colorScheme.error)
                }
                Spacer(Modifier.height(24.dp))
            }
        }
    }

    if (confirmEmpty) {
        DestructiveDialog(
            title = stringResource(R.string.tr_delete_all_title),
            body = stringResource(
                if (trash.size == 1) R.string.tr_delete_all_one else R.string.tr_delete_all_many,
                trash.size,
            ),
            onDismiss = { confirmEmpty = false },
            onConfirm = {
                scope.launch { app.repository.emptyTrash() }
                confirmEmpty = false
            },
        )
    }

    confirmPurge?.let { entry ->
        DestructiveDialog(
            title = stringResource(R.string.tr_delete_one_title, entry.record.title),
            body = stringResource(R.string.tr_cannot_undo),
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
                Text(stringResource(R.string.scr_detail_delete), color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) } },
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
            trashCountdown(daysLeft),
            fontSize = 11.sp,
            color = if (daysLeft <= 3) MaterialTheme.colorScheme.error else c.ink(0.45f),
        )
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                stringResource(R.string.tr_restore),
                modifier = Modifier.clickable(onClick = onRestore),
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = c.primary,
            )
            Text(
                stringResource(R.string.tr_delete_now),
                modifier = Modifier.clickable(onClick = onPurge),
                fontSize = 13.sp,
                color = c.ink(0.55f),
            )
        }
    }
}
// #endregion
