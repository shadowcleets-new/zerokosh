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
