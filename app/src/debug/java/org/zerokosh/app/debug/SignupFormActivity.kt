/**
 * @file SignupFormActivity.kt
 * @description A throwaway sign-up form, debug builds only, for exercising the
 *              autofill service from the outside.
 */
package org.zerokosh.app.debug

// #region Imports
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
// #endregion

/**
 * Two fields and nothing else, so the only thing under test is the service.
 *
 * The username carries the `username` hint and the password carries
 * `newPassword` — that second one is the whole point. It is what a real
 * sign-up page emits, what [org.zerokosh.app.autofill.ZerokoshAutofillService]
 * reads to tell "invent one" from "which of your logins is this", and
 * therefore what decides whether the generator offer appears at all.
 *
 * Launch it with:
 * `adb shell am start -n com.zerokosh.app/org.zerokosh.app.debug.SignupFormActivity`
 */
class SignupFormActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (24 * resources.displayMetrics.density).toInt()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 3, pad, pad)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }

        root.addView(
            TextView(this).apply {
                text = "Create your account"
                textSize = 22f
            },
        )

        root.addView(
            EditText(this).apply {
                id = View.generateViewId()
                hint = "Email"
                setAutofillHints(View.AUTOFILL_HINT_USERNAME)
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            },
        )

        root.addView(
            EditText(this).apply {
                id = View.generateViewId()
                hint = "Choose a password"
                // The signal under test. AUTOFILL_HINT_NEW_PASSWORD needs API
                // 33, and this activity exists only for local testing, so the
                // literal keeps it usable on the whole minSdk range.
                setAutofillHints("newPassword")
                inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            },
        )

        setContentView(root)
    }
}
