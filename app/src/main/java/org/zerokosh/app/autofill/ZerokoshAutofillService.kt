/**
 * @file ZerokoshAutofillService.kt
 * @description §6.7 AutofillService. Matches login records by domain and
 *              app_profile/login by package via bundled app_map.json.
 *              NEVER uses AccessibilityService. Auth-gated datasets.
 */
package org.zerokosh.app.autofill

// #region Imports
import android.app.assist.AssistStructure
import android.content.Intent
import android.os.Build
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.InlinePresentation
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.view.autofill.AutofillId
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
// #endregion

class ZerokoshAutofillService : AutofillService() {

    // Saving writes the vault, which is disk and crypto — not work for the
    // binder thread the framework calls us on.
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback,
    ) {
        val structure = request.fillContexts.lastOrNull()?.structure
        val targets = structure?.let(::parseStructure)?.targets
        if (targets == null || targets.isEmpty) {
            callback.onSuccess(null)
            return
        }

        val app = application as ZerokoshApp
        if (app.repository.state.value != VaultState.Unlocked) {
            // Response-level auth: MainActivity carries the unlock and hands the
            // finished response back through EXTRA_AUTHENTICATION_RESULT, so the
            // form the user was standing in is filled the moment they are in.
            callback.onSuccess(AutofillFill.lockedResponse(this, targets))
            return
        }
        callback.onSuccess(unlockedResponse(request, app, targets))
    }

    /**
     * The same content as [AutofillFill.responseFor], plus the inline chips that
     * only exist here — building one needs the FillRequest, which the auth
     * activity never sees.
     */
    private fun unlockedResponse(
        request: FillRequest,
        app: ZerokoshApp,
        targets: FieldTargets,
    ): FillResponse? {
        val matches = AutofillFill.matchRecords(app, targets)
        val saveInfo = AutofillFill.saveInfoFor(targets)
        if (matches.isEmpty() && saveInfo == null) return null
        val response = FillResponse.Builder()
        matches.take(5).forEachIndexed { index, record ->
            val inline = inlineFor(request, index, record)
            response.addDataset(AutofillFill.datasetFor(this, targets, record, inline))
        }
        saveInfo?.let(response::setSaveInfo)
        return response.build()
    }

    private fun inlineFor(request: FillRequest, index: Int, record: Record): InlinePresentation? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return null
        if (index >= InlineSuggestions.maxSuggestions(request)) return null
        val username = AutofillFill.usernameOf(record).ifBlank { null }
        return InlineSuggestions.build(this, request, index, record.title, username)
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

    private val Parsed.targets: FieldTargets
        get() = FieldTargets(packageName, webDomain, usernameId, passwordId, isNewPassword)

    private data class Parsed(
        val packageName: String,
        val webDomain: String?,
        val usernameId: AutofillId?,
        val passwordId: AutofillId?,
        val isNewPassword: Boolean = false,
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
        var newPassword = false
        var usernameValue = ""
        var passwordValue = ""

        fun toParsed() =
            Parsed(packageName, webDomain, usernameId, passwordId, newPassword, usernameValue, passwordValue)
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
            // AUTOFILL_HINT_NEW_PASSWORD, lowercased upstream. Its presence is
            // the site telling us outright that this is a sign-up.
            if (hints.any { it == "newpassword" }) newPassword = true
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

}
