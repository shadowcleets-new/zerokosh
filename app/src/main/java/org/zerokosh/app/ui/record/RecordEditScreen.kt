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
package org.zerokosh.app.ui.record

// #region Imports
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material.icons.outlined.Contactless
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import org.zerokosh.app.ui.common.RevealToggle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.WhiteCard
import org.zerokosh.app.ui.theme.FieldLabelStyle
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.model.CustomField
import org.zerokosh.app.R
import org.zerokosh.app.ui.common.fieldLabel
import org.zerokosh.app.ui.generator.GeneratorSheet
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.core.model.FieldType
import org.zerokosh.core.model.FieldValidation
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.TemplateField
import org.zerokosh.core.util.CardUtils
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
// #endregion

// #region Screen state & save
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordEditScreen(
    app: ZerokoshApp,
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
    // The mockup's "+ Add another field": the record model and the detail view
    // already carry custom fields, this is where they get authored.
    var customFields by remember { mutableStateOf(existing?.custom_fields ?: emptyList()) }
    var showAddField by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    fun invalidFields(): List<String> = template.fields.filter { f ->
        val v = values[f.k].orEmpty()
        !FieldValidation.isValid(f, v) && f.type != FieldType.CARDNUM // Luhn warns, never blocks (§5.10)
    }.map { it.k }

    var showInvalid by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize(),
        // BV-20: the root scaffold in ZerokoshNav already consumes the system
        // bars; applying them again here padded the gesture bar twice.
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // NFC prefill is only meaningful on the card template, and only when
            // the phone can actually do it.
            var showTapCard by remember { mutableStateOf(false) }
            if (showTapCard) {
                TapCardSheet(
                    onCard = { card ->
                        card.pan?.let { values["card_number"] = it }
                        card.expiryMonthYear?.let { mmyy ->
                            // The MONTHYEAR field stores YYYY-MM.
                            values["expiry"] = "20" + mmyy.substring(2) + "-" + mmyy.substring(0, 2)
                        }
                        card.cardholderName?.let { values["name_on_card"] = it }
                        // Network comes from the card number's IIN, not the chip —
                        // detectNetwork already drives the badge on this field, so
                        // this is the same answer written down rather than a new
                        // source of truth.
                        card.pan?.let { pan ->
                            CardUtils.detectNetwork(pan)?.let { n ->
                                values["card_network"] = when (n) {
                                    CardUtils.Network.RUPAY -> "RuPay"
                                    CardUtils.Network.VISA -> "Visa"
                                    CardUtils.Network.MASTERCARD -> "Mastercard"
                                    CardUtils.Network.AMEX -> "American Express"
                                    CardUtils.Network.DINERS -> "Diners Club"
                                    CardUtils.Network.MAESTRO -> "Maestro"
                                }
                            }
                        }
                        // Only when the card said so itself; kind is never guessed.
                        card.kind?.let { values["card_type"] = it }
                        showTapCard = false
                    },
                    onDismiss = { showTapCard = false },
                )
            }

            // Header: Cancel / mono record kicker / Save, exactly as the mockup.
            val saveAction = save@{
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
                        custom_fields = customFields.filter { it.label.isNotBlank() },
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
                }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.scr_edit_cancel),
                    style = MaterialTheme.typography.bodyMedium,
                    color = VaultTheme.colors.mute,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = onDone)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                )
                Kicker(
                    text = listOf(
                        if (existing == null) "New" else "Edit",
                        institution.ifBlank { org.zerokosh.app.ui.gallery.templateName(templateId) },
                    ).joinToString(" · "),
                )
                Text(
                    text = stringResource(R.string.scr_edit_save),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = VaultTheme.colors.accent,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { saveAction() }
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                )
            }

            // Brand strip: which template this record is being cut from.
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                BrandTile(code = institution.ifBlank { title.ifBlank { templateId } })
                Column {
                    Text(
                        text = title.ifBlank { org.zerokosh.app.ui.gallery.templateName(templateId) },
                        style = MaterialTheme.typography.titleMedium,
                        color = VaultTheme.colors.ink,
                        maxLines = 1,
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "using the ${org.zerokosh.app.ui.gallery.templateName(templateId)} template",
                        fontSize = 11.sp,
                        color = VaultTheme.colors.mute,
                        maxLines = 1,
                    )
                }
            }

            Column(
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
                    ) {
                        template.fields.forEach { field ->
                            FieldEditor(
                                app = app,
                                templateId = templateId,
                                field = field,
                                value = values[field.k].orEmpty(),
                                onValueChange = { values[field.k] = it },
                                showInvalid = showInvalid,
                                // Only the card template has a card to tap.
                                onTapCard = if (templateId == "card") {
                                    { showTapCard = true }
                                } else {
                                    null
                                },
                            )
                        }
                    }
                }
            }
            if (customFields.isNotEmpty()) {
                WhiteCard(corner = 20.dp) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        customFields.forEachIndexed { index, custom ->
                            CustomFieldEditor(
                                app = app,
                                field = custom,
                                onValueChange = { updated ->
                                    customFields = customFields.toMutableList()
                                        .also { it[index] = updated }
                                },
                                onRemove = {
                                    customFields = customFields.toMutableList()
                                        .also { it.removeAt(index) }
                                },
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(VaultTheme.colors.primary.copy(alpha = 0.06f))
                    .border(1.dp, VaultTheme.colors.primary.copy(alpha = 0.20f), RoundedCornerShape(16.dp))
                    .clickable { showAddField = true }
                    .padding(vertical = 12.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center,
            ) {
                Text(
                    text = "+ Add another field",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = VaultTheme.colors.primary,
                )
            }

            Spacer(Modifier.height(48.dp))
        }
        }
    }

    if (showAddField) {
        AddCustomFieldDialog(
            onDismiss = { showAddField = false },
            onAdd = { label, type ->
                customFields = customFields + CustomField(label = label, type = type, value = "")
                showAddField = false
            },
        )
    }
}

/** One user-named field: the label is fixed, the value edits like any secret or text. */
@Composable
private fun CustomFieldEditor(
    app: ZerokoshApp,
    field: CustomField,
    onValueChange: (CustomField) -> Unit,
    onRemove: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = field.label.uppercase(),
                style = FieldLabelStyle,
                color = VaultTheme.colors.mute,
            )
            Text(
                text = "Remove",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .clickable(onClick = onRemove)
                    .padding(horizontal = 6.dp, vertical = 2.dp),
            )
        }
        Spacer(Modifier.height(6.dp))
        if (field.type == "SECRET") {
            SecretField(
                app = app,
                value = field.value,
                onValueChange = { onValueChange(field.copy(value = it)) },
                label = field.label,
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            OutlinedTextField(
                value = field.value,
                onValueChange = { onValueChange(field.copy(value = it)) },
                label = { Text(field.label) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
            )
        }
    }
}

@Composable
private fun AddCustomFieldDialog(onDismiss: () -> Unit, onAdd: (String, String) -> Unit) {
    var label by remember { mutableStateOf("") }
    var secret by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add a field") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text("Field name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Checkbox(checked = secret, onCheckedChange = { secret = it })
                    Text("Treat as a secret (masked, hold to reveal)")
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = label.isNotBlank(),
                onClick = { onAdd(label.trim(), if (secret) "SECRET" else "TEXT") },
            ) { Text("Add") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(stringResource(R.string.msg_cancel)) } },
    )
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
    app: ZerokoshApp,
    templateId: String,
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    showInvalid: Boolean,
    onTapCard: (() -> Unit)? = null,
) {
    val label = fieldLabel(templateId, field.k)
    val invalid = showInvalid && !FieldValidation.isValid(field, value)
    val modifier = Modifier.fillMaxWidth()

    when (field.type) {
        FieldType.TEXT -> PlainField(value, onValueChange, label, modifier, invalid)
        FieldType.NOTE -> OutlinedTextField(
            value = value, onValueChange = onValueChange, label = { Text(label) },
            modifier = modifier, minLines = 3,
        )
        FieldType.SECRET -> SecretField(app, value, onValueChange, label, modifier)
        FieldType.PIN -> {
            var pinVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = value,
                onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(8)) }, // 3–8 digits (§2.2)
                label = { Text(label) },
                visualTransformation = if (pinVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = { RevealToggle(visible = pinVisible, onToggle = { pinVisible = !pinVisible }) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                modifier = modifier, singleLine = true,
                isError = invalid,
                supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
            )
        }
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
        FieldType.CARDNUM -> CardNumberField(value, onValueChange, label, modifier, onTapCard)
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
        // TextFieldLabelPosition is only on the TextFieldState-based overload,
        // so Cutout waits on that migration. The expressive shape applies here.
        shape = OutlinedTextFieldDefaults.roundedShape,
        modifier = modifier, singleLine = true,
        isError = invalid,
        supportingText = { if (invalid) Text(stringResource(R.string.scr_edit_invalid), color = MaterialTheme.colorScheme.error) },
    )
}

/** §2.2 SECRET: masked, monospace, reveal button, generator button (§5.8). */
@Composable
private fun SecretField(
    app: ZerokoshApp,
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
            IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Outlined.CalendarToday, contentDescription = "Pick a date")
            }
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
        trailingIcon = { IconButton(onClick = { showPicker = true }) {
                Icon(Icons.Outlined.CalendarToday, contentDescription = "Pick a date")
            } },
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
    app: ZerokoshApp,
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
    app: ZerokoshApp,
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
private fun CardNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    onTapCard: (() -> Unit)? = null,
) {
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
        // The tap-to-read button and the detected-network badge share this slot.
        // The button is offered only where the hardware can honour it.
        trailingIcon = {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                if (onTapCard != null) {
                    IconButton(onClick = onTapCard) {
                        Icon(
                            Icons.Outlined.Contactless,
                            contentDescription = "Read the card by tapping it",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
                if (network != null) {
                    Text(
                        when (network) {
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
