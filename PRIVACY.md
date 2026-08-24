# Privacy Policy — Zerokosh

**Last updated: 25 August 2026**

Zerokosh is an offline, open-source vault for the credentials of Indian
financial life. This policy describes what the app does with your data.

It is short because there is very little to describe.

## The short version

**Zerokosh collects nothing. Zerokosh transmits nothing. There is no account,
no server, and no analytics.**

Everything you put into Zerokosh stays in one encrypted file on your own device,
plus wherever you choose to copy that file.

## This is verifiable, not just claimed

Zerokosh does not request the `INTERNET` permission.

Android enforces this at the operating system level: an app without that
permission cannot open a network connection at all. It is not a policy the app
promises to follow — it is a capability the app does not have. You can confirm
it yourself:

- In Android: **Settings → Apps → Zerokosh → Permissions**
- In the source: [`app/src/main/AndroidManifest.xml`](https://github.com/)
- In the built app: any APK analyser will list the permissions

Because of this, no data can reach us, any third party, or any advertiser, by
any route, including one added by mistake.

## What is stored, and where

Your records — passwords, card details, account numbers, notes, and anything
else you enter — are stored in a single encrypted vault file in Zerokosh's
private storage on your device.

The file is encrypted with **XChaCha20-Poly1305**. The key is derived from your
passphrase using **Argon2id**. Neither your passphrase nor the key is stored
anywhere; the passphrase is only ever held in memory while the vault is open,
and is wiped when it locks.

Nobody but you can read that file, including us. If you lose both your
passphrase and your Recovery Key, the data is unrecoverable. There is no reset,
because there is nobody holding a copy to reset it from.

## Permissions, and why each exists

| Permission | Why | Optional? |
|---|---|---|
| **Camera** | Scanning a TOTP QR code when you add a two-factor secret. Frames are processed on-device and never stored or sent. | Yes — the app works fully without it; you can type the secret instead. |
| **NFC** | Reading the number and expiry from a contactless card you hold against the phone, so you do not have to type them. Read-only; nothing is written to the card and no payment is made. | Yes — declared `required="false"`, so Zerokosh installs and runs on phones with no NFC. |
| **Biometric** | Unlocking your existing vault with your fingerprint or face, as an alternative to typing the passphrase. Android holds the biometric data; Zerokosh never sees it. | Yes — off unless you turn it on. |
| **Notifications** | Local reminders you create yourself, for things like a card expiry. Generated on your device; no push service is involved. | Yes. |

No location, contacts, storage, phone, SMS, or advertising-identifier access is
requested.

## Files you choose to create

Two features write data outside the app's private storage, and both happen only
when you ask:

- **Export** writes a copy of your encrypted vault to a location you pick. The
  file is ciphertext — useless to anyone without your passphrase or Recovery
  Key — but it is then in your care, and whatever you copy it to is governed by
  that service's own privacy policy, not this one.
- **Sync folder** keeps that same encrypted file in a folder you nominate, which
  may be a cloud-backed one. Zerokosh does not upload it; it writes to a local
  folder, and any syncing is done by the app that owns that folder.

Zerokosh cannot read either destination beyond the folder you grant it.

## Android backup is disabled

Zerokosh sets `allowBackup="false"`. Your vault is deliberately excluded from
Android's automatic cloud backup, so it will not be copied to Google's servers
as part of a device backup. Moving your vault to a new phone is something you do
explicitly, with Export and Import.

## Children

Zerokosh is not directed at children and collects no data from anyone,
including children.

## Changes to this policy

If this policy changes, the updated version will be published at this address
and the date at the top will change. Because the app has no network access, it
cannot notify you; checking here is the way to see it.

## Contact

Zerokosh is free and open-source software, licensed under **GPL-3.0**. The full
source is available for inspection, which is the strongest form this promise can
take — you do not have to take our word for any of the above.

Questions or reports: open an issue on the project's repository.
