# Queue 08B — M5 · M6

The import ENGINE (`core/import/CsvImport.kt`) and the autofill SERVICE + LoginHelper are already implemented and FROZEN. M5/M6 are the UI + manifest wiring around them, plus tests.

---

## GROUP M5 — import UI, authenticator QR (already wired), import tests

### TASK-501 · Import screen (S14) — preview count before commit (§5.11)
Target (NEW): `app/src/main/java/org/bharatvault/app/ui/import/ImportScreen.kt`
Complete behavior:
- `@Composable fun ImportScreen(app, onDone)`.
- State: `preview: ImportPreview? = null`, `busy=false`.
- `OpenDocument(arrayOf("text/*", "text/csv", "text/comma-separated-values", "application/json"))` launcher → read text via `contentResolver.openInputStream(uri)?.bufferedReader()?.readText()` → `CsvImport.parseAuto(text, app.prefs.deviceId, System.currentTimeMillis())` → set `preview`.
- When `preview != null`: show "N items ready to import" (`scr_import_ready`, `%1$d` = `preview.records.size`) + source label + Import / Cancel.
- Import → `scope.launch { val (insert, update) = CsvImport.mergeWithExisting(preview.records, app.repository.body.value?.records ?: emptyList()); (insert + update).forEach { app.repository.upsertRecord(it) }; onDone() }`.
- Empty preview (`records.isEmpty()`) → `scr_import_failed`.
Wire into Settings: PATCH the import row placeholder to a `TextButton(onClick = { showImport = true })`; host `if (showImport) ImportScreen(app) { showImport = false }` (a full-screen dialog or nav route — use an `AlertDialog`-free full `Surface` overlay via `androidx.compose.ui.window.Dialog(onDismissRequest, DialogProperties(usePlatformDefaultWidth=false))`).
Strings:
```xml
    <string name="scr_import_title">Import passwords</string>
    <string name="scr_import_pick">Choose a file</string>
    <string name="scr_import_ready">%1$d items ready to import</string>
    <string name="scr_import_source">From: %1$s</string>
    <string name="scr_import_commit">Import</string>
    <string name="scr_import_failed">We couldn\'t read that file. Export a CSV or JSON from your other manager and try again.</string>
```
VERIFY: `BUILD`.

### TASK-502 · Authenticator QR — confirm wiring (NO code change expected)
`AuthenticatorScreen` + `QrScannerScreen` are frozen and functional (CameraX + ZXing, camera permission flow per §6.2). Action: run `BUILD`; if green, this task is a no-op confirmation. Do NOT refactor the scanner.
VERIFY: `BUILD`. HUMAN-DEVICE: §11.5 iOS row N/A; Android — scan a Google Authenticator `otpauth://` QR → live code appears in S11.

### TASK-503 · Import round-trip test (Chrome 100 rows lossless — §0.7 M5 gate)
Target (NEW): `core/src/test/kotlin/org/bharatvault/core/CsvImportTest.kt`
```kotlin
package org.bharatvault.core
import org.bharatvault.core.import.CsvImport
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class CsvImportTest {
    private val dev = "test-device"
    private val now = 1_751_875_200_000L

    @Test fun `chrome csv maps columns`() {
        val csv = "name,url,username,password,note\nSBI,https://retail.onlinesbi.sbi,rahul,S@m1,salary\n"
        val p = CsvImport.parseChromeCsv(csv, dev, now)
        assertEquals(1, p.records.size)
        val r = p.records[0]
        assertEquals("login", r.template_id)
        assertEquals("SBI", r.title)
        assertEquals("https://retail.onlinesbi.sbi", r.fields["website"])
        assertEquals("rahul", r.fields["username"])
        assertEquals("S@m1", r.fields["password"])
        assertEquals("salary", r.fields["notes"])
    }

    @Test fun `100 rows import losslessly`() {
        val sb = StringBuilder("name,url,username,password,note\n")
        repeat(100) { i -> sb.append("Site$i,https://site$i.example,user$i,Pass@$i,note$i\n") }
        val p = CsvImport.parseChromeCsv(sb.toString(), dev, now)
        assertEquals(100, p.records.size)
        p.records.forEachIndexed { i, r ->
            assertEquals("Site$i", r.title)
            assertEquals("Pass@$i", r.fields["password"])
        }
    }

    @Test fun `quoted fields with commas and newlines survive`() {
        val csv = "name,url,username,password,note\n\"A, B\",https://x.example,u,\"p\"\"q\",\"line1\nline2\"\n"
        val p = CsvImport.parseChromeCsv(csv, dev, now)
        assertEquals("A, B", p.records[0].title)
        assertEquals("p\"q", p.records[0].fields["password"])
        assertTrue(p.records[0].fields["notes"]!!.contains("line2"))
    }

    @Test fun `duplicate url+username updates instead of duplicating`() {
        val existing = CsvImport.parseChromeCsv("name,url,username,password,note\nX,https://x.example,u,old,\n", dev, now).records
        val incoming = CsvImport.parseChromeCsv("name,url,username,password,note\nX,https://x.example,u,new,\n", dev, now).records
        val (insert, update) = CsvImport.mergeWithExisting(incoming, existing)
        assertEquals(0, insert.size)
        assertEquals(1, update.size)
        assertEquals("new", update[0].fields["password"])
    }
}
```
VERIFY: `CORETEST` (now 46 tests).

### TASK-504 · Commit M5
CHECKPOINT (M5): commit per §8.3, milestone "M5". Gates: CORETEST (RFC6238 vectors already pass; CSV 100-row test green). Pending-device: §11.5 QR scan.

---

## GROUP M6 — autofill manifest wiring + resources + tests

### TASK-601 · Register AutofillService in manifest
Target (PATCH): `app/src/main/AndroidManifest.xml`
OLD →
```xml
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
```
NEW →
```xml
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <service
            android:name=".autofill.BharatVaultAutofillService"
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

        <receiver
            android:name=".autofill.LoginHelperReceiver"
            android:exported="false" />
    </application>
```
VERIFY: `BUILD`.

### TASK-602 · Autofill service xml + label string
Target (NEW): `app/src/main/res/xml/autofill_service.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<autofill-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:settingsActivity="org.bharatvault.app.MainActivity" />
```
Strings: `<string name="scr_autofill_service_label">BharatVault</string>`.
VERIFY: `BUILD`.

### TASK-603 · Login-helper entry point in S9 (copy-mode, §6.7)
Target (PATCH): `RecordDetailScreen.kt` TopBar actions. Add, before the delete IconButton, a "Login helper" action for `bank_account`/`login`/`demat` templates:
OLD →
```kotlin
                    IconButton(onClick = { confirmDelete = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.scr_detail_delete))
                    }
```
NEW →
```kotlin
                    if (record.template_id in setOf("bank_account", "login", "demat")) {
                        IconButton(onClick = {
                            if (NotificationPermission.ensure(context as androidx.fragment.app.FragmentActivity)) {
                                LoginHelper.show(context, record)
                            }
                        }) {
                            Icon(Icons.Outlined.ContentPaste, contentDescription = stringResource(R.string.scr_login_helper_channel))
                        }
                    }
                    IconButton(onClick = { confirmDelete = true }) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.scr_detail_delete))
                    }
```
Add imports: `import org.bharatvault.app.autofill.LoginHelper`, `import org.bharatvault.app.reminders.NotificationPermission`, `import androidx.compose.material.icons.outlined.ContentPaste`.
VERIFY: `BUILD`.

### TASK-604 · Autofill matcher test
Target (NEW): `app/src/test/kotlin/org/bharatvault/app/AutofillMatchTest.kt` (unit test on the pure matching helpers). Since `matchRecords`/`domainHost` are `private`, extract them: PATCH `BharatVaultAutofillService` to move `domainHost` and `extractDomainHint` into a top-level `internal object AutofillMatch { fun domainHost(url); fun extractDomainHint(pkg); fun matches(record, packageName, webDomain, appMap): Boolean }`, and have the service delegate. Then test:
```kotlin
package org.bharatvault.app
import org.bharatvault.app.autofill.AutofillMatch
import kotlin.test.Test
import kotlin.test.assertEquals
class AutofillMatchTest {
    @Test fun `domain host strips scheme and www`() {
        assertEquals("zomato.com", AutofillMatch.domainHost("https://www.zomato.com/order"))
        assertEquals("site.example", AutofillMatch.domainHost("site.example"))
    }
    @Test fun `package hint extracts second segment`() {
        assertEquals("zomato", AutofillMatch.extractDomainHint("com.application.zomato"))
    }
}
```
Add `testImplementation(kotlin("test-junit"))` to `app/build.gradle.kts` dependencies (test-only, R0.7-exempt). Add a JVM unit-test source set is default for AGP (`src/test`).
VERIFY: `BUILD` then `.\gradlew.bat :app:testDebugUnitTest --console=plain`.

### TASK-605 · Commit M6
CHECKPOINT (M6): commit per §8.3, milestone "M6". Pending-device: §11.5 — autofill fills a WebView login + native test app; copy-mode notification sequence on a MIUI/HyperOS device; save-prompt captures new creds.
