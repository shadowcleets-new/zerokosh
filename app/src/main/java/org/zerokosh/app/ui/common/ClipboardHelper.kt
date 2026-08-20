package org.zerokosh.app.ui.common

// #region Imports
import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
// #endregion

/**
 * §5.3/§6.6 clipboard hygiene: M/H copies are marked sensitive (excluded from
 * clipboard history/sync where the OS honors it) and cleared after 30 s via a
 * foreground timer, with a WorkManager fallback that survives OEM background
 * kills (MIUI/HyperOS etc.).
 */
object ClipboardHelper {

    const val CLEAR_AFTER_MS = 30_000L
    private val handler = Handler(Looper.getMainLooper())
    private var lastSensitiveCopyAtMs = 0L

    fun copySensitive(context: Context, value: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Zerokosh", value).apply {
            description.extras = PersistableBundle().apply {
                putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
            }
        }
        cm.setPrimaryClip(clip)
        lastSensitiveCopyAtMs = System.currentTimeMillis()

        handler.removeCallbacksAndMessages(TOKEN)
        handler.postAtTime({ clearIfOurs(context) }, TOKEN, android.os.SystemClock.uptimeMillis() + CLEAR_AFTER_MS)

        WorkManager.getInstance(context).enqueue(
            OneTimeWorkRequestBuilder<ClipboardClearWorker>()
                .setInitialDelay(CLEAR_AFTER_MS + 2_000, TimeUnit.MILLISECONDS)
                .build(),
        )
    }

    fun copyPlain(context: Context, value: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText("Zerokosh", value))
    }

    /** Clear only if the clip is still ours — never stomp another app's copy. */
    fun clearIfOurs(context: Context) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val label = cm.primaryClipDescription?.label
        if (label == "Zerokosh") {
            if (Build.VERSION.SDK_INT >= 28) cm.clearPrimaryClip()
            else cm.setPrimaryClip(ClipData.newPlainText("", ""))
        }
    }

    private val TOKEN = Any()

    class ClipboardClearWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
        override fun doWork(): Result {
            // Only fire if the 30 s window has actually elapsed (fallback path).
            if (System.currentTimeMillis() - lastSensitiveCopyAtMs >= CLEAR_AFTER_MS) {
                clearIfOurs(applicationContext)
            }
            return Result.success()
        }
    }
}
