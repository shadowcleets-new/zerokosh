# Phase 3 — The Immutable Data & Network Engine

## 3.1 Reality check for the Executor

This app has **no network layer** (one opt-in socket, §3.5 below) and **no database** (one encrypted file). Everything in this phase is already implemented and FROZEN in `:core` and `app/data`; your job is to *use* these APIs exactly as tabulated. The schemas of record: Master Spec §2 (model), §3 (crypto), §4 (file format).

## 3.2 `:core` public API contract (FROZEN — call, never modify)

### model
```kotlin
data class Record(uuid, template_id, title, institution = "", fields: Map<String,String>,
                  custom_fields: List<CustomField>, tags: List<String>, favorite: Boolean,
                  created_at: Long, modified_at: Long, rev: Int, device_id: String,
                  reminders: List<Reminder>)                       // §2.1, snake_case JSON
data class CustomField(label: String, type: String, value: String) // type ∈ TEXT|SECRET|DATE|NUMBER
data class Reminder(field_id: String, days_before: Int)
data class Tombstone(uuid: String, deleted_at: Long)
data class Attachment(name: String, mime: String, data: String /*base64*/)
data class VaultBody(format = 1, records, tombstones, attachments: Map<String,Attachment>, meta)
val VaultJson: Json                                                // the ONLY Json instance
data class TemplateCatalog(format, templates: List<Template>); fun byId(id): Template?
data class Template(id, icon, fields: List<TemplateField>)
data class TemplateField(k, t, s, v?, m?) { val type: FieldType; val sensitivity: Sensitivity; val typeParam: String? }
enum FieldType { TEXT, SECRET, PIN, NUMBER, DATE, MONTHYEAR, PHONE, EMAIL, URL, TOTP, CARDNUM, IFSC, PICKER, LINK, NOTE, FILE }
enum Sensitivity { L, M, H }
object FieldValidation { val IFSC_REGEX: Regex; fun isValid(field, value): Boolean }
fun Record.contentDiffersFrom(other: Record): Boolean
```

### crypto / vault
```kotlin
interface CryptoProvider { argon2id(pw, salt, ops, memBytes): ByteArray
    aeadEncrypt(pt, aad, nonce, key): ByteArray; aeadDecrypt(ct, aad, nonce, key): ByteArray?
    randomBytes(n): ByteArray }                                   // impls: AndroidCrypto, test-only LazySodiumTestCrypto
fun ByteArray.wipe()
object RecoveryKey { fun encode(bytes16): String /*BVR-…*/; fun decode(s): ByteArray? }
object VaultFileCodec { fun encode(...): ByteArray; fun decode(bytes): VaultEnvelope; sha256Hex; b64; unb64 }
class VaultEnvelope(vaultUuid, lastModifiedMs, header: VaultHeader, bodyNonce, bodyCiphertext, prefix)
object VaultOperations {
    fun createVault(passphrase, deviceId, nowMs, crypto, ops=3, memBytes=64MiB, initialBody): CreatedVault
    fun unlockWithPassphrase(fileBytes, passphrase, crypto): UnlockResult
    fun unlockWithRecoveryKey(fileBytes, recoveryInput, crypto): UnlockResult
    fun unlockWithMasterKey(fileBytes, masterKey, crypto): UnlockResult
    fun deriveMasterKey(fileBytes, passphrase, crypto): ByteArray
    fun save(envelope, body, vaultKey, deviceId, nowMs, crypto): ByteArray
    fun changePassphrase(fileBytes, current, new, deviceId, nowMs, crypto): ByteArray?
    fun rotateRecoveryKey(fileBytes, passphrase, deviceId, nowMs, crypto): Pair<ByteArray,String>? }
sealed class UnlockResult { Success(vaultKey, body, envelope) | WrongCredential | Corrupt }
object VaultMerge { const TOMBSTONE_TTL_MS; fun merge(local, remote, nowMs, freshUuid): VaultBody
                    fun applyDeletion(body, uuid, nowMs): VaultBody }
```

### totp / generator / util / import
```kotlin
object Totp { data class Params(secretBase32, label, issuer, algorithm="SHA1", digits=6, periodSeconds=30)
    fun code(params, timeMs): String; fun secondsRemaining(params, timeMs): Int
    fun hotp(secret, counter, digits, algorithm): String
    fun base32Decode(s): ByteArray; fun parseOtpauthUri(uri): Params? }
object PasswordGenerator { fun generate(length, upper, lower, digits, symbols, symbolSet, crypto): String
    fun generatePin(length /*4|6*/, crypto): String
    fun generateForRule(rule: BankRule, requestedLength, crypto): String
    fun satisfiesRule(password, rule): Boolean }
data class BankRule(bank, max_len, min_len, symbols_allowed, require_symbol, require_digit); data class BankRules(format, rules)
object CardUtils { enum Network { RUPAY, VISA, MASTERCARD, AMEX, DINERS, MAESTRO }
    fun luhnValid(digits): Boolean; fun detectNetwork(digits): Network? }
data class ImportPreview(records: List<Record>, sourceLabel: String)
object CsvImport { fun parseAuto(text, deviceId, nowMs): ImportPreview          // detects Chrome/Bitwarden/LastPass/KeePass
    fun parseChromeCsv / parseBitwardenCsv / parseLastPassCsv / parseKeePassCsv (text, deviceId, nowMs): ImportPreview
    fun mergeWithExisting(incoming, existing): Pair<List<Record> /*insert*/, List<Record> /*update*/>
    fun parseCsv(text): List<List<String>> }                                    // RFC-4180
```

## 3.3 Storage layer — `VaultStore` contract & implementations (FROZEN)

```kotlin
interface VaultStore {
    fun exists(): Boolean
    fun read(): ByteArray?
    fun writeAtomic(bytes: ByteArray, verify: (ByteArray) -> Boolean)  // §4.4: tmp → fsync → verify → rename
    fun backupCurrent()                                                 // → vault.bvlt.bak
    fun conflictSiblings(): List<Pair<String, ByteArray>>               // §4.5.3 vault*.bvlt
    fun deleteSibling(name: String)
    fun readBackup(): ByteArray?
}
```

| Behavior | `LocalVaultStore` (app-private, default) | `SafVaultStore` (user sync folder) |
|---|---|---|
| Location | `context.filesDir/vault.bvlt` | `DocumentFile.fromTreeUri(treeUri)` child `vault.bvlt` |
| Atomic write | java.nio `Files.move(…, ATOMIC_MOVE)` | tmp create → verify → delete old → `DocumentsContract.renameDocument`; provider-refuses-rename fallback writes final directly |
| fsync | `FileOutputStream.fd.sync()` | provider-dependent flush (accepted limitation) |
| Allowed filenames | ONLY `vault.bvlt`, `.tmp`, `.bak`, siblings `vault*.bvlt` (§5.6) | same |

## 3.4 Repository truth table (WHEN data moves — memorize this)

| Moment | Action (already coded in `VaultRepository`) |
|---|---|
| Unlock success | body → `_body` StateFlow; `lastSeenModifiedMs` recorded; then `mergeConflictSiblings()` |
| App returns to foreground unlocked | `refreshFromDisk()` (TASK-304 wires the call): disk `lastModifiedMs` ≠ lastSeen → decrypt with session VaultKey → `VaultMerge.merge` → persist if changed |
| Every save (`persist`) | re-read disk → if changed, merge FIRST (§4.5.1) → `backupCurrent()` once per session → `VaultOperations.save` → `writeAtomic` with decode-verify |
| Record upsert | new: uuid=random, rev=1, timestamps=now; existing: `rev+1`, `modified_at=now`, `device_id=ours` (§2.1) |
| Delete | `VaultMerge.applyDeletion` → tombstone, never hard delete (§4.5.2) |
| Lock (§5.2) | `vaultKey.wipe()`; body=null; state=Locked |
| Offline | is the default; nothing changes. NEVER add a network check to any vault path. |

## 3.5 The ONLY network call (§5.9) — `breach/BreachCheck.kt` (FROZEN)

```
GET https://api.pwnedpasswords.com/range/{first-5-hex-of-SHA1(password)}
Headers: User-Agent: BharatVault · Add-Padding: true
Timeouts: connect 8000 ms, read 8000 ms · Runs ONLY when Settings toggle is ON and user taps.
Success 200 → body lines "SUFFIX:count"; local suffix compare → count (0 if absent)
Any non-200 / exception → return -1 (UI: msg_breach_error). NO retry loop. NO caching.
```
`INTERNET` permission enters the manifest only in TASK-801, next to the comment that binds it to §5.9.
