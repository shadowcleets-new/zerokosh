# Releasing Zerokosh

How to cut a Google Play release, and what has already been handled.

Play Store and F-Droid ship from the same source. Nothing here breaks the
F-Droid path: with no keystore configured the release build stays unsigned,
which is exactly what F-Droid expects, because it builds from source and signs
with its own key.

---

## 1. Create your upload key — once, ever

**Run this yourself.** The passwords are yours; they must not pass through a
chat window, a commit, or anyone else's terminal.

```sh
keytool -genkeypair -v \
  -keystore zerokosh-upload.jks \
  -keyalg RSA -keysize 4096 -validity 10000 \
  -alias zerokosh
```

Then create `keystore.properties` in the repository root:

```properties
storeFile=zerokosh-upload.jks
storePassword=<the store password you chose>
keyAlias=zerokosh
keyPassword=<the key password you chose>
```

`keystore.properties`, `*.jks` and `*.keystore` are all gitignored. Confirm
before your first commit:

```sh
git status --porcelain --ignored | grep -E "jks|keystore"
```

> **Back the keystore up somewhere you will still have in five years.**
> Losing it means you can never update this listing again — you would have to
> publish a new app under a new package name, and existing users would not
> receive it as an update. Enrolling in Play App Signing at upload time gives
> Google the signing key and lets you reset a lost *upload* key, which is worth
> doing.

CI can supply the same four values as environment variables instead:
`ZEROKOSH_STORE_FILE`, `ZEROKOSH_STORE_PASSWORD`, `ZEROKOSH_KEY_ALIAS`,
`ZEROKOSH_KEY_PASSWORD`.

---

## 2. Verify before you build

`bundleRelease` succeeding proves nothing about whether the app runs. R8 removes
code it cannot see being used, and both lazysodium and the serializers are
reached by reflection. A release build has already been shipped-shaped and dead
on arrival once — see the WorkManager/Room note in `proguard-rules.pro`.

So always install and open the minified build first:

```sh
./gradlew :app:assembleReleaseCheck
adb install -r app/build/outputs/apk/releaseCheck/app-releaseCheck.apk
```

`releaseCheck` is the shipping R8 configuration under the applicationId
`com.zerokosh.app.releasecheck`, so it installs **beside** a real Zerokosh
rather than replacing it. That matters: swapping an app for one signed with a
different key requires uninstalling first, and uninstalling a vault app destroys
the vault.

Open it, unlock, add a record, tap a card, export a backup. A blank screenshot
is not a bug — `FLAG_SECURE` is on by default and blanks captures until you
enable screenshots in Settings.

Also run:

```sh
./gradlew :core:test :app:check
```

`check` includes `deadComposables`, which fails the build on any `@Composable`
that ships without ever being called.

---

## 3. Build the bundle

```sh
./gradlew clean :app:bundleRelease
```

Output: `app/build/outputs/bundle/release/app-release.aab`

Confirm it is signed with your key and not something else:

```sh
jarsigner -verify -verbose -certs app/build/outputs/bundle/release/app-release.aab | head -20
```

Bump `versionCode` in `app/build.gradle.kts` for **every** upload — Play rejects
a repeat. `versionName` is what users see.

---

## 4. Play Console

### Privacy policy — required

Play requires one for any app requesting a sensitive permission, and Zerokosh
requests the camera. It is live:

**<https://zerokosh-privacy-somebodyunknownms-8456s-projects.vercel.app>**

Verified publicly reachable with no login wall, which is what Play checks.
`PRIVACY.md` in this repository is the source of that page; edit it there, then
redeploy so the two never disagree.

A custom domain would read better in the listing than a `.vercel.app`
subdomain, but Play accepts either.

### Data safety form

Answer it as: **no data collected, no data shared.**

This is not an optimistic reading. The app does not hold the `INTERNET`
permission, so the operating system will not let it open a network connection at
all. Encryption at rest and the absence of a delete-my-data mechanism both
follow from there — there is nothing held anywhere to delete.

### Content rating

Utility, no user-generated content, no ads, no in-app purchases.

### Listing assets you must produce

- App icon, 512×512 PNG
- Feature graphic, 1024×500
- At least two phone screenshots — capture them from a build with screenshots
  enabled in Settings, or `FLAG_SECURE` will hand you black rectangles
- Short description, 80 characters
- Full description, up to 4000

### Start on a closed track

Internal testing → closed → production. The first review of a new app takes
days, and finding a crash after a production rollout is a much worse day than
finding one on an internal track.

---

## 5. Expect a question about the card handling

Zerokosh stores card numbers and reads contactless cards over NFC. That is
unusual enough to draw a reviewer's attention, so have the answer ready rather
than being surprised by it:

- The NFC read is **reader mode** — the phone acts as a terminal to read a card
  the user is holding. It is not Tap-to-Pay, it initiates no payment, and it
  writes nothing to the card.
- It reads only what the card offers openly to any terminal: the number, the
  expiry, sometimes the cardholder name. The printed CVV is not on the chip and
  cannot be read by anything.
- Everything read stays on the device, and the app has no network permission to
  send it anywhere.
- Tapping another phone is explicitly rejected with an explanation, because a
  wallet app only ever emits a device token, never the real card number.

---

## Already handled

Recorded so nobody re-litigates a solved problem.

| | |
|---|---|
| `targetSdk` | 36, above Play's floor for new apps |
| 16 KB page alignment | lazysodium 5.2.0 and JNA 5.17.0 — required for Android 15+ |
| ABIs | `armeabi-v7a`, `arm64-v8a`, `x86`, `x86_64`. JNA's aar also carries `armeabi`, `mips` and `mips64`; Android dropped those at NDK r16/r17, so they were 398 KB no device could load |
| R8 + resource shrinking | on, with keep rules for lazysodium, the serializers, Room and Workers |
| Log stripping | `Log.v` and `Log.d` removed from release by `-assumenosideeffects` |
| `allowBackup` | `false`, plus data extraction rules — the vault stays out of Android cloud backup |
| Permissions | camera is the only dangerous one, and it is optional. **No `INTERNET`.** |
| NFC and camera | `required="false"`, so the app installs on phones without either |
| `dependenciesInfo` | off, so no Google-signed metadata blob — F-Droid reproducibility |
| Debuggable | release is not, by default and not overridden |
