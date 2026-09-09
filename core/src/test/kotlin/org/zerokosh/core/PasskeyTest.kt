/**
 * @file PasskeyTest.kt
 * @description The bytes of a passkey, checked against the specification.
 *
 * These assert structure and offsets rather than "what the code produced",
 * because a relying party parses these byte-for-byte and a passkey that is
 * subtly wrong locks someone out of an account that has no password to fall
 * back on. Where a value is fixed by the spec — the COSE map for ES256, the
 * CBOR encoding of -7, the position of the flags byte — it is written out here
 * literally.
 */
package org.zerokosh.core

import org.zerokosh.core.passkey.WebAuthn
import java.math.BigInteger
import java.security.Signature
import java.security.interfaces.ECPublicKey
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PasskeyTest {

    private val rpId = "example.com"

    // #region Authenticator data layout
    @Test
    fun `assertion authenticator data is 37 bytes with the hash first`() {
        val data = WebAuthn.authenticatorData(rpId, signCount = 0)
        // 32 hash + 1 flags + 4 counter, and nothing else without attestation.
        assertEquals(37, data.size)
        assertContentEquals(
            WebAuthn.sha256(rpId.toByteArray()),
            data.copyOfRange(0, 32),
        )
    }

    @Test
    fun `flags say user present and verified, and not attested, for an assertion`() {
        val flags = WebAuthn.authenticatorData(rpId, signCount = 0)[32].toInt()
        assertEquals(0x01, flags and 0x01, "UP")
        assertEquals(0x04, flags and 0x04, "UV")
        assertEquals(0x00, flags and 0x40, "AT must be clear without attested data")
    }

    @Test
    fun `registration sets the attested bit and appends the credential`() {
        val pair = WebAuthn.generateKeyPair()
        val cose = WebAuthn.coseKey(pair.public as ECPublicKey)
        val credId = ByteArray(16) { it.toByte() }
        val data = WebAuthn.authenticatorData(rpId, 0, credId, cose)

        assertEquals(0x40, data[32].toInt() and 0x40, "AT")
        // 37 + 16 aaguid + 2 length + id + key
        assertEquals(37 + 16 + 2 + credId.size + cose.size, data.size)
        // The length is big-endian at offset 53.
        assertEquals(0, data[53].toInt())
        assertEquals(credId.size, data[54].toInt())
        assertContentEquals(credId, data.copyOfRange(55, 55 + credId.size))
    }

    @Test
    fun `the sign counter is big-endian at offset 33`() {
        val data = WebAuthn.authenticatorData(rpId, signCount = 0x01020304)
        assertContentEquals(
            byteArrayOf(1, 2, 3, 4),
            data.copyOfRange(33, 37),
        )
    }
    // #endregion

    // #region COSE key
    @Test
    fun `the COSE key is the ES256 map the spec fixes`() {
        val pair = WebAuthn.generateKeyPair()
        val cose = WebAuthn.coseKey(pair.public as ECPublicKey)
        // a5            map(5)
        // 01 02         1: 2      kty = EC2
        // 03 26         3: -7     alg = ES256   (-7 is major type 1, value 6)
        // 20 01        -1: 1      crv = P-256
        // 21 58 20 ..  -2: bytes(32)  x
        // 22 58 20 ..  -3: bytes(32)  y
        val expectedPrefix = byteArrayOf(
            0xa5.toByte(),
            0x01, 0x02,
            0x03, 0x26,
            0x20, 0x01,
            0x21, 0x58, 0x20,
        )
        assertContentEquals(expectedPrefix, cose.copyOfRange(0, expectedPrefix.size))
        // 10 header + 32 x + 3 header + 32 y
        assertEquals(10 + 32 + 3 + 32, cose.size)
        assertContentEquals(byteArrayOf(0x22, 0x58, 0x20), cose.copyOfRange(42, 45))
    }

    /**
     * The coordinate padding is the whole reason fixed32 exists: BigInteger
     * adds a sign byte when the high bit is set and drops leading zeroes when it
     * is not, and either would be read as a different point on the curve.
     */
    @Test
    fun `coordinates are always exactly 32 bytes`() {
        val short = BigInteger.ONE.toByteArray()
        val signed = BigInteger("ff".repeat(32), 16).toByteArray()
        assertEquals(32, WebAuthn.fixed32(short).size)
        assertEquals(32, WebAuthn.fixed32(signed).size)
        assertEquals(1, WebAuthn.fixed32(short)[31].toInt())
        assertEquals(0, WebAuthn.fixed32(short)[0].toInt())
    }
    // #endregion

    // #region Attestation
    @Test
    fun `attestation is none and carries an empty statement`() {
        val authData = WebAuthn.authenticatorData(rpId, 0)
        val obj = WebAuthn.attestationObject(authData)
        // a3                     map(3)
        // 63 666d74 646e6f6e65   "fmt": "none"
        // 67 61747453746d74 a0   "attStmt": {}
        val expectedPrefix = byteArrayOf(
            0xa3.toByte(),
            0x63, 0x66, 0x6d, 0x74,
            0x64, 0x6e, 0x6f, 0x6e, 0x65,
            0x67, 0x61, 0x74, 0x74, 0x53, 0x74, 0x6d, 0x74,
            0xa0.toByte(),
        )
        assertContentEquals(expectedPrefix, obj.copyOfRange(0, expectedPrefix.size))
    }
    // #endregion

    // #region Signing
    /**
     * The one that matters: a relying party verifies over authData followed by
     * the SHA-256 of the client data. This checks the signature against that
     * exact message with an independent verifier, so a wrong order or a double
     * hash fails here rather than at a bank's login page.
     */
    @Test
    fun `the signature verifies over authData followed by the client data hash`() {
        val pair = WebAuthn.generateKeyPair()
        val authData = WebAuthn.authenticatorData(rpId, signCount = 7)
        val clientData = WebAuthn.clientDataJson(
            type = "webauthn.get",
            challenge = WebAuthn.b64url(ByteArray(32) { 9 }),
            origin = "https://example.com",
            packageName = null,
        )
        val signature = WebAuthn.sign(pair.private, authData, clientData)

        val verifier = Signature.getInstance("SHA256withECDSA").apply {
            initVerify(pair.public)
            update(authData)
            update(WebAuthn.sha256(clientData))
        }
        assertTrue(verifier.verify(signature), "assertion signature must verify")
    }

    @Test
    fun `a signature does not verify against different authenticator data`() {
        val pair = WebAuthn.generateKeyPair()
        val clientData = WebAuthn.clientDataJson("webauthn.get", "abc", "https://example.com", null)
        val signature = WebAuthn.sign(pair.private, WebAuthn.authenticatorData(rpId, 1), clientData)

        val verifier = Signature.getInstance("SHA256withECDSA").apply {
            initVerify(pair.public)
            update(WebAuthn.authenticatorData(rpId, 2)) // counter moved
            update(WebAuthn.sha256(clientData))
        }
        assertTrue(!verifier.verify(signature), "a different counter must not verify")
    }

    @Test
    fun `a stored private key round-trips and still signs`() {
        val pair = WebAuthn.generateKeyPair()
        val restored = WebAuthn.privateKeyFrom(pair.private.encoded)
        val authData = WebAuthn.authenticatorData(rpId, 1)
        val clientData = WebAuthn.clientDataJson("webauthn.get", "abc", "https://example.com", null)

        val verifier = Signature.getInstance("SHA256withECDSA").apply {
            initVerify(pair.public)
            update(authData)
            update(WebAuthn.sha256(clientData))
        }
        assertTrue(verifier.verify(WebAuthn.sign(restored, authData, clientData)))
    }
    // #endregion

    // #region Client data
    @Test
    fun `client data names the ceremony, challenge and origin`() {
        val json = WebAuthn.clientDataJson(
            type = "webauthn.create",
            challenge = "Q0hBTExFTkdF",
            origin = "https://example.com",
            packageName = "com.example.app",
        ).decodeToString()
        assertTrue(json.contains(""""type":"webauthn.create""""), json)
        assertTrue(json.contains(""""challenge":"Q0hBTExFTkdF""""), json)
        assertTrue(json.contains(""""origin":"https://example.com""""), json)
        assertTrue(json.contains(""""androidPackageName":"com.example.app""""), json)
    }

    @Test
    fun `base64url is unpadded and round-trips`() {
        val raw = ByteArray(33) { it.toByte() }
        val encoded = WebAuthn.b64url(raw)
        assertTrue(!encoded.contains("="), "must be unpadded")
        assertTrue(!encoded.contains("+") && !encoded.contains("/"), "must be url-safe")
        assertContentEquals(raw, WebAuthn.b64urlDecode(encoded))
    }
    // #endregion
}
