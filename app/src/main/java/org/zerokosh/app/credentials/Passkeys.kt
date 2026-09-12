/**
 * @file Passkeys.kt
 * @description Turning a WebAuthn ceremony into a vault record, and back.
 *
 * The byte-level work lives in core/passkey, where it is covered by JVM tests
 * against the specification. This file is the part that has to know about
 * Android: who is asking, what the request JSON says, and how the result is
 * stored.
 *
 * A passkey is kept as an ordinary record on the `passkey` template, which
 * means it inherits everything records already have — it shows up in the vault,
 * it can be deleted, it merges, it goes into a backup, and the private key is
 * marked sensitivity H so it is treated as a secret everywhere that matters.
 * A separate hidden store would have been less code and worse: a credential the
 * user cannot see is a credential they cannot revoke.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. RECORD MAPPING
 * 3. REGISTRATION
 * 4. ASSERTION
 * 5. CALLER IDENTITY
 */
package org.zerokosh.app.credentials

// #region Imports
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.provider.CallingAppInfo
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.core.model.Record
import org.zerokosh.core.passkey.WebAuthn
import java.security.interfaces.ECPublicKey
import java.util.UUID
// #endregion

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
internal object Passkeys {

    const val TEMPLATE_ID = "passkey"

    private val lenient = Json { ignoreUnknownKeys = true; isLenient = true }

    // #region Record mapping
    fun passkeysFor(app: ZerokoshApp, rpId: String): List<Record> =
        app.repository.body.value?.records.orEmpty().filter {
            it.template_id == TEMPLATE_ID && it.fields["website"].equals(rpId, ignoreCase = true)
        }

    fun credentialIdOf(record: Record): ByteArray? =
        record.fields["credential_id"]?.let { runCatching { WebAuthn.b64urlDecode(it) }.getOrNull() }

    fun signCountOf(record: Record): Long = record.fields["sign_count"]?.toLongOrNull() ?: 0L
    // #endregion

    // #region Registration
    /**
     * What a relying party asked for, reduced to the parts that matter here.
     *
     * Parsed leniently on purpose: the options object carries a great deal this
     * authenticator has no say in — timeouts, attestation preferences, hints —
     * and failing on an unknown key would mean breaking on sites that are
     * perfectly willing to work with us.
     */
    data class CreationOptions(
        val rpId: String,
        val rpName: String,
        val userName: String,
        val userDisplayName: String,
        val userHandle: String,
        val challenge: String,
    )

    fun parseCreationOptions(requestJson: String): CreationOptions? = runCatching {
        val root = lenient.parseToJsonElement(requestJson).jsonObject
        val rp = root["rp"]!!.jsonObject
        val user = root["user"]!!.jsonObject
        CreationOptions(
            rpId = rp["id"]!!.jsonPrimitive.content,
            rpName = rp.str("name") ?: rp["id"]!!.jsonPrimitive.content,
            userName = user.str("name").orEmpty(),
            userDisplayName = user.str("displayName").orEmpty(),
            userHandle = user.str("id").orEmpty(),
            challenge = root["challenge"]!!.jsonPrimitive.content,
        )
    }.getOrNull()

    /** A new key pair, the record that keeps it, and the JSON the site expects. */
    class Registration(val record: Record, val responseJson: String)

    fun register(
        app: ZerokoshApp,
        options: CreationOptions,
        origin: String,
        packageName: String?,
        userVerified: Boolean,
    ): Registration {
        val pair = WebAuthn.generateKeyPair()
        val public = pair.public as ECPublicKey
        // 32 bytes from the same source the vault uses for everything else.
        val credentialId = ByteArray(32).also { app.repository.crypto.randomBytes(it.size).copyInto(it) }

        val clientData = WebAuthn.clientDataJson(
            type = "webauthn.create",
            challenge = options.challenge,
            origin = origin,
            packageName = packageName,
        )
        val authData = WebAuthn.authenticatorData(
            rpId = options.rpId,
            signCount = 0,
            credentialId = credentialId,
            coseKey = WebAuthn.coseKey(public),
            userVerified = userVerified,
        )
        val attestation = WebAuthn.attestationObject(authData)
        val now = System.currentTimeMillis()

        val record = Record(
            uuid = UUID.randomUUID().toString(),
            template_id = TEMPLATE_ID,
            title = options.rpName.ifBlank { options.rpId },
            institution = options.rpName,
            fields = buildMap {
                put("website", options.rpId)
                if (options.userName.isNotBlank()) put("username", options.userName)
                put("credential_id", WebAuthn.b64url(credentialId))
                put("private_key", WebAuthn.b64url(pair.private.encoded))
                if (options.userHandle.isNotBlank()) put("user_handle", options.userHandle)
                put("sign_count", "0")
            },
            created_at = now,
            modified_at = now,
            device_id = app.prefs.deviceId,
        )
        return Registration(
            record = record,
            responseJson = WebAuthn.registrationResponseJson(
                credentialId = credentialId,
                attestationObject = attestation,
                clientDataJson = clientData,
                authData = authData,
                publicKey = public,
            ),
        )
    }
    // #endregion

    // #region Assertion
    data class RequestOptions(val rpId: String, val challenge: String)

    fun parseRequestOptions(requestJson: String): RequestOptions? = runCatching {
        val root = lenient.parseToJsonElement(requestJson).jsonObject
        RequestOptions(
            rpId = root.str("rpId").orEmpty(),
            challenge = root["challenge"]!!.jsonPrimitive.content,
        )
    }.getOrNull()

    /** The signed assertion, plus the record with its counter moved on. */
    class Assertion(val responseJson: String, val updated: Record)

    fun assert(
        record: Record,
        options: RequestOptions,
        origin: String,
        packageName: String?,
        userVerified: Boolean,
    ): Assertion? {
        val credentialId = credentialIdOf(record) ?: return null
        val pkcs8 = record.fields["private_key"]
            ?.let { runCatching { WebAuthn.b64urlDecode(it) }.getOrNull() } ?: return null
        val privateKey = runCatching { WebAuthn.privateKeyFrom(pkcs8) }.getOrNull() ?: return null

        // Incremented before signing, so what the site is told matches what is
        // stored. A counter that goes backwards is how a relying party detects
        // a cloned credential, so it must never repeat.
        val nextCount = signCountOf(record) + 1
        val clientData = WebAuthn.clientDataJson(
            type = "webauthn.get",
            challenge = options.challenge,
            origin = origin,
            packageName = packageName,
        )
        val authData = WebAuthn.authenticatorData(
            rpId = options.rpId.ifBlank { record.fields["website"].orEmpty() },
            signCount = nextCount,
            userVerified = userVerified,
        )
        val signature = WebAuthn.sign(privateKey, authData, clientData)
        val handle = record.fields["user_handle"]
            ?.takeIf { it.isNotBlank() }
            ?.let { runCatching { WebAuthn.b64urlDecode(it) }.getOrNull() }

        return Assertion(
            responseJson = WebAuthn.assertionResponseJson(
                credentialId = credentialId,
                authData = authData,
                clientDataJson = clientData,
                signature = signature,
                userHandle = handle,
            ),
            updated = record.copy(
                fields = record.fields + ("sign_count" to nextCount.toString()),
                modified_at = System.currentTimeMillis(),
                rev = record.rev + 1,
            ),
        )
    }
    // #endregion

    // #region Caller identity
    /**
     * The origin to put in the client data.
     *
     * A browser calling for a website reports that site, but reading it needs a
     * list of browsers we are prepared to believe, which this app does not ship.
     * For everything else the origin is the caller's own signing certificate in
     * the form Android specifies, which is what a relying party with an
     * assetlinks file will be checking against.
     */
    fun originFor(info: CallingAppInfo?): String {
        val cert = info?.signingInfo?.apkContentsSigners?.firstOrNull()?.toByteArray()
            ?: return "android:apk-key-hash:unknown"
        return "android:apk-key-hash:" + WebAuthn.b64url(WebAuthn.sha256(cert))
    }
    // #endregion
}

private fun JsonObject.str(key: String): String? =
    runCatching { this[key]?.jsonPrimitive?.content }.getOrNull()
