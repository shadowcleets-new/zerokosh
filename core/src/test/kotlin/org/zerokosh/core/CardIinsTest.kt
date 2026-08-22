package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.zerokosh.core.card.CardIins

private val TABLE = CardIins.parse(
    """
    v1
    B	HDFC Bank	ICICI Bank
    V	Classic	Platinum
    431111 0 C 1
    607600 1 D 0
    652855 -1 D -1
    508500 -1 P -1
    """.trimIndent(),
)

class CardIinsTest {

    @Test
    fun `resolves bank kind and variant`() {
        val info = TABLE.lookup("4311111111111111")!!
        assertEquals("HDFC Bank", info.bank)
        assertEquals("Credit", info.kind)
        assertEquals("Platinum", info.variant)
    }

    @Test
    fun `resolves a debit card`() {
        val info = TABLE.lookup("6076001234567890")!!
        assertEquals("ICICI Bank", info.bank)
        assertEquals("Debit", info.kind)
        assertEquals("Classic", info.variant)
    }

    @Test
    fun `an unnamed issuer still yields the kind`() {
        // the case that matters: no bank, but debit-vs-credit is still known
        val info = TABLE.lookup("6528551234567890")!!
        assertNull(info.bank)
        assertEquals("Debit", info.kind)
        assertNull(info.variant)
        assertTrue(!info.isEmpty)
    }

    @Test
    fun `prepaid is a kind of its own`() {
        assertEquals("Prepaid", TABLE.lookup("5085001234567890")?.kind)
    }

    @Test
    fun `accepts a bare iin as well as a full pan`() {
        assertEquals("HDFC Bank", TABLE.lookup("431111")?.bank)
    }

    @Test
    fun `tolerates the spaces the card field renders`() {
        assertEquals("HDFC Bank", TABLE.lookup("4311 1111 1111 1111")?.bank)
    }

    @Test
    fun `an unknown iin is null, not a guess`() {
        assertNull(TABLE.lookup("999999123456"))
    }

    @Test
    fun `too few digits yields null`() {
        assertNull(TABLE.lookup("4311"))
    }

    @Test
    fun `a malformed line costs that line, not the table`() {
        val t = CardIins.parse(
            """
            v1
            B	HDFC Bank
            V	Classic
            431111 0 C 0
            this is not a row
            9999 0 C 0
            607600 x C 0
            """.trimIndent(),
        )
        assertEquals("HDFC Bank", t.lookup("431111")?.bank)
        assertEquals(1, t.size)
    }

    @Test
    fun `an empty table answers null rather than throwing`() {
        assertNull(CardIins.EMPTY.lookup("4311111111111111"))
    }
}
