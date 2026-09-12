package org.zerokosh.app.data

// #region Imports
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.UUID
// #endregion

/**
 * The slice of [Prefs] the vault repository needs.
 *
 * Extracted so the repository can be exercised without Android: [Prefs] itself
 * is backed by EncryptedSharedPreferences and needs a Context, which is what
 * kept the app module's single most important class untestable.
 */
interface VaultPrefs {
    val deviceId: String
    var kdfOps: Long
    var kdfMem: Long
    var failedAttempts: Int
    var cooldownUntilMs: Long
    var cooldownSeconds: Int
    var syncFolderUri: String

    /** Set by the repository on every successful unlock — see [Prefs.secretIsPin]. */
    var secretIsPin: Boolean
}

/**
 * §6.2/§6.4: EncryptedSharedPreferences for small non-vault state only
 * (device id, KDF params, UI settings). The vault itself NEVER lives here.
 *
 * The library is deprecated with no drop-in replacement, and the suppression is
 * deliberate rather than neglect. Nothing stored here is a secret — the value is
 * defence in depth over a device id and a theme choice — while migrating the
 * store would strand every existing install's prefs, including onboardingDone,
 * which would silently re-onboard people who already have a vault. The cost of
 * moving is real and immediate; the cost of staying is a warning. Revisit when
 * a replacement exists, and migrate the keys rather than dropping them.
 */
@Suppress("DEPRECATION")
/** The one definition of what counts as a PIN, used wherever a secret is set. */
fun looksLikePin(secret: String): Boolean = secret.length == 6 && secret.all(Char::isDigit)

/**
 * The same question of a secret still in its bytes. Deliberately not
 * `String(secret)`: that would copy the passphrase onto the heap to count its
 * digits, and nothing here needs it as text.
 */
fun looksLikePin(secret: ByteArray): Boolean =
    secret.size == 6 && secret.all { it >= '0'.code.toByte() && it <= '9'.code.toByte() }

class Prefs(context: Context) : VaultPrefs {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "zerokosh_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    override val deviceId: String
        get() = prefs.getString(KEY_DEVICE_ID, null) ?: UUID.randomUUID().toString()
            .also { prefs.edit().putString(KEY_DEVICE_ID, it).apply() }

    override var kdfOps: Long
        get() = prefs.getLong(KEY_KDF_OPS, 0)
        set(v) = prefs.edit().putLong(KEY_KDF_OPS, v).apply()

    override var kdfMem: Long
        get() = prefs.getLong(KEY_KDF_MEM, 0)
        set(v) = prefs.edit().putLong(KEY_KDF_MEM, v).apply()

    /** §5.2: minutes in background before auto-lock. 0 = immediately. Default 1. */
    var autoLockMinutes: Int
        get() = prefs.getInt(KEY_AUTOLOCK, 1)
        set(v) = prefs.edit().putInt(KEY_AUTOLOCK, v).apply()

    var languageTag: String
        get() = prefs.getString(KEY_LANG, "") ?: ""
        set(v) = prefs.edit().putString(KEY_LANG, v).apply()

    var themeOption: Int
        get() = prefs.getInt(KEY_THEME, 0)
        set(v) = prefs.edit().putInt(KEY_THEME, v).apply()

    var onboardingDone: Boolean
        get() = prefs.getBoolean(KEY_ONBOARDED, false)
        set(v) = prefs.edit().putBoolean(KEY_ONBOARDED, v).apply()

    /** §5.5: default OFF — vault screens excluded from screenshots/recents. */
    var allowScreenshots: Boolean
        get() = prefs.getBoolean(KEY_ALLOW_SHOTS, false)
        set(v) = prefs.edit().putBoolean(KEY_ALLOW_SHOTS, v).apply()

    var quickUnlockEnabled: Boolean
        get() = prefs.getBoolean(KEY_QUICK_UNLOCK, false)
        set(v) = prefs.edit().putBoolean(KEY_QUICK_UNLOCK, v).apply()

    /**
     * Whether the vault opens with a 6-digit PIN rather than a passphrase.
     *
     * The lock screen has to name the thing it is asking for before anything is
     * decrypted, so this cannot live inside the vault. It records the *shape* of
     * the secret, not the secret: an attacker who can read this file can read
     * the vault file beside it, and six digits is the first thing anyone would
     * try against a vault anyway. False until something says otherwise, so an
     * install that predates this says "passphrase" until the first typed unlock
     * settles it.
     */
    override var secretIsPin: Boolean
        get() = prefs.getBoolean(KEY_SECRET_IS_PIN, false)
        set(v) = prefs.edit().putBoolean(KEY_SECRET_IS_PIN, v).apply()

    // §5.1 S13: failed-attempt cooldown, doubling — survives process death.
    override var failedAttempts: Int
        get() = prefs.getInt(KEY_FAILS, 0)
        set(v) = prefs.edit().putInt(KEY_FAILS, v).apply()

    override var cooldownUntilMs: Long
        get() = prefs.getLong(KEY_COOLDOWN, 0)
        set(v) = prefs.edit().putLong(KEY_COOLDOWN, v).apply()

    override var cooldownSeconds: Int
        get() = prefs.getInt(KEY_COOLDOWN_LEN, 30)
        set(v) = prefs.edit().putInt(KEY_COOLDOWN_LEN, v).apply()

    /** SAF tree uri of the user-chosen sync folder (§5.6); empty = app-private storage. */
    /** BV-02: ask for POST_NOTIFICATIONS once, and only when a dated record exists. */
    var notificationAsked: Boolean
        get() = prefs.getBoolean(KEY_NOTIF_ASKED, false)
        set(v) = prefs.edit().putBoolean(KEY_NOTIF_ASKED, v).apply()

    override var syncFolderUri: String
        get() = prefs.getString(KEY_SYNC_URI, "") ?: ""
        set(v) = prefs.edit().putString(KEY_SYNC_URI, v).apply()

    /**
     * Whether the user got a Recovery Kit out of the app and proved it.
     *
     * Set only by the confirmation challenge, never by the Save button alone —
     * tapping Save and then cancelling the file picker is the ordinary way to
     * end up with nothing, and that is exactly the case this flag exists to
     * catch. False is not a failure state; it is a standing item in Vault
     * Review until the user deals with it.
     */
    var recoveryKitSaved: Boolean
        get() = prefs.getBoolean(KEY_KIT_SAVED, false)
        set(v) = prefs.edit().putBoolean(KEY_KIT_SAVED, v).apply()

    /** Dismissing the home banner hides it until the next reminder falls due. */
    var kitBannerSnoozedUntilMs: Long
        get() = prefs.getLong(KEY_KIT_SNOOZE, 0)
        set(v) = prefs.edit().putLong(KEY_KIT_SNOOZE, v).apply()

    /**
     * When the passphrase was last actually typed, rather than stood in for by
     * a fingerprint. Quick unlock means this can otherwise be months ago
     * without anybody noticing, which is how a vault becomes unopenable while
     * appearing to work perfectly.
     */
    var lastPassphraseUseMs: Long
        get() = prefs.getLong(KEY_LAST_PASS_USE, 0)
        set(v) = prefs.edit().putLong(KEY_LAST_PASS_USE, v).apply()

    /** How many kit nudges have gone out, so day 7 and day 30 fire once each. */
    var kitRemindersSent: Int
        get() = prefs.getInt(KEY_KIT_REMINDERS, 0)
        set(v) = prefs.edit().putInt(KEY_KIT_REMINDERS, v).apply()

    /** Set when the vault is created, so day-7 and day-30 nudges have an origin. */
    var vaultCreatedMs: Long
        get() = prefs.getLong(KEY_VAULT_CREATED, 0)
        set(v) = prefs.edit().putLong(KEY_VAULT_CREATED, v).apply()

    private companion object {
        const val KEY_DEVICE_ID = "device_id"
        const val KEY_SECRET_IS_PIN = "secret_is_pin"
        const val KEY_KDF_OPS = "kdf_ops"
        const val KEY_KDF_MEM = "kdf_mem"
        const val KEY_AUTOLOCK = "auto_lock_minutes"
        const val KEY_LANG = "language_tag"
        const val KEY_ONBOARDED = "onboarding_done"
        const val KEY_ALLOW_SHOTS = "allow_screenshots"
        const val KEY_QUICK_UNLOCK = "quick_unlock_enabled"
        const val KEY_FAILS = "failed_attempts"
        const val KEY_COOLDOWN = "cooldown_until_ms"
        const val KEY_COOLDOWN_LEN = "cooldown_seconds"
        const val KEY_SYNC_URI = "sync_folder_uri"
        const val KEY_NOTIF_ASKED = "notification_asked"
        const val KEY_THEME = "theme_option"
        const val KEY_KIT_SAVED = "recovery_kit_saved"
        const val KEY_KIT_SNOOZE = "kit_banner_snoozed_until_ms"
        const val KEY_LAST_PASS_USE = "last_passphrase_use_ms"
        const val KEY_VAULT_CREATED = "vault_created_ms"
        const val KEY_KIT_REMINDERS = "kit_reminders_sent"
    }
}
