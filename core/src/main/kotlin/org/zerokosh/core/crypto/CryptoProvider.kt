package org.zerokosh.core.crypto

/**
 * The only crypto surface :core is allowed to touch (§3.1–§3.2, §6.4).
 * Implementations MUST delegate to libsodium — Lazysodium-android in :app,
 * lazysodium-java in the JVM test suite. NEVER implement a primitive by hand.
 */
interface CryptoProvider {

    /** `crypto_pwhash` Argon2id13, 32-byte output (§3.2). */
    fun argon2id(password: ByteArray, salt: ByteArray, opsLimit: Long, memLimitBytes: Long): ByteArray

    /** `crypto_aead_xchacha20poly1305_ietf_encrypt`; returns ciphertext||tag. */
    fun aeadEncrypt(plaintext: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray

    /** `crypto_aead_xchacha20poly1305_ietf_decrypt`; null on authentication failure. */
    fun aeadDecrypt(ciphertextAndTag: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray?

    /** libsodium `randombytes_buf` — the ONLY random source for keys/salts/nonces/generator. */
    fun randomBytes(count: Int): ByteArray

    companion object {
        const val KEY_BYTES = 32
        const val NONCE_BYTES = 24
        const val TAG_BYTES = 16
        const val SALT_BYTES = 16
        const val DEFAULT_OPS = 3L
        const val DEFAULT_MEM_BYTES = 67_108_864L // 64 MiB
        const val MIN_MEM_BYTES = 33_554_432L // 32 MiB floor (§3.2)
    }
}

/** Zero a key buffer after use (§3.5). */
fun ByteArray.wipe() {
    fill(0)
}
