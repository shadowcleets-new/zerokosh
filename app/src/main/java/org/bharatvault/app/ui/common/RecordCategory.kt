/**
 * @file RecordCategory.kt
 * @description Maps template ids onto the six shelves the mockup's Home screen
 *              filters and groups by, plus the derived row metadata (masked
 *              identifier and micro-badge) each vault row shows.
 */
package org.bharatvault.app.ui.common

// #region Imports
import org.bharatvault.app.data.AssetCatalog
import org.bharatvault.core.model.Record
import org.bharatvault.core.model.Sensitivity
// #endregion

// #region Categories
enum class RecordCategory(val label: String) {
    BanksUpi("Banks & UPI"),
    Cards("Cards"),
    Investments("Investments"),
    GovId("Gov & ID"),
    Utilities("Utilities"),
    Apps("Apps & Logins"),
}

private val CategoryByTemplate = mapOf(
    "bank_account" to RecordCategory.BanksUpi,
    "upi" to RecordCategory.BanksUpi,
    "card" to RecordCategory.Cards,
    "demat" to RecordCategory.Investments,
    "epf_pension" to RecordCategory.Investments,
    "pan_card" to RecordCategory.GovId,
    "aadhaar_card" to RecordCategory.GovId,
    "passport" to RecordCategory.GovId,
    "driving_license" to RecordCategory.GovId,
    "voter_id" to RecordCategory.GovId,
    "digilocker" to RecordCategory.GovId,
    "gov_id" to RecordCategory.GovId,
    "utility" to RecordCategory.Utilities,
    "telecom" to RecordCategory.Utilities,
    "transit" to RecordCategory.Utilities,
    "shopping" to RecordCategory.Utilities,
    "app_profile" to RecordCategory.Apps,
    "login" to RecordCategory.Apps,
    "secure_note" to RecordCategory.Apps,
)

val Record.category: RecordCategory
    get() = CategoryByTemplate[template_id] ?: RecordCategory.Apps
// #endregion

// #region Row metadata
/**
 * The mono line under a record title — the first identifying field, masked to
 * its template's sensitivity, so the list never leaks a secret at a glance.
 */
fun recordMeta(catalog: AssetCatalog, record: Record): String {
    val template = catalog.templates.byId(record.template_id)
    val entry = record.fields.entries.firstOrNull { (key, value) ->
        value.isNotBlank() &&
            (template?.fields?.firstOrNull { it.k == key }?.sensitivity ?: Sensitivity.L) != Sensitivity.H
    } ?: return record.institution
    val field = template?.fields?.firstOrNull { it.k == entry.key }
    return maskedValue(entry.value, field, revealed = false)
}

/** The small uppercase badge beside a title: UPI, TOTP or ID. */
fun recordBadge(record: Record): String? = when {
    record.fields.keys.any { it.contains("totp") } -> "TOTP"
    record.template_id == "upi" || record.fields.keys.any { it.contains("upi") } -> "UPI"
    record.category == RecordCategory.GovId -> "ID"
    else -> null
}
// #endregion

// #region Time formatting
/** "3d ago" / "just now" — the relative stamp under a record title. */
fun relativeTime(millis: Long, nowMs: Long = System.currentTimeMillis()): String {
    val delta = (nowMs - millis).coerceAtLeast(0)
    val minutes = delta / 60_000
    val hours = minutes / 60
    val days = hours / 24
    return when {
        minutes < 1 -> "just now"
        minutes < 60 -> "${minutes}m ago"
        hours < 24 -> "${hours}h ago"
        days < 30 -> "${days}d ago"
        days < 365 -> "${days / 30}mo ago"
        else -> "${days / 365}y ago"
    }
}
// #endregion
