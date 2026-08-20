package org.zerokosh.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import org.zerokosh.core.emv.EmvReader
import org.zerokosh.core.emv.Tlv
import org.zerokosh.core.emv.decodeExpiryTag
import org.zerokosh.core.emv.decodePanTag
import org.zerokosh.core.emv.decodeTrack2

private fun hex(s: String): ByteArray =
    s.replace(" ", "").chunked(2).map { it.toInt(16).toByte() }.toByteArray()

class EmvParserTest {

    // ---- TLV ------------------------------------------------------------
    @Test
    fun `reads a simple tag`() {
        val nodes = Tlv.parse(hex("5A 08 4111111111111111"))
        assertEquals(1, nodes.size)
        assertEquals(0x5A, nodes[0].tag)
        assertEquals(8, nodes[0].value.size)
    }

    @Test
    fun `reads a two byte tag`() {
        val nodes = Tlv.parse(hex("5F24 03 251231"))
        assertEquals(0x5F24, nodes[0].tag)
    }

    @Test
    fun `recurses into constructed tags`() {
        // 70 (constructed) containing 5A
        val nodes = Tlv.parse(hex("70 0A 5A 08 4111111111111111"))
        assertEquals(0x70, nodes[0].tag)
        assertEquals(0x5A, Tlv.find(nodes, 0x5A)?.tag)
    }

    @Test
    fun `reads a long form length`() {
        val payload = "AA".repeat(200)
        val nodes = Tlv.parse(hex("5A 81 C8 $payload"))
        assertEquals(200, nodes[0].value.size)
    }

    @Test
    fun `skips padding between elements`() {
        val nodes = Tlv.parse(hex("00 00 5A 02 1234 FF FF"))
        assertEquals(1, nodes.size)
        assertEquals(0x5A, nodes[0].tag)
    }

    @Test
    fun `a truncated element does not lose the elements before it`() {
        // valid 5A, then a 5F24 claiming 3 bytes but carrying 1
        val nodes = Tlv.parse(hex("5A 02 1234 5F24 03 25"))
        assertEquals(1, nodes.size)
        assertEquals(0x5A, nodes[0].tag)
    }

    @Test
    fun `malformed input terminates instead of looping`() {
        // a tag that never ends
        val nodes = Tlv.parse(hex("5F FF FF FF"))
        assertTrue(nodes.isEmpty() || nodes.size < 4)
    }

    // ---- Track 2 --------------------------------------------------------
    @Test
    fun `decodes pan and expiry from track 2`() {
        // 4111111111111111 D 2512 201 ... F
        val (pan, expiry) = decodeTrack2(hex("4111111111111111D25122010000000000000F"))
        assertEquals("4111111111111111", pan)
        assertEquals("1225", expiry) // MMYY
    }

    @Test
    fun `rejects track 2 with an impossible month`() {
        val (_, expiry) = decodeTrack2(hex("4111111111111111D25992010000000000000F"))
        assertNull(expiry)
    }

    @Test
    fun `rejects track 2 with no separator`() {
        val (pan, _) = decodeTrack2(hex("41111111111111111111111111111111"))
        assertNull(pan)
    }

    // ---- individual tags -------------------------------------------------
    @Test
    fun `decodes an expiry tag to MMYY`() {
        assertEquals("1225", decodeExpiryTag(hex("251231")))
    }

    @Test
    fun `decodes a pan tag and strips padding`() {
        assertEquals("4111111111111111", decodePanTag(hex("4111111111111111")))
        assertEquals("411111111111111", decodePanTag(hex("411111111111111F")))
    }

    @Test
    fun `rejects a pan that is too short`() {
        assertNull(decodePanTag(hex("41111111FFFF")))
    }

    // ---- end to end ------------------------------------------------------
    @Test
    fun `extracts a card from a record response`() {
        val record = hex(
            "70 28" +
                "57 13 4111111111111111D25122010000000000000F" +
                "5F20 0A 5A45524F4B4F53482F4D" +
                "5F24 03 251231",
        )
        val card = EmvReader.extract(listOf(record))
        assertEquals("4111111111111111", card.pan)
        assertEquals("1225", card.expiryMonthYear)
        assertEquals("ZEROKOSH/M", card.cardholderName)
        assertEquals("1111", card.panLastFour)
    }

    @Test
    fun `falls back to tag 5A when track 2 is absent`() {
        val record = hex("70 10 5A 08 4111111111111111 5F24 03 251231")
        val card = EmvReader.extract(listOf(record))
        assertEquals("4111111111111111", card.pan)
        assertEquals("1225", card.expiryMonthYear)
    }

    @Test
    fun `a placeholder cardholder name is discarded`() {
        val record = hex("70 0B 5F20 07 554E4B4E4F574E")
        assertNull(EmvReader.extract(listOf(record)).cardholderName)
    }

    @Test
    fun `garbage in yields an empty card rather than an exception`() {
        val card = EmvReader.extract(listOf(hex("DEADBEEF"), ByteArray(0)))
        assertTrue(card.isEmpty)
    }

    @Test
    fun `never produces a cvv`() {
        // The whole point: no field on the model can carry one.
        val record = hex("70 13 57 13 4111111111111111D25122010000000000000F")
        val card = EmvReader.extract(listOf(record))
        assertTrue(card::class.java.declaredFields.none { it.name.contains("cvv", true) })
    }
}
