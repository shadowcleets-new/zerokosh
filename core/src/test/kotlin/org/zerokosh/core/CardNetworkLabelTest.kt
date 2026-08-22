package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import org.zerokosh.core.util.CardUtils

/**
 * The labels are written straight into the card_network picker, so they have to
 * be the picker's own spellings. A drift here shows up as a filled field that
 * renders as "Other".
 */
class CardNetworkLabelTest {

    @Test
    fun `labels match the card_networks picker exactly`() {
        assertEquals(
            listOf("RuPay", "Visa", "Mastercard", "American Express", "Diners Club", "Maestro"),
            CardUtils.Network.entries.map { it.label },
        )
    }
}
