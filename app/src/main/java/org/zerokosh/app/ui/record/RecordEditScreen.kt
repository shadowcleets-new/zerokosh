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
import org.zerokosh.app.ui.common.VaultFieldCard
import org.zerokosh.app.ui.common.VaultPickerSheet
import org.zerokosh.core.model.seedFieldsFromPreset
import org.zerokosh.app.ui.common.pickerHasLogos
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.semantics.Role
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
    // The gallery choice IS the institution — that is what the field groups by.
    var institution by rememberSaveable {
        mutableStateOf(existing?.institution ?: presetName.orEmpty())
    }
    val values = remember {
        mutableStateMapOf<String, String>().apply {
            existing?.fields?.forEach { (k, v) -> put(k, v) }
            // Picking "ICICI Bank" in the gallery and then hunting for ICICI
            // Bank in an 80-row picker is the same question asked twice.
            if (existing == null && presetName != null) {
                putAll(seedFieldsFromPreset(template, presetName, app.catalog.pickers))
                if (templateId == "app_profile") put("app_name", presetName)
            }
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
                        // Precedence: the chip wins where it speaks, because the
                        // card asserted it. The bundled IIN table fills what the
                        // chip left silent — which is most cards, since only some
                        // issuers write DEBIT/CREDIT into the Application Label.
                        val iin = card.pan?.let { app.catalog.cardIins.lookup(it) }
                        (card.kind ?: iin?.kind)?.let { values["card_type"] = it }
                        // Bank and variant are table-only; neither is on the chip.
                        // The bank goes to `institution`, the record's grouping field —
                        // the card template has no bank_name of its own, so writing one
                        // would save a field nothing renders.
                        // Existing entries are never clobbered: a prefill must not
                        // overwrite something the user already typed.
                        iin?.bank?.let { if (institution.isBlank()) institution = it }
                        iin?.variant?.let {
                            if (values["card_variant"].isNullOrBlank()) values["card_variant"] = it
                        }
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

            // The mockup is one flat list of field cards at 8dp, inside 24dp
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
                    run {
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
    val invalidText = if (invalid) stringResource(R.string.scr_edit_invalid) else null
    val errorColour = MaterialTheme.colorScheme.error

    // Every branch renders into the mockup's field card. Only the input inside
    // it differs: monospace for anything transcribed, masked for secrets, a
    // dropdown for pickers.
    when (field.type) {
        FieldType.SECRET -> SecretField(app, value, onValueChange, label, modifier)
        FieldType.CARDNUM -> CardNumberField(value, onValueChange, label, modifier, onTapCard)
        FieldType.PICKER -> PickerField(app, field, value, onValueChange, label, modifier)
        FieldType.LINK -> LinkField(app, field, value, onValueChange, label, modifier)
        FieldType.DATE -> DateField(value, onValueChange, label, modifier)
        FieldType.MONTHYEAR -> MonthYearField(value, onValueChange, label, modifier)
        FieldType.FILE -> { /* attachments arrive with M4 templates */ }

        FieldType.PIN -> {
            var pinVisible by remember { mutableStateOf(false) }
            VaultFieldCard(
                label = label,
                modifier = modifier,
                supporting = invalidText,
                supportingColor = errorColour,
                trailing = { RevealToggle(visible = pinVisible, onToggle = { pinVisible = !pinVisible }) },
            ) {
                FieldInput(
                    value = value,
                    onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(8)) },
                    mono = true,
                    dimmed = !pinVisible,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    visualTransformation = if (pinVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                )
            }
        }

        FieldType.NOTE -> VaultFieldCard(label = label, modifier = modifier) {
            FieldInput(value = value, onValueChange = onValueChange, singleLine = false)
        }

        else -> {
            // The plain text family. They differ only by keyboard, filter and
            // whether the value is something a human transcribes.
            val mono = field.type in setOf(FieldType.NUMBER, FieldType.IFSC, FieldType.TOTP)
            val keyboard = when (field.type) {
                FieldType.NUMBER -> KeyboardType.Number
                FieldType.PHONE -> KeyboardType.Phone
                FieldType.EMAIL -> KeyboardType.Email
                FieldType.URL -> KeyboardType.Uri
                else -> KeyboardType.Text
            }
            val filter: (String) -> String = when (field.type) {
                FieldType.NUMBER -> { v -> v.filter(Char::isDigit) }
                FieldType.IFSC -> { v -> v.uppercase().take(11) } // uppercase (§2.2)
                else -> { v -> v }
            }
            val supporting = when {
                invalidText != null -> invalidText
                field.type == FieldType.EMAIL && value.isNotEmpty() && !value.contains('@') ->
                    stringResource(R.string.scr_edit_invalid)
                else -> null
            }
            VaultFieldCard(
                label = label,
                modifier = modifier,
                supporting = supporting,
                supportingColor = errorColour,
            ) {
                FieldInput(
                    value = value,
                    onValueChange = { onValueChange(filter(it)) },
                    mono = mono,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboard),
                )
            }
        }
    }
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
    VaultFieldCard(
        label = label,
        modifier = modifier,
        trailing = {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                RevealToggle(visible = visible, onToggle = { visible = !visible })
                IconButton(onClick = { showGenerator = true }) {
                    Icon(
                        Icons.Outlined.Casino,
                        contentDescription = stringResource(R.string.scr_edit_generate),
                        tint = VaultTheme.colors.ink(0.55f),
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
        },
    ) {
        FieldInput(
            value = value,
            onValueChange = onValueChange,
            mono = true,
            dimmed = !visible,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (visible) VisualTransformation.None
            else PasswordVisualTransformation(),
        )
    }
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
    VaultFieldCard(
        label = label,
        modifier = modifier,
        trailing = {
            IconButton(onClick = { showPicker = true }) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = "Pick a date",
                    tint = VaultTheme.colors.ink(0.55f),
                    modifier = Modifier.size(20.dp),
                )
            }
        },
    ) {
        FieldInput(
            value = value,
            onValueChange = {},
            mono = true,
            placeholder = "YYYY-MM-DD",
        )
    }
    if (showPicker) {
        val state = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { millis ->
                        onValueChange(
                            java.time.Instant.ofEpochMilli(millis)
                                .atZone(java.time.ZoneOffset.UTC).toLocalDate().toString(),
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
    VaultFieldCard(
        label = label,
        modifier = modifier,
        trailing = {
            IconButton(onClick = { showPicker = true }) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = "Pick a date",
                    tint = VaultTheme.colors.ink(0.55f),
                    modifier = Modifier.size(20.dp),
                )
            }
        },
    ) {
        FieldInput(value = value, onValueChange = {}, mono = true, placeholder = "YYYY-MM")
    }
    if (showPicker) {
        val now = java.time.LocalDate.now()
        var month by remember { mutableStateOf(value.substringAfter('-', "").toIntOrNull() ?: now.monthValue) }
        var year by remember { mutableStateOf(value.substringBefore('-', "").toIntOrNull() ?: now.year) }
        AlertDialog(
            onDismissRequest = { showPicker = false },
            title = { Text(label) },
            text = {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DropdownSelector(
                        options = (1..12).map { it.toString().padStart(2, '0') },
                        selected = month.toString().padStart(2, '0'),
                        onSelect = { month = it.toInt() },
                        modifier = Modifier.weight(1f),
                    )
                    DropdownSelector(
                        options = (now.year..now.year + 20).map { it.toString() },
                        selected = year.toString(),
                        onSelect = { year = it.toInt() },
                        modifier = Modifier.weight(1f),
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onValueChange("$year-" + month.toString().padStart(2, '0'))
                    showPicker = false
                }) { Text(stringResource(R.string.msg_ok)) }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) { Text(stringResource(R.string.msg_cancel)) }
            },
        )
    }
}

/**
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
        VaultFieldCard(
            label = "$label — ${stringResource(R.string.scr_edit_picker_other_hint)}",
            modifier = modifier,
            trailing = {
                TextButton(onClick = { freeText = false; onValueChange("") }) {
                    Text(stringResource(R.string.msg_back))
                }
            },
        ) {
            FieldInput(value = value, onValueChange = onValueChange)
        }
    } else {
        val otherLabel = stringResource(R.string.scr_edit_picker_other)
        FieldCardMenu(
            label = label,
            selected = value,
            options = options,
            onSelect = { picked ->
                if (picked == otherLabel) freeText = true else onValueChange(picked)
            },
            modifier = modifier,
            logos = pickerHasLogos(field.typeParam),
            pinned = otherLabel,
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
    FieldCardMenu(
        label = label,
        selected = candidates.firstOrNull { it.uuid == value }?.title ?: noneLabel,
        options = listOf(noneLabel) + candidates.map { it.title },
        onSelect = { picked ->
            onValueChange(candidates.firstOrNull { it.title == picked }?.uuid ?: "")
        },
        modifier = modifier,
    )
}

/**
 * A choice rendered as the mockup's field card: label, current value, chevron.
 * Tapping anywhere on the card opens the menu — the whole card is the target,
 * not a 24dp arrow.
 */
@Composable
private fun FieldCardMenu(
    label: String,
    selected: String,
    options: List<String>,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    logos: Boolean = false,
    pinned: String? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val c = VaultTheme.colors
    Box {
        VaultFieldCard(
            label = label,
            modifier = modifier.clickable(role = Role.Button) { expanded = true },
            trailing = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    tint = c.ink(0.55f),
                    modifier = Modifier.size(20.dp),
                )
            },
        ) {
            Text(
                text = selected.ifBlank { "—" },
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    color = if (selected.isBlank()) c.ink(0.3f) else c.ink,
                ),
                maxLines = 1,
            )
        }
    }
    // A dropdown made the user scroll 80 banks to reach "Yes Bank". The sheet
    // searches, shows the logo, and opens on whatever is already selected.
    if (expanded) {
        VaultPickerSheet(
            title = label,
            options = options,
            selected = selected,
            onSelect = onSelect,
            onDismiss = { expanded = false },
            logos = logos,
            pinned = pinned,
        )
    }
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
    VaultFieldCard(
        label = label,
        modifier = modifier,
        supporting = if (!luhnOk) stringResource(R.string.scr_edit_luhn_warning) else null,
        supportingColor = MaterialTheme.colorScheme.secondary, // warn, don't block
        trailing = {
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                if (onTapCard != null) {
                    IconButton(onClick = onTapCard) {
                        Icon(
                            Icons.Outlined.Contactless,
                            contentDescription = "Read the card by tapping it",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp),
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
                    )
                }
            }
        },
    ) {
        FieldInput(
            value = value,
            onValueChange = { v -> onValueChange(v.filter(Char::isDigit).take(19)) }, // stored without spaces (§2.2)
            mono = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CardGroupingTransformation,
        )
    }
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
