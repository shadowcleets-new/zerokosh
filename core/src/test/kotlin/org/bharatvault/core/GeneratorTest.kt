package org.bharatvault.core

// #region Imports
import org.bharatvault.core.generator.BankRule
import org.bharatvault.core.generator.BankRules
import org.bharatvault.core.generator.PasswordGenerator
import org.bharatvault.core.model.VaultJson
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
// #endregion

/** §11.4: per bank rule, 1,000 generated passwords all satisfy it; chi-square sanity. */
class GeneratorTest {

    private val crypto = LazySodiumTestCrypto.INSTANCE

    private fun loadRules(): List<BankRule> {
        val file = File("../spec/bank_rules.json").canonicalFile
        return VaultJson.decodeFromString<BankRules>(file.readText()).rules
    }

    @Test
    fun `1000 passwords per bank rule all satisfy the rule`() {
        val rules = loadRules()
        assertTrue(rules.size >= 10, "seed 10 banks (§5.8)")
        for (rule in rules) {
            repeat(1000) {
                val pw = PasswordGenerator.generateForRule(rule, 16, crypto)
                assertTrue(PasswordGenerator.satisfiesRule(pw, rule), "${rule.bank}: $pw")
            }
        }
    }

    @Test
    fun `pin mode generates digits only`() {
        repeat(100) {
            val pin = PasswordGenerator.generatePin(6, crypto)
            assertEquals(6, pin.length)
            assertTrue(pin.all { it.isDigit() })
        }
        assertEquals(4, PasswordGenerator.generatePin(4, crypto).length)
    }

    @Test
    fun `all enabled classes appear`() {
        repeat(200) {
            val pw = PasswordGenerator.generate(8, upper = true, lower = true, digits = true, symbols = true, crypto = crypto)
            assertTrue(pw.any { it.isUpperCase() } && pw.any { it.isLowerCase() } && pw.any { it.isDigit() } && pw.any { !it.isLetterOrDigit() }, pw)
        }
    }

    @Test
    fun `chi-square sanity on character distribution`() {
        // digits-only, large sample: chi-square over 10 bins
        val n = 50_000
        val counts = IntArray(10)
        repeat(n / 10) {
            PasswordGenerator.generatePin(6, crypto).forEach { counts[it - '0']++ }
        }
        val total = counts.sum()
        val expected = total / 10.0
        val chi2 = counts.sumOf { (it - expected) * (it - expected) / expected }
        // df=9; p=0.001 critical value ≈ 27.88. A uniform CSPRNG passes with huge margin.
        assertTrue(chi2 < 27.88, "chi-square too high: $chi2 counts=${counts.toList()}")
    }
}
