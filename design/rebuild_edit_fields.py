# -*- coding: utf-8 -*-
"""Rebuild the record-edit field layer against the Lovable mockup.

The mockup (ScreenEdit in the Lovable source) renders each field as its own
white card with a 10px uppercase label and the value beneath — no outline, no
floating label, no grouping. This replaces the stock OutlinedTextField layer
with that, and flattens the two GroupCards into the mockup's 8dp-spaced list.
"""
import io

P = 'app/src/main/java/org/zerokosh/app/ui/record/RecordEditScreen.kt'
s = io.open(P, encoding='utf-8').read()


def sub(old, new, n=1):
    global s
    assert s.count(old) >= n, 'ANCHOR MISSING:\n%r' % (old[:180],)
    s = s.replace(old, new, n)


# ---- 1. the shared input primitive ---------------------------------------
sub('''/** §2.2 PICKER: bundled list + "Other" free text (DECISIONS.md D-002). */''',
    '''/**
 * A bare text input for use inside [VaultFieldCard]. No decoration of its own —
 * the card supplies the label, padding and border, exactly as the mockup does.
 * Secrets and anything a human has to transcribe render monospace.
 */
@Composable
private fun FieldInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    mono: Boolean = false,
    dimmed: Boolean = false,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
) {
    val c = VaultTheme.colors
    val style = (if (mono) SecretTextStyle else MaterialTheme.typography.bodyLarge)
        .copy(fontSize = 14.sp, color = if (dimmed) c.ink(0.5f) else c.ink)
    Box(modifier) {
        if (value.isEmpty() && placeholder != null) {
            Text(placeholder, style = style.copy(color = c.ink(0.3f)))
        }
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = style,
            singleLine = singleLine,
            cursorBrush = SolidColor(c.primary),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

/** §2.2 PICKER: bundled list + "Other" free text (DECISIONS.md D-002). */''')

# ---- 2. flatten the grouping to the mockup's list -------------------------
sub('''            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
            WhiteCard(corner = 20.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it; titleMissing = false },
                        label = { Text(stringResource(R.string.scr_edit_title_hint)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = titleMissing,
                        supportingText = {
                            if (titleMissing) Text(stringResource(R.string.scr_edit_required_title), color = MaterialTheme.colorScheme.error)
                        },
                    )
                    OutlinedTextField(
                        value = institution,
                        onValueChange = { institution = it },
                        label = { Text(stringResource(R.string.scr_edit_institution_hint)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                    )
                }
            }
            if (template.fields.isNotEmpty()) {
                WhiteCard(corner = 20.dp) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {''',
    '''            // The mockup is one flat list of field cards at 8dp, inside 24dp
            // side padding — not grouped cards. px-6 space-y-2 in the source.
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                VaultFieldCard(
                    label = stringResource(R.string.scr_edit_title_hint),
                    supporting = if (titleMissing) {
                        stringResource(R.string.scr_edit_required_title)
                    } else {
                        null
                    },
                    supportingColor = MaterialTheme.colorScheme.error,
                ) {
                    FieldInput(
                        value = title,
                        onValueChange = { title = it; titleMissing = false },
                    )
                }
                VaultFieldCard(label = stringResource(R.string.scr_edit_institution_hint)) {
                    FieldInput(value = institution, onValueChange = { institution = it })
                }
            if (template.fields.isNotEmpty()) {
                run {
                    run {''')

io.open(P, 'w', encoding='utf-8', newline='\n').write(s)
print('grouping flattened; title and institution on the mockup field card')
