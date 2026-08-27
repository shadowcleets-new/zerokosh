/**
 * @file PassphraseSuggestions.kt
 * @description Fresh example passphrases for the S4 "TRY" chips (§2.4).
 *
 * The two examples used to be string literals, which made them the worst
 * possible suggestions: printed in a public repository, shipped identically to
 * every install, and therefore the first entries in any wordlist an attacker
 * would build against this app. Anyone who typed one in had a passphrase that
 * was already written down. These are generated per screen instead.
 *
 * SecureRandom, not Random.Default: a generator whose output a user may type
 * into a password field is a cryptographic generator, whatever its label says.
 * This project already deleted one non-cryptographic password generator for
 * exactly that reason.
 */
package org.zerokosh.core.passphrase

import java.security.SecureRandom

object PassphraseSuggestions {

    /**
     * Everyday, concrete, easy to picture — the properties that make a phrase
     * stick without being guessable. Deliberately no proper nouns, no brands and
     * nothing that reads like an answer to a security question.
     */
    private val WORDS = listOf(
        "almond", "anchor", "bangle", "banyan", "basil", "brass", "cardamom",
        "chai", "coconut", "cumin", "dosa", "ember", "fennel", "ferry",
        "ginger", "granite", "harbour", "hibiscus", "indigo", "ivory",
        "jaggery", "jasmine", "jute", "kettle", "kite", "kohl", "lantern",
        "ledger", "lotus", "lychee", "mango", "marigold", "mirror", "monsoon",
        "mustard", "neem", "nutmeg", "opal", "orchid", "otter", "palm",
        "papaya", "peepal", "pepper", "quartz", "quilt", "quiver", "raga",
        "rickshaw", "river", "saffron", "sandal", "sitar", "tabla", "tamarind",
        "teak", "tiger", "turmeric", "umbrella", "urn", "veena", "velvet",
        "vetiver", "walnut", "wicket", "willow", "yak", "zari", "zebra",
    )

    private val rng = SecureRandom()

    /** Distinct words per phrase, so "chai chai chai" can never be suggested. */
    private fun phrase(words: Int): String {
        val picked = LinkedHashSet<String>()
        while (picked.size < words) picked.add(WORDS[rng.nextInt(WORDS.size)])
        return picked.joinToString(" ")
    }

    /**
     * `count` distinct suggestions. One carries a bare digit, mirroring the
     * "not a single dictionary word" check the screen asks the user to satisfy —
     * the examples should demonstrate the rule, not just sit beside it.
     *
     * With 69 words there are ~300k three-word combinations, so a repeat between
     * two screen visits is possible but vanishingly unlikely; the guarantee made
     * here is only that the suggestions shown together are never equal.
     */
    fun suggest(count: Int = 2, words: Int = 3): List<String> {
        require(count >= 1) { "count must be positive" }
        require(words in 2..WORDS.size) { "words out of range" }
        val out = LinkedHashSet<String>()
        while (out.size < count) {
            val base = phrase(words)
            out.add(
                if (out.size == 1) {
                    // Drop a digit between two of the words rather than gluing it
                    // to one, so the example stays readable and typeable.
                    val parts = base.split(" ").toMutableList()
                    parts.add(1, rng.nextInt(10).toString())
                    parts.joinToString(" ")
                } else {
                    base
                },
            )
        }
        return out.toList()
    }
}
