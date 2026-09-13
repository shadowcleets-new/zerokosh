/**
 * @file BrandNameTest.kt
 * @description The cases from a real imported vault, and the ones that must
 *              keep working exactly as they did.
 */
package org.zerokosh.app.ui.common

import kotlin.test.Test
import kotlin.test.assertEquals

class BrandNameTest {

    @Test
    fun `an imported URL title finds the brand behind it`() {
        assertEquals("google", brandOf("accounts.google.com").logoKey)
        assertEquals("accenture", brandOf("https://candidate.accenture.com/8c7f2a").logoKey)
        assertEquals("netflix", brandOf("www.netflix.com").logoKey)
        assertEquals("github", brandOf("https://github.com/login?next=/x").logoKey)
        // a port, credentials and a trailing path are all noise around the host
        assertEquals("example", brandOf("https://user:pw@shop.example.com:8443/cart").logoKey)
    }

    @Test
    fun `two-label public suffixes are not mistaken for the brand`() {
        assertEquals("sbi", brandOf("sbi.co.in").logoKey)
        assertEquals("hdfcbank", brandOf("netbanking.hdfcbank.co.in").logoKey)
        assertEquals("bbc", brandOf("bbc.co.uk").logoKey)
        // and a single-label suffix still works
        assertEquals("zomato", brandOf("www.zomato.com").logoKey)
    }

    @Test
    fun `monograms are initials, not the first four characters`() {
        assertEquals("GO", brandOf("accounts.google.com").monogram) // was ACCO
        assertEquals("AC", brandOf("accenture.com").monogram) // was ACCE
        assertEquals("TW", brandOf("TwoPaneCheck").monogram) // was TWOP
        assertEquals("HB", brandOf("HDFC Bank").monogram)
        assertEquals("SB", brandOf("State Bank of India").monogram)
    }

    /** An address names no brand; "HTTP" named the protocol. */
    @Test
    fun `a bare IP address claims no brand`() {
        val brand = brandOf("http://202.143.96.43:82/login.aspx")
        assertEquals("", brand.logoKey)
        assertEquals("20", brand.monogram)
    }

    /** Everything already in the vault must resolve exactly as before. */
    @Test
    fun `plain institution names are untouched`() {
        assertEquals("upi", brandOf("UPI").logoKey)
        assertEquals("ahmedabadmetro", brandOf("Ahmedabad Metro").logoKey)
        assertEquals("pancard", brandOf("PAN Card").logoKey)
        assertEquals("tatapower", brandOf("Tata Power").logoKey)
        // a dot inside a name that is not a host stays part of the name
        assertEquals("drreddys", brandOf("Dr. Reddy's").logoKey)
    }

    @Test
    fun `nothing at all does not crash the tile`() {
        assertEquals(Brand("", "?"), brandOf("   "))
    }
}
