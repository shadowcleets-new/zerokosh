/**
 * @file AutofillSetup.kt
 * @description Discovery and status for the autofill provider (§6.7).
 *
 * The service has been registered and selectable since BV-01, and `pm
 * query-services` on a device confirms Android sees it. What was missing is any
 * way to find that out from inside the app: the word "autofill" did not appear
 * anywhere in the UI, so the only route to switching provider was for the user
 * to already know it lived in system Settings. A provider nobody can find is a
 * provider nobody uses.
 */
package org.zerokosh.app.autofill

// #region Imports
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.autofill.AutofillManager
// #endregion

object AutofillSetup {

    private fun manager(context: Context): AutofillManager? =
        context.getSystemService(AutofillManager::class.java)

    /** False on devices with autofill compiled out — a few do exist. */
    fun isSupported(context: Context): Boolean =
        manager(context)?.isAutofillSupported == true

    /**
     * True only when *this* app is the selected provider. It says nothing about
     * whether some other manager is active, which is the correct scope: the
     * only thing this screen can offer to change is our own status.
     */
    fun isEnabled(context: Context): Boolean =
        runCatching { manager(context)?.hasEnabledAutofillServices() == true }.getOrDefault(false)

    /**
     * Opens the system's provider picker, pre-pointed at this app.
     *
     * ACTION_REQUEST_SET_AUTOFILL_SERVICE is the sanctioned route and shows the
     * OS's own confirmation, which is what makes it safe: an app cannot make
     * itself the password provider silently. When it is already us, that intent
     * is a no-op dialog, so send the user to the plain settings panel instead —
     * the useful action there is turning it off or comparing providers.
     */
    fun intent(context: Context): Intent =
        if (isEnabled(context)) {
            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
        } else {
            Intent(Settings.ACTION_REQUEST_SET_AUTOFILL_SERVICE)
                .setData(Uri.parse("package:${context.packageName}"))
        }
}
