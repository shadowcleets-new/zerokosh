/**
 * @file CredentialEntryActivity.kt
 * @description Where a Credential Manager row actually turns into a credential.
 *
 * The provider service only ever returns rows — a label and a PendingIntent.
 * Nothing it returns contains a password, which is what lets a locked vault
 * answer a request at all. This activity is the other half: the user has picked
 * a row, so now the vault is opened if it needs to be and the credential is
 * handed back.
 *
 * Three arrivals, all of them ending in a result the platform reads:
 *   - ACTION_GET: a specific saved login was picked.
 *   - ACTION_UNLOCK_AND_GET: the vault was locked, so the row said "unlock"
 *     instead. Unlocking hands back the whole list, not one credential.
 *   - ACTION_CREATE: an app is offering a password to store.
 *
 * Same sheet as the autofill unlock, and for the same reason: this floats over
 * someone else's screen, so it has no business being the whole app.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. ACTIVITY
 * 3. UNLOCK
 * 4. GET
 * 5. CREATE
 */
package org.zerokosh.app.credentials

// #region Imports
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.res.stringResource
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CreatePasswordResponse
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.provider.BeginGetCredentialResponse
import androidx.credentials.provider.PendingIntentHandler
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.autofill.AutofillFill
import org.zerokosh.app.autofill.PendingSave
import org.zerokosh.app.autofill.loginRecordFor
import org.zerokosh.app.data.UnlockOutcome
import org.zerokosh.app.data.VaultState
import org.zerokosh.app.quickunlock.QuickUnlockManager
import org.zerokosh.app.ui.UnlockSheet
import org.zerokosh.app.ui.theme.ZerokoshTheme
import org.zerokosh.core.vault.UnlockResult
// #endregion

// #region Activity
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class CredentialEntryActivity : FragmentActivity() {

    private val app: ZerokoshApp get() = application as ZerokoshApp

    override fun onDestroy() {
        app.exitProviderSheet()
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Not the user returning to Zerokosh: this serves another app's form.
        app.enterProviderSheet()
        // This window is only ever about a password, whatever the user's
        // screenshot setting says.
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        lifecycleScope.launch {
            if (!ensureUnlocked()) return@launch
            when (intent.action) {
                ACTION_GET -> deliverPassword()
                ACTION_UNLOCK_AND_GET -> deliverEntries()
                ACTION_CREATE -> saveCredential()
                else -> cancel()
            }
        }
    }

    private fun cancel() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
    // #endregion

    // #region Unlock
    /**
     * Open the vault, quietly if the grace period already covers it.
     *
     * Returns false when the sheet has been put on screen instead — the caller
     * stops, and the sheet's own success path re-enters [onCreate]'s dispatch.
     */
    private suspend fun ensureUnlocked(): Boolean {
        if (app.repository.state.value == VaultState.Unlocked || app.resumeSessionIfLive()) return true
        if (QuickUnlockManager.isEnrolled(this)) {
            val result = QuickUnlockManager.unlock(this, app)
            if (result is UnlockResult.Success) {
                app.repository.adoptBiometricUnlock(result)
                return true
            }
        }
        showSheet()
        return false
    }

    private fun showSheet() {
        setContent {
            ZerokoshTheme {
                UnlockSheet(
                    title = stringResource(R.string.scr_autofill_unlock_first),
                    onUnlock = { entered, onWrong ->
                        lifecycleScope.launch {
                            val outcome = app.repository.unlockWithPassphrase(entered.toByteArray())
                            if (outcome != UnlockOutcome.SUCCESS) {
                                onWrong()
                                return@launch
                            }
                            when (intent.action) {
                                ACTION_GET -> deliverPassword()
                                ACTION_UNLOCK_AND_GET -> deliverEntries()
                                ACTION_CREATE -> saveCredential()
                                else -> cancel()
                            }
                        }
                    },
                    onCancel = ::cancel,
                )
            }
        }
    }
    // #endregion

    // #region Get
    /** The one login the user picked. */
    private fun deliverPassword() {
        val id = intent.getStringExtra(EXTRA_RECORD_ID)
        val record = app.repository.body.value?.records?.firstOrNull { it.uuid == id }
        if (record == null) {
            cancel()
            return
        }
        val response = GetCredentialResponse(
            PasswordCredential(
                id = AutofillFill.usernameOf(record).ifBlank { record.title },
                password = AutofillFill.passwordOf(record),
            ),
        )
        val result = Intent()
        PendingIntentHandler.setGetCredentialResponse(result, response)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    /**
     * The list, now that the vault is open.
     *
     * An AuthenticationAction is answered with a whole BeginGetCredentialResponse
     * rather than a credential: the user asked to unlock, not to pick, and the
     * picking happens next in the system's own sheet.
     */
    private fun deliverEntries() {
        val request = PendingIntentHandler.retrieveBeginGetCredentialRequest(intent)
        if (request == null) {
            cancel()
            return
        }
        val result = Intent()
        PendingIntentHandler.setBeginGetCredentialResponse(
            result,
            BeginGetCredentialResponse(
                credentialEntries = CredentialEntries.passwordEntries(this, app, request),
            ),
        )
        setResult(Activity.RESULT_OK, result)
        finish()
    }
    // #endregion

    // #region Create
    /** Store a password an app has just had the user choose. */
    private suspend fun saveCredential() {
        val request = PendingIntentHandler.retrieveProviderCreateCredentialRequest(intent)
        val password = request?.callingRequest as? CreatePasswordRequest
        if (password == null) {
            cancel()
            return
        }
        val credential = PendingSave.Credential(
            username = password.id,
            password = password.password,
            webDomain = null,
            packageName = request.callingAppInfo.packageName,
        )
        // getOrNull()?.isSuccess, not runCatching{}.isSuccess: upsertRecord
        // *returns* a Result rather than throwing, so the outer runCatching is
        // happy even when the save failed. That reports a password stored when
        // it was dropped, which is the worst answer available here.
        val saved = runCatching {
            app.repository.upsertRecord(loginRecordFor(app, credential))
        }.getOrNull()?.isSuccess == true
        if (!saved) {
            cancel()
            return
        }
        val result = Intent()
        PendingIntentHandler.setCreateCredentialResponse(result, CreatePasswordResponse())
        setResult(Activity.RESULT_OK, result)
        finish()
    }
    // #endregion

    companion object {
        const val ACTION_GET = "org.zerokosh.app.credentials.GET"
        const val ACTION_UNLOCK_AND_GET = "org.zerokosh.app.credentials.UNLOCK_AND_GET"
        const val ACTION_CREATE = "org.zerokosh.app.credentials.CREATE"
        const val EXTRA_RECORD_ID = "org.zerokosh.app.credentials.RECORD_ID"
    }
}
