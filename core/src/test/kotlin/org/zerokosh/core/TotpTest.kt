package org.zerokosh.core

// #region Imports
import org.zerokosh.core.totp.Totp
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
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
}
