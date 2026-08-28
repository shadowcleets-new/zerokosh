/**
 * @file SettingsScreen.kt
 * @description S12 (§5.1): auto-lock, quick-unlock, change passphrase, new
 *              recovery kit, sync folder (M3), import/export (M5), language,
 *              About with donation links, security info.
 *
 * [TABLE OF CONTENTS]
 * 1. SCREEN LIST
 * 2. CHANGE PASSPHRASE DIALOG
 * 3. NEW RECOVERY KEY DIALOG
 * 4. SYNC FOLDER DIALOG
 * 5. ABOUT
 */
package org.zerokosh.app.ui.settings

// #region Imports
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FolderZip
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.*
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import org.zerokosh.app.autofill.AutofillSetup
import org.zerokosh.app.ui.common.RevealToggle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.SectionLabel
import org.zerokosh.app.ui.common.WhiteCard
import org.zerokosh.app.ui.backup.rememberImportBackup
import org.zerokosh.app.ui.backup.rememberImportCsv
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.app.ui.onboarding.passphraseScore
import org.zerokosh.app.ui.record.DropdownSelector
// #endregion

// #region Screen list
/** "Active · Vault" or "Not backed up" — its own function to keep the branching
 *  out of SettingsScreen, which sits at the top of the complexity baseline. */
@Composable
private fun syncFolderValue(isConfigured: Boolean, folderName: String?): String {
    if (!isConfigured) return stringResource(R.string.scr_settings_sync_not_set)
    val name = folderName ?: stringResource(R.string.st_folder_fallback)
    return stringResource(R.string.st_active_value, name)
}

@Composable
fun SettingsScreen(
    app: ZerokoshApp,
    onOpenTrash: () -> Unit = {},
    onOpenHealth: () -> Unit = {},
) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val scope = rememberCoroutineScope()
    var showChangePass by remember { mutableStateOf(false) }
    var showNewRecovery by remember { mutableStateOf(false) }
    var quickUnlockOn by remember { mutableStateOf(app.prefs.quickUnlockEnabled) }
    var allowShots by remember { mutableStateOf(app.prefs.allowScreenshots) }
    val autofillSupported = remember { AutofillSetup.isSupported(context) }
    val trashCount = app.repository.body.collectAsState().value?.trash?.size ?: 0
    var autofillOn by remember { mutableStateOf(AutofillSetup.isEnabled(context)) }
    val lifecycleOwner = context as? LifecycleOwner
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) autofillOn = AutofillSetup.isEnabled(context)
        }
        lifecycleOwner?.lifecycle?.addObserver(observer)
        onDispose { lifecycleOwner?.lifecycle?.removeObserver(observer) }
    }
    var enableQuickUnlockAsk by remember { mutableStateOf(false) }
    
    // Sync folder state
    var syncUri by remember { mutableStateOf(app.prefs.syncFolderUri) }
    var showSyncSheet by remember { mutableStateOf(false) }
    var syncBusy by remember { mutableStateOf(false) }

    val syncFolderLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocumentTree()
    ) { uri: Uri? ->
        if (uri != null) {
            runCatching {
                val flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flags)
            }
            runCatching {
                app.repository.updateSyncFolder(context, uri)
                syncUri = uri.toString()
            }.onSuccess {
                scope.launch {
                    syncBusy = true
                    val success = runCatching { app.repository.manualBackup(context) }.getOrDefault(false)
                    syncBusy = false
                    val folderName = runCatching { DocumentFile.fromTreeUri(context, uri)?.name }.getOrNull() ?: context.getString(R.string.st_folder_fallback)
                    if (success) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.st_toast_folder_backed, folderName),
                            Toast.LENGTH_LONG,
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.st_toast_folder_connected, folderName),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
            }.onFailure { e ->
                Toast.makeText(
                    context,
                    context.getString(R.string.st_toast_folder_failed, e.localizedMessage.orEmpty()),
                    Toast.LENGTH_LONG,
                ).show()
            }
        }
    }
    
    // Export a copy of the encrypted vault file. Safe to hand out: it is
    // ciphertext, useless without the passphrase or the Recovery Kit.
    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/octet-stream")
    ) { uri: Uri? ->
        if (uri != null) {
            // BV-09: reading the vault and writing it to a content URI is file I/O;
            // it was running on the main thread inside the picker callback.
            scope.launch {
                val ok = withContext(Dispatchers.IO) {
                    runCatching {
                        val bytes = app.repository.store.read() ?: return@runCatching false
                        context.contentResolver.openOutputStream(uri)?.use { it.write(bytes) }
                            ?: return@runCatching false
                        true
                    }.getOrDefault(false)
                }
                Toast.makeText(
                    context,
                    context.getString(
                        if (ok) R.string.st_toast_exported else R.string.st_toast_export_failed,
                    ),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    val importBackup = rememberImportBackup(app)
    val importCsv = rememberImportCsv(app)

    // Dropdown states
    var showAutoLockDropdown by remember { mutableStateOf(false) }
    var showLanguageDropdown by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VaultTheme.colors.paper)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        // Header - mono kicker over the serif statement, plus a lock-now action.
        Column(modifier = Modifier.padding(horizontal = 24.dp).padding(top = 16.dp, bottom = 8.dp)) {
            Kicker(stringResource(R.string.tab_settings))
            Spacer(Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.st_subtitle),
                    style = MaterialTheme.typography.displaySmall,
                    color = VaultTheme.colors.ink,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { app.repository.lock() }) {
                    Icon(Icons.Outlined.Lock, contentDescription = stringResource(R.string.scr_lock_title), tint = VaultTheme.colors.ink)
                }
            }
        }

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
            // Security Group
            SettingsGroupTitle(stringResource(R.string.st_group_security))
            SettingsCard {
                // Auto-lock
                val autoLockOptions = listOf(
                    0 to stringResource(R.string.scr_settings_autolock_immediately),
                    1 to stringResource(R.string.scr_settings_autolock_1min),
                    5 to stringResource(R.string.scr_settings_autolock_5min),
                    15 to stringResource(R.string.scr_settings_autolock_15min),
                )
                val currentAutoLock = autoLockOptions.firstOrNull { it.first == app.prefs.autoLockMinutes }?.second ?: autoLockOptions[1].second
                
                Box {
                    SettingsRow(
                        title = stringResource(R.string.scr_settings_autolock),
                        value = currentAutoLock,
                        onClick = { showAutoLockDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showAutoLockDropdown,
                        onDismissRequest = { showAutoLockDropdown = false }
                    ) {
                        autoLockOptions.forEach { (minutes, label) ->
                            DropdownMenuItem(
                                text = { Text(label, style = MaterialTheme.typography.bodyMedium) },
                                onClick = {
                                    app.prefs.autoLockMinutes = minutes
                                    showAutoLockDropdown = false
                                }
                            )
                        }
                    }
                }
                SettingsDivider()
                SettingsRowToggle(
                    title = stringResource(R.string.scr_settings_quick_unlock),
                    checked = quickUnlockOn,
                    enabled = QuickUnlockManager.hardwareBackedBiometricsAvailable(context),
                    onToggle = { turnOn ->
                        if (turnOn) {
                            enableQuickUnlockAsk = true
                        } else {
                            QuickUnlockManager.disable(context, app)
                            quickUnlockOn = false
                        }
                    }
                )
                SettingsDivider()
                SettingsRowToggle(
                    title = stringResource(R.string.scr_settings_allow_screenshots),
                    checked = allowShots,
                    onToggle = { on ->
                        app.prefs.allowScreenshots = on
                        allowShots = on
                        (context as? MainActivity)?.applyScreenPrivacy()
                    }
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.scr_settings_autofill),
                    value = when {
                        !autofillSupported -> stringResource(R.string.scr_settings_autofill_unsupported)
                        autofillOn -> stringResource(R.string.scr_settings_autofill_on)
                        else -> stringResource(R.string.scr_settings_autofill_off)
                    },
                    valueColor = if (autofillOn) VaultTheme.colors.accent else Color.Unspecified,
                    onClick = {
                        if (!autofillSupported) return@SettingsRow
                        // The OS owns this choice and shows its own confirmation,
                        // which is exactly why an app cannot make itself the
                        // password provider quietly. runCatching because a few
                        // OEM builds ship no activity for this intent at all.
                        runCatching { context.startActivity(AutofillSetup.intent(context)) }
                    }
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.st_vault_review),
                    value = stringResource(R.string.st_vault_review_detail),
                    onClick = onOpenHealth,
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.st_recently_deleted),
                    value = if (trashCount > 0) "$trashCount" else null,
                    onClick = onOpenTrash,
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.scr_settings_change_passphrase),
                    onClick = { showChangePass = true }
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.scr_settings_new_recovery),
                    onClick = { showNewRecovery = true }
                )
            }
            
            Spacer(Modifier.height(24.dp))
            
            // Sync Group
            SettingsGroupTitle(stringResource(R.string.st_group_sync))
            SettingsCard {
                val isConfigured = syncUri.isNotEmpty()
                // Read outside remember{}: a lambda is not a composable context.
                val activeFolderFallback = stringResource(R.string.st_active_folder)
                val folderName = remember(syncUri, activeFolderFallback) {
                    if (isConfigured) {
                        runCatching {
                            DocumentFile.fromTreeUri(context, Uri.parse(syncUri))?.name
                        }.getOrNull() ?: activeFolderFallback
                    } else null
                }

                SettingsRow(
                    title = stringResource(R.string.scr_settings_sync_folder),
                    value = syncFolderValue(isConfigured, folderName),
                    valueColor = if (isConfigured) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    onClick = {
                        if (isConfigured) {
                            showSyncSheet = true
                        } else {
                            syncFolderLauncher.launch(null)
                        }
                    }
                )
            }
            
            Spacer(Modifier.height(24.dp))

            // Appearance Group
            SettingsGroupTitle(stringResource(R.string.st_group_appearance))
            SettingsCard {
                Box {
                    var showThemeDropdown by remember { mutableStateOf(false) }
                    val currentTheme = when (app.prefs.themeOption) {
                        1 -> stringResource(R.string.st_theme_light)
                        2 -> stringResource(R.string.st_theme_dark)
                        else -> stringResource(R.string.st_theme_system)
                    }
                    SettingsRow(
                        title = stringResource(R.string.st_theme),
                        value = currentTheme,
                        onClick = { showThemeDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showThemeDropdown,
                        onDismissRequest = { showThemeDropdown = false }
                    ) {
                        listOf(
                            stringResource(R.string.st_theme_system) to 0,
                            stringResource(R.string.st_theme_light) to 1,
                            stringResource(R.string.st_theme_dark) to 2,
                        ).forEach { (label, opt) ->
                            DropdownMenuItem(
                                text = { Text(label, style = MaterialTheme.typography.bodyMedium) },
                                onClick = {
                                    app.prefs.themeOption = opt
                                    showThemeDropdown = false
                                    (context as? Activity)?.recreate()
                                }
                            )
                        }
                    }
                }
                SettingsDivider()
                Box {
                    val currentLang = if (app.prefs.languageTag == "hi") "हिन्दी" else "English"
                    SettingsRow(
                        title = stringResource(R.string.scr_settings_language),
                        value = currentLang,
                        onClick = { showLanguageDropdown = true }
                    )
                    DropdownMenu(
                        expanded = showLanguageDropdown,
                        onDismissRequest = { showLanguageDropdown = false }
                    ) {
                        listOf("English" to "en", "हिन्दी" to "hi").forEach { (label, tag) ->
                            DropdownMenuItem(
                                text = { Text(label, style = MaterialTheme.typography.bodyMedium) },
                                onClick = {
                                    app.prefs.languageTag = tag
                                    showLanguageDropdown = false
                                    (context as? Activity)?.recreate()
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            
            // About Section
            SettingsGroupTitle(stringResource(R.string.st_group_about))
            SettingsCard {
                SettingsRow(
                    title = stringResource(R.string.scr_settings_about),
                    value = "v0.1.0",
                    onClick = { }
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.st_license),
                    value = "GPL-3.0",
                    onClick = { }
                )
                SettingsDivider()
                SettingsRow(
                    title = stringResource(R.string.st_logos_by),
                    value = "Logo.dev",
                    onClick = { }
                )
            }

            Spacer(Modifier.height(24.dp))

            // The terminal action from the mockup: hand the user their own ciphertext.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(VaultTheme.colors.primary.copy(alpha = 0.05f))
                    .border(1.dp, VaultTheme.colors.primary.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                    .clickable { exportLauncher.launch("zerokosh-backup.kosh") }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(R.string.st_export_kosh),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = VaultTheme.colors.primary,
                )
            }

            Spacer(Modifier.height(10.dp))

            // The other half of export. Outlined rather than tinted: importing
            // merges into the open vault, so it is the heavier of the two.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, VaultTheme.colors.line, RoundedCornerShape(16.dp))
                    .clickable(onClick = importBackup)
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    stringResource(R.string.st_import_kosh),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = VaultTheme.colors.ink(0.8f),
                )
            }

            Spacer(Modifier.height(10.dp))

            // The route in from everywhere else. :core has parsed these formats
            // since M5; until now nothing called it.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, VaultTheme.colors.line, RoundedCornerShape(16.dp))
                    .clickable(onClick = importCsv)
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        stringResource(R.string.st_import_other),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = VaultTheme.colors.ink(0.8f),
                    )
                    Text(
                        stringResource(R.string.st_import_other_detail),
                        fontSize = 11.sp,
                        color = VaultTheme.colors.ink(0.45f),
                    )
                }
            }

            // Clears the bottom navigation bar.
            Spacer(Modifier.height(120.dp))
        }
    }

    if (showChangePass) {
        ChangePassphraseDialog(app = app, onDismiss = { showChangePass = false })
    }

    if (showNewRecovery) {
        NewRecoveryKeyDialog(app = app, onDismiss = { showNewRecovery = false })
    }

    if (enableQuickUnlockAsk) {
        EnableQuickUnlockDialog(
            app = app,
            onDone = { enabled ->
                quickUnlockOn = enabled
                enableQuickUnlockAsk = false
            }
        )
    }

    if (showSyncSheet) {
        val activeFolderFallback = stringResource(R.string.st_active_folder)
        val folderName = remember(syncUri, activeFolderFallback) {
            runCatching {
                DocumentFile.fromTreeUri(context, Uri.parse(syncUri))?.name
            }.getOrNull() ?: activeFolderFallback
        }
        SyncFolderDialog(
            app = app,
            folderName = folderName,
            onBackupNow = {
                scope.launch {
                    syncBusy = true
                    val success = app.repository.manualBackup(context)
                    syncBusy = false
                    if (success) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.st_toast_backed_up, folderName),
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.st_toast_backup_failed),
                            Toast.LENGTH_LONG,
                        ).show()
                    }
                }
            },
            onChangeFolder = {
                showSyncSheet = false
                syncFolderLauncher.launch(null)
            },
            onDisconnect = {
                app.repository.updateSyncFolder(context, null)
                syncUri = ""
                showSyncSheet = false
                Toast.makeText(
                    context,
                    context.getString(R.string.st_toast_disconnected),
                    Toast.LENGTH_SHORT,
                ).show()
            },
            onDismiss = { showSyncSheet = false },
            busy = syncBusy
        )
    }
}

@Composable
private fun SettingsGroupTitle(text: String) {
    SectionLabel(
        text = text,
        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp),
    )
}

/** The white grouped card the mockup draws every settings block inside. */
@Composable
private fun SettingsCard(content: @Composable ColumnScope.() -> Unit) {
    WhiteCard(corner = 20.dp, content = content)
}

@Composable
private fun SettingsRow(
    title: String,
    value: String? = null,
    valueColor: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    val c = VaultTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = c.ink,
            modifier = Modifier.weight(1f)
        )
        if (value != null) {
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = if (valueColor == Color.Unspecified) FontWeight.Normal else FontWeight.SemiBold,
                color = if (valueColor == Color.Unspecified) c.mute else valueColor
            )
        }
    }
}

@Composable
private fun SettingsRowToggle(
    title: String,
    checked: Boolean,
    enabled: Boolean = true,
    onToggle: (Boolean) -> Unit
) {
    val c = VaultTheme.colors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = { onToggle(!checked) })
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) c.ink else c.ink(0.38f),
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = checked,
            onCheckedChange = onToggle,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = c.paper,
                checkedTrackColor = c.accent,
                checkedBorderColor = c.accent,
                uncheckedThumbColor = c.paper,
                uncheckedTrackColor = c.ink(0.18f),
                uncheckedBorderColor = c.ink(0.18f),
            ),
        )
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(
        color = VaultTheme.colors.line,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}
// #endregion

// #region Change Passphrase Dialog
@Composable
private fun ChangePassphraseDialog(app: ZerokoshApp, onDismiss: () -> Unit) {
    // BV-14: resolved in composition so a locale change re-reads it.
    val passphraseChangedMessage = stringResource(R.string.scr_settings_passphrase_changed)
    var current by remember { mutableStateOf("") }
    var newPass by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var wrongCurrent by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val valid = current.isNotEmpty() && newPass.length >= 10 && newPass == confirm

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.scr_settings_change_passphrase)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var showCurrent by remember { mutableStateOf(false) }
                var showNew by remember { mutableStateOf(false) }
                var showConfirm by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = current, onValueChange = { current = it; wrongCurrent = false },
                    label = { Text(stringResource(R.string.scr_settings_current_passphrase)) },
                    visualTransformation = if (showCurrent) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(visible = showCurrent, onToggle = { showCurrent = !showCurrent }) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = wrongCurrent,
                    supportingText = { if (wrongCurrent) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) }
                )
                OutlinedTextField(
                    value = newPass, onValueChange = { newPass = it },
                    label = { Text(stringResource(R.string.scr_settings_new_passphrase)) },
                    visualTransformation = if (showNew) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(visible = showNew, onToggle = { showNew = !showNew }) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                OutlinedTextField(
                    value = confirm, onValueChange = { confirm = it },
                    label = { Text(stringResource(R.string.st_confirm_new_passphrase)) },
                    visualTransformation = if (showConfirm) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(visible = showConfirm, onToggle = { showConfirm = !showConfirm }) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = confirm.isNotEmpty() && confirm != newPass
                )
            }
        },
        confirmButton = {
            Button(
                enabled = valid && !busy,
                onClick = {
                    busy = true
                    scope.launch {
                        val ok = app.repository.changePassphrase(current.toByteArray(), newPass.toByteArray())
                        busy = false
                        if (ok) {
                            Toast.makeText(context, passphraseChangedMessage, Toast.LENGTH_SHORT).show()
                            onDismiss()
                        } else {
                            wrongCurrent = true
                        }
                    }
                }
            ) {
                if (busy) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                else Text(stringResource(R.string.msg_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) }
        }
    )
}
// #endregion

// #region New Recovery Key Dialog
@Composable
private fun NewRecoveryKeyDialog(app: ZerokoshApp, onDismiss: () -> Unit) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var newKey by remember { mutableStateOf<String?>(null) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.scr_settings_new_recovery)) },
        text = {
            if (newKey == null) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.st_new_recovery_body),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    var showPass by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = passphrase, onValueChange = { passphrase = it; wrong = false },
                        label = { Text(stringResource(R.string.scr_lock_hint)) },
                        visualTransformation = if (showPass) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = { RevealToggle(visible = showPass, onToggle = { showPass = !showPass }) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true, isError = wrong,
                        supportingText = { if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) }
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        stringResource(R.string.st_new_recovery_result),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = newKey!!,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                    Text(
                        stringResource(R.string.st_new_recovery_note),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }
        },
        confirmButton = {
            if (newKey == null) {
                Button(
                    enabled = passphrase.isNotEmpty() && !busy,
                    onClick = {
                        busy = true
                        scope.launch {
                            val key = app.repository.rotateRecoveryKey(passphrase.toByteArray())
                            busy = false
                            if (key != null) {
                                newKey = key
                            } else {
                                wrong = true
                            }
                        }
                    }
                ) {
                    if (busy) CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                    else Text(stringResource(R.string.st_generate))
                }
            } else {
                Button(onClick = onDismiss) { Text(stringResource(R.string.st_done)) }
            }
        },
        dismissButton = {
            if (newKey == null) {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) }
            }
        }
    )
}
// #endregion

// #region Sync Folder Dialog
@Composable
private fun SyncFolderDialog(
    app: ZerokoshApp,
    folderName: String,
    onBackupNow: () -> Unit,
    onChangeFolder: () -> Unit,
    onDisconnect: () -> Unit,
    onDismiss: () -> Unit,
    busy: Boolean,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Outlined.FolderZip, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(stringResource(R.string.st_backup_sheet_title))
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = stringResource(R.string.st_connected_folder, folderName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = stringResource(R.string.st_backup_sheet_body),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (busy) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                        Text(
                            stringResource(R.string.st_backing_up),
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onBackupNow,
                enabled = !busy,
            ) {
                Icon(Icons.Outlined.Sync, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(6.dp))
                Text(stringResource(R.string.st_backup_now))
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                OutlinedButton(onClick = onChangeFolder, enabled = !busy) {
                    Text(stringResource(R.string.st_change_folder))
                }
                TextButton(onClick = onDisconnect, enabled = !busy) {
                    Text(stringResource(R.string.st_disconnect), color = MaterialTheme.colorScheme.error)
                }
            }
        }
    )
}
// #endregion

// #region Quick-unlock enable (needs passphrase → MasterKey → Keystore)
@Composable
private fun EnableQuickUnlockDialog(app: ZerokoshApp, onDone: (Boolean) -> Unit) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    // BV-14: LocalContext can be a wrapper; LocalActivity resolves the host.
    val activity = LocalActivity.current as FragmentActivity

    AlertDialog(
        onDismissRequest = { onDone(false) },
        title = { Text(stringResource(R.string.scr_settings_quick_unlock)) },
        text = {
            var showPass by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = passphrase, onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = if (showPass) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = { RevealToggle(visible = showPass, onToggle = { showPass = !showPass }) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true, isError = wrong,
                supportingText = { if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) },
            )
        },
        confirmButton = {
            TextButton(
                enabled = !busy && passphrase.isNotEmpty(),
                onClick = {
                    busy = true
                    scope.launch {
                        val bytes = passphrase.toByteArray()
                        val verified = app.repository.verifyPassphrase(bytes)
                        val enabled = verified && QuickUnlockManager.enable(activity, app, bytes)
                        bytes.fill(0)
                        busy = false
                        if (!verified) wrong = true else onDone(enabled)
                    }
                },
            ) { Text(stringResource(R.string.msg_ok)) }
        },
        dismissButton = { TextButton(onClick = { onDone(false) }) { Text(stringResource(R.string.msg_cancel)) } },
    )
}
// #endregion

// #region About
// #endregion
