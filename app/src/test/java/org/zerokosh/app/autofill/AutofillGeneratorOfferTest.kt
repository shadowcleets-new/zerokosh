package org.zerokosh.app.autofill

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * When the autofill dropdown offers to invent a password.
 *
 * The rule is small enough to read, which is exactly why it is worth pinning:
 * the last row is the one that matters, and it is the one an innocent-looking
 * change to the condition would break first.
 */
class AutofillGeneratorOfferTest {

    @Test
    fun `offers on a sign-up form`() {
        assertTrue(
            AutofillFill.shouldOfferGenerated(
                hasPasswordField = true,
                isNewPassword = true,
                hasSavedMatches = false,
            ),
        )
    }

    @Test
    fun `offers on an unhinted form for a site we have never seen`() {
        assertTrue(
            AutofillFill.shouldOfferGenerated(
                hasPasswordField = true,
                isNewPassword = false,
                hasSavedMatches = false,
            ),
        )
    }

    @Test
    fun `offers on a sign-up form even when the site already has a login`() {
        // A second account on a site you already use is a real thing people do.
        assertTrue(
            AutofillFill.shouldOfferGenerated(
                hasPasswordField = true,
                isNewPassword = true,
                hasSavedMatches = true,
            ),
        )
    }

    @Test
    fun `stays quiet on a sign-in form for a site we have a login for`() {
        assertFalse(
            AutofillFill.shouldOfferGenerated(
                hasPasswordField = true,
                isNewPassword = false,
                hasSavedMatches = true,
            ),
        )
    }

    @Test
    fun `never offers when there is no password field to fill`() {
        // The username step of a two-step login. Nowhere to put it.
        assertFalse(
            AutofillFill.shouldOfferGenerated(
                hasPasswordField = false,
                isNewPassword = true,
                hasSavedMatches = false,
            ),
        )
    }
}
