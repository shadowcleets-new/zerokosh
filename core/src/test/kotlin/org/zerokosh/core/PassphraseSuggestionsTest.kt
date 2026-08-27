package org.zerokosh.core

import org.zerokosh.core.passphrase.PassphraseSuggestions
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PassphraseSuggestionsTest {

    @Test
    fun `suggestions shown together are never equal`() {
        repeat(500) {
            val s = PassphraseSuggestions.suggest()
            assertEquals(2, s.size)
            assertEquals(2, s.toSet().size, "duplicate suggestions: $s")
        }
    }

    @Test
    fun `words within a phrase never repeat`() {
        repeat(500) {
            for (s in PassphraseSuggestions.suggest(words = 4)) {
                val words = s.split(" ").filter { w -> w.any(Char::isLetter) }
                assertEquals(words.size, words.toSet().size, "repeated word in: $s")
            }
        }
    }

    @Test
    fun `one suggestion carries a digit so the examples satisfy the screen's own rule`() {
        repeat(200) {
            val s = PassphraseSuggestions.suggest()
            assertEquals(1, s.count { p -> p.any(Char::isDigit) }, "exactly one digit example: $s")
        }
    }

    @Test
    fun `every suggestion clears the ten-character bar the screen enforces`() {
        repeat(200) {
            for (s in PassphraseSuggestions.suggest()) {
                assertTrue(s.length >= 10, "too short to be usable advice: '$s'")
            }
        }
    }

    /**
     * The whole point of the change: the output must not be a fixed pair. 200
     * draws collapsing to one value would mean the generator is not generating.
     */
    @Test
    fun `output varies across calls`() {
        val seen = (1..200).map { PassphraseSuggestions.suggest().first() }.toSet()
        assertTrue(seen.size > 150, "suspiciously little variation: ${seen.size} distinct of 200")
    }
}
