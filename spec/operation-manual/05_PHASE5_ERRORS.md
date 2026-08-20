# Phase 5 — Error Boundaries, Exceptions & Resilience

## 5.1 Domain error taxonomy (complete — there are no others)

| Layer | Type | Raised when | Consumed by |
|---|---|---|---|
| `:core` codec | `VaultFormatException(message)` | bad magic/version/lengths, unreadable header, body sha mismatch | `VaultOperations.unlock` → `UnlockResult.Corrupt`; repository `runCatching { decode }` |
| `:core` unlock | `UnlockResult.WrongCredential` | AEAD open of `wrap_mk`/`wrap_rk` fails, malformed recovery key | UI shows exactly "Wrong passphrase" (`scr_lock_wrong`) — NEVER a crypto string (§3.3) |
| `:core` unlock | `UnlockResult.Corrupt` | file damaged though credential OK | backup-restore path below |
| repository | `UnlockOutcome` enum | see mapping table 5.2 | LockScreen `handleOutcome` |
| store | `IllegalStateException` from `check`/`error` | tmp verify failed, SAF tree unavailable | bubbles to caller's coroutine; persist aborts BEFORE touching `vault.kosh` — the old file stays intact (this is the §4.4 guarantee; do not catch-and-continue) |
| breach | return value `-1` | any non-200, timeout, IOException | `msg_breach_error`; no retry |
| import | empty `ImportPreview(records=[])` | unparseable file | `scr_import_failed`; no retry |

## 5.2 `UnlockOutcome` → exact UI behavior (frozen in LockScreen)

| Outcome | UI |
|---|---|
| `SUCCESS` | root switch flips to MainScaffold (no toast) |
| `WRONG_CREDENTIAL` | passphrase mode: `scr_lock_wrong` under field · recovery mode: `scr_lock_recovery_invalid`; cooldown label refreshes |
| `DAMAGED_RESTORED` | banner `msg_file_damaged` ("File damaged — restored from backup", §11.2), stay on lock screen |
| `DAMAGED` | root switch → `DamagedScreen` (msg_file_damaged, error color, centered) |
| `COOLDOWN` | `scr_lock_cooldown` "%1$d seconds", input disabled until 0 |

## 5.3 Unlock throttling algorithm (frozen; §5.1 S13 — restated so you never re-derive it)

```
on failure: failedAttempts += 1 (persisted)
            if failedAttempts >= 5:
                cooldownUntilMs = now + cooldownSeconds*1000
                cooldownSeconds = min(cooldownSeconds*2, 1800)   # 30→60→120…→1800 cap
on success: failedAttempts = 0; cooldownSeconds = 30
NEVER wipe data on failures (§14). Cooldown survives process death (Prefs).
```

## 5.4 Retry rules (exhaustive)

| Operation | Retry policy |
|---|---|
| Vault write (`persist`) | none — atomic write either lands or throws with old file intact; user retries by saving again |
| Breach check | none (single shot, 8 s timeouts) |
| `ReminderWorker` | returns `Result.success()` always (missed day is re-covered next day); WorkManager default backoff untouched |
| `ClipboardClearWorker` | none; main-Handler path is primary, worker is the fallback |
| Biometric prompt | user-driven; `KeyPermanentlyInvalidatedException` → silently `disable()` + one-line hint `scr_lock_use_passphrase_once` (§6.5) — never an error dialog |
| CameraX bind / QR decode | per-frame `try/catch` continue; no dialog |

Exponential-backoff HTTP retry frameworks: NOT APPLICABLE — there is no API client. Do not introduce one.

## 5.5 Catch discipline

- Allowed broad catches exist ONLY at: QR frame decode, SAF provider quirks (`renameDocument` fallback), asset `app_map.json` absence, biometric cipher init, attachment open, PDF write, breach socket. Each already exists with a narrow purpose comment. New code may not add `catch (e: Exception)` unless the task's template contains it.
- Never swallow `VaultFormatException` into success paths; never convert `WrongCredential` into `Corrupt` or vice-versa.
- Coroutine failures in UI `scope.launch` blocks around repository calls: let them crash in debug; where a task requires user-facing failure text it specifies the exact string resource.

## 5.6 Failure copy (tone §10.6: plain, warm, no jargon — never say "exception", "crypto", "AEAD")

All failure strings live in `strings.xml` and are enumerated in the Phase 8 tasks that introduce them (`msg_file_damaged`, `scr_lock_wrong`, `scr_lock_cooldown`, `scr_lock_recovery_invalid`, `scr_edit_invalid`, `scr_edit_luhn_warning`, `msg_attachment_too_big`, `scr_import_failed`, `msg_breach_error`, `msg_sync_folder_error`).
