# DECISIONS.md

Per R0.4: where the spec is genuinely silent, the simplest option preserving R0.2 is chosen and recorded here.

---

## D-001 · 2026-07-09 · JVM test crypto provider

**Context:** §6.1 requires `:core` to be pure Kotlin/JVM and to run the §3.6/§11.2 conformance tests on the JVM, but the only allowed crypto dependency (§6.2) is `com.goterl:lazysodium-android`, which cannot load on a desktop JVM.
**Decision:** `:core` main source depends on nothing but `kotlinx-serialization-json`; crypto goes through the `CryptoProvider` interface (§6.4). The JVM test suite uses `com.goterl:lazysodium-java` as a **test-only** dependency to implement that interface. R0.7 explicitly excludes test-only dependencies from the budget; the runtime APK contains only `lazysodium-android`.

## D-002 · 2026-07-09 · "Other" option in pickers

**Context:** §2.4 requires every picker to include an "Other" free-text option, but does not say whether "Other" lives in `pickers.json` or in UI.
**Decision:** `pickers.json` holds pure lists; the picker UI always appends a localized "Other" entry that opens free-text input. Keeps the JSON reusable across platforms without a magic sentinel string.

## D-003 · 2026-07-09 · ifsc.db not bundled in this build

**Context:** §5.10/Appendix E: `ifsc.db` is built from Razorpay's dataset by `spec/build_ifsc.py` and refreshed each release; the app MUST work fully without it.
**Decision:** The build script is committed; the ~3 MB database is generated at release time rather than committed to git (binary churn every release). IFSC lookup silently disables when the asset is absent, exactly as §2.2 specifies.

## D-005 · 2026-07-09 · Android string keys use underscores

**Context:** §12 keys strings `tpl.<template>.<field>` / `scr.<screen>.<element>` / `msg.<id>`, but Android resource names cannot contain dots.
**Decision:** Dots map to underscores on Android only (`tpl_bank_account_ifsc`). The logical key scheme stays identical across platforms; `spec/gen_strings.py` generates the template-field labels so field IDs remain the single source of truth (R0.2 item 5).

## D-006 · 2026-07-09 · zxing generates the S4 Recovery-Kit QR

**Context:** §5.1 S4 requires the recovery PDF to carry the key as text + QR, but §6.2 scopes zxing to "TOTP QR only" (scanning).
**Decision:** The already-allowed zxing `core` artifact also encodes the S4 QR — the simplest option that satisfies S4 without a new dependency (R0.4). No other QR use exists.

## D-007 · 2026-07-09 · Attachments live in the repository, not the field map

**Context:** §2.1 stores a `FILE` field's value as the attachment uuid, with bytes in `VaultBody.attachments`. The edit form's `values` is a flat `Map<String,String>`, but attachment bytes belong to the whole body.
**Decision:** `VaultRepository.putAttachment(name, mime, bytes)` inserts into `attachments` (enforcing the ≤ 2 MB cap, §2.1) and returns the uuid; the FILE editor stores that uuid in `values`. Keeps the field map flat and the size-cap in one place. (Specified for the Executor in Operation Manual TASK-401.)

## D-008 · 2026-07-09 · Attachments render in-memory, never to disk

**Context:** §3.5/§14 forbid writing plaintext secrets to disk, temp files, or cache. Attachments (e.g. a scanned PAN) are high-sensitivity.
**Decision:** Viewing an attachment decodes its base64 into an in-memory `Bitmap`/`ImageBitmap` shown in a dialog — no `FileProvider`, no temp file, no external-app handoff in v1. Non-image attachments show metadata only. (Operation Manual TASK-402.)

## D-004 · 2026-07-09 · Canonical §11.2 fixture records

**Context:** §11.2 requires every platform's fixture vault to contain "the same 3 sample records + 1 attachment" but does not enumerate them.
**Decision:** Canonical content is defined in `core/src/test/kotlin/org/zerokosh/core/FixtureVault.kt` (also the reference for the Swift and C# cores): a `bank_account` (SBI, uuid 1111…), a `login` (Zomato, uuid 2222…), a `upi` linked to the bank record (uuid 3333…), and one text attachment (uuid 4444…). Passphrase `test-vault-1234`, device uuid all-zeros v4, timestamps fixed at 1751875200000.
