/**
 * @file VaultFile.kt
 * @description `.kosh` vault file format v1 (§4) — byte-identical across
 *              platforms — plus the §3.3 key hierarchy operations.
 *
 * [TABLE OF CONTENTS]
 * 1. HEADER JSON TYPES (§4.2)
 * 2. BINARY CODEC (§4.1)
 * 3. KEY HIERARCHY (§3.3)
 * 4. VAULT OPERATIONS (create / unlock / save / change passphrase)
 */
package org.zerokosh.core.vault

// #region Imports
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import org.zerokosh.core.crypto.CryptoProvider
import org.zerokosh.core.crypto.RecoveryKey
import org.zerokosh.core.crypto.wipe
import org.zerokosh.core.model.VaultBody
import org.zerokosh.core.model.VaultJson
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.MessageDigest
import java.util.Base64
// #endregion

// #region Header JSON types (§4.2)
@Serializable
data class WrappedKeyJson(val nonce: String, val ct: String) // base64

@Serializable
data class VaultHeader(
    val kdf: String = "argon2id13",
    val ops: Long,
    val mem: Long,
    val salt_mk: String, // 16B base64
    val salt_rk: String, // 16B base64
    val wrap_mk: WrappedKeyJson,
    val wrap_rk: WrappedKeyJson,
    val created_at: Long,
    val writer_device: String,
    val body_sha256: String, // hex of body ciphertext
)
// #endregion

// #region Binary codec (§4.1)
class VaultFormatException(message: String) : Exception(message)

/** Parsed-but-still-encrypted vault file. */
class VaultEnvelope(
    val vaultUuid: ByteArray, // 16 bytes
    val lastModifiedMs: Long,
    val header: VaultHeader,
    val bodyNonce: ByteArray, // 24 bytes
    val bodyCiphertext: ByteArray, // ct||tag
    val prefix: ByteArray, // bytes [0..60) — the AEAD aad
)

object VaultFileCodec {
    private val MAGIC = "BVLT".toByteArray(Charsets.US_ASCII)
    private const val FORMAT_VERSION = 1
    private const val PREFIX_LEN = 60
    private const val MAX_HEADER_LEN = 4096
    private val B64 = Base64.getEncoder()

    fun sha256Hex(data: ByteArray): String =
        MessageDigest.getInstance("SHA-256").digest(data).joinToString("") { "%02x".format(it) }

    fun encode(
        vaultUuid: ByteArray,
        lastModifiedMs: Long,
        header: VaultHeader,
        bodyPlaintext: ByteArray,
        vaultKey: ByteArray,
        crypto: CryptoProvider,
    ): ByteArray {
        require(vaultUuid.size == 16)
        val bodyNonce = crypto.randomBytes(CryptoProvider.NONCE_BYTES)
        // body_sha256 is hex(SHA-256) — always 64 chars, so header length is
        // stable across the placeholder and the real value.
        val placeholder = header.copy(body_sha256 = "0".repeat(64))
        val headerLen = VaultJson.encodeToString(placeholder).toByteArray().size
        if (headerLen > MAX_HEADER_LEN) throw VaultFormatException("header too large: $headerLen")
        val prefix = buildPrefix(vaultUuid, lastModifiedMs, headerLen, bodyNonce)
        val ciphertext = crypto.aeadEncrypt(bodyPlaintext, prefix, bodyNonce, vaultKey)
        val headerBytes = VaultJson.encodeToString(header.copy(body_sha256 = sha256Hex(ciphertext))).toByteArray()
        check(headerBytes.size == headerLen) { "header length changed after sha fill-in" }
        return prefix + headerBytes + ciphertext
    }

    fun decode(fileBytes: ByteArray): VaultEnvelope {
        if (fileBytes.size < PREFIX_LEN) throw VaultFormatException("file too short")
        val buf = ByteBuffer.wrap(fileBytes).order(ByteOrder.LITTLE_ENDIAN)
        val magic = ByteArray(4).also { buf.get(it) }
        if (!magic.contentEquals(MAGIC)) throw VaultFormatException("bad magic")
        val version = buf.short.toInt() and 0xFFFF
        if (version != FORMAT_VERSION) throw VaultFormatException("unsupported version $version")
        buf.short // reserved
        val uuid = ByteArray(16).also { buf.get(it) }
        val lastModified = buf.long
        val headerLen = buf.int
        if (headerLen <= 0 || headerLen > MAX_HEADER_LEN) throw VaultFormatException("bad header_len $headerLen")
        val bodyNonce = ByteArray(CryptoProvider.NONCE_BYTES).also { buf.get(it) }
        if (fileBytes.size < PREFIX_LEN + headerLen + CryptoProvider.TAG_BYTES) {
            throw VaultFormatException("truncated file")
        }
        val headerJson = String(fileBytes, PREFIX_LEN, headerLen, Charsets.UTF_8)
        val header = try {
            VaultJson.decodeFromString<VaultHeader>(headerJson)
        } catch (e: Exception) {
            throw VaultFormatException("unreadable header")
        }
        val ciphertext = fileBytes.copyOfRange(PREFIX_LEN + headerLen, fileBytes.size)
        if (sha256Hex(ciphertext) != header.body_sha256) throw VaultFormatException("body checksum mismatch")
        return VaultEnvelope(
            vaultUuid = uuid,
            lastModifiedMs = lastModified,
            header = header,
            bodyNonce = bodyNonce,
            bodyCiphertext = ciphertext,
            prefix = fileBytes.copyOfRange(0, PREFIX_LEN),
        )
    }

    private fun buildPrefix(uuid: ByteArray, lastModifiedMs: Long, headerLen: Int, bodyNonce: ByteArray): ByteArray {
        val buf = ByteBuffer.allocate(PREFIX_LEN).order(ByteOrder.LITTLE_ENDIAN)
        buf.put(MAGIC)
        buf.putShort(FORMAT_VERSION.toShort())
        buf.putShort(0) // reserved
        buf.put(uuid)
        buf.putLong(lastModifiedMs)
        buf.putInt(headerLen)
        buf.put(bodyNonce)
        return buf.array()
    }

    fun b64(bytes: ByteArray): String = B64.encodeToString(bytes)
    fun unb64(s: String): ByteArray = Base64.getDecoder().decode(s)
}
// #endregion

// #region Key hierarchy (§3.3)
object KeyHierarchy {
    val AAD_KEYWRAP = "BVLT-KEYWRAP".toByteArray(Charsets.US_ASCII)

    fun deriveKey(secret: ByteArray, salt: ByteArray, ops: Long, memBytes: Long, crypto: CryptoProvider): ByteArray =
        crypto.argon2id(secret, salt, ops, memBytes)

    fun wrap(vaultKey: ByteArray, wrappingKey: ByteArray, crypto: CryptoProvider): WrappedKeyJson {
        val nonce = crypto.randomBytes(CryptoProvider.NONCE_BYTES)
        val ct = crypto.aeadEncrypt(vaultKey, AAD_KEYWRAP, nonce, wrappingKey)
        return WrappedKeyJson(nonce = VaultFileCodec.b64(nonce), ct = VaultFileCodec.b64(ct))
    }

    /** Null when the wrapping key is wrong ("Wrong passphrase" path, §3.3). */
    fun unwrap(wrapped: WrappedKeyJson, wrappingKey: ByteArray, crypto: CryptoProvider): ByteArray? =
        crypto.aeadDecrypt(
            VaultFileCodec.unb64(wrapped.ct),
            AAD_KEYWRAP,
            VaultFileCodec.unb64(wrapped.nonce),
            wrappingKey,
        )
}
// #endregion

// #region Vault operations
sealed class UnlockResult {
    class Success(val vaultKey: ByteArray, val body: VaultBody, val envelope: VaultEnvelope) : UnlockResult()

    /** UI MUST render exactly "Wrong passphrase" (§3.3) — never a crypto error string. */
    object WrongCredential : UnlockResult()
    object Corrupt : UnlockResult()
}

class CreatedVault(
    val fileBytes: ByteArray,
    val recoveryKeyFormatted: String, // shown ONCE at S4, never again
    val vaultUuid: ByteArray,
)

object VaultOperations {

    fun createVault(
        passphrase: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
        ops: Long = CryptoProvider.DEFAULT_OPS,
        memBytes: Long = CryptoProvider.DEFAULT_MEM_BYTES,
        initialBody: VaultBody = VaultBody(),
    ): CreatedVault {
        val saltMk = crypto.randomBytes(CryptoProvider.SALT_BYTES)
        val saltRk = crypto.randomBytes(CryptoProvider.SALT_BYTES)
        val vaultKey = crypto.randomBytes(CryptoProvider.KEY_BYTES)
        val recoveryRaw = crypto.randomBytes(RecoveryKey.RAW_BYTES)

        val masterKey = KeyHierarchy.deriveKey(passphrase, saltMk, ops, memBytes, crypto)
        val recoveryStretched = KeyHierarchy.deriveKey(recoveryRaw, saltRk, ops, memBytes, crypto)

        val header = VaultHeader(
            ops = ops,
            mem = memBytes,
            salt_mk = VaultFileCodec.b64(saltMk),
            salt_rk = VaultFileCodec.b64(saltRk),
            wrap_mk = KeyHierarchy.wrap(vaultKey, masterKey, crypto),
            wrap_rk = KeyHierarchy.wrap(vaultKey, recoveryStretched, crypto),
            created_at = nowMs,
            writer_device = deviceId,
            body_sha256 = "",
        )
        val vaultUuid = crypto.randomBytes(16)
        val bodyBytes = VaultJson.encodeToString(initialBody).toByteArray()
        val file = VaultFileCodec.encode(vaultUuid, nowMs, header, bodyBytes, vaultKey, crypto)

        masterKey.wipe()
        recoveryStretched.wipe()
        vaultKey.wipe()
        val formatted = RecoveryKey.encode(recoveryRaw)
        recoveryRaw.wipe()
        return CreatedVault(file, formatted, vaultUuid)
    }

    fun unlockWithPassphrase(fileBytes: ByteArray, passphrase: ByteArray, crypto: CryptoProvider): UnlockResult =
        unlock(fileBytes, crypto) { header ->
            val mk = KeyHierarchy.deriveKey(
                passphrase, VaultFileCodec.unb64(header.salt_mk), header.ops, header.mem, crypto,
            )
            val vk = KeyHierarchy.unwrap(header.wrap_mk, mk, crypto)
            mk.wipe()
            vk
        }

    fun unlockWithRecoveryKey(fileBytes: ByteArray, recoveryInput: String, crypto: CryptoProvider): UnlockResult {
        val raw = RecoveryKey.decode(recoveryInput) ?: return UnlockResult.WrongCredential
        return unlock(fileBytes, crypto) { header ->
            val rs = KeyHierarchy.deriveKey(
                raw, VaultFileCodec.unb64(header.salt_rk), header.ops, header.mem, crypto,
            )
            val vk = KeyHierarchy.unwrap(header.wrap_rk, rs, crypto)
            rs.wipe()
            vk
        }
    }

    /**
     * Unlock with an already-held VaultKey.
     *
     * The key that opens the body directly, so this skips the KDF entirely —
     * which is the point. It backs the grace period from "Lock when I leave the
     * app": resuming inside the window must not cost an Argon2 derivation, and
     * there is no passphrase around to run one on anyway.
     */
    fun unlockWithVaultKey(fileBytes: ByteArray, vaultKey: ByteArray, crypto: CryptoProvider): UnlockResult =
        unlock(fileBytes, crypto) { vaultKey.copyOf() }

    /** Unlock with an already-held MasterKey (biometric quick-unlock path, §3.4). */
    fun unlockWithMasterKey(fileBytes: ByteArray, masterKey: ByteArray, crypto: CryptoProvider): UnlockResult =
        unlock(fileBytes, crypto) { header -> KeyHierarchy.unwrap(header.wrap_mk, masterKey, crypto) }

    /** Derive the MasterKey for this file's params (for storing under biometric keystore, §3.4). */
    fun deriveMasterKey(fileBytes: ByteArray, passphrase: ByteArray, crypto: CryptoProvider): ByteArray {
        val header = VaultFileCodec.decode(fileBytes).header
        return KeyHierarchy.deriveKey(passphrase, VaultFileCodec.unb64(header.salt_mk), header.ops, header.mem, crypto)
    }

    private inline fun unlock(
        fileBytes: ByteArray,
        crypto: CryptoProvider,
        obtainVaultKey: (VaultHeader) -> ByteArray?,
    ): UnlockResult {
        val envelope = try {
            VaultFileCodec.decode(fileBytes)
        } catch (e: VaultFormatException) {
            return UnlockResult.Corrupt
        }
        val vaultKey = obtainVaultKey(envelope.header) ?: return UnlockResult.WrongCredential
        val plaintext = crypto.aeadDecrypt(envelope.bodyCiphertext, envelope.prefix, envelope.bodyNonce, vaultKey)
            ?: return UnlockResult.Corrupt // wrap opened but body doesn't — damaged file
        val body = try {
            VaultJson.decodeFromString<VaultBody>(plaintext.decodeToString())
        } catch (e: Exception) {
            return UnlockResult.Corrupt
        } finally {
            plaintext.wipe()
        }
        return UnlockResult.Success(vaultKey, body, envelope)
    }

    /** Re-encrypt with a fresh body; salts/wraps/created_at are carried over unchanged. */
    fun save(
        envelope: VaultEnvelope,
        body: VaultBody,
        vaultKey: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
    ): ByteArray {
        val header = envelope.header.copy(writer_device = deviceId)
        val bodyBytes = VaultJson.encodeToString(body).toByteArray()
        return VaultFileCodec.encode(envelope.vaultUuid, nowMs, header, bodyBytes, vaultKey, crypto)
    }

    /** §3.3: passphrase change re-wraps wrap_mk ONLY — VaultKey and wrap_rk never change. */
    fun changePassphrase(
        fileBytes: ByteArray,
        currentPassphrase: ByteArray,
        newPassphrase: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
    ): ByteArray? {
        val result = unlockWithPassphrase(fileBytes, currentPassphrase, crypto)
        if (result !is UnlockResult.Success) return null
        return rewrapForNewPassphrase(result, newPassphrase, deviceId, nowMs, crypto)
    }

    /**
     * The same re-wrap, reached with the MasterKey the Keystore released rather
     * than a passphrase the user can no longer produce.
     *
     * Quick unlock means someone can go months opening the vault with a
     * fingerprint and never typing the passphrase — and the moment they notice
     * they have forgotten it is the moment [changePassphrase] stops being
     * available to them, because it wants the very thing they have lost. This
     * is the way out of that, and it needs no Recovery Key.
     *
     * It grants no read access that the caller did not already have: reaching
     * here at all means the Keystore has already released the MasterKey behind
     * a biometric prompt, and anything that unwraps the vault can already
     * export every record in it. What it does grant is the power to lock the
     * owner out, so the caller must gate it on a fresh authentication rather
     * than on a session unlocked minutes ago.
     */
    fun changePassphraseWithMasterKey(
        fileBytes: ByteArray,
        masterKey: ByteArray,
        newPassphrase: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
    ): ByteArray? {
        val result = unlockWithMasterKey(fileBytes, masterKey, crypto)
        if (result !is UnlockResult.Success) return null
        return rewrapForNewPassphrase(result, newPassphrase, deviceId, nowMs, crypto)
    }

    /** Re-derives MK from [newPassphrase] under a fresh salt and re-wraps only wrap_mk. */
    private fun rewrapForNewPassphrase(
        result: UnlockResult.Success,
        newPassphrase: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
    ): ByteArray {
        val saltMk = crypto.randomBytes(CryptoProvider.SALT_BYTES)
        val mk = KeyHierarchy.deriveKey(newPassphrase, saltMk, result.envelope.header.ops, result.envelope.header.mem, crypto)
        val header = result.envelope.header.copy(
            salt_mk = VaultFileCodec.b64(saltMk),
            wrap_mk = KeyHierarchy.wrap(result.vaultKey, mk, crypto),
            writer_device = deviceId,
        )
        mk.wipe()
        val bodyBytes = VaultJson.encodeToString(result.body).toByteArray()
        val out = VaultFileCodec.encode(result.envelope.vaultUuid, nowMs, header, bodyBytes, result.vaultKey, crypto)
        result.vaultKey.wipe()
        return out
    }

    /** Settings → "Generate NEW recovery key": re-wraps wrap_rk only (§5.1 S4). */
    fun rotateRecoveryKey(
        fileBytes: ByteArray,
        passphrase: ByteArray,
        deviceId: String,
        nowMs: Long,
        crypto: CryptoProvider,
    ): Pair<ByteArray, String>? {
        val result = unlockWithPassphrase(fileBytes, passphrase, crypto)
        if (result !is UnlockResult.Success) return null
        val recoveryRaw = crypto.randomBytes(RecoveryKey.RAW_BYTES)
        val saltRk = crypto.randomBytes(CryptoProvider.SALT_BYTES)
        val rs = KeyHierarchy.deriveKey(recoveryRaw, saltRk, result.envelope.header.ops, result.envelope.header.mem, crypto)
        val header = result.envelope.header.copy(
            salt_rk = VaultFileCodec.b64(saltRk),
            wrap_rk = KeyHierarchy.wrap(result.vaultKey, rs, crypto),
            writer_device = deviceId,
        )
        rs.wipe()
        val bodyBytes = VaultJson.encodeToString(result.body).toByteArray()
        val out = VaultFileCodec.encode(result.envelope.vaultUuid, nowMs, header, bodyBytes, result.vaultKey, crypto)
        result.vaultKey.wipe()
        val formatted = RecoveryKey.encode(recoveryRaw)
        recoveryRaw.wipe()
        return out to formatted
    }
}
// #endregion
