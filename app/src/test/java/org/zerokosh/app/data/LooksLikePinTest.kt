/**
 * @file LooksLikePinTest.kt
 * @description What counts as a PIN, in both forms the app asks the question.
 */
package org.zerokosh.app.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class LooksLikePinTest {

    @Test
    fun `six digits and nothing else`() {
        assertTrue(looksLikePin("241000"))
        assertFalse(looksLikePin("24100"))      // five
        assertFalse(looksLikePin("2410000"))    // seven
        assertFalse(looksLikePin("24100a"))
        assertFalse(looksLikePin("correct horse battery staple"))
        assertFalse(looksLikePin(""))
    }

    /** The byte form must agree, and must not be fooled by digits elsewhere. */
    @Test
    fun `the byte form agrees with the text form`() {
        for (s in listOf("241000", "24100", "24100a", "abcdef", "", "correct horse")) {
            assertEquals(looksLikePin(s), looksLikePin(s.toByteArray()))
        }
        assertTrue(looksLikePin("000000".toByteArray()))
        assertFalse(looksLikePin("12345/".toByteArray()))  // '/' is 0x2F, just below '0'
        assertFalse(looksLikePin("12345:".toByteArray()))  // ':' is 0x3A, just above '9'
    }
}
