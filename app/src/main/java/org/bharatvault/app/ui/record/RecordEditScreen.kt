/**
 * @file RecordEditScreen.kt
 * @description S10 (§5.1): template-driven edit form; typed inputs per §2.2;
 *              inline validation; save increments rev; SECRET fields carry a
 *              generator button (§5.8).
 *
 * [TABLE OF CONTENTS]
 * 1. SCREEN STATE & SAVE
 * 2. FIELD EDITOR DISPATCH (§2.2)
 * 3. TYPE-SPECIFIC EDITORS (date, month-year, picker, link, cardnum…)
 * 4. CARD NUMBER TRANSFORMATION (groups of 4 + Luhn/network badge)
 */
package org.bharatvault.app.ui.record

// #region Imports
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.ui.common.fieldLabel
import org.bharatvault.app.ui.generator.GeneratorSheet
import org.bharatvault.app.ui.theme.SecretTextStyle
import org.bharatvault.core.model.FieldType
import org.bharatvault.core.model.FieldValidation
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.TemplateField
import org.bharatvault.core.util.CardUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
// #endregion

// #region Screen state & save
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordEditScreen(
    app: BharatVaultApp,
    templateIdArg: String,
    editUuid: String?,
    presetName: String?,
    onDone: () -> Unit,
) {
    val body = app.repository.body.collectAsState().value
    val existing = editUuid?.let { id -> body?.records?.firstOrNull { it.uuid == id } }
    val templateId = existing?.template_id ?: templateIdArg
    val template = app.catalog.templates.byId(templateId) ?: run { onDone(); return }

    var title by rememberSaveable { mutableStateOf(existing?.title ?: presetName.orEmpty()) }
    var institution by rememberSaveable { mutableStateOf(existing?.institution.orEmpty()) }
    val values = remember {
        mutableStateMapOf<String, String>().apply {
            existing?.fields?.forEach { (k, v) -> put(k, v) }
            if (presetName != null && templateId == "app_profile") put("app_name", presetName)
        }
    }
    var titleMissing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun invalidFields(): List<String> = template.fields.filter { f ->
        val v = values[f.k].orEmpty()
        !FieldValidation.isValid(f, v) && f.type != FieldType.CARDNUM // Luhn warns, never blocks (§5.10)
    }.map { it.k }

    var showInvalid by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        existing?.title ?: "${stringResource(R.string.scr_edit_title_new)} ${
                            org.bharatvault.app.ui.gallery.templateName(templateId)
                        }",
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.scr_edit_cancel))
                    }
                },
                actions = {
                    TextButton(onClick = save@{
                        if (title.isBlank()) {
                            titleMissing = true
                            return@save
                        }
                        if (invalidFields().isNotEmpty()) {
                            showInvalid = true
                            return@save
                        }
                        val record = Record(
                            uuid = existing?.uuid ?: "",
                            template_id = templateId,
                            title = title.trim(),
                            institution = institution.ifBlank { deriveInstitution(templateId, values) }.trim(),
                            fields = values.filterValues { it.isNotBlank() }, // §2.1: empty fields omitted
                            custom_fields = existing?.custom_fields ?: emptyList(),
                            tags = existing?.tags ?: emptyList(),
                            favorite = existing?.favorite ?: false,
                            created_at = existing?.created_at ?: 0,
                            modified_at = 0,
                            rev = existing?.rev ?: 1,
                            device_id = "",
                            reminders = existing?.reminders ?: emptyList(),
                        )
                        scope.launch {
                            app.repository.upsertRecord(record)
                            onDone()
                        }
                    }) { Text(stringResource(R.string.scr_edit_save)) }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; titleMissing = false },
                label = { Text(stringResource(R.string.scr_edit_title_hint)) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
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
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
                singleLine = true,
            )
            template.fields.forEach { field ->
                FieldEditor(
                    app = app,
                    templateId = templateId,
                    field = field,
                    value = values[field.k].orEmpty(),
                    onValueChange = { values[field.k] = it },
                    showInvalid = showInvalid,
                )
            }
            Spacer(Modifier.height(48.dp))
        }
    }
}

/** S6 grouping: pull institution from the natural key field when the user left it blank. */
private fun deriveInstitution(templateId: String, values: Map<String, String>): String = when (templateId) {
    "bank_account" -> values["bank_name"].orEmpty()
    "card" -> values["card_variant"].orEmpty()
    "demat" -> values["broker"].orEmpty()
    "insurance" -> values["insurer"].orEmpty()
    "utility" -> values["provider"].orEmpty()
    "telecom" -> values["operator"].orEmpty()
    "app_profile" -> values["app_name"].orEmpty()
    "login" -> values["website"]?.removePrefix("https://")?.removePrefix("http://")
        ?.removePrefix("www.")?.substringBefore('/').orEmpty()
    else -> ""
}
// #endregion

// #region Field editor dispatch (§2.2 — implement exactly these behaviors)
@Composable
fun FieldEditor(
    app: BharatVaultApp,
    templateId: String,
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    showInvalid: Boolean,
) {
    val label = fieldLabel(templateId, field.k)
    val invalid = showInvalid && !FieldValidation.isValid(field, value)
    val modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)

    when (field.type) {
        FieldType.TEXT -> PlainField(value, onValueChange, label, modifier, invalid)
        FieldType.NOTE -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            modifier = modifier, minLines = 3,
        )
        FieldType.SECRET -> SecretField(app, value, onValueChange, label, modifier)
        FieldType.PIN -> OutlinedTextField(
            value = value,
            onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(8)) }, // 3–8 digits (§2.2)
            label = { Text(label) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            modifier = modifier, singleLine = true,
            isError = invalid,
            supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
        )
        FieldType.NUMBER -> OutlinedTextField(
            value = value,
            onValueChange = { v -> onValueChange(v.filter(Char::isDigit)) },
            label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = modifier, singleLine = true,
            isError = invalid,
            supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
        )
        FieldType.PHONE -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            placeholder = { Text("+91") }, // §2.2 default prefix hint
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = modifier, singleLine = true,
        )
        FieldType.EMAIL -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = modifier, singleLine = true,
            isError = value.isNotEmpty() && !value.contains('@'),
        )
        FieldType.URL -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            modifier = modifier, singleLine = true,
        )
        FieldType.DATE -> DateField(value, onValueChange, label, modifier)
        FieldType.MONTHYEAR -> MonthYearField(value, onValueChange, label, modifier)
        FieldType.IFSC -> OutlinedTextField(
            value = value,
            onValueChange = { v -> onValueChange(v.uppercase().take(11)) }, // uppercase (§2.2)
            label = { Text(label) },
            modifier = modifier, singleLine = true,
            textStyle = SecretTextStyle,
            isError = invalid,
            supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
        )
        FieldType.CARDNUM -> CardNumberField(value, onValueChange, label, modifier)
        FieldType.TOTP -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            placeholder = { Text(stringResource(R.string.scr_edit_totp_hint)) },
            modifier = modifier, singleLine = true,
            textStyle = SecretTextStyle,
        )
        FieldType.PICKER -> PickerField(app, field, value, onValueChange, label, modifier)
        FieldType.LINK -> LinkField(app, field, value, onValueChange, label, modifier)
        FieldType.FILE -> { /* attachments arrive with M4 templates */ }
    }
}

@Composable
private fun PlainField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalid: Boolean,
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange, label = { Text(label) },
        modifier = modifier, singleLine = true,
        isError = invalid,
        supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
    )
}

/** §2.2 SECRET: masked, monospace, reveal button, generator button (§5.8). */
@Composable
private fun SecretField(
    app: BharatVaultApp,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
) {
    var visible by remember { mutableStateOf(false) }
    var showGenerator by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        textStyle = SecretTextStyle,
        modifier = modifier,
        singleLine = true,
        trailingIcon = {
            Row {
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        if (visible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                        contentDescription = stringResource(if (visible) R.string.scr_detail_hide else R.string.scr_detail_reveal),
                    )
                }
                IconButton(onClick = { showGenerator = true }) {
                    Icon(Icons.Outlined.Casino, contentDescription = stringResource(R.string.scr_edit_generate))
                }
            }
        },
    )
    if (showGenerator) {
        GeneratorSheet(
            app = app,
            onUse = {
                onValueChange(it)
                showGenerator = false
            },
            onDismiss = { showGenerator = false },
        )
    }
}
// #endregion

// #region Type-specific editors
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DateField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier) {
    var showPicker by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value, onValueChange = {}, readOnly = true, label = { Text(label) },
        placeholder = { Text("YYYY-MM-DD") },
        modifier = modifier,
        trailingIcon = {
            TextButton(onClick = { showPicker = true }) { Text("📅") }
        },
    )
    if (showPicker) {
        val state = rememberDatePickerState(
            initialSelectedDateMillis = runCatching {
                LocalDate.parse(value).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
            }.getOrNull(),
        )
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { ms ->
                        onValueChange(
                            Instant.ofEpochMilli(ms).atZone(ZoneOffset.UTC).toLocalDate().toString(), // YYYY-MM-DD (§2.1)
                        )
                    }
                    showPicker = false
                }) { Text(stringResource(R.string.msg_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        ) { DatePicker(state = state) }
    }
}

/** §2.2 MONTHYEAR: month + year picker storing YYYY-MM (card expiry). */
@Composable
private fun MonthYearField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier) {
    var showPicker by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value, onValueChange = {}, readOnly = true, label = { Text(label) },
        placeholder = { Text("YYYY-MM") },
        modifier = modifier,
        trailingIcon = { TextButton(onClick = { showPicker = true }) { Text("📅") } },
    )
    if (showPicker) {
        val now = LocalDate.now()
        var month by remember { mutableStateOf(value.substringAfter('-', "").toIntOrNull() ?: now.monthValue) }
        var year by remember { mutableStateOf(value.substringBefore('-', "").toIntOrNull() ?: now.year) }
        AlertDialog(
            onDismissRequest = { showPicker = false },
            title = { Text(label) },
            text = {
                Row {
                    DropdownSelector(
                        options = (1..12).map { it.toString().padStart(2, '0') },
                        selected = month.toString().padStart(2, '0'),
                        onSelect = { month = it.toInt() },
                        modifier = Modifier.weight(1f).padding(end = 8.dp),
                    )
                    DropdownSelector(
                        options = (now.year - 20..now.year + 20).map(Int::toString),
                        selected = year.toString(),
                        onSelect = { year = it.toInt() },
                        modifier = Modifier.weight(1f),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onValueChange("$year-${month.toString().padStart(2, '0')}")
                    showPicker = false
                }) { Text(stringResource(R.string.msg_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        )
    }
}

/** §2.2 PICKER: bundled list + "Other" free text (DECISIONS.md D-002). */
@Composable
private fun PickerField(
    app: BharatVaultApp,
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
) {
    val options = app.catalog.pickers[field.typeParam].orEmpty()
    var freeText by remember { mutableStateOf(value.isNotEmpty() && value !in options) }
    if (freeText) {
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            label = { Text("$label — ${stringResource(R.string.scr_edit_picker_other_hint)}") },
            modifier = modifier, singleLine = true,
        )
        TextButton(onClick = { freeText = false; onValueChange("") }) {
            Text(stringResource(R.string.msg_back))
        }
    } else {
        val otherLabel = stringResource(R.string.scr_edit_picker_other)
        DropdownSelector(
            options = options + otherLabel,
            selected = value,
            onSelect = { picked ->
                if (picked == otherLabel) freeText = true else onValueChange(picked)
            },
            label = label,
            modifier = modifier,
        )
    }
}

/** §2.2 LINK: picker of existing records of that template; stores their uuid. */
@Composable
private fun LinkField(
    app: BharatVaultApp,
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
) {
    val body = app.repository.body.collectAsState().value
    val candidates = body?.records?.filter { it.template_id == field.typeParam }.orEmpty()
    val noneLabel = stringResource(R.string.scr_edit_link_none)
    DropdownSelector(
        options = listOf(noneLabel) + candidates.map { it.title },
        selected = candidates.firstOrNull { it.uuid == value }?.title ?: noneLabel,
        onSelect = { picked ->
            onValueChange(candidates.firstOrNull { it.title == picked }?.uuid ?: "")
        },
        label = label,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = label?.let { { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = modifier.menuAnchor(androidx.compose.material3.MenuAnchorType.PrimaryNotEditable),
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelect(option)
                        expanded = false
                    },
                )
            }
        }
    }
}
// #endregion

// #region Card number (§2.2/§5.10: groups of 4, Luhn check, network badge)
@Composable
private fun CardNumberField(value: String, onValueChange: (String) -> Unit, label: String, modifier: Modifier) {
    val network = CardUtils.detectNetwork(value)
    val luhnOk = value.length < 12 || CardUtils.luhnValid(value)
    OutlinedTextField(
        value = value,
        onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(19)) }, // stored without spaces (§2.2)
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        visualTransformation = CardGroupingTransformation,
        textStyle = SecretTextStyle,
        modifier = modifier,
        singleLine = true,
        trailingIcon = network?.let { n ->
            {
                Text(
                    when (n) {
                        CardUtils.Network.RUPAY -> "RuPay"
                        CardUtils.Network.VISA -> "Visa"
                        CardUtils.Network.MASTERCARD -> "MC"
                        CardUtils.Network.AMEX -> "Amex"
                        CardUtils.Network.DINERS -> "Diners"
                        CardUtils.Network.MAESTRO -> "Maestro"
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 12.dp),
                )
            }
        },
        supportingText = {
            if (!luhnOk) Text(stringResource(R.string.scr_edit_luhn_warning), color = MaterialTheme.colorScheme.secondary) // warn, don't block
        },
    )
}

/** Groups digits in 4s while typing; storage stays digit-only. */
private val CardGroupingTransformation = VisualTransformation { text ->
    val digits = text.text
    val grouped = digits.chunked(4).joinToString(" ")
    androidx.compose.ui.text.input.TransformedText(
        androidx.compose.ui.text.AnnotatedString(grouped),
        object : androidx.compose.ui.text.input.OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset + (offset / 4).coerceAtMost(maxOf(0, (digits.length - 1) / 4))
            override fun transformedToOriginal(offset: Int): Int = offset - (offset / 5)
        },
    )
}
// #endregion
