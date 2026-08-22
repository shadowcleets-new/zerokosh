# Zerokosh — Design System

The working reference for how this app looks, moves and behaves. Every value
here is the value in the code, not an intention. Where the code and this file
disagree, the code is right and this file is a bug.

**Direction:** *Organic Editorial — warm + serif.* Transcribed from the Lovable
mockup pack. Soft organic shapes, an editorial serif for anything that speaks,
a neutral sans for anything that labels, and monospace for anything that is
data. Warm neutrals throughout; nothing in this app is cold grey.

---

## Table of contents

1. [The two colour systems](#1-the-two-colour-systems)
2. [Brand palette (VaultColors)](#2-brand-palette-vaultcolors)
3. [Material tonal scheme](#3-material-tonal-scheme)
4. [Typography](#4-typography)
5. [Shape](#5-shape)
6. [Motion](#6-motion)
7. [Components](#7-components)
8. [Screens](#8-screens)
9. [Material 3 Expressive adoption](#9-material-3-expressive-adoption)
10. [Accessibility](#10-accessibility)
11. [Brand logos](#11-brand-logos)
12. [Voice and copy](#12-voice-and-copy)
13. [Invariants — things that must not drift](#13-invariants--things-that-must-not-drift)
14. [Pitfalls, learned the hard way](#14-pitfalls-learned-the-hard-way)
15. [Recipes](#15-recipes)
16. [Open items](#16-open-items)

---

## 1. The two colour systems

This is the single most important thing to understand before touching colour.
The app carries **two** parallel colour systems and they are not
interchangeable.

| | `VaultColors` | `ColorScheme` |
|---|---|---|
| Reached via | `VaultTheme.colors` | `MaterialTheme.colorScheme` |
| Defined in | `Theme.kt` → `LightVaultColors` / `DarkVaultColors` | `Theme.kt` → `LightColors` / `DarkColors` (generated) |
| Drives | our hand-built components | Material components |
| Purpose | the product's **identity** | correct Material **role** resolution |
| Roles | 12 named tokens | 36 M3 roles |

**Why both.** The identity tokens (`paper`, `ink`, `card`, `line`, `dock`) have
no Material equivalent — Material has no concept of "the dock" or "hairline".
Meanwhile Material components resolve roles we never think about
(`tertiaryContainer`, `surfaceContainerHighest`), and if those are undefined
they silently fall back to Google's baseline purple.

**Rule:** a hand-built component reads `VaultTheme.colors`. A Material
component either uses its defaults (now safe, because every role is defined) or
is passed explicit colours from `VaultTheme.colors`. Never mix the two on one
surface — that is how you get two slightly different oranges on one screen.

---

## 2. Brand palette (VaultColors)

Source values are OKLCH from the mockup stylesheet; the hexes are their exact
sRGB resolution. The OKLCH comment is kept next to each so the derivation
survives.

### Light

| Token | Hex | OKLCH | Used for |
|---|---|---|---|
| `paper` | `#FDFAF6` | `oklch(.985 .006 75)` | page ground |
| `surface` | `#F6F3EF` | `oklch(.965 .006 75)` | inset fields, search dock |
| `card` | `#FFFFFF` | — | card faces, brand tiles |
| `ink` | `#180F0D` | `oklch(.18 .015 40)` | all primary text |
| `mute` | `#787069` | `oklch(.55 .015 60)` | secondary text |
| `line` | `#E1DDDA` | `oklch(.90 .006 70)` | hairlines, borders |
| `primary` | `#BB4717` | `oklch(.55 .16 40)` | burnt orange — the brand |
| `primarySoft` | `primary @ 8%` | — | tinted fills |
| `accent` | `#018D87` | `oklch(.58 .10 190)` | teal — positive/secondary |
| `accentSoft` | `accent @ 10%` | — | tinted fills |
| `dock` | `#120C09` | `oklch(.16 .012 40)` | charcoal dock |
| `onDock` | `#FDFAF6` | — | text on dock |

### Dark

| Token | Hex | Notes |
|---|---|---|
| `paper` | `#120C09` | |
| `surface` | `#241D1B` | |
| `card` | `#1B1412` | |
| `ink` | `#F4F1ED` | |
| `mute` | `#A9A39E` | |
| `line` | `#1AFFFFFF` | white @ 10%, not a solid |
| `primary` | `#F0834E` | lightened for dark grounds |
| `primarySoft` | `primary @ 12%` | |
| `accent` | `#1DBCB5` | |
| `accentSoft` | `accent @ 14%` | |
| `dock` | `#0C0806` | darker than paper |
| `onDock` | `#F4F1ED` | |

### The ink opacity ramp

`VaultColors.ink(alpha)` is the mockup's `text-vault-ink/45` convention. Used
constantly — prefer it over inventing a new grey.

Common stops: `.75` body-on-card, `.7` chip label, `.55` inactive icon/label,
`.45` caption, `.4` counter, `.3` disabled border, `.12` track, `.05` tile
border.

### Fixed-palette exception

`LockScreen.kt` is charcoal in **both** themes, so it deliberately uses the
literal tokens (`VaultDock`, `VaultPaper`, `VaultErrorDark`) rather than the
theme-flipping ones. `LockError` is pinned to the dark error red because the
light one is ~3.6:1 on charcoal.

---

## 3. Material tonal scheme

Generated, not hand-written. `design/gen_tonal_scheme.py` emits
`LightColors` / `DarkColors` into `Theme.kt`.

### Why generated

The scheme used to set ~24 roles by hand and leave 12 undefined. Undefined
roles fall back to the M3 baseline — which is how an unset `tertiaryContainer`
produced a **pink** floating action button on the record detail screen. Every
role is now a tone of a ramp, so a role cannot be forgotten.

### How

An M3 **tone is CIELAB L\***. A ramp is the seed's hue and chroma held constant
while L\* sweeps the tone stops, chroma reduced by binary search where a tone
leaves sRGB. Material's own generator lives in `material-color-utilities`,
which the locked dependency list rules out, and `material3`'s `TonalPalette` is
`internal` — hence generating it here and bundling constants. No runtime cost,
no dependency, every value reviewable in the diff.

### The six ramps

| Ramp | Seed | Derivation |
|---|---|---|
| primary | `#BB4717` | brand |
| secondary | `#018D87` | brand |
| error | `#D40C1A` | brand |
| tertiary | `#647400` | primary hue rotated **+60°**, per the M3 spec |
| neutral | `#736B68` | primary hue at **5.5%** chroma |
| neutral variant | `#7B6962` | primary hue at **13%** chroma |

Neutrals carry the primary's hue at low chroma, which is why the greys read
warm rather than blue.

### The Expressive rule

`expressiveLightColorScheme()` is `lightColorScheme()` with four `on*Container`
roles moved to tone 30. That is the *only* thing it changes — it is otherwise
the baseline purple palette, takes no seed, and has no dark counterpart. We
adopt the **rule**, not the hues: `on*Container` sits at **tone 30 light / tone
90 dark** on our own ramps.

### Pinned anchors

`primary`, `secondary` and `error` are pinned to the literal brand hexes rather
than landing on tone 40. `VaultColors` uses those exact values, and letting the
scheme drift to `#A93806` would put two oranges on one screen.

### Contrast floor

Worst pair is **4.07:1** (`onSecondary` on the pinned teal). Everything else
clears 4.5:1. Regenerate and re-check contrast if you touch a seed.

---

## 4. Typography

Three families, three jobs. Never introduce a fourth.

| Family | File | Role |
|---|---|---|
| **Newsreader** | `newsreader.ttf`, `newsreader_italic.ttf` | anything that *speaks* — headlines, card titles |
| **Instrument Sans** | `instrument_sans.ttf` | anything that *labels* — UI, body, buttons |
| **JetBrains Mono** | `jetbrains_mono.ttf` | anything that is *data* — secrets, codes, kickers, metadata |

All three are **variable fonts**, loaded with `FontVariation.Settings`. The
file-level `@OptIn(ExperimentalTextApi::class)` in `Theme.kt` exists solely for
`Font(variationSettings=)` — don't remove it.

### Scale

Mockups are drawn at 340dp wide, the design width of a Pixel in dp, so mockup
px map 1:1 to sp.

| Token | Family | Size / line | Tracking | Used for |
|---|---|---|---|---|
| `displayLarge` | Newsreader | 52 / 48 | −1.04 | welcome hero |
| `displayMedium` | Newsreader | 34 / 36 | −0.68 | onboarding headlines |
| `displaySmall` | Newsreader | 32 / 34 | −0.64 | Home, Authenticator headlines |
| `headlineMedium` | Newsreader | 28 / 30 | −0.56 | sheet titles |
| `headlineSmall` | Newsreader | 24 / 28 | — | card titles |
| `titleLarge` | Newsreader | 20 / 24 | — | |
| `titleMedium` | Instrument Sans | 14.5 / 18 | — | list row title |
| `titleSmall` | Instrument Sans | 13.5 / 17 | — | |
| `bodyLarge` | Instrument Sans | 15 / 22 | — | |
| `bodyMedium` | Instrument Sans | 13 / 19 | — | **the workhorse** |
| `bodySmall` | Instrument Sans | 11.5 / 15 | — | sub-labels |
| `labelLarge` | Instrument Sans | 15 / 20 | −0.15 | pill buttons |
| `labelMedium` | Instrument Sans | 12.5 / 16 | — | chips |
| `labelSmall` | JetBrains Mono | 10 / 14 | **+2.4** | kickers, uppercase |

### Named styles outside the scale

| Style | Spec | Used for |
|---|---|---|
| `MetaTextStyle` | Mono 11.5 / 15 | metadata under a record title |
| `SecretTextStyle` | Mono 15 / 22, +0.3 | revealed secrets, account numbers, card numbers |
| `FieldLabelStyle` | Sans 10, semibold, +1.2, uppercase | field captions |
| `SectionLabelStyle` | Sans 11, semibold, +1.98, uppercase | section headers |
| `TotpTextStyle` | Mono 26 / 30, **+4.16** | the rotating TOTP digits |

**Rule:** any digit sequence a human must read, compare or transcribe — PAN,
IFSC, TOTP, recovery key, account number — is monospace. This is not
decoration; proportional digits make transcription errors.

---

## 5. Shape

### Corner tokens

Named constants in `Theme.kt`, lifted from the mockup's `--radius` roles:

```
CornerTile  = 16.dp   brand tiles, small tiles
CornerCard  = 24.dp   cards
CornerGroup = 26.dp   grouped list card
CornerHero  = 28.dp   hero surfaces
```

### Material shape scale

`BharatShapes` keeps the mockup's own corner values but expresses them through
the full Expressive scale. The three largest sizes come from `ShapeDefaults`,
because the hand-written `Shapes()` could not name them at all and components
asking for them were silently falling back.

```
extraSmall          8.dp
small              12.dp
medium             16.dp   (rounded-2xl)
large              24.dp   (rounded-3xl)
largeIncreased     ShapeDefaults.LargeIncreased
extraLarge         26.dp   (grouped list card)
extraLargeIncreased ShapeDefaults.ExtraLargeIncreased
extraExtraLarge    ShapeDefaults.ExtraExtraLarge
```

### Organic blobs

`VaultBlobs` in `VaultUi.kt`. These were originally a hand-rolled `Shape`
porting CSS elliptical `border-radius`, including a port of the CSS
radius-overlap normalisation. They are now `MaterialShapes`, which are real
`RoundedPolygon`s and therefore **morphable**.

| Blob | Morphs between | Period |
|---|---|---|
| `Primary` | Puffy ⇄ Cookie9Sided | 9 s |
| `Accent` | Clover4Leaf ⇄ Flower | 11 s |
| `Ink` | Cookie9Sided ⇄ Pill | 13 s |

Periods are deliberately mismatched so the composition never repeats a pose.

---

## 6. Motion

`MaterialExpressiveTheme(motionScheme = MotionScheme.expressive())`.

**There are no duration constants.** A hand-tuned `cubic-bezier(0.32, 0.72, 0,
1)` and three duration ints used to live in `Theme.kt`; they are deleted. Read
the scheme at the call site instead:

```kotlin
MaterialTheme.motionScheme.defaultSpatialSpec()   // position, size, shape
MaterialTheme.motionScheme.fastSpatialSpec()      // small, immediate
MaterialTheme.motionScheme.slowSpatialSpec()      // large, deliberate
MaterialTheme.motionScheme.defaultEffectsSpec()   // colour, alpha
```

Spatial for anything that moves or changes shape; effects for anything that
only changes colour or opacity.

### Reduced motion

Decorative motion **must** stop when the user asks the system to reduce it.
The pattern, from `breathingBlob`:

```kotlin
val animated = Settings.Global.getFloat(
    context.contentResolver, Settings.Global.ANIMATOR_DURATION_SCALE, 1f,
) > 0f
if (!animated) return remember(morph) { MorphShape(morph, 0f) }
```

Verified: at scale 0 three consecutive frames are byte-identical.

---

## 7. Components

All in `ui/common/VaultUi.kt` unless noted. This is the shared vocabulary —
check here before building anything new.

**Structure** — `Kicker`, `SectionLabel`, `SheetHandle`, `OnboardingTopBar`,
`BottomActionBar`, `RowDivider`

**Containers** — `GroupCard`, `WhiteCard`, `InkCard`, `NoticeCard`

**Actions** — `PrimaryPillButton` (56dp, `CircleShape`, optional `loading`),
`OutlinedPillButton`, `SubtleTextButton`, `VaultExtendedFab`

**Selection** — `VaultToggleRow` (ButtonGroup), `VaultFilterChip`

**Data display** — `VaultListRow`, `BrandTile`, `StatusPill`, `MicroBadge`,
`CopyChip`, `StepProgress`

**Navigation** — `VaultNavBar` (ShortNavigationBar), `VaultNavRail`
(WideNavigationRail at ≥600dp), `SearchDock`

**Input affordances** — `RevealToggle` (48dp, on every masked field)

### Rules

- `PrimaryPillButton` is 56dp tall and fully round. There is one primary action
  per screen.
- A card is `GroupCard` (grouped rows) or `WhiteCard` (standalone). `InkCard`
  is for inverted emphasis only.
- Never build a bespoke row. `VaultListRow` handles leading slot, title, meta,
  badge and chevron.

---

## 8. Screens

| Screen | File | State |
|---|---|---|
| Welcome | `ui/onboarding/WelcomeScreen.kt` | NoVault |
| Language, Trust, Passphrase, Recovery, Quick Unlock | `ui/onboarding/Onboarding.kt` | NoVault, steps 2–6 |
| Lock | `ui/lock/LockScreen.kt` | Locked |
| Home / Vault | `ui/home/HomeScreen.kt` | Unlocked |
| Authenticator | `ui/authenticator/AuthenticatorScreen.kt` | Unlocked |
| Template Gallery | `ui/gallery/TemplateGalleryScreen.kt` | Unlocked |
| Record Detail | `ui/record/RecordDetailScreen.kt` | Unlocked |
| Record Edit | `ui/record/RecordEditScreen.kt` | Unlocked |
| Settings | `ui/settings/SettingsScreen.kt` | Unlocked |

Navigation lives in `ui/ZerokoshNav.kt`: a four-tab `Scaffold` whose bottom bar
becomes a side rail at ≥600dp, with the Add FAB in the `floatingActionButton`
slot.

---

## 9. Material 3 Expressive adoption

Adopted as **mechanism, not identity** — motion, shape vocabulary and component
behaviour, but our own colour and type.

### In use

`MaterialExpressiveTheme` · `MotionScheme` · `MaterialShapes` · `Morph` ·
`ShapeDefaults` · `ShortNavigationBar` · `WideNavigationRail` ·
`HorizontalFloatingToolbar` · `ToggleButton` · `ButtonGroup` ·
`LinearWavyProgressIndicator` · `CircularWavyProgressIndicator` ·
`LoadingIndicator` · `ContainedLoadingIndicator` · `FloatingActionButtonMenu` ·
`ToggleFloatingActionButton` · `animateFloatingActionButton` ·
`HorizontalMultiBrowseCarousel` · `FilterChipDefaults.shapes()` ·
`RippleThemeConfiguration` · `rememberBottomSheetState` ·
`OutlinedTextFieldDefaults.roundedShape`

### Declined, with reasons

| API | Why not |
|---|---|
| `SearchBar` family | Expands search into a surface that covers content; the vault filters in place behind the filter chips. Adopting it means two competing result surfaces or losing the chips mid-search. |
| `expressiveLightColorScheme` | Baseline purple, no seed, no dark counterpart. The rule is adopted; the hues are not. |
| `tonalColors`, `ShapeDefaults` for fields | Turn outlined fields into filled-tonal ones, against the mockup. |
| `SplitButton`, toggle variants, other carousels | No home without inventing UI to host them. |

### Blocked

`TextFieldLabelPosition.Cutout` exists only on the `TextFieldState` overload.
Reaching it means migrating every field off `value`/`onValueChange`, which
changes how form state and validation are held. A refactor, not a styling swap.

---

## 10. Accessibility

### Contrast

- Body and label text: **≥4.5:1** against its own surface.
- Verify against the surface the element actually sits on, not the page ground.
  A chip on a card is measured against the card.
- Known ceiling: teal `#018D87` on paper caps at ~3.4:1. It is the brand colour
  and applies to existing text ("Copy", "Unlocked"). Do not spread it to new
  small text.

### Tap targets

**48dp minimum**, always. The painted affordance may be smaller — wrap it.

```kotlin
Modifier.sizeIn(minWidth = 48.dp, minHeight = 48.dp)
```

`CopyChip` and `RevealToggle` both do this; the painted chip stays small.

### Masked fields

**Every** field that masks input carries a `RevealToggle`. All nine do. The one
exception is the lock screen's recovery-key mode, where the input is already
displayed in clear and a toggle would be a no-op.

Visibility state uses plain `remember`, not `rememberSaveable` — a rotation
re-masks. Losing the reveal on rotation costs less than carrying a plaintext
passphrase through saved instance state.

### Focus

Inset focus rings via `RippleDefaults.InsetFocusRingRippleThemeConfiguration`,
provided at the theme root. The previous opacity wash was near-invisible on our
tinted surfaces.

### Screen readers

- Give every icon-only control a `contentDescription` that names the **action**
  ("Show passphrase"), not the icon.
- Use `semantics(mergeDescendants = true)` on composite rows so a chip reads as
  "All, 8" rather than "All" then "8".
- A control that toggles must expose `Role.Button` or be genuinely `checkable`.

---

## 11. Brand logos

~290 bundled marks in `app/src/main/res/drawable/`.

**Naming:** `logo_<name>.png`, where `<name>` is the catalog `logoName`
lowercased with every non-alphanumeric stripped. `"HDFC Bank"` → `logo_hdfcbank`.
Resolved at runtime by `CompanyLogo.kt` via `getIdentifier`, so a mismatch fails
silently to a monogram.

**Format rules:**
- PNG. Android's drawable loader cannot read `.jfif` or `.svg`.
- Max 512px on the long edge — these display at ~44dp.
- Preserve alpha where the source has it.
- One resource name per qualifier. `logo_x.xml` and `logo_x.png` will fail the
  build.

**Fallback:** no drawable → `CompanyLogo` draws a monogram from the first four
characters, tinted from the theme. Currently 47 of 315 catalog entries fall back.

**Tooling:** `design/import_logos.py`, `design/audit_logos.py` (lists what is
missing), `design/contact_sheet.py` (renders what was imported for review).

---

## 12. Voice and copy

The copy is part of the design. It is calm, concrete and never markety.

- **Short declaratives.** "A blank vault, ready." "Your vault is sealed."
- **Editorial headlines with one emphasised clause** in italic serif and
  primary colour: "1 credential, *all offline.*"
- **State what the app cannot do**, plainly. "No reset link. No support
  backdoor." That honesty is the product's argument.
- **Never promise what the hardware cannot deliver.** The tap-to-read sheet
  says the CVV is not on the chip, so nobody hunts for a setting that does not
  exist.
- **Errors explain the fix.** "That recovery key isn't right — check it and try
  again", not "Invalid input".
- **Buttons name the outcome.** "Seal the vault", not "Submit".

---

## 13. Invariants — things that must not drift

**No `INTERNET` permission.** The app declares none. "0 servers contacted" is a
verifiable manifest fact, not a slogan. Anything needing data must bundle it at
build time.

**Dependency list is locked** to §6.2. Adding one requires human review. Several
design decisions here exist specifically to avoid a new dependency — the tonal
scheme generator and the dead-composable guard among them.

**`FLAG_SECURE` by default.** §5.5. "Allow screenshots" (default **off**)
clears it. Note this blanks `screencap` *and* `uiautomator`, which makes the app
unreadable to automation until the setting is on.

**No unreferenced composables.** `:app:deadComposables` fails the build,
wired into `check`. Nothing in the Kotlin toolchain catches this: "public but
unused" is never a warning because public implies an external caller, and unused
*private* composables are not flagged either because the Compose plugin rewrites
them.

**Templates are spec.** `spec/templates.json` and
`app/src/main/assets/templates.json` must stay byte-identical.
`TemplateCatalogTest` allows additions but fails removals and renames.

---

## 14. Pitfalls, learned the hard way

Every one of these cost real debugging time.

**A trailing lambda binds to the *last* parameter.** `RevealToggle(visible) { … }`
binds the lambda to `tint`, not `onToggle`. Pass callbacks by name when a
composable has trailing params after them.

**`animateIcon` tints through a `ColorFilter`**, which overrides `Icon(tint=)`.
Its default is `onPrimaryContainer` → `onPrimary`. Override the container colour
without overriding the icon colour and you get orange on orange — an invisible
FAB.

**Overriding a container colour means overriding its content colour.** This is
the general form of the bug above.

**`fillMaxSize()` before `widthIn(max=)` defeats the cap.** Modifier order is
semantic.

**Translucent container roles break under Material.** M3 composites container
roles over surfaces it picks itself, so a translucent `primaryContainer` picks
up whatever is behind it. Container tints are pre-flattened opaque.

**A wavy indicator forced to a non-native size distorts.** `CircularWavyProgressIndicator`
draws its wave against its own 48dp container; squeezing it to 44dp scalloped
the ring into a blob. Let it size itself, then damp `amplitude` if needed.

**`lastScrolledForward` did not flip reliably.** Derive scroll direction from
`firstVisibleItemIndex`/`Offset` with a deadband instead.

**`TextFieldLabelPosition` is only on the `TextFieldState` overload.** Passing
it to the `value`/`onValueChange` overload fails to resolve.

**Kotlin call sites take several shapes.** `Name(…)`, `Name { }` with no parens,
`::Name`. Any tool matching only `Name(` will report live code as dead — this
exact bug nearly deleted a live `SettingsCard`.

**A file is not orphaned just because its composables are.** `Components.kt` had
three dead composables and two live non-composable helpers.

---

## 15. Recipes

**Change a brand colour**
1. Edit the seed in `design/gen_tonal_scheme.py` **and** the token in `Theme.kt`.
2. Re-run the generator; it rewrites `LightColors`/`DarkColors`.
3. Re-check contrast on every `on*`/container pair.
4. Verify in **both** themes on a device.

**Add a component**
1. Check `VaultUi.kt` first — the vocabulary is probably already there.
2. Read `VaultTheme.colors`, not literals.
3. Take a `modifier: Modifier = Modifier` as the third parameter.
4. Give icon-only controls a 48dp target and a `contentDescription`.
5. It must be referenced, or `:app:deadComposables` fails the build.

**Add brand logos** — drop files in a folder, extend the map in
`design/import_logos.py`, run it, then `design/contact_sheet.py` to eyeball the
result before committing.

**Animate something** — read `MaterialTheme.motionScheme`. Spatial for
movement, effects for colour. If it is decorative, honour
`ANIMATOR_DURATION_SCALE`.

---

## 16. Open items

- **47 catalog entries** still render as monograms — mostly foreign banks and
  bus operators. `design/audit_logos.py` lists them.
- **12 duplicate drawable names** across `drawable/` and `drawable-nodpi/` ship
  twice in the APK.
- **`TextFieldLabelPosition.Cutout`** blocked behind the `TextFieldState`
  migration.
- **Teal on paper** caps at ~3.4:1. A palette decision, not a bug, but it
  constrains where teal text may be used.
- **Logo mark** not yet chosen; the launcher icon is still the placeholder
  padlock, and `<monochrome>` points at a two-colour drawable, which renders as
  a filled blob under Android 13+ themed icons.
