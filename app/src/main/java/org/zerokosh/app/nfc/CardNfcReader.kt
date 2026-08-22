/**
 * @file CardNfcReader.kt
 * @description Reads the openly readable fields off a contactless payment card.
 *
 *              Reader mode, not host card emulation: this is the phone acting as
 *              a terminal, which is the opposite direction to Tap-to-Pay. The two
 *              conflict, so NFC dispatch for other apps is suppressed while the
 *              sheet is open and released the moment it closes.
 *
 *              What comes back is the PAN, the expiry, and sometimes the
 *              cardholder name. The printed CVV is not on the chip and cannot be
 *              read by anything — see EmvCard.
 *
 * [TABLE OF CONTENTS]
 * 1. IMPORTS
 * 2. APDUs
 * 3. READ SEQUENCE
 * 4. READER MODE BINDING
 */
package org.zerokosh.app.nfc

// #region Imports
import android.app.Activity
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.IsoDep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.zerokosh.core.emv.EmvCard
import org.zerokosh.core.emv.EmvReader
import org.zerokosh.core.emv.looksLikeRandomUid
import org.zerokosh.core.emv.Tlv
// #endregion

// #region APDUs
/** The contactless payment directory every EMV card exposes. */
private val SELECT_PPSE = byteArrayOf(
    0x00, 0xA4.toByte(), 0x04, 0x00, 0x0E,
    0x32, 0x50, 0x41, 0x59, 0x2E, 0x53, 0x59, 0x53, 0x2E,
    0x44, 0x44, 0x46, 0x30, 0x31,
    0x00,
)

/** GET PROCESSING OPTIONS with an empty PDOL, which most contactless cards accept. */
private val GPO_EMPTY = byteArrayOf(
    0x80.toByte(), 0xA8.toByte(), 0x00, 0x00, 0x02, 0x83.toByte(), 0x00, 0x00,
)

private const val TAG_APPLICATION_TEMPLATE = 0x61
private const val TAG_AID = 0x4F
private const val TAG_AFL = 0x94

private fun selectAid(aid: ByteArray): ByteArray =
    byteArrayOf(0x00, 0xA4.toByte(), 0x04, 0x00, aid.size.toByte()) + aid + 0x00

private fun readRecord(record: Int, sfi: Int): ByteArray =
    byteArrayOf(
        0x00, 0xB2.toByte(), record.toByte(), ((sfi shl 3) or 4).toByte(), 0x00,
    )

/**
 * Wallet apps do not hold the number printed on the card. Google Pay and
 * Samsung Pay provision a device token (a DPAN) under EMVCo tokenisation; the
 * real number never leaves the issuer. So this is not a limitation worth
 * working around — a read that "succeeded" would store a number that is not
 * the user's card and cannot be used anywhere else.
 */
private const val PHONE_NOT_A_CARD =
    "That looks like another phone, not a card. Wallet apps only ever emit a " +
        "device token, never the number printed on your card, so it cannot be " +
        "copied this way. Tap the physical card instead."

private const val NO_NUMBER_FOUND =
    "Read the card but found no number on it. Try holding it still against the back of the phone."

/** A response is usable only when it ends in the 0x9000 success word. */
private fun ByteArray.isOk(): Boolean =
    size >= 2 && this[size - 2] == 0x90.toByte() && this[size - 1] == 0x00.toByte()

private fun ByteArray.body(): ByteArray = if (size >= 2) copyOfRange(0, size - 2) else this
// #endregion

// #region Read sequence
object CardNfcReader {

    /**
     * Runs the EMV read against an already-connected tag. Every step is
     * best-effort: cards vary enough that a failure at one stage often still
     * leaves enough behind to recover a PAN, so responses are accumulated
     * throughout rather than gated on a clean run.
     */
    suspend fun read(tag: Tag): Result<EmvCard> = withContext(Dispatchers.IO) {
        val isoDep = IsoDep.get(tag)
            ?: return@withContext Result.failure(
                IllegalStateException("That does not look like a payment card."),
            )
        runCatching {
            isoDep.timeout = 5_000
            isoDep.connect()
            val responses = mutableListOf<ByteArray>()

            val ppse = isoDep.transceive(SELECT_PPSE)
            if (ppse.isOk()) responses += ppse.body()

            val aids = Tlv.parse(ppse.body())
                .let { Tlv.findAll(it, TAG_APPLICATION_TEMPLATE) }
                .mapNotNull { app -> Tlv.find(app.children, TAG_AID)?.value }
                .ifEmpty { Tlv.findAll(Tlv.parse(ppse.body()), TAG_AID).map { it.value } }

            for (aid in aids) {
                val selected = runCatching { isoDep.transceive(selectAid(aid)) }.getOrNull()
                    ?: continue
                if (!selected.isOk()) continue
                responses += selected.body()

                val gpo = runCatching { isoDep.transceive(GPO_EMPTY) }.getOrNull()
                if (gpo != null && gpo.isOk()) responses += gpo.body()

                // Walk the Application File Locator when the card offered one,
                // otherwise sweep the short file identifiers cards commonly use.
                val afl = gpo?.body()?.let { Tlv.find(Tlv.parse(it), TAG_AFL)?.value }
                val slots: List<Pair<Int, Int>> = if (afl != null && afl.size >= 4) {
                    afl.toList().chunked(4).flatMap { entry ->
                        if (entry.size < 4) return@flatMap emptyList()
                        val sfi = (entry[0].toInt() and 0xFF) shr 3
                        val first = entry[1].toInt() and 0xFF
                        val last = entry[2].toInt() and 0xFF
                        (first..last).map { it to sfi }
                    }
                } else {
                    (1..4).flatMap { sfi -> (1..4).map { rec -> rec to sfi } }
                }

                for ((record, sfi) in slots) {
                    val response = runCatching { isoDep.transceive(readRecord(record, sfi)) }
                        .getOrNull() ?: continue
                    if (response.isOk()) responses += response.body()
                }
                // One application is enough; a card with several would only repeat itself.
                if (responses.size > 1) break
            }

            val card = EmvReader.extract(responses)
            if (card.pan == null) {
                // A phone in a wallet app answers the field but not the read, and
                // the generic "hold it still" advice sends the user round in
                // circles trying a thing that cannot work.
                throw IllegalStateException(
                    if (looksLikeRandomUid(tag.id)) PHONE_NOT_A_CARD else NO_NUMBER_FOUND,
                )
            }
            card
        }.also {
            runCatching { isoDep.close() }
        }
    }
}
// #endregion

// #region Reader mode binding
/**
 * Turns the phone into a terminal for as long as the sheet is open. Reader mode
 * is scoped to the activity deliberately: leaving it on would keep intercepting
 * taps meant for the user's payment app.
 */
class NfcReaderSession(private val activity: Activity) {

    private val adapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(activity)

    val isAvailable: Boolean get() = adapter != null
    val isEnabled: Boolean get() = adapter?.isEnabled == true

    fun start(onTag: (Tag) -> Unit) {
        adapter?.enableReaderMode(
            activity,
            { tag -> onTag(tag) },
            NfcAdapter.FLAG_READER_NFC_A or
                NfcAdapter.FLAG_READER_NFC_B or
                // The platform sound is a payment cue; this is not a payment.
                NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS or
                NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            null,
        )
    }

    fun stop() {
        runCatching { adapter?.disableReaderMode(activity) }
    }
}
// #endregion
