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
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CredentialManagerCallback
import androidx.credentials.CreateCredentialResponse
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialException
import java.util.concurrent.Executors
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

        root.addView(heading("Credential Manager"))
        val status = TextView(this).apply { text = "—" }
        root.addView(Button(this).apply {
            text = "Sign in with Credential Manager"
            setOnClickListener { getCredential(status) }
        })
        root.addView(Button(this).apply {
            text = "Save a password via Credential Manager"
            setOnClickListener { createCredential(status) }
        })
        root.addView(status)

        setContentView(root)
    }

    /**
     * The path that never touches AutofillService.
     *
     * This is the whole reason Zerokosh needs a credential provider: an app
     * that signs in this way is invisible to an autofill-only manager.
     */
    private fun getCredential(status: TextView) {
        status.text = "requesting…"
        CredentialManager.create(this).getCredentialAsync(
            context = this,
            request = GetCredentialRequest(listOf(GetPasswordOption())),
            cancellationSignal = null,
            executor = Executors.newSingleThreadExecutor(),
            callback = object : CredentialManagerCallback<GetCredentialResponse, GetCredentialException> {
                override fun onResult(result: GetCredentialResponse) {
                    val c = result.credential
                    val text = if (c is PasswordCredential) {
                        "got: " + c.id + " / " + c.password.length + " chars"
                    } else {
                        "got type " + c.type
                    }
                    runOnUiThread { status.text = text }
                }

                override fun onError(e: GetCredentialException) {
                    runOnUiThread { status.text = "error: " + e.type }
                }
            },
        )
    }

    private fun createCredential(status: TextView) {
        status.text = "saving…"
        CredentialManager.create(this).createCredentialAsync(
            context = this,
            request = CreatePasswordRequest("cm-test@zerokosh.com", "CmTestPassword123"),
            cancellationSignal = null,
            executor = Executors.newSingleThreadExecutor(),
            callback = object : CredentialManagerCallback<CreateCredentialResponse, CreateCredentialException> {
                override fun onResult(result: CreateCredentialResponse) {
                    runOnUiThread { status.text = "saved" }
                }

                override fun onError(e: CreateCredentialException) {
                    runOnUiThread { status.text = "error: " + e.type }
                }
            },
        )
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
