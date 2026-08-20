# -*- coding: utf-8 -*-
"""Give every masked passphrase/PIN field a reveal toggle."""
import io

UI = 'app/src/main/java/org/zerokosh/app/ui/common/VaultUi.kt'
LOCK = 'app/src/main/java/org/zerokosh/app/ui/lock/LockScreen.kt'
AUTH = 'app/src/main/java/org/zerokosh/app/ui/common/RevealAuth.kt'
EDIT = 'app/src/main/java/org/zerokosh/app/ui/record/RecordEditScreen.kt'
SET = 'app/src/main/java/org/zerokosh/app/ui/settings/SettingsScreen.kt'


def rd(p):
    return io.open(p, encoding='utf-8').read()


def wr(p, s):
    io.open(p, 'w', encoding='utf-8', newline='\n').write(s)


def sub(s, old, new, path='', count=1):
    assert s.count(old) >= count, 'ANCHOR MISSING %s:\n%r' % (path, old[:170])
    return s.replace(old, new, count)


# ---------- 1. the shared toggle -------------------------------------------
s = rd(UI)
s = sub(s, '''/** `text-vault-accent bg-vault-accent-soft rounded-lg` — one-tap copy affordance. */''',
        '''/**
 * Reveal toggle for a masked field. Every place the user types a secret needs
 * one — typing a long passphrase blind is the single biggest accessibility
 * barrier in the app, and it is worst exactly where it matters most, on the
 * lock screen. 48dp because this sits next to the field's own tap target.
 */
@Composable
fun RevealToggle(
    visible: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = VaultTheme.colors.ink(0.55f),
) {
    IconButton(onClick = onToggle, modifier = modifier.size(48.dp)) {
        Icon(
            if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
            contentDescription = if (visible) "Hide passphrase" else "Show passphrase",
            tint = tint,
            modifier = Modifier.size(20.dp),
        )
    }
}

/** `text-vault-accent bg-vault-accent-soft rounded-lg` — one-tap copy affordance. */''', UI)

for imp in [
    'import androidx.compose.material.icons.outlined.Visibility\n',
    'import androidx.compose.material.icons.outlined.VisibilityOff\n',
    'import androidx.compose.material3.IconButton\n',
]:
    if imp not in s:
        s = s.replace('import androidx.compose.material3.Icon\n', imp + 'import androidx.compose.material3.Icon\n', 1)
wr(UI, s)
print('VaultUi.kt: RevealToggle added')

# ---------- 2. lock screen (the reported one) ------------------------------
s = rd(LOCK)
s = sub(s, '''            if (showCredential || recoveryMode) {
                Spacer(Modifier.height(32.dp))''',
        '''            if (showCredential || recoveryMode) {
                Spacer(Modifier.height(32.dp))
                var revealed by remember { mutableStateOf(false) }''', LOCK)
s = sub(s, '''                    visualTransformation = if (recoveryMode) androidx.compose.ui.text.input.VisualTransformation.None
                    else PasswordVisualTransformation(),''',
        '''                    visualTransformation =
                        if (recoveryMode || revealed) androidx.compose.ui.text.input.VisualTransformation.None
                        else PasswordVisualTransformation(),
                    // The recovery key is already shown in clear, so it needs no toggle.
                    trailingIcon = if (recoveryMode) null else {
                        {
                            RevealToggle(
                                visible = revealed,
                                onToggle = { revealed = !revealed },
                                tint = LockOnSurface.copy(alpha = 0.7f),
                            )
                        }
                    },''', LOCK)
if 'import org.zerokosh.app.ui.common.RevealToggle\n' not in s:
    s = s.replace('import androidx.compose.ui.text.input.PasswordVisualTransformation\n',
                  'import androidx.compose.ui.text.input.PasswordVisualTransformation\n'
                  'import org.zerokosh.app.ui.common.RevealToggle\n', 1)
wr(LOCK, s)
print('LockScreen.kt: reveal toggle added')

# ---------- 3. reveal-auth dialog ------------------------------------------
s = rd(AUTH)
s = sub(s, '''    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }''',
        '''    var passphrase by remember { mutableStateOf("") }
    var wrong by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    var revealed by remember { mutableStateOf(false) }''', AUTH)
s = sub(s, '''                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = wrong,''',
        '''                visualTransformation = if (revealed) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = { RevealToggle(revealed) { revealed = !revealed } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = wrong,''', AUTH)
s = sub(s, 'import androidx.compose.ui.text.input.PasswordVisualTransformation\n',
        'import androidx.compose.ui.text.input.PasswordVisualTransformation\n'
        'import androidx.compose.ui.text.input.VisualTransformation\n', AUTH)
wr(AUTH, s)
print('RevealAuth.kt: reveal toggle added')

# ---------- 4. record edit, PIN field --------------------------------------
s = rd(EDIT)
old_pin = (
    '        FieldType.PIN -> OutlinedTextField(\n'
    '            value = value,\n'
    '            onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(8)) }, '
    '// 3\u20138 digits (\u00a72.2)\n'
    '            label = { Text(label) },\n'
    '            visualTransformation = PasswordVisualTransformation(),\n'
    '            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),\n'
    '            modifier = modifier, singleLine = true,\n'
    '            isError = invalid,\n'
    '            supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), '
    'color = MaterialTheme.colorScheme.error) },\n'
    '        )\n'
)
new_pin = (
    '        FieldType.PIN -> {\n'
    '            var pinVisible by remember { mutableStateOf(false) }\n'
    '            OutlinedTextField(\n'
    '                value = value,\n'
    '                onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(8)) }, '
    '// 3\u20138 digits (\u00a72.2)\n'
    '                label = { Text(label) },\n'
    '                visualTransformation = if (pinVisible) VisualTransformation.None\n'
    '                else PasswordVisualTransformation(),\n'
    '                trailingIcon = { RevealToggle(pinVisible) { pinVisible = !pinVisible } },\n'
    '                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),\n'
    '                modifier = modifier, singleLine = true,\n'
    '                isError = invalid,\n'
    '                supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), '
    'color = MaterialTheme.colorScheme.error) },\n'
    '            )\n'
    '        }\n'
)
s = sub(s, old_pin, new_pin, EDIT)
if 'import org.zerokosh.app.ui.common.RevealToggle\n' not in s:
    s = s.replace('import androidx.compose.ui.text.input.PasswordVisualTransformation\n',
                  'import androidx.compose.ui.text.input.PasswordVisualTransformation\n'
                  'import org.zerokosh.app.ui.common.RevealToggle\n', 1)
wr(EDIT, s)
print('RecordEditScreen.kt: PIN reveal toggle added')

# ---------- 5. settings: five masked fields --------------------------------
s = rd(SET)

# change-passphrase dialog: current / new / confirm
s = sub(s, '''            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = current, onValueChange = { current = it; wrongCurrent = false },
                    label = { Text(stringResource(R.string.scr_settings_current_passphrase)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = wrongCurrent,
                    supportingText = { if (wrongCurrent) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) }
                )
                OutlinedTextField(
                    value = newPass, onValueChange = { newPass = it },
                    label = { Text(stringResource(R.string.scr_settings_new_passphrase)) },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                OutlinedTextField(
                    value = confirm, onValueChange = { confirm = it },
                    label = { Text("Confirm new passphrase") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = confirm.isNotEmpty() && confirm != newPass
                )
            }''',
        '''            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var showCurrent by remember { mutableStateOf(false) }
                var showNew by remember { mutableStateOf(false) }
                var showConfirm by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = current, onValueChange = { current = it; wrongCurrent = false },
                    label = { Text(stringResource(R.string.scr_settings_current_passphrase)) },
                    visualTransformation = if (showCurrent) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(showCurrent) { showCurrent = !showCurrent } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = wrongCurrent,
                    supportingText = { if (wrongCurrent) Text(stringResource(R.string.scr_lock_wrong), color = MaterialTheme.colorScheme.error) }
                )
                OutlinedTextField(
                    value = newPass, onValueChange = { newPass = it },
                    label = { Text(stringResource(R.string.scr_settings_new_passphrase)) },
                    visualTransformation = if (showNew) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(showNew) { showNew = !showNew } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )
                OutlinedTextField(
                    value = confirm, onValueChange = { confirm = it },
                    label = { Text("Confirm new passphrase") },
                    visualTransformation = if (showConfirm) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = { RevealToggle(showConfirm) { showConfirm = !showConfirm } },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true, isError = confirm.isNotEmpty() && confirm != newPass
                )
            }''', SET)

# new-recovery-key dialog
s = sub(s, '''                    OutlinedTextField(
                        value = passphrase, onValueChange = { passphrase = it; wrong = false },
                        label = { Text(stringResource(R.string.scr_lock_hint)) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true, isError = wrong,''',
        '''                    var showPass by remember { mutableStateOf(false) }
                    OutlinedTextField(
                        value = passphrase, onValueChange = { passphrase = it; wrong = false },
                        label = { Text(stringResource(R.string.scr_lock_hint)) },
                        visualTransformation = if (showPass) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = { RevealToggle(showPass) { showPass = !showPass } },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true, isError = wrong,''', SET)

# quick-unlock confirm dialog
s = sub(s, '''            OutlinedTextField(
                value = passphrase, onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true, isError = wrong,''',
        '''            var showPass by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = passphrase, onValueChange = { passphrase = it; wrong = false },
                label = { Text(stringResource(R.string.scr_lock_hint)) },
                visualTransformation = if (showPass) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = { RevealToggle(showPass) { showPass = !showPass } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true, isError = wrong,''', SET)

for imp in ['import androidx.compose.ui.text.input.VisualTransformation\n',
            'import org.zerokosh.app.ui.common.RevealToggle\n']:
    if imp not in s:
        s = s.replace('import androidx.compose.ui.text.input.PasswordVisualTransformation\n',
                      'import androidx.compose.ui.text.input.PasswordVisualTransformation\n' + imp, 1)
wr(SET, s)
print('SettingsScreen.kt: five reveal toggles added')
