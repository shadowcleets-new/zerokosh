package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.zerokosh.core.search.matchesPickerQuery

class PickerMatchTest {

    @Test
    fun `substring anywhere in the name`() {
        assertTrue(matchesPickerQuery("Kotak Mahindra Bank", "mahindra"))
        assertTrue(matchesPickerQuery("Kotak Mahindra Bank", "KOTAK"))
    }

    @Test
    fun `acronym with the joiners dropped`() {
        // the whole point: nobody types "State Bank of India" to find SBI
        assertTrue(matchesPickerQuery("State Bank of India", "sbi"))
        assertTrue(matchesPickerQuery("Punjab National Bank", "pnb"))
    }

    @Test
    fun `acronym with the joiners kept`() {
        assertTrue(matchesPickerQuery("Bank of Baroda", "bob"))
        assertTrue(matchesPickerQuery("Bank of India", "boi"))
    }

    @Test
    fun `a partial acronym still narrows`() {
        assertTrue(matchesPickerQuery("State Bank of India", "sb"))
    }

    @Test
    fun `an acronym does not match a different bank`() {
        assertFalse(matchesPickerQuery("Bank of Baroda", "boi"))
        assertFalse(matchesPickerQuery("Canara Bank", "sbi"))
    }

    @Test
    fun `a single word never matches by initial`() {
        // "z" must not surface Zerodha, LIC and Vi all at once
        assertFalse(matchesPickerQuery("Zerodha", "zg"))
        assertTrue(matchesPickerQuery("Zerodha", "z"))
    }

    @Test
    fun `blank query keeps everything`() {
        assertTrue(matchesPickerQuery("Yes Bank", ""))
        assertTrue(matchesPickerQuery("Yes Bank", "   "))
    }

    @Test
    fun `whitespace around the query is ignored`() {
        assertTrue(matchesPickerQuery("Yes Bank", "  yes "))
    }
}
