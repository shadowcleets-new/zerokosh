package org.bharatvault.app.data

// #region Imports
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.UUID
// #endregion

/**
 * §6.2/§6.4: EncryptedSharedPreferences for small non-vault state only
 * (device id, KDF params, UI settings). The vault itself NEVER lives here.
 */
class Prefs(context: Context) {

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "bharatvault_prefs",
        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
    )

    val deviceId: String
        get() = prefs.getString(KEY_DEVICE_ID, null) ?: UUID.randomUUID().toString()
            .also { prefs.edit().putString(KEY_DEVICE_ID, it).apply() }

    var kdfOps: Long
        get() = prefs.getLong(KEY_KDF_OPS, 0)
        set(v) = prefs.edit().putLong(KEY_KDF_OPS, v).apply()

    var kdfMem: Long
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

    // §5.1 S13: failed-attempt cooldown, doubling — survives process death.
    var failedAttempts: Int
        get() = prefs.getInt(KEY_FAILS, 0)
        set(v) = prefs.edit().putInt(KEY_FAILS, v).apply()

    var cooldownUntilMs: Long
        get() = prefs.getLong(KEY_COOLDOWN, 0)
        set(v) = prefs.edit().putLong(KEY_COOLDOWN, v).apply()

    var cooldownSeconds: Int
        get() = prefs.getInt(KEY_COOLDOWN_LEN, 30)
        set(v) = prefs.edit().putInt(KEY_COOLDOWN_LEN, v).apply()

    /** SAF tree uri of the user-chosen sync folder (§5.6); empty = app-private storage. */
    /** BV-02: ask for POST_NOTIFICATIONS once, and only when a dated record exists. */
    var notificationAsked: Boolean
        get() = prefs.getBoolean(KEY_NOTIF_ASKED, false)
        set(v) = prefs.edit().putBoolean(KEY_NOTIF_ASKED, v).apply()

    var syncFolderUri: String
        get() = prefs.getString(KEY_SYNC_URI, "") ?: ""
        set(v) = prefs.edit().putString(KEY_SYNC_URI, v).apply()

    private companion object {
        const val KEY_DEVICE_ID = "device_id"
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
    }
}
