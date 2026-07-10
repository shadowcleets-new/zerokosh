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
 * 4. ABOUT
 */
package org.bharatvault.app.ui.settings

// #region Imports
import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.MainActivity
import org.bharatvault.app.R
import org.bharatvault.app.quickunlock.QuickUnlockManager
import org.bharatvault.app.ui.onboarding.passphraseScore
import org.bharatvault.app.ui.record.DropdownSelector
// #endregion

// #region Screen list
@Composable
fun SettingsScreen(app: BharatVaultApp) {
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val scope = rememberCoroutineScope()
    var showChangePass by remember { mutableStateOf(false) }
    var showNewRecovery by remember { mutableStateOf(false) }
    var quickUnlockOn by remember { mutableStateOf(app.prefs.quickUnlockEnabled) }
    var allowShots by remember { mutableStateOf(app.prefs.allowScreenshots) }
    var enableQuickUnlockAsk by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                stringResource(R.string.scr_settings_title),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.weight(1f),
            )
            IconButton(onClick = { app.repository.lock() }) {
                Icon(Icons.Outlined.Lock, contentDescription = stringResource(R.string.scr_lock_title))
            }
        }
        Spacer(Modifier.height(16.dp))

        // §5.2 auto-lock timing: 0/1/5/15
        Text(stringResource(R.string.scr_settings_autolock), style = MaterialTheme.typography.titleMedium)
        val autoLockOptions = listOf(
            0 to stringResource(R.string.scr_settings_autolock_immediately),
            1 to stringResource(R.string.scr_settings_autolock_1min),
            5 to stringResource(R.string.scr_settings_autolock_5min),
            15 to stringResource(R.string.scr_settings_autolock_15min),
        )
        DropdownSelector(
            options = autoLockOptions.map { it.second },
            selected = autoLockOptions.firstOrNull { it.first == app.prefs.autoLockMinutes }?.second
                ?: autoLockOptions[1].second,
            onSelect = { picked -> app.prefs.autoLockMinutes = autoLockOptions.first { it.second == picked }.first },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        )

        SettingRow(
            title = stringResource(R.string.scr_settings_quick_unlock),
            checked = quickUnlockOn,
            enabled = QuickUnlockManager.hardwareBackedBiometricsAvailable(context),
        ) { turnOn ->
            if (turnOn) {
                enableQuickUnlockAsk = true
            } else {
                QuickUnlockManager.disable(context, app)
                quickUnlockOn = false
            }
        }

        SettingRow(
            title = stringResource(R.string.scr_settings_allow_screenshots),
            subtitle = stringResource(R.string.scr_settings_allow_screenshots_warning),
            checked = allowShots,
        ) { on ->
            app.prefs.allowScreenshots = on
            allowShots = on
            (context as? MainActivity)?.applyScreenPrivacy()
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        TextButton(onClick = { showChangePass = true }) { Text(stringResource(R.string.scr_settings_change_passphrase)) }
        TextButton(onClick = { showNewRecovery = true }) { Text(stringResource(R.string.scr_settings_new_recovery)) }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // §5.6 sync folder + §5.11 import/export land with M3/M5
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.scr_settings_sync_folder), modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.scr_settings_sync_not_set),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
            )
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // Language
        Text(stringResource(R.string.scr_settings_language), style = MaterialTheme.typography.titleMedium)
        DropdownSelector(
            options = listOf("English", "हिन्दी"),
            selected = if (app.prefs.languageTag == "hi") "हिन्दी" else "English",
            onSelect = { picked ->
                app.prefs.languageTag = if (picked == "हिन्दी") "hi" else "en"
                (context as? Activity)?.recreate()
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
        AboutSection()
    }

    if (showChangePass) {
        ChangePassphraseDialog(app) { showChangePass = false }
    }
    if (showNewRecovery) {
        NewRecoveryKeyDialog(app) { showNewRecovery = false }
    }
    if (enableQuickUnlockAsk) {
        EnableQuickUnlockDialog(
            app = app,
            onDone = { enabled ->
                quickUnlockOn = enabled
                enableQuickUnlockAsk = false
            },
        )
    }
}

@Composable
private fun SettingRow(
    title: String,
    checked: Boolean,
    subtitle: String? = null,
    enabled: Boolean = true,
    onToggle: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            if (subtitle != null) {
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Switch(checked = checked, onCheckedChange = onToggle, enabled = enabled)
    }
}
// #endregion

// #region Change passphrase (§3.3: re-wraps wrap_mk only)
@Composable
private fun ChangePassphraseDialog(app: BharatVaultApp, onClose: () -> Unit) {
    var current by remember { mutableStateOf("") }
    var new by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val doneMsg = stringResource(R.string.scr_settings_passphrase_changed)

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(stringResource(R.string.scr_settings_change_passphrase)) },
        text = {
            Column {
                OutlinedTextField(
                    value = current, onValueChange = { current = it; wrong = false },
                    label = { Text(stringResource(R.string.scr_settings_current_passphrase)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = wrong,
                    supportingText = { if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) },
                )
                OutlinedTextField(
                    value = new, onValueChange = { new = it },
                    label = { Text(stringResource(R.string.scr_settings_new_passphrase)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    supportingText = {
                        if (new.isNotEmpty() && passphraseScore(new) == 0) {
                            Text(stringResource(R.string.scr_create_too_short), color = MaterialTheme.colorScheme.error)
                        }
                    },
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = !busy && current.isNotEmpty() && new.length >= 10,
                onClick = {
                    busy = true
                    scope.launch {
                        val ok = app.repository.changePassphrase(current.toByteArray(), new.toByteArray())
                        busy = false
                        if (ok) {
                            Toast.makeText(context, doneMsg, Toast.LENGTH_SHORT).show()
                            onClose()
                        } else {
                            wrong = true
                        }
                    }
                },
            ) { Text(stringResource(R.string.msg_ok)) }
        },
        dismissButton = { TextButton(onClick = onClose) { Text(stringResource(R.string.msg_cancel)) } },
    )
}
// #endregion

// #region New recovery key (S4 from Settings: re-wraps wrap_rk)
@Composable
private fun NewRecoveryKeyDialog(app: BharatVaultApp, onClose: () -> Unit) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var newKey by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = { if (newKey == null) onClose() },
        title = { Text(stringResource(if (newKey == null) R.string.scr_settings_new_recovery else R.string.scr_recovery_title)) },
        text = {
            if (newKey == null) {
                OutlinedTextField(
                    value = passphrase, onValueChange = { passphrase = it; wrong = false },
                    label = { Text(stringResource(R.string.scr_lock_hint)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = wrong,
                    supportingText = { if (wrong) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) },
                )
            } else {
                Column {
                    Card {
                        Text(
                            newKey!!,
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge.copy(fontFamily = FontFamily.Monospace),
                            textAlign = TextAlign.Center,
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.scr_recovery_never_again),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        },
        confirmButton = {
            if (newKey == null) {
                TextButton(
                    enabled = !busy && passphrase.isNotEmpty(),
                    onClick = {
                        busy = true
                        scope.launch {
                            val result = app.repository.rotateRecoveryKey(passphrase.toByteArray())
                            busy = false
                            if (result != null) newKey = result else wrong = true
                        }
                    },
                ) { Text(stringResource(R.string.msg_ok)) }
            } else {
                TextButton(onClick = onClose) { Text(stringResource(R.string.scr_recovery_done)) }
            }
        },
        dismissButton = {
            if (newKey == null) TextButton(onClick = onClose) { Text(stringResource(R.string.msg_cancel)) }
        },
    )
}
// #endregion

// #region Quick-unlock enable (needs passphrase → MasterKey → Keystore)
@Composable
private fun EnableQuickUnlockDialog(app: BharatVaultApp, onDone: (Boolean) -> Unit) {
    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as FragmentActivity

    AlertDialog(
        onDismissRequest = { onDone(false) },
        title = { Text(stringResource(R.string.scr_settings_quick_unlock)) },
        text = {
            OutlinedTextField(
                value = passphrase, onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = PasswordVisualTransformation(),
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
@Composable
private fun AboutSection() {
    Text(stringResource(R.string.scr_settings_about), style = MaterialTheme.typography.titleMedium)
    Spacer(Modifier.height(8.dp))
    Text(stringResource(R.string.scr_about_body), style = MaterialTheme.typography.bodyMedium)
    Spacer(Modifier.height(4.dp))
    Text(
        stringResource(R.string.scr_about_version, "0.1.0"),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
        stringResource(R.string.scr_about_license),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}
// #endregion
