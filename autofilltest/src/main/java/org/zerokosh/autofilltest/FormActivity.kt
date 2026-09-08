/**
 * @file FormActivity.kt
 * @description Two forms — sign-up and sign-in — for pointing the Zerokosh
 *              autofill service at from outside its own package.
 */
package org.zerokosh.autofilltest

// #region Imports
import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
// #endregion

/**
 * Both forms on one screen, because the difference between them is the whole
 * thing under test: the sign-up password carries `newPassword` and the sign-in
 * password carries `password`, and the service is supposed to offer a
 * generated password for the first and a saved credential for the second.
 *
 * `adb shell am start -n org.zerokosh.autofilltest/.FormActivity`
 */
class FormActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (20 * resources.displayMetrics.density).toInt()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 3, pad, pad)
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }

        root.addView(heading("Sign up (newPassword)"))
        root.addView(field("Email", View.AUTOFILL_HINT_USERNAME, password = false))
        root.addView(field("Choose a password", "newPassword", password = true))
        root.addView(Button(this).apply { text = "Create account" })

        root.addView(heading("Sign in (password)"))
        root.addView(field("Email", View.AUTOFILL_HINT_USERNAME, password = false))
        root.addView(field("Password", View.AUTOFILL_HINT_PASSWORD, password = true))
        root.addView(Button(this).apply { text = "Sign in" })

        setContentView(root)
    }

    private fun heading(label: String) = TextView(this).apply {
        text = label
        textSize = 18f
        setPadding(0, (24 * resources.displayMetrics.density).toInt(), 0, 0)
    }

    private fun field(label: String, hint: String, password: Boolean) = EditText(this).apply {
        id = View.generateViewId()
        this.hint = label
        // The literals rather than the constants: AUTOFILL_HINT_NEW_PASSWORD
        // needs API 33 and this has to run on the whole minSdk range.
        setAutofillHints(hint)
        inputType = InputType.TYPE_CLASS_TEXT or if (password) {
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else {
            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }
    }
}
