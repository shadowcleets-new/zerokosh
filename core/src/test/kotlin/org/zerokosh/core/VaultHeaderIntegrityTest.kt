/**
 * @file VaultHeaderIntegrityTest.kt
 * @description The header arrives unauthenticated; what the format refuses.
 *
 * The body's AEAD authenticates the 60-byte binary prefix, not the header JSON,
 * and body_sha256 is an unkeyed hash anyone can recompute. So whoever can write
 * the file — a synced folder, a stolen backup — can rewrite the KDF parameters.
 * Lowering them makes the real passphrase fail, which walks the user into the
 * fingerprint reset, and the re-wrap would carry the weakened parameters on.
 */
package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import org.zerokosh.core.crypto.CryptoProvider
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.core.vault.VaultFileCodec
import org.zerokosh.core.vault.VaultFormatException
import org.zerokosh.core.vault.VaultOperations

class VaultHeaderIntegrityTest {

    private val crypto = LazySodiumTestCrypto.INSTANCE
    private val passphrase = "correct-horse-battery-staple".toByteArray()

    private fun vault(ops: Long, mem: Long) = VaultOperations.createVault(
        passphrase = passphrase.copyOf(),
        deviceId = "test-device",
        nowMs = 1_700_000_000_000L,
        crypto = crypto,
        ops = ops,
        memBytes = mem,
    ).fileBytes

    @Test
    fun `a vault at the floor still opens`() {
        val file = vault(CryptoProvider.MIN_OPS, CryptoProvider.MIN_MEM_BYTES)
        assertTrue(VaultOperations.unlockWithPassphrase(file, passphrase.copyOf(), crypto) is UnlockResult.Success)
    }

    @Test
    fun `memory below the floor is refused rather than opened`() {
        val file = vault(CryptoProvider.MIN_OPS, CryptoProvider.MIN_MEM_BYTES / 4)
        assertFailsWith<VaultFormatException> { VaultFileCodec.decode(file) }
        assertEquals(UnlockResult.Corrupt, VaultOperations.unlockWithPassphrase(file, passphrase.copyOf(), crypto))
    }

    @Test
    fun `too few passes is refused rather than opened`() {
        val file = vault(1L, CryptoProvider.MIN_MEM_BYTES)
        assertFailsWith<VaultFormatException> { VaultFileCodec.decode(file) }
    }

    /**
     * A damaged header field used to throw IllegalArgumentException straight out
     * of unlock, past the one place that can say "damaged file" rather than
     * "wrong passphrase". Same length in, so header_len still matches.
     */
    @Test
    fun `a damaged salt reports a damaged file instead of throwing`() {
        val file = vault(CryptoProvider.MIN_OPS, CryptoProvider.MIN_MEM_BYTES)
        val text = file.decodeToString(0, minOf(file.size, 2048))
        val marker = "\"salt_mk\":\""
        val start = text.indexOf(marker) + marker.length
        val end = text.indexOf('"', start)
        val damaged = file.copyOf()
        for (i in start until end) damaged[i] = '!'.code.toByte() // not base64, same length
        assertEquals(UnlockResult.Corrupt, VaultOperations.unlockWithPassphrase(damaged, passphrase.copyOf(), crypto))
    }
}
