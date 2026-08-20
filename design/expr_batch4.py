# -*- coding: utf-8 -*-
"""Expressive batch 4: field shape, unified sheet state, contained loading."""
import io

EDIT = 'app/src/main/java/org/zerokosh/app/ui/record/RecordEditScreen.kt'
GEN = 'app/src/main/java/org/zerokosh/app/ui/generator/GeneratorSheet.kt'
LOCK = 'app/src/main/java/org/zerokosh/app/ui/lock/LockScreen.kt'


def rd(p):
    return io.open(p, encoding='utf-8').read()


def wr(p, s):
    io.open(p, 'w', encoding='utf-8', newline='\n').write(s)


def sub(s, old, new, path='', n=1):
    assert s.count(old) >= n, 'ANCHOR MISSING %s:\n%r' % (path, old[:170])
    return s.replace(old, new, n)


# ---- record edit form: expressive field shape + explicit label position ----
s = rd(EDIT)
s = sub(s, '''private fun PlainField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalid: Boolean,
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        modifier = modifier, singleLine = true,''',
        '''private fun PlainField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalid: Boolean,
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        // Cutout is already the expressive overload's default; stating it keeps
        // the intent visible if the default ever moves.
        labelPosition = TextFieldLabelPosition.Cutout(),
        shape = OutlinedTextFieldDefaults.roundedShape,
        modifier = modifier, singleLine = true,''', EDIT)
for imp in ['import androidx.compose.material3.OutlinedTextFieldDefaults\n',
            'import androidx.compose.material3.TextFieldLabelPosition\n']:
    if imp not in s:
        s = s.replace('import androidx.compose.material3.OutlinedTextField\n',
                      'import androidx.compose.material3.OutlinedTextField\n' + imp, 1)
wr(EDIT, s)
print('RecordEditScreen.kt: expressive field shape + explicit Cutout label')

# ---- generator sheet: unified sheet state ---------------------------------
s = rd(GEN)
s = sub(s, '    ModalBottomSheet(onDismissRequest = onDismiss) {',
        '''    // rememberBottomSheetState is the unified replacement for the deprecated
    // rememberModalBottomSheetState / rememberStandardBottomSheetState pair.
    val sheetState = rememberBottomSheetState()
    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {''', GEN)
if 'import androidx.compose.material3.rememberBottomSheetState\n' not in s:
    s = s.replace('import androidx.compose.material3.ModalBottomSheet\n',
                  'import androidx.compose.material3.ModalBottomSheet\n'
                  'import androidx.compose.material3.rememberBottomSheetState\n', 1)
wr(GEN, s)
print('GeneratorSheet.kt: rememberBottomSheetState')

# ---- lock screen: contained loading indicator while deriving --------------
s = rd(LOCK)
s = sub(s, '''                    enabled = !busy && cooldown == 0,''',
        '''                    enabled = !busy && cooldown == 0,
                    trailingIcon = if (busy) {
                        {
                            // Argon2id at 64 MB takes visible time on a cold
                            // start; the contained indicator gives that wait a
                            // home inside the field rather than a dead control.
                            ContainedLoadingIndicator(
                                modifier = Modifier.size(40.dp),
                                indicatorColor = LockOnSurface,
                            )
                        }
                    } else if (recoveryMode) null else {
                        {
                            RevealToggle(
                                visible = revealed,
                                onToggle = { revealed = !revealed },
                                tint = LockOnSurface.copy(alpha = 0.7f),
                            )
                        }
                    },''', LOCK)
# the old trailingIcon block is now redundant
s = sub(s, '''                    // The recovery key is already shown in clear, so it needs no toggle.
                    trailingIcon = if (recoveryMode) null else {
                        {
                            RevealToggle(
                                visible = revealed,
                                onToggle = { revealed = !revealed },
                                tint = LockOnSurface.copy(alpha = 0.7f),
                            )
                        }
                    },
''', '', LOCK)
if 'import androidx.compose.material3.ContainedLoadingIndicator\n' not in s:
    s = s.replace('import androidx.compose.material3.PasswordVisualTransformation\n',
                  'import androidx.compose.material3.PasswordVisualTransformation\n', 1)
    s = s.replace('import org.zerokosh.app.ui.common.RevealToggle\n',
                  'import androidx.compose.material3.ContainedLoadingIndicator\n'
                  'import org.zerokosh.app.ui.common.RevealToggle\n', 1)
wr(LOCK, s)
print('LockScreen.kt: ContainedLoadingIndicator while the key derives')
