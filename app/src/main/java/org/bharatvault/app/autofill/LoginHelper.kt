/**
 * @file LoginHelper.kt
 * @description §6.7 copy-mode fallback for Indian bank apps that block autofill.
 *              Posts a notification with sequential buttons: User ID → Password
 *              → Transaction password; each tap copies the next value (30 s clear).
 *              NEVER uses AccessibilityService.
 */
package org.bharatvault.app.autofill

// #region Imports
import android.app.NotificationChannel
import android.app.NotificationManager
import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import org.bharatvault.app.R
import org.bharatvault.app.ui.common.ClipboardHelper
import org.bharatvault.core.model.Record
// #endregion

object LoginHelper {
    const val CHANNEL_ID = "bharatvault_login_helper"
    const val NOTIF_ID = 6107
    const val ACTION_COPY_STEP = "org.bharatvault.app.COPY_LOGIN_STEP"
    const val EXTRA_VALUE = "value"
    const val EXTRA_LABEL = "label"

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val nm = context.getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    context.getString(R.string.scr_login_helper_channel),
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply { description = context.getString(R.string.scr_login_helper_channel_desc) },
            )
        }
    }

    /**
     * Build ordered steps from a bank_account / login / demat record:
     * netbanking_user_id / username / client_id → login_password / password → transaction_password.
     */
    fun stepsFor(record: Record): List<Pair<String, String>> {
        val steps = mutableListOf<Pair<String, String>>()
        fun add(label: String, vararg keys: String) {
            val v = keys.firstNotNullOfOrNull { record.fields[it]?.takeIf { s -> s.isNotBlank() } }
            if (v != null) steps += label to v
        }
        add("User ID", "netbanking_user_id", "username", "client_id", "customer_id", "card_portal_login", "portal_login")
        add("Password", "login_password", "password", "password_if_any", "card_portal_password", "portal_password")
        add("Transaction password", "transaction_password", "profile_password", "tpin", "mpin")
        return steps
    }

    fun show(context: Context, record: Record) {
        ensureChannel(context)
        val steps = stepsFor(record)
        if (steps.isEmpty()) return

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_lock)
            .setContentTitle(context.getString(R.string.scr_login_helper_title, record.title))
            .setContentText(context.getString(R.string.scr_login_helper_body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)

        steps.forEachIndexed { index, (label, value) ->
            val intent = Intent(context, LoginHelperReceiver::class.java).apply {
                action = ACTION_COPY_STEP
                putExtra(EXTRA_VALUE, value)
                putExtra(EXTRA_LABEL, label)
            }
            val pi = PendingIntent.getBroadcast(
                context,
                index + 1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
            builder.addAction(0, label, pi)
        }

        // BV-14: POST_NOTIFICATIONS is a runtime permission on Android 13+, and a
        // notify() without it throws SecurityException. Autofill runs in a service
        // with no UI to prompt from, so this posts only when the grant is already
        // there and stays silent otherwise.
        val allowed = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) ==
            PackageManager.PERMISSION_GRANTED
        if (allowed) {
            runCatching {
                NotificationManagerCompat.from(context).notify(NOTIF_ID, builder.build())
            }
        }
    }
}

class LoginHelperReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != LoginHelper.ACTION_COPY_STEP) return
        val value = intent.getStringExtra(LoginHelper.EXTRA_VALUE) ?: return
        ClipboardHelper.copySensitive(context, value)
    }
}
