package org.zerokosh.core

import org.zerokosh.core.passphrase.isWeakPinPattern
import org.zerokosh.core.passphrase.pinScore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PinStrengthTest {

    @Test
    fun `repeated blocks are caught at every block size`() {
        // block 1, 2 and 3 respectively — one loop covers all three.
        for (pin in listOf("000000", "111111", "121212", "123123", "454545", "987987")) {
            assertTrue(isWeakPinPattern(pin), "$pin should be weak")
        }
    }

    @Test
    fun `runs in both directions are caught`() {
        for (pin in listOf("123456", "654321", "234567", "876543")) {
            assertTrue(isWeakPinPattern(pin), "$pin should be weak")
        }
    }

    @Test
    fun `an ordinary pin is not flagged`() {
        for (pin in listOf("419372", "805164", "273908", "640517")) {
            assertFalse(isWeakPinPattern(pin), "$pin should not be weak")
        }
    }

    @Test
    fun `short or non-numeric input scores zero`() {
        for (pin in listOf("", "1", "12345", "12345a", "abcdef")) {
            assertEquals(0, pinScore(pin), "'$pin' should be unusable")
        }
    }

    @Test
    fun `weak patterns score one and ordinary pins score two`() {
        assertEquals(1, pinScore("123456"))
        assertEquals(1, pinScore("000000"))
        assertEquals(2, pinScore("419372"))
    }

    /**
     * The ceiling matters as much as the floor: six digits is ~19.9 bits, and a
     * meter that ever called that "strong" beside a passphrase would be lying
     * about the choice being made on that screen.
     */
    @Test
    fun `no pin ever scores above two`() {
        for (n in 0 until 100_000) {
            val pin = n.toString().padStart(6, '7')
            assertTrue(pinScore(pin) <= 2, "$pin scored above the ceiling")
        }
    }
}
