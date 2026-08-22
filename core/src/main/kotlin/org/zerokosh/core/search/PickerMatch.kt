/**
 * @file PickerMatch.kt
 * @description Query matching for the picker sheet's search field.
 *
 *              Lives in core rather than next to the composable so it can be
 *              tested — it is pure string logic and the app module has no test
 *              source set.
 */
package org.zerokosh.core.search

private val JOINERS = setOf("of", "and", "the", "&")

/**
 * Substring match, plus the two acronyms people actually type: "sbi" for State
 * Bank of India (joiners dropped) and "bob" for Bank of Baroda (joiners kept).
 *
 * Deliberately not a fuzzy scorer. A user typing three letters wants the two or
 * three banks those letters name, not a ranked list of everything vaguely
 * similar — and a predictable filter is one they can learn.
 */
fun matchesPickerQuery(option: String, query: String): Boolean {
    if (query.isBlank()) return true
    val q = query.trim().lowercase()
    if (option.lowercase().contains(q)) return true
    val words = option.split(' ', '-', '/', '.').filter(String::isNotEmpty)
    if (words.size < 2) return false
    val withJoiners = words.map { it.first().lowercaseChar() }.joinToString("")
    val withoutJoiners = words.filter { it.lowercase() !in JOINERS }
        .map { it.first().lowercaseChar() }.joinToString("")
    return withJoiners.startsWith(q) || withoutJoiners.startsWith(q)
}
