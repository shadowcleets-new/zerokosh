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

/**
 * Which saved login belongs on which site.
 *
 * The offering half of autofill: too strict and a saved credential never
 * appears where it is needed, too loose and one appears where it does not
 * belong. The last two cases are the ones with teeth.
 */
class AutofillSiteMatchTest {

    @Test
    fun `exact host matches`() {
        assertTrue(AutofillFill.sameSite(here = "hdfcbank.com", saved = "hdfcbank.com"))
    }

    @Test
    fun `login subdomain matches the bare domain saved`() {
        // You save hdfcbank.com; the sign-in page is netbanking.hdfcbank.com.
        assertTrue(AutofillFill.sameSite(here = "netbanking.hdfcbank.com", saved = "hdfcbank.com"))
    }

    @Test
    fun `bare domain matches a subdomain saved`() {
        assertTrue(AutofillFill.sameSite(here = "google.com", saved = "accounts.google.com"))
    }

    @Test
    fun `a different site that merely ends the same does not match`() {
        // The old contains() check said yes here, and handed the credential
        // for one bank to another site.
        assertFalse(AutofillFill.sameSite(here = "bank.com", saved = "mybank.com"))
        assertFalse(AutofillFill.sameSite(here = "mybank.com", saved = "bank.com"))
    }

    @Test
    fun `a lookalike domain does not match`() {
        assertFalse(AutofillFill.sameSite(here = "evil-hdfcbank.com", saved = "hdfcbank.com"))
        assertFalse(AutofillFill.sameSite(here = "hdfcbank.com.attacker.net", saved = "hdfcbank.com"))
    }
}
