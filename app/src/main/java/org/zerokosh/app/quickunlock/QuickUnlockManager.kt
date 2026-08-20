/**
 * @file QuickUnlockManager.kt
 * @description §3.4/§6.5 biometric quick-unlock: MasterKey copy encrypted by a
 *              hardware-backed AndroidKeystore AES-GCM key that requires
 *              BIOMETRIC_STRONG auth and invalidates on re-enrolment.
 *              NEVER stores the passphrase.
 */
package org.zerokosh.app.quickunlock

// #region Imports
import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.suspendCancellableCoroutine
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.R
import org.zerokosh.core.crypto.wipe
import org.zerokosh.core.vault.UnlockResult
import org.zerokosh.core.vault.VaultOperations
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlin.coroutines.resume
// #endregion

object QuickUnlockManager {

    private const val KEYSTORE = "AndroidKeyStore"
    private const val KEY_ALIAS = "zerokosh_quick_unlock"
    private const val PREF_BLOB = "quick_unlock_blob"
    private const val PREF_IV = "quick_unlock_iv"

    // #region Availability
    fun hardwareBackedBiometricsAvailable(context: Context): Boolean =
        BiometricManager.from(context).canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    // #endregion

    // #region Enable (enrolment — requires one biometric confirmation)
    /** Derives MasterKey from the passphrase, wraps it under the Keystore key. */
    suspend fun enable(activity: FragmentActivity, app: ZerokoshApp, passphrase: ByteArray): Boolean {
        if (!hardwareBackedBiometricsAvailable(activity)) return false
        val fileBytes = app.repository.store.read() ?: return false
        val masterKey = VaultOperations.deriveMasterKey(fileBytes, passphrase, app.repository.crypto)
        try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding").apply {
                init(Cipher.ENCRYPT_MODE, getOrCreateKey())
            }
            val authed = authenticate(activity, cipher) ?: return false
            val ct = authed.doFinal(masterKey)
            val prefs = rawPrefs(activity)
            prefs.edit()
                .putString(PREF_BLOB, Base64.encodeToString(ct, Base64.NO_WRAP))
                .putString(PREF_IV, Base64.encodeToString(authed.iv, Base64.NO_WRAP))
                .apply()
            app.prefs.quickUnlockEnabled = true
            return true
        } catch (e: Exception) {
            disable(activity, app)
            return false
        } finally {
            masterKey.wipe()
        }
    }
    // #endregion

    // #region Unlock
    /**
     * §6.5: BiometricPrompt + CryptoObject → decrypt MasterKey → open vault.
     * Returns null on cancel/failure; silently disables and falls back to
     * passphrase when the key was invalidated by biometric re-enrolment.
     */
    suspend fun unlock(activity: FragmentActivity, app: ZerokoshApp): UnlockResult? {
        val prefs = rawPrefs(activity)
        val blob = prefs.getString(PREF_BLOB, null) ?: return null
        val iv = prefs.getString(PREF_IV, null) ?: return null
        val cipher = try {
            Cipher.getInstance("AES/GCM/NoPadding").apply {
                init(
                    Cipher.DECRYPT_MODE,
                    loadKey() ?: throw KeyPermanentlyInvalidatedException(),
                    GCMParameterSpec(128, Base64.decode(iv, Base64.NO_WRAP)),
                )
            }
        } catch (e: KeyPermanentlyInvalidatedException) {
            // biometrics re-enrolled — delete copy, fall back to passphrase (§3.4)
            disable(activity, app)
            return null
        } catch (e: Exception) {
            disable(activity, app)
            return null
        }
        val authed = authenticate(activity, cipher) ?: return null
        return try {
            val masterKey = authed.doFinal(Base64.decode(blob, Base64.NO_WRAP))
            val fileBytes = app.repository.store.read() ?: return null
            val result = VaultOperations.unlockWithMasterKey(fileBytes, masterKey, app.repository.crypto)
            masterKey.wipe()
            result
        } catch (e: Exception) {
            null
        }
    }
    // #endregion

    // #region Disable / helpers
    fun disable(context: Context, app: ZerokoshApp) {
        rawPrefs(context).edit().remove(PREF_BLOB).remove(PREF_IV).apply()
        runCatching {
            KeyStore.getInstance(KEYSTORE).apply { load(null) }.deleteEntry(KEY_ALIAS)
        }
        app.prefs.quickUnlockEnabled = false
    }

    fun isEnrolled(context: Context): Boolean =
        rawPrefs(context).getString(PREF_BLOB, null) != null

    /** Blob is Keystore-AES-GCM ciphertext; plain prefs file is fine for it. */
    private fun rawPrefs(context: Context) =
        context.getSharedPreferences("zerokosh_quick_unlock", Context.MODE_PRIVATE)

    private fun getOrCreateKey(): SecretKey {
        loadKey()?.let { return it }
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE)
        val spec = KeyGenParameterSpec.Builder(KEY_ALIAS, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .setUserAuthenticationRequired(true)
            .setInvalidatedByBiometricEnrollment(true)
            .apply {
                if (android.os.Build.VERSION.SDK_INT >= 30) {
                    setUserAuthenticationParameters(0, KeyProperties.AUTH_BIOMETRIC_STRONG)
                } else {
                    @Suppress("DEPRECATION")
                    setUserAuthenticationValidityDurationSeconds(-1)
                }
            }
            .build()
        generator.init(spec)
        return generator.generateKey()
    }

    private fun loadKey(): SecretKey? =
        (KeyStore.getInstance(KEYSTORE).apply { load(null) }.getKey(KEY_ALIAS, null)) as? SecretKey

    private suspend fun authenticate(activity: FragmentActivity, cipher: Cipher): Cipher? =
        suspendCancellableCoroutine { cont ->
            val prompt = BiometricPrompt(
                activity,
                ContextCompat.getMainExecutor(activity),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        if (cont.isActive) cont.resume(result.cryptoObject?.cipher)
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        if (cont.isActive) cont.resume(null)
                    }
                },
            )
            val info = BiometricPrompt.PromptInfo.Builder()
                .setTitle(activity.getString(R.string.scr_lock_biometric_title))
                .setSubtitle(activity.getString(R.string.scr_lock_biometric_subtitle))
                .setNegativeButtonText(activity.getString(R.string.msg_cancel))
                .setAllowedAuthenticators(BIOMETRIC_STRONG)
                .build()
            prompt.authenticate(info, BiometricPrompt.CryptoObject(cipher))
            cont.invokeOnCancellation { runCatching { prompt.cancelAuthentication() } }
        }
    // #endregion
}
