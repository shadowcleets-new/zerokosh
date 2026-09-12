> The most recent 15 sessions are below. Older entries, verbatim and newest
> first, are in [`docs/changelog-archive/`](docs/changelog-archive/).

## [2026-09-11 10:30:00] - Baseline Profiles, generated where they cannot reach a real vault

### 1. Intent, Roles, & Context
- **The Problem:** release builds shipped with the profiles Compose and Tink carry for themselves and none for Zerokosh's own code. Every cold start interpreted and JIT-compiled the app's startup, lock screen and vault until the phone compiled them in the background — which Play eventually helps with through cloud profiles, and F-Droid and GitHub installs never get at all.
- **Specialist Personas Invoked:** Android Performance Engineer; Principal Security Auditor (where the generator is allowed to run); Build & Release Engineer (F-Droid reproducibility).
- **The Strategy:** record the real journey with the Baseline Profile Gradle plugin and a macrobenchmark generator, on a Gradle-managed emulator only. The test phone holds a vault of 195 records, and a test run uninstalls the app it tested — which is how a test vault was lost once already.

### 2. Surgical Technical Modifications
- **`:baselineprofile` (new)** — a `com.android.test` module targeting `:app`, with `androidx.baselineprofile` and `benchmark-macro-junit4` 1.5.0 (released 9 Sep 2026; the first line that supports AGP 9 without `newDsl=false`). A managed `pixel6Api34` device on the plain AOSP image, which was already installed, and `useConnectedDevices = false`.
- **`BaselineProfileGenerator.kt` (new)** — the first iteration creates a vault through the real onboarding (passphrase, recovery kit deferred, no quick unlock — an emulator has no sensor); every later one starts where a returning user does, at the lock screen, unlocks and walks all four tabs. `includeInStartupProfile` also emits a startup profile, which R8 uses for dex layout. The passphrase is a throwaway constant, deliberately not the PIN used on real phones: this repository is public.
- **`app/build.gradle.kts`** — applies the plugin and names the producer. The plugin's `nonMinifiedRelease` and `benchmarkRelease` build types copy release, and two things must not be copied: the signing (unsigned on any clone without the keystore, so uninstallable — the debug key is used) and the applicationId (now `com.zerokosh.app.benchmark`, so even on a phone the profiled build sits beside a real Zerokosh and the post-run uninstall removes only itself). `profileinstaller` is not added: compose-ui, biometric and lazysodium already bring 1.4.0, so the §6.2 list gains nothing.
- **`app/src/release/generated/baselineProfiles/` (new, committed)** — 29,916 rules, 1,848 of them Zerokosh's own: startup, lock screen, onboarding, vault home, the four tabs, navigation, the vault and crypto core. A plain release build only reads these files, so F-Droid never runs a device.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:generateBaselineProfile --dry-run` — the only device task in the graph is the managed emulator's, zero connected-device tasks. The generator passed on the emulator (1 test, 0 failed; stable by iteration 8 after the first-run onboarding made early iterations differ). `:core:test :app:check :baselineprofile:assemble` green: the same 180 unit tests, `verifyComplexity` (81 files, 563 functions, 16 baselined, 0 new over 10), `verifyTemplates`, `deadComposables`. The profiled APK was inspected rather than assumed: package `com.zerokosh.app.benchmark`, signed `CN=Android Debug`.
- **Resulting App State:** the release APK is **147,514 bytes larger (+1.21%)**, 12,193,302 to 12,340,816. The compiled profile inside it grows 9,133 to 14,281 bytes, and R8 now splits the dex it used to emit whole — one 5.64 MB `classes.dex` becomes 3.07 MB plus 2.87 MB — which is the startup profile doing what it is for, putting the classes cold start needs in the primary dex. The cost is paid in download size for a cold start that does less work.
- **Not yet measured:** the startup gain itself. Macrobenchmark numbers from an emulator this memory-starved would not mean much, and the Pixel run is not mine to start without asking — although the separate applicationId now makes it harmless to the vault on it.
- **Known gaps:** the `.benchmark` applicationId was verified by inspecting the built APK, not by a second generation run — the machine ran out of memory during the first (the harness killed the Gradle client; the daemon finished the job). The vault the generator makes is empty, so list rows are not profiled; Play's cloud profiles cover them there.
- **Next Sprint Phase:** the `org.multipaz` plan; optionally a startup benchmark on the Pixel.

## [2026-09-11 09:05:00] - The QR scanner on camera-compose, and the two bugs it was hiding

### 1. Intent, Roles, & Context
- **The Problem:** the authenticator's QR scanner was a `PreviewView` wrapped in `AndroidView`, the one camera surface in an otherwise all-Compose app. `camera-view` also pulled `androidx.media3` and, through it, Guava's annotation jars into a password manager that plays no media. Rewriting the scanner turned up two defects in the frame analysis that had nothing to do with the view.
- **Specialist Personas Invoked:** Android Platform Engineer (CameraX); Principal Security Auditor (dependency surface); Test Architect.
- **The Strategy:** swap the view for the composable CameraX now ships, fix what the rewrite exposed, and measure the size claim rather than assume it.

### 2. Surgical Technical Modifications
- **`app/build.gradle.kts`** — `camera-view` replaced by `camera-compose` 1.6.2; `camera-camera2` and `camera-lifecycle` 1.6.1 to 1.6.2. Five dependency groups leave the graph (`androidx.media3`, `com.google.code.findbugs`, `com.google.errorprone`, `com.google.j2objc`, `org.checkerframework`); none are added.
- **`ui/authenticator/AuthenticatorScreen.kt`** — `CameraXViewfinder` fed by the `Preview` use case's `SurfaceRequest`, bound through `ProcessCameraProvider.awaitInstance` in a `LaunchedEffect` in place of a future and listener. The existing unbind on dispose is kept.
- **Decoding off the main thread** — the analyzer ran on `ContextCompat.getMainExecutor`, so a full ZXing pass over every camera frame competed with the UI for the one thread it draws on. It now runs on a single-thread executor, shut down on dispose; only the decoded secret is posted back to the main thread.
- **`ui/authenticator/QrDecode.kt` (new): row stride** — the Y plane was handed to ZXing as if its rows were exactly `width` bytes long. Many cameras pad each row out to a `rowStride` wider than the picture, and on those every row after the first is read from the wrong offset: the QR arrives sheared and never decodes, with no error — the scanner just keeps scanning. `PlanarYUVLuminanceSource` is now given the stride as the data width and cropped to the picture. A frame whose stride or row count cannot hold the picture it claims is refused rather than read.
- **`app/proguard-rules.pro`** — `-dontwarn` for the errorprone and JSR-305 annotations Tink references. Those classes had been on the classpath only by accident, via media3's Guava; removing media3 made R8 fail on them as missing. Annotation-only, with no runtime behaviour, so not keeping them changes nothing at run time.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:core:test :app:check` green — 180 unit tests, `verifyComplexity` (81 files, 563 functions, 0 new over 10), `verifyTemplates`, `deadComposables`, minified release build. `QrDecodeTest` (5): a packed frame decodes; a frame padded by 96 bytes a row decodes; the same padded frame read the old way returns nothing, which is the bug reproduced; malformed frames are refused; only TOTP payloads pass.
- **Resulting App State:** on a Pixel 9 the scanner draws the live preview; the camera service logs a `CONNECT` for the app on open and a `DISCONNECT` on leaving, with no client left active. The Pixel packs its rows, so the stride bug could never have been seen on it — which is why the padded-frame test exists.
- **Size, measured:** the release APK is **15,320 bytes larger (+0.13%)**, not smaller. R8 was already stripping media3 from the old build, so the removal saves nothing on disk; the gain is the smaller dependency graph and one fewer interop view. The earlier suggestion that this swap would shrink the app was wrong.
- **Next Sprint Phase:** Baseline Profiles, then the `org.multipaz` integration plan.

## [2026-09-11 08:10:00] - After v0.5.0: Navigation 3, two panes, passkeys, translations, one catalogue

### 1. Intent, Roles, & Context
- **The Problem:** five follow-ups deferred at v0.5.0, taken one at a time: Navigation 3, the adaptive layout it unlocks, passkeys, the sixteen unreviewed translations, and a drifted template catalogue found on the way. Two of the reasons given for deferring them were wrong, and are corrected below.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor (WebAuthn); Apple-caliber CXO (two-pane); Localisation Lead; Test Architect.
- **The Strategy:** one commit per piece, so each can be verified and reverted on its own; the commit bodies carry the full reasoning.

### 2. Surgical Technical Modifications
- **`bd7880a` Type-safe routes** — all fourteen destinations typed with kotlinx.serialization. Hand-built route strings split "Punjab & Sind Bank" on the ampersand; the `Uri.encode`/`Uri.decode` workaround is deleted rather than moved.
- **`4a01c1e` Navigation 3** — `NavDisplay` over back stacks the app owns, one saveable stack per tab in `VaultNavState`. Nav3 **has** stable releases (1.0.0 to 1.1.7); calling it pre-release at v0.5.0 was wrong. Shared-element transitions carried across unchanged.
- **`9749b0e` Two-pane list and detail** — `ListDetailSceneStrategy` marks the vault list and the record as panes, so a record opens beside the list on a wide window. Below that width nothing changes.
- **`0de36c7` Passkeys** — `core/passkey` (CBOR, COSE_Key, authenticator data, ES256 assertions) written against the WebAuthn specification; attestation "none" and a zero AAGUID. A passkey is a record on a new `passkey` template, so the vault format does **not** change — another deferral reason that was wrong. The sign counter is persisted before the assertion is released, and the ceremony is abandoned if that write fails.
- **`20a5285` Translations** — an audit rather than a native review, which cannot honestly be given: all 766 keys present in all sixteen locales, zero placeholder mismatches. `verifyTemplates` now fails the build on a placeholder mismatch, the one translation error that crashes rather than reads badly. `docs/translation-review/` holds one packet per language for a native speaker, ordered by consequence.
- **`d37bde4` One template catalogue** — `spec/templates.json` had drifted from the shipped `assets/templates.json` (a whole template and seven fields missing), and `TemplateCatalogTest` guarded the stale copy. The shipped file is now the only one; `verifyTemplates` also fails when `strings_templates.xml` differs from what the generator produces.
- **`b2f93ed` Changelog archive** — the v0.5.0 trim had moved eighteen entries into gitignored `.claude/`, so they were never committed. Restored to `docs/changelog-archive/`, all 33 entries verified intact. `.gitnexus/`, a 54 MB local code index, is ignored.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:core:test :app:check` green after each commit; 175 unit tests, including twelve byte-exact WebAuthn tests, one verifying a real signature with an independent verifier. 7/7 instrumented tests after the Nav3 migration.
- **Resulting App State:** on a Pixel 9 with a vault recreated by hand: tabs, pushes and back all behave under Nav3; in landscape the rail, list and record sit side by side, portrait collapses to one pane, and back returns from the record to the list. Observed and not yet changed: the nav rail hides while a record is open in two-pane mode.
- **Known gaps:** passkey ceremonies are proven by tests but not yet run end to end against a real relying party. No native speaker has reviewed any translation.
- **Next Sprint Phase:** the camera-compose swap and Baseline Profiles.

## [2026-09-09 10:30:00] - v0.5.0: autofill was filling the wrong box, and four libraries

### 1. Intent, Roles, & Context
- **The Problem:** autofill had never worked correctly on a real form. The field parser classified every email box as the password field, so the password went into the wrong place and the username was never recorded at all. Underneath that sat three more failures, each hiding the next: the locked-vault prompt was invisible on any phone with a modern keyboard, unlocking could not return a result to the platform, and the vault forgot it was unlocked whenever Android reclaimed the process.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor; Apple-caliber CXO (the unlock sheet); Test Architect.
- **The Strategy:** drive every claim on a device, instrument rather than guess when the device disagreed, and treat "the user says it does not work" as a statement about the product rather than about their setup.

### 2. Surgical Technical Modifications
- **`autofill/AutofillFill.isPasswordInputType` (new)** — the input-type test was `type and TYPE_TEXT_VARIATION_WEB_PASSWORD`. A variation is a value inside a bit field, not a flag: `WEB_PASSWORD` is `0xe0` and `EMAIL_ADDRESS` is `0x20`, so the AND is non-zero for an ordinary email box. Every email field on every form claimed the password slot; the real password field was then skipped as a duplicate and the username never recorded. Masks the variation and compares it. Four unit tests pin it.
- **`AutofillFill.sameSite` (new)** — matching was a substring test, wrong in both directions. A login saved for `hdfcbank.com` was never offered at `netbanking.hdfcbank.com`, which is where Indian banks put sign-in; and `mybank.com` contains `bank.com`, so one site's credential was offered on another. Compares hosts on label boundaries, parent or child. Five tests; removing the boundary fails two.
- **`AutofillFill.domainHost`** — `java.net.URI` rejects "hdfcbank. com", and a phone keyboard inserts that space after a full stop unprompted, so a record could silently match nothing. Whitespace is stripped before parsing.
- **`ZerokoshAutofillService` field pairing** — a page carrying both a sign-in and a sign-up form got whichever password field came first in the tree, paired with the first username on the page, filling one form's name beside the other form's password. A focused password field now wins, and the username is paired with the one directly above it.
- **Inline presentations for the locked and generated rows** — a locked vault's "Unlock Zerokosh to fill" had no `InlinePresentation`, so on any phone whose keyboard draws the suggestion strip the prompt was invisible. The user tapped a password field, saw nothing at all, and concluded autofill did not work. The inline callback is now keyed on the row rather than on a Record.
- **`AutofillFill.authIntent`** — carried `FLAG_ACTIVITY_NEW_TASK`. An activity started into a new task **cannot return a result**, so the platform took `RESULT_CANCELED` however carefully `EXTRA_AUTHENTICATION_RESULT` was set: unlocking left the form empty. This is why the authentication dataset had always been a dead end.
- **`autofill/AutofillAuthActivity` (new)** and **`ui/UnlockSheet.kt` (new)** — the unlock used to open the whole password manager over somebody else's login page. It is now the system biometric prompt where quick unlock is enrolled, otherwise a card in the bottom fifth of the screen with the page still visible behind it.
- **Password generation on sign-up** — `shouldOfferGenerated` offers a fresh 16-character password when the form says `newPassword`, and when the vault has nothing saved for the site; withheld where a login already exists. Generated from the same CSPRNG as the in-app generator, so no network and no new permission.
- **`data/SessionKeeper` (new)** — the vault key lived only in the repository's memory and died with the process, which Android reclaims at will. The autofill service runs in that same process, so "Lock when I leave the app" described something the app did not do. The key is now sealed by a per-session Android Keystore key minted with `setKeyValidityEnd`, beside a deadline enforced three ways: the Keystore's own validity, the elapsed-realtime clock, and a boot reference, so a reboot or a wound-back date drops the session rather than extending it.
- **Auto-lock durations** — 2, 10 and 30 minutes added. The three fixed strings collapse into one format string derived from each language's existing "After 5 minutes", so seven choices cost one translation per language rather than seven, and no new wording entered the sixteen unreviewed languages.
- **`ZerokoshApp` process lifecycle** — auto-lock was bookkept by hand in `MainActivity`, which can only see its own Activity. `ProcessLifecycleOwner` asks whether the app is in front, and debounces the hand-offs the hand-rolled version had to special-case. Provider sheets are exempt: they serve another app, and reading one as "the user returning" locked the vault out from under the very request it was opened to answer.
- **`credentials/` (new)** — `ZerokoshCredentialProviderService`, `CredentialEntries`, `CredentialEntryActivity`. An app that calls `CredentialManager.getCredential()` never reaches `AutofillService`, so Zerokosh was absent from those screens silently. Matching reuses the autofill rule deliberately, so a login that fills in one place fills in the other. Passkeys are **not** here: they need real WebAuthn and a new record type, which changes the vault format and everything that merges it.
- **Two swallowed failures** — `upsertRecord` *returns* a `Result` rather than throwing, so `runCatching { ... }.isSuccess` was true even when the save had failed. Both save paths reported a stored password that had been dropped.
- **`app/src/androidTest` (new)** — seven instrumented tests over `UnlockSheet`, the only way into the vault from outside the app.
- **`ZerokoshNav` breakpoint** — `LocalConfiguration.screenWidthDp` describes the display, not this app's window. `currentWindowAdaptiveInfo()` reads the real window metrics, so a phone-shaped window on a tablet stops being handed the tablet layout.
- **Toolchain** — AGP 9.3.1 to 9.4.0; Gradle was already 9.7.1. Four dependency groups added under R0.5: `lifecycle-process`, `credentials`, `material3.adaptive` with `window`, and the instrumented-test set. Deliberately not added: Hilt, DataStore, splashscreen, Glance.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:core:test :app:check` green throughout — 173 unit tests, `verifyComplexity` (75 files, 523 functions, 16 baselined, 0 new over 10), `verifyTemplates`, `deadComposables`. `:app:connectedDebugAndroidTest` 7/7 on a Pixel 9. Mutation-checked: breaking the input-type mask, the host boundary, the sheet's field-clear and its enablement guard each fail exactly the tests that cover them.
- **Adversarial review:** a multi-agent review of the first draft of the session work found three defects, all real and all fixed here — a resume re-armed a full window so the deadline slid indefinitely; after one resume the deadline was never checked again, so a service-hosted process filled passwords hours late; and `runBlocking` on the binder thread risked both a missed suggestion and a deadlock against the repository's IO mutex. That review exhausted the month's agent budget (114 of 119 agents died), and a fan-out that large was the wrong instrument for a five-file diff.
- **Resulting App State:** verified on a Pixel 9 running Android 17. Sign-in offers the saved credential and fills both fields; sign-up offers that credential *and* a generated password; a locked vault shows the unlock prompt, and unlocking fills the form without the app appearing. Killing the process outright and filling inside the window offers credentials with no prompt; the stored deadline is unchanged across repeated resumes; forty seconds past a one-minute window the same long-lived process locks the vault, wipes the session and asks again. Credential Manager verified against a real client added to `:autofilltest`: unlocked get returns the credential, locked get shows the unlock action and then the list, create writes a record to the vault.
- **Known gaps:** the adaptive breakpoint is build-verified but never seen on screen — `connectedAndroidTest` cleared the app's data, and the test vault with it, and onboarding could not be driven back through adb. Navigation 3 is deliberately not started: there is no stable release (best is `1.2.0-beta01`), it is a rewrite of a 562-line navigation file, and it could not be verified on a device.
- **Next Sprint Phase:** recreate a device vault, verify the nav rail, then Navigation 3 and the two-pane list/detail it unlocks. Passkeys remain the large open piece, and no native speaker has yet reviewed any of the sixteen non-English translations.

## [2026-08-28 00:45:00] - The device run: five real bugs, three of them in autofill

### 1. Intent, Roles, & Context
- **The Problem:** everything shipped so far had been verified by reading it. Running it on a Pixel 9 against a minified `releaseCheck` build found five defects, three of which meant the headline autofill feature had never worked once.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor; QA Automation Lead.
- **The Strategy:** drive the real build over adb, fix what the device says rather than what the code implies, and re-run each scenario against the fix.

### 2. Surgical Technical Modifications
- **`autofill/AutofillFill.kt` (new)** — response building and the auth handshake, extracted from the service because the locked case needs the same code from an Activity. `FieldTargets` carries ids and origin only; typed values still travel in memory via `PendingSave`.
- **`ZerokoshAutofillService.onFillRequest`** — three fixes. (a) A response carrying neither a dataset nor a SaveInfo threw `IllegalStateException` out of the binder callback and killed the app process; the guard admitted a one-field structure while `saveInfoFor` demanded two, which is the first page of every two-step login. Now returns `onSuccess(null)`. (b) Locked vaults now use response-level authentication so several matches can be offered after unlock. (c) Datasets are built from `res/layout/autofill_suggestion.xml`.
- **`res/layout/autofill_suggestion.xml` (new)** — `android.R.layout.simple_list_item_2` is rooted in `TwoLineListItem`, which is not `@RemoteView`. RemoteViews refused to inflate it inside system_server *after* the service reported success, so no credential suggestion had ever rendered and nothing on our side logged a thing.
- **`MainActivity.DeliverAutofillWhenUnlocked`** — returns the finished `FillResponse` through `EXTRA_AUTHENTICATION_RESULT` and finishes. Previously the auth dataset opened the app and stopped: the user unlocked and the form stayed empty.
- **`autofill/AutofillSave.loginRecordFor`** — a credential captured in a native app was stored with no `website`, and the login matcher keys entirely off that field, so it could never be offered back to the app it came from. Stores the package name, which is what the matcher already derives its hint from.
- **`ui/common/RecordCategory.recordMeta`** — iterated the fields map, whose order is a state map's hash order, so a row could show a masked username where the site belongs. Iterates template order.
- **`core/import/CsvImport.mergeWithExisting`** — an update rebuilt the record from the CSV row, dropping institution, template, custom fields, reminders, older history, and any field the export has no column for, a TOTP secret included. Now updates the existing record. `CsvMergeTest` pins it and fails without the fix.
- **`ui/onboarding/Onboarding.kt`** — `FLAG_SECURE` is clamped for the Recovery Kit step only, so the one screen showing the key is not also the one screen a recorder can have; and a refused quick-unlock keeps the user on step 6 with a reason instead of silently finishing with it off. `sealOnboarding` extracted to stay under complexity 10.
- **`ui/common/SaveError.kt`** — the appended detail no longer surfaces internal `/data/user/...` paths.
- **`quickunlock/QuickUnlockManager.kt`** — the bare `catch` on the enable path now logs; it is the only place the reason for a failed enrolment exists.
- **`app/build.gradle.kts`** — `releaseCheck` is `isDebuggable = true`. Shrinking and obfuscation stay on; without `run-as` the vault file cannot be made unwritable and the save-failure path cannot be exercised on a device. Never published.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:core:test`, `:app:testDebugUnitTest`, `verifyComplexity` (68 files, 440 functions, 16 baselined, 0 new over 10) — all green. `verifyComplexity` caught `QuickUnlockScreen` at 13 during this work and was the reason it was split.
- **Resulting App State:** verified on a Pixel 9 against the minified build. Screenshot lifecycle measured three ways (11,585 colours during onboarding, 9 when clamped, 1,775 after the Settings toggle). All 315 logos render — the Play Store monogram bug is gone. Autofill: suggestion renders, one tap fills both fields, save prompt captures a new credential, and the captured credential is offered back in the same app. Locked-vault auth round trip lands back in the form filled. Save-failure dialog raised by `chmod 0555` on the vault directory; edits survive it and a retry succeeds. Biometric enrolment wraps the master key and unlock arms the prompt. Trash restore, CSV import (2 added, 1 updated), and vault review all correct.
- **Next Sprint Phase:** publish the Android repo (GPL obligation), make Hindi real, upload the `versionCode 2` bundle, and the polish plan's Phases 0/1.

## [2026-08-27 18:30:00] - Four internal gaps: silent saves, no app tests, no catalogue guard, a deprecation

### 1. Intent, Roles, & Context
- **The Problem:** four gaps found by reading the app rather than running it — a save that could fail invisibly, an app module with no tests, a catalogue consistency check that lived only in a shell history, and a deprecated storage library emitting 11 warnings.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor; Test Architect.
- **The Strategy:** fix the data-loss path first, then remove the conditions that let it hide.

### 2. Surgical Technical Modifications
- **`SaveResult` (`VaultRepository`)** — `persist` returns `Result<Unit>`; all eight mutators propagate it. Written as a wrapper around the existing body, not a nesting, so merge-before-write is untouched and the diff is signatures rather than 40 re-indented lines. Absent state (locked vault, missing uuid) reports success: an error the user cannot act on is worse than none.
- **`ui/common/SaveError.kt`** — one dialog, one message that names the likely cause (a lapsed SAF grant), what to do, and that nothing was lost from the vault as it stood. Wired into the three call sites that navigate away on the assumption of success.
- **`verifyTemplates` build task** — asserts templates, labels, category map and picker lists agree. Tested in both directions: removing one label fails the build naming it. Wired into `check` and both release assemblies.
- **App module test source set — the first tests it has ever had.** Required making three Android dependencies substitutable: `VaultPrefs` extracted from `Prefs`, `chooseKdfParams` moved onto `CryptoProvider` with a spec default, and the repository now takes `CryptoProvider` rather than `AndroidCrypto`. 10 tests over the save-failure path, history capture, trash round-trip, backup-once-per-session and file round-trip.
- **`@Suppress("DEPRECATION")` on `Prefs`** with the reasoning recorded: nothing there is a secret, and migrating the store would strand `onboardingDone` and silently re-onboard existing users. Staying costs a warning; moving costs data.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:app:assembleRelease`, `:core:test`, `:app:testDebugUnitTest` (10 run, 0 failures), `:app:deadComposables` (117), `:app:verifyTemplates` (21 templates, 184 fields), `verifyDynamicResources` — all green.
- **Resulting App State:** unchanged in the happy path; a failed write now produces a dialog instead of silence. Not verified on device.
- **Next Sprint Phase:** the device batch, now nine items.
## [2026-08-27 16:10:00] - Templates that offered fields the service cannot have

### 1. Intent, Roles, & Context
- **The Problem:** WhatsApp's edit screen offered Membership, Membership renewal and Gift cards. Not isolated — three templates handed irrelevant fields to a third of the 315-service catalogue.
- **Specialist Personas Invoked:** Information Architect; SEO/Growth Strategist (Indian financial vocabulary); Android Platform Engineer.
- **The Strategy:** measure the catalogue before changing it. The detail screen already hides empty fields, so this was only ever an edit-screen problem, and only ever fixable in the templates and the mapping.

### 2. Surgical Technical Modifications
- **Re-mapped 10 services** (gallery only, no stored record touched): WhatsApp, SmartThings, Justdial, Vyapar, KNOT → `login`; Keepa, Alibaba → `login`; FamApp, Cheq → `card`; INDmoney → `demat`. `app_profile` drops 12 → 4, and the four left (PVR, BookMyShow, District, Samsung Wallet) genuinely have memberships and gift cards.
- **Split `transit`** with a new `travel_booking` template: 16 booking and ride-hailing services keep a wallet and lose `smart_card_number`; 6 information and courier apps become plain logins. `transit` is now exactly the 29 operators with a real NCMC stored-value card.
- **7 fields added** from research: `demat.depository` (CDSL 16-digit vs NSDL `IN`-prefixed — the numbers were unvalidatable without it), `insurance.{premium_mode, commencement_date, maturity_date, policy_term}`, `telecom.{circle, sim_number}`. Two new picker lists.
- **`maturity_date` wired into reminders and the vault review** — the reason to store a payout date is to be told about it.
- **79 label strings added.** A consistency check written for this work found that all six Gov ID templates — PAN, Aadhaar, Passport, DL, Voter ID, DigiLocker — had **no labels at all**, plus `shopping` and `transit`. 71 fields were rendering as humanised ids: "Dob", "Epic number", "Pan number". Pre-existing, unrelated to the reported bug, and invisible until something checked.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** a new consistency pass asserts every gallery template exists, every template has a title, every field has a label, every template is in the category map and every PICKER names a real list — 21 templates, 184 fields, all consistent. `:app:assembleDebug`, `:app:assembleRelease` (dynamic-resource guard ok), `:core:test`, `:app:deadComposables` (116) green.
- **Resulting App State:** release APK carries **214 `tpl_` strings, up from 119**. Not verified on device.
- **Next Sprint Phase:** the device batch, now eight items.
## [2026-08-27 14:20:00] - Autofill "save" was reporting success and saving nothing

### 1. Intent, Roles, & Context
- **The Problem:** Android's "Save to Zerokosh?" prompt worked, the user tapped yes, and the credential was discarded. Both branches of `onSaveRequest` called `callback.onSuccess()` without writing anything.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor (silent-failure class).
- **The Strategy:** the consent already exists by the time the framework calls us — the system asked and was told yes. What was missing was the writing down.

### 2. Surgical Technical Modifications
- **Two separate bugs in one method:**
  - Vault locked → returned immediately, dropping the credential.
  - Vault unlocked → started `MainActivity` with `EXTRA_AUTOFILL_SAVE`, which **nothing read**, and never touched the typed values at all. `parseStructure` only ever returned `AutofillId`s.
- **Modified Files:**
  - `autofill/ZerokoshAutofillService.kt`: `Parsed` now carries `usernameValue`/`passwordValue`, read from `node.autofillValue` — populated only on a save request, and the thing that makes saving possible. `onSaveRequest` rewritten: fails honestly when there is nothing to store, saves directly when open, stashes when shut. Work moved off the binder thread onto a service scope cancelled in `onDestroy`.
  - `autofill/PendingSave.kt` (new): in-memory handoff, same reasoning as `PendingCard` — a password must never travel as an Intent extra. Deliberately survives a lock, since it exists *because* the vault is locked; a 5-minute TTL bounds that instead.
  - `autofill/AutofillSave.kt` (new): one record builder for both routes. Updates an existing site+user match rather than inserting a duplicate, which also puts the replaced password into that record's history.
  - `ui/ZerokoshNav.kt`: finishes a stashed save on unlock.
  - Removed `EXTRA_AUTOFILL_SAVE`/`EXTRA_PACKAGE`/`EXTRA_DOMAIN`, dead once the intent round-trip went.
- **Irreversible Actions:** none.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:app:assembleRelease` (dynamic-resource guard ok), `:core:test`, `:app:deadComposables` (116 checked) — green.
- **Resulting App State:** **not verified on device**; joins the batch.
- **Next Sprint Phase:** one device pass covering seven items, then publish the repo and make Hindi real.
## [2026-08-27 12:40:00] - The release build shipped with no logos at all

### 1. Intent, Roles, & Context
- **The Problem:** every institution fell back to a monogram in the Play build while looking correct in debug. Reported as a logo bug; it was not only logos.
- **Specialist Personas Invoked:** Android Release Engineer; Principal Security Auditor (silent-failure class).
- **The Strategy:** measure the artifact rather than the source. `aapt2 dump resources` on both APKs settled it in one command.

### 2. Surgical Technical Modifications
- **Root cause:** `isShrinkResources = true`. The shrinker resolves `R.<type>.<name>` in code and `@type/name` in XML and nothing else. Both dynamic families are composed strings — `getIdentifier("logo_$key", …)` in `CompanyLogo.kt` and `getIdentifier("tpl_${templateId}_$fieldKey", …)` in `FieldLabels.kt` — so it judged the whole set dead.
- **Measured before the fix:** logos **0 of 299**; template label strings **12 of 119**. Debug had all of both, because debug never shrinks. The reported symptom was the logos; 107 missing field labels were falling back to humanised field ids unnoticed.
- **Modified Files:**
  - `app/src/main/res/raw/keep.xml` (new): `tools:keep="@drawable/logo_*,@string/tpl_*"`.
  - `app/build.gradle.kts`: `verifyReleaseDynamicResources` / `verifyReleaseCheckDynamicResources`, each reading its own shrinker report and failing the build on any `logo_`/`tpl_` marked "is not reachable." Wired via `finalizedBy` on the matching assemble/bundle tasks.
- **Irreversible Actions:** none.
- **Payload/Schema Changes:** none.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** release APK now carries **299 logo drawables and 119 tpl_ strings — identical to debug**. The guard was tested in both directions: with `keep.xml` removed the build fails naming 406 dropped resources; restored, it passes.
- **Resulting App State:** APK 10.49 MB (up ~0.15 MB). Not yet installed on hardware.
- **Next Sprint Phase:** versionCode 2 must reach Play — the published alpha has no logos and degraded field labels.
## [2026-08-27 11:30:00] - Live strength for PINs, and suggestions that are actually generated

### 1. Intent, Roles, & Context
- **The Problem:** two reports on S4 — the strength meter appeared not to respond to typing, and the "TRY" examples never changed.
- **Specialist Personas Invoked:** Principal Security Auditor (entropy claims); Apple-caliber CXO.
- **The Strategy:** the first report named the wrong widget. The Argon2id card shows KDF cost measured on the device and must not move with typing — it would tell the user their passphrase changed the key-derivation cost, which it does not. The live meter already existed a few dp above it; what did not exist was that meter in **PIN** mode, which is the branch that needs it most.

### 2. Surgical Technical Modifications
- **Modified Files:**
  - `core/passphrase/PinStrength.kt` (new): `pinScore` / `isWeakPinPattern`. One loop over block sizes catches `000000`, `121212` and `123123` alike; runs are caught by first differences. Ceiling of 2 on purpose — six digits is ~19.9 bits and must never read "strong" beside a 60-bit passphrase.
  - `core/passphrase/PassphraseSuggestions.kt` (new): SecureRandom over a 69-word list, ~300k three-word combinations. Distinct words per phrase, distinct phrases per pair, one carrying a digit so the examples satisfy the screen's own "not a single dictionary word" rule.
  - `ui/onboarding/Onboarding.kt`: meter now renders in both modes with PIN-specific copy and a 3-of-5 cap; PIN gets its own three checks; TRY chips read from the generator.
  - **Tests:** `PinStrengthTest` (incl. all 100k six-digit PINs against the ceiling), `PassphraseSuggestionsTest` (500 draws for distinctness, 200 for variation).
- **Irreversible Actions:** none.
- **Payload/Schema Changes:** none.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:core:test`, `:app:deadComposables` (109 checked) — green.
- **Resulting App State:** **not verified on hardware** — device unplugged. Joins the two checks already queued.
- **Next Sprint Phase:** on device — PIN meter behaviour, fresh TRY chips per visit, step 4 usable in landscape, and FLAG_SECURE flipping at completion.
## [2026-08-27 10:45:00] - Landscape onboarding was a dead end

### 1. Intent, Roles, & Context
- **The Problem:** on a landscape phone the first-run flow could not be completed. `WelcomeScreen`'s primary button sat below the viewport with no way to scroll to it, and `OnboardingScaffold` squeezed its body to roughly 40dp between a fixed header and a pinned action bar.
- **Specialist Personas Invoked:** Apple-caliber CXO; Android Platform Engineer.
- **The Strategy:** keep the designed portrait layout byte-for-byte and branch only when the viewport is genuinely too short, rather than rewriting the layout for the common case to satisfy the rare one.

### 2. Surgical Technical Modifications
- **Modified Files:**
  - `ui/onboarding/WelcomeScreen.kt`: `Box` → `BoxWithConstraints`; below 640dp of height the column scrolls and the weighted spacer becomes a fixed 28dp gap. `weight(1f)` needs a bounded height, which a scrolling column does not have, so leaving it in place would have silently resolved to zero.
  - `ui/onboarding/Onboarding.kt` (`OnboardingScaffold`): below 600dp the header scrolls together with the body in one region; only the action bar stays pinned. Portrait is untouched.
- **Irreversible Actions:** none.
- **Payload/Schema Changes:** none.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:core:test`, `:app:deadComposables` (109 checked) — green.
- **Resulting App State:** the WelcomeScreen fix is **confirmed on a Pixel 9 in landscape** — both buttons reachable by scrolling where before they were unreachable at any scroll position. The **scaffold fix compiles but is unverified**: the device was disconnected before it could be installed.
- **Next Sprint Phase:** on device — (a) step 4 usable in landscape, (b) FLAG_SECURE flips at the moment setup completes. Both are the last two unverified items.
## [2026-08-27 02:10:00] - Screenshots during first run; reveal on the Confirm field

### 1. Intent, Roles, & Context
- **The Problem:** FLAG_SECURE applied from the very first frame, so the Recovery Key — shown exactly once during setup — could not be captured. And the Confirm field on step 4 had no reveal toggle, unlike the field directly above it.
- **Specialist Personas Invoked:** Apple-caliber CXO; Principal Security Auditor (screen-capture policy).
- **The Strategy:** treat first run as the one window where capture is a recovery feature rather than a leak, and shut it the instant setup reports done.

### 2. Surgical Technical Modifications
- **Modified Files:**
  - `MainActivity.kt` (`applyScreenPrivacy`): condition widened to `allowScreenshots || !onboardingDone`.
  - `ui/ZerokoshNav.kt` (quickunlock destination): re-applies the flag when `onboardingDone` flips. The window flag is set per-activity, so the pref alone would not clamp until the next cold start.
  - `ui/onboarding/Onboarding.kt` (Confirm field): passes `onToggleVisible`. `FilledSecretField` only draws the eye when that lambda is non-null, which is why the field above it had one and this did not. Both fields share the existing `visible` state deliberately — a confirm field exists to catch a typo, and two fields that disagree about masking make that harder, not easier.
- **Irreversible Actions:** none.
- **Payload/Schema Changes:** none — `onboardingDone` already existed.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:core:test`, `:app:deadComposables` (109 checked) — all green.
- **Resulting App State:** **not verified on hardware** — the Pixel 9 disconnected mid-run, so the capture behaviour is reasoned from the code, not observed. Worth one pass on device before shipping.
- **Next Sprint Phase:** confirm on device that first run captures and that the first frame after setup is black; AGP still pinned at 9.3.1 pending a stable 9.4.0.

## [2026-08-27 01:20:00] - Edge-to-edge deprecations, Gradle 9.7.1, discoverable autofill

### 1. Intent, Roles, & Context
- **The Problem:** Play Console flagged "Edge-to-edge may not display for all users"; the toolchain was a minor behind; and the autofill provider, though registered and working, was invisible from inside the app.
- **Specialist Personas Invoked:** Android Platform Engineer; Release Engineer; Apple-caliber CXO (discoverability).
- **The Strategy:** treat each report as a claim to verify rather than a task to perform. The edge-to-edge warning turned out to be one dead theme attribute, not missing inset handling. The autofill complaint turned out to be a discovery problem, not a missing service.

### 2. Surgical Technical Modifications
- **Modified Files:**
  - `app/src/main/res/values/themes.xml`: dropped `android:statusBarColor`. Deprecated at API 35 and inert at `targetSdk 36`, so it was a no-op that still tripped Play's check. `enableEdgeToEdge()` in `MainActivity` already owns the bars on every supported API. All 14 screens were audited for inset handling first; every one already pads.
  - `gradle/wrapper/*`, `gradlew*`: Gradle 9.6.1 -> **9.7.1**.
  - `app/build.gradle.kts`: `androidx.autofill:autofill:1.3.0` — a §6.2 addition, justified in place. The platform's `InlinePresentation` needs a Slice whose layout is a versioned androidx contract; hand-rolling it means hard-coding an internal format.
  - `autofill/AutofillSetup.kt` (new): provider status + the system picker intent.
  - `autofill/InlineSuggestions.kt` (new): keyboard-strip chips, API 30+, IME-version-checked.
  - `autofill/ZerokoshAutofillService.kt`: both dataset paths now carry an inline presentation alongside the RemoteViews one; chip count clamped to what the IME offered.
  - `ui/settings/SettingsScreen.kt`: "Autofill service" row with live status, refreshed on `ON_RESUME` so returning from the picker updates it.
- **Irreversible Actions:** none.
- **Payload/Schema Changes:** none.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:app:assembleRelease`, `:core:test`, `:app:deadComposables` (109 composables, none dead) — all green on Gradle 9.7.1. Confirmed in the built artifact rather than assumed: `aapt2 dump resources` shows **zero** `statusBarColor` entries under `style/Theme.Zerokosh`; the "Autofill service" string ships; `InlineSuggestions` and androidx's `InlineSuggestionUi` are both in the dex.
- **Resulting App State:** the **minified** `releaseCheck` build installs and runs on a Pixel 9 — the check that matters, since a new library plus R8 is exactly where a release breaks. `pm query-services` confirms the service survives minification and is still offered to the system.
- **Next Sprint Phase:** AGP stays at **9.3.1**. There is no stable 9.4.0 — Google Maven has only `9.4.0-rc02` and alphas, and a release candidate is the wrong toolchain for a Play submission. Also outstanding: `EncryptedSharedPreferences`/`MasterKey` are deprecated (11 warnings in `Prefs.kt`), and the app module still has no test source set.

## [2026-08-26 21:55:00] - Play Console identity, and the listing assets finished

### 1. Intent, Roles, & Context
- **The Problem:** the app id had to become the one reserved in Play Console (`com.zerokosh.app`), and the store listing was still one asset short — screenshots.
- **Specialist Personas Invoked:** Android Release Engineer; Growth/Store-Listing Strategist.
- **The Strategy:** change `applicationId` and nothing else. `namespace` names the generated `R`/`BuildConfig` and resolves the manifest's relative class names — it is not what Play matches on, so renaming it would have moved ~100 source files and rewritten the R8 keep rules to buy nothing. Resource lookups through `context.packageName` still resolve, because the merged manifest's package follows `applicationId`; that is the same mechanism `applicationIdSuffix` has always relied on, and `releaseCheck` already exercises it.

### 2. Surgical Technical Modifications
- **Modified Files:**
  - `app/build.gradle.kts` (defaultConfig): `applicationId` → `com.zerokosh.app`, with the namespace split explained in place so nobody "fixes" the mismatch later.
  - `RELEASE.md` (line 68): the `releaseCheck` id follows the suffix.
  - `play/listing.md`: the "one asset still missing" section replaced by the upload table and screenshot order.
  - `play/screenshots/`: five device captures plus their Play-ready conversions.
- **Irreversible Actions:** none yet — but `applicationId` is permanent **after** the first upload. Play never lets an id be reused or renamed, so this had to be right before the AAB goes up, not after.
- **Payload/Schema Changes:** none. No stored data is keyed by package name; existing sideloaded installs simply sit beside the new id rather than upgrading it.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:bundleRelease` clean; merged release manifest reads `package="com.zerokosh.app"`; `jarsigner -verify` passes with the upload key (alias `zerokosh`, valid to 2054-01-10, comfortably past Play's 2033 floor); `design/check_listing.py` green (24/30, 72/80, 2964/4000); `make_play_screenshots.py` emitted 5 files at exactly 1080×1920.
- **Resulting App State:** unchanged at runtime — one build-config string moved. The 10.9 MB AAB is uploadable as it stands.
- **Next Sprint Phase:** the Console work only the account holder can do — upload the AAB, accept Play App Signing, complete Data safety and the content-rating questionnaire, paste the listing. The app module still has 0 tests; seven ship-blocking bugs this month were all found by looking at the app, none by the suite.

## [2026-08-21 09:30:00] - NFC card capture, brand logos, Expressive completion

### 1. Intent, Roles, & Context
- **The Problem:** eight sessions of work with nothing logged since the glitch sweep — a second logo batch, the rest of the Expressive surface, a colour system rebuild, a dead-code purge, and a new NFC capture path.
- **Specialist Personas Invoked:** Android Platform Engineer; Principal Security Auditor (EMV/PAN handling); Colour Systems Engineer; Accessibility Auditor.
- **The Strategy:** finish what the coverage audit listed, then let the audit's own findings redirect the work — the two most valuable changes this session were both things the audit surfaced rather than things that were planned.

### 2. Surgical Technical Modifications
- **NFC card capture** (`cdf2abe`) — tap a contactless card to prefill the card template.
  - `core/emv/Tlv.kt`, `core/emv/EmvCard.kt`: BER-TLV reader and Track 2 / `5A` / `5F24` / `5F20` decoding, in pure Kotlin with **17 unit tests**, because this consumes data off a payment card.
  - `app/nfc/CardNfcReader.kt`: reader mode, PPSE → AID → GPO → READ RECORD.
  - `ui/record/TapCardSheet.kt`: the sheet, plus a contactless button on the card-number field.
  - **The printed CVV is not on the chip and cannot be read.** The chip's CVC3 is a per-tap value, not the number on the signature strip. Stated in the sheet's own copy so nobody hunts for a setting that does not exist. Fills 3 of the card template's 12 fields.
  - Tap-to-Pay is host card emulation — the phone pretending to *be* a card. Reading one is the opposite (`enableReaderMode`), and the two conflict, so reader mode is scoped to the sheet and released on dispose.
  - No new dependency: `IsoDep` is platform, so the §6.2 list is untouched. NFC declared `required="false"`.
- **Colour system rebuilt tonally** (`f909021`) — `expressiveLightColorScheme()` turned out to be `lightColorScheme()` with four roles moved to tone 30: the M3 baseline palette, no seed, no dark counterpart. Adopting it literally would have replaced the burnt orange with Google's purple. So the Expressive *rule* was adopted instead: a full scheme generated from our own seeds in CIELAB (an M3 tone **is** L\*), emitted as constants by `design/gen_tonal_scheme.py`. **36 roles, all derived** — the old scheme set 24 by hand and left 12 undefined, which is what produced a pink FAB. Worst contrast pair 4.07:1, up from ~3.4:1.
- **Dead code purge + build guard** (`6922f37`) — `PasswordGeneratorScreen` was not merely unreferenced: it carried a second password generator built on `kotlin.collections.random()`, i.e. `Random.Default`, a **non-cryptographic PRNG**. Wiring it up would have shipped a weak generator in a password manager. Deleted, along with seven other unreferenced composables (419 lines).
  - Nothing in the toolchain catches this: "public but unused" is never a warning because public implies an external caller, and unused *private* composables are not flagged either because the Compose plugin rewrites them. The build emits 21 warnings and not one is "never used". Hence `:app:deadComposables`, wired into `check`, no dependency added.
- **Expressive completion** (`341943a`) — inset focus rings, `FilterChipDefaults.shapes()` replacing a hand-rolled morph, `ButtonGroup`, `animateFloatingActionButton`, `roundedShape`, `rememberBottomSheetState`, `ContainedLoadingIndicator`. Referenced Expressive APIs 15 → 24.
- **Brand logos** (`745c70b`, `54470c1`, `7bb498d`) — 73 imported across two batches; monogram fallbacks **98 → 47**. Nine `.jfif` and one SVG could not ship as delivered; Mastercard was rebuilt from the SVG's own geometry. Five sources had the transparency checkerboard baked in as pixels.
- **Accessibility** (`4289b85`, `450686b`) — a reveal toggle on all nine masked fields (only two had one), and the lock screen's mode button now names where it goes rather than where it has been.

### 3. Verification & Validation
- **Execution Commands & Diagnostics:** `:app:assembleDebug`, `:app:check` (incl. `deadComposables`), `:core:test` — 0 lint errors, all tests green, 102 composables checked.
- **Resulting App State:** verified on a Pixel 9. The blob morph was measured rather than eyeballed — silhouette area swings **15.7%** (30,320 → 25,558 px) while the bounding box expands, which a background gradient cannot do; at `animator_duration_scale=0` three frames are byte-identical, confirming the reduced-motion path. **NFC confirmed against a real contactless card**: number and expiry captured, and the expiry stored in `YYYY-MM` exactly as the `MMYY` conversion predicts. Cardholder name absent — that issuer does not expose tag `5F20`, which is the documented "sometimes available" case now confirmed in the field rather than in theory. (The issuer and the captured values are deliberately not recorded here: this file is public, and a changelog is not a place to write down anything read off a payment card.)
- **Next Sprint Phase:** 47 monogram fallbacks remain (mostly foreign banks and bus operators; `design/audit_logos.py` lists them). `TextFieldLabelPosition.Cutout` is blocked behind a `TextFieldState` migration. 12 duplicate drawable resource names still ship twice. And the repo directory rename to `zerokosh` is now done.
