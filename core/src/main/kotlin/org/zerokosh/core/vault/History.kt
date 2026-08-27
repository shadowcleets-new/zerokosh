/**
 * @file History.kt
 * @description Previous values of secret fields (§5.13).
 *
 * The scenario is common enough to be worth the bytes: you rotate a password,
 * the site did not actually take it, and the value you just replaced is gone
 * for good — this app has no reset link by design, so "gone" is final.
 *
 * Only high-sensitivity fields are kept. Keeping every field would turn the
 * vault into an edit log of names and notes, which is a privacy cost with no
 * recovery benefit.
 */
package org.zerokosh.core.vault

import org.zerokosh.core.model.PastSecret
import org.zerokosh.core.model.Record

/** Per field. Deep enough for a bad rotation, shallow enough to stay bounded. */
const val HISTORY_PER_FIELD: Int = 5

/**
 * Returns [next] with any secret it overwrote recorded in its history.
 *
 * @param previous the stored record, or null when this is a creation.
 * @param secretKeys field keys worth remembering — the caller derives these
 *        from the template, because a bare `Map<String, String>` cannot tell a
 *        password from a nickname.
 */
fun captureHistory(
    previous: Record?,
    next: Record,
    secretKeys: Set<String>,
    nowMs: Long,
    perField: Int = HISTORY_PER_FIELD,
): Record {
    if (previous == null) return next

    val added = mutableListOf<PastSecret>()
    for (key in secretKeys) {
        val before = previous.fields[key].orEmpty()
        val after = next.fields[key].orEmpty()
        // A cleared field is still a change worth remembering; an unchanged one
        // is not, or every unrelated edit would deepen the history.
        if (before.isNotEmpty() && before != after) {
            added += PastSecret(k = key, value = before, replaced_at = nowMs)
        }
    }
    if (added.isEmpty()) return next

    // Newest first, then capped per field rather than globally, so churn on one
    // password cannot evict the history of every other field.
    val combined = (added + previous.history + next.history.filterNot { it in previous.history })
        .distinct()
        .sortedByDescending { it.replaced_at }
    val capped = combined
        .groupBy { it.k }
        .flatMap { (_, entries) -> entries.take(perField) }
        .sortedByDescending { it.replaced_at }

    return next.copy(history = capped)
}

/** Drops every remembered secret — used when the user asks to forget them. */
fun Record.withoutHistory(): Record = if (history.isEmpty()) this else copy(history = emptyList())
