# Queue 08C — M7 · M8

---

## GROUP M7 — Hindi localization + accessibility

### TASK-701 · Generate Hindi template-field labels
Target (NEW): `spec/gen_strings_hi.py` + output `app/src/main/res/values-hi/strings_templates.xml`.
Write `gen_strings_hi.py` as a copy of `spec/gen_strings.py` whose `SPECIAL`/`humanize` maps produce Hindi labels (glossary from TASK-703). It reads `spec/templates.json`, writes `values-hi/strings_templates.xml` with the SAME resource names as `values/strings_templates.xml` (only the values differ). Run it. Every `name=` must match the English file 1:1 (§12: keys identical across locales).
VERIFY: `BUILD` (missing-translation lint is not fatal but every `tpl_*` name must resolve).

### TASK-702 · Hindi screen strings
Target (NEW): `app/src/main/res/values-hi/strings.xml`
Contains a Hindi `<string>` for EVERY name present in `values/strings.xml` (all `scr_*`, `msg_*`, `cd_*`, `app_name` stays "Zerokosh"). Tone §10.6: warm, plain, natively written (NOT machine-translated word salad); use the glossary (passphrase = "मुख्य पासवर्ड", Recovery Key = "रिकवरी कुंजी", vault = "तिजोरी"). Placeholders (`%1$s`, `%1$d`) MUST be preserved exactly. Do NOT concatenate fragments (§12).
VERIFY: `BUILD`. Then confirm parity:
```powershell
$en = Select-String -Path app\src\main\res\values\strings.xml -Pattern 'name="([^"]+)"' | ForEach-Object { $_.Matches.Groups[1].Value } | Sort-Object
$hi = Select-String -Path app\src\main\res\values-hi\strings.xml -Pattern 'name="([^"]+)"' | ForEach-Object { $_.Matches.Groups[1].Value } | Sort-Object
Compare-Object $en $hi   # expect NO output (100% parity, §0.7 M7 gate)
```

### TASK-703 · Hindi glossary (repo governance §15)
Target (NEW): `l10n/glossary_hi.md` — table of English term → agreed Hindi term (passphrase, PIN, UPI, bank, card, recovery key, vault, sync folder, biometric, breach, etc.), so translators stay consistent. Referenced by CONTRIBUTING.md.
VERIFY: none (docs).

### TASK-704 · Accessibility pass (§10.7)
Target (PATCH, multiple frozen screens — additive only):
- Ensure every icon-only `IconButton` has a non-null `contentDescription` (audit: grep `contentDescription = null` in `ui/` and give each a string; decorative-only icons inside a labeled row may stay null — document which).
- Masked values already announce "hidden" via `semantics { contentDescription = ... }` in `FieldRow` — verify TOTP and card rows do too; add where missing.
- Confirm no color-only meaning: the Luhn warning and cooldown also carry text (they do). Strength meter has a text label (it does).
- Do NOT alter layouts; a11y is additive semantics + contentDescription only.
VERIFY: `BUILD`. HUMAN-DEVICE: TalkBack traverses S3/S6/S9/S10/S13 with every control announced; dynamic type at 200% shows no label truncation.
CHECKPOINT (M7): commit per §8.3, milestone "M7". Gate: Compare-Object parity empty; TalkBack walk (pending-device).

---

## GROUP M8 — hardening, breach toggle, packaging, release

### TASK-801 · INTERNET permission + breach toggle (§5.9)
Target (PATCH): `AndroidManifest.xml`
OLD → `    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />`
NEW →
```xml
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <!-- INTERNET exists ONLY for the opt-in §5.9 breach check; the app is fully functional without it -->
    <uses-permission android:name="android.permission.INTERNET" />
```
Then add the breach-check toggle to Settings (default OFF): a `Switch` bound to a new `prefs.breachCheckEnabled` (add the Prefs accessor mirroring `allowScreenshots`), with subtitle `scr_settings_breach_desc`. When ON, S9 SECRET-field detail rows may show a "Check if leaked" action that calls `BreachCheck.checkPassword(value)` on `Dispatchers.IO` and shows count or `msg_breach_error`. The toggle gates ALL calls — `BreachCheck` is NEVER invoked when off (§5.9).
Strings:
```xml
    <string name="scr_settings_breach">Check for leaked passwords</string>
    <string name="scr_settings_breach_desc">When on, Zerokosh can check a password against known breaches by sending only the first 5 characters of its fingerprint — never the password itself.</string>
    <string name="scr_breach_safe">Not found in known breaches</string>
    <string name="scr_breach_found">Seen in %1$d breaches — change it</string>
    <string name="msg_breach_error">Couldn\'t check right now. Try again later.</string>
```
VERIFY: `BUILD`.

### TASK-802 · Network security config
Target (NEW): `app/src/main/res/xml/network_security_config.xml` — EXACT content from `07_PHASE7_SECURITY.md` §7.3.
PATCH manifest `<application>` open tag: add `android:networkSecurityConfig="@xml/network_security_config"` (per §6.1 final manifest).
VERIFY: `BUILD`.

### TASK-803 · IFSC db build script confirmation
`spec/build_ifsc.py` is committed (M0). Action: verify present; do NOT run it (needs the Razorpay CSV, a release-time step). `assets/ifsc.db` stays uncommitted (D-003); the app already handles its absence (TASK-404).
VERIFY: `Test-Path spec\build_ifsc.py` → True.

### TASK-804 · Release signing config (keystore generated OFFLINE by human — R0.5)
Target (PATCH): `app/build.gradle.kts` — add the `signingConfigs { create("release") { … } }` block from `01_PHASE1_SCAFFOLDING_BUILD.md` §1.4 and set `buildTypes.release.signingConfig = signingConfigs.getByName("release")` GUARDED so an absent `keystore.properties` leaves it null (unsigned CI build still compiles). Confirm `.gitignore` already ignores `*.jks`/`*.keystore`/`keystore.properties`.
VERIFY: `RELEASE` (produces an unsigned or debug-signed `app-release-unsigned.apk` when no keystore) — must reach `BUILD SUCCESSFUL`. STOP before generating a real keystore (human step).

### TASK-805 · §11.6 security checklist automation
Target (NEW): `spec/security_checklist.ps1` — runs the greps from `07_PHASE7_SECURITY.md` §7.5 (banned deps, secret-shaped logging), prints PASS/FAIL per item. Manual items (proxy zero-request, label = "no data collected") are printed as HUMAN-DEVICE reminders.
VERIFY: run it → all automatable items PASS.

### TASK-806 · Run the full security gate (§11.6)
Action: run `spec/security_checklist.ps1`; run `CORETEST`; run `RELEASE`. All green. List the remaining HUMAN-DEVICE gates (proxy zero-request behind mitmproxy with breach OFF; APK ≤ 15 MB check via `(Get-Item app\build\outputs\apk\release\*.apk).Length`; cold start ≤ 2 s on a 2 GB device).
VERIFY: all three commands succeed.

### TASK-807 · Reproducible-build & F-Droid readiness
Confirm: `versionCode` fixed (1), `dependenciesInfo.includeInApk=false`/`includeInBundle=false` present, no build timestamps, all deps FLOSS (Lazysodium/JNA ✅). Produce `README` download-channel section already exists (M0). Publish signing cert SHA-256 is a post-keystore human step — list it, do not fake it.
VERIFY: `BUILD` green; grep confirms flags present.

### TASK-808 · Final checkpoint (STOP for human review — R0.5)
Do NOT publish to any store. Assemble the release artifact procedure and STOP.
CHECKPOINT (M8): commit per §8.3, milestone "M8".
Commit body MUST include: security-checklist output, `:core:test` + `:app:testDebugUnitTest` results, release APK size, and the explicit line:
`AWAITING HUMAN: keystore generation, store publishing, proxy zero-request test, on-device §11.3/§11.5 walk, cross-platform §11.2 matrix (needs iOS/Windows cores).`
Then STOP. The Android deliverable is code-complete and gate-verified up to every automatable check; the remaining gates are physical-device and human-authority actions the Executor must not perform.

---

## Definition of Done for the Android app (Master Spec Appendix F, Android slice)

☐ INFRA…M8 checkpoints committed with passing automatable gates
☐ §11.6 automatable items ✅ (security_checklist.ps1)
☐ en+hi 100% (Compare-Object empty)
☐ store privacy label plan = "no data collected"
☐ README lists official channels + (post-keystore) signing fingerprint
☐ v0.x tagged; v1.0.0 tag is a human decision after device walk
Pending non-Executor: keystore, store, device walks, §11.2 cross-platform matrix (iOS/Windows cores are separate deliverables per ADR-001).
