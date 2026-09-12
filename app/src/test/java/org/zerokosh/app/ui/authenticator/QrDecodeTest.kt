/**
 * @file QrDecodeTest.kt
 * @description Reading a TOTP QR out of camera frames, padded and not.
 *
 * The case that matters is the padded one. The test device packs its rows, so
 * the bug this guards against could not be reproduced on it — which is exactly
 * why it is pinned here instead, by building the frame layout other phones
 * produce and checking a real QR still reads.
 */
package org.zerokosh.app.ui.authenticator

import com.google.zxing.BarcodeFormat
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.qrcode.QRCodeWriter
import java.util.EnumMap
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class QrDecodeTest {

    private val payload = "otpauth://totp/Zerokosh:test?secret=JBSWY3DPEHPK3PXP&issuer=Zerokosh"

    private fun reader() = MultiFormatReader().apply {
        setHints(
            EnumMap<DecodeHintType, Any>(DecodeHintType::class.java).apply {
                put(DecodeHintType.POSSIBLE_FORMATS, listOf(BarcodeFormat.QR_CODE))
            },
        )
    }

    /**
     * A Y plane of [width] x [height] with [padding] extra bytes on every row,
     * the way a camera lays one out when its stride exceeds the picture width.
     * The padding is filled with mid-grey noise rather than zeroes, so a reader
     * that wrongly treats it as picture sees garbage rather than a clean edge.
     */
    private fun frame(width: Int, height: Int, padding: Int): Pair<ByteArray, Int> {
        val matrix = QRCodeWriter().encode(payload, BarcodeFormat.QR_CODE, width, height)
        val stride = width + padding
        val data = ByteArray(stride * height) { i -> ((i * 37) % 200 + 28).toByte() }
        for (y in 0 until height) {
            for (x in 0 until width) {
                data[y * stride + x] = if (matrix[x, y]) 0 else 255.toByte()
            }
        }
        return data to stride
    }

    @Test
    fun `a packed frame decodes`() {
        val (data, stride) = frame(width = 480, height = 480, padding = 0)
        assertEquals(payload, decodeOtpLuminance(reader(), data, stride, 480, 480))
    }

    /** The case the old code got wrong, on the devices that pad their rows. */
    @Test
    fun `a padded frame decodes when the stride is honoured`() {
        val (data, stride) = frame(width = 480, height = 480, padding = 96)
        assertEquals(payload, decodeOtpLuminance(reader(), data, stride, 480, 480))
    }

    /**
     * The old behaviour, reproduced: tell ZXing the rows are `width` wide when
     * they are really `stride` wide. Every row after the first is read from the
     * wrong offset, the QR is sheared, and nothing comes back.
     */
    @Test
    fun `treating a padded frame as packed is exactly what failed`() {
        val (data, _) = frame(width = 480, height = 480, padding = 96)
        assertNull(decodeOtpLuminance(reader(), data, rowStride = 480, width = 480, height = 480))
    }

    @Test
    fun `a malformed frame is refused rather than read`() {
        assertNull(decodeOtpLuminance(reader(), ByteArray(100), rowStride = 0, width = 10, height = 10))
        // stride narrower than the picture it claims to hold
        assertNull(decodeOtpLuminance(reader(), ByteArray(10_000), rowStride = 50, width = 100, height = 100))
        // not enough rows for the height
        assertNull(decodeOtpLuminance(reader(), ByteArray(1_000), rowStride = 100, width = 100, height = 100))
    }

    @Test
    fun `only TOTP payloads are accepted`() {
        assertTrue(isOtpPayload("otpauth://totp/x?secret=ABC"))
        assertTrue(isOtpPayload("JBSWY3DPEHPK3PXP"))
        assertFalse(isOtpPayload("https://example.com"))
        assertFalse(isOtpPayload("WIFI:S:home;T:WPA;P:password;;"))
    }

    /**
     * An otpauth:// prefix used to be enough to be scanned and saved, which is
     * how a counter-based or malformed code became a stored record that crashed
     * the list it appeared in. The filter now asks whether a code can be made.
     */
    @Test
    fun `otpauth payloads that cannot make a code are not accepted`() {
        assertFalse(isOtpPayload("otpauth://hotp/ACME?secret=JBSWY3DPEHPK3PXP&counter=1"))
        assertFalse(isOtpPayload("otpauth://totp/NoSecret?issuer=x"))
        assertFalse(isOtpPayload("otpauth-migration://offline?data=CjEKCkhlbGxv"))
        assertFalse(isOtpPayload("otpauth://totp/Empty?secret=A"))
    }
}
