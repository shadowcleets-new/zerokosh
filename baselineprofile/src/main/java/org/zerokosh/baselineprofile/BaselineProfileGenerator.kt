/**
 * @file BaselineProfileGenerator.kt
 * @description The journey the Baseline Profile is recorded from.
 *
 * A fresh emulator has no vault, so the first iteration makes one through the
 * real onboarding: passphrase, recovery kit deferred, no quick unlock (an
 * emulator has no sensor). Every later iteration starts where a returning user
 * does, at the lock screen, and walks the four tabs.
 *
 * Selectors are the English strings the managed device renders. A copy change
 * to one of these buttons fails generation loudly, which is the right way for
 * it to fail.
 */
package org.zerokosh.baselineprofile

import android.widget.EditText
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import java.util.regex.Pattern
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun generate() = rule.collect(
        // The profiled build's own id (see :app's androidComponents block),
        // so this can never touch an installed Zerokosh.
        packageName = "com.zerokosh.app.benchmark",
        // Also emits a startup profile, which R8 uses to put the classes cold
        // start needs next to each other in the dex.
        includeInStartupProfile = true,
    ) {
        pressHome()
        startActivityAndWait()
        device.reachVault()
        for (tab in listOf("Codes", "Templates", "Settings", "Vault")) device.tap(By.text(tab))
    }
}

// ponytail: a throwaway vault on a throwaway emulator. Deliberately not the PIN
// used for test vaults on real phones: this file is public.
private const val PASSPHRASE = "profile-emulator-only"

private fun UiDevice.reachVault() {
    val first = Pattern.compile("Create a new vault|Unlock|Vault")
    check(wait(Until.hasObject(By.text(first)), 15_000)) { "no recognisable first screen" }
    when {
        hasObject(By.text("Create a new vault")) -> createVault()
        hasObject(By.text("Unlock")) -> {
            typeInto(0, PASSPHRASE)
            tap(By.text("Unlock"))
        }
        // Otherwise the auto-lock window was still open and the vault is showing.
    }
    // Argon2id sits on both paths above, and an emulator is slow at it.
    check(wait(Until.hasObject(By.text("Vault")), 60_000)) { "never reached the vault" }
}

private fun UiDevice.createVault() {
    tap(By.text("Create a new vault"))
    tap(By.text(Pattern.compile("Continue in .+")))
    tap(By.text("I understand · Continue"))
    typeInto(0, PASSPHRASE)
    typeInto(1, PASSPHRASE)
    tap(By.text("Seal the vault"))
    // Gone once the vault is written and the recovery kit screen has replaced it.
    check(wait(Until.gone(By.text(Pattern.compile("Seal the vault|Sealing…"))), 60_000)) {
        "vault creation did not finish"
    }
    tap(By.text(Pattern.compile("I.ll do this later")))
    tap(By.text("Continue with passphrase"))
}

private fun UiDevice.typeInto(index: Int, text: String) {
    val fields = wait(Until.findObjects(By.clazz(EditText::class.java)), 10_000)
    checkNotNull(fields?.getOrNull(index)) { "no text field #$index" }.text = text
}

/** Tap what [selector] matches, scrolling down to it if it is below the fold. */
private fun UiDevice.tap(selector: BySelector) {
    val target = wait(Until.findObject(selector), 5_000)
        ?: findObject(By.scrollable(true))?.scrollUntil(Direction.DOWN, Until.findObject(selector))
    checkNotNull(target) { "nothing on screen matches $selector" }.click()
    waitForIdle()
}
