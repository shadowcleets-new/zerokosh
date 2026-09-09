/**
 * @file ZerokoshCredentialProviderService.kt
 * @description Credential Manager provider (API 34+), alongside the autofill
 *              service rather than instead of it.
 *
 * Android 14 added a second, parallel way for an app to ask for a credential.
 * An app that calls CredentialManager.getCredential() never reaches
 * AutofillService at all, so on those screens Zerokosh simply was not offered —
 * silently, with nothing to see and nothing to fix from the user's side. That
 * share only grows as apps adopt Credential Manager, and it is the only route
 * to passkeys.
 *
 * Autofill stays. It is the only path below API 34, which is most of the
 * install base, and plenty of apps will keep using it above 34. The two are
 * separate switches in system settings and can both be on.
 *
 * The shape of the API is worth stating, because it is not autofill's. Nothing
 * here returns a password. The service returns *entries* — a row, a label, and
 * a PendingIntent — and the credential itself is only produced later, by
 * [CredentialEntryActivity], after the user has picked that row. That is what
 * keeps a locked vault from having to decide anything at request time.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. SERVICE
 * 3. GET
 * 4. CREATE
 * 5. SESSION WINDOW
 */
package org.zerokosh.app.credentials

// #region Imports
import android.os.Build
import android.os.CancellationSignal
import android.os.OutcomeReceiver
import androidx.annotation.RequiresApi
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.provider.AuthenticationAction
import androidx.credentials.provider.BeginCreateCredentialRequest
import androidx.credentials.provider.BeginCreateCredentialResponse
import androidx.credentials.provider.BeginGetCredentialRequest
import androidx.credentials.provider.BeginGetCredentialResponse
import androidx.credentials.provider.BeginGetPasswordOption
import androidx.credentials.provider.BeginGetPublicKeyCredentialOption
import androidx.credentials.provider.CreateEntry
import androidx.credentials.provider.CredentialProviderService
import androidx.credentials.provider.ProviderClearCredentialStateRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.data.SessionKeeper
import org.zerokosh.app.data.VaultState
// #endregion

// #region Service
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
class ZerokoshCredentialProviderService : CredentialProviderService() {

    // Matching reads the vault, and resuming a session decrypts it. Neither is
    // work for the binder thread the framework calls us on — the same lesson
    // the autofill service learned the hard way.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    // #region Get
    /**
     * Which rows to show for a sign-in, without opening any of them.
     *
     * A locked vault answers with an [AuthenticationAction] instead of entries:
     * the system draws it as a row that unlocks first and comes back for the
     * real list, which is the Credential Manager spelling of the autofill
     * service's "Unlock Zerokosh to fill".
     */
    override fun onBeginGetCredentialRequest(
        request: BeginGetCredentialRequest,
        cancellationSignal: CancellationSignal,
        callback: OutcomeReceiver<BeginGetCredentialResponse, GetCredentialException>,
    ) {
        val app = application as ZerokoshApp
        val wanted = request.beginGetCredentialOptions
        val asksForSomethingWeHave = wanted.any {
            it is BeginGetPasswordOption || it is BeginGetPublicKeyCredentialOption
        }
        if (!asksForSomethingWeHave) {
            callback.onResult(BeginGetCredentialResponse())
            return
        }

        val job = scope.launch {
            applySessionWindow(app)
            val response = if (app.repository.state.value != VaultState.Unlocked) {
                BeginGetCredentialResponse(
                    authenticationActions = listOf(
                        AuthenticationAction(
                            getString(R.string.scr_autofill_unlock_first),
                            CredentialEntries.entryIntent(
                                this@ZerokoshCredentialProviderService,
                                CredentialEntryActivity.ACTION_UNLOCK_AND_GET,
                            ),
                        ),
                    ),
                )
            } else {
                BeginGetCredentialResponse(
                    credentialEntries = CredentialEntries.entriesFor(
                        this@ZerokoshCredentialProviderService,
                        app,
                        request,
                    ),
                )
            }
            callback.onResult(response)
        }
        cancellationSignal.setOnCancelListener { job.cancel() }
    }

    // #endregion

    // #region Create
    /**
     * The row offered when an app wants to store a new password.
     *
     * One entry, because there is one vault. The record is not written here —
     * [CredentialEntryActivity] does that once the user has chosen this row,
     * unlocking first if it has to.
     */
    override fun onBeginCreateCredentialRequest(
        request: BeginCreateCredentialRequest,
        cancellationSignal: CancellationSignal,
        callback: OutcomeReceiver<BeginCreateCredentialResponse, CreateCredentialException>,
    ) {
        callback.onResult(
            BeginCreateCredentialResponse(
                createEntries = listOf(
                    // The system renders this as "Save to <name>", so the name
                    // is the whole label it needs.
                    CreateEntry(
                        getString(R.string.app_name),
                        CredentialEntries.entryIntent(this, CredentialEntryActivity.ACTION_CREATE),
                    ),
                ),
            ),
        )
    }

    /**
     * Nothing to clear. Zerokosh keeps no "which account signed in last" state
     * for other apps — the vault is the only record, and forgetting a
     * credential is something the user does in the app.
     */
    override fun onClearCredentialStateRequest(
        request: ProviderClearCredentialStateRequest,
        cancellationSignal: CancellationSignal,
        callback: OutcomeReceiver<Void?, ClearCredentialException>,
    ) {
        callback.onResult(null)
    }
    // #endregion

    // #region Session window
    /**
     * Bring the vault into line with the auto-lock window, exactly as the
     * autofill service does and for the same reason: this service can be the
     * only thing keeping the process alive, and nothing in it runs on a timer.
     */
    private suspend fun applySessionWindow(app: ZerokoshApp) {
        val held = SessionKeeper.hasStoredSession(this)
        val live = SessionKeeper.isLive(this)
        val unlocked = app.repository.state.value == VaultState.Unlocked
        if (held && !live && unlocked) {
            app.repository.lock()
            return
        }
        if (!unlocked && live) app.resumeSessionIfLive()
    }

    // #endregion
}
// #endregion
