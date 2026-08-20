package org.zerokosh.core.model

// #region Imports
import kotlinx.serialization.Serializable
// #endregion

// #region Field & sensitivity enums (§2.2, §2.3)
enum class Sensitivity { L, M, H }

enum class FieldType {
    TEXT, SECRET, PIN, NUMBER, DATE, MONTHYEAR, PHONE, EMAIL, URL, TOTP,
    CARDNUM, IFSC, PICKER, LINK, NOTE, FILE;

    companion object {
        /** "PICKER:banks" → PICKER, "LINK:bank_account" → LINK. */
        fun parse(raw: String): FieldType = valueOf(raw.substringBefore(':'))
    }
}
// #endregion

// #region templates.json schema (§2.4 — file itself is bundled verbatim)
@Serializable
data class TemplateField(
    val k: String, // field_id — NEVER rename (R0.2 item 5)
    val t: String, // type, possibly "PICKER:<list>" / "LINK:<template_id>"
    val s: String, // L | M | H
    val v: String? = null, // validation regex
    val m: String? = null, // mask rule ("last4")
) {
    val type: FieldType get() = FieldType.parse(t)
    val sensitivity: Sensitivity get() = Sensitivity.valueOf(s)

    /** The parameter after ':' for PICKER/LINK types, else null. */
    val typeParam: String? get() = t.substringAfter(':', "").ifEmpty { null }
}

@Serializable
data class Template(
    val id: String,
    val icon: String,
    val fields: List<TemplateField>,
)

@Serializable
data class TemplateCatalog(
    val format: Int,
    val templates: List<Template>,
) {
    fun byId(id: String): Template? = templates.firstOrNull { it.id == id }

    companion object {
        fun parse(jsonText: String): TemplateCatalog = VaultJson.decodeFromString(jsonText)
    }
}
// #endregion

// #region Field validation helpers
object FieldValidation {
    val IFSC_REGEX = Regex("^[A-Z]{4}0[A-Z0-9]{6}$")

    /** Validates a value against a template field's regex (empty values are always valid — §2.1 omits them). */
    fun isValid(field: TemplateField, value: String): Boolean {
        if (value.isEmpty()) return true
        if (field.type == FieldType.IFSC) return IFSC_REGEX.matches(value)
        val pattern = field.v ?: return true
        return Regex(pattern).matches(value)
    }
}
// #endregion
