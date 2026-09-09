/**
 * @file SessionKeeper.kt
 * @description The grace period from "Lock when I leave the app", made to
 *              survive the app process being killed.
 *
 * The vault key lived only in the repository's memory, so it did not outlive
 * the process — and Android reclaims a backgrounded process whenever it likes.
 * A user who had chosen "After 5 minutes" would come back inside their five
 * minutes to a sealed vault, and, worse, the autofill service runs in that same
 * process: it would be started fresh by the platform, find no key, and answer
 * every password field with "Unlock Zerokosh to fill". The setting was
 * therefore describing something the app did not actually do.
 *
 * What is stored is the vault key sealed by an AES key in the Android Keystore,
 * beside the deadline it stops being valid. The Keystore key is hardware-held
 * and cannot be exported, so the sealed blob is worthless on its own — copying
 * it off the device, or reading it out of a backup, gets an attacker nothing.
 *
 * The deadline is enforced three ways, because a timestamp in a preferences
 * file is the weakest thing in this design:
 *   - the Keystore key itself is minted with setKeyValidityEnd, so the platform
 *     refuses to decrypt past the deadline whatever the app does;
 *   - the elapsed-realtime clock, which cannot be wound back from Settings;
 *   - a boot reference, so a reboot or any wall-clock change drops the session
 *     rather than extending it.
 * A fresh Keystore key is minted per session and the old one deleted, so
 * dropping a session really does destroy the means to read what it wrote.
 *
 * The trade this makes is real and is exactly the one the setting names: for
 * the length of the window, someone holding the *unlocked* phone can open the
 * vault without the passphrase. "Immediately" is the choice that opts out, and
 * it stores nothing at all.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. STORAGE
 * 3. REMEMBER / RESUME / CLEAR
 * 4. DEADLINE
 * 5. KEYSTORE
 */
package org.zerokosh.app.data

// #region Imports
import android.content.Context
import android.os.SystemClock
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.security.KeyStore
import java.util.Date
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
// #endregion

object SessionKeeper {

    // #region Storage
    private const val TAG = "SessionKeeper"
    private const val KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "zerokosh_session"
    private const val PREFS = "zerokosh_session"
    private const val PREF_BLOB = "blob"
    private const val PREF_IV = "iv"
    private const val PREF_EXPIRES = "expires_at"
    private const val PREF_EXPIRES_ELAPSED = "expires_elapsed"
    private const val PREF_BOOT_REF = "boot_ref"

    /**
     * How far the boot reference may drift and still be believed.
     *
     * It is (wall clock − time since boot), so it holds still through a whole
     * boot and moves the moment either clock is adjusted. NTP nudges it by
     * milliseconds; winding the date back to revive a dead session moves it by
     * hours. A minute of slack tells those two apart without ever locking
     * somebody out because their phone synced the time.
     */
    private const val BOOT_REF_TOLERANCE_MS = 60_000L

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    /** Whether a session was written at all, regardless of whether it is still good. */
    fun hasStoredSession(context: Context): Boolean = prefs(context).contains(PREF_BLOB)
    // #endregion

    // #region Remember / resume / clear
    /**
     * Hold [vaultKey] until [expiresAtMs], so a fresh process inside the window
     * can open the vault without asking again.
     *
     * A window of zero — "Immediately" — is not a short session, it is the
     * absence of one: nothing is written, and anything already held is dropped.
     *
     * Always re-mints the Keystore key, because the deadline is baked into it.
     */
    fun remember(context: Context, vaultKey: ByteArray, expiresAtMs: Long) {
        val remaining = expiresAtMs - System.currentTimeMillis()
        if (remaining <= 0) {
            clear(context)
            return
        }
        try {
            clear(context)
            val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply {
                init(Cipher.ENCRYPT_MODE, mintKey(expiresAtMs))
            }
            val sealed = cipher.doFinal(vaultKey)
            prefs(context).edit()
                .putString(PREF_BLOB, Base64.encodeToString(sealed, Base64.NO_WRAP))
                .putString(PREF_IV, Base64.encodeToString(cipher.iv, Base64.NO_WRAP))
                .putLong(PREF_EXPIRES, expiresAtMs)
                .putLong(PREF_EXPIRES_ELAPSED, SystemClock.elapsedRealtime() + remaining)
                .putLong(PREF_BOOT_REF, bootRef())
                .apply()
        } catch (e: Exception) {
            // Never fatal. Failing to keep a session costs the user a passphrase
            // prompt; throwing here would cost them the unlock they just did.
            Log.w(TAG, "could not keep session", e)
            clear(context)
        }
    }

    /**
     * The held vault key, or null when there is none, it has run out, or the
     * Keystore key is gone.
     *
     * The caller owns the array that comes back and should wipe it.
     */
    fun resume(context: Context): ByteArray? {
        if (!isLive(context)) return null
        val p = prefs(context)
        val blob = p.getString(PREF_BLOB, null) ?: return null
        val iv = p.getString(PREF_IV, null) ?: return null
        return try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply {
                init(
                    Cipher.DECRYPT_MODE,
                    loadKey() ?: throw KeyPermanentlyInvalidatedException(),
                    GCMParameterSpec(128, Base64.decode(iv, Base64.NO_WRAP)),
                )
            }
            cipher.doFinal(Base64.decode(blob, Base64.NO_WRAP))
        } catch (e: Exception) {
            // A wiped Keystore, a restored backup, a key past its validity end,
            // a corrupt blob: all mean the same thing to the user, which is
            // that they type their passphrase.
            Log.w(TAG, "could not resume session", e)
            clear(context)
            null
        }
    }

    /** Drop the session. Called on every explicit lock and every expiry. */
    fun clear(context: Context) {
        prefs(context).edit().clear().apply()
        try {
            KeyStore.getInstance(KEYSTORE).apply { load(null) }.deleteEntry(KEY_ALIAS)
        } catch (e: Exception) {
            Log.w(TAG, "could not delete session key", e)
        }
    }
    // #endregion

    // #region Deadline
    /**
     * Whether a session is being held and every clock still agrees it is good.
     *
     * Clears what it finds dead, so callers that need to know a session *was*
     * held must ask [hasStoredSession] first.
     */
    fun isLive(context: Context): Boolean {
        val p = prefs(context)
        if (!p.contains(PREF_BLOB)) return false
        val alive = withinBootReference(p.getLong(PREF_BOOT_REF, Long.MIN_VALUE)) &&
            SystemClock.elapsedRealtime() < p.getLong(PREF_EXPIRES_ELAPSED, 0L) &&
            System.currentTimeMillis() < p.getLong(PREF_EXPIRES, 0L)
        if (!alive) clear(context)
        return alive
    }

    /**
     * Wall clock minus time since boot: constant for a boot, and moved by any
     * adjustment to either. A mismatch means the device rebooted or the date
     * was changed, and a session should not survive either.
     */
    private fun bootRef(): Long = System.currentTimeMillis() - SystemClock.elapsedRealtime()

    private fun withinBootReference(stored: Long): Boolean =
        stored != Long.MIN_VALUE && Math.abs(bootRef() - stored) <= BOOT_REF_TOLERANCE_MS
    // #endregion

    // #region Keystore
    /**
     * A fresh key that the platform itself will stop honouring at [expiresAtMs].
     *
     * No user authentication on this one, unlike quick-unlock's key: the point
     * of the grace period is that it does not ask. The validity end is what
     * keeps that from meaning "forever" if the preferences holding the deadline
     * are ever tampered with.
     */
    private fun mintKey(expiresAtMs: Long): SecretKey {
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE)
        generator.init(
            KeyGenParameterSpec.Builder(
                KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(256)
                .setUserAuthenticationRequired(false)
                .setKeyValidityEnd(Date(expiresAtMs))
                .build(),
        )
        return generator.generateKey()
    }

    private fun loadKey(): SecretKey? =
        (KeyStore.getInstance(KEYSTORE).apply { load(null) }.getKey(KEY_ALIAS, null)) as? SecretKey
    // #endregion
}
