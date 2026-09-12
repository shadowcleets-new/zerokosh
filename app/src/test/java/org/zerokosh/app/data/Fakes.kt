/**
 * @file Fakes.kt
 * @description Test doubles for the three things VaultRepository depends on.
 *
 *              All three were Android types until now, which is the whole reason
 *              the app module's most important class had never been tested.
 */
package org.zerokosh.app.data

import org.zerokosh.core.crypto.CryptoProvider

/**
 * A store that can be told to fail, which is the point.
 *
 * The real SAF store throws in five places when a sync folder's permission grant
 * lapses, and no caller used to survive that. [failWrites] reproduces it without
 * needing a device, a folder or a revoked grant.
 */
class FakeVaultStore(private var content: ByteArray? = null) : VaultStore {

    var failWrites: Throwable? = null
    var writes = 0
    var backups = 0
    private var backup: ByteArray? = null

    override fun exists(): Boolean = content != null
    override fun read(): ByteArray? = content

    override fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean) {
        failWrites?.let { throw it }
        check(verify(bytes)) { "verify rejected the candidate" }
        content = bytes
        writes++
    }

    override fun backupCurrent() {
        backup = content
        backups++
    }

    override fun conflictSiblings(): List<Pair<String, ByteArray>> = emptyList()
    override fun deleteSibling(name: String) = Unit
    override fun readBackup(): ByteArray? = backup
}

/** In-memory [VaultPrefs]; the real one needs a Context and a Keystore. */
class FakeVaultPrefs(override val deviceId: String = "test-device") : VaultPrefs {
    override var kdfOps: Long = 0
    override var kdfMem: Long = 0
    override var failedAttempts: Int = 0
    override var cooldownUntilMs: Long = 0
    override var cooldownSeconds: Int = 30
    override var syncFolderUri: String = ""
    override var secretIsPin: Boolean = false
}

/**
 * Deterministic, reversible, and emphatically not cryptography.
 *
 * The real primitives are already covered by :core against libsodium test
 * vectors. What these tests exercise is the repository's control flow — does a
 * failed write surface, does history get captured, does trash round-trip — and
 * for that a real Argon2id would add 64 MiB and several seconds per test while
 * proving nothing new. The tag is a real check, so tampering still fails.
 */
class FakeCrypto : CryptoProvider {

    private var counter = 0

    override fun argon2id(password: ByteArray, salt: ByteArray, opsLimit: Long, memLimitBytes: Long): ByteArray =
        ByteArray(CryptoProvider.KEY_BYTES) { i ->
            (password.getOrElse(i % password.size.coerceAtLeast(1)) { 0 } + salt.getOrElse(i % salt.size.coerceAtLeast(1)) { 0 } + i).toByte()
        }

    private fun keystream(nonce: ByteArray, key: ByteArray, size: Int) =
        ByteArray(size) { i -> (key[i % key.size] + nonce[i % nonce.size] + i).toByte() }

    private fun tag(body: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray) =
        ByteArray(CryptoProvider.TAG_BYTES) { i ->
            var acc = key[i % key.size] + nonce[i % nonce.size]
            for (b in body) acc = acc * 31 + b
            for (b in aad) acc = acc * 17 + b
            acc.toByte()
        }

    override fun aeadEncrypt(plaintext: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray {
        val stream = keystream(nonce, key, plaintext.size)
        val body = ByteArray(plaintext.size) { (plaintext[it].toInt() xor stream[it].toInt()).toByte() }
        return body + tag(body, aad, nonce, key)
    }

    override fun aeadDecrypt(ciphertextAndTag: ByteArray, aad: ByteArray, nonce: ByteArray, key: ByteArray): ByteArray? {
        if (ciphertextAndTag.size < CryptoProvider.TAG_BYTES) return null
        val body = ciphertextAndTag.copyOfRange(0, ciphertextAndTag.size - CryptoProvider.TAG_BYTES)
        val got = ciphertextAndTag.copyOfRange(body.size, ciphertextAndTag.size)
        if (!got.contentEquals(tag(body, aad, nonce, key))) return null
        val stream = keystream(nonce, key, body.size)
        return ByteArray(body.size) { (body[it].toInt() xor stream[it].toInt()).toByte() }
    }

    /** Deterministic so a failing test reproduces exactly. */
    override fun randomBytes(count: Int): ByteArray = ByteArray(count) { (counter++ % 251).toByte() }
}
