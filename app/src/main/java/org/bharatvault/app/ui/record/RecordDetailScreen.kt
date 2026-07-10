/**
 * @file RecordDetailScreen.kt
 * @description S9 (§5.1/§10.4): fields in template order; masked per §2.3;
 *              copy/reveal per row; H fields gated by fresh auth (§5.4);
 *              reveal auto-re-masks after 10 s; live TOTP with ring.
 *
 * [TABLE OF CONTENTS]
 * 1. SCREEN + HEADER ACTIONS
 * 2. FIELD ROW (copy / reveal / auth gate)
 * 3. TOTP ROW
 */
package org.bharatvault.app.ui.record

// #region Imports
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import android.widget.Toast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.quickunlock.QuickUnlockManager
import org.bharatvault.app.ui.common.ClipboardHelper
import org.bharatvault.app.ui.common.PassphraseAuthDialog
import org.bharatvault.app.ui.common.RevealAuth
import org.bharatvault.app.ui.common.fieldLabel
import org.bharatvault.app.ui.common.groupCardNumber
import org.bharatvault.app.ui.common.maskedValue
import org.bharatvault.app.ui.theme.SecretTextStyle
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.Sensitivity
import org.bharatvault.core.model.TemplateField
import org.bharatvault.core.totp.Totp
// #endregion

// #region Screen + header actions
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordDetailScreen(
    app: BharatVaultApp,
    uuid: String,
    onEdit: () -> Unit,
    onOpenRecord: (String) -> Unit,
    onClose: () -> Unit,
) {
    val body by app.repository.body.collectAsState()
    val record = body?.records?.firstOrNull { it.uuid == uuid } ?: run { onClose(); return }
    val template = app.catalog.templates.byId(record.template_id)
    val scope = rememberCoroutineScope()
    var confirmDelete by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(record.title) },
                navigationIcon = {
                    IconButton(onClick = onClose) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.msg_back))
                    }
                },
                actions = {
                    IconButton(onClick = { scope.launch { app.repository.toggleFavorite(uuid) } }) {
                        Icon(
                            if (record.favorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = stringResource(R.string.scr_detail_favorite),
                            tint = if (record.favorite) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.scr_detail_edit))
                    }
                    IconButton(onClick = { confirmDelete = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.scr_detail_delete))
                    }
                },
            )
        },
    ) { padding ->
        val orderedFields = template?.fields?.filter { record.fields[it.k]?.isNotEmpty() == true } ?: emptyList()
        LazyColumn(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp)) {
            items(orderedFields, key = { it.k }) { field ->
                val value = record.fields[field.k] ?: return@items
                when (field.type) {
                    FieldType.TOTP -> TotpRow(app, record.template_id, field, value)
                    FieldType.LINK -> LinkRow(app, record.template_id, field, value, onOpenRecord)
                    else -> FieldRow(app, record.template_id, field, value)
                }
            }
            items(record.custom_fields.size) { i ->
                val custom = record.custom_fields[i]
                FieldRow(
                    app = app,
                    templateId = record.template_id,
                    field = TemplateField(k = custom.label, t = custom.type, s = if (custom.type == "SECRET") "H" else "L"),
                    value = custom.value,
                    labelOverride = custom.label,
                )
            }
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text(stringResource(R.string.scr_detail_delete_confirm_title)) },
            text = { Text(stringResource(R.string.scr_detail_delete_confirm_body)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmDelete = false
                    scope.launch {
                        app.repository.deleteRecord(uuid)
                        onClose()
                    }
                }) { Text(stringResource(R.string.scr_detail_delete_confirm_yes), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        )
    }
}
// #endregion

// #region Field row (§10.4 anatomy: label / value / copy / eye)
@Composable
fun FieldRow(
    app: BharatVaultApp,
    templateId: String,
    field: TemplateField,
    value: String,
    labelOverride: String? = null,
) {
    var revealed by remember { mutableStateOf(false) }
    var revealDeadline by remember { mutableLongStateOf(0L) }
    var authAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val scope = rememberCoroutineScope()
    val copiedMsg = stringResource(R.string.scr_detail_copied)
    val hiddenDesc = stringResource(R.string.cd_mask_hidden)

    // §2.3/§10.4: auto-re-mask after 10 s
    LaunchedEffect(revealDeadline) {
        if (revealDeadline > 0) {
            delay(10_000)
            revealed = false
        }
    }

    val isHigh = field.sensitivity == Sensitivity.H
    val maskable = field.sensitivity != Sensitivity.L

    /** §5.4: H needs fresh auth (biometric if enabled, else passphrase), 60 s grace. */
    fun gated(action: () -> Unit) {
        if (!isHigh || RevealAuth.withinGrace()) {
            action()
            return
        }
        if (app.prefs.quickUnlockEnabled && QuickUnlockManager.isEnrolled(context)) {
            scope.launch {
                val result = QuickUnlockManager.unlock(activity, app)
                if (result is org.bharatvault.core.vault.UnlockResult.Success) {
                    result.vaultKey.fill(0) // only needed the auth, session already open
                    RevealAuth.markAuthenticated()
                    action()
                }
            }
        } else {
            authAction = action
        }
    }

    fun copyValue() {
        if (field.sensitivity == Sensitivity.L) {
            ClipboardHelper.copyPlain(context, value)
        } else {
            ClipboardHelper.copySensitive(context, value)
            Toast.makeText(context, copiedMsg, Toast.LENGTH_SHORT).show()
        }
    }

    val displayValue = when {
        field.type == FieldType.CARDNUM && revealed -> groupCardNumber(value)
        else -> maskedValue(value, field, revealed || !maskable)
    }
    val monospace = field.type in setOf(FieldType.SECRET, FieldType.PIN, FieldType.CARDNUM, FieldType.TOTP)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { gated(::copyValue) }, // §10.4: tapping value = copy
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    labelOverride ?: fieldLabel(templateId, field.k),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                if (isHigh) {
                    Spacer(Modifier.width(4.dp))
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = stringResource(R.string.cd_shield_high_sensitivity),
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            Text(
                displayValue,
                style = if (monospace) SecretTextStyle else MaterialTheme.typography.bodyLarge,
                modifier = Modifier.semantics {
                    if (maskable && !revealed) contentDescription = hiddenDesc // §10.7
                },
            )
        }
        IconButton(onClick = { gated(::copyValue) }) {
            Icon(Icons.Outlined.ContentCopy, contentDescription = stringResource(R.string.scr_detail_copy))
        }
        if (maskable) {
            IconButton(onClick = {
                if (revealed) {
                    revealed = false
                } else {
                    gated {
                        revealed = true
                        revealDeadline = System.currentTimeMillis()
                    }
                }
            }) {
                Icon(
                    if (revealed) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                    contentDescription = stringResource(if (revealed) R.string.scr_detail_hide else R.string.scr_detail_reveal),
                )
            }
        }
    }

    authAction?.let { pending ->
        PassphraseAuthDialog(
            repository = app.repository,
            onSuccess = {
                authAction = null
                pending()
            },
            onDismiss = { authAction = null },
        )
    }
}
// #endregion

// #region TOTP row (§10.4: "123 456" + progress ring) and LINK row
@Composable
fun TotpRow(app: BharatVaultApp, templateId: String, field: TemplateField, secret: String) {
    var nowMs by remember { mutableLongStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            nowMs = System.currentTimeMillis()
            delay(1000)
        }
    }
    val params = remember(secret) {
        Totp.parseOtpauthUri(secret) ?: Totp.Params(secretBase32 = secret)
    }
    val code = remember(nowMs / 1000 / params.periodSeconds, params) {
        runCatching { Totp.code(params, nowMs) }.getOrDefault("——————")
    }
    val remaining = Totp.secondsRemaining(params, nowMs)
    val context = LocalContext.current
    val copiedMsg = stringResource(R.string.scr_detail_copied)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable {
                ClipboardHelper.copySensitive(context, code)
                Toast.makeText(context, copiedMsg, Toast.LENGTH_SHORT).show()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                fieldLabel(templateId, field.k),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                code.chunked((code.length + 1) / 2).joinToString(" "),
                style = SecretTextStyle.copy(fontSize = MaterialTheme.typography.titleLarge.fontSize),
                color = MaterialTheme.colorScheme.primary,
            )
        }
        CircularProgressIndicator(
            progress = { remaining.toFloat() / params.periodSeconds },
            modifier = Modifier.size(28.dp),
            strokeWidth = 3.dp,
        )
        Spacer(Modifier.width(8.dp))
        Text("$remaining", style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun LinkRow(
    app: BharatVaultApp,
    templateId: String,
    field: TemplateField,
    linkedUuid: String,
    onOpenRecord: (String) -> Unit,
) {
    val body by app.repository.body.collectAsState()
    val linked = body?.records?.firstOrNull { it.uuid == linkedUuid }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable(enabled = linked != null) { onOpenRecord(linkedUuid) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                fieldLabel(templateId, field.k),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                linked?.title ?: stringResource(R.string.scr_edit_link_none),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
// #endregion
