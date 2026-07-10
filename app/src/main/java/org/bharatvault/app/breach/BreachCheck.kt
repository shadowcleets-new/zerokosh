/**
 * @file BreachCheck.kt
 * @description §5.9 optional breach check (OFF by default). Sends ONLY the
 *              first 5 hex chars of SHA-1(password) to Have I Been Pwned
 *              range API (k-anonymity). NEVER runs without the Settings toggle.
 */
package org.bharatvault.app.breach

// #region Imports
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
// #endregion

object BreachCheck {

    /**
     * @return number of times the password appears in known breaches, or -1 on network/error.
     */
    fun checkPassword(password: String): Int {
        val sha1 = MessageDigest.getInstance("SHA-1")
            .digest(password.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02X".format(it) }
        val prefix = sha1.substring(0, 5)
        val suffix = sha1.substring(5)
        val url = URL("https://api.pwnedpasswords.com/range/$prefix")
        val conn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = 8_000
            readTimeout = 8_000
            setRequestProperty("User-Agent", "BharatVault")
            setRequestProperty("Add-Padding", "true")
        }
        return try {
            if (conn.responseCode != 200) return -1
            val body = conn.inputStream.bufferedReader().readText()
            body.lineSequence().firstOrNull { line ->
                line.startsWith(suffix, ignoreCase = true)
            }?.substringAfter(':')?.trim()?.toIntOrNull() ?: 0
        } catch (_: Exception) {
            -1
        } finally {
            conn.disconnect()
        }
    }
}
