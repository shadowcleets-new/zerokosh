/**
 * @file ZerokoshAutofillService.kt
 * @description §6.7 AutofillService. Matches login records by domain and
 *              app_profile/login by package via bundled app_map.json.
 *              NEVER uses AccessibilityService. Auth-gated datasets.
 */
package org.zerokosh.app.autofill

// #region Imports
import android.app.PendingIntent
import android.app.assist.AssistStructure
import android.content.Intent
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveInfo
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import android.widget.RemoteViews
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.data.VaultState
import org.zerokosh.core.model.Record
import java.net.URI
// #endregion

class ZerokoshAutofillService : AutofillService() {

    // Saving writes the vault, which is disk and crypto — not work for the
    // binder thread the framework calls us on.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    /** Offered on every response so a new credential can still be captured. */
    private fun saveInfoFor(parsed: Parsed): SaveInfo? {
        val username = parsed.usernameId ?: return null
        val password = parsed.passwordId ?: return null
        return SaveInfo.Builder(
            SaveInfo.SAVE_DATA_TYPE_USERNAME or SaveInfo.SAVE_DATA_TYPE_PASSWORD,
            arrayOf(username, password),
        ).build()
    }

    private fun addInline(
        builder: Dataset.Builder,
        request: FillRequest,
        index: Int,
        title: String,
        subtitle: String?,
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return
        if (index >= InlineSuggestions.maxSuggestions(request)) return
        InlineSuggestions.build(this, request, index, title, subtitle)
            ?.let { builder.setInlinePresentation(it) }
    }

    /**
     * A shut vault has nothing to offer but a way in, so the single dataset is
     * an authentication trigger rather than a value.
     */
    private fun lockedDataset(request: FillRequest, parsed: Parsed): Dataset {
        val pending = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
        val label = getString(R.string.scr_autofill_unlock_first)
        val presentation = RemoteViews(packageName, android.R.layout.simple_list_item_1).apply {
            setTextViewText(android.R.id.text1, label)
        }
        val builder = Dataset.Builder(presentation)
        parsed.usernameId?.let { builder.setValue(it, AutofillValue.forText("")) }
        parsed.passwordId?.let { builder.setValue(it, AutofillValue.forText("")) }
        addInline(builder, request, 0, label, null)
        builder.setAuthentication(pending.intentSender)
        return builder.build()
    }

    /** The first non-empty of the field names this template family might use. */
    private fun firstOf(record: Record, vararg keys: String): String =
        keys.firstNotNullOfOrNull { record.fields[it]?.takeIf(String::isNotBlank) }.orEmpty()

    private fun datasetFor(request: FillRequest, parsed: Parsed, record: Record, index: Int): Dataset {
        val username = firstOf(record, "username", "registered_email", "registered_mobile")
        val password = firstOf(record, "password", "password_if_any", "login_password")
        val presentation = RemoteViews(packageName, android.R.layout.simple_list_item_2).apply {
            setTextViewText(android.R.id.text1, record.title)
            setTextViewText(android.R.id.text2, username)
        }
        val builder = Dataset.Builder(presentation)
        parsed.usernameId?.let { builder.setValue(it, AutofillValue.forText(username)) }
        parsed.passwordId?.let { builder.setValue(it, AutofillValue.forText(password)) }
        // Both presentations on one dataset: the platform draws the chip where
        // the IME has a strip and falls back to the dropdown where it does not.
        addInline(builder, request, index, record.title, username.ifBlank { null })
        return builder.build()
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        val structure = request.fillContexts.lastOrNull()?.structure
        val parsed = structure?.let(::parseStructure)
        if (parsed == null || (parsed.usernameId == null && parsed.passwordId == null)) {
            callback.onSuccess(null)
            return
        }

        val app = application as ZerokoshApp
        val response = FillResponse.Builder()
        if (app.repository.state.value != VaultState.Unlocked) {
            callback.onSuccess(response.addDataset(lockedDataset(request, parsed)).build())
            return
        }

        val matches = matchRecords(app.repository.body.value?.records.orEmpty(), parsed.packageName, parsed.webDomain)
        matches.take(5).forEachIndexed { index, record ->
            response.addDataset(datasetFor(request, parsed, record, index))
        }
        // Offered even with no matches, so a brand new credential can be saved.
        saveInfoFor(parsed)?.let(response::setSaveInfo)
        callback.onSuccess(response.build())
    }

    /**
     * Android has already asked the user "save to Zerokosh?" and been told yes,
     * so the consent exists; this is only the writing down.
     *
     * Both previous paths reported success and saved nothing. Locked, it
     * returned immediately and dropped the credential. Unlocked, it started
     * MainActivity with an extra nothing read, and never looked at the typed
     * values at all — so the user was told their password was saved when it had
     * been discarded. Reporting failure honestly is the minimum here; actually
     * saving is the point.
     */
    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        val structure = request.fillContexts.lastOrNull()?.structure
        if (structure == null) {
            callback.onFailure(getString(R.string.scr_autofill_save_failed))
            return
        }
        val parsed = parseStructure(structure)
        if (parsed.passwordValue.isEmpty()) {
            // Nothing worth storing, and a login record without one would be a
            // record the user has to go and finish by hand.
            callback.onFailure(getString(R.string.scr_autofill_save_failed))
            return
        }

        val app = application as ZerokoshApp
        val credential = PendingSave.Credential(
            username = parsed.usernameValue,
            password = parsed.passwordValue,
            webDomain = parsed.webDomain,
            packageName = parsed.packageName,
        )

        if (app.repository.state.value != VaultState.Unlocked) {
            // The vault cannot be written shut. Hold the credential in memory —
            // never in an Intent extra — and open the app so the user can unlock;
            // ZerokoshNav finishes the save once they do.
            PendingSave.offer(credential)
            startActivity(
                Intent(this, MainActivity::class.java).apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK },
            )
            callback.onSuccess()
            return
        }

        scope.launch {
            val ok = runCatching { app.repository.upsertRecord(loginRecordFor(app, credential)) }.isSuccess
            withContext(Dispatchers.Main) {
                if (ok) callback.onSuccess()
                else callback.onFailure(getString(R.string.scr_autofill_save_failed))
            }
        }
    }

    private data class Parsed(
        val packageName: String,
        val webDomain: String?,
        val usernameId: AutofillId?,
        val passwordId: AutofillId?,
        /** Populated on a save request; empty on a fill request. */
        val usernameValue: String = "",
        val passwordValue: String = "",
    )

    /** An HTML `type=` attribute, when the node came from a web view. */
    private fun htmlType(node: AssistStructure.ViewNode): String? =
        node.htmlInfo?.attributes
            ?.firstOrNull { it.first.equals("type", true) }
            ?.second
            ?.lowercase()

    private fun isPasswordNode(node: AssistStructure.ViewNode, hints: List<String>): Boolean {
        if (hints.any { it.contains("password") }) return true
        if (htmlType(node) == "password") return true
        val type = node.inputType
        return (type and android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0 ||
            (type and android.text.InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD) != 0
    }

    private fun isUsernameNode(node: AssistStructure.ViewNode, hints: List<String>): Boolean {
        if (hints.any { it.contains("username") || it.contains("email") }) return true
        return htmlType(node) in setOf("email", "text")
    }

    /**
     * Mutable while the tree is walked, then frozen into [Parsed]. A class
     * rather than six captured locals so the walk can be a real function
     * instead of a closure nested inside its caller.
     */
    private class Scan(var packageName: String) {
        var webDomain: String? = null
        var usernameId: AutofillId? = null
        var passwordId: AutofillId? = null
        var usernameValue = ""
        var passwordValue = ""

        fun toParsed() = Parsed(packageName, webDomain, usernameId, passwordId, usernameValue, passwordValue)
    }

    /**
     * autofillValue is only populated on a save request. Reading it is what
     * makes saving possible at all: the ids alone name fields whose contents
     * were never looked at.
     */
    private fun Scan.record(node: AssistStructure.ViewNode, hints: List<String>) {
        val id = node.autofillId ?: return
        val typed = node.autofillValue?.takeIf { it.isText }?.textValue?.toString().orEmpty()
        if (isPasswordNode(node, hints)) {
            if (passwordId != null) return
            passwordId = id
            if (typed.isNotEmpty()) passwordValue = typed
            return
        }
        if (usernameId != null || !isUsernameNode(node, hints)) return
        usernameId = id
        if (typed.isNotEmpty()) usernameValue = typed
    }

    private fun walk(node: AssistStructure.ViewNode, scan: Scan) {
        val hints = node.autofillHints?.map { it.lowercase() }.orEmpty()
        node.webDomain?.let { scan.webDomain = it }
        if (scan.packageName.isEmpty()) node.idPackage?.let { scan.packageName = it }
        scan.record(node, hints)
        for (i in 0 until node.childCount) walk(node.getChildAt(i), scan)
    }

    private fun parseStructure(structure: AssistStructure): Parsed {
        val scan = Scan(structure.activityComponent?.packageName.orEmpty())
        for (i in 0 until structure.windowNodeCount) {
            walk(structure.getWindowNodeAt(i).rootViewNode, scan)
        }
        return scan.toParsed()
    }

    private fun matchRecords(records: List<Record>, packageName: String, webDomain: String?): List<Record> {
        val domain = webDomain?.lowercase()?.removePrefix("www.")
            ?: packageName.let { extractDomainHint(it) }
        val appMap = (application as ZerokoshApp).catalog.appMap
        return records.filter { rec ->
            when (rec.template_id) {
                "login" -> {
                    val site = rec.fields["website"].orEmpty().lowercase()
                    domain != null && (site.contains(domain) || domainHost(site) == domain)
                }
                "app_profile" -> {
                    val name = rec.fields["app_name"].orEmpty().lowercase()
                    val mapped = appMap[packageName]?.lowercase()
                    mapped != null && (name.contains(mapped) || mapped.contains(name))
                }
                else -> false
            }
        }.sortedByDescending { it.favorite }
    }

    private fun domainHost(url: String): String? = try {
        val withScheme = if (url.contains("://")) url else "https://$url"
        URI(withScheme).host?.lowercase()?.removePrefix("www.")
    } catch (_: Exception) {
        null
    }

    private fun extractDomainHint(pkg: String): String? {
        // crude: com.zomato.android → zomato
        val parts = pkg.split('.')
        return parts.getOrNull(1)
    }

}
