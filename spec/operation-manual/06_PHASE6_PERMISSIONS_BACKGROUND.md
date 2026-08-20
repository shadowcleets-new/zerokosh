# Phase 6 — System Permissions & Background Processing

## 6.1 Final `AndroidManifest.xml` (end-state after TASK-601 + TASK-801/802)

This is the COMPLETE target manifest. Tasks patch the committed file toward exactly this; diff any drift back to this document.

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- CAMERA is the ONLY dangerous permission (TOTP QR scan, runtime, optional — §0.6/§6.7) -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" android:required="false" />
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <!-- INTERNET exists ONLY for the opt-in §5.9 breach check; the app is fully functional without it -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".ZerokoshApp"
        android:label="@string/app_name"
        android:icon="@mipmap/ic_launcher"
        android:allowBackup="false"
        android:fullBackupContent="false"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:networkSecurityConfig="@xml/network_security_config"
        android:theme="@style/Theme.Zerokosh"
        android:supportsRtl="true">

        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <!-- §6.7 AutofillService (M6) -->
        <service
            android:name=".autofill.ZerokoshAutofillService"
            android:exported="true"
            android:label="@string/scr_autofill_service_label"
            android:permission="android.permission.BIND_AUTOFILL_SERVICE">
            <intent-filter>
                <action android:name="android.service.autofill.AutofillService" />
            </intent-filter>
            <meta-data
                android:name="android.autofill"
                android:resource="@xml/autofill_service" />
        </service>

        <!-- §6.7 copy-mode login helper buttons -->
        <receiver
            android:name=".autofill.LoginHelperReceiver"
            android:exported="false" />
    </application>
</manifest>
```

FORBIDDEN forever (§14): `READ_SMS`, `RECEIVE_SMS`, `READ_CONTACTS`, `ACCESS_*_LOCATION`, `READ_CALL_LOG`, `QUERY_ALL_PACKAGES`, foreground-service permissions, any `BIND_ACCESSIBILITY_SERVICE` component.

## 6.2 Runtime permission flows (exact conditional logic)

**CAMERA** (only inside `QrScannerScreen`, frozen):
```
granted = checkSelfPermission(CAMERA) == GRANTED
if !granted → LaunchedEffect fires ActivityResultContracts.RequestPermission()
callback → granted=true → CameraX binds | granted=false → show scr_auth_camera_denied
           + Cancel button. NO settings deep-link, NO re-nag loop, NO rationale dialog
           (the screen text IS the rationale).
```

**POST_NOTIFICATIONS** (API 33+; needed by reminders TASK-403 and login-helper TASK-603):
```
helper NotificationPermission.ensure(activity):
    if SDK < 33 → return true
    if checkSelfPermission(POST_NOTIFICATIONS) == GRANTED → return true
    else activity.requestPermissions(arrayOf(POST_NOTIFICATIONS), 4033); return false
Call sites: (a) RecordEditScreen save when the record gained reminders,
            (b) "Login helper" action in S9.
Denied → feature silently does nothing beyond the one system prompt; never blocks saving.
```

## 6.3 WorkManager inventory (complete — no other background work may exist)

| Work | Class | Trigger | Constraints | Policy |
|---|---|---|---|---|
| Daily reminder scan (§5.7) | `reminders.ReminderWorker` | `PeriodicWorkRequestBuilder<ReminderWorker>(1, TimeUnit.DAYS)` unique name `zerokosh_daily_reminders` | **none** (offline-first; notifications are local) | `ExistingPeriodicWorkPolicy.KEEP`; scheduled from `ZerokoshApp.onCreate` (TASK-403) |
| Clipboard clear fallback (§5.3) | `ClipboardHelper.ClipboardClearWorker` | one-time, `setInitialDelay(32 s)` per sensitive copy | none | fire-and-forget; clears only if clip label is ours AND 30 s elapsed |

Reminder behavior (frozen in `ReminderWorker`): runs only when the vault happens to be unlocked in-process (locked → `Result.success()`, silently skipped — §5.7 never decrypts in background); fires one notification per due `Reminder(field_id, days_before)` and auto-covers the §5.7 default fields (`expiry`, `premium_due_date`, `membership_renewal`, `renewal_date`) at 30 days; text = `scr_reminder_body(record.title)` — NEVER contains a secret value. Channels: `zerokosh_reminders` (DEFAULT importance), `zerokosh_login_helper` (HIGH).

## 6.4 Services & receivers

- `ZerokoshAutofillService` — see Phase 8 TASK-601; runs in the app process, reaches the object graph via `application as ZerokoshApp`; locked vault → single auth-gated dataset "Unlock Zerokosh to fill" that launches `MainActivity` via IntentSender.
- `LoginHelperReceiver` — `exported="false"`, action `org.zerokosh.app.COPY_LOGIN_STEP`, copies the step value via `ClipboardHelper.copySensitive` (30 s clear).
- No foreground services, no boot receivers, no exported components beyond the two above + launcher activity.
