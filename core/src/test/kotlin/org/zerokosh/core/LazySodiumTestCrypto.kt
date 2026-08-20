package org.zerokosh.core

// #region Imports
import com.goterl.lazysodium.LazySodiumJava
import com.goterl.lazysodium.SodiumJava
import com.goterl.lazysodium.interfaces.PwHash
import com.sun.jna.NativeLong
import org.zerokosh.core.crypto.CryptoProvider
// #endregion

/**
 * JVM CryptoProvider for the conformance suite — lazysodium-java (test-only
 * dependency, DECISIONS.md D-001). Mirrors the LazySodiumAndroid impl in :app.
 */
class LazySodiumTestCrypto : CryptoProvider {

    private val ls = LazySodiumJava(SodiumJava())

    override fun argon2id(password: ByteArray, salt: ByteArray, opsLimit: Long, memLimitBytes: Long): ByteArray {
        val out = ByteArray(CryptoProvider.KEY_BYTES)
        val ok = ls.cryptoPwHash(
            out, out.size, password, password.size, salt,
            opsLimit, NativeLong(memLimitBytes), PwHash.Alg.PWHASH_ALG_ARGON2ID13,
        )
        check(ok) { "crypto_pwhash failed" }
        return out
    }

    override fun aeadEncrypt(plaintext: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray {
        val ct = ByteArray(plaintext.size + CryptoProvider.TAG_BYTES)
        val ok = ls.cryptoAeadXChaCha20Poly1305IetfEncrypt(
            ct, null, plaintext, plaintext.size.toLong(), aad, aad.size.toLong(), null, nonce, key,
        )
        check(ok) { "aead encrypt failed" }
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

    companion object {
        val INSTANCE by lazy { LazySodiumTestCrypto() }
    }
}

fun ByteArray.toHex(): String = joinToString("") { "%02x".format(it) }

fun String.hexToBytes(): ByteArray {
    check(length % 2 == 0)
    return ByteArray(length / 2) { substring(it * 2, it * 2 + 2).toInt(16).toByte() }
}
