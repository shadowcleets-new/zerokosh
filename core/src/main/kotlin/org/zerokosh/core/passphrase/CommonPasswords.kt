/**
 * @file CommonPasswords.kt
 * @description Offline check for passwords that are guessed first (§5.12).
 *
 * The obvious feature here is a breach lookup against Have I Been Pwned. It is
 * disqualified: it needs the INTERNET permission, and this app's central claim
 * is that it cannot open a network connection at all. Trading that for a
 * convenience is a bad deal at any price.
 *
 * What survives offline is most of the value. Real-world password distributions
 * have a brutal head — a small set of passwords covers a large share of
 * accounts — so catching the head locally catches most of the genuinely doomed
 * choices. This is deliberately the head, not a breach corpus: it will not tell
 * you your password appeared in a dump, only that it is one an attacker tries
 * early. The UI must not overstate it.
 *
 * Normalisation is intentionally narrow. Lowercasing catches "Password1" for
 * "password1"; stripping trailing digits would flag far too much and teach
 * users to ignore the warning, which is worse than not warning at all.
 */
package org.zerokosh.core.passphrase

object CommonPasswords {

    /**
     * The head of the distribution: keyboard walks, the perennial favourites,
     * and the India-specific ones a generic English list misses entirely.
     */
    private val LIST: Set<String> = setOf(
        // Keyboard walks and runs
        "123456", "123456789", "12345678", "12345", "1234567", "1234567890",
        "qwerty", "qwertyuiop", "qwerty123", "asdfgh", "asdfghjkl", "zxcvbnm",
        "1q2w3e4r", "1qaz2wsx", "qazwsx", "abc123", "abcd1234", "a1b2c3d4",
        "111111", "000000", "121212", "123123", "654321", "666666", "999999",
        // Perennials
        "password", "password1", "password123", "passw0rd", "p@ssw0rd",
        "letmein", "welcome", "welcome1", "admin", "admin123", "root",
        "iloveyou", "monkey", "dragon", "sunshine", "princess", "football",
        "shadow", "master", "trustno1", "superman", "batman", "starwars",
        "login", "guest", "test", "test123", "changeme", "secret", "hello",
        "freedom", "whatever", "qwe123", "asdf1234", "michael", "jordan23",
        // India-specific, which a generic English list misses entirely
        "india123", "bharat123", "indian123", "jaihind", "vandemataram",
        "krishna", "krishna123", "ganesha", "ganpati", "sairam", "saibaba",
        "omnamah", "omsairam", "radhakrishna", "hanuman", "mahadev",
        "shivshankar", "jaimatadi", "waheguru", "allah123", "bismillah",
        "chennai", "mumbai123", "delhi123", "bangalore", "hyderabad",
        "sachin", "sachin10", "dhoni", "dhoni07", "viratkohli", "rohit45",
        "bollywood", "shahrukh", "salmankhan", "amitabh", "rajinikanth",
        // Bank and finance flavoured, i.e. exactly what this app stores
        "bank123", "netbanking", "account123", "money123", "paytm123",
        "upi12345", "atmpin123", "mypassword", "mypin1234", "vault123",
    )

    /** True when the value is one an attacker tries in the first few thousand. */
    fun isCommon(value: String): Boolean =
        value.isNotBlank() && LIST.contains(value.trim().lowercase())

    /** Exposed so a test can assert the list stays sane, not for UI use. */
    internal val size: Int get() = LIST.size
}
