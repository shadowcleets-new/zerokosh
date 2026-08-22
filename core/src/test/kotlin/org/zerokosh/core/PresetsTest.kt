package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.zerokosh.core.model.Template
import org.zerokosh.core.model.TemplateField
import org.zerokosh.core.model.seedFieldsFromPreset

private val PICKERS = mapOf(
    "banks" to listOf("State Bank of India", "HDFC Bank", "ICICI Bank", "Yes Bank"),
    "card_kinds" to listOf("Debit", "Credit", "Prepaid", "Forex"),
    "card_networks" to listOf("RuPay", "Visa", "Mastercard"),
    "account_types" to listOf("Savings", "Current", "Salary"),
)

private val BANK = Template(
    id = "bank_account",
    icon = "bank",
    fields = listOf(
        TemplateField(k = "bank_name", t = "PICKER:banks", s = "L"),
        TemplateField(k = "account_type", t = "PICKER:account_types", s = "L"),
        TemplateField(k = "account_number", t = "NUMBER", s = "H"),
    ),
)

private val CARD = Template(
    id = "card",
    icon = "card",
    fields = listOf(
        TemplateField(k = "card_type", t = "PICKER:card_kinds", s = "L"),
        TemplateField(k = "card_network", t = "PICKER:card_networks", s = "L"),
    ),
)

class PresetsTest {

    @Test
    fun `the bank the user picked fills the bank field`() {
        val seed = seedFieldsFromPreset(BANK, "ICICI Bank", PICKERS)
        assertEquals("ICICI Bank", seed["bank_name"])
    }

    @Test
    fun `an unrelated picker on the same template is left alone`() {
        // account_type is not answered by choosing a bank; guessing "Savings"
        // would put a fact in the vault the user never stated
        assertNull(seedFieldsFromPreset(BANK, "ICICI Bank", PICKERS)["account_type"])
    }

    @Test
    fun `non-picker fields are never seeded`() {
        assertNull(seedFieldsFromPreset(BANK, "ICICI Bank", PICKERS)["account_number"])
    }

    @Test
    fun `the pickers spelling wins over the gallerys`() {
        // the gallery catalog says "YES Bank"; the picker says "Yes Bank", and
        // a value the picker cannot match renders as "Other"
        assertEquals("Yes Bank", seedFieldsFromPreset(BANK, "YES Bank", PICKERS)["bank_name"])
    }

    @Test
    fun `a whole word inside the brand fills too`() {
        val seed = seedFieldsFromPreset(CARD, "HDFC Credit Card", PICKERS)
        assertEquals("Credit", seed["card_type"])
        assertNull(seed["card_network"])
    }

    @Test
    fun `the network is read out of the brand when it is there`() {
        assertEquals("RuPay", seedFieldsFromPreset(CARD, "RuPay Card", PICKERS)["card_network"])
    }

    @Test
    fun `a partial word never matches`() {
        // "Creditor Ledger" must not be read as a credit card
        assertTrue(seedFieldsFromPreset(CARD, "Creditor Ledger", PICKERS).isEmpty())
    }

    @Test
    fun `a brand that answers nothing seeds nothing`() {
        assertTrue(seedFieldsFromPreset(BANK, "Zerodha", PICKERS).isEmpty())
    }

    @Test
    fun `a missing picker list costs that field, not the seed`() {
        val t = Template(
            "x", "x",
            listOf(
                TemplateField(k = "nope", t = "PICKER:does_not_exist", s = "L"),
                TemplateField(k = "bank_name", t = "PICKER:banks", s = "L"),
            ),
        )
        assertEquals("HDFC Bank", seedFieldsFromPreset(t, "HDFC Bank", PICKERS)["bank_name"])
    }
}
