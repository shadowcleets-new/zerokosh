/**
 * @file EmvCard.kt
 * @description Pulls the readable fields out of an EMV contactless response.
 *
 *              Deliberately limited to what a card actually exposes: the PAN,
 *              the expiry, and the cardholder name where the issuer bothered to
 *              write one. The printed CVV is NOT on the chip and never can be —
 *              the chip's CVC3 is a per-tap value, not the number on the signature
 *              strip — so nothing here will ever produce one.
 *
 * [TABLE OF CONTENTS]
 * 1. EMV TAGS
 * 2. RESULT MODEL
 * 3. TRACK 2 DECODING
 * 4. EXTRACTION
 */
package org.zerokosh.core.emv

// #region EMV tags
private const val TAG_TRACK2_EQUIVALENT = 0x57
private const val TAG_PAN = 0x5A
private const val TAG_CARDHOLDER_NAME = 0x5F20
private const val TAG_EXPIRY = 0x5F24
private const val TAG_TRACK2_CONTACTLESS = 0x9F6B
// #endregion

// #region Result model
/**
 * What a tap yielded. Every field is nullable because issuers differ wildly in
 * what they expose — an Indian RuPay card and a Visa from the same bank will not
 * agree on the cardholder name.
 */
data class EmvCard(
    val pan: String? = null,
    /** Four digits, MMYY, ready for the card template's MONTHYEAR field. */
    val expiryMonthYear: String? = null,
    val cardholderName: String? = null,
) {
    val isEmpty: Boolean get() = pan == null && expiryMonthYear == null && cardholderName == null

    /** Last four digits, for showing the user which card was read without echoing the PAN. */
    val panLastFour: String? get() = pan?.takeLast(4)?.takeIf { it.length == 4 }
}
// #endregion

// #region Track 2 decoding
/**
 * Track 2 equivalent data packs the PAN and expiry into nibbles:
 *   PAN  'D'  YYMM  service code  discretionary  ['F' padding]
 * The separator is nibble D; F pads to a whole byte.
 */
internal fun decodeTrack2(bytes: ByteArray): Pair<String?, String?> {
    val nibbles = StringBuilder(bytes.size * 2)
    for (b in bytes) {
        val v = b.toInt() and 0xFF
        nibbles.append("0123456789ABCDEF"[v shr 4])
        nibbles.append("0123456789ABCDEF"[v and 0x0F])
    }
    val text = nibbles.toString()
    val sep = text.indexOf('D')
    if (sep <= 0) return null to null

    val pan = text.substring(0, sep)
    if (!pan.all { it.isDigit() } || pan.length !in 12..19) return null to null

    // YYMM follows the separator; the template wants MMYY.
    val after = text.substring(sep + 1)
    val expiry = if (after.length >= 4 && after.take(4).all { it.isDigit() }) {
        val yy = after.substring(0, 2)
        val mm = after.substring(2, 4)
        if (mm.toInt() in 1..12) mm + yy else null
    } else {
        null
    }
    return pan to expiry
}

/** Tag 5F24 is YYMMDD in packed BCD; the template wants MMYY. */
internal fun decodeExpiryTag(bytes: ByteArray): String? {
    if (bytes.size < 2) return null
    val yy = "%02X".format(bytes[0])
    val mm = "%02X".format(bytes[1])
    if (!yy.all { it.isDigit() } || !mm.all { it.isDigit() }) return null
    return if (mm.toInt() in 1..12) mm + yy else null
}

/** Tag 5A is the PAN in packed BCD, right-padded with F nibbles. */
internal fun decodePanTag(bytes: ByteArray): String? {
    val digits = bytes.joinToString("") { "%02X".format(it) }.trimEnd('F')
    return digits.takeIf { it.isNotEmpty() && it.all(Char::isDigit) && it.length in 12..19 }
}
// #endregion

// #region Extraction
object EmvReader {

    /**
     * Reads every response the card returned and keeps the first plausible value
     * for each field. Track 2 is preferred for the PAN because it is the tag most
     * consistently present across schemes; tag 5A is the fallback.
     */
    fun extract(responses: List<ByteArray>): EmvCard {
        var pan: String? = null
        var expiry: String? = null
        var name: String? = null

        for (response in responses) {
            val nodes = Tlv.parse(response)

            for (tag in listOf(TAG_TRACK2_EQUIVALENT, TAG_TRACK2_CONTACTLESS)) {
                for (node in Tlv.findAll(nodes, tag)) {
                    val (p, e) = decodeTrack2(node.value)
                    if (pan == null) pan = p
                    if (expiry == null) expiry = e
                }
            }
            if (pan == null) {
                Tlv.find(nodes, TAG_PAN)?.let { pan = decodePanTag(it.value) }
            }
            if (expiry == null) {
                Tlv.find(nodes, TAG_EXPIRY)?.let { expiry = decodeExpiryTag(it.value) }
            }
            if (name == null) {
                Tlv.find(nodes, TAG_CARDHOLDER_NAME)?.let { node ->
                    val raw = node.value.toString(Charsets.US_ASCII).trim()
                    // Issuers that omit the name still send the field, filled with
                    // spaces or a literal placeholder.
                    name = raw.takeIf {
                        it.isNotBlank() && !it.equals("UNKNOWN", true) && it != "/"
                    }?.replace(Regex("\\s+"), " ")
                }
            }
        }
        return EmvCard(pan = pan, expiryMonthYear = expiry, cardholderName = name)
    }
}
// #endregion
