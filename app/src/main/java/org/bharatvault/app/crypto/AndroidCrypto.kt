package org.bharatvault.app.crypto

// #region Imports
import com.goterl.lazysodium.LazySodiumAndroid
import com.goterl.lazysodium.SodiumAndroid
import com.goterl.lazysodium.interfaces.PwHash
import com.sun.jna.NativeLong
import org.bharatvault.core.crypto.CryptoProvider
// #endregion

/** §6.4: Lazysodium-android implementation of the :core crypto interface. */
class AndroidCrypto : CryptoProvider {

    private val ls = LazySodiumAndroid(SodiumAndroid())

    override fun argon2id(password: ByteArray, salt: ByteArray, opsLimit: Long, memLimitBytes: Long): ByteArray {
        val out = ByteArray(CryptoProvider.KEY_BYTES)
        val ok = ls.cryptoPwHash(
            out, out.size, password, password.size, salt,
            opsLimit, NativeLong(memLimitBytes), PwHash.Alg.PWHASH_ALG_ARGON2ID13,
        )
        check(ok) { "key derivation failed" } // no crypto detail leaks to UI (§3.3)
        return out
    }

    override fun aeadEncrypt(plaintext: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray {
        val ct = ByteArray(plaintext.size + CryptoProvider.TAG_BYTES)
        val ok = ls.cryptoAeadXChaCha20Poly1305IetfEncrypt(
            ct, null, plaintext, plaintext.size.toLong(), aad, aad.size.toLong(), null, nonce, key,
        )
        check(ok) { "encryption failed" }
        return ct
    }

    override fun aeadDecrypt(ciphertextAndTag: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray? {
        if (ciphertextAndTag.size < CryptoProvider.TAG_BYTES) return null
        val m = ByteArray(ciphertextAndTag.size - CryptoProvider.TAG_BYTES)
        val ok = ls.cryptoAeadXChaCha20Poly1305IetfDecrypt(
            m, null, null, ciphertextAndTag, ciphertextAndTag.size.toLong(), aad, aad.size.toLong(), nonce, key,
        )
        return if (ok) m else null
    }

    override fun randomBytes(count: Int): ByteArray = ls.randomBytesBuf(count)

    /**
     * §6.4/§3.2: pick KDF memlimit at first run — 64 MiB unless the device
     * can't allocate it, stepping down to the 32 MiB floor.
     */
    fun chooseKdfParams(): Pair<Long, Long> {
        val salt = randomBytes(CryptoProvider.SALT_BYTES)
        for (mem in longArrayOf(CryptoProvider.DEFAULT_MEM_BYTES, 50_331_648L, CryptoProvider.MIN_MEM_BYTES)) {
            try {
                argon2id("bench".toByteArray(), salt, CryptoProvider.DEFAULT_OPS, mem)
                return CryptoProvider.DEFAULT_OPS to mem
            } catch (_: Throwable) {
                // step down (§3.2)
            }
        }
        return CryptoProvider.DEFAULT_OPS to CryptoProvider.MIN_MEM_BYTES
    }
}
