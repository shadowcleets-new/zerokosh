package org.zerokosh.app.pdf

// #region Imports
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.print.PrintManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.zerokosh.app.R
import java.io.FileOutputStream
import java.io.OutputStream
// #endregion

/**
 * §5.1 S4: A4 Recovery Kit PDF — key as text + QR + plain-language
 * instructions. Built with the platform PdfDocument (no extra dependency);
 * QR via the bundled zxing encoder (DECISIONS.md D-006).
 */
object RecoveryKitPdf {

    private const val A4_WIDTH = 595
    private const val A4_HEIGHT = 842

    fun write(context: Context, recoveryKey: String, out: OutputStream) {
        val doc = PdfDocument()
        val page = doc.startPage(PdfDocument.PageInfo.Builder(A4_WIDTH, A4_HEIGHT, 1).create())
        val canvas = page.canvas

        val title = Paint().apply {
            textSize = 24f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            color = Color.rgb(0x1A, 0x6B, 0x54)
        }
        val body = Paint().apply { textSize = 12f; color = Color.BLACK }
        val mono = Paint().apply {
            textSize = 15f
            typeface = Typeface.MONOSPACE
            color = Color.BLACK
        }

        var y = 70f
        canvas.drawText(context.getString(R.string.scr_recovery_pdf_title), 50f, y, title)
        y += 40f
        drawWrapped(canvas, context.getString(R.string.scr_recovery_body), 50f, y, body).also { y = it + 30f }

        // The key, centered, monospace
        val keyWidth = mono.measureText(recoveryKey)
        canvas.drawText(recoveryKey, (A4_WIDTH - keyWidth) / 2f, y, mono)
        y += 40f

        // QR of the raw key string
        val qr = qrBitmap(recoveryKey, 220)
        canvas.drawBitmap(qr, (A4_WIDTH - qr.width) / 2f, y, null)
        y += qr.height + 40f

        drawWrapped(canvas, context.getString(R.string.scr_recovery_pdf_instructions), 50f, y, body)

        doc.finishPage(page)
        doc.writeTo(out)
        doc.close()
    }

    /**
     * Hands the same page to Android's print spooler.
     *
     * Worth the extra path because it reaches the two places a vault key
     * actually belongs — a sheet of paper in a drawer, and whatever "Save as
     * PDF" destination the user already trusts — without Zerokosh needing to
     * know about printers or storage providers at all. It also survives the
     * phone, which no on-device copy does.
     *
     * Returns false when the platform has no print service, so the caller does
     * not claim a save that never happened.
     */
    fun print(context: Context, recoveryKey: String): Boolean {
        val manager = context.getSystemService(Context.PRINT_SERVICE) as? PrintManager ?: return false
        val adapter = object : PrintDocumentAdapter() {
            override fun onLayout(
                oldAttributes: PrintAttributes?,
                newAttributes: PrintAttributes?,
                cancellationSignal: CancellationSignal?,
                callback: LayoutResultCallback,
                extras: Bundle?,
            ) {
                if (cancellationSignal?.isCanceled == true) {
                    callback.onLayoutCancelled()
                    return
                }
                callback.onLayoutFinished(
                    PrintDocumentInfo.Builder("Zerokosh-Recovery-Kit.pdf")
                        .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(1)
                        .build(),
                    newAttributes != oldAttributes,
                )
            }

            override fun onWrite(
                pages: Array<out PageRange>?,
                destination: ParcelFileDescriptor,
                cancellationSignal: CancellationSignal?,
                callback: WriteResultCallback,
            ) {
                val ok = runCatching {
                    FileOutputStream(destination.fileDescriptor).use { out ->
                        write(context, recoveryKey, out)
                    }
                }.isSuccess
                if (ok) callback.onWriteFinished(arrayOf(PageRange.ALL_PAGES)) else callback.onWriteFailed(null)
            }
        }
        return runCatching {
            manager.print("Zerokosh Recovery Kit", adapter, null)
        }.isSuccess
    }

    private fun drawWrapped(canvas: Canvas, text: String, x: Float, startY: Float, paint: Paint): Float {
        val maxWidth = A4_WIDTH - 2 * x
        var y = startY
        var line = StringBuilder()
        for (word in text.split(" ")) {
            val candidate = if (line.isEmpty()) word else "$line $word"
            if (paint.measureText(candidate) > maxWidth) {
                canvas.drawText(line.toString(), x, y, paint)
                y += paint.textSize * 1.5f
                line = StringBuilder(word)
            } else {
                line = StringBuilder(candidate)
            }
        }
        if (line.isNotEmpty()) {
            canvas.drawText(line.toString(), x, y, paint)
        }
        return y
    }

    private fun qrBitmap(content: String, size: Int): Bitmap {
        val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
        for (x in 0 until size) {
            for (yPx in 0 until size) {
                bmp.setPixel(x, yPx, if (matrix[x, yPx]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }
}
