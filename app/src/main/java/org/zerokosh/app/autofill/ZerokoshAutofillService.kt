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
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.MainActivity
import org.zerokosh.app.R
import org.zerokosh.app.data.VaultState
import org.zerokosh.core.model.Record
import java.net.URI
// #endregion

class ZerokoshAutofillService : AutofillService() {

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        val structure = request.fillContexts.lastOrNull()?.structure
        if (structure == null) {
            callback.onSuccess(null)
            return
        }
        val parsed = parseStructure(structure)
        if (parsed.usernameId == null && parsed.passwordId == null) {
            callback.onSuccess(null)
            return
        }

        val app = application as ZerokoshApp
        if (app.repository.state.value != VaultState.Unlocked) {
            // Require unlock — authentication activity
            val authIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pending = PendingIntent.getActivity(
                this, 0, authIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            val presentation = RemoteViews(packageName, android.R.layout.simple_list_item_1).apply {
                setTextViewText(android.R.id.text1, getString(R.string.scr_autofill_unlock_first))
            }
            val builder = Dataset.Builder(presentation)
            parsed.usernameId?.let { builder.setValue(it, AutofillValue.forText("")) }
            parsed.passwordId?.let { builder.setValue(it, AutofillValue.forText("")) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                InlineSuggestions.maxSuggestions(request) > 0
            ) {
                InlineSuggestions.build(
                    this, request, 0,
                    getString(R.string.scr_autofill_unlock_first), null,
                )?.let { builder.setInlinePresentation(it) }
            }
            builder.setAuthentication(pending.intentSender)
            val response = FillResponse.Builder()
                .addDataset(builder.build())
                .build()
            callback.onSuccess(response)
            return
        }

        val body = app.repository.body.value
        val matches = matchRecords(body?.records.orEmpty(), parsed.packageName, parsed.webDomain)
        if (matches.isEmpty()) {
            // Still offer save for new credentials
            val response = FillResponse.Builder()
            if (parsed.usernameId != null && parsed.passwordId != null) {
                response.setSaveInfo(
                    SaveInfo.Builder(
                        SaveInfo.SAVE_DATA_TYPE_USERNAME or SaveInfo.SAVE_DATA_TYPE_PASSWORD,
                        arrayOf(parsed.usernameId, parsed.passwordId),
                    ).build(),
                )
            }
            callback.onSuccess(response.build())
            return
        }

        val response = FillResponse.Builder()
        val inlineSlots = InlineSuggestions.maxSuggestions(request)
        for ((index, record) in matches.take(5).withIndex()) {
            val username = record.fields["username"]
                ?: record.fields["registered_email"]
                ?: record.fields["registered_mobile"]
                ?: ""
            val password = record.fields["password"]
                ?: record.fields["password_if_any"]
                ?: record.fields["login_password"]
                ?: ""
            val presentation = RemoteViews(packageName, android.R.layout.simple_list_item_2).apply {
                setTextViewText(android.R.id.text1, record.title)
                setTextViewText(android.R.id.text2, username)
            }
            val ds = Dataset.Builder(presentation)
            parsed.usernameId?.let { ds.setValue(it, AutofillValue.forText(username)) }
            parsed.passwordId?.let { ds.setValue(it, AutofillValue.forText(password)) }
            // Both presentations on one dataset: the platform draws the chip
            // where the IME has a strip, and falls back to the dropdown where
            // it does not. Only the first `inlineSlots` get a chip, because
            // asking for more than the keyboard offered is a no-op at best.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && index < inlineSlots) {
                InlineSuggestions.build(
                    this, request, index, record.title, username.ifBlank { null },
                )?.let { ds.setInlinePresentation(it) }
            }
            response.addDataset(ds.build())
        }
        if (parsed.usernameId != null && parsed.passwordId != null) {
            response.setSaveInfo(
                SaveInfo.Builder(
                    SaveInfo.SAVE_DATA_TYPE_USERNAME or SaveInfo.SAVE_DATA_TYPE_PASSWORD,
                    arrayOf(parsed.usernameId, parsed.passwordId),
                ).build(),
            )
        }
        callback.onSuccess(response.build())
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        // Save prompt: store as login record when vault is unlocked
        val structure = request.fillContexts.lastOrNull()?.structure
        if (structure == null) {
            callback.onSuccess()
            return
        }
        val parsed = parseStructure(structure)
        val app = application as ZerokoshApp
        if (app.repository.state.value != VaultState.Unlocked) {
            callback.onSuccess()
            return
        }
        // Values are in the structure client state; for v1 we open MainActivity with save intent
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(EXTRA_AUTOFILL_SAVE, true)
            putExtra(EXTRA_PACKAGE, parsed.packageName)
            putExtra(EXTRA_DOMAIN, parsed.webDomain)
        }
        startActivity(intent)
        callback.onSuccess()
    }

    private data class Parsed(
        val packageName: String,
        val webDomain: String?,
        val usernameId: AutofillId?,
        val passwordId: AutofillId?,
    )

    private fun parseStructure(structure: AssistStructure): Parsed {
        var packageName = structure.activityComponent?.packageName.orEmpty()
        var webDomain: String? = null
        var usernameId: AutofillId? = null
        var passwordId: AutofillId? = null

        fun walk(node: AssistStructure.ViewNode) {
            val hints = node.autofillHints?.map { it.lowercase() }.orEmpty()
            val id = node.autofillId
            val html = node.htmlInfo
            val inputType = node.inputType
            if (node.webDomain != null) webDomain = node.webDomain
            if (packageName.isEmpty() && node.idPackage != null) packageName = node.idPackage!!

            val isPassword = hints.any { it.contains("password") } ||
                html?.attributes?.any { it.first.equals("type", true) && it.second.equals("password", true) } == true ||
                (inputType and android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD) != 0 ||
                (inputType and android.text.InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD) != 0

            val isUsername = hints.any {
                it.contains("username") || it.contains("email") || it == "emailAddress".lowercase()
            } || html?.attributes?.any {
                it.first.equals("type", true) && (it.second.equals("email", true) || it.second.equals("text", true))
            } == true

            if (id != null) {
                if (isPassword && passwordId == null) passwordId = id
                else if (isUsername && usernameId == null && !isPassword) usernameId = id
            }
            for (i in 0 until node.childCount) walk(node.getChildAt(i))
        }
        for (i in 0 until structure.windowNodeCount) {
            walk(structure.getWindowNodeAt(i).rootViewNode)
        }
        return Parsed(packageName, webDomain, usernameId, passwordId)
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

    companion object {
        const val EXTRA_AUTOFILL_SAVE = "autofill_save"
        const val EXTRA_PACKAGE = "autofill_package"
        const val EXTRA_DOMAIN = "autofill_domain"
    }
}
