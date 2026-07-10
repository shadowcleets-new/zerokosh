/**
 * @file ReminderWorker.kt
 * @description §5.7 local reminders via WorkManager daily check.
 *              Notification text NEVER contains secret values.
 */
package org.bharatvault.app.reminders

// #region Imports
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import org.bharatvault.app.BharatVaultApp
import org.bharatvault.app.R
import org.bharatvault.app.data.VaultState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
// #endregion

class ReminderWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val app = applicationContext as? BharatVaultApp ?: return Result.success()
        if (app.repository.state.value != VaultState.Unlocked) return Result.success()
        val body = app.repository.body.value ?: return Result.success()
        ensureChannel(applicationContext)
        val today = LocalDate.now()
        val fmt = DateTimeFormatter.ISO_LOCAL_DATE
        var notifId = 7000
        for (record in body.records) {
            for (reminder in record.reminders) {
                val dateStr = record.fields[reminder.field_id] ?: continue
                val due = runCatching { LocalDate.parse(dateStr.take(10), fmt) }.getOrNull() ?: continue
                val trigger = due.minusDays(reminder.days_before.toLong())
                if (!today.isBefore(trigger) && !today.isAfter(due)) {
                    val text = applicationContext.getString(
                        R.string.scr_reminder_body,
                        record.title,
                    )
                    val n = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_menu_my_calendar)
                        .setContentTitle(applicationContext.getString(R.string.scr_reminder_title))
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .build()
                    NotificationManagerCompat.from(applicationContext).notify(notifId++, n)
                }
            }
            // Auto-suggest 30-day reminders for common date fields (§5.7)
            for (fieldId in listOf("expiry", "premium_due_date", "membership_renewal", "renewal_date")) {
                if (record.reminders.any { it.field_id == fieldId }) continue
                val dateStr = record.fields[fieldId] ?: continue
                val due = runCatching { LocalDate.parse(dateStr.take(10), fmt) }.getOrNull() ?: continue
                val trigger = due.minusDays(30)
                if (!today.isBefore(trigger) && !today.isAfter(due)) {
                    val text = applicationContext.getString(R.string.scr_reminder_body, record.title)
                    val n = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_menu_my_calendar)
                        .setContentTitle(applicationContext.getString(R.string.scr_reminder_title))
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true)
                        .build()
                    NotificationManagerCompat.from(applicationContext).notify(notifId++, n)
                }
            }
        }
        return Result.success()
    }

    companion object {
        const val CHANNEL_ID = "bharatvault_reminders"
        const val WORK_NAME = "bharatvault_daily_reminders"

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
    }
}
