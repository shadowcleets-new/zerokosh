# Queue 08A — INFRA · M3 · M4

Baseline is commit `6982e9c`. Every PATCH's OLD block is verbatim from that commit. Not found → STOP.

---

## GROUP INFRA — version catalog migration

### TASK-101 · Create version catalog
Target (NEW): `gradle/libs.versions.toml`
Action: write the file EXACTLY as given in `01_PHASE1_SCAFFOLDING_BUILD.md` §1.2.
VERIFY: `BUILD` (catalog unused yet, must still be green).
CHECKPOINT: none (bundle with 104).

### TASK-102 · Root build.gradle.kts → catalog plugin aliases
Target (PATCH): `build.gradle.kts`
OLD →
```kotlin
plugins {
    id("com.android.application") version "8.13.2" apply false
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("org.jetbrains.kotlin.jvm") version "2.2.10" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.10" apply false
}
```
NEW →
```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose) apply false
}
```
VERIFY: `BUILD`.

### TASK-103 · core/build.gradle.kts → catalog
Target (PATCH): `core/build.gradle.kts`
OLD → `    id("org.jetbrains.kotlin.jvm")` and the next line `    id("org.jetbrains.kotlin.plugin.serialization")`
NEW →
```kotlin
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
```
Then PATCH the dependencies block:
OLD →
```kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    // Test-only (excluded from the R0.7 budget — see DECISIONS.md D-001):
    // desktop libsodium binding to run the §3.6 conformance vectors on the JVM.
    testImplementation(kotlin("test-junit"))
    testImplementation("com.goterl:lazysodium-java:5.1.4")
    testImplementation("net.java.dev.jna:jna:5.14.0")
    testRuntimeOnly("org.slf4j:slf4j-nop:2.0.13")
```
NEW →
```kotlin
    implementation(libs.serialization.json)

    // Test-only (excluded from the R0.7 budget — see DECISIONS.md D-001):
    // desktop libsodium binding to run the §3.6 conformance vectors on the JVM.
    testImplementation(kotlin("test-junit"))
    testImplementation(libs.lazysodium.java)
    testImplementation(libs.jna)
    testRuntimeOnly(libs.slf4j.nop)
```
VERIFY: `CORETEST` (must stay 42 tests green).

### TASK-104 · app/build.gradle.kts → catalog
Target (PATCH): `app/build.gradle.kts`
Convert the `plugins { }` ids to `alias(libs.plugins.*)` (android.application, kotlin.android, kotlin.compose, kotlin.serialization) and every `implementation("group:name:ver")` in the dependencies block to its catalog alias from §1.2 (`libs.lazysodium.android`, `libs.jna`, `libs.compose.bom`, `libs.compose.ui`, `libs.compose.material3`, `libs.compose.icons.extended`, `libs.activity.compose`, `libs.navigation.compose`, `libs.biometric`, `libs.security.crypto`, `libs.documentfile`, `libs.serialization.json`, `libs.work.runtime`, `libs.camera.camera2`, `libs.camera.lifecycle`, `libs.camera.view`, `libs.zxing.core`). KEEP the lazysodium-android `exclude(group="net.java.dev.jna", module="jna")` block and the `@aar` classifier on jna: `implementation(libs.jna) { artifact { type = "aar" } }`. Do not touch `android { }`.
VERIFY: `BUILD`.
CHECKPOINT (INFRA): commit "INFRA: migrate to Gradle version catalog · Gates: BUILD+CORETEST green".

---

## GROUP M3 — sync folder, lifecycle refresh, quick-unlock/passphrase coupling

### TASK-301 · VaultRepository: expose store swap
Target (PATCH): `app/src/main/java/org/bharatvault/app/data/VaultRepository.kt`
OLD →
```kotlin
    /** Applies a §3.4 biometric quick-unlock result obtained via BiometricPrompt. */
```
NEW →
```kotlin
    /** §5.6: swap the vault store (app-private ↔ sync folder). Copies the current
     *  vault into the new store if the new store has none yet, so no data is lost. */
    suspend fun switchStore(newStore: VaultStore) = ioMutex.withLock {
        withContext(Dispatchers.Default) {
            val current = store.read()
            if (current != null && !newStore.exists()) {
                newStore.writeAtomic(current) { candidate ->
                    runCatching { VaultFileCodec.decode(candidate) }.isSuccess
                }
            }
            store = newStore
            backupDoneThisSession = false
        }
    }

    /** Applies a §3.4 biometric quick-unlock result obtained via BiometricPrompt. */
```
VERIFY: `BUILD`.

### TASK-302 · Settings: real sync-folder chooser (replaces placeholder Row)
Target (PATCH): `app/src/main/java/org/bharatvault/app/ui/settings/SettingsScreen.kt`
OLD (verbatim placeholder) →
```kotlin
        // §5.6 sync folder + §5.11 import/export land with M3/M5
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(stringResource(R.string.scr_settings_sync_folder), modifier = Modifier.weight(1f))
            Text(
                stringResource(R.string.scr_settings_sync_not_set),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
            )
        }
```
NEW →
```kotlin
        // §5.6 sync folder chooser (ACTION_OPEN_DOCUMENT_TREE + persisted permission)
        val hasSyncFolder = app.prefs.syncFolderUri.isNotBlank()
        val treeLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.OpenDocumentTree(),
        ) { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION,
                )
                app.prefs.syncFolderUri = uri.toString()
                val store = org.bharatvault.app.data.SafVaultStore(context, uri)
                scope.launch { app.repository.switchStore(store) }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(stringResource(R.string.scr_settings_sync_folder))
                Text(
                    if (hasSyncFolder) stringResource(R.string.scr_settings_sync_set)
                    else stringResource(R.string.scr_settings_sync_not_set),
                    style = MaterialTheme.typography.labelSmall,
                    color = if (hasSyncFolder) MaterialTheme.colorScheme.onSurfaceVariant
                    else MaterialTheme.colorScheme.error,
                )
            }
            TextButton(onClick = { treeLauncher.launch(null) }) {
                Text(stringResource(if (hasSyncFolder) R.string.scr_settings_sync_change else R.string.scr_settings_sync_choose))
            }
        }
```
Add imports (top import block): `import androidx.activity.compose.rememberLauncherForActivityResult`, `import androidx.activity.result.contract.ActivityResultContracts`, `import androidx.compose.foundation.layout.Column`.
Strings (append under the About block anchor `<!-- About -->` — actually insert right before `<!-- Common -->` in `values/strings.xml`):
```xml
    <string name="scr_settings_sync_set">Backed up to your folder</string>
    <string name="scr_settings_sync_choose">Choose folder</string>
    <string name="scr_settings_sync_change">Change</string>
    <string name="msg_sync_folder_error">Could not open that folder. Pick another.</string>
```
VERIFY: `BUILD`. HUMAN-DEVICE: §11.3 step 15 (folder → vault.bvlt appears).

### TASK-303 · App: restore sync store on launch
Target (PATCH): `app/src/main/java/org/bharatvault/app/BharatVaultApp.kt`
OLD →
```kotlin
        repository = VaultRepository(AndroidCrypto(), LocalVaultStore(this), prefs)
    }
```
NEW →
```kotlin
        val store = SafVaultStore.fromUriString(this, prefs.syncFolderUri) ?: LocalVaultStore(this)
        repository = VaultRepository(AndroidCrypto(), store, prefs)
        ReminderWorker.schedule(this)
    }
```
Add imports: `import org.bharatvault.app.data.SafVaultStore`, `import org.bharatvault.app.reminders.ReminderWorker`.
VERIFY: `BUILD`.

### TASK-304 · App: merge external edits on foreground (§4.5.1)
Target (PATCH): `app/src/main/java/org/bharatvault/app/MainActivity.kt`
OLD →
```kotlin
        backgroundedAtMs = 0
    }
```
NEW →
```kotlin
        backgroundedAtMs = 0
        if (app.repository.state.value == VaultState.Unlocked) {
            lifecycleScope.launch { app.repository.refreshFromDisk() }
        }
    }
```
Add imports: `import androidx.lifecycle.lifecycleScope`, `import kotlinx.coroutines.launch`.
VERIFY: `BUILD`. HUMAN-DEVICE: §11.3 steps 16–17 (device-B merge, conflict copy).

### TASK-305 · Passphrase change must disable quick-unlock (stale MasterKey, §7.1)
Target (PATCH): `app/src/main/java/org/bharatvault/app/ui/settings/SettingsScreen.kt`, inside `ChangePassphraseDialog` success branch.
OLD →
```kotlin
                        if (ok) {
                            Toast.makeText(context, doneMsg, Toast.LENGTH_SHORT).show()
                            onClose()
                        } else {
```
NEW →
```kotlin
                        if (ok) {
                            QuickUnlockManager.disable(context, app) // old MasterKey no longer opens the vault
                            Toast.makeText(context, doneMsg, Toast.LENGTH_SHORT).show()
                            onClose()
                        } else {
```
Add import: `import org.bharatvault.app.quickunlock.QuickUnlockManager`.
VERIFY: `BUILD`.

### TASK-306 · Export (encrypted .bvlt copy + red-warning plain CSV) — §5.11
Target (NEW): `app/src/main/java/org/bharatvault/app/ui/settings/ExportDialog.kt`
Complete code: a `@Composable fun ExportDialog(app, onClose)` with two buttons.
- "Export encrypted backup" → `CreateDocument("application/octet-stream")` launcher naming `bharatvault-backup.bvlt`; on uri → `context.contentResolver.openOutputStream(uri)?.use { it.write(app.repository.store.read() ?: ByteArray(0)) }`.
- "Export as plain CSV" → guarded by a two-step red confirm (`AlertDialog`, title `scr_export_csv_warn_title`, body `scr_export_csv_warn_body`, confirm button colored `MaterialTheme.colorScheme.error`) → `CreateDocument("text/csv")` naming `bharatvault-plain.csv`; writes header `name,url,username,password,note` then one line per `login` record built from fields website/username/password/notes, each field passed through a local `csvEscape(s) = if (s.contains(',')||s.contains('"')||s.contains('\n')) "\"" + s.replace("\"","\"\"") + "\"" else s`.
Wire it: PATCH SettingsScreen to add `var showExport by remember { mutableStateOf(false) }`, a `TextButton(onClick = { showExport = true }) { Text(stringResource(R.string.scr_settings_export)) }` beneath the import row area, and `if (showExport) ExportDialog(app) { showExport = false }`.
Strings:
```xml
    <string name="scr_export_csv_warn_title">Export without protection?</string>
    <string name="scr_export_csv_warn_body">A plain CSV shows every password in readable text to anyone who opens the file. Only do this to move into another manager, then delete the file.</string>
    <string name="scr_export_encrypted">Export encrypted backup</string>
    <string name="scr_export_csv">Export as plain CSV</string>
    <string name="scr_export_done">Backup saved</string>
```
VERIFY: `BUILD`.
CHECKPOINT (M3): commit per §8.3, milestone "M3", pending-device steps 15–22.

---

## GROUP M4 — attachments, custom fields, reminders UI, IFSC lookup

### TASK-401 · Attachment FILE field editor (≤ 2 MB, §2.1)
Target (PATCH): `app/src/main/java/org/bharatvault/app/ui/record/RecordEditScreen.kt`
Problem it solves: `FieldType.FILE -> { }` is currently a no-op.
Approach (DECISIONS D-007 — record it): attachments are stored in `VaultBody.attachments` keyed by a generated uuid; the field value holds that uuid. Since `values` is a flat `Map<String,String>` and the repository owns `attachments`, the editor picks bytes, but the actual attachment insertion happens in the repository. Add to `VaultRepository`:
```kotlin
    suspend fun putAttachment(name: String, mime: String, bytes: ByteArray): String? {
        if (bytes.size > 2 * 1024 * 1024) return null           // §2.1 ≤ 2 MB
        val current = _body.value ?: return null
        val uuid = java.util.UUID.randomUUID().toString()
        val b64 = android.util.Base64.encodeToString(bytes, android.util.Base64.NO_WRAP)
        persist(current.copy(attachments = current.attachments + (uuid to org.bharatvault.core.model.Attachment(name, mime, b64))))
        return uuid
    }
```
Then replace `FieldType.FILE -> { /* attachments arrive with M4 templates */ }` with a composable `FileField(app, value, onValueChange, label, modifier)` that shows the attachment name (looked up from `app.repository.body` by uuid) or a "Attach file" button; button → `GetContent()` launcher → read bytes via `contentResolver.openInputStream` → `scope.launch { val id = app.repository.putAttachment(name, mime, bytes); if (id != null) onValueChange(id) else /* toast msg_attachment_too_big */ }`.
Strings: `<string name="scr_edit_attach">Attach file</string>`, `<string name="msg_attachment_too_big">File is too big — keep attachments under 2 MB</string>`.
VERIFY: `BUILD`.

### TASK-402 · Attachment view in S9 (in-memory only, never to disk — §3.5)
Target (PATCH): `RecordDetailScreen.kt` `when (field.type)` dispatch.
OLD → `                    else -> FieldRow(app, record.template_id, field, value)`
NEW →
```kotlin
                    FieldType.FILE -> AttachmentRow(app, record.template_id, field, value)
                    else -> FieldRow(app, record.template_id, field, value)
```
Add `AttachmentRow` composable: resolves `app.repository.body.value?.attachments?.get(uuid)`; shows name + a "View" button that, for image mimes, decodes `Base64.decode(data)` → `BitmapFactory.decodeByteArray` into an in-memory `ImageBitmap` shown in a dialog (D-008: attachments NEVER written to disk/cache); non-image → shows name only with a note. No FileProvider, no temp file.
VERIFY: `BUILD`.

### TASK-403 · Reminders UI in S10 + POST_NOTIFICATIONS gate
Target (PATCH): `RecordEditScreen.kt`. After the fields loop, for template fields of `FieldType.DATE` add a per-date "Remind me 30 days before" `Switch` bound into a local `reminders: SnapshotStateList<Reminder>`. On save, pass `reminders = reminders.toList()` instead of `existing?.reminders ?: emptyList()`.
OLD → `                            reminders = existing?.reminders ?: emptyList(),`
NEW → `                            reminders = reminderState.toList(),`
Add near the other state: `val reminderState = remember { mutableStateListOf<org.bharatvault.core.model.Reminder>().apply { existing?.reminders?.let { addAll(it) } } }`.
On first reminder toggled on: call `NotificationPermission.ensure(activity)` (NEW helper, TASK-404-adjacent) — create `app/src/main/java/org/bharatvault/app/reminders/NotificationPermission.kt`:
```kotlin
package org.bharatvault.app.reminders
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
object NotificationPermission {
    fun ensure(activity: FragmentActivity): Boolean {
        if (Build.VERSION.SDK_INT < 33) return true
        val granted = ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        if (!granted) activity.requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 4033)
        return granted
    }
}
```
Strings: `<string name="scr_edit_remind">Remind me before this date</string>`.
VERIFY: `BUILD`. HUMAN-DEVICE: create a record with an expiry + reminder, confirm a notification the day it triggers (text has NO secret).

### TASK-404 · IFSC offline lookup (auto-fill bank_name + branch, §2.2/§5.10)
Target (NEW): `app/src/main/java/org/bharatvault/app/data/IfscLookup.kt`
```kotlin
package org.bharatvault.app.data
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.File
// §5.10/Appendix E: offline IFSC → bank/branch. App works fully if assets/ifsc.db is absent.
class IfscLookup private constructor(private val db: SQLiteDatabase?) {
    data class Result(val bank: String, val branch: String)
    fun lookup(code: String): Result? {
        val d = db ?: return null
        d.rawQuery("SELECT bank, branch FROM ifsc WHERE code = ? LIMIT 1", arrayOf(code.uppercase())).use { c ->
            return if (c.moveToFirst()) Result(c.getString(0), c.getString(1)) else null
        }
    }
    companion object {
        @Volatile private var instance: IfscLookup? = null
        fun get(context: Context): IfscLookup = instance ?: synchronized(this) {
            instance ?: build(context).also { instance = it }
        }
        private fun build(context: Context): IfscLookup {
            val out = File(context.cacheDir, "ifsc.db")   // read-only reference data, NOT a secret (D-003)
            val db = runCatching {
                if (!out.exists()) context.assets.open("ifsc.db").use { input -> out.outputStream().use { input.copyTo(it) } }
                SQLiteDatabase.openDatabase(out.path, null, SQLiteDatabase.OPEN_READONLY)
            }.getOrNull()
            return IfscLookup(db)
        }
    }
}
```
Then PATCH `RecordEditScreen.FieldEditor` `FieldType.IFSC` branch: on `onValueChange`, when the value matches `FieldValidation.IFSC_REGEX`, call `IfscLookup.get(context).lookup(value)` and if non-null and the `bank_name`/`branch` values are currently blank, set them via the `values` map. (Only bank_account has both fields; guard with `values.containsKey`.)
Note: `assets/ifsc.db` is NOT committed (D-003); lookup silently returns null without it. Build it with `spec/build_ifsc.py` at release time.
VERIFY: `BUILD`.

### TASK-405 · Custom fields editor (§2.1 custom_fields)
Target (PATCH): `RecordEditScreen.kt`. After template fields, render existing `custom_fields` (label + value editors, type from a small dropdown TEXT/SECRET/DATE/NUMBER) and an "Add field" button appending to a local `mutableStateListOf<CustomField>()` seeded from `existing?.custom_fields`. On save pass `custom_fields = customState.toList()`.
OLD → `                            custom_fields = existing?.custom_fields ?: emptyList(),`
NEW → `                            custom_fields = customState.toList(),`
Strings: `<string name="scr_edit_add_field">Add your own field</string>`, `<string name="scr_edit_field_label">Field name</string>`.
VERIFY: `BUILD`.
CHECKPOINT (M4): commit per §8.3, milestone "M4". Every template creates/saves/reopens; generator bank-rule tests already green in `:core`.
