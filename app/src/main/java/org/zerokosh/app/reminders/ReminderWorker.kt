/**
 * @file ReminderWorker.kt
 * @description §5.7 local reminders via WorkManager daily check.
 *              Notification text NEVER contains secret values.
 */
package org.zerokosh.app.reminders

// #region Imports
import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.zerokosh.core.model.Record
import org.zerokosh.app.ZerokoshApp
import org.zerokosh.app.R
import org.zerokosh.app.data.VaultState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
// #endregion

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    /** Inside the window that starts `daysBefore` ahead and ends on the day. */
    private fun isDue(dateStr: String?, daysBefore: Int, today: LocalDate): Boolean {
        val raw = dateStr ?: return false
        val due = runCatching { LocalDate.parse(raw.take(10), DateTimeFormatter.ISO_LOCAL_DATE) }
            .getOrNull() ?: return false
        return !today.isBefore(due.minusDays(daysBefore.toLong())) && !today.isAfter(due)
    }

    /**
     * POST_NOTIFICATIONS became a runtime permission at API 33. Without it a
     * notify() is dropped on the floor, so the check is not lint appeasement —
     * it is the difference between a reminder and a silent no-op.
     */
    private fun canNotify(): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED

    // Lint cannot follow the check into canNotify(), so it is named here rather
    // than inlined: the guard is real and on the first line, and duplicating it
    // to satisfy flow analysis would leave two copies to keep in step.
    @SuppressLint("MissingPermission")
    private fun notifyFor(record: Record, id: Int) {
        if (!canNotify()) return
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_my_calendar)
            .setContentTitle(applicationContext.getString(R.string.scr_reminder_title))
            .setContentText(applicationContext.getString(R.string.scr_reminder_body, record.title))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(applicationContext).notify(id, notification)
    }

    /**
     * The dates worth a reminder: the user's own, plus a 30-day default on the
     * common ones they did not set (§5.7). Returns field ids only so the
     * notifying stays in one place.
     */
    private fun dueFields(record: Record, today: LocalDate): List<String> {
        val explicit = record.reminders
            .filter { isDue(record.fields[it.field_id], it.days_before, today) }
            .map { it.field_id }
        val suggested = AUTO_SUGGEST_FIELDS
            .filter { field -> record.reminders.none { it.field_id == field } }
            .filter { isDue(record.fields[it], AUTO_SUGGEST_DAYS, today) }
        return explicit + suggested
    }

    override suspend fun doWork(): Result {
        val app = applicationContext as? ZerokoshApp ?: return Result.success()
        if (app.repository.state.value != VaultState.Unlocked) return Result.success()
        val body = app.repository.body.value ?: return Result.success()
        ensureChannel(applicationContext)

        val today = LocalDate.now()
        var notifId = 7000
        for (record in body.records) {
            repeat(dueFields(record, today).size) { notifyFor(record, notifId++) }
        }
        notifyMissingKit(app)
        return Result.success()
    }

    /**
     * Two nudges, at a week and at a month, then silence.
     *
     * The onboarding step asks when the vault is empty and the risk is
     * abstract; a week later there is real data in it and the same question
     * means something. Capped at two because a warning that arrives every day
     * is one people learn to swipe away without reading, which is worse than
     * not sending it.
     */
    // Same reason as notifyFor above: lint cannot follow the guard into
    // canNotify(), and duplicating the check here to satisfy flow analysis
    // would leave two copies to keep in step.
    @SuppressLint("MissingPermission")
    private fun notifyMissingKit(app: ZerokoshApp) {
        if (app.prefs.recoveryKitSaved || !canNotify()) return
        val created = app.prefs.vaultCreatedMs
        if (created <= 0L) return
        val ageDays = (System.currentTimeMillis() - created) / (24 * 60 * 60 * 1000)
        val sent = app.prefs.kitRemindersSent
        val due = when {
            sent == 0 && ageDays >= 7 -> true
            sent == 1 && ageDays >= 30 -> true
            else -> false
        }
        if (!due) return
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(applicationContext.getString(R.string.rem_kit_title))
            .setContentText(applicationContext.getString(R.string.rem_kit_body))
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(applicationContext.getString(R.string.rem_kit_body)),
            )
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(applicationContext).notify(7999, notification)
        app.prefs.kitRemindersSent = sent + 1
    }

    companion object {
        /** Date fields that get a default reminder when the user set none (§5.7). */
        val AUTO_SUGGEST_FIELDS = listOf(
            "expiry", "premium_due_date", "membership_renewal", "renewal_date", "maturity_date",
        )
        const val AUTO_SUGGEST_DAYS = 30

        const val CHANNEL_ID = "zerokosh_reminders"
        const val WORK_NAME = "zerokosh_daily_reminders"
        const val RUN_NOW_NAME = "zerokosh_reminders_on_unlock"

        fun ensureChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= 26) {
                val nm = context.getSystemService(NotificationManager::class.java)
                nm.createNotificationChannel(
                    NotificationChannel(
                        CHANNEL_ID,
                        context.getString(R.string.scr_reminder_channel),
                        NotificationManager.IMPORTANCE_DEFAULT,
                    ),
                )
            }
        }

        fun schedule(context: Context) {
            ensureChannel(context)
            val req = PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                req,
            )
        }

        /**
         * doWork() can only read the vault while it is unlocked, so the daily
         * periodic run would almost always find it locked and return early. This
         * fires a check at the one moment the records are readable — right after
         * the user unlocks (BV-02).
         */
        fun runNow(context: Context) {
            ensureChannel(context)
            WorkManager.getInstance(context).enqueueUniqueWork(
                RUN_NOW_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequestBuilder<ReminderWorker>().build(),
            )
        }
    }
}
