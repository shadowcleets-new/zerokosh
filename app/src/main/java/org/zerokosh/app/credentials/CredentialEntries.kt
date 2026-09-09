/**
 * @file CredentialEntries.kt
 * @description Turning saved logins into Credential Manager rows.
 *
 * Shared by the provider service, which builds the rows for a sign-in, and the
 * entry activity, which has to build them a second time after an unlock —
 * because an AuthenticationAction hands back a whole response, not a single
 * credential.
 */
package org.zerokosh.app.credentials

// #region Imports
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.credentials.provider.BeginGetCredentialRequest
import androidx.credentials.provider.BeginGetPasswordOption
import androidx.credentials.provider.CallingAppInfo
import androidx.credentials.provider.BeginGetPublicKeyCredentialOption
import androidx.credentials.provider.CredentialEntry
import androidx.credentials.provider.PasswordCredentialEntry
import androidx.credentials.provider.PublicKeyCredentialEntry
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.autofill.AutofillFill
import org.zerokosh.app.autofill.FieldTargets
import org.zerokosh.core.model.Record
// #endregion

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
internal object CredentialEntries {

    /** Same ceiling the autofill dropdown uses, for the same reason. */
    const val MAX_ENTRIES = 5

    /**
     * Who is asking, in the terms the vault already matches on.
     *
     * Package name only. A browser asking on a website's behalf can report that
     * site, but reading it means calling CallingAppInfo.getOrigin with a list of
     * browsers we are prepared to believe — a trust list, and not one to adopt
     * as a side effect of adding a library. Until that decision is made
     * deliberately, a browser is treated as the app it is, which is honest
     * rather than wrong: it matches less, never something it shouldn't.
     *
     * Reusing [FieldTargets] is the point — one matching rule serves both this
     * and the autofill service, so a login that fills in one fills in the other.
     */
    fun targetsFor(info: CallingAppInfo?): FieldTargets = FieldTargets(
        packageName = info?.packageName.orEmpty(),
        webDomain = null,
        usernameId = null,
        passwordId = null,
    )

    /** Every row worth showing for this request: saved logins, then passkeys. */
    fun entriesFor(
        context: Context,
        app: ZerokoshApp,
        request: BeginGetCredentialRequest,
    ): List<CredentialEntry> = passwordEntries(context, app, request) +
        passkeyEntries(context, app, request)

    /** One row per saved login that matches the caller. */
    fun passwordEntries(
        context: Context,
        app: ZerokoshApp,
        request: BeginGetCredentialRequest,
    ): List<PasswordCredentialEntry> {
        val options = request.beginGetCredentialOptions.filterIsInstance<BeginGetPasswordOption>()
        if (options.isEmpty()) return emptyList()
        val matches = AutofillFill.matchRecords(app, targetsFor(request.callingAppInfo))
        return matches.take(MAX_ENTRIES).flatMap { record ->
            options.map { option -> entryFor(context, record, option) }
        }
    }

    /**
     * One row per passkey held for the relying party that is asking.
     *
     * Matched on the rpId the request names rather than on the caller's package,
     * because that is what a passkey is scoped to — the site, not the app that
     * happens to be showing it.
     */
    fun passkeyEntries(
        context: Context,
        app: ZerokoshApp,
        request: BeginGetCredentialRequest,
    ): List<PublicKeyCredentialEntry> {
        val options = request.beginGetCredentialOptions
            .filterIsInstance<BeginGetPublicKeyCredentialOption>()
        return options.flatMap { option ->
            val rpId = Passkeys.parseRequestOptions(option.requestJson)?.rpId.orEmpty()
            if (rpId.isBlank()) return@flatMap emptyList()
            Passkeys.passkeysFor(app, rpId).take(MAX_ENTRIES).map { record ->
                PublicKeyCredentialEntry.Builder(
                    context,
                    record.fields["username"].orEmpty().ifBlank { record.title },
                    entryIntent(context, CredentialEntryActivity.ACTION_GET_PASSKEY, record.uuid),
                    option,
                )
                    .setDisplayName(record.title)
                    .build()
            }
        }
    }

    private fun entryFor(
        context: Context,
        record: Record,
        option: BeginGetPasswordOption,
    ): PasswordCredentialEntry {
        val username = AutofillFill.usernameOf(record).ifBlank { record.title }
        return PasswordCredentialEntry.Builder(
            context,
            username,
            entryIntent(context, CredentialEntryActivity.ACTION_GET, record.uuid),
            option,
        )
            .setDisplayName(record.title)
            .build()
    }

    /**
     * FLAG_MUTABLE because the platform writes the request into this Intent on
     * its way through, and a distinct request code per row so FLAG_UPDATE_CURRENT
     * updates the one it means rather than rewriting every row to whichever was
     * built last.
     *
     * No FLAG_ACTIVITY_NEW_TASK, ever: an activity started into a new task
     * cannot return a result, and returning a result is the entire job here.
     * That mistake cost the autofill path its whole unlock-then-fill flow.
     */
    fun entryIntent(context: Context, action: String, recordId: String? = null): PendingIntent {
        val intent = Intent(context, CredentialEntryActivity::class.java)
            .setAction(action)
            .putExtra(CredentialEntryActivity.EXTRA_RECORD_ID, recordId)
        return PendingIntent.getActivity(
            context,
            (action + recordId.orEmpty()).hashCode() and 0x0000FFFF,
            intent,
            PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }
}
