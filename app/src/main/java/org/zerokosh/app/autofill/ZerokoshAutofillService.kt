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
import org.zerokosh.app.data.SessionKeeper
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
        // Off the binder thread. Answering a fill request may have to read the
        // vault off disk and decrypt it, and where the vault lives in a sync
        // folder that read is a round trip to a DocumentsProvider that may be
        // backed by cloud storage. The platform gives this about five seconds
        // and the app only has one main thread, so doing it inline risked both
        // a missed suggestion and an ANR — and, because the unlock path takes
        // the repository's IO mutex, a genuine deadlock against a UI coroutine
        // holding it. The callback may be answered later, so it is.
        val job = scope.launch {
            applySessionWindow(app, targets)
            val response = if (app.repository.state.value != VaultState.Unlocked) {
                // Response-level auth: AutofillAuthActivity carries the unlock
                // and hands the finished response back through
                // EXTRA_AUTHENTICATION_RESULT, so the form the user was
                // standing in is filled the moment they are in.
                AutofillFill.lockedResponse(
                    this@ZerokoshAutofillService,
                    targets,
                    inlineAt(request, 0, getString(R.string.scr_autofill_unlock_first), null),
                )
            } else {
                // Same response the post-unlock path builds, plus the keyboard
                // chips. This used to be a second copy of that logic and had
                // already fallen behind it: the generated-password offer never
                // appeared here, which is every fill request on an
                // already-unlocked vault — the common one.
                AutofillFill.responseFor(
                    this@ZerokoshAutofillService,
                    app,
                    targets,
                ) { index, title, subtitle -> inlineAt(request, index, title, subtitle) }
            }
            withContext(Dispatchers.Main) { callback.onSuccess(response) }
        }
        cancellationSignal.setOnCancelListener { job.cancel() }
    }

    /**
     * Bring the vault into line with the auto-lock window before answering.
     *
     * Two directions, and the second is the one that was missing. A fresh
     * process holding no key resumes the session, which is what stops "Unlock
     * Zerokosh to fill" appearing inside a window the user asked for. But a
     * process that already resumed reports Unlocked forever after: this service
     * can be the only thing keeping it alive, nothing in it runs on a timer,
     * and MainActivity — the one place that locks on a schedule — never starts
     * here. Without the expiry check below, one resume meant filling passwords
     * for as long as the process happened to survive, hours past the deadline.
     */
    private suspend fun applySessionWindow(app: ZerokoshApp, targets: FieldTargets) {
        // Our own screens are exempt. The record editor's own fields raise fill
        // requests, and locking the vault under someone who is mid-edit would
        // throw away what they were typing.
        if (targets.packageName == packageName) return
        val held = SessionKeeper.hasStoredSession(this)
        // isLive clears what it finds expired, so ask whether one was held first.
        val live = SessionKeeper.isLive(this)
        val unlocked = app.repository.state.value == VaultState.Unlocked
        if (held && !live && unlocked) {
            app.repository.lock()
            return
        }
        if (!unlocked && live) app.resumeSessionIfLive()
    }

    /** A chip for one row of the response, or null when the IME will not draw one. */
    private fun inlineAt(
        request: FillRequest,
        index: Int,
        title: String,
        subtitle: String?,
    ): InlinePresentation? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) return null
        if (index >= InlineSuggestions.maxSuggestions(request)) return null
        return InlineSuggestions.build(this, request, index, title, subtitle)
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

        scope.launch {
            // Same window the fill path applies, and off the binder thread for
            // the same reason: resuming reads and decrypts the vault.
            applySessionWindow(app, targets(parsed))
            if (app.repository.state.value != VaultState.Unlocked) {
                // The vault cannot be written shut. Hold the credential in
                // memory — never in an Intent extra — and open the app so the
                // user can unlock; ZerokoshNav finishes the save once they do.
                PendingSave.offer(credential)
                withContext(Dispatchers.Main) {
                    startActivity(
                        Intent(this@ZerokoshAutofillService, MainActivity::class.java)
                            .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK },
                    )
                    callback.onSuccess()
                }
                return@launch
            }
            // upsertRecord returns a Result rather than throwing, so
            // runCatching{}.isSuccess was true even when the save failed —
            // reporting a stored password that had been dropped, which is
            // precisely the failure this method's own history complains about.
            val ok = runCatching { app.repository.upsertRecord(loginRecordFor(app, credential)) }
                .getOrNull()?.isSuccess == true
            withContext(Dispatchers.Main) {
                if (ok) callback.onSuccess()
                else callback.onFailure(getString(R.string.scr_autofill_save_failed))
            }
        }
    }

    /** The origin of a parsed save request, for the session-window check. */
    private fun targets(parsed: Parsed): FieldTargets =
        FieldTargets(parsed.packageName, parsed.webDomain, parsed.usernameId, parsed.passwordId)

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
        return AutofillFill.isPasswordInputType(node.inputType)
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

        /** Whether the password already recorded is the one the user is standing in. */
        var passwordFocused = false

        /** The most recent username field seen, which is the one a password pairs with. */
        var lastUsernameId: AutofillId? = null
        var lastUsernameValue = ""

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
        val focused = node.isFocused
        if (isPasswordNode(node, hints)) {
            if (!claimsPassword(passwordId, passwordFocused, focused)) return
            passwordId = id
            passwordFocused = focused
            // AUTOFILL_HINT_NEW_PASSWORD, lowercased upstream. Its presence is
            // the site telling us outright that this is a sign-up. Assigned
            // rather than only ever set, so that taking a later field also
            // takes that field's answer.
            newPassword = hints.any { it == "newpassword" }
            if (typed.isNotEmpty()) passwordValue = typed
            // Pair with the username immediately above it rather than the
            // first one on the page. On a page carrying both a sign-in and a
            // sign-up form, the first is the other form's box, and filling it
            // put the name in one form and the password in the other.
            usernameId = lastUsernameId
            if (lastUsernameValue.isNotEmpty()) usernameValue = lastUsernameValue
            return
        }
        if (!isUsernameNode(node, hints)) return
        lastUsernameId = id
        lastUsernameValue = typed
        // Until a password field shows up the newest username is the answer,
        // which is the whole of a two-step login's first page.
        if (passwordId == null) {
            usernameId = id
            if (typed.isNotEmpty()) usernameValue = typed
        }
    }

    /**
     * Whether a newly seen password field should replace the one recorded.
     *
     * First one wins, except that a focused field beats an unfocused one. A
     * single page can carry both a sign-in and a sign-up form — plenty do —
     * and taking whichever came first in the tree meant offering to fill a
     * field the user was not in, so nothing appeared under the one they tapped.
     */
    private fun claimsPassword(existing: AutofillId?, existingFocused: Boolean, focused: Boolean): Boolean =
        existing == null || (focused && !existingFocused)

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
