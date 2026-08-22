package org.zerokosh.core.util

/** §5.10: Luhn validation + card-network detection by prefix table. */
object CardUtils {

    /**
     * [label] is the exact `card_networks` picker option, so a detected network
     * can be written straight into the field. Two spellings of "Mastercard"
     * would leave the picker showing "Other" for a value it does contain.
     */
    enum class Network(val label: String) {
        RUPAY("RuPay"),
        VISA("Visa"),
        MASTERCARD("Mastercard"),
        AMEX("American Express"),
        DINERS("Diners Club"),
        MAESTRO("Maestro"),
    }

    fun luhnValid(digits: String): Boolean {
        if (digits.length < 12 || !digits.all { it.isDigit() }) return false
        var sum = 0
        var double = false
        for (i in digits.length - 1 downTo 0) {
            var d = digits[i] - '0'
            if (double) {
                d *= 2
                if (d > 9) d -= 9
            }
            sum += d
            double = !double
        }
        return sum % 10 == 0
    }

    /**
     * Prefix table from §5.10. RuPay checked before Maestro so 508xx / 60 / 65
     * don't get swallowed by Maestro's 50 / 56–58 ranges.
     */
    fun detectNetwork(digits: String): Network? {
        if (digits.isEmpty() || !digits.all { it.isDigit() }) return null
        fun starts(vararg p: String) = p.any { digits.startsWith(it) }
        fun inRange(len: Int, lo: Int, hi: Int): Boolean {
            if (digits.length < len) return false
            val head = digits.substring(0, len).toInt()
            return head in lo..hi
        }
        return when {
            starts("508", "60", "65", "81", "82") -> Network.RUPAY
            starts("34", "37") -> Network.AMEX
            inRange(3, 300, 305) || starts("36", "38") -> Network.DINERS
            inRange(2, 51, 55) || inRange(4, 2221, 2720) -> Network.MASTERCARD
            starts("4") -> Network.VISA
            starts("50", "56", "57", "58") -> Network.MAESTRO
            else -> null
        }
    }
}
