/**
 * @file Presets.kt
 * @description Seeding a new record from the brand the user already picked.
 *
 *              Choosing "ICICI Bank" in the template gallery and then being
 *              asked to find ICICI Bank again in an 80-row picker is asking the
 *              same question twice. Anything the gallery choice already answers
 *              gets filled in.
 *
 * [TABLE OF CONTENTS]
 * 1. SEEDING
 * 2. WHOLE-WORD CONTAINMENT
 */
package org.zerokosh.core.model

// #region Seeding
/**
 * Values to pre-fill on a new record created from a gallery brand.
 *
 * The rule is deliberately narrow: a picker field is filled only when the
 * brand is one of *that picker's own options*, either outright ("ICICI Bank"
 * in `banks`) or as a whole word inside it ("Credit" in "HDFC Credit Card").
 * Nothing is inferred beyond that — a wrong bank sitting in someone's vault is
 * worse than an empty field they fill themselves.
 *
 * The stored value is always the picker's spelling, never the gallery's. The
 * catalog says "YES Bank" where the picker says "Yes Bank", and a value the
 * picker cannot match would render as "Other".
 */
fun seedFieldsFromPreset(
    template: Template,
    preset: String,
    pickers: Map<String, List<String>>,
): Map<String, String> = buildMap {
    val needle = preset.lowercase()
    for (field in template.fields) {
        if (field.type != FieldType.PICKER) continue
        val options = pickers[field.typeParam] ?: continue
        val match = options.firstOrNull { it.equals(preset, ignoreCase = true) }
            ?: options.firstOrNull { containsWord(needle, it.lowercase()) }
        if (match != null) put(field.k, match)
    }
}
// #endregion

// #region Whole-word containment
/**
 * Whole-word containment. Plain `contains` would read "Credit" out of
 * "Creditor" and "Vi" out of "Vivo"; the boundary check is what makes filling
 * from a brand name safe rather than a guess.
 */
private fun containsWord(haystack: String, word: String): Boolean {
    if (word.isEmpty()) return false
    var from = 0
    while (from <= haystack.length - word.length) {
        val at = haystack.indexOf(word, from)
        if (at < 0) return false
        val startsClean = at == 0 || !haystack[at - 1].isLetterOrDigit()
        val end = at + word.length
        val endsClean = end == haystack.length || !haystack[end].isLetterOrDigit()
        if (startsClean && endsClean) return true
        from = at + 1
    }
    return false
}
// #endregion
