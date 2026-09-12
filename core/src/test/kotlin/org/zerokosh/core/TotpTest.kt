package org.zerokosh.core

// #region Imports
import org.zerokosh.core.totp.Totp
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull
// #endregion

class TotpTest {

    /** RFC 6238 Appendix B, SHA-1 rows (§5.8 MUST pass). Secret = ASCII "12345678901234567890", 8 digits. */
    @Test
    fun `rfc 6238 appendix b sha1 vectors`() {
        val secret = "12345678901234567890".toByteArray()
        val vectors = mapOf(
            59L to "94287082",
            1111111109L to "07081804",
            1111111111L to "14050471",
            1234567890L to "89005924",
            2000000000L to "69279037",
            20000000000L to "65353130",
        )
        for ((timeSec, expected) in vectors) {
            assertEquals(expected, Totp.hotp(secret, timeSec / 30, 8, "SHA1"), "T=$timeSec")
        }
    }

    @Test
    fun `base32 decode rfc4648`() {
        assertContentEquals(
            "12345678901234567890".toByteArray(),
            Totp.base32Decode("GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ"),
        )
        // lowercase, spaces and padding tolerated
        assertContentEquals("f".toByteArray(), Totp.base32Decode("my======"))
    }

    @Test
    fun `otpauth uri parsing honors algorithm digits period`() {
        val p = Totp.parseOtpauthUri(
            "otpauth://totp/Zerodha:AB1234?secret=GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ&issuer=Zerodha&algorithm=SHA256&digits=8&period=60",
        )!!
        assertEquals("Zerodha:AB1234", p.label)
        assertEquals("Zerodha", p.issuer)
        assertEquals("SHA256", p.algorithm)
        assertEquals(8, p.digits)
        assertEquals(60, p.periodSeconds)
    }

    @Test
    fun `otpauth defaults are sha1 6 digits 30s`() {
        val p = Totp.parseOtpauthUri("otpauth://totp/Test?secret=GEZDGNBV")!!
        assertEquals("SHA1", p.algorithm)
        assertEquals(6, p.digits)
        assertEquals(30, p.periodSeconds)
        // 6-digit code via the full param path matches an RFC vector prefix-truncated calc
        val code = Totp.code(p.copy(secretBase32 = "GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ"), 59_000L)
        assertEquals("287082", code) // last 6 of RFC's 8-digit 94287082
    }

    @Test
    fun `invalid uris reject`() {
        assertNull(Totp.parseOtpauthUri("otpauth://hotp/x?secret=GEZDGNBV"))
        assertNull(Totp.parseOtpauthUri("https://example.com"))
        assertNull(Totp.parseOtpauthUri("otpauth://totp/NoSecret?issuer=x"))
        assertNull(Totp.parseOtpauthUri("otpauth://totp/Bad?secret=11111111")) // '1' not in RFC4648 base32
    }

    /**
     * The chain that crashed the authenticator screen: a counter-based QR was
     * accepted by the scanner, could not be parsed as TOTP, and was then stored
     * whole as if the URI itself were the secret. Asking for a code decoded
     * "otpauth://hotp/..." as base32 and threw — during composition, on the one
     * screen that lists every code, so one bad record took all of them out.
     */
    @Test
    fun `a value that cannot make a code is refused rather than wrapped`() {
        assertNull(Totp.paramsOrNull("otpauth://hotp/ACME?secret=JBSWY3DPEHPK3PXP&counter=1"))
        assertNull(Totp.paramsOrNull("otpauth://totp/NoQuery"))
        assertNull(Totp.paramsOrNull("otpauth-migration://offline?data=CjEKCkhlbGxvId6tvu8"))
        assertNull(Totp.paramsOrNull("https://example.com"))
        assertNull(Totp.paramsOrNull(""))
        assertNull(Totp.paramsOrNull("   "))
        // Decodes to zero bytes, and Mac.init rejects an empty key.
        assertNull(Totp.paramsOrNull("A"))
        assertNull(Totp.paramsOrNull("="))
        assertNull(Totp.parseOtpauthUri("otpauth://totp/Empty?secret=A"))
    }

    /** Executable proof of what the old fallback did, so nobody restores it. */
    @Test
    fun `wrapping an unparsed URI as a secret throws when a code is asked for`() {
        val asTheScreenUsedToBuildIt =
            Totp.Params(secretBase32 = "otpauth://hotp/ACME?secret=JBSWY3DPEHPK3PXP&counter=1")
        assertFailsWith<IllegalArgumentException> { Totp.code(asTheScreenUsedToBuildIt, 0L) }
    }

    @Test
    fun `what can make a code is accepted, URI or bare secret`() {
        val uri = Totp.paramsOrNull("otpauth://totp/Zoho:me?secret=JBSWY3DPEHPK3PXP&issuer=Zoho")
        assertEquals("JBSWY3DPEHPK3PXP", uri?.secretBase32)
        assertEquals("Zoho", uri?.issuer)
        val bare = Totp.paramsOrNull("jbswy3dpehpk3pxp", label = "Typed by hand")
        assertEquals("jbswy3dpehpk3pxp", bare?.secretBase32)
        assertEquals("Typed by hand", bare?.label)
        // Every accepted value can actually produce a code.
        for (p in listOfNotNull(uri, bare)) {
            assertEquals(6, Totp.code(p, 1_789_000_000_000L).length)
        }
    }
}
