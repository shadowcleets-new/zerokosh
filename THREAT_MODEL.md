# Zerokosh Threat Model

Zerokosh is a local-first encrypted credential vault. This document says honestly what it defends against and what it does not.

## Assets

- The vault body: all records, attachments, TOTP secrets (encrypted with a random 32-byte VaultKey).
- The passphrase / 6-digit PIN (never stored anywhere, in any form).
- The Recovery Key (shown once at setup; only the Argon2id-stretched *wrap* of the VaultKey under it is stored).
- The MasterKey copy used for biometric quick-unlock (stored only inside the hardware-backed OS keystore, gated by user authentication).

## In scope — what we defend against

| Threat | Defense |
|---|---|
| **Stolen or lost device** | Vault file is Argon2id + XChaCha20-Poly1305 encrypted; auto-lock zeroes keys from memory; biometric key store invalidates on re-enrolment; failed unlocks trigger doubling cooldowns (never data wipe). |
| **Cloud account compromise** (Google Drive / iCloud / OneDrive account holding the sync folder) | The attacker gets only the encrypted `.kosh` file. Offline brute force is throttled by Argon2id (64 MiB, ops 3). A strong passphrase keeps the vault safe even in the attacker's hands. |
| **Phishing / shoulder surfing** | High-sensitivity fields are fully masked and require fresh biometric auth to reveal or copy; reveals auto-re-mask after 10 s; clipboard clears after 30 s and is marked sensitive; screenshots of vault screens are blocked by default; in-app scam-awareness tips ("Banks NEVER ask for your OTP"). |
| **Malicious fork / fake app** | GPL-3.0 source, reproducible/checksummed builds, signing fingerprints published in README, F-Droid builds from source, trademark registration recommended. |
| **Network attackers** | There is nothing to attack: the app makes zero network calls unless the user explicitly triggers the k-anonymity breach check (only a 5-char SHA-1 prefix ever leaves the device) or the desktop update-tag check. |
| **Vault corruption** (crash or sync race mid-write) | Atomic tmp-write → fsync → verify-by-decrypt → rename, plus a `.bak` of the previous state; conflict siblings are merged, never discarded. |

## Out of scope — what we do NOT defend against (honestly)

- **A compromised or rooted OS.** If malware runs with root, can read app memory, log keys, or capture the screen at the OS level, no password manager can protect you. We reduce exposure (memory zeroing, no plaintext on disk) but cannot eliminate it.
- **Targeted state-level attackers.** Lawful-intercept level device compromise, hardware implants, or coerced disclosure of your passphrase are outside this design.
- **A weak passphrase.** Argon2id makes guessing expensive, not impossible. A 4-character passphrase will fall. The app enforces length ≥ 10 (or a hardware-gated PIN) for this reason.
- **You losing both the passphrase and the Recovery Key.** There is no reset. Nobody — including the developers — can open your vault. This is a feature and a responsibility.
- **Windows quick-unlock caveat.** On Windows, DPAPI binds the quick-unlock blob to your Windows user account, not to the biometric itself; the passphrase remains the root secret (documented in-app).

## Notes for reviewers

The full cryptographic specification, byte-level file format, and conformance vectors are in `spec/zerokosh_master_build_manual_v2.md` (§3, §4, §3.6). Every release must pass the §11.6 security checklist, including a proxy-verified zero-network-calls test.
