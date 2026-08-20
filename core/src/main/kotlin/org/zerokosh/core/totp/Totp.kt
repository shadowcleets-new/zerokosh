package org.zerokosh.core.totp

// #region Imports
import java.net.URLDecoder
import java.nio.ByteBuffer
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
// #endregion

/**
 * RFC 6238 TOTP (§5.8): HMAC-SHA1 default, 6 digits, 30 s; honors
 * algorithm/digits/period from otpauth:// URIs. HMAC comes from the platform
 * JDK (javax.crypto) — libsodium does not expose HMAC-SHA1; this is standard
 * platform crypto, not a hand-rolled primitive.
 */
object Totp {

    data class Params(
        val secretBase32: String,
        val label: String = "",
        val issuer: String = "",
        val algorithm: String = "SHA1",
        val digits: Int = 6,
        val periodSeconds: Int = 30,
    )

    // #region Code generation
    fun code(params: Params, timeMs: Long): String {
        val counter = timeMs / 1000 / params.periodSeconds
        return hotp(base32Decode(params.secretBase32), counter, params.digits, params.algorithm)
    }

    /** Seconds until the current code rotates. */
    fun secondsRemaining(params: Params, timeMs: Long): Int {
        val period = params.periodSeconds
        return (period - ((timeMs / 1000) % period)).toInt()
    }

    fun hotp(secret: ByteArray, counter: Long, digits: Int, algorithm: String): String {
        val mac = Mac.getInstance("Hmac${algorithm.uppercase().replace("-", "")}")
        mac.init(SecretKeySpec(secret, mac.algorithm))
        val hash = mac.doFinal(ByteBuffer.allocate(8).putLong(counter).array())
        val offset = hash[hash.size - 1].toInt() and 0x0F
        val binary = ((hash[offset].toInt() and 0x7F) shl 24) or
            ((hash[offset + 1].toInt() and 0xFF) shl 16) or
            ((hash[offset + 2].toInt() and 0xFF) shl 8) or
            (hash[offset + 3].toInt() and 0xFF)
        var mod = 1
        repeat(digits) { mod *= 10 }
        return (binary % mod).toString().padStart(digits, '0')
    }
    // #endregion

    // #region Base32 (RFC 4648 — TOTP secrets use this alphabet, not Crockford)
    private const val B32 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567"

    fun base32Decode(s: String): ByteArray {
        val clean = s.uppercase().replace(" ", "").replace("-", "").trimEnd('=')
        val out = ByteArray(clean.length * 5 / 8)
        var buffer = 0
        var bits = 0
        var idx = 0
        for (c in clean) {
            val v = B32.indexOf(c)
            require(v >= 0) { "invalid base32" }
            buffer = (buffer shl 5) or v
            bits += 5
            if (bits >= 8) {
                bits -= 8
                out[idx++] = ((buffer shr bits) and 0xFF).toByte()
            }
        }
        return out
    }
    // #endregion

    // #region otpauth:// URI parsing (§5.8, S11 QR scanner)
    fun parseOtpauthUri(uri: String): Params? {
        val prefix = "otpauth://totp/"
        if (!uri.startsWith(prefix, ignoreCase = true)) return null
        val rest = uri.substring(prefix.length)
        val qIdx = rest.indexOf('?')
        val label = URLDecoder.decode(if (qIdx >= 0) rest.substring(0, qIdx) else rest, "UTF-8")
        val query = if (qIdx >= 0) rest.substring(qIdx + 1) else return null
        val params = query.split('&').mapNotNull {
            val eq = it.indexOf('=')
            if (eq <= 0) null
            else it.substring(0, eq).lowercase() to URLDecoder.decode(it.substring(eq + 1), "UTF-8")
        }.toMap()
        val secret = params["secret"] ?: return null
        return try {
            base32Decode(secret) // validate early
            Params(
                secretBase32 = secret,
                label = label,
                issuer = params["issuer"] ?: label.substringBefore(':', ""),
                algorithm = params["algorithm"] ?: "SHA1",
                digits = params["digits"]?.toIntOrNull() ?: 6,
                periodSeconds = params["period"]?.toIntOrNull() ?: 30,
            )
        } catch (e: IllegalArgumentException) {
            null
        }
    }
    // #endregion
}
