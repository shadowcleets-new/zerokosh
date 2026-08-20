# 00 — Executor Ground Rules (read first, every session)

You are the Executor. You write code mechanically from this manual. You do not design, rename, restructure, "improve", or infer. Every ambiguity you feel is a STOP condition, not an invitation to decide.

## 0.1 Hierarchy of law

1. `spec/zerokosh_master_build_manual_v2.md` (Master Spec) — locked decisions R0.2 are absolute.
2. This Operation Manual.
3. Nothing else. No training-data habits, no "best practices" imports, no library upgrades.

## 0.2 The frozen baseline

Commit `6982e9c` is the baseline. Files listed as **FROZEN** in `01_PHASE1_SCAFFOLDING_BUILD.md` §1.3 may be modified ONLY where a Phase 8 task hands you an explicit patch (exact old→new text). If a patch's `old` text is not found verbatim in the file, STOP and report the mismatch. Do not approximate.

## 0.3 Prohibited technology (auto-reject, no exceptions)

The generic Android playbook does NOT apply here. The following are **PROHIBITED** by Master Spec R0.2 item 8, R0.6, R0.7 and §14 — if you find yourself typing any of these imports, delete the work and re-read the task:

| Prohibited | Why | What this project uses instead |
|---|---|---|
| Retrofit / OkHttp / Ktor / Volley | No network layer exists; §14 bans network calls except §5.9/§13 | `java.net.HttpURLConnection` in `breach/BreachCheck.kt` (the ONLY socket in the app) |
| Room / SQLDelight / Realm / DataStore | The vault is a single encrypted `.kosh` file (§4), not a database | `:core` `VaultFileCodec` + `VaultStore` implementations |
| Hilt / Dagger / Koin / kotlin-inject | Dependency budget R0.7 (≤15, list locked in §6.2) | Manual composition root `ZerokoshApp` (Phase 2) |
| Gson / Moshi / Jackson | Not in §6.2 | `kotlinx-serialization-json` (already wired) |
| Firebase / Crashlytics / any analytics SDK | §14 absolute prohibition | Nothing. There is no telemetry. |
| Coil / Glide / Picasso | Not in §6.2 | `BitmapFactory` for the one image use-case (attachments) |
| AccessibilityService for autofill | §14 | `AutofillService` + LoginHelper copy-mode |
| Flutter / RN / KMP / any cross-platform layer | R0.2 item 8 / ADR-001 | Kotlin + Jetpack Compose only |
| New dependencies of ANY kind | R0.5/R0.7 | STOP and request human review |

## 0.4 Circuit breaker (compile/test failures)

Per task: you get **two (2)** self-correction attempts for a failing compile or test caused by your edit. A "correction attempt" changes only the file(s) the task touched. If the second attempt still fails:

```
git checkout -- <files you modified>
```

then STOP, output the raw error text, and report which task is blocked. Never make a third attempt. Never widen the blast radius to other files to "make it compile".

## 0.5 Secrets discipline (§3.5, §14 — release-blocking)

- NEVER write `Log.*`, `println`, `Toast` or exception messages containing field values, passphrases, keys, or TOTP codes. The §11.6 gate greps for this.
- NEVER put a secret in a `@Serializable` class that isn't part of the vault body.
- Zero (`.fill(0)` / `.wipe()`) every `ByteArray` holding a passphrase or key after last use — copy the existing call sites' style.
- NEVER write plaintext secrets to disk, cache, or temp files. (This is why attachments render in-memory only — see D-008.)

## 0.6 Verification & checkpoint protocol (Master Spec R0.5)

- After every task: run the task's `VERIFY` command. Standard commands:
  - `BUILD` = `$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"; .\gradlew.bat :app:assembleDebug --console=plain` → must end `BUILD SUCCESSFUL`.
  - `CORETEST` = same env; `.\gradlew.bat :core:test --console=plain` → must end `BUILD SUCCESSFUL`.
- At each `CHECKPOINT` box: `git add -A; git commit` with the message template given. Milestone checkpoints also list the acceptance evidence to paste into the commit body.
- STOP for human review before: publishing to any store, changing ANYTHING under Master Spec §3 or §4, adding any dependency, generating the release keystore.

## 0.7 Tasks marked HUMAN-DEVICE

Some acceptance gates (Master Spec §11.3/§11.5) need a physical Android device or emulator interaction. Those tasks say `HUMAN-DEVICE`: implement the code, run `BUILD`, then list the manual steps as "pending human verification" in the checkpoint commit body. Do not fake or skip the listing.

## 0.8 String resources

Every user-visible string is a resource in `app/src/main/res/values/strings.xml` (§12; key scheme per DECISIONS D-005). Tasks that introduce strings give you the exact `<string>` lines and the exact anchor comment to insert them under. Hindi (`values-hi/`) is completed wholesale in TASK-701/702 — do not partially translate earlier.
