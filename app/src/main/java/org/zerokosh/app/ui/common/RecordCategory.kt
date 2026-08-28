/**
 * @file RecordCategory.kt
 * @description Maps template ids onto the six shelves the mockup's Home screen
 *              filters and groups by, plus the derived row metadata (masked
 *              identifier and micro-badge) each vault row shows.
 */
package org.zerokosh.app.ui.common

// #region Imports
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import org.zerokosh.app.R
import org.zerokosh.app.data.AssetCatalog
import org.zerokosh.core.model.FieldType
import org.zerokosh.core.model.Record
import org.zerokosh.core.model.Sensitivity
// #endregion

// #region Categories
enum class RecordCategory(@StringRes val labelRes: Int) {
    BanksUpi(R.string.cat_banks),
    Cards(R.string.cat_cards),
    Investments(R.string.cat_investments),
    GovId(R.string.cat_govid),
    Utilities(R.string.cat_utilities),
    Apps(R.string.cat_apps),
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
    "travel_booking" to RecordCategory.Utilities,
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
    // Template order, not map order. `fields` is a plain Map, so its iteration
    // order is whatever order the editor happened to write the keys in — clear
    // a website, fill in a username, then retype the website and the URL lands
    // last. The row under the title then reads "••••••••••••" (a masked
    // username) instead of the site the record is for. Template order is the
    // order the user sees in the editor, which is the one they expect here.
    val ordered = (template?.fields?.map { it.k }.orEmpty() + record.fields.keys).distinct()
    val key = ordered.firstOrNull { k ->
        val field = template?.fields?.firstOrNull { it.k == k }
        record.fields[k].orEmpty().isNotBlank() &&
            (field?.sensitivity ?: Sensitivity.L) != Sensitivity.H &&
            // A LINK field holds the linked record's uuid, so rendering it put
            // "30151a25-7f72-4e3b-..." under Google Pay instead of its UPI ID.
            // Skipped rather than resolved: the next identifying field is the
            // better line anyway, and resolving would need the whole vault body
            // here just to print a title.
            field?.type != FieldType.LINK
    } ?: return record.institution
    val field = template?.fields?.firstOrNull { it.k == key }
    return maskedValue(record.fields.getValue(key), field, revealed = false)
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
@Composable
fun relativeTime(millis: Long, nowMs: Long = System.currentTimeMillis()): String {
    val delta = (nowMs - millis).coerceAtLeast(0)
    val minutes = delta / 60_000
    val hours = minutes / 60
    val days = hours / 24
    return when {
        minutes < 1 -> stringResource(R.string.time_just_now)
        minutes < 60 -> stringResource(R.string.time_minutes, minutes.toInt())
        hours < 24 -> stringResource(R.string.time_hours, hours.toInt())
        days < 30 -> stringResource(R.string.time_days, days.toInt())
        days < 365 -> stringResource(R.string.time_months, (days / 30).toInt())
        else -> stringResource(R.string.time_years, (days / 365).toInt())
    }
}
// #endregion
