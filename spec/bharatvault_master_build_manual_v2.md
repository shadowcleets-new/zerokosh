# BharatVault — Master Build Manual v2.0 — Fully-Native Edition

**What this document is:** a complete, self-contained specification that any implementer — a human developer, a large AI model, or a small AI model — can follow, section by section, to build BharatVault as **four fully native applications: Android, iOS, Windows, and macOS**.

**v2.0 architecture lock:** BharatVault is built with each platform's own native language and first-party toolkit — **Kotlin + Jetpack Compose (Android), Swift + SwiftUI (iOS and macOS), C# + WinUI 3 (Windows)**. NO cross-platform framework — Flutter, React Native, Kotlin Multiplatform, Compose Multiplatform, .NET MAUI, Electron, Tauri, or any WebView/shared-runtime UI — may be used anywhere in this project. Interoperability between the four apps is guaranteed by this specification and its conformance suite, **not by shared code**. Rationale and rejected alternatives: Appendix G (ADR-001).

**What BharatVault is:** an open-source (GPL-3.0), local-first, zero-server password and credential vault designed for Indian users. It models Indian financial life correctly (bank accounts with transaction/profile passwords, ATM PINs, UPI PINs, IFSC codes, LPG IDs, FASTag, demat accounts) and is free forever for individuals because it has no backend to pay for.

**Reading order:** §0 → §1 → §2 → §3 → §4 → §5, then ONLY the section for the platform you are building (§6–§9), then §10–§15. Do not skip §0–§5. Every platform section assumes you know §2–§5.

---

## §0. RULES FOR AI AGENTS AND IMPLEMENTERS — READ FIRST

**R0.1 Conformance language.** MUST = mandatory, build fails without it. NEVER = prohibited, build fails with it. SHOULD = do it unless you have a documented reason. MAY = optional.

**R0.2 You MUST NOT change these locked decisions** (they are the product):
1. No server. No account creation. No network calls are required for the app to function. The only permitted network calls are the two optional, user-initiated features in §5.9 (breach check) and §13 (update check on desktop).
2. All vault data is encrypted with the exact scheme in §3 before it touches any disk.
3. The vault file format in §4 is byte-identical across all four platforms. A vault created on Android MUST open on Windows, macOS, and iOS.
4. Crypto library = **libsodium** on every platform, via the bindings named in each platform section. NEVER implement any cryptographic primitive yourself. NEVER substitute another crypto library.
5. Templates and field IDs = the JSON in §2.4, copied **verbatim** into the app as a bundled resource. NEVER rename a field ID.
6. No analytics, no telemetry, no crash-reporting SDKs, no ads, no third-party tracking of any kind.
7. License GPL-3.0.
8. **Fully native, per platform (ADR-001, Appendix G).** Android = Kotlin + Jetpack Compose. iOS = Swift + SwiftUI. macOS = Swift + SwiftUI. Windows = C# + WinUI 3. The core (§2–§4) is implemented **independently three times** — once in Kotlin, once in Swift, once in C#. NEVER introduce Flutter, React Native, Kotlin Multiplatform, Compose Multiplatform, .NET MAUI, Electron, Tauri, a WebView-based UI, or any shared cross-platform runtime or UI layer. The single permitted code sharing is Swift code between the iOS and macOS targets, because Swift/SwiftUI is Apple's native stack for both (§9.1). **The specification is the shared code:** the four apps stay interoperable because every implementation must reproduce the §3.6 vectors byte-for-byte and pass the §11.2 cross-platform fixture matrix.

**R0.3 You MAY decide yourself** (do not stop to ask): exact padding/margins within the design tokens (§10), internal class names, test file organization, icon artwork, copywriting of helper text (must match tone rules §10.6).

**R0.4 If stuck:** re-read the relevant spec section; if the spec is genuinely silent, choose the simplest option that preserves R0.2, and record the decision in `DECISIONS.md` at repo root. NEVER resolve ambiguity by adding a network call, a dependency, or a weaker crypto parameter.

**R0.5 Checkpoint protocol.** After completing each milestone in §0.7, output/commit: ✅ milestone ID, list of acceptance tests passed (they are binary), and any `DECISIONS.md` entries. STOP and request human review before: publishing to any app store, changing anything in §3 or §4, adding any dependency not listed in this manual.

**R0.6 Absolute prohibitions (full list in §14).** NEVER log, print, or toast secret values. NEVER write plaintext secrets to disk, temp files, or OS backup. NEVER send vault data anywhere. NEVER request SMS, contacts, location, or camera permissions (camera MAY be requested only for the TOTP QR scanner, §5.8).

**R0.7 Dependency budget.** Each platform app MUST use ≤ 15 third-party dependencies (test-only dependencies excluded). Every allowed dependency is named in §6–§9. Adding any other dependency requires human review (R0.5).

### §0.7 Milestone map (build in this exact order)

| ID | Milestone | Spec sections needed | Binary acceptance test |
|---|---|---|---|
| M0 | Repo, license, docs skeleton | §0, §12, §13 | Repo has LICENSE (GPL-3.0), README, SECURITY.md, DECISIONS.md, `/spec` folder containing this manual and `templates.json` |
| M1 | Core library (model + crypto + vault file) on your platform | §2, §3, §4 | All conformance vectors in §3.6 pass; round-trip test in §11.2 passes |
| M2 | Unlock flows + Home + Add/Edit for templates T1, T2, T3, T10, T11 | §5.1–§5.5, §10 | Manual script §11.3 steps 1–14 pass |
| M3 | Platform integration: biometric quick-unlock, sync-folder, clipboard hygiene, screen privacy | §5.6, §5.7, platform section | Script §11.3 steps 15–22 pass |
| M4 | Generator + remaining templates T4–T9, T12 + reminders | §2.4, §5.8, §5.10 | Every template creates/saves/reopens; generator passes bank-rule tests §11.4 |
| M5 | TOTP authenticator + import | §5.8, §5.11 | RFC 6238 test vectors pass; Chrome CSV with 100 rows imports losslessly |
| M6 | Autofill / credential integration (platform-specific) | Platform section | Platform autofill test in §11.5 passes |
| M7 | Localization (en + hi minimum) + accessibility | §12, §10.7 | 100% strings externalized; screen reader traverses all screens |
| M8 | Hardening + packaging + release | §11.6, §13 | Security checklist §11.6 all ✅; signed artifact produced |

Build platforms in this order unless instructed otherwise: **Android → iOS → macOS (shares Swift with iOS per §9.1) → Windows.** Each platform is a self-contained deliverable; never block one platform's release on another except for the §11.2 conformance matrix.

---

## §1. PRODUCT SUMMARY (context — why the spec is the way it is)

Indian users distrust cloud password managers with banking credentials, are highly price-sensitive, and live in an OTP-first app ecosystem (Zepto/Blinkit/Swiggy/Zomato log in with phone + OTP, so there is often no password — the vault stores the *identity around* those apps: registered phone, wallet PINs, gift cards, memberships, TOTP, passkeys). One Indian bank relationship produces 8–12 distinct secrets that global apps cannot model. BharatVault wins by (a) modelling India correctly via templates, (b) being structurally unable to see user data (local-first, E2EE, no accounts), and (c) being free because there is nothing to host. The user's chosen **sync folder** (which may live inside Google Drive, iCloud Drive, OneDrive, or any synced directory) carries the encrypted vault between devices; BharatVault itself never talks to any cloud.

**Why fully native (context for lock R0.2 item 8):** the two security-critical surfaces of a password manager — hardware-backed key storage with biometrics, and OS credential/autofill integration (Android AutofillService, iOS/macOS Credential Provider extensions, Windows Hello) — are per-OS native APIs that no cross-platform framework abstracts well. A framework would still force native code exactly where the risk lives, while adding a foreign runtime, binary size, and audit surface. Fully native delivers first-class autofill, the smallest auditable binaries, platform-correct accessibility, and zero framework-vendor risk. The accepted cost — writing the core in three languages — is deliberately contained by keeping §2–§4 small, exact, and conformance-tested.

---

## §2. CANONICAL DATA MODEL (identical on all platforms)

### §2.1 Record

Every stored item is a **Record**, serialized as JSON inside the encrypted vault body:

```json
{
  "uuid": "8-4-4-4-12 lowercase UUIDv4",
  "template_id": "bank_account",
  "title": "SBI Salary Account",
  "institution": "State Bank of India",
  "fields": { "field_id": "string value", "...": "..." },
  "custom_fields": [ {"label": "…", "type": "TEXT|SECRET|DATE|NUMBER", "value": "…"} ],
  "tags": ["work"],
  "favorite": false,
  "created_at": 1751875200000,
  "modified_at": 1751875200000,
  "rev": 1,
  "device_id": "uuid of the device that last wrote this record",
  "reminders": [ {"field_id": "expiry", "days_before": 30} ]
}
```

Rules: all timestamps are Unix **milliseconds** UTC (integer). `rev` increments by 1 on every save. All field values are strings (dates as `YYYY-MM-DD`, month-year as `YYYY-MM`, numbers as digits). Empty fields are omitted from `fields`. Attachments: value of a `FILE` field is the attachment UUID; attachment bytes live in the vault body's `attachments` map (§4.4), each ≤ 2 MB, total vault SHOULD stay < 25 MB.

### §2.2 Field types (enum — implement exactly these)

| Type | Input behavior | Storage |
|---|---|---|
| TEXT | plain text | string |
| SECRET | masked, monospace, reveal button, generator button | string |
| PIN | masked, numeric keypad, 3–8 digits | string |
| NUMBER | numeric keypad | digit string |
| DATE | date picker | `YYYY-MM-DD` |
| MONTHYEAR | month+year picker (card expiry) | `YYYY-MM` |
| PHONE | phone keypad, `+91` default prefix hint | string |
| EMAIL | email keyboard, format check | string |
| URL | url keyboard | string |
| TOTP | stores otpauth secret; renders live 6-digit code + countdown | base32 secret string |
| CARDNUM | numeric, groups of 4 while typing, Luhn check, network badge (RuPay/Visa/MC/Amex) | digit string, no spaces |
| IFSC | uppercase, regex `^[A-Z]{4}0[A-Z0-9]{6}$`; on valid entry auto-fill `bank_name`+`branch` from bundled `ifsc.db` if present, else leave blank | string |
| PICKER:<list> | dropdown from bundled list (`banks`, `brokers`, `insurers`, `card_networks`, `account_types`, `states`, `telecom`, `gas_providers`) + "Other" free text | string |
| LINK:<template_id> | picker of existing records of that template; stores their uuid | uuid string |
| NOTE | multiline text | string |
| FILE | encrypted attachment | attachment uuid |

### §2.3 Sensitivity levels (drive UI behavior — §5.4)

`L` (low: visible normally) · `M` (medium: masked to last 4 by default, tap to reveal) · `H` (high: fully masked; reveal and copy REQUIRE a fresh biometric/PIN authentication; auto-re-mask after 10 s).

### §2.4 `templates.json` — copy this file VERBATIM into the app bundle

Legend: `k`=field_id, `t`=type, `s`=sensitivity, `v`=validation regex (optional), `m`=mask rule (`last4` shows only final 4 chars when masked). Display labels come from string resources keyed `tpl.<template_id>.<field_id>` (§12) — English defaults are the humanized field_id.

```json
{"format":1,"templates":[
{"id":"bank_account","icon":"bank","fields":[
 {"k":"bank_name","t":"PICKER:banks","s":"L"},
 {"k":"account_number","t":"NUMBER","s":"M","m":"last4","v":"^[0-9]{9,18}$"},
 {"k":"account_type","t":"PICKER:account_types","s":"L"},
 {"k":"ifsc","t":"IFSC","s":"L"},
 {"k":"micr","t":"NUMBER","s":"L","v":"^[0-9]{9}$"},
 {"k":"branch","t":"TEXT","s":"L"},
 {"k":"customer_id","t":"TEXT","s":"M"},
 {"k":"netbanking_user_id","t":"TEXT","s":"M"},
 {"k":"login_password","t":"SECRET","s":"H"},
 {"k":"transaction_password","t":"SECRET","s":"H"},
 {"k":"profile_password","t":"SECRET","s":"H"},
 {"k":"tpin","t":"PIN","s":"H"},
 {"k":"registered_mobile","t":"PHONE","s":"L"},
 {"k":"registered_email","t":"EMAIL","s":"L"},
 {"k":"nominee","t":"TEXT","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"card","icon":"card","fields":[
 {"k":"card_number","t":"CARDNUM","s":"M","m":"last4"},
 {"k":"name_on_card","t":"TEXT","s":"L"},
 {"k":"expiry","t":"MONTHYEAR","s":"L"},
 {"k":"cvv","t":"PIN","s":"H","v":"^[0-9]{3,4}$"},
 {"k":"atm_pin","t":"PIN","s":"H","v":"^[0-9]{4,6}$"},
 {"k":"card_type","t":"PICKER:card_kinds","s":"L"},
 {"k":"card_variant","t":"TEXT","s":"L"},
 {"k":"linked_account","t":"LINK:bank_account","s":"L"},
 {"k":"billing_cycle_day","t":"NUMBER","s":"L","v":"^([1-9]|[12][0-9]|3[01])$"},
 {"k":"card_portal_login","t":"TEXT","s":"M"},
 {"k":"card_portal_password","t":"SECRET","s":"H"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"upi","icon":"upi","fields":[
 {"k":"upi_id","t":"TEXT","s":"L","v":"^[\\w.\\-]{2,}@[a-zA-Z]{2,}$"},
 {"k":"linked_account","t":"LINK:bank_account","s":"L"},
 {"k":"upi_pin","t":"PIN","s":"H","v":"^[0-9]{4,6}$"},
 {"k":"apps_used","t":"TEXT","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"demat","icon":"chart","fields":[
 {"k":"broker","t":"PICKER:brokers","s":"L"},
 {"k":"client_id","t":"TEXT","s":"M"},
 {"k":"dp_id","t":"TEXT","s":"M"},
 {"k":"bo_id","t":"TEXT","s":"M"},
 {"k":"login_password","t":"SECRET","s":"H"},
 {"k":"mpin","t":"PIN","s":"H"},
 {"k":"cdsl_tpin","t":"PIN","s":"H"},
 {"k":"totp","t":"TOTP","s":"H"},
 {"k":"api_key","t":"SECRET","s":"H"},
 {"k":"api_secret","t":"SECRET","s":"H"},
 {"k":"mf_folios","t":"NOTE","s":"M"},
 {"k":"nominee","t":"TEXT","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"insurance","icon":"shield","fields":[
 {"k":"insurer","t":"PICKER:insurers","s":"L"},
 {"k":"policy_number","t":"TEXT","s":"M"},
 {"k":"policy_type","t":"PICKER:policy_types","s":"L"},
 {"k":"sum_assured","t":"NUMBER","s":"L"},
 {"k":"premium_amount","t":"NUMBER","s":"L"},
 {"k":"premium_due_date","t":"DATE","s":"L"},
 {"k":"portal_login","t":"TEXT","s":"M"},
 {"k":"portal_password","t":"SECRET","s":"H"},
 {"k":"nominee","t":"TEXT","s":"L"},
 {"k":"agent_contact","t":"PHONE","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"gov_id","icon":"id","fields":[
 {"k":"id_kind","t":"PICKER:gov_id_kinds","s":"L"},
 {"k":"id_number","t":"TEXT","s":"H","m":"last4"},
 {"k":"name_as_per_id","t":"TEXT","s":"L"},
 {"k":"portal_login","t":"TEXT","s":"M"},
 {"k":"portal_password","t":"SECRET","s":"H"},
 {"k":"expiry","t":"DATE","s":"L"},
 {"k":"file_copy","t":"FILE","s":"H"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"epf_pension","icon":"savings","fields":[
 {"k":"scheme","t":"PICKER:pension_schemes","s":"L"},
 {"k":"uan_or_pran","t":"TEXT","s":"M"},
 {"k":"password","t":"SECRET","s":"H"},
 {"k":"ipin","t":"PIN","s":"H"},
 {"k":"tpin","t":"PIN","s":"H"},
 {"k":"linked_mobile","t":"PHONE","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"utility","icon":"bolt","fields":[
 {"k":"utility_kind","t":"PICKER:utility_kinds","s":"L"},
 {"k":"provider","t":"TEXT","s":"L"},
 {"k":"consumer_number","t":"TEXT","s":"M"},
 {"k":"portal_login","t":"TEXT","s":"M"},
 {"k":"portal_password","t":"SECRET","s":"H"},
 {"k":"wifi_password","t":"SECRET","s":"M"},
 {"k":"vehicle_number","t":"TEXT","s":"L"},
 {"k":"fastag_id","t":"TEXT","s":"M"},
 {"k":"due_day","t":"NUMBER","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"telecom","icon":"sim","fields":[
 {"k":"mobile_number","t":"PHONE","s":"L"},
 {"k":"operator","t":"PICKER:telecom","s":"L"},
 {"k":"plan_type","t":"PICKER:plan_types","s":"L"},
 {"k":"account_number","t":"TEXT","s":"M"},
 {"k":"sim_pin","t":"PIN","s":"H"},
 {"k":"puk","t":"PIN","s":"H"},
 {"k":"renewal_date","t":"DATE","s":"L"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"app_profile","icon":"cart","fields":[
 {"k":"app_name","t":"TEXT","s":"L"},
 {"k":"registered_mobile","t":"PHONE","s":"L"},
 {"k":"registered_email","t":"EMAIL","s":"L"},
 {"k":"password_if_any","t":"SECRET","s":"H"},
 {"k":"wallet_pin","t":"PIN","s":"H"},
 {"k":"membership","t":"TEXT","s":"L"},
 {"k":"membership_renewal","t":"DATE","s":"L"},
 {"k":"gift_cards","t":"NOTE","s":"M"},
 {"k":"totp","t":"TOTP","s":"H"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"login","icon":"key","fields":[
 {"k":"website","t":"URL","s":"L"},
 {"k":"username","t":"TEXT","s":"M"},
 {"k":"password","t":"SECRET","s":"H"},
 {"k":"totp","t":"TOTP","s":"H"},
 {"k":"notes","t":"NOTE","s":"L"}]},
{"id":"secure_note","icon":"note","fields":[
 {"k":"body","t":"NOTE","s":"M"},
 {"k":"attachment","t":"FILE","s":"H"}]}
]}
```

Picker lists (`banks`, `brokers`, `insurers`, `card_networks`, `card_kinds` [Debit/Credit/Prepaid/Forex], `account_types` [Savings/Current/Salary/NRE/NRO/FD/RD], `gov_id_kinds` [PAN/Aadhaar/Voter ID/Driving Licence/Passport/DigiLocker/Income Tax/GSTIN/Udyam], `pension_schemes` [EPF/NPS/PPF/APY/Sukanya Samriddhi], `utility_kinds` [Electricity/LPG/Piped Gas/Water/Broadband/DTH/FASTag/Society/Property Tax], `policy_types`, `plan_types`, `telecom`, `states`) ship as `pickers.json` in the bundle; seed the top-50 Indian banks and top-15 brokers/insurers; every picker MUST include an "Other" free-text option. `app_profile` MUST ship quick-add presets for: Zepto, Blinkit, Swiggy, Zomato, Amazon, Flipkart, Myntra, BigBasket, JioMart, Instamart (preset = pre-filled `app_name` + icon).

---

## §3. CRYPTOGRAPHY SPECIFICATION (identical on all platforms — NEVER deviate)

**§3.1 Library.** libsodium only. Bindings: Android → Lazysodium-android; iOS/macOS → swift-sodium (SPM); Windows → Geralt (NuGet). All expose the two primitives below.

**§3.2 Primitives.**
- KDF: `crypto_pwhash` **Argon2id13**, output 32 bytes. Default params: `opslimit=3`, `memlimit=67108864` (64 MiB). At first setup the app MAY raise params if derivation takes < 400 ms on-device, and MUST lower memlimit stepwise (32 MiB floor) if the device cannot allocate 64 MiB. Whatever is used is stored in the header (§4.2) — readers always use header params, never defaults.
- AEAD: `crypto_aead_xchacha20poly1305_ietf` (key 32 B, nonce 24 B random per encryption via the library's CSPRNG, 16 B tag appended to ciphertext).

**§3.3 Key hierarchy.**
```
passphrase ──Argon2id(salt_mk)──► MasterKey (32B, never stored)
RecoveryKey = 16 random bytes, shown once as BVR-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX
             (Crockford base32 of the 16 bytes + 1 checksum char = sum of bytes mod 32)
RecoveryStretched = Argon2id(RecoveryKey_bytes, salt_rk) ──► 32B
VaultKey = 32 random bytes (encrypts everything; never changes on passphrase change)
header stores: wrap_mk = AEAD(VaultKey, key=MasterKey,        aad="BVLT-KEYWRAP")
               wrap_rk = AEAD(VaultKey, key=RecoveryStretched, aad="BVLT-KEYWRAP")
body ciphertext = AEAD(body_plaintext, key=VaultKey, aad = the 60-byte fixed prefix of the file (§4.1))
```
Unlock = derive MasterKey → open `wrap_mk` → get VaultKey → decrypt body. Recovery unlock = same via `wrap_rk`. Passphrase change = re-wrap `wrap_mk` only. AEAD open failure on `wrap_mk` MUST show exactly: "Wrong passphrase" (never a crypto error string).

**§3.4 Biometric quick-unlock (per-platform key store, §6–§9).** Store a copy of MasterKey encrypted by a hardware-backed OS key that requires user authentication. NEVER store the passphrase itself. If OS auth is unavailable/invalidated (biometrics re-enrolled), silently delete the copy and fall back to passphrase.

**§3.5 Memory hygiene.** Zero key byte-arrays after use where the language allows; never place secrets in string-interned types when a byte buffer is possible; lock screens exclude app from OS screenshots/recents per platform section; secrets NEVER appear in logs, exceptions, or crash output.

**§3.6 Conformance vectors (generated with libsodium; every platform's test suite MUST reproduce these exactly).**
```
VECTOR A — Argon2id13, out=32B, ops=3, mem=67108864
 password  = "correct-horse-battery-staple" (UTF-8)
 salt      = 000102030405060708090a0b0c0d0e0f
 derived   = 9acedbbff8ce68cae902e239bdccd32578ccc20eb9054e679f9b8cf5a9f0e19a

VECTOR B — XChaCha20-Poly1305-IETF encrypt
 key       = 000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f
 nonce     = 000102030405060708090a0b0c0d0e0f1011121314151617
 aad       = "BVLT" (ASCII)
 plaintext = "namaste india" (ASCII)
 ct||tag   = f0a3621ee3a6e88e5a2a42a7aa69c591ec4cf4b20dae58d36bcf83fdc8

VECTOR C — key wrap (uses VECTOR A output as MasterKey)
 vault_key = aa × 32 bytes
 nonce     = 24 × 24 bytes (hex 24 repeated)
 aad       = "BVLT-KEYWRAP" (ASCII)
 wrapped   = f68ef02b2231e71a1bf6088d69ecbbc5af74cc70df18e6bc11fb175216a9c21a522d9ddd9b237a446d680e306fd1426b
```
Note: AEAD outputs above store nonce separately; on disk the layout is §4's `nonce || ct||tag`. A reference generator script is in Appendix D — regenerate and extend vectors from it, never by hand.

---

## §4. VAULT FILE FORMAT v1 (`.bvlt` — byte-identical across platforms)

**§4.1 Layout (all integers little-endian):**
```
offset 0   : magic  = ASCII "BVLT" (4 bytes)
offset 4   : format_version = uint16 = 1
offset 6   : reserved = uint16 = 0
offset 8   : vault_uuid = 16 bytes (random, fixed for vault's life)
offset 24  : last_modified_ms = uint64
offset 32  : header_len = uint32 (length of header JSON, ≤ 4096)
offset 36  : body_nonce = 24 bytes
offset 60  : header JSON (UTF-8, exactly header_len bytes)
then       : body ciphertext||tag (AEAD as §3.3; aad = bytes[0..60) of this file)
```

**§4.2 Header JSON (plaintext by design — contains no secrets):**
```json
{"kdf":"argon2id13","ops":3,"mem":67108864,
 "salt_mk":"<16B base64>","salt_rk":"<16B base64>",
 "wrap_mk":{"nonce":"<24B b64>","ct":"<48B b64>"},
 "wrap_rk":{"nonce":"<24B b64>","ct":"<48B b64>"},
 "created_at":0,"writer_device":"<uuid>","body_sha256":"<hex of ciphertext>"}
```

**§4.3 Body plaintext JSON:** `{"format":1,"records":[Record…],"tombstones":[{"uuid":"…","deleted_at":ms}],"attachments":{"<uuid>":{"name":"…","mime":"…","data":"<base64>"}},"meta":{"device_names":{"<device_uuid>":"Rahul's Pixel"}}}`. No compression in v1.

**§4.4 Atomic write (MANDATORY, prevents corruption):** serialize → encrypt → write to `vault.bvlt.tmp` in same directory → flush/fsync → verify by re-reading and AEAD-opening the tmp file → rename over `vault.bvlt`. Before the first write of a session, copy current file to `vault.bvlt.bak`. NEVER write the vault in place.

**§4.5 Sync & merge (no server — the file lives in a user-chosen folder that any cloud client may sync):**
1. On every foreground/unlock and before every save, re-read the file. If `last_modified_ms` differs from the last value this device saw, MERGE before writing.
2. Merge algorithm (record level): union by `uuid`. If a uuid exists on both sides with different `rev`/content → keep the one with newer `modified_at`; if the older side differs in content, ALSO keep it as a new record titled `"<title> (conflict copy)"` with a fresh uuid. NEVER silently discard data. Tombstone beats record when `deleted_at > modified_at`; tombstones purge after 90 days. Attachments: union by uuid.
3. Cloud sync clients may briefly present two files (`vault.bvlt` and a "conflicted copy"); on unlock, if any sibling file matching `vault*.bvlt` exists with the same `vault_uuid`, merge it in and delete it.
4. LAN device-to-device sync is OUT OF SCOPE for v1 (record in DECISIONS.md if requested).

---

## §5. APP BEHAVIOR SPECIFICATION (identical on all platforms)

### §5.1 Screens (implement all; navigation flow S1→S2→S3→S4→S5→S6 on first run, S13→S6 thereafter)

| ID | Screen | Contents / rules |
|---|---|---|
| S1 | Language picker | Grid of languages in their own script (English, हिन्दी, বাংলা, తెలుగు, मराठी, தமிழ், ગુજરાતી, ಕನ್ನಡ, മലയാളം, ଓଡ଼ିଆ, ਪੰਜਾਬੀ). v1 ships en+hi complete; others appear when ≥95% translated |
| S2 | Trust explainer | 3 swipe cards: "Your data stays on this device", "We have no servers — nothing to hack, nothing to sell", "Free forever, open source". Diagram: phone→lock→(no cloud). One button: Continue |
| S3 | Create passphrase | Passphrase field + strength meter (zxcvbn-style: length≥10 required) OR "Use a 6-digit PIN" option (PIN allowed ONLY if device has hardware-backed keystore + biometrics — else force passphrase; explain why in one line) |
| S4 | Recovery Kit | Shows RecoveryKey once, formatted `BVR-…`; buttons: "Save as PDF" (A4, key as text+QR, instructions "keep with your property papers", localized) and "I have written it down" (must check a confirm box). NEVER show this key again after this screen; Settings offers "Generate NEW recovery key" (re-wraps `wrap_rk`) |
| S5 | Quick unlock opt-in | Enable fingerprint/Face ID/Windows Hello/Touch ID (§3.4). Skippable |
| S6 | Home | Records grouped by `institution` (fallback: template group), section headers with bank logos/icons; search bar on top; FAB/toolbar "+"; tabs: Home · Authenticator · Settings |
| S7 | Search | Live filter over title, institution, tags, username-class fields; transliteration table maps Latin→Devanagari common terms (`bijli→बिजली`, `bank/बैंक`, `paisa`, `gas`, `pani`) so Hinglish queries hit Hindi-titled records; NEVER index SECRET/PIN/H values |
| S8 | Template gallery ("+") | Grid of the 12 templates + `app_profile` quick-add presets row (Zepto, Blinkit, Swiggy, Zomato…) |
| S9 | Record detail | Fields in template order; each row: label, masked/visible value per §2.3, copy icon, reveal icon; TOTP rows show live code + ring countdown; LINK rows navigate; header shows edit/delete/favorite |
| S10 | Record edit | Typed inputs per §2.2; SECRET fields show generator button (§5.8); validations inline; save increments `rev` |
| S11 | Authenticator tab | List of all TOTP fields across the vault: title, live code, countdown; tap = copy; "Add via QR" opens camera scanner for `otpauth://` (the ONLY camera use permitted) |
| S12 | Settings | Auto-lock timing, quick-unlock toggle, change passphrase, new recovery kit, sync folder chooser, import, export, language, About (version, license, source link, UPI donation QR + GitHub Sponsors link), Security info page |
| S13 | Lock screen | Passphrase/PIN entry + biometric prompt; 5 consecutive failures → 30 s cooldown, doubling; NEVER wipe data on failures |
| S14 | Import | Chrome/Google CSV, Bitwarden JSON/CSV, LastPass CSV, KeePass CSV → mapped to `login` records (§5.11), preview count before commit |

### §5.2 Locking
Vault auto-locks (VaultKey + MasterKey zeroed from memory): after N minutes in background (default 1; options 0/1/5/15), on device lock where detectable, and on user tap of the lock icon. Cold start always begins at S13.

### §5.3 Clipboard hygiene
Copying any field with sensitivity M or H MUST auto-clear the clipboard after 30 s (platform mechanism in §6–§9) and show "Copied · clears in 30s". Where the OS supports it, mark the clip "sensitive" so it is excluded from clipboard history/sync.

### §5.4 Masking & reveal
Per §2.3. H-field reveal/copy triggers OS biometric/credential prompt if quick-unlock is enabled; else re-entry of PIN/passphrase, at most once per 60 s (grace window).

### §5.5 Screen privacy
Vault screens MUST be excluded from OS screenshots/recents where the platform allows (FLAG_SECURE on Android; obscured snapshot on iOS/macOS app switcher; `IsScreenCaptureEnabled=false` on Windows). Setting "Allow screenshots" (default OFF) may disable this, with a warning.

### §5.6 Sync folder
Settings → "Choose sync folder": OS folder picker; persist durable access (SAF persisted permission / security-scoped bookmark / saved path). The app reads/writes ONLY `vault.bvlt`, `vault.bvlt.tmp`, `vault.bvlt.bak`, and conflict siblings inside that folder. If no folder chosen, vault lives in app-private storage and Settings shows a "Not backed up" badge. First-run S5 footer offers "Set up backup folder now".

### §5.7 Reminders
Local notifications only. For any DATE field with a reminder (`expiry`, `premium_due_date`, `membership_renewal`, `renewal_date` auto-suggest one at 30 days), schedule via the platform scheduler. Notification text NEVER contains secret values — format: "SBI credit card expires this month · open BharatVault".

### §5.8 Generator & TOTP
Generator: length slider 8–64 (default 16), toggles for upper/lower/digits/symbols, "PIN mode" (digits only, 4/6), and a **bank preset dropdown** loaded from bundled `bank_rules.json`:
`[{"bank":"SBI","max_len":20,"min_len":8,"symbols_allowed":"@#$%","require_symbol":true,"require_digit":true}, …]` (seed 10 banks; community maintains). Random source = libsodium `randombytes`. TOTP: RFC 6238, HMAC-SHA1, 6 digits, 30 s (honor `algorithm/digits/period` params from `otpauth://` URIs); MUST pass RFC 6238 Appendix B test vectors (SHA-1 rows).

### §5.9 Breach check (optional, OFF by default)
Only network feature. Toggle in Settings with plain-language explanation: sends the FIRST 5 HEX CHARS of the SHA-1 of a password to `https://api.pwnedpasswords.com/range/<prefix>` (k-anonymity), compares suffixes locally. NEVER send more; NEVER run without the toggle on.

### §5.10 Field-level extras
CARDNUM: Luhn validate; detect network by prefix table (RuPay 60/65/81/82/508, Visa 4, Mastercard 51–55/2221–2720, Amex 34/37, Diners 300–305/36/38, Maestro 50/56–58) — show badge, warn (don't block) on Luhn fail. IFSC: bundle `ifsc.db` (SQLite built from Razorpay's MIT-licensed open dataset by the Appendix E script) — offline lookup < 100 ms; app ships fine without the db (lookup silently disabled).

### §5.11 Import mapping (Chrome CSV → `login`)
Columns `name,url,username,password,note` → `title,website,username,password,notes`. Show "N items ready to import" preview; duplicates (same url+username) update instead of duplicate. Bitwarden/LastPass/KeePass CSVs map their equivalent columns; unmapped columns append to `notes` as `key: value` lines. Export: encrypted `.bvlt` copy (default) or plain CSV behind a red two-step warning.

---

## §6. ANDROID — native implementation

**§6.1 Stack (locked):** Kotlin 2.x · Jetpack Compose + Material 3 · minSdk 26, target latest stable · Gradle Kotlin DSL. Modules: `:app` (UI, platform glue) and `:core` (pure Kotlin/JVM: model, crypto wrapper, vault file, merge — NO Android imports; this module runs the §3.6/§11.2 tests on JVM). `:core` is plain Kotlin for testability only — it is NOT a Kotlin Multiplatform module, MUST NOT gain KMP targets, and MUST NOT be consumed by any non-Android platform (R0.2 item 8).

**§6.2 Allowed dependencies (complete list):** `com.goterl:lazysodium-android` (+`net.java.dev.jna:jna@aar`), `androidx.compose.*` BOM, `androidx.biometric:biometric`, `androidx.security:security-crypto` (only for prefs, not vault), `androidx.documentfile`, `androidx.navigation:navigation-compose`, `kotlinx-serialization-json`, `androidx.camera:*` + `com.google.zxing:core` (TOTP QR only), `androidx.work:work-runtime-ktx` (reminders). Nothing else without R0.5 review.

**§6.3 Project setup:** Android Studio → New Project → "Empty Activity (Compose)" → package `org.bharatvault.app` → add `:core` module (Kotlin JVM library) → copy `/spec/templates.json`, `pickers.json`, `bank_rules.json` into `app/src/main/assets/`.

**§6.4 Crypto wiring (`:core` calls through an interface; `:app` provides Lazysodium impl):**
```kotlin
val ls = LazySodiumAndroid(SodiumAndroid())
// Argon2id: ls.cryptoPwHash(out32, pass, salt, 3L, 67108864L, PwHash.Alg.PWHASH_ALG_ARGON2ID13)
// AEAD:     ls.cryptoAeadXChaCha20Poly1305IetfEncrypt(...) / ...Decrypt(...)
```
First run: benchmark KDF, persist chosen params, generate `device_id` UUID into EncryptedSharedPreferences.

**§6.5 Biometric quick-unlock:** generate AES-256-GCM key in AndroidKeystore with `setUserAuthenticationRequired(true)`, `setInvalidatedByBiometricEnrollment(true)`, `setUserAuthenticationParameters(0, AUTH_BIOMETRIC_STRONG)`; encrypt MasterKey with it; unlock via `BiometricPrompt` + `CryptoObject`. `KeyPermanentlyInvalidatedException` → delete blob, fall back to passphrase (no error dialog beyond "Please unlock with your passphrase once").

**§6.6 Platform behaviors:** FLAG_SECURE on every activity by default (§5.5). Clipboard: `ClipDescription.EXTRA_IS_SENSITIVE=true`; clear after 30 s via foreground timer + WorkManager fallback (OEM background-kill safe). Sync folder: `ACTION_OPEN_DOCUMENT_TREE` + `takePersistableUriPermission`; all vault IO through DocumentFile with the §4.4 tmp/rename dance (SAF rename via `DocumentsContract.renameDocument`). Reminders: WorkManager periodic daily check → local notifications. `android:allowBackup="false"`.

**§6.7 Autofill (M6):** implement `AutofillService`. `onFillRequest`: read structure → collect package name / web domain hints (`autofillHints`, HTML attributes) → match `login` records by domain and `app_profile`/`login` by package via a bundled `app_map.json` (community file: packageName→record-matching hints) → return datasets (inline presentation on API 30+) requiring authentication → auth activity unlocks vault → fill. `onSaveRequest`: offer to save new credentials as `login`. **Copy-mode fallback** (many Indian bank apps block autofill): from S9, "Login helper" posts a notification with buttons User ID → Password → Transaction password; each tap copies the next value (30 s clear). NEVER use AccessibilityService in the Play build. Manifest: service with `BIND_AUTOFILL_SERVICE` permission + xml config; no dangerous permissions requested anywhere except CAMERA (TOTP scan, runtime, optional).

**§6.8 Release (M8):** `./gradlew :app:bundleRelease` + APK for GitHub/F-Droid; keystore generated and stored offline; publish signing cert SHA-256 in README. F-Droid: submit metadata (app builds from source, no proprietary deps — Lazysodium/JNA are FLOSS ✅). Play: one-time $25 account; Data safety form = "No data collected / No data shared"; declare AutofillService use. Reproducible-build flags: fixed `versionCode`, no build timestamps.

---

## §7. iOS — native implementation

**§7.1 Stack (locked):** Swift 5.10 · SwiftUI · iOS 16+ · Xcode 15+. Targets: `BharatVault` (app), `BharatVaultCore` (Swift package: model/crypto/vault/merge, NO UIKit — an independent Swift implementation of §2–§4, shared ONLY with the macOS target per §9.1, which is native-within-Apple and explicitly permitted by R0.2 item 8; never shared with Android or Windows), `AutoFillExtension` (Credential Provider). Dependencies (complete list): `swift-sodium` (SPM), `CodeScanner` or native `AVFoundation`+`Vision` for QR (prefer native = zero dep). Serialization via `Codable` (built-in).

**§7.2 Crypto wiring:**
```swift
let sodium = Sodium()
// Argon2id: sodium.pwHash.hash(outputLength: 32, passwd: pass, salt: salt,
//           opsLimit: 3, memLimit: 67108864, alg: .Argon2ID13)
// AEAD:     sodium.aead.xchacha20poly1305ietf.encrypt/decrypt(..., additionalData:)
```
Run §3.6 vectors in `BharatVaultCoreTests` (M1).

**§7.3 Quick unlock:** store MasterKey in Keychain with `SecAccessControlCreateWithFlags(..., kSecAttrAccessibleWhenPasscodeSetThisDeviceOnly, [.biometryCurrentSet])`; read triggers Face ID/Touch ID automatically; `errSecItemNotFound` after biometric re-enrolment → fall back to passphrase and delete item. `LAContext` preflight for availability.

**§7.4 Platform behaviors:** app-switcher privacy: on `scenePhase != .active` overlay an opaque logo view (secrets never in snapshot). Clipboard: `UIPasteboard.general.setItems([[UTType.utf8PlainText.identifier: value]], options: [.localOnly: true, .expirationDate: Date().addingTimeInterval(30)])` — native 30 s expiry, no Handoff. Sync folder: `UIDocumentPickerViewController(forOpeningContentTypes: [.folder])` → security-scoped bookmark persisted; ALL file IO wrapped in `NSFileCoordinator` (mandatory for iCloud Drive correctness) + §4.4 tmp/rename via `FileManager.replaceItemAt`. Reminders: `UNUserNotificationCenter` calendar triggers. Register UTI/document type for `.bvlt` so "Open in BharatVault" works.

**§7.5 AutoFill Credential Provider (M6):** extension of class `ASCredentialProviderViewController`. Implement: `prepareCredentialList(for:)` (show matching `login` records for the requested `ASCredentialServiceIdentifier` domain), `provideCredentialWithoutUserInteraction(for:)` (fail with `userInteractionRequired` when locked), and credential identity sync: after vault changes, write `ASPasswordCredentialIdentity` entries (domain, username, recordUUID) to `ASCredentialIdentityStore` — identities only, NEVER passwords. Vault access from the extension: app + extension share an App Group; the encrypted vault path/bookmark lives in the group container; unlock inside the extension via the same Keychain item (`kSecAttrAccessGroup` shared). iOS 17+: MAY also register as passkey provider (`ASCredentialProviderViewController` passkey registration/assertion callbacks) — mark Phase 2 if time-boxed.

**§7.6 Release:** requires Apple Developer Program (**US$99/year** — see §13 honesty ledger). Capabilities: App Groups, AutoFill Credential Provider, Keychain Sharing. Privacy Nutrition Label: "Data Not Collected". TestFlight beta → App Store. Export compliance: uses standard encryption exemption categories — answer the encryption questions accurately (uses encryption: yes; exempt: yes, standard algorithms).


---

## §8. WINDOWS — native implementation

**§8.1 Stack (locked):** C# / .NET 8 · **WinUI 3** (Windows App SDK, packaged MSIX) · Windows 10 1809+. Projects: `BharatVault.Core` (net8 class library: model/crypto/vault/merge — no UI refs; an **independent C# implementation** of §2–§4, NOT a port, binding, or transpilation of the Kotlin or Swift cores; runs §3.6/§11.2 tests via xUnit) and `BharatVault.App` (WinUI 3). Dependencies (complete list): `Geralt` (libsodium binding — Argon2id + XChaCha20-Poly1305), `Microsoft.WindowsAppSDK`, `CommunityToolkit.Mvvm`, `System.Text.Json` (built-in). QR scan not needed on desktop (TOTP added by pasting the secret/URI).

**§8.2 Setup:** Visual Studio 2022 → workload ".NET Desktop Development" + "Windows App SDK C# Templates" → New Project → "Blank App, Packaged (WinUI 3 in Desktop)" → add Core library → copy `/spec/*.json` to `Assets/`.

**§8.3 Crypto wiring (Geralt):**
```csharp
Span<byte> key = stackalloc byte[32];
Argon2id.DeriveKey(key, passphraseBytes, salt, iterations: 3, memorySize: 67108864);
XChaCha20Poly1305.Encrypt(ciphertext, plaintext, nonce, key, associatedData);
```
CryptographicOperations.ZeroMemory on all key buffers (§3.5).

**§8.4 Quick unlock (Windows Hello):** gate = `UserConsentVerifier.RequestVerificationAsync("Unlock BharatVault")`; storage = MasterKey wrapped with DPAPI `ProtectedData.Protect(masterKey, entropy, DataProtectionScope.CurrentUser)` where `entropy` = 32 random bytes kept in the app's local settings. Honest limitation (document in Security page): DPAPI binds to the Windows user account, not to the biometric itself; the passphrase remains the root secret. Prefer `KeyCredentialManager` (Hello-backed key signs a challenge that becomes the wrap entropy) when available — implement if straightforward, else record in DECISIONS.md.

**§8.5 Platform behaviors:** window privacy: on lock/minimize show opaque lock view; call `AppWindow.IsShownInSwitchers` normally but exclude content via lock overlay; set `ApplicationView`/`GraphicsCapture` protections where API available. Clipboard: `Clipboard.SetContentWithOptions` with `IsAllowedInHistory=false, IsRoamable=false`; 30 s `DispatcherQueueTimer` then `Clipboard.Clear()` (only if still holding our content). Sync folder: `FolderPicker` → save with `StorageApplicationPermissions.FutureAccessList`; watch with `FileSystemWatcher` for external changes → trigger §4.5 merge; atomic write via `File.Replace`. Reminders: `AppNotificationBuilder` toasts scheduled via Task Scheduler-free in-app scheduler + startup task (MAY defer to Phase 2; record decision). Autofill equivalent: global hotkey (Ctrl+Alt+B) opens quick-search palette → Enter types nothing, COPIES selected field (30 s clear). NEVER implement keystroke injection into other apps in v1. Browser extension = separate Phase-3 project, out of scope here.

**§8.6 Release:** MSIX signed package. Free path: GitHub Releases (users enable sideloading; ship a one-line install script) + **winget** manifest PR to `microsoft/winget-pkgs` (free). Optional: Microsoft Store (one-time ~US$19 individual fee) — Store signs the package, removing the self-signing pain; recommended. Publish SHA-256 of artifacts in release notes.

---

## §9. macOS — native implementation (shares code with iOS)

**§9.1 Stack:** same Xcode workspace as §7. Add a macOS 13+ SwiftUI target consuming `BharatVaultCore` unchanged. This iOS↔macOS Swift sharing is native-within-Apple (one vendor, one toolchain, native UI on both OSes) and is the ONLY cross-target code sharing permitted by R0.2 item 8 — it does not violate the fully-native mandate. Platform-specific code isolated behind `#if os(macOS)`. Expected reuse ≥ 80% (models, crypto, vault, viewmodels, most SwiftUI views with adaptive layout).

**§9.2 Differences from iOS:**
- Quick unlock: same Keychain + `.biometryCurrentSet` (Touch ID) with `LAContext`; fall back to device password prompt on non-Touch-ID Macs.
- Sync folder: `NSOpenPanel` (canChooseDirectories) → security-scoped bookmark (App Sandbox ON, entitlement `com.apple.security.files.user-selected.read-write`); keep `NSFileCoordinator` wrapping.
- Window privacy: `NSWindow.sharingType = .none` on vault windows (blocks screen capture); lock overlay on `resignActive`.
- Clipboard: `NSPasteboard.general` + `org.nspasteboard.ConcealedType` marker; 30 s timer clear with changeCount check.
- Menu bar: standard app menu + ⌘F search, ⌘L lock, ⌘N new record. Layout: `NavigationSplitView` (sidebar = institutions, detail = record).
- AutoFill: macOS Safari/system AutoFill via the same Credential Provider extension type on macOS 14+ (`ASCredentialProviderViewController` is available) — implement if the shared extension compiles for the target; else ship the ⌘-palette copy flow like §8.5 and record decision.
- Reminders: `UNUserNotificationCenter` (same API as iOS).

**§9.3 Release:** Developer ID signing + **notarization** required for distribution outside the Mac App Store (covered by the same US$99/yr Apple membership as iOS). Free channels: notarized `.dmg` on GitHub Releases + **Homebrew cask** (free PR to `homebrew/homebrew-cask`). Mac App Store optional.

---

## §10. UI / DESIGN SPECIFICATION (all platforms — native components, shared tokens)

**§10.1 Tokens.** Primary `#1A6B54` (deep green — trust/finance, distinct from every incumbent), on-primary `#FFFFFF`, accent `#E8A317` (marigold), error `#B3261E`, surface light `#FAFAF7`, surface dark `#0F1412` (true-black variant `#000000` optional), success `#2E7D32`. Corner radius 12; touch targets ≥ 48dp/44pt; type scale: display 28/semibold, title 20/semibold, body 16/regular, caption 13; monospace (platform default mono) for all SECRET/PIN/CARDNUM/TOTP values. Dark mode mandatory, follows system.
**§10.2 Iconography.** Simple line icons per template (`bank, card, upi, chart, shield, id, savings, bolt, sim, cart, key, note`); institution rows show the bank/broker logo if bundled, else a colored monogram circle derived from the name hash.
**§10.3 Layout rules.** Phone: bottom tab bar (Home/Authenticator/Settings), FAB "+", content reachable one-handed (primary actions in lower half). Desktop/tablet: split view sidebar+detail; ⌘/Ctrl-F focuses search.
**§10.4 Field row anatomy (S9).** `[label caption] / [value: masked●●●● or text] / [copy icon] [eye icon]`; H fields show a small shield glyph; tapping value = copy (same as icon); reveal auto-re-masks after 10 s; TOTP row shows `123 456` split with progress ring.
**§10.5 Empty states teach.** Empty Home shows two non-interactive sample cards ("This is how your bank account will look") + one scam-awareness card ("Banks NEVER ask for your OTP. Anyone who does is a fraudster.") — rotating set of 6 localized safety tips.
**§10.6 Tone.** Plain, warm, zero jargon. Never say "encrypt/AES/zero-knowledge" in primary UI — say "Only you can open this. Not even we can." Hindi copy written natively, not machine-translated (Weblate reviewers gate this).
**§10.7 Accessibility.** Full screen-reader labels (mask state announced as "hidden"), dynamic type up to 200% without truncation of labels, contrast ≥ 4.5:1, no color-only meaning.

---

## §11. TESTING & CONFORMANCE (binary gates)

**§11.1 Unit (per platform core module):** §3.6 vectors A/B/C byte-exact; Luhn table (10 valid, 10 invalid card numbers); IFSC regex; TOTP RFC 6238 SHA-1 vectors; RecoveryKey encode/decode round-trip incl. checksum; merge algorithm cases (both-modified, delete-vs-edit, conflict copy, tombstone purge).
**§11.2 Vault round-trip:** create vault with 3 records + 1 attachment → save → reopen with passphrase → byte-compare records; reopen with RecoveryKey; corrupt one ciphertext byte → open MUST fail cleanly ("File damaged — restored from backup" using `.bak`); cross-platform gate (the contract that replaces shared code): each core commits a fixture vault it created — `fixtures/vault_android.bvlt`, `fixtures/vault_apple.bvlt`, `fixtures/vault_windows.bvlt` (all passphrase `test-vault-1234`, all containing the same 3 sample records + 1 attachment) — and CI proves the full matrix: **every platform opens, edits, saves, and re-opens every other platform's fixture with zero data loss** (iOS and macOS share one fixture since they share a core). Any red cell in the matrix blocks release on ALL platforms.
**§11.3 Manual UI script (22 steps, run on a 2 GB-RAM Android device and on each desktop OS):** 1 install→language→trust cards, 2 set passphrase, 3 save recovery PDF, 4 enable biometric, 5 add bank_account with all 16 fields, 6 IFSC autofill fires, 7 add card — Luhn badge shows RuPay for a 60-prefix number, 8 add upi linked to the bank, 9 H-field reveal demands biometric, 10 copy clears at 30 s, 11 screenshot blocked on vault screen, 12 background 1 min → app relocks, 13 search "sbi" and Hinglish "bijli" (after adding a utility), 14 kill+relaunch → data intact, 15 choose sync folder → vault.bvlt appears, 16 edit on device B (or second install) → device A merges without loss, 17 conflict copy created when both edited offline, 18 wrong passphrase ×5 → cooldown, 19 recovery-key unlock works, 20 change passphrase → old fails/new works/recovery still works, 21 import 100-row Chrome CSV, 22 delete record → gone after sync on both.
**§11.4 Generator:** for each entry in `bank_rules.json`, 1,000 generated passwords all satisfy the rule; chi-square sanity on character distribution.
**§11.5 Autofill:** Android — fills a test WebView login + a native test app; save-prompt captures new creds; copy-mode notification sequence works on a MIUI/HyperOS device. iOS — QuickType bar shows identity on a Safari test page; extension refuses when locked. Windows/macOS — palette/extension flow per §8.5/§9.2.
**§11.6 Security checklist (all must be ✅ before any release):** no secret in any log (grep release logs), `allowBackup=false` / equivalent, screenshots blocked by default, clipboard clears, no network calls with breach-toggle OFF (verify with a proxy: **zero** requests), dependencies match §6–§9 lists exactly, licenses compatible with GPL-3.0, release binary reproducible or checksummed + signed, threat model (§15) published, SECURITY.md contact live.

---

## §12. LOCALIZATION

All user-visible strings in platform resource files (`strings.xml` / `Localizable.xcstrings` / `.resw`), keyed identically across platforms: `tpl.<template>.<field>`, `scr.<screen>.<element>`, `msg.<id>`. Never concatenate translated fragments; use placeholders (`%1$s`). Numbers/dates via locale formatters, but field storage formats stay as §2.1. v1 ships **en + hi** at 100%; push all keys to **Weblate** (free libre hosting) for community translation of the other 9 languages; a language becomes selectable at ≥ 95%. Hindi glossary is part of the repo (`/l10n/glossary_hi.md`) so translators keep terms consistent (e.g., passphrase = "मुख्य पासवर्ड").

---

## §13. RELEASE, DISTRIBUTION & HONEST COST LEDGER

| Channel | Cost | Notes |
|---|---|---|
| GitHub repo, Actions CI, Releases, Pages site | ₹0 | Public repo; CI runs §11.1–11.2 on every PR |
| F-Droid (Android) | ₹0 | Builds from source; the trust channel |
| Google Play | US$25 once | Data safety = "No data collected" |
| Apple App Store + macOS notarization | **US$99 / year** | One membership covers iOS + macOS. This is the single recurring cost the 4-platform scope adds — unavoidable for iOS distribution. Mitigation: it is grant-fundable (§15) |
| winget (Windows) | ₹0 | Manifest PR |
| Microsoft Store | ~US$19 once | Optional; solves MSIX signing |
| Homebrew cask (macOS) | ₹0 | PR after notarized DMG exists |
| Trademark (India, class 9/42) | ~₹4.5–9k once | Strongly recommended — blocks scam clones |
| Servers, databases, analytics | ₹0 forever | Structurally none |

Desktop apps MAY include a manual "Check for updates" button that fetches only the GitHub Releases *latest version tag* (no auto-download, no background checks) — the second and last permitted network call. Version scheme `MAJOR.MINOR.PATCH`, git-tagged, changelog per release, all artifacts checksummed (SHA-256 in release notes) and signed.

---

## §14. ABSOLUTE PROHIBITIONS (final gate — scan the codebase against each)

NEVER: implement crypto primitives by hand or change §3 parameters silently · store or transmit the passphrase, MasterKey, VaultKey, or RecoveryKey (outside the wrapped forms in §4.2 and the OS keystores in §3.4) · write plaintext secrets to disk, temp files, logs, analytics, crash reports, notifications, or OS backups · add analytics/telemetry/ads/tracking SDKs · make network calls except §5.9 and §13 update-tag check · request SMS, contacts, call-log, or location permissions · read the user's SMS for OTPs · use Android AccessibilityService for autofill in store builds · introduce Flutter, React Native, Kotlin Multiplatform, Compose Multiplatform, .NET MAUI, Electron, Tauri, or any cross-platform UI framework, shared runtime, or WebView-based UI (R0.2 item 8 / ADR-001) · auto-wipe the vault on failed unlocks · show the RecoveryKey after S4 · index SECRET/PIN/H values in search · exceed the dependency lists · ship a store build whose Data-safety/Privacy label says anything other than "no data collected".

---

## §15. GOVERNANCE, SECURITY PROGRAM & SUSTAINABILITY (repo-level)

Repo root MUST contain: `LICENSE` (GPL-3.0), `README.md` (what/why/screenshots/official-download links + signing fingerprints), `SECURITY.md` (coordinated disclosure, contact, 90-day window), `CONTRIBUTING.md` (DCO sign-off, code style, how to add a bank to `bank_rules.json`/`pickers.json` without touching app code), `THREAT_MODEL.md` (in scope: stolen device, cloud-account compromise, phishing, malicious fork; out of scope: compromised/rooted OS, targeted state actors — say so honestly), `DECISIONS.md`, `/spec` (this manual + JSON resources + fixtures). Two release-key holders by the second release. Funding (never gates features): UPI QR + GitHub Sponsors in About; apply to Zerodha **FLOSS/fund**, **FOSS United** grants, NLnet — the US$99 Apple fee and a professional audit are exactly what these grants cover. Optional future Teams/SMB tier may fund the project; the individual app stays free forever.

---

## APPENDIX A — Glossary of Indian terms (for implementers unfamiliar with India)

IFSC: 11-char code identifying a bank branch for transfers (e.g., SBIN0001234). MICR: 9-digit cheque-routing code. Customer ID / CIF / CRN: bank's internal customer number, needed for net-banking login. Transaction password: a SECOND password some Indian banks require to confirm money movement, distinct from the login password. Profile password: a THIRD password some banks (e.g., HDFC) use for profile changes. UPI: India's instant payment system; a UPI ID looks like `name@okhdfcbank`; the UPI PIN belongs to the bank account, not the app. MPIN: mobile-banking PIN. TPIN: telephone-banking PIN, and separately CDSL's TPIN authorizes selling shares. Demat: electronic share-holding account; DP ID + Client ID form the 16-digit BO ID. UAN: permanent Employee Provident Fund number. PRAN: pension (NPS) account number. PAN: tax ID (ABCDE1234F). Aadhaar: 12-digit national ID — store locally only, mask by default, never transmit. FASTag: electronic toll tag linked to a vehicle and a payment source. LPG consumer number: cooking-gas connection ID with a distributor. RuPay: India's domestic card network. Zepto/Blinkit/Swiggy/Zomato: quick-commerce & food apps that log in via phone + OTP (no password) — hence the `app_profile` template.

## APPENDIX B — Chrome CSV fixture (commit as `fixtures/chrome_sample.csv`)
```
name,url,username,password,note
SBI NetBanking,https://retail.onlinesbi.sbi,rahul123,S@mplePass1,salary account
Zomato,https://www.zomato.com,rahul@example.com,,phone login only
```

## APPENDIX C — RecoveryKey encoding
16 random bytes → Crockford Base32 (no padding) → 26 chars → append 1 checksum char = Crockford digit of (sum of the 16 raw bytes mod 32) → group as `BVR-XXXXX-XXXXX-XXXXX-XXXXX-XXXXX-XX`. Decoder strips `BVR-` and hyphens, is case-insensitive, maps I→1, L→1, O→0, verifies checksum before attempting §3.3 unwrap.

## APPENDIX D — Conformance-vector generator (reference implementation; commit as `spec/gen_vectors.py`)
```python
# pip install pynacl
import nacl.pwhash, nacl.bindings, binascii
hx = lambda b: binascii.hexlify(b).decode()
key = nacl.pwhash.argon2id.kdf(32, b"correct-horse-battery-staple",
        bytes(range(16)), opslimit=3, memlimit=67108864)
assert hx(key) == "9acedbbff8ce68cae902e239bdccd32578ccc20eb9054e679f9b8cf5a9f0e19a"
ct = nacl.bindings.crypto_aead_xchacha20poly1305_ietf_encrypt(
        b"namaste india", b"BVLT", bytes(range(24)), bytes(range(32)))
assert hx(ct) == "f0a3621ee3a6e88e5a2a42a7aa69c591ec4cf4b20dae58d36bcf83fdc8"
wrapped = nacl.bindings.crypto_aead_xchacha20poly1305_ietf_encrypt(
        b"\xaa"*32, b"BVLT-KEYWRAP", b"\x24"*24, key)
assert hx(wrapped) == ("f68ef02b2231e71a1bf6088d69ecbbc5af74cc70df18e6bc11fb1752"
                       "16a9c21a522d9ddd9b237a446d680e306fd1426b")
print("all vectors OK — extend this file to add fixture vaults")
```

## APPENDIX E — IFSC database build (commit as `spec/build_ifsc.py`)
Download Razorpay's MIT-licensed IFSC dataset release (github.com/razorpay/ifsc, `IFSC.csv`), load into SQLite table `ifsc(code TEXT PRIMARY KEY, bank TEXT, branch TEXT, city TEXT, state TEXT)`, create index, vacuum, ship as `assets/ifsc.db` (~2–3 MB compressed). Refresh each app release. The app MUST work fully if this file is absent.

## APPENDIX F — Definition of Done, whole project
☐ M0–M8 checkpoints committed with passing gates ☐ §11.2 fixture matrix fully green — every platform opens, edits, and round-trips every other platform's fixture vault ☐ §11.6 checklist ✅ on every platform ☐ APK ≤ 15 MB, cold start ≤ 2 s on a 2 GB device ☐ Windows/macOS binaries ≤ 60 MB ☐ en+hi 100% ☐ store privacy labels = no data collected ☐ README lists official channels + signing fingerprints ☐ v1.0.0 tagged.

## APPENDIX G — ADR-001: Fully native implementation per platform (ACCEPTED · LOCKED)

**Status:** Accepted 2026-07-08 by the project owner. Locked via R0.2 item 8. No implementer — human or AI — may reverse this; changing it requires the owner's written sign-off recorded here.

**Context:** BharatVault must ship on Android, iOS, Windows, and macOS as a security product whose value depends on hardware-backed key storage and biometrics, deep OS credential/autofill integration, small auditable binaries, and user trust.

**Decision:** Build each platform with its own native language and first-party UI toolkit: Kotlin + Jetpack Compose (Android); Swift + SwiftUI (iOS and macOS, sharing Swift only between these two Apple targets); C# + WinUI 3 (Windows). The core (§2–§4) is implemented independently in Kotlin, Swift, and C#. Interoperability is enforced by the byte-level vault format (§4), the libsodium conformance vectors (§3.6), and the release-blocking fixture matrix (§11.2) — the specification, not shared code, is the contract.

**Alternatives considered and rejected:**
- **Flutter (Dart):** one codebase, but autofill/credential-provider extensions, Keystore/Keychain/Hello key handling, and passkey provider APIs still require per-OS native code — exactly where the security risk lives — while adding the Dart runtime, canvas-rendered (non-native) UI, larger binaries, and a bigger audit surface.
- **Kotlin Multiplatform shared core:** shares the crypto/model layer, but adds Kotlin/Native↔Swift and JVM↔.NET FFI and toolchain complexity, entangles every platform's reproducible build and security audit with a foreign toolchain, and still shares none of the UI or OS-integration code.
- **React Native:** JavaScript runtime and bridge are unsuitable for secret handling and the memory-hygiene requirements of §3.5; same native-extension burden as Flutter.
- **Electron / Tauri (desktop):** a Chromium/WebView surface is an unacceptable footprint and attack/audit surface for a credential vault; violates the size and trust goals outright.

**Consequences (accepted costs):** the core exists in three languages, so every change to §2–§4 must land on all platforms in the same release train; the conformance suite becomes release-blocking and is maintained as a first-class artifact; contributors need per-platform expertise (§8 of the operation manual's skills matrix). **Benefits:** first-class autofill and biometrics on every OS, smallest binaries (≤ 15 MB Android APK), native accessibility, per-platform security audits with no cross-toolchain dependencies, and zero framework-vendor risk for a project meant to live for decades.

**Revisit criteria (the only doors back):** (a) OS vendors ship first-class cross-platform credential-provider APIs, or (b) sustained failure to keep three cores at parity despite the conformance suite, documented over ≥ 2 release cycles.

---

*End of manual v2.0. A model that has completed §0–§5 plus one platform section has everything required to produce a conformant, interoperable, fully native BharatVault application. The three core implementations are kept honest not by sharing code, but by sharing this specification.*
