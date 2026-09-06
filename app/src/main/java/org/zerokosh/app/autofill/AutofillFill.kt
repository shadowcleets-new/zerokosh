/**
 * @file AutofillFill.kt
 * @description Building a FillResponse, and the handshake that lets an unlock
 *              round trip back into the form the user was standing in (§6.7).
 *
 * All of this used to live privately inside ZerokoshAutofillService, which was
 * fine until the locked case needed the same code from an Activity. The locked
 * case is the common one — the vault re-locks a minute after you leave it — and
 * it was the one that did not work: the "Unlock Zerokosh to fill" dataset opened
 * the app and stopped there. Nothing ever returned a result to the platform, so
 * the user unlocked, looked at a still-empty form, and had to go back and tap
 * the field a second time.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. FIELD TARGETS
 * 3. RESPONSE BUILDING
 * 4. AUTH HANDSHAKE
 * 5. DOMAIN MATCHING
 */
package org.zerokosh.app.autofill

// #region Imports
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.service.autofill.Dataset
import android.service.autofill.FillResponse
import android.service.autofill.InlinePresentation
import android.service.autofill.SaveInfo
import android.view.View
import android.view.autofill.AutofillId
import android.view.autofill.AutofillManager
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.core.model.Record
import java.net.URI
// #endregion

// #region Field targets
/**
 * Which fields a request is about, and where it came from.
 *
 * Ids and an origin only. Nothing here is secret, which is what makes it safe to
 * put in the Intent that survives into the recents task description — the typed
 * values travel through [PendingSave] in memory instead, and always did.
 */
data class FieldTargets(
    val packageName: String,
    val webDomain: String?,
    val usernameId: AutofillId?,
    val passwordId: AutofillId?,
) {
    val ids: Array<AutofillId> get() = listOfNotNull(usernameId, passwordId).toTypedArray()

    val isEmpty: Boolean get() = usernameId == null && passwordId == null
}
// #endregion

object AutofillFill {

    // #region Response building
    /**
     * The dropdown row. Built from our own layout because the platform's
     * simple_list_item_2 is rooted in TwoLineListItem, which RemoteViews will not
     * inflate — see the comment in autofill_suggestion.xml.
     */
    fun suggestionView(context: Context, title: String, subtitle: String?): RemoteViews =
        RemoteViews(context.packageName, R.layout.autofill_suggestion).apply {
            setTextViewText(R.id.autofill_title, title)
            if (subtitle.isNullOrBlank()) {
                setViewVisibility(R.id.autofill_subtitle, View.GONE)
            } else {
                setTextViewText(R.id.autofill_subtitle, subtitle)
            }
        }

    /** The first non-empty of the field names this template family might use. */
    private fun firstOf(record: Record, vararg keys: String): String =
        keys.firstNotNullOfOrNull { record.fields[it]?.takeIf(String::isNotBlank) }.orEmpty()

    fun usernameOf(record: Record): String =
        firstOf(record, "username", "registered_email", "registered_mobile")

    fun passwordOf(record: Record): String =
        firstOf(record, "password", "password_if_any", "login_password")

    fun datasetFor(
        context: Context,
        targets: FieldTargets,
        record: Record,
        inline: InlinePresentation? = null,
    ): Dataset {
        val username = usernameOf(record)
        val builder = Dataset.Builder(suggestionView(context, record.title, username))
        targets.usernameId?.let { builder.setValue(it, AutofillValue.forText(username)) }
        targets.passwordId?.let { builder.setValue(it, AutofillValue.forText(passwordOf(record))) }
        // Both presentations on one dataset: the platform draws the chip where the
        // IME has a strip and falls back to the dropdown where it does not.
        //
        // inlineFor() already returns null below API 30, so this is never reached
        // on an older device. The check is repeated here because that guard sits
        // in another file, where neither lint nor the next reader can see it.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            inline?.let { builder.setInlinePresentation(it) }
        }
        return builder.build()
    }

    /** Offered on every response so a new credential can still be captured. */
    fun saveInfoFor(targets: FieldTargets): SaveInfo? {
        val username = targets.usernameId ?: return null
        val password = targets.passwordId ?: return null
        return SaveInfo.Builder(
            SaveInfo.SAVE_DATA_TYPE_USERNAME or SaveInfo.SAVE_DATA_TYPE_PASSWORD,
            arrayOf(username, password),
        ).build()
    }

    /**
     * Everything an unlocked vault has to offer for [targets], or null when that
     * is nothing at all.
     *
     * Null rather than an empty response on purpose: FillResponse.build() throws
     * IllegalStateException on a response carrying neither a dataset nor a
     * SaveInfo, and that combination is reachable whenever a page shows only one
     * of the two fields on a site the vault has never seen — the first step of
     * every two-step login. The throw would propagate out of onFillRequest and
     * take the whole app process with it.
     */
    fun responseFor(context: Context, app: ZerokoshApp, targets: FieldTargets): FillResponse? {
        val matches = matchRecords(app, targets)
        val saveInfo = saveInfoFor(targets)
        if (matches.isEmpty() && saveInfo == null) return null
        val response = FillResponse.Builder()
        matches.take(MAX_DATASETS).forEach { response.addDataset(datasetFor(context, targets, it)) }
        saveInfo?.let(response::setSaveInfo)
        return response.build()
    }
    // #endregion

    // #region Auth handshake
    /**
     * The response shown while the vault is shut: no values, just a way in.
     *
     * Response-level authentication rather than a dataset-level one because after
     * unlocking there may be several matching credentials to choose between, and
     * only a FillResponse can carry more than one.
     */
    fun lockedResponse(context: Context, targets: FieldTargets): FillResponse =
        FillResponse.Builder()
            .setAuthentication(
                targets.ids,
                authIntentSender(context, targets),
                suggestionView(context, context.getString(R.string.scr_autofill_unlock_first), null),
            )
            .build()

    /**
     * FLAG_MUTABLE because the platform writes the request metadata into this
     * Intent on its way through; an immutable one is rejected outright.
     */
    private fun authIntentSender(context: Context, targets: FieldTargets) =
        PendingIntent.getActivity(
            context,
            AUTH_REQUEST_CODE,
            authIntent(context, targets),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE,
        ).intentSender

    fun authIntent(context: Context, targets: FieldTargets): Intent =
        Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(EXTRA_AUTH, true)
            putExtra(EXTRA_PACKAGE, targets.packageName)
            putExtra(EXTRA_DOMAIN, targets.webDomain)
            putExtra(EXTRA_USERNAME_ID, targets.usernameId)
            putExtra(EXTRA_PASSWORD_ID, targets.passwordId)
        }

    /** The targets an auth Intent carries, or null when it is not one of ours. */
    @Suppress("DEPRECATION")
    fun targetsFrom(intent: Intent?): FieldTargets? {
        if (intent == null || !intent.getBooleanExtra(EXTRA_AUTH, false)) return null
        val targets = FieldTargets(
            packageName = intent.getStringExtra(EXTRA_PACKAGE).orEmpty(),
            webDomain = intent.getStringExtra(EXTRA_DOMAIN),
            usernameId = intent.getParcelableExtra(EXTRA_USERNAME_ID),
            passwordId = intent.getParcelableExtra(EXTRA_PASSWORD_ID),
        )
        return targets.takeUnless { it.isEmpty }
    }

    /**
     * Hand the unlocked vault's answer back to the form the user came from.
     *
     * RESULT_CANCELED when there is nothing to offer is not a failure — it tells
     * the platform to carry on without a fill, which is right for a vault that
     * holds no credential for this app.
     */
    fun deliver(activity: Activity, app: ZerokoshApp, targets: FieldTargets) {
        val response = responseFor(activity, app, targets)
        if (response == null) {
            activity.setResult(Activity.RESULT_CANCELED)
            return
        }
        activity.setResult(
            Activity.RESULT_OK,
            Intent().putExtra(AutofillManager.EXTRA_AUTHENTICATION_RESULT, response),
        )
    }
    // #endregion

    // #region Domain matching
    fun matchRecords(app: ZerokoshApp, targets: FieldTargets): List<Record> {
        val domain = targets.webDomain?.lowercase()?.removePrefix("www.")
            ?: extractDomainHint(targets.packageName)
        return app.repository.body.value?.records.orEmpty()
            .filter { matches(app, it, domain, targets.packageName) }
            .sortedByDescending { it.favorite }
    }

    private fun matches(app: ZerokoshApp, rec: Record, domain: String?, pkg: String): Boolean =
        when (rec.template_id) {
            "login" -> {
                val site = rec.fields["website"].orEmpty().lowercase()
                domain != null && (site.contains(domain) || domainHost(site) == domain)
            }
            "app_profile" -> {
                val name = rec.fields["app_name"].orEmpty().lowercase()
                val mapped = app.catalog.appMap[pkg]?.lowercase()
                mapped != null && (name.contains(mapped) || mapped.contains(name))
            }
            else -> false
        }

    private fun domainHost(url: String): String? = try {
        val withScheme = if (url.contains("://")) url else "https://$url"
        URI(withScheme).host?.lowercase()?.removePrefix("www.")
    } catch (_: Exception) {
        null
    }

    /** crude: com.zomato.android → zomato */
    private fun extractDomainHint(pkg: String): String? = pkg.split('.').getOrNull(1)
    // #endregion

    private const val MAX_DATASETS = 5
    private const val AUTH_REQUEST_CODE = 1101
    private const val EXTRA_AUTH = "org.zerokosh.app.autofill.AUTH"
    private const val EXTRA_PACKAGE = "org.zerokosh.app.autofill.PACKAGE"
    private const val EXTRA_DOMAIN = "org.zerokosh.app.autofill.DOMAIN"
    private const val EXTRA_USERNAME_ID = "org.zerokosh.app.autofill.USERNAME_ID"
    private const val EXTRA_PASSWORD_ID = "org.zerokosh.app.autofill.PASSWORD_ID"
}
