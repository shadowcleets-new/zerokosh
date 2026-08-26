# Play Console listing copy

Paste into **Main store listing**. Lengths are checked by
`design/check_listing.py`, which fails if either limit is exceeded.

---

## App name (30 max)

```
Zerokosh — Offline Vault
```

## Short description (80 max)

```
Passwords, cards and IDs, encrypted on your phone. No account. No cloud.
```

## Full description (4000 max)

```
Zerokosh keeps the credentials of Indian financial life in one encrypted vault
on your phone — and nowhere else.

No account to create. No server to trust. No sync you did not ask for.

WHY THIS IS DIFFERENT

Most vaults ask you to believe a promise about what happens to your data.
Zerokosh does not request the INTERNET permission at all, so Android will not
let it open a network connection even if it tried. You can check that yourself
in Settings → Apps → Zerokosh → Permissions before you trust it with anything.

BUILT FOR INDIAN FINANCIAL LIFE

Templates that already know the fields, so you are not inventing labels:

• Bank accounts — IFSC, MICR, customer ID, net-banking and transaction
  passwords, TPIN
• Cards — number, expiry, CVV, ATM PIN, network, variant, billing cycle
• UPI IDs, linked to the account they draw from
• PAN, Aadhaar, Passport, Voter ID, Driving Licence, DigiLocker
• Demat and broker logins, insurance policies, EPF and pension
• Utilities, telecom, transit passes, and ordinary app logins

315 Indian services are pre-filled with the right template and the right logo —
SBI, HDFC, ICICI, Axis, Kotak, PNB, Zerodha, Groww, LIC, Jio, and more.

TAP A CARD INSTEAD OF TYPING IT

Hold a contactless card against the back of your phone and Zerokosh reads the
number and expiry straight off the chip, then works out the network, whether it
is debit or credit, the issuing bank and the variant. Nothing is written to the
card. No payment is made. It reads only what the card already offers openly to
any terminal.

EVERYTHING ELSE

• Argon2id and XChaCha20-Poly1305 — the same primitives, whatever your phone
• Unlock with your fingerprint, or your passphrase
• A Recovery Key, shown once, for the day you forget the passphrase
• TOTP two-factor codes, with a built-in authenticator
• Autofill, so passwords reach the app that needs them
• A password generator that uses your device's cryptographic random source
• Screenshots blocked by default, so the vault does not appear in your recents
• Clipboard cleared automatically after you copy a secret
• Local reminders for a card that is about to expire
• English and हिन्दी

YOUR DATA, YOUR MOVE

Export the encrypted vault whenever you like — it is ciphertext, useless
without your passphrase. Import it on another phone, or keep it in a folder your
own cloud app syncs. Zerokosh writes the file; what syncs it is your choice, not
ours.

Android's automatic cloud backup is switched off deliberately, so your vault is
never swept into a device backup without you asking.

OPEN SOURCE

Zerokosh is free software under GPL-3.0. The full source is public, which is the
only version of this promise you should accept from anybody — you do not have to
take our word for a single claim above.

If you lose both your passphrase and your Recovery Key, your data is gone. There
is no reset, because there is nobody holding a copy to reset it from. That is
the trade, and it is the whole point.
```

---

## Notes for the form

- **Category** — Tools. (Finance invites a financial-services review Zerokosh
  does not need; it moves no money and connects to no institution.)
- **Tags** — password manager, security, privacy
- **Contains ads** — No
- **In-app purchases** — No
- **Content rating** — Everyone; no user-generated content, no ads, no purchases
- **Data safety** — no data collected, no data shared. See `RELEASE.md`.
- **Privacy policy** —
  <https://zerokosh-privacy-somebodyunknownms-8456s-projects.vercel.app>

- **Package name** — `com.zerokosh.app`, the id reserved in Play Console. It is
  not the Kotlin package (`org.zerokosh.app`); see the note in
  `app/build.gradle.kts`.

## Assets to upload

All produced already; nothing here still needs making.

| Console field | File |
| --- | --- |
| App icon (512×512) | `play/icon-512.png` |
| Feature graphic (1024×500) | `play/feature-graphic-1024x500.png` |
| Phone screenshots | `play/screenshots/*-play.png`, in filename order |

The five screenshots, in the order they should be uploaded:

1. `1-vault-play.png` — the vault list. What the app is for.
2. `2-record-play.png` — a card open, secrets masked. Detail without exposure.
3. `3-templates-play.png` — the template gallery. 315 Indian services.
4. `4-codes-play.png` — the built-in authenticator.
5. `5-settings-play.png` — the claims, in the product.

Play rejects a raw Pixel 9 capture: the panel is 1080×2424, and Play's long
edge may not exceed twice the short one. `design/make_play_screenshots.py`
scales each capture to fit 1080×1920 and pads it in the app's own background —
padded rather than cropped, because cropping 504px costs either the status bar
or the navigation bar, and both are what make a screenshot read as a phone.
Re-run it after replacing any capture; it overwrites the `-play.png` beside it.

Captures must come from a build with **Settings → Allow screenshots** on.
`FLAG_SECURE` is the default and hands you black rectangles otherwise — the app
behaving correctly, not a bug. Use invented records: an uploaded screenshot of
a real vault is permanent.
