/**
 * @file QrDecode.kt
 * @description Reading a TOTP enrolment QR out of a camera frame's luminance.
 *
 * Kept free of Android so the part that is easy to get wrong can be tested on
 * the JVM, including against frame layouts the test device never produces.
 */
package org.zerokosh.app.ui.authenticator

// #region Imports
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
// #endregion

/**
 * An otpauth URI or a bare base32 secret from a luminance plane, or null.
 *
 * Row stride is the part that used to be wrong. A camera's Y plane is not
 * guaranteed to be packed: many devices pad each row, so a row is [rowStride]
 * bytes of which only the first [width] are picture. The old code told ZXing
 * the rows were [width] wide, which is right only when there is no padding.
 * When there is, every row after the first starts in the wrong place and the
 * image ZXing sees is sheared — so a perfectly good QR simply never scans.
 * Passing the stride as the data width and cropping to the real width reads
 * the frame as it actually is.
 */
internal fun decodeOtpLuminance(
    reader: MultiFormatReader,
    data: ByteArray,
    rowStride: Int,
    width: Int,
    height: Int,
): String? {
    if (rowStride <= 0) return null
    val rows = data.size / rowStride
    if (rowStride < width || rows < height) return null
    val source = PlanarYUVLuminanceSource(data, rowStride, rows, 0, 0, width, height, false)
    val result = runCatching { reader.decodeWithState(BinaryBitmap(HybridBinarizer(source))) }.getOrNull()
        ?: runCatching {
            reader.decodeWithState(BinaryBitmap(HybridBinarizer(source.rotateCounterClockwise())))
        }.getOrNull()
        ?: return null
    return result.text.takeIf { isOtpPayload(it) }
}

/** What a TOTP enrolment QR can legitimately hold. */
internal fun isOtpPayload(text: String): Boolean =
    text.startsWith("otpauth://", ignoreCase = true) ||
        text.matches(Regex("^[A-Z2-7=]+$", RegexOption.IGNORE_CASE))
