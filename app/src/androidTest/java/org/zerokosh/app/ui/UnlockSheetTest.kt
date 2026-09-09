/**
 * @file UnlockSheetTest.kt
 * @description The unlock that appears over other apps, on a real device.
 *
 * This sheet is the only way into the vault from outside the app — both the
 * autofill service and the Credential Manager provider route through it — and
 * it is composed against a real window, a real keyboard and the user's font
 * scale. None of that exists in a JVM unit test, and every defect worth finding
 * this month was of exactly that kind.
 */
package org.zerokosh.app.ui

// #region Imports
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.zerokosh.app.R
// #endregion

@RunWith(AndroidJUnit4::class)
class UnlockSheetTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private val title get() = rule.activity.getString(R.string.scr_autofill_unlock_first)
    private val unlockLabel get() = rule.activity.getString(R.string.scr_lock_unlock)
    private val passphraseLabel get() = rule.activity.getString(R.string.scr_lock_hint)
    private val wrongLabel get() = rule.activity.getString(R.string.scr_lock_wrong)

    /** What was typed, and whether cancel was asked for. */
    private var submitted: String? = null
    private var cancelled = false
    private lateinit var reject: () -> Unit

    private fun show(accept: Boolean = true) {
        rule.setContent {
            UnlockSheet(
                title = title,
                onUnlock = { entered, onWrong ->
                    submitted = entered
                    reject = onWrong
                    if (!accept) onWrong()
                },
                onCancel = { cancelled = true },
            )
        }
    }

    @Test
    fun unlockIsDisabledUntilSomethingIsTyped() {
        show()
        rule.onNodeWithText(unlockLabel).assertIsNotEnabled()
        rule.onNodeWithText(passphraseLabel).performTextInput("241000")
        rule.onNodeWithText(unlockLabel).assertIsEnabled()
    }

    @Test
    fun tappingUnlockHandsOverExactlyWhatWasTyped() {
        show()
        rule.onNodeWithText(passphraseLabel).performTextInput("correct horse")
        rule.onNodeWithText(unlockLabel).performClick()
        rule.waitForIdle()
        assertEquals("correct horse", submitted)
    }

    /**
     * The keyboard's own action, which is how a passphrase actually gets
     * submitted — nobody reaches for the button after typing.
     */
    @Test
    fun theImeActionSubmitsToo() {
        show()
        rule.onNodeWithText(passphraseLabel).performTextInput("241000")
        rule.onNodeWithText(passphraseLabel).performImeAction()
        rule.waitForIdle()
        assertEquals("241000", submitted)
    }

    @Test
    fun aWrongPassphraseSaysSoAndClearsTheField() {
        show(accept = false)
        rule.onNodeWithText(passphraseLabel).performTextInput("wrong")
        rule.onNodeWithText(unlockLabel).performClick()
        rule.waitForIdle()
        rule.onNodeWithText(wrongLabel).assertIsDisplayed()
        // Cleared, so the next attempt starts from nothing rather than from a
        // half-corrected wrong answer.
        rule.onNodeWithText(unlockLabel).assertIsNotEnabled()
    }

    /**
     * A second attempt has to be possible. Leaving the sheet stuck busy after
     * one wrong passphrase would mean the only way back is to dismiss and start
     * the whole request again.
     */
    @Test
    fun aSecondAttemptIsAccepted() {
        show(accept = false)
        rule.onNodeWithText(passphraseLabel).performTextInput("wrong")
        rule.onNodeWithText(unlockLabel).performClick()
        rule.waitForIdle()
        submitted = null
        rule.onNodeWithText(passphraseLabel).performTextInput("241000")
        rule.onNodeWithText(unlockLabel).performClick()
        rule.waitForIdle()
        assertEquals("241000", submitted)
    }

    @Test
    fun cancelIsReported() {
        show()
        rule.onNodeWithText(rule.activity.getString(android.R.string.cancel)).performClick()
        rule.waitForIdle()
        assertTrue(cancelled)
    }

    /** Nothing is handed over until the user asks for it. */
    @Test
    fun typingAloneSubmitsNothing() {
        show()
        rule.onNodeWithText(passphraseLabel).performTextInput("241000")
        rule.waitForIdle()
        assertNull(submitted)
    }
}
