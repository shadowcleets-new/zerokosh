/**
 * @file LockSecretNamingTest.kt
 * @description The lock screen must ask for the secret the user actually set.
 *
 * Reported from a phone whose vault opens with six digits: every prompt said
 * "passphrase". Nothing recorded which kind had been chosen, so the screen
 * could not have known — these are the four decisions it makes once it does.
 */
package org.zerokosh.app.ui.lock

import androidx.compose.ui.text.input.KeyboardType
import kotlin.test.Test
import kotlin.test.assertEquals
import org.zerokosh.app.R

class LockSecretNamingTest {

    @Test
    fun `a PIN vault is never called a passphrase`() {
        assertEquals(R.string.ob_pass_tab_pin, fieldLabel(recoveryMode = false, isPin = true))
        assertEquals(R.string.scr_lock_wrong_pin, wrongSecret(recoveryMode = false, isPin = true))
        assertEquals(R.string.scr_lock_use_pin_once, usePassphraseOnce(isPin = true))
        // Six digits deserve a digit keypad.
        assertEquals(KeyboardType.NumberPassword, keyboardFor(recoveryMode = false, isPin = true))
    }

    @Test
    fun `a passphrase vault is unchanged`() {
        assertEquals(R.string.scr_lock_hint, fieldLabel(recoveryMode = false, isPin = false))
        assertEquals(R.string.scr_lock_wrong, wrongSecret(recoveryMode = false, isPin = false))
        assertEquals(R.string.scr_lock_use_passphrase_once, usePassphraseOnce(isPin = false))
        assertEquals(KeyboardType.Password, keyboardFor(recoveryMode = false, isPin = false))
    }

    /** Recovery keys are neither, and are typed as ordinary text. */
    @Test
    fun `recovery mode outranks both`() {
        for (isPin in listOf(true, false)) {
            assertEquals(R.string.scr_lock_recovery_hint, fieldLabel(recoveryMode = true, isPin = isPin))
            assertEquals(R.string.scr_lock_recovery_invalid, wrongSecret(recoveryMode = true, isPin = isPin))
            assertEquals(KeyboardType.Text, keyboardFor(recoveryMode = true, isPin = isPin))
        }
    }
}
