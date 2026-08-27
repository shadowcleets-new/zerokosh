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
    /**
     * IIN ranges, in the order they must be tested — RuPay's 60/65 overlap
     * Discover ranges this app does not issue, and Maestro's 50 sits inside
     * Mastercard's old space, so first match wins and order is the rule.
     *
     * A table rather than a when-chain: the branch count was the whole of this
     * function's complexity, and a list of ranges is also the shape the data
     * actually has.
     */
    private class IinRule(
        val prefixes: List<String> = emptyList(),
        val range: Triple<Int, Int, Int>? = null,
        val network: Network,
    )

    private val IIN_RULES = listOf(
        IinRule(prefixes = listOf("508", "60", "65", "81", "82"), network = Network.RUPAY),
        IinRule(prefixes = listOf("34", "37"), network = Network.AMEX),
        IinRule(range = Triple(3, 300, 305), network = Network.DINERS),
        IinRule(prefixes = listOf("36", "38"), network = Network.DINERS),
        IinRule(range = Triple(2, 51, 55), network = Network.MASTERCARD),
        IinRule(range = Triple(4, 2221, 2720), network = Network.MASTERCARD),
        IinRule(prefixes = listOf("4"), network = Network.VISA),
        IinRule(prefixes = listOf("50", "56", "57", "58"), network = Network.MAESTRO),
    )

    private fun IinRule.matches(digits: String): Boolean {
        if (prefixes.any(digits::startsWith)) return true
        val (len, lo, hi) = range ?: return false
        if (digits.length < len) return false
        return digits.substring(0, len).toInt() in lo..hi
    }

    fun detectNetwork(digits: String): Network? {
        if (digits.isEmpty() || !digits.all { it.isDigit() }) return null
        return IIN_RULES.firstOrNull { it.matches(digits) }?.network
    }
}
