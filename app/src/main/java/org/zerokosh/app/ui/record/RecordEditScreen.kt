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
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Contactless
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.nfc.PendingCard
import org.zerokosh.app.ui.common.BrandTile
import org.zerokosh.app.ui.common.Kicker
import org.zerokosh.app.ui.common.RevealToggle
import org.zerokosh.app.ui.common.SaveErrorDialog
import org.zerokosh.app.ui.common.VaultFieldCard
import org.zerokosh.app.ui.common.VaultPickerSheet
import org.zerokosh.app.ui.common.WhiteCard
import org.zerokosh.app.ui.common.fieldLabel
import org.zerokosh.app.ui.common.pickerHasLogos
import org.zerokosh.app.ui.common.saveErrorMessage
import org.zerokosh.app.ui.generator.GeneratorSheet
import org.zerokosh.app.ui.theme.FieldLabelStyle
import org.zerokosh.app.ui.theme.SecretTextStyle
import org.zerokosh.app.ui.theme.VaultTheme
import org.zerokosh.core.emv.EmvCard
import org.zerokosh.core.model.CustomField
import org.zerokosh.core.model.FieldType
import org.zerokosh.core.model.FieldValidation
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.TemplateField
import org.zerokosh.core.model.seedFieldsFromPreset
import org.zerokosh.core.util.CardUtils
// #endregion

// #region Screen state & save
/**
 * One card per template field, in template order.
 *
 * The card-number branch is the only thing here that is not a plain editor: a
 * typed number knows as much as a tapped one, since network, type, variant and
 * issuing bank all come from the bundled IIN table rather than the chip.
 */
@Composable
private fun TemplateFieldsSection(
    app: ZerokoshApp,
    template: org.zerokosh.core.model.Template,
    templateId: String,
    values: androidx.compose.runtime.snapshots.SnapshotStateMap<String, String>,
    showInvalid: Boolean,
    onTapCard: () -> Unit,
    onInstitutionInferred: (String) -> Unit,
) {
    if (template.fields.isEmpty()) return
    val isCard = templateId == "card"
    template.fields.forEach { field ->
        FieldEditor(
            app = app,
            templateId = templateId,
            field = field,
            value = values[field.k].orEmpty(),
            onValueChange = {
                values[field.k] = it
                if (isCard && field.k == "card_number") {
                    applyCardNumberInference(it, app, values, onInstitutionInferred)
                }
            },
            showInvalid = showInvalid,
            // Only the card template has a card to tap.
            onTapCard = if (isCard) onTapCard else null,
        )
    }
}

/**
 * The user's own fields, if they added any. Editing one means rewriting the
 * whole list, so the list rather than an index is what comes back out — the
 * caller should not have to know how a change is applied.
 */
@Composable
private fun CustomFieldsCard(
    app: ZerokoshApp,
    fields: List<CustomField>,
    onChange: (List<CustomField>) -> Unit,
) {
    if (fields.isEmpty()) return
    WhiteCard(corner = 20.dp) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            fields.forEachIndexed { index, custom ->
                CustomFieldEditor(
                    app = app,
                    field = custom,
                    onValueChange = { updated ->
                        onChange(fields.toMutableList().also { it[index] = updated })
                    },
                    onRemove = {
                        onChange(fields.toMutableList().also { it.removeAt(index) })
                    },
                )
            }
        }
    }
}

/**
 * The record a save would write. Pulled out of the save handler so the handler
 * is the two guards and the write, and the field-by-field construction — which
 * carries the rules about what an edit preserves — sits on its own.
 */
internal fun buildRecord(
    existing: Record?,
    templateId: String,
    title: String,
    institution: String,
    values: Map<String, String>,
    customFields: List<CustomField>,
): Record = Record(
    uuid = existing?.uuid ?: "",
    template_id = templateId,
    title = title.trim(),
    institution = institution.ifBlank { deriveInstitution(templateId, values) }.trim(),
    fields = values.filterValues { it.isNotBlank() }, // §2.1: empty fields omitted
    custom_fields = customFields.filter { it.label.isNotBlank() },
    // An edit preserves what the form never showed.
    // history included: it is only rebuilt when a secret actually changes, so
    // leaving it out dropped every remembered password whenever anything else
    // was edited — a title fix wiped the lot.
    history = existing?.history ?: emptyList(),
    tags = existing?.tags ?: emptyList(),
    favorite = existing?.favorite ?: false,
    created_at = existing?.created_at ?: 0,
    modified_at = 0,
    rev = existing?.rev ?: 1,
    device_id = "",
    reminders = existing?.reminders ?: emptyList(),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordEditScreen(
    app: ZerokoshApp,
    templateIdArg: String,
    editUuid: String?,
    presetName: String?,
    /**
     * The organisation behind the gallery entry, where it differs from the
     * product: "HDFC Bank" for the HDFC Credit Card, "Microsoft" for Xbox and
     * Teams alike. It resolves the logo and it is what the record groups by,
     * so a card and an account at the same bank sit together.
     */
    brandName: String? = null,
    onDone: () -> Unit,
) {
    val context = LocalContext.current
    val body = app.repository.body.collectAsState().value
    val existing = editUuid?.let { id -> body?.records?.firstOrNull { it.uuid == id } }
    val templateId = existing?.template_id ?: templateIdArg
    val template = app.catalog.templates.byId(templateId) ?: run { onDone(); return }

    var title by rememberSaveable { mutableStateOf(existing?.title ?: presetName.orEmpty()) }
    // The gallery choice IS the institution — that is what the field groups by,
    // and what BrandTile resolves the logo from.
    var institution by rememberSaveable {
        mutableStateOf(existing?.institution ?: brandName ?: presetName.orEmpty())
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
    // A card tapped from the FAB opens this screen with the read already done.
    // Taken once and dropped — a second composition must not re-apply it over
    // edits the user has since made.
    LaunchedEffect(Unit) {
        if (existing == null && templateId == "card") {
            PendingCard.take()?.let { card ->
                applyTappedCard(card, app, values) { institution = it }
                if (title.isBlank()) title = cardTitle(card, app, institution)
            }
        }
    }

    var titleMissing by remember { mutableStateOf(false) }
    // The mockup's "+ Add another field": the record model and the detail view
    // already carry custom fields, this is where they get authored.
    var customFields by remember { mutableStateOf(existing?.custom_fields ?: emptyList()) }
    var showAddField by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var saveError by remember { mutableStateOf<String?>(null) }

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
                        applyTappedCard(card, app, values) { institution = it }
                        if (title.isBlank()) title = cardTitle(card, app, institution)
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
                    val record = buildRecord(existing, templateId, title, institution, values, customFields)
                    scope.launch {
                        app.repository.upsertRecord(record)
                            .onSuccess { onDone() }
                            .onFailure { saveError = saveErrorMessage(context, it) }
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
                        stringResource(
                            if (existing == null) R.string.scr_edit_title_new else R.string.scr_detail_edit,
                        ),
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
                        text = stringResource(
                            R.string.re_using_template,
                            org.zerokosh.app.ui.gallery.templateName(templateId),
                        ),
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
            TemplateFieldsSection(
                app = app,
                template = template,
                templateId = templateId,
                values = values,
                showInvalid = showInvalid,
                onTapCard = { showTapCard = true },
                onInstitutionInferred = { bank -> if (institution.isBlank()) institution = bank },
            )
            CustomFieldsCard(
                app = app,
                fields = customFields,
                onChange = { customFields = it },
            )

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
                    text = stringResource(R.string.re_add_field),
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

    SaveErrorDialog(saveError) { saveError = null }
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
                text = stringResource(R.string.re_remove),
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
        title = { Text(stringResource(R.string.re_add_field_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = label,
                    onValueChange = { label = it },
                    label = { Text(stringResource(R.string.re_field_name)) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                )
                Row(
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Checkbox(checked = secret, onCheckedChange = { secret = it })
                    Text(stringResource(R.string.re_treat_secret))
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
    val modifier = Modifier.fillMaxWidth()
    val invalidText = if (showInvalid && !FieldValidation.isValid(field, value)) {
        stringResource(R.string.scr_edit_invalid)
    } else {
        null
    }

    // Every branch renders into the mockup's field card. Only the input inside
    // it differs: monospace for anything transcribed, masked for secrets, a
    // dropdown for pickers. Split across two dispatches so neither is a
    // ten-armed when — the four here need collaborators the rest do not.
    when (field.type) {
        FieldType.SECRET -> SecretField(app, value, onValueChange, label, modifier)
        FieldType.CARDNUM -> CardNumberField(value, onValueChange, label, modifier, onTapCard)
        FieldType.PICKER -> PickerField(app, field, value, onValueChange, label, modifier)
        FieldType.LINK -> LinkField(app, field, value, onValueChange, label, modifier)
        else -> SelfContainedFieldEditor(field, value, onValueChange, label, modifier, invalidText)
    }
}

/** The field types that need nothing but their own value. */
@Composable
private fun SelfContainedFieldEditor(
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalidText: String?,
) {
    when (field.type) {
        FieldType.DATE -> DateField(value, onValueChange, label, modifier)
        FieldType.MONTHYEAR -> MonthYearField(value, onValueChange, label, modifier)
        FieldType.FILE -> Unit // attachments arrive with M4 templates
        FieldType.PIN -> PinField(value, onValueChange, label, modifier, invalidText)
        FieldType.NOTE -> VaultFieldCard(label = label, modifier = modifier) {
            FieldInput(value = value, onValueChange = onValueChange, singleLine = false)
        }
        else -> PlainTextField(field, value, onValueChange, label, modifier, invalidText)
    }
}

@Composable
private fun PinField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalidText: String?,
) {
    var pinVisible by remember { mutableStateOf(false) }
    VaultFieldCard(
        label = label,
        modifier = modifier,
        supporting = invalidText,
        supportingColor = MaterialTheme.colorScheme.error,
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

/** Monospace for anything a human transcribes from a document. */
private fun isTranscribed(type: FieldType): Boolean =
    type in setOf(FieldType.NUMBER, FieldType.IFSC, FieldType.TOTP)

private fun keyboardFor(type: FieldType): KeyboardType = when (type) {
    FieldType.NUMBER -> KeyboardType.Number
    FieldType.PHONE -> KeyboardType.Phone
    FieldType.EMAIL -> KeyboardType.Email
    FieldType.URL -> KeyboardType.Uri
    else -> KeyboardType.Text
}

private fun filterFor(type: FieldType): (String) -> String = when (type) {
    FieldType.NUMBER -> { v -> v.filter(Char::isDigit) }
    FieldType.IFSC -> { v -> v.uppercase().take(11) } // uppercase (§2.2)
    else -> { v -> v }
}

/**
 * The plain text family. They differ only by keyboard, filter and whether the
 * value is something a human transcribes.
 */
@Composable
private fun PlainTextField(
    field: TemplateField,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier,
    invalidText: String?,
) {
    val missingAt = field.type == FieldType.EMAIL && value.isNotEmpty() && !value.contains('@')
    val supporting = invalidText ?: if (missingAt) stringResource(R.string.scr_edit_invalid) else null
    val filter = filterFor(field.type)
    VaultFieldCard(
        label = label,
        modifier = modifier,
        supporting = supporting,
        supportingColor = MaterialTheme.colorScheme.error,
    ) {
        FieldInput(
            value = value,
            onValueChange = { onValueChange(filter(it)) },
            mono = isTranscribed(field.type),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardFor(field.type)),
        )
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
                    contentDescription = stringResource(R.string.re_pick_date),
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
        // Open on the date the field already holds. Left at its default the
        // state selects nothing and displays the current month, so correcting a
        // passport expiry of 2031-04 started the user back at today. The
        // displayed month follows the selection, so seeding one fixes both.
        val state = rememberDatePickerState(
            initialSelectedDateMillis = remember(value) {
                runCatching {
                    LocalDate.parse(value)
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant().toEpochMilli()
                }.getOrNull()
            },
        )
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(onClick = {
                    state.selectedDateMillis?.let { millis ->
                        onValueChange(
                            Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC).toLocalDate().toString(),
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
                    contentDescription = stringResource(R.string.re_pick_date),
                    tint = VaultTheme.colors.ink(0.55f),
                    modifier = Modifier.size(20.dp),
                )
            }
        },
    ) {
        FieldInput(value = value, onValueChange = {}, mono = true, placeholder = "YYYY-MM")
    }
    if (showPicker) {
        val now = LocalDate.now()
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
                        label = stringResource(R.string.scr_edit_month),
                    )
                    DropdownSelector(
                        options = (now.year..now.year + 20).map { it.toString() },
                        selected = year.toString(),
                        onSelect = { year = it.toInt() },
                        modifier = Modifier.weight(1f),
                        label = stringResource(R.string.scr_edit_year),
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

/**
 * Writes a tapped card onto a card record. Shared so that tapping from the FAB
 * and tapping from an open card form fill by exactly the same rules.
 *
 * Never clobbers what the user already typed: a prefill is a suggestion.
 */
private fun applyTappedCard(
    card: EmvCard,
    app: ZerokoshApp,
    values: MutableMap<String, String>,
    setInstitution: (String) -> Unit,
) {
    card.pan?.let { values["card_number"] = it }
    card.expiryMonthYear?.let { mmyy ->
        // The MONTHYEAR field stores YYYY-MM.
        values["expiry"] = "20" + mmyy.substring(2) + "-" + mmyy.substring(0, 2)
    }
    card.cardholderName?.let { values["name_on_card"] = it }
    // Network comes from the card number's IIN, not the chip — detectNetwork
    // already drives the badge on this field, so this is the same answer written
    // down rather than a new source of truth.
    card.pan?.let { pan ->
        CardUtils.detectNetwork(pan)?.let { n -> values["card_network"] = n.label }
    }
    // Precedence: the chip wins where it speaks, because the card asserted it.
    // The bundled IIN table fills what the chip left silent — which is most
    // cards, since only some issuers write DEBIT/CREDIT into the Application
    // Label.
    val iin = card.pan?.let { app.catalog.cardIins.lookup(it) }
    (card.kind ?: iin?.kind)?.let { values["card_type"] = it }
    // Bank and variant are table-only; neither is on the chip. The bank goes to
    // `institution`, the record's grouping field — the card template has no
    // bank_name of its own, so writing one would save a field nothing renders.
    iin?.bank?.let(setInstitution)
    iin?.variant?.let {
        if (values["card_variant"].isNullOrBlank()) values["card_variant"] = it
    }
}

/**
 * Fills what a card number implies, without overwriting anything the user typed.
 *
 * Deliberately quieter than [applyTappedCard]: a tap is an explicit request to
 * populate the record, whereas typing is not, so this only ever fills blanks and
 * waits for enough digits to identify an issuer rather than guessing from three.
 */
private fun applyCardNumberInference(
    typed: String,
    app: ZerokoshApp,
    values: MutableMap<String, String>,
    setInstitution: (String) -> Unit,
) {
    val digits = typed.filter(Char::isDigit)
    // Six is the length of an IIN; below that any match would be a coincidence.
    if (digits.length < 6) return

    CardUtils.detectNetwork(digits)?.let { network ->
        if (values["card_network"].isNullOrBlank()) values["card_network"] = network.label
    }
    val iin = app.catalog.cardIins.lookup(digits) ?: return
    iin.kind?.let { if (values["card_type"].isNullOrBlank()) values["card_type"] = it }
    iin.variant?.let { if (values["card_variant"].isNullOrBlank()) values["card_variant"] = it }
    iin.bank?.let(setInstitution)
}

/**
 * A name for a card the user tapped rather than named. "HDFC Bank Credit" beats
 * an empty title on the vault list, and beats a PAN fragment — the number is the
 * one thing that should not be sitting in a record's headline.
 */
private fun cardTitle(card: EmvCard, app: ZerokoshApp, institution: String): String {
    val iin = card.pan?.let { app.catalog.cardIins.lookup(it) }
    val bank = institution.ifBlank { iin?.bank.orEmpty() }
    val kind = card.kind ?: iin?.kind
    val network = card.pan?.let { CardUtils.detectNetwork(it)?.label }
    return listOfNotNull(bank.ifBlank { null }, kind ?: network)
        .joinToString(" ")
        .ifBlank { card.applicationLabel.orEmpty() }
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
    // The caller's modifier belongs on the box, not on the field inside it. A
    // Row weight passed down here landed on a child of the box instead of on a
    // Row child, so it was ignored: the month dropdown took its full intrinsic
    // width and squeezed the year one to nothing.
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = label?.let { { Text(it) } },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(androidx.compose.material3.MenuAnchorType.PrimaryNotEditable),
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
/**
 * The short mark shown inside the field. Deliberately not Network.label — that
 * is the value stored on the record, and a badge has room for "MC" where a
 * stored field wants "Mastercard".
 */
private fun networkBadge(network: CardUtils.Network): String = when (network) {
    CardUtils.Network.RUPAY -> "RuPay"
    CardUtils.Network.VISA -> "Visa"
    CardUtils.Network.MASTERCARD -> "MC"
    CardUtils.Network.AMEX -> "Amex"
    CardUtils.Network.DINERS -> "Diners"
    CardUtils.Network.MAESTRO -> "Maestro"
}

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
                            contentDescription = stringResource(R.string.re_tap_card),
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
                if (network != null) {
                    Text(
                        networkBadge(network),
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
