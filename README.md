# Zerokosh

A free, open-source, local-first password and credential vault built for Indian financial life.

**Only you can open your vault. Not even we can.** Zerokosh has no servers, no accounts, no analytics — your encrypted vault never leaves your device unless *you* put it in a sync folder you control (Google Drive, iCloud Drive, OneDrive, anything that syncs a folder).

## What it models

One Indian bank relationship produces 8–12 distinct secrets. Zerokosh has templates for all of them:

- **Bank accounts** — account number, IFSC, customer ID, net-banking login, transaction password, profile password, TPIN
- **Cards** — with RuPay/Visa/Mastercard detection, ATM PIN, linked account
- **UPI** — UPI ID + PIN, linked to its bank account
- **Demat/broking** — client ID, DP ID, BO ID, CDSL TPIN, TOTP
- **Insurance, EPF/NPS/PPF, utilities (LPG, FASTag, electricity), telecom (SIM PIN, PUK), government IDs**
- **App profiles** — Zepto, Blinkit, Swiggy, Zomato and other OTP-first apps (registered phone, wallet PIN, gift cards)
- **Logins & TOTP authenticator** — with autofill and QR import

## Security in one paragraph

The vault is a single encrypted file (`.kosh`). Your passphrase is stretched with **Argon2id** (libsodium), the vault body is encrypted with **XChaCha20-Poly1305**. A one-time **Recovery Key** (`KSH-…`) is your only backup if you forget the passphrase. Biometric quick-unlock stores a hardware-protected copy of the key on your device — never the passphrase. Full details: [THREAT_MODEL.md](THREAT_MODEL.md) and `spec/` (the complete build specification).

## Fully native, per platform

Android = Kotlin + Jetpack Compose · iOS/macOS = Swift + SwiftUI · Windows = C# + WinUI 3. No cross-platform framework, ever (ADR-001 in `spec/`). The four apps interoperate because they implement the same byte-exact vault format, proven by a release-blocking conformance matrix.

This repository contains the **Android** implementation:

- `core/` — pure Kotlin/JVM: data model, crypto, vault file format, merge (no Android imports)
- `app/` — Jetpack Compose UI, biometrics, SAF sync folder, autofill, reminders

## Building (Android)

```
./gradlew :core:test          # conformance vectors + unit tests
./gradlew :app:assembleDebug  # debug APK
```

Requires JDK 17+ and the Android SDK (compileSdk 36).

## Official download channels

- GitHub Releases (this repository) — APK, SHA-256 checksums in release notes
- F-Droid — builds from source
- Google Play
```

Check any APK from GitHub Releases against it with
`keytool -printcert -jarfile <file>.apk`. Note that Play re-signs with its
own app-signing key, so a build installed from Play will not match this —
it is the fingerprint for the artifacts published here, and for F-Droid it
differs again because F-Droid builds from source and signs with its own key.

## License

[GPL-3.0-or-later](LICENSE). Free forever for individuals — there is nothing to host, so there is nothing to charge for.

Zerokosh is free software: you can redistribute it and/or modify it under the
terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version. The `or later` matters: it is what lets this code move to a future GPL
without every past contributor having to be found and asked.

## Contributing & security reports

See [CONTRIBUTING.md](CONTRIBUTING.md) (adding your bank to `bank_rules.json` / `pickers.json` needs no code) and [SECURITY.md](SECURITY.md) for coordinated disclosure.
