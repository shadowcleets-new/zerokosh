package org.zerokosh.core

// #region Imports
import org.zerokosh.core.model.FieldValidation
import org.zerokosh.core.util.CardUtils
import org.zerokosh.core.util.CardUtils.Network
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
// #endregion

class CardAndValidationTest {

    // §11.1: Luhn table — 10 valid, 10 invalid.
    private val validCards = listOf(
        "4111111111111111", "4012888888881881", "5555555555554444", "5105105105105100",
        "378282246310005", "371449635398431", "6011111111111117", "30569309025904",
        "3566002020360505", "6011000990139424",
    )

    @Test
    fun `luhn accepts 10 known-valid numbers`() {
        validCards.forEach { assertTrue(CardUtils.luhnValid(it), it) }
    }

    @Test
    fun `luhn rejects 10 invalid numbers`() {
        validCards.map { it.dropLast(1) + ((it.last() - '0' + 1) % 10) }
            .forEach { assertFalse(CardUtils.luhnValid(it), it) }
    }

    @Test
    fun `network detection per §5-10 prefix table`() {
        assertEquals(Network.RUPAY, CardUtils.detectNetwork("6079834512345678"))
        assertEquals(Network.RUPAY, CardUtils.detectNetwork("6521111111111117"))
        assertEquals(Network.RUPAY, CardUtils.detectNetwork("5081231234567890"))
        assertEquals(Network.RUPAY, CardUtils.detectNetwork("8112345678901234"))
        assertEquals(Network.RUPAY, CardUtils.detectNetwork("8212345678901234"))
        assertEquals(Network.VISA, CardUtils.detectNetwork("4111111111111111"))
        assertEquals(Network.MASTERCARD, CardUtils.detectNetwork("5555555555554444"))
        assertEquals(Network.MASTERCARD, CardUtils.detectNetwork("2221000000000009"))
        assertEquals(Network.MASTERCARD, CardUtils.detectNetwork("2720990000000007"))
        assertEquals(Network.AMEX, CardUtils.detectNetwork("378282246310005"))
        assertEquals(Network.DINERS, CardUtils.detectNetwork("30569309025904"))
        assertEquals(Network.DINERS, CardUtils.detectNetwork("36148900647913"))
        assertEquals(Network.MAESTRO, CardUtils.detectNetwork("5018000000000009"))
        assertEquals(Network.MAESTRO, CardUtils.detectNetwork("5612345678901234"))
        assertEquals(null, CardUtils.detectNetwork("9999"))
    }

    // §11.1: IFSC regex.
    @Test
    fun `ifsc regex`() {
        listOf("SBIN0001234", "HDFC0000001", "ICIC0006543", "UTIB0ABC123").forEach {
            assertTrue(FieldValidation.IFSC_REGEX.matches(it), it)
        }
        listOf("SBIN1001234", "sbin0001234", "SBI00012345", "SBIN000123", "SBIN00012345", "1BIN0001234").forEach {
            assertFalse(FieldValidation.IFSC_REGEX.matches(it), it)
        }
    }
}
