/**
 * @file VaultHealth.kt
 * @description Cross-record review: reuse, weakness and expiry (§5.12).
 *
 * Every per-record check this app had already existed — strength scoring lives
 * on the create screen, expiry dates drive ReminderWorker. What nothing did was
 * look *across* records, which is where the findings that actually matter live:
 * the same password on a bank and a shopping site is invisible to any check that
 * only ever sees one record at a time.
 *
 * Pure and in core so it can be tested, and so it never touches a file, a clock
 * or a network. `nowMs` is a parameter for the same reason.
 */
package org.zerokosh.core.health

import org.zerokosh.core.model.Record
import org.zerokosh.core.model.Sensitivity
import org.zerokosh.core.model.Template
import org.zerokosh.core.passphrase.CommonPasswords
import org.zerokosh.core.passphrase.isWeakPinPattern

enum class HealthKind {
    /** The same secret stored on more than one record. */
    REUSED,

    /** In the small set an attacker tries first. */
    COMMON,

    /** Short enough to brute-force, or a PIN that is a run or a repeat. */
    WEAK,

    /** A date field already past, or within the warning window. */
    EXPIRING,
}

data class HealthFinding(
    val kind: HealthKind,
    val recordUuid: String,
    val recordTitle: String,
    val fieldKey: String,
    /** Human-facing specifics: how many records share it, when it expires. */
    val detail: String = "",
) {
    /** Sort key — a reused bank password matters more than a stale membership. */
    val severity: Int
        get() = when (kind) {
            HealthKind.REUSED -> 0
            HealthKind.COMMON -> 1
            HealthKind.WEAK -> 2
            HealthKind.EXPIRING -> 3
        }
}

/** Dates the app already warns about via reminders, reused here. */
private val EXPIRY_KEYS =
    setOf("expiry", "premium_due_date", "membership_renewal", "renewal_date", "maturity_date")

/** `YYYY-MM` or `YYYY-MM-DD`, which is how this app stores every date. */
private val DATE_RE = Regex("^(\\d{4})-(\\d{2})(?:-(\\d{2}))?$")

/** A PIN field is scored as a PIN; everything else on length and commonness. */
private val PIN_KEYS = setOf("atm_pin", "upi_pin", "tpin", "card_pin", "pin")

/**
 * @param templates keyed by `template_id`; a record whose template is unknown is
 *        skipped rather than guessed at, since without it there is no way to
 *        tell a password from a nickname.
 * @param withinDays how far ahead an expiry counts as a finding.
 */
fun reviewVault(
    records: List<Record>,
    templates: Map<String, Template>,
    nowMs: Long,
    withinDays: Int = 45,
): List<HealthFinding> {
    val findings = mutableListOf<HealthFinding>()

    // Reuse is counted over high-sensitivity values only. Counting every field
    // would report "both cards expire in 2030" as password reuse.
    val secretOwners = mutableMapOf<String, MutableList<Record>>()

    for (record in records) {
        val template = templates[record.template_id] ?: continue
        for (field in template.fields) {
            val value = record.fields[field.k]?.trim().orEmpty()
            if (value.isEmpty()) continue

            if (field.sensitivity == Sensitivity.H) {
                secretOwners.getOrPut(value) { mutableListOf() }.add(record)

                if (CommonPasswords.isCommon(value)) {
                    findings += HealthFinding(
                        HealthKind.COMMON, record.uuid, record.title, field.k,
                        "guessed in the first few thousand tries",
                    )
                } else if (field.k in PIN_KEYS) {
                    if (isWeakPinPattern(value)) {
                        findings += HealthFinding(
                            HealthKind.WEAK, record.uuid, record.title, field.k,
                            "a run or a repeated pattern",
                        )
                    }
                } else if (value.length < 10) {
                    findings += HealthFinding(
                        HealthKind.WEAK, record.uuid, record.title, field.k,
                        "${value.length} characters",
                    )
                }
            }

            if (field.k in EXPIRY_KEYS) {
                expiryFinding(record, field.k, value, nowMs, withinDays)?.let(findings::add)
            }
        }
    }

    for ((_, owners) in secretOwners) {
        if (owners.size < 2) continue
        for (record in owners) {
            findings += HealthFinding(
                HealthKind.REUSED, record.uuid, record.title, "",
                "shared with ${owners.size - 1} other record${if (owners.size > 2) "s" else ""}",
            )
        }
    }

    return findings.sortedWith(compareBy({ it.severity }, { it.recordTitle }))
}

private const val DAY_MS = 86_400_000L

private fun expiryFinding(
    record: Record,
    key: String,
    value: String,
    nowMs: Long,
    withinDays: Int,
): HealthFinding? {
    val m = DATE_RE.matchEntire(value) ?: return null
    val year = m.groupValues[1].toInt()
    val month = m.groupValues[2].toInt()
    if (month !in 1..12) return null
    val day = m.groupValues[3].toIntOrNull() ?: lastDayOf(year, month)

    val dueMs = utcMillis(year, month, day)
    val daysLeft = (dueMs - nowMs) / DAY_MS
    if (daysLeft > withinDays) return null
    return HealthFinding(
        HealthKind.EXPIRING, record.uuid, record.title, key,
        if (daysLeft < 0) "expired" else "in $daysLeft day${if (daysLeft == 1L) "" else "s"}",
    )
}

private fun lastDayOf(year: Int, month: Int): Int = when (month) {
    1, 3, 5, 7, 8, 10, 12 -> 31
    4, 6, 9, 11 -> 30
    else -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
}

/**
 * Days since the epoch by civil-date arithmetic (Howard Hinnant's algorithm),
 * so this stays a pure Kotlin function with no java.time dependency and the same
 * answer on every core.
 */
private fun utcMillis(year: Int, month: Int, day: Int): Long {
    val y = if (month <= 2) year - 1 else year
    val era = (if (y >= 0) y else y - 399) / 400
    val yoe = y - era * 400
    val mp = (month + 9) % 12
    val doy = (153 * mp + 2) / 5 + day - 1
    val doe = yoe * 365 + yoe / 4 - yoe / 100 + doy
    return (era * 146_097L + doe - 719_468L) * DAY_MS
}
