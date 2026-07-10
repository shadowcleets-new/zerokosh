# Phase 7 — App Security & Data Privacy Hardening

## 7.1 Android Keystore (quick-unlock, §3.4/§6.5 — frozen in `quickunlock/QuickUnlockManager.kt`)

| Parameter | Exact value |
|---|---|
| Provider / alias | `AndroidKeyStore` / `bharatvault_quick_unlock` |
| Algorithm | AES-256, transformation `AES/GCM/NoPadding`, tag length 128 |
| Key gate | `setUserAuthenticationRequired(true)` · `setInvalidatedByBiometricEnrollment(true)` · SDK≥30 `setUserAuthenticationParameters(0, AUTH_BIOMETRIC_STRONG)` · SDK<30 `setUserAuthenticationValidityDurationSeconds(-1)` |
| What it encrypts | the 32-byte **MasterKey** (derived from passphrase) — NEVER the passphrase, NEVER the VaultKey directly |
| Blob storage | plain prefs file `bharatvault_quick_unlock` (`quick_unlock_blob`/`quick_unlock_iv`, Base64 NO_WRAP) — contents are Keystore ciphertext, so plain prefs are acceptable |
| Prompt | androidx `BiometricPrompt` + `CryptoObject(cipher)`, `BIOMETRIC_STRONG` only, negative button = cancel |
| Invalidation | `KeyPermanentlyInvalidatedException` (re-enrolled biometrics) → delete blob + Keystore entry + `prefs.quickUnlockEnabled=false`, hint `scr_lock_use_passphrase_once` — silent fallback per §3.4 |
| Passphrase change | MUST call `QuickUnlockManager.disable` (stale MasterKey) — enforced by TASK-305 |

## 7.2 EncryptedSharedPreferences (`data/Prefs.kt` — frozen)

File `bharatvault_prefs`; master key `MasterKey.Builder(context).setKeyScheme(AES256_GCM)`; key scheme `AES256_SIV`, value scheme `AES256_GCM`. Contents allowed: device_id, KDF params, UI settings, fail counters, sync-folder uri, feature toggles. FORBIDDEN contents: passphrase, any key material, any vault field value, TOTP secrets. The vault NEVER touches SharedPreferences (§6.2 note: security-crypto is "only for prefs, not vault").

## 7.3 Network security config (TASK-802 creates; manifest wires it)

`app/src/main/res/xml/network_security_config.xml` — EXACT content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <!-- The app makes exactly one opt-in HTTPS call (§5.9). No cleartext, anywhere, ever. -->
    <base-config cleartextTrafficPermitted="false">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
</network-security-config>
```

**SSL pinning: deliberately NONE.** Rationale (record — do not "add security" here): the single endpoint `api.pwnedpasswords.com` receives a 5-char anonymous hash prefix — no secret, no identity. Pinning would brick the optional feature on certificate rotation, the exact failure mode a maintenance-light FOSS app must avoid. System trust anchors only; user CAs are excluded by using `system` src only.

## 7.4 Memory & surface hygiene checklist (§3.5/§5.5 — verified at §11.6 gate)

- Key/passphrase `ByteArray`s wiped after use (`.wipe()` / `.fill(0)`) — pattern is in every frozen call site; replicate it in new code.
- `FLAG_SECURE` on the window by default; only the `allow_screenshots` setting (default OFF, warning text) clears it (`MainActivity.applyScreenPrivacy`).
- Secrets render in Compose `Text` from in-memory state only; no secret ever enters: logs, toasts (except non-secret labels), exceptions, `rememberSaveable`, WorkManager input data, notification text, clipboard without the 30 s sensitive path.
- Clipboard: `EXTRA_IS_SENSITIVE` flag + 30 s clear (Handler + Worker fallback); clear only when the clip is still ours.
- `allowBackup=false` + full `data_extraction_rules.xml` excludes (root/file/database/sharedpref/external for both cloud-backup and device-transfer).
- Release build strips `Log.v/Log.d` (R8 rule) — and §11.6 additionally greps release logs for any field value.

## 7.5 Release verification commands (run at TASK-806; all must pass)

```powershell
# 1. No banned APIs/deps crept in (run from repo root; expect NO matches → exit 1 from Select-String is success)
Get-ChildItem app\src, core\src -Recurse -Filter *.kt | Select-String -Pattern "retrofit2|okhttp3|androidx\.room|com\.google\.gson|dagger|javax\.inject|koin" -List
# 2. No secret-shaped logging (expect NO matches)
Get-ChildItem app\src, core\src -Recurse -Filter *.kt | Select-String -Pattern "Log\.[vdiwe]\(.*(pass|secret|pin|totp|key)" -List
# 3. Dependency list == §6.2 (manual diff of app/build.gradle.kts against Master Spec)
# 4. Proxy test (HUMAN-DEVICE): with breach toggle OFF, drive §11.3 steps 1–22 behind mitmproxy → ZERO requests.
```
