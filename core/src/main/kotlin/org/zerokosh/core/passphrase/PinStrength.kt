/**
 * @file PinStrength.kt
 * @description Strength scoring for the 6-digit PIN branch of S4 (§2.4).
 *
 * The create screen only ever showed a live strength meter for passphrases, so
 * choosing the PIN option meant typing a secret with no feedback at all — the
 * one place feedback matters most, because a PIN is the weaker of the two
 * options by construction.
 *
 * In core rather than beside the composable so it can be tested: this is branchy
 * security logic, and the app module has no test source set.
 */
package org.zerokosh.core.passphrase

/**
 * Blocks of digits repeated to fill the field (121212, 123123), runs (123456,
 * 654321) and the perennial favourites. Not a serious dictionary — it is the
 * shape of PIN people reach for when they want one they cannot forget.
 */
private val COMMON_PINS = setOf(
    "123456", "654321", "112233", "123321", "010203", "789456",
    "159753", "147258", "135790", "246810", "102030", "123654",
)

/**
 * True for a PIN measurably weaker than its ~19.9 bits of nominal entropy.
 */
fun isWeakPinPattern(pin: String): Boolean {
    if (pin.isEmpty()) return false
    // A short block repeated to fill the field. Covers 000000 and 111111 at
    // block size 1, 121212 at 2, and 123123 at 3, so they need no separate case.
    for (block in 1..pin.length / 2) {
        if (pin.length % block == 0 && pin.chunked(block).toSet().size == 1) return true
    }
    val steps = pin.zipWithNext { a, b -> b - a }
    if (steps.isNotEmpty() && (steps.all { it == 1 } || steps.all { it == -1 })) return true
    return pin in COMMON_PINS
}

/**
 * 0 = unusable, 1 = weak, 2 = as good as a PIN gets.
 *
 * The ceiling is 2 on purpose. Six digits is at most log2(10^6) ≈ 19.9 bits, so
 * no PIN should ever be labelled "strong" beside a passphrase worth 60+ — the
 * meter would be lying about the choice the user is making on that very screen.
 */
fun pinScore(pin: String, length: Int = 6): Int {
    if (pin.length < length || !pin.all(Char::isDigit)) return 0
    return if (isWeakPinPattern(pin)) 1 else 2
}
