package org.bharatvault.core

// #region Imports
import org.bharatvault.core.vault.KeyHierarchy
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
// #endregion

/** §3.6 conformance vectors — MUST reproduce byte-for-byte on every platform. */
class ConformanceVectorsTest {

    private val crypto = LazySodiumTestCrypto.INSTANCE

    @Test
    fun `VECTOR A - Argon2id13`() {
        val derived = crypto.argon2id(
            password = "correct-horse-battery-staple".toByteArray(),
            salt = "000102030405060708090a0b0c0d0e0f".hexToBytes(),
            opsLimit = 3,
            memLimitBytes = 67_108_864,
        )
        assertEquals("9acedbbff8ce68cae902e239bdccd32578ccc20eb9054e679f9b8cf5a9f0e19a", derived.toHex())
    }

    @Test
    fun `VECTOR B - XChaCha20-Poly1305-IETF encrypt`() {
        val ct = crypto.aeadEncrypt(
            plaintext = "namaste india".toByteArray(),
            aad = "BVLT".toByteArray(),
            nonce = "000102030405060708090a0b0c0d0e0f1011121314151617".hexToBytes(),
            key = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f".hexToBytes(),
        )
        assertEquals("f0a3621ee3a6e88e5a2a42a7aa69c591ec4cf4b20dae58d36bcf83fdc8", ct.toHex())
    }

    @Test
    fun `VECTOR C - key wrap using VECTOR A output as MasterKey`() {
        val masterKey = crypto.argon2id(
            "correct-horse-battery-staple".toByteArray(),
            "000102030405060708090a0b0c0d0e0f".hexToBytes(),
            3, 67_108_864,
        )
        val wrapped = crypto.aeadEncrypt(
            plaintext = ByteArray(32) { 0xAA.toByte() },
            aad = KeyHierarchy.AAD_KEYWRAP,
            nonce = ByteArray(24) { 0x24 },
            key = masterKey,
        )
        assertEquals(
            "f68ef02b2231e71a1bf6088d69ecbbc5af74cc70df18e6bc11fb175216a9c21a522d9ddd9b237a446d680e306fd1426b",
            wrapped.toHex(),
        )
    }

    @Test
    fun `AEAD decrypt round-trips and rejects tampering`() {
        val key = crypto.randomBytes(32)
        val nonce = crypto.randomBytes(24)
        val aad = "BVLT".toByteArray()
        val ct = crypto.aeadEncrypt("hello".toByteArray(), aad, nonce, key)
        assertEquals("hello", crypto.aeadDecrypt(ct, aad, nonce, key)!!.decodeToString())
        ct[0] = (ct[0].toInt() xor 1).toByte()
        assertNull(crypto.aeadDecrypt(ct, aad, nonce, key))
    }
}
