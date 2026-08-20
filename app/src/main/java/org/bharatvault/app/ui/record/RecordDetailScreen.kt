package org.bharatvault.app.ui.record

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.statusBarsPadding
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.ui.common.BrandTile
import org.bharatvault.app.ui.common.CopyChip
import org.bharatvault.app.ui.common.relativeTime
import org.bharatvault.app.ui.gallery.templateName
import org.bharatvault.app.ui.theme.FieldLabelStyle
import org.bharatvault.app.ui.theme.SecretTextStyle
import org.bharatvault.app.ui.theme.VaultTheme
import org.bharatvault.app.R
import org.bharatvault.app.quickunlock.QuickUnlockManager
import org.bharatvault.app.ui.common.ClipboardHelper
import org.bharatvault.app.ui.common.CompanyLogo
import org.bharatvault.app.ui.common.PassphraseAuthDialog
import org.bharatvault.app.ui.common.RevealAuth
import org.bharatvault.app.ui.common.fieldLabel
import org.bharatvault.app.ui.common.groupCardNumber
import org.bharatvault.app.ui.common.maskedValue
import org.bharatvault.app.ui.motion.LocalAnimatedVisibilityScope
import org.bharatvault.app.ui.motion.LocalSharedTransitionScope
import org.bharatvault.app.ui.theme.SecretTextStyle
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.Sensitivity
import org.bharatvault.core.model.TemplateField
import org.bharatvault.core.totp.Totp

@OptIn(ExperimentalSharedTransitionApi::class)
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
    var showMenu by remember { mutableStateOf(false) }

    val sharedScope = LocalSharedTransitionScope.current
    val animScope = LocalAnimatedVisibilityScope.current

    val vaultPaper = MaterialTheme.colorScheme.background
    val vaultPrimary = MaterialTheme.colorScheme.primary
    val vaultInk = MaterialTheme.colorScheme.onBackground
    val vaultMute = MaterialTheme.colorScheme.onSurfaceVariant
    val vaultSurface = MaterialTheme.colorScheme.surface
    val vaultLine = MaterialTheme.colorScheme.surfaceVariant

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(vaultPaper)
            .statusBarsPadding()
            .let {
                if (sharedScope != null && animScope != null) {
                    with(sharedScope) {
                        it.sharedBounds(
                            rememberSharedContentState(key = "container-${uuid}"),
                            animatedVisibilityScope = animScope
                        )
                    }
                } else it
            }
    ) {
        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
        ) {
            // Background color and decorative circle
            Box(modifier = Modifier.fillMaxSize().background(vaultPrimary.copy(alpha = 0.1f)))
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 24.dp, y = (-32).dp)
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(vaultPrimary.copy(alpha = 0.15f))
            )

            // Content at the bottom
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .let {
                            if (sharedScope != null && animScope != null) {
                                with(sharedScope) {
                                    it.sharedElement(
                                        rememberSharedContentState(key = "logo-${uuid}"),
                                        animatedVisibilityScope = animScope
                                    )
                                }
                            } else it
                        },
                    contentAlignment = Alignment.Center
                ) {
                    BrandTile(code = record.institution.ifBlank { record.title }, size = 56.dp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = record.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = vaultInk,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = listOf(
                            record.institution.ifBlank { templateName(record.template_id) },
                            "last edit " + relativeTime(record.modified_at),
                        ).joinToString(" · "),
                        fontSize = 11.sp,
                        color = vaultMute,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(vaultSurface)
                            .border(1.dp, vaultLine, CircleShape)
                            .clickable { showMenu = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.MoreHoriz,
                            contentDescription = "Options",
                            tint = vaultInk,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = { showMenu = false; onEdit() }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete", color = MaterialTheme.colorScheme.error) },
                            onClick = { showMenu = false; confirmDelete = true }
                        )
                    }
                }
            }
        }

        // Fields List
        val orderedFields = template?.fields?.filter { record.fields[it.k]?.isNotEmpty() == true } ?: emptyList()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 120.dp)
        ) {
            items(orderedFields, key = { it.k }) { field ->
                val value = record.fields[field.k] ?: return@items
                when (field.type) {
                    FieldType.TOTP -> TotpRow(field, value, template?.id ?: "")
                    FieldType.LINK -> LinkRow(app, field, value, template?.id ?: "", onOpenRecord)
                    else -> FieldRow(app, field, value, template?.id ?: "")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(record.custom_fields.size) { i ->
                val custom = record.custom_fields[i]
                FieldRow(
                    app = app,
                    field = TemplateField(k = custom.label, t = custom.type, s = if (custom.type == "SECRET") "H" else "L"),
                    value = custom.value,
                    templateId = record.template_id,
                    labelOverride = custom.label,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondary))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Never leaves this device. Encrypted at rest.",
                        fontSize = 11.sp,
                        color = vaultInk
                    )
                }
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

@Composable
fun FieldRow(
    app: BharatVaultApp,
    field: TemplateField,
    value: String,
    templateId: String,
    labelOverride: String? = null,
) {
    var revealed by remember { mutableStateOf(false) }
    var revealDeadline by remember { mutableLongStateOf(0L) }
    var authAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val scope = rememberCoroutineScope()

    val isHigh = field.sensitivity == Sensitivity.H
    val maskable = field.sensitivity != Sensitivity.L

    LaunchedEffect(revealDeadline) {
        if (revealDeadline > 0) {
            delay(10_000)
            revealed = false
        }
    }

    fun gated(action: () -> Unit) {
        if (!isHigh || RevealAuth.withinGrace()) {
            action()
            return
        }
        if (app.prefs.quickUnlockEnabled && QuickUnlockManager.isEnrolled(context)) {
            scope.launch {
                val result = QuickUnlockManager.unlock(activity, app)
                if (result is org.bharatvault.core.vault.UnlockResult.Success) {
                    result.vaultKey.fill(0)
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
            Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
        }
    }

    val displayValue = when {
        field.type == FieldType.CARDNUM && revealed -> groupCardNumber(value)
        else -> maskedValue(value, field, revealed || !maskable)
    }

    val label = labelOverride ?: fieldLabel(templateId, field.k)

    if (isHigh) {
        // High sensitivity style (black background)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VaultTheme.colors.ink)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label.uppercase(),
                    style = FieldLabelStyle,
                    color = VaultTheme.colors.paper.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = displayValue,
                    style = SecretTextStyle,
                    color = VaultTheme.colors.paper,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(VaultTheme.colors.paper.copy(alpha = 0.10f))
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                gated {
                                    revealed = true
                                    revealDeadline = System.currentTimeMillis()
                                }
                                tryAwaitRelease()
                                revealed = false
                            }
                        )
                    }
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (revealed) "Release to hide" else "Hold to reveal",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VaultTheme.colors.paper
                )
            }
        }
    } else {
        // Low sensitivity style (white background)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(VaultTheme.colors.card)
                .border(1.dp, VaultTheme.colors.ink(0.05f), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label.uppercase(),
                    style = FieldLabelStyle,
                    color = VaultTheme.colors.mute
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = displayValue,
                    style = SecretTextStyle,
                    color = VaultTheme.colors.ink,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            CopyChip(onClick = { copyValue() })
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

@Composable
fun TotpRow(field: TemplateField, secret: String, templateId: String) {
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
    val label = fieldLabel(templateId, field.k)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable {
                ClipboardHelper.copySensitive(context, code)
                Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = code.chunked((code.length + 1) / 2).joinToString(" "),
                fontFamily = FontFamily.Monospace,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }
        Box(contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                progress = { 1f },
                modifier = Modifier.size(36.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = 3.dp,
            )
            CircularProgressIndicator(
                progress = { remaining.toFloat() / params.periodSeconds },
                modifier = Modifier.size(36.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 3.dp,
            )
            Text(
                text = "${remaining}s",
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun LinkRow(
    app: BharatVaultApp,
    field: TemplateField,
    linkedUuid: String,
    templateId: String,
    onOpenRecord: (String) -> Unit,
) {
    val body by app.repository.body.collectAsState()
    val linked = body?.records?.firstOrNull { it.uuid == linkedUuid }
    val label = fieldLabel(templateId, field.k)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable(enabled = linked != null) { onOpenRecord(linkedUuid) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = linked?.title ?: stringResource(R.string.scr_edit_link_none),
                fontFamily = FontFamily.Monospace,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

