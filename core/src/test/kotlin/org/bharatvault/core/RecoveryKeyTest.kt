package org.bharatvault.core

// #region Imports
import org.bharatvault.core.crypto.RecoveryKey
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
// #endregion

/** Appendix C: Crockford Base32 + checksum, format BVR-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX. */
class RecoveryKeyTest {

    @Test
    fun `encode format matches spec grouping`() {
        val encoded = RecoveryKey.encode(ByteArray(16))
        assertTrue(Regex("^BVR-[0-9A-Z]{5}-[0-9A-Z]{5}-[0-9A-Z]{5}-[0-9A-Z]{5}-[0-9A-Z]{5}-[0-9A-Z]{2}$").matches(encoded), encoded)
    }

    @Test
    fun `round-trip including checksum`() {
        val crypto = LazySodiumTestCrypto.INSTANCE
        repeat(50) {
            val raw = crypto.randomBytes(16)
            assertContentEquals(raw, RecoveryKey.decode(RecoveryKey.encode(raw)))
        }
    }

    @Test
    fun `decoder is case-insensitive and maps I L O`() {
        val raw = ByteArray(16) { it.toByte() }
        val encoded = RecoveryKey.encode(raw)
        assertContentEquals(raw, RecoveryKey.decode(encoded.lowercase()))
        // 1→I/L and 0→O substitutions must decode back
        val confused = encoded.replace('1', 'I').replace('0', 'O')
        assertContentEquals(raw, RecoveryKey.decode(confused))
    }

    @Test
    fun `checksum failure rejects`() {
        val encoded = RecoveryKey.encode(ByteArray(16) { 7 })
        val last = encoded.last()
        val tampered = encoded.dropLast(1) + (if (last == 'A') 'B' else 'A')
        assertNull(RecoveryKey.decode(tampered))
    }

    @Test
    fun `malformed inputs reject`() {
        assertNull(RecoveryKey.decode(""))
        assertNull(RecoveryKey.decode("BVR-SHORT"))
        assertNull(RecoveryKey.decode("BVR-UUUUU-UUUUU-UUUUU-UUUUU-UUUUU-UU")) // U not in Crockford
    }

    @Test
    fun `checksum is sum of raw bytes mod 32`() {
        val raw = ByteArray(16) { 1 } // sum = 16 -> 'G' (index 16)
        val encoded = RecoveryKey.encode(raw).replace("BVR-", "").replace("-", "")
        assertEquals('G', encoded[26])
    }
}
