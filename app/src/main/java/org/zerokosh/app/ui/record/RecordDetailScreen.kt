package org.zerokosh.app.ui.record

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalResources
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
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.CopyChip
import org.zerokosh.app.ui.common.relativeTime
import org.zerokosh.app.ui.gallery.templateName
import org.zerokosh.app.ui.theme.FieldLabelStyle
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.app.R
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.app.ui.common.ClipboardHelper
import org.zerokosh.app.ui.common.CompanyLogo
import org.zerokosh.app.ui.common.PassphraseAuthDialog
import org.zerokosh.app.ui.common.RevealAuth
import org.zerokosh.app.ui.common.fieldLabel
import org.zerokosh.app.ui.common.groupCardNumber
import org.zerokosh.app.ui.common.SaveErrorDialog
import org.zerokosh.app.ui.common.saveErrorMessage
import org.zerokosh.app.ui.common.maskedValue
import org.zerokosh.core.model.Record
import org.zerokosh.app.ui.theme.JetBrainsMono
import org.zerokosh.app.ui.motion.LocalAnimatedVisibilityScope
import org.zerokosh.app.ui.motion.LocalSharedTransitionScope
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.core.model.FieldType
import org.zerokosh.core.model.Sensitivity
import org.zerokosh.core.model.TemplateField
import org.zerokosh.core.totp.Totp

@OptIn(
    ExperimentalSharedTransitionApi::class,
    androidx.compose.material3.ExperimentalMaterial3ExpressiveApi::class,
)
@Composable
fun RecordDetailScreen(
    app: ZerokoshApp,
    uuid: String,
    onEdit: () -> Unit,
    onOpenRecord: (String) -> Unit,
    onClose: () -> Unit,
) {
    val context = LocalContext.current
    val body by app.repository.body.collectAsState()
    val record = body?.records?.firstOrNull { it.uuid == uuid } ?: run { onClose(); return }
    val template = app.catalog.templates.byId(record.template_id)
    val scope = rememberCoroutineScope()
    var saveError by remember { mutableStateOf<String?>(null) }
    var confirmDelete by remember { mutableStateOf(false) }

    val sharedScope = LocalSharedTransitionScope.current
    val animScope = LocalAnimatedVisibilityScope.current

    val vaultPaper = MaterialTheme.colorScheme.background
    val vaultPrimary = MaterialTheme.colorScheme.primary
    val vaultInk = MaterialTheme.colorScheme.onBackground
    val vaultMute = MaterialTheme.colorScheme.onSurfaceVariant
    val vaultSurface = MaterialTheme.colorScheme.surface
    val vaultLine = MaterialTheme.colorScheme.surfaceVariant

    Box(modifier = Modifier.fillMaxSize()) {
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
                                stringResource(R.string.rd_last_edit, relativeTime(record.modified_at)),
                            ).joinToString(" · "),
                            fontSize = 11.sp,
                            color = vaultMute,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
                // What the record used to hold. Only shown when there is
                // something, so an untouched vault never sees the section.
                if (record.history.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        HistorySection(app = app, record = record)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
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
                            text = stringResource(R.string.hm_reassurance),
                            fontSize = 11.sp,
                            color = vaultInk
                        )
                    }
                }
            }
        }

        // Edit and Delete used to hide behind a "..." overflow menu. The
        // Expressive floating toolbar surfaces both over the scrolling content,
        // which is what the toolbar exists for -- and the LazyColumn already
        // reserved 120dp of bottom padding for it.
        HorizontalFloatingToolbar(
            expanded = true,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 24.dp),
            colors = FloatingToolbarDefaults.vibrantFloatingToolbarColors(
                toolbarContainerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                toolbarContentColor = MaterialTheme.colorScheme.onSurface,
                fabContainerColor = MaterialTheme.colorScheme.primary,
                fabContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            floatingActionButton = {
                FloatingToolbarDefaults.VibrantFloatingActionButton(
                    onClick = onEdit,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.scr_detail_edit))
                }
            },
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.msg_back))
            }
            IconButton(onClick = { confirmDelete = true }) {
                Icon(
                    Icons.Filled.DeleteOutline,
                    contentDescription = stringResource(R.string.scr_detail_delete),
                    tint = MaterialTheme.colorScheme.error,
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
                            .onSuccess { onClose() }
                            .onFailure { saveError = saveErrorMessage(context, it) }
                    }
                }) { Text(stringResource(R.string.scr_detail_delete_confirm_yes), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        )
    }

    SaveErrorDialog(saveError) { saveError = null }
}

/**
 * Values this record used to hold (§5.13).
 *
 * Gated by the same [RevealAuth] as any other high-sensitivity reveal, because
 * an old password is still a password — and often still in use somewhere else,
 * which is exactly why it is worth keeping and exactly why it is worth
 * protecting.
 */
@Composable
private fun HistorySection(app: ZerokoshApp, record: Record) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var shown by remember(record.uuid) { mutableStateOf(false) }
    var authing by remember { mutableStateOf(false) }
    var confirmForget by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(VaultTheme.colors.ink(0.04f))
            .padding(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.scr_detail_history_title),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = VaultTheme.colors.ink,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = if (shown) {
                    stringResource(R.string.rd_history_hide)
                } else {
                    stringResource(R.string.rd_history_show, record.history.size)
                },
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = VaultTheme.colors.primary,
                modifier = Modifier.clickable {
                    if (shown) {
                        shown = false
                    } else if (RevealAuth.withinGrace()) {
                        shown = true
                    } else {
                        authing = true
                    }
                },
            )
        }

        if (shown) {
            record.history.forEach { past ->
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = fieldLabel(context, record.template_id, past.k),
                    fontSize = 10.5.sp,
                    color = VaultTheme.colors.ink(0.45f),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = past.value,
                        fontFamily = JetBrainsMono,
                        fontSize = 13.sp,
                        color = VaultTheme.colors.ink,
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = stringResource(R.string.scr_detail_copy),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VaultTheme.colors.primary,
                        modifier = Modifier.clickable {
                            ClipboardHelper.copySensitive(context, past.value)
                        },
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.rd_forget_these),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable { confirmForget = true },
            )
        }
    }

    if (authing) {
        PassphraseAuthDialog(
            repository = app.repository,
            onSuccess = {
                RevealAuth.markAuthenticated()
                authing = false
                shown = true
            },
            onDismiss = { authing = false },
        )
    }

    if (confirmForget) {
        AlertDialog(
            onDismissRequest = { confirmForget = false },
            title = { Text(stringResource(R.string.rd_forget_title)) },
            text = { Text(stringResource(R.string.rd_forget_body, record.history.size)) },
            confirmButton = {
                TextButton(onClick = {
                    confirmForget = false
                    shown = false
                    scope.launch { app.repository.forgetHistory(record.uuid) }
                }) { Text(stringResource(R.string.rd_forget), color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { confirmForget = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        )
    }
}

/** Quick unlock is only an option when the user enabled it and enrolled. */
private fun biometricReady(app: ZerokoshApp, context: android.content.Context): Boolean =
    app.prefs.quickUnlockEnabled && QuickUnlockManager.isEnrolled(context)

/**
 * Prompts, and reports whether the user proved themselves. The key material is
 * wiped immediately — this call is asking "is it you", not opening the vault,
 * which is already open.
 */
private suspend fun authenticateByBiometric(activity: FragmentActivity, app: ZerokoshApp): Boolean {
    val result = QuickUnlockManager.unlock(activity, app)
    if (result !is org.zerokosh.core.vault.UnlockResult.Success) return false
    result.vaultKey.fill(0)
    RevealAuth.markAuthenticated()
    return true
}

/**
 * A low-sensitivity value goes to the clipboard as-is; anything else is copied
 * through the path that clears it again after 30 seconds.
 */
private fun copyFieldValue(context: android.content.Context, field: TemplateField, value: String) {
    if (field.sensitivity == Sensitivity.L) {
        ClipboardHelper.copyPlain(context, value)
        return
    }
    ClipboardHelper.copySensitive(context, value)
    Toast.makeText(context, context.getString(R.string.rd_copied), Toast.LENGTH_SHORT).show()
}

@Composable
fun FieldRow(
    app: ZerokoshApp,
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
        if (!biometricReady(app, context)) {
            authAction = action
            return
        }
        scope.launch { if (authenticateByBiometric(activity, app)) action() }
    }

    fun copyValue() = copyFieldValue(context, field, value)

    val displayValue = if (field.type == FieldType.CARDNUM && revealed) {
        groupCardNumber(value)
    } else {
        maskedValue(value, field, revealed || !maskable)
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
                    text = stringResource(
                        if (revealed) R.string.rd_release_to_hide else R.string.rd_hold_to_reveal,
                    ),
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
    val resources = LocalResources.current
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
                Toast.makeText(context, resources.getString(R.string.rd_copied), Toast.LENGTH_SHORT).show()
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
    app: ZerokoshApp,
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

