/**
 * @file WebAuthn.kt
 * @description The bytes a passkey is made of.
 *
 * A passkey is an ES256 key pair the relying party never sees the private half
 * of. Registering one means handing back an attestation object; signing in
 * means handing back a signature over the authenticator data and a hash of the
 * client data. Both are byte-exact structures — the relying party hashes and
 * parses them — so this file is written against the spec rather than against
 * what happens to work.
 *
 * Deliberately free of Android: it is all java.security and byte arrays, so the
 * whole of it is exercised by ordinary JVM tests rather than only on a device.
 * That matters more here than anywhere else in this app, because a passkey that
 * is subtly wrong locks a user out of an account with no password to fall back
 * on.
 *
 * Attestation is "none". Zerokosh is a software authenticator with nothing to
 * attest to, and claiming otherwise would be a lie the relying party could
 * check.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. FLAGS AND CONSTANTS
 * 3. KEYS
 * 4. AUTHENTICATOR DATA
 * 5. REGISTRATION
 * 6. ASSERTION
 */
package org.zerokosh.core.passkey

// #region Imports
import java.security.KeyFactory
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.MessageDigest
import java.security.PrivateKey
import java.security.Signature
import java.security.interfaces.ECPublicKey
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Base64
// #endregion

object WebAuthn {

    // #region Flags and constants
    /** User present. Set whenever the user actually chose this credential. */
    private const val FLAG_USER_PRESENT = 0x01

    /** User verified — the vault was unlocked, which is a real verification. */
    private const val FLAG_USER_VERIFIED = 0x04

    /** Attested credential data follows. Registration only. */
    private const val FLAG_ATTESTED = 0x40

    /**
     * All zeroes, which is what a software authenticator making no attestation
     * claim is supposed to send. A made-up AAGUID would be a claim about
     * hardware that does not exist.
     */
    private val AAGUID = ByteArray(16)

    private const val COSE_KTY = 1
    private const val COSE_ALG = 3
    private const val COSE_CRV = -1
    private const val COSE_X = -2
    private const val COSE_Y = -3

    private const val COSE_KTY_EC2 = 2
    private const val COSE_ALG_ES256 = -7
    private const val COSE_CRV_P256 = 1

    /** The one algorithm this authenticator offers. */
    const val ALG_ES256 = -7L
    // #endregion

    // #region Keys
    fun generateKeyPair(): KeyPair =
        KeyPairGenerator.getInstance("EC").apply {
            initialize(ECGenParameterSpec("secp256r1"))
        }.generateKeyPair()

    fun privateKeyFrom(pkcs8: ByteArray): PrivateKey =
        KeyFactory.getInstance("EC").generatePrivate(PKCS8EncodedKeySpec(pkcs8))

    /**
     * The public key as a COSE_Key map.
     *
     * X and Y are fixed at 32 bytes each. BigInteger.toByteArray() adds a
     * leading zero for values with the high bit set and drops leading zeroes
     * otherwise, and either would produce a key the relying party reads as a
     * different point.
     */
    fun coseKey(publicKey: ECPublicKey): ByteArray {
        val point = publicKey.w
        return Cbor.map(
            COSE_KTY to COSE_KTY_EC2,
            COSE_ALG to COSE_ALG_ES256,
            COSE_CRV to COSE_CRV_P256,
            COSE_X to fixed32(point.affineX.toByteArray()),
            COSE_Y to fixed32(point.affineY.toByteArray()),
        )
    }

    internal fun fixed32(raw: ByteArray): ByteArray = when {
        raw.size == 32 -> raw
        raw.size > 32 -> raw.copyOfRange(raw.size - 32, raw.size)
        else -> ByteArray(32 - raw.size) + raw
    }
    // #endregion

    // #region Authenticator data
    /**
     * @param signCount incremented on every assertion. A relying party that
     *   tracks it can spot a cloned credential by the counter going backwards.
     */
    /**
     * @param userVerified whether the user proved who they are *for this
     *   ceremony* — a fingerprint or a typed passphrase just now, not a vault
     *   that happens to be open. UV is a claim the relying party acts on: a
     *   bank may skip its own second factor on the strength of it, so setting
     *   it unconditionally told every site that a thief holding an unlocked
     *   phone had been verified. Defaults to false, which is merely useless
     *   rather than untrue.
     */
    fun authenticatorData(
        rpId: String,
        signCount: Long,
        credentialId: ByteArray? = null,
        coseKey: ByteArray? = null,
        userVerified: Boolean = false,
    ): ByteArray {
        val rpIdHash = sha256(rpId.toByteArray(Charsets.UTF_8))
        var flags = FLAG_USER_PRESENT
        if (userVerified) flags = flags or FLAG_USER_VERIFIED
        if (credentialId != null && coseKey != null) flags = flags or FLAG_ATTESTED

        val out = ArrayList<Byte>(128)
        out.addAll(rpIdHash.toList())
        out.add(flags.toByte())
        out.addAll(uint32(signCount).toList())
        if (credentialId != null && coseKey != null) {
            out.addAll(AAGUID.toList())
            out.addAll(uint16(credentialId.size).toList())
            out.addAll(credentialId.toList())
            out.addAll(coseKey.toList())
        }
        return out.toByteArray()
    }

    private fun uint16(v: Int) = byteArrayOf((v shr 8).toByte(), v.toByte())

    private fun uint32(v: Long) =
        byteArrayOf((v shr 24).toByte(), (v shr 16).toByte(), (v shr 8).toByte(), v.toByte())
    // #endregion

    // #region Registration
    /** `{ "fmt": "none", "attStmt": {}, "authData": <bytes> }`, in that order. */
    fun attestationObject(authData: ByteArray): ByteArray = Cbor.map(
        "fmt" to "none",
        "attStmt" to Cbor.PreEncoded(Cbor.map()),
        "authData" to authData,
    )

    /**
     * The JSON a relying party receives after `navigator.credentials.create()`.
     *
     * Assembled by hand rather than through a serializer because the field
     * names and nesting are fixed by the spec, and a data class would only
     * restate them one layer further away.
     */
    fun registrationResponseJson(
        credentialId: ByteArray,
        attestationObject: ByteArray,
        clientDataJson: ByteArray,
        authData: ByteArray,
        publicKey: ECPublicKey,
    ): String {
        val id = b64url(credentialId)
        return """
            {"id":"$id","rawId":"$id","type":"public-key",
            "authenticatorAttachment":"platform",
            "clientExtensionResults":{},
            "response":{"clientDataJSON":"${b64url(clientDataJson)}",
            "attestationObject":"${b64url(attestationObject)}",
            "authenticatorData":"${b64url(authData)}",
            "publicKeyAlgorithm":$ALG_ES256,
            "publicKey":"${b64url(publicKey.encoded)}",
            "transports":["internal","hybrid"]}}
        """.trimIndent().replace("\n", "")
    }

    /** `{"type":..., "challenge":..., "origin":..., "androidPackageName":...}`. */
    fun clientDataJson(type: String, challenge: String, origin: String, packageName: String?): ByteArray {
        val pkg = packageName?.let { ""","androidPackageName":"${escape(it)}"""" }.orEmpty()
        return ("""{"type":"$type","challenge":"${escape(challenge)}",""" +
            """"origin":"${escape(origin)}","crossOrigin":false$pkg}""")
            .toByteArray(Charsets.UTF_8)
    }
    // #endregion

    // #region Assertion
    /**
     * Sign an assertion.
     *
     * The signed message is the authenticator data followed by the SHA-256 of
     * the client data, concatenated — not hashed together, not the other way
     * round. Getting that wrong produces a signature that verifies against
     * nothing.
     */
    fun sign(privateKey: PrivateKey, authData: ByteArray, clientDataJson: ByteArray): ByteArray =
        Signature.getInstance("SHA256withECDSA").run {
            initSign(privateKey)
            update(authData)
            update(sha256(clientDataJson))
            sign()
        }

    /** The JSON a relying party receives after `navigator.credentials.get()`. */
    fun assertionResponseJson(
        credentialId: ByteArray,
        authData: ByteArray,
        clientDataJson: ByteArray,
        signature: ByteArray,
        userHandle: ByteArray?,
    ): String {
        val id = b64url(credentialId)
        val handle = userHandle?.let { ""","userHandle":"${b64url(it)}"""" }.orEmpty()
        return """
            {"id":"$id","rawId":"$id","type":"public-key",
            "authenticatorAttachment":"platform",
            "clientExtensionResults":{},
            "response":{"clientDataJSON":"${b64url(clientDataJson)}",
            "authenticatorData":"${b64url(authData)}",
            "signature":"${b64url(signature)}"$handle}}
        """.trimIndent().replace("\n", "")
    }
    // #endregion

    // #region Helpers
    fun sha256(input: ByteArray): ByteArray =
        MessageDigest.getInstance("SHA-256").digest(input)

    fun b64url(input: ByteArray): String =
        Base64.getUrlEncoder().withoutPadding().encodeToString(input)

    fun b64urlDecode(input: String): ByteArray =
        Base64.getUrlDecoder().decode(input.trim().trimEnd('='))

    private fun escape(s: String): String =
        s.replace("\\", "\\\\").replace("\"", "\\\"")
    // #endregion
}
