# Phase 4 — Rigid State Architecture & UI Manifest

## 4.1 The frozen architecture (do not "upgrade" to MVI/ViewModels)

Unidirectional flow, two tiers:

1. **App state** — `VaultRepository` exposes exactly two StateFlows: `state: StateFlow<VaultState>` (`NoVault | Locked | Unlocked | Damaged`) and `body: StateFlow<VaultBody?>`. All mutations go through repository suspend functions. Screens `collectAsState()` and never cache vault data elsewhere.
2. **Screen state** — plain `remember { mutableStateOf(...) }` vars local to each composable, enumerated below. No ViewModels, no SavedStateHandles (except `rememberSaveable` where listed): a locked vault must NOT leak into saved instance state.

Root switch (`ZerokoshNav`): `NoVault → OnboardingFlow`, `Locked → LockScreen` (+ `RevealAuth.reset()`), `Unlocked → MainScaffold`, `Damaged → DamagedScreen`. One-time events (toasts) fire at the interaction site via `Toast.makeText` — there is no event bus; do not add Channels/SharedFlow.

## 4.2 Per-screen contract tables (FROZEN screens)

Notation: state var `name: Type = initial` · events = user interactions and their exact handler effect · effects = `LaunchedEffect` keys.

**S1 `LanguageScreen(app, onDone)`** — state: none. Events: tap language card → `prefs.languageTag = tag; activity.recreate(); onDone()`; Continue → `onDone()`. Grid `GridCells.Fixed(2)`, cards padding 6dp, text `titleLarge` centered, vertical padding 24dp.

**S2 `TrustScreen(onDone)`** — state: `pager = rememberPagerState { 3 }`. 3 cards (icon 96dp, `displaySmall` title, `bodyLarge` body, both centered). Continue button 52dp height, fillMaxWidth.

**S3 `CreatePassphraseScreen(app, onboarding, onDone)`** — state: `pass=""`, `confirm=""`, `pinMode=false`, `busy=false`; derived `pinAllowed = QuickUnlockManager.hardwareBackedBiometricsAvailable(app)`; `valid` = pinMode ? (6 digits && match) : (len ≥ 10 && match). Events: PIN toggle (only if pinAllowed) clears both fields; primary button → `busy=true; repository.createVault(pass.toByteArray())` → stash passphrase+recoveryKey in `OnboardingState` → `onDone()`. Strength meter = `passphraseScore(p): Int 0..3` (frozen fn) → `LinearProgressIndicator(progress = (score+1)/4f)` colored error/secondary/primary/tertiary.

**S4 `RecoveryKitScreen(app, onboarding, onDone)`** — state: `confirmed=false`, `pdfSaved=false`; launcher `CreateDocument("application/pdf")` → `RecoveryKitPdf.write(context, key, outStream)`. Continue enabled only when checkbox checked; on tap sets `onboarding.recoveryKey = null` (key never shown again, §5.1 S4).

**S5 `QuickUnlockScreen(app, onboarding, onDone)`** — state: none beyond `available`. "Turn on" → `QuickUnlockManager.enable(activity, app, onboarding.passphrase!!)` then `onboarding.wipe(); onDone()`. "Not now" → wipe + done. `prefs.onboardingDone = true` set by caller.

**S13 `LockScreen(app)`** — state: `passphrase=""`, `wrong=false`, `damagedRestored=false`, `busy=false`, `recoveryMode=false`, `recoveryInput=""`, `recoveryInvalid=false`, `cooldown=repository.cooldownRemainingSeconds()`, `biometricFellBack=false`. Effects: `LaunchedEffect(cooldown)` 1 s ticker while > 0; `LaunchedEffect(Unit)` auto-fires biometric when enrolled && cooldown==0. Events: Unlock → `unlockWithPassphrase/unlockWithRecoveryKey` → `handleOutcome` maps per Phase 5 table; fingerprint icon re-triggers biometric; mode toggle swaps passphrase/recovery input. Cooldown text = `scr_lock_cooldown` with seconds.

**S6/S7 `HomeScreen(app, onAdd, onOpen)`** — state: `query=""`. Data: filter via frozen `searchableText(record, catalog)` (indexes title/institution/tags + L/M TEXT-EMAIL-PHONE-URL-PICKER-IFSC fields; NEVER SECRET/PIN/H) and `matchesQuery` (token AND-match + Hinglish transliteration map bijli/bank/paisa/gas/pani/bima/card/phone/ghar). Groups: `groupBy institution.ifBlank { template label }`, sorted; rows sorted favorites-first. FAB → `onAdd()`. Row tap → `onOpen(uuid)`. Empty vault → 2 sample cards + 1 random safety-tip card (`msg_safety_tip_1..6`). List bottom spacer 88dp (FAB clearance).

**S8 `TemplateGalleryScreen(app, onPick)`** — quick-add `LazyRow` of `catalog.appPresets` (monogram 40dp, label `labelSmall`) → `onPick("app_profile", preset)`; grid `GridCells.Fixed(3)` of 12 templates (icon 32dp, label `labelMedium`) → `onPick(template.id, null)`.

**S9 `RecordDetailScreen(app, uuid, onEdit, onOpenRecord, onClose)`** — state: `confirmDelete=false`. TopBar actions: favorite toggle → `repository.toggleFavorite`; edit → `onEdit()`; delete → confirm dialog → `repository.deleteRecord(uuid); onClose()`. Rows dispatch: `TOTP → TotpRow` (1 s ticker, code split "123 456", `CircularProgressIndicator` ring 28dp + seconds label; tap = sensitive copy), `LINK → LinkRow` (navigates via `onOpenRecord`), else `FieldRow`.
`FieldRow` per-row state: `revealed=false`, `revealDeadline=0L` (effect: 10 s auto-re-mask, §2.3), `authAction: (()->Unit)?`. Gate `gated(action)`: H + outside 60 s grace → biometric if enrolled else `PassphraseAuthDialog`; success → `RevealAuth.markAuthenticated()`. Copy: L → `copyPlain`; M/H → `copySensitive` + toast `scr_detail_copied`. Anatomy per §10.4: `labelSmall` caption (+12dp shield icon when H) / value (`SecretTextStyle` mono for SECRET-PIN-CARDNUM-TOTP) / copy icon / eye icon; row vertical padding 10dp; tapping the value copies.

**S10 `RecordEditScreen(app, templateIdArg, editUuid, presetName, onDone)`** — state: `title` (rememberSaveable), `institution` (rememberSaveable), `values = mutableStateMapOf<String,String>()` seeded from record/preset, `titleMissing=false`, `showInvalid=false`. Save (TopBar text button): title blank → `titleMissing`; invalid fields (regex `v`, CARDNUM exempt — Luhn warns never blocks §5.10) → `showInvalid`; else build Record (empty fields dropped §2.1; institution fallback `deriveInstitution` from bank_name/broker/insurer/provider/operator/app_name/website-host) → `repository.upsertRecord` → `onDone()`.

**S11 `AuthenticatorScreen(app)`** — state: `showScanner=false`. Entries = every non-blank TOTP field across records. Row = `TotpLiveRow` (500 ms ticker; ring 40dp in 48dp box; copy icon + tap-to-copy). "Add via QR" → `QrScannerScreen(onCancel, onSecret)`; onSecret builds a `login` Record (title = issuer·label fallback "Authenticator", field `totp` = full otpauth URI or bare secret) → `upsertRecord` → toast `msg_saved`.

**S12 `SettingsScreen(app)`** — state: `showChangePass`, `showNewRecovery`, `quickUnlockOn`, `allowShots`, `enableQuickUnlockAsk` (all Boolean). Rows in order: auto-lock dropdown (0/1/5/15 → `prefs.autoLockMinutes`), quick-unlock switch (on→`EnableQuickUnlockDialog` passphrase→`QuickUnlockManager.enable`; off→`disable`), screenshots switch (+ warning subtitle; calls `MainActivity.applyScreenPrivacy()`), change-passphrase dialog (`repository.changePassphrase`; MUST also disable quick-unlock — TASK-305 patch), new-recovery-key dialog (passphrase → `rotateRecoveryKey` → show once), sync-folder section (TASK-302 replaces placeholder), language dropdown (en/hi, `activity.recreate()`), About. Lock icon in header → `repository.lock()`.

## 4.3 UI manifest (tokens — Master Spec §10; all frozen in `ui/theme/Theme.kt`)

| Token | Value |
|---|---|
| primary / accent / error | `#1A6B54` / `#E8A317` / `#B3261E` |
| surface light / dark / success | `#FAFAF7` / `#0F1412` / `#2E7D32` |
| Corner radius | 12dp (`Shapes.small/medium`) |
| Type scale | displaySmall 28sp/semibold · titleLarge 20sp/semibold · titleMedium 16sp/semibold · bodyLarge 16sp · bodyMedium 14sp · labelSmall/Medium 13sp/medium |
| Secrets | `SecretTextStyle` = monospace 16sp — MANDATORY for SECRET/PIN/CARDNUM/TOTP values |
| Touch targets | ≥ 48dp (buttons 52dp height; icon buttons default 48dp) |
| Screen padding | 24dp onboarding/lock · 16dp list screens · field vertical padding 6dp (edit) / 10dp (detail rows) |
| Dark mode | follows system; both palettes defined; never hardcode a Color outside Theme.kt/monogram palette |

## 4.4 Keyboard & input matrix (§2.2 → Compose, as frozen in `RecordEditScreen`)

| FieldType | KeyboardType | Transform/filter | Extras |
|---|---|---|---|
| TEXT | default | — | singleLine |
| SECRET | Password | PasswordVisualTransformation unless revealed | eye + generator (Casino icon) trailing |
| PIN | NumberPassword | digits only, max 8 | masked |
| NUMBER | Number | digits only | regex `v` inline error |
| DATE | readonly | — | Material3 DatePickerDialog → `YYYY-MM-DD` UTC |
| MONTHYEAR | readonly | — | month+year dropdown dialog → `YYYY-MM` |
| PHONE | Phone | — | placeholder `+91` |
| EMAIL | Email | — | error when missing `@` |
| URL | Uri | — | — |
| TOTP | default mono | — | placeholder `scr_edit_totp_hint` |
| CARDNUM | Number | digits ≤ 19; visual groups of 4 | network badge text; Luhn warning `scr_edit_luhn_warning` |
| IFSC | default mono | uppercase ≤ 11 | regex error; db autofill (TASK-404) |
| PICKER | readonly dropdown | — | list + `scr_edit_picker_other` → free text |
| LINK | readonly dropdown | — | records of `typeParam` template + None; stores uuid |
| NOTE | default | — | minLines 3 |
| FILE | — | — | TASK-401 attachment picker |

ImeAction: default (Next/Done supplied by singleLine) — do not customize further.
