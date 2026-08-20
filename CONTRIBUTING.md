# Contributing to Zerokosh

Thank you! Two rules before anything else:

1. **Read `spec/zerokosh_master_build_manual_v2.md` §0 first.** The locked decisions in R0.2 (crypto scheme, vault format, fully-native stacks, dependency lists, no telemetry) are the product. PRs that violate them are closed without review.
2. **DCO sign-off required.** Every commit must carry `Signed-off-by: Your Name <email>` (`git commit -s`), certifying the [Developer Certificate of Origin](https://developercertificate.org/).

## The easiest contributions need no code

- **Add your bank's password rules** — edit `spec/bank_rules.json`. One JSON object per bank: `{"bank":"IDFC FIRST","max_len":20,"min_len":8,"symbols_allowed":"@#$%&*","require_symbol":true,"require_digit":true}`. Cite the bank's published password policy page in the PR description.
- **Add banks/brokers/insurers to pickers** — edit `spec/pickers.json`. Keep lists alphabetized after the seeded top entries.
- **Translations** — join the Weblate project (link in README once live). Hindi terms must follow `/l10n/glossary_hi.md`. A language ships when ≥ 95% translated.
- **App autofill hints** — edit `app_map.json` (package name → matching hints) to make autofill work with more Indian apps.

## Code contributions

- Android code lives here (`core/` pure Kotlin, `app/` Compose). Match the existing style; ktlint-compatible formatting.
- `core/` must stay free of Android imports — it runs the conformance suite on the JVM.
- Anything touching spec §3 (crypto) or §4 (file format) requires maintainer sign-off *before* you start (R0.5) — open an issue first.
- New third-party dependencies are almost always rejected (R0.7 dependency budget, ≤ 15). Test-only dependencies are exempt but still reviewed.
- Every PR must pass `./gradlew :core:test :app:assembleDebug` and must not add any network call (§14).

## Commit / PR checklist

- [ ] `git commit -s` (DCO)
- [ ] `./gradlew :core:test` green
- [ ] No new dependencies (or a linked pre-approved issue)
- [ ] No secrets in logs, toasts, or exception messages
- [ ] Strings externalized (`strings.xml`), never concatenated translations
