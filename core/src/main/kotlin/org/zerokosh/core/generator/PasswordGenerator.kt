package org.zerokosh.core.generator

// #region Imports
import kotlinx.serialization.Serializable
import org.zerokosh.core.crypto.CryptoProvider
// #endregion

// #region Bank rule schema (bank_rules.json, §5.8)
@Serializable
data class BankRule(
    val bank: String,
    val max_len: Int,
    val min_len: Int,
    val symbols_allowed: String,
    val require_symbol: Boolean,
    val require_digit: Boolean,
)

@Serializable
data class BankRules(val format: Int = 1, val rules: List<BankRule> = emptyList())
// #endregion

/** §5.8 generator. Random source = libsodium randombytes via CryptoProvider — nothing else. */
object PasswordGenerator {

    const val UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    const val LOWER = "abcdefghijklmnopqrstuvwxyz"
    const val DIGITS = "0123456789"
    const val DEFAULT_SYMBOLS = "!@#$%^&*()-_=+[]{};:,.?"

    fun generate(
        length: Int,
        upper: Boolean = true,
        lower: Boolean = true,
        digits: Boolean = true,
        symbols: Boolean = true,
        symbolSet: String = DEFAULT_SYMBOLS,
        crypto: CryptoProvider,
    ): String {
        require(length in 4..64)
        val classes = buildList {
            if (upper) add(UPPER)
            if (lower) add(LOWER)
            if (digits) add(DIGITS)
            if (symbols && symbolSet.isNotEmpty()) add(symbolSet)
        }
        require(classes.isNotEmpty()) { "at least one character class" }
        val all = classes.joinToString("")
        // Retry until every enabled class appears — unbiased, and terminates
        // fast for any realistic length/class combination.
        while (true) {
            val candidate = CharArray(length) { all[randomIndex(all.length, crypto)] }.concatToString()
            if (classes.all { cls -> candidate.any { it in cls } }) return candidate
        }
    }

    /** "PIN mode": digits only, 4 or 6 (§5.8). */
    fun generatePin(length: Int, crypto: CryptoProvider): String {
        require(length == 4 || length == 6)
        return CharArray(length) { DIGITS[randomIndex(10, crypto)] }.concatToString()
    }

    /** Bank preset: clamps length into the rule's bounds and honors its symbol policy. */
    fun generateForRule(rule: BankRule, requestedLength: Int, crypto: CryptoProvider): String {
        val length = requestedLength.coerceIn(rule.min_len, rule.max_len)
        while (true) {
            val candidate = generate(
                length = length,
                upper = true,
                lower = true,
                digits = true,
                symbols = rule.symbols_allowed.isNotEmpty(),
                symbolSet = rule.symbols_allowed,
                crypto = crypto,
            )
            val okSymbol = !rule.require_symbol || candidate.any { it in rule.symbols_allowed }
            val okDigit = !rule.require_digit || candidate.any { it in DIGITS }
            if (okSymbol && okDigit) return candidate
        }
    }

    fun satisfiesRule(password: String, rule: BankRule): Boolean =
        password.length in rule.min_len..rule.max_len &&
            password.all { it.isLetterOrDigit() || it in rule.symbols_allowed } &&
            (!rule.require_symbol || password.any { it in rule.symbols_allowed }) &&
            (!rule.require_digit || password.any { it.isDigit() })

    /** Unbiased index via rejection sampling over libsodium random bytes. */
    private fun randomIndex(bound: Int, crypto: CryptoProvider): Int {
        val limit = 256 - (256 % bound)
        while (true) {
            val b = crypto.randomBytes(1)[0].toInt() and 0xFF
            if (b < limit) return b % bound
        }
    }
}
