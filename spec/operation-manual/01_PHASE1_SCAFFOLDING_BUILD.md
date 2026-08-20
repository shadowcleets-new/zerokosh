# Phase 1 — Modular Project Scaffolding & Build Matrix

## 1.1 Module layout (locked by Master Spec §6.1 — exactly two modules)

```
C:\Users\acer\zerokosh\
├── settings.gradle.kts            root; includes :core, :app
├── build.gradle.kts               plugin versions only (migrates to catalog in TASK-102)
├── gradle.properties              jvmargs, androidX flags
├── gradle\wrapper\                Gradle 9.3.1 wrapper (committed)
├── gradle\libs.versions.toml      version catalog (created by TASK-101)
├── core\                          pure Kotlin/JVM — model, crypto iface, vault file, merge,
│   └── src\main\kotlin\org\zerokosh\core\{model,crypto,vault,totp,generator,util,import}\
│   └── src\test\kotlin\org\zerokosh\core\   conformance suite (§3.6/§11.2)
├── app\                           Android UI + platform glue
│   └── src\main\java\org\zerokosh\app\{crypto,data,ui,quickunlock,pdf,reminders,autofill,breach}\
│   └── src\main\assets\           templates.json · pickers.json · bank_rules.json · app_map.json
│   └── src\main\res\              values, values-hi (TASK-701), xml, drawable, mipmap-anydpi-v26
├── spec\                          Master Spec + JSON resources + this manual
├── fixtures\                      vault_android.kosh (§11.2) · chrome_sample.csv
└── l10n\                          glossary_hi.md (created by TASK-703)
```

**RULE:** No new modules. `:core` MUST keep zero Android imports (it runs the conformance suite on the JVM) and MUST NOT become a KMP module (R0.2 item 8).

## 1.2 Version catalog — exact content (TASK-101 writes this file verbatim)

`C:\Users\acer\zerokosh\gradle\libs.versions.toml`:

```toml
[versions]
agp = "8.13.2"
kotlin = "2.2.10"
composeBom = "2026.03.01"
activityCompose = "1.10.1"
navigationCompose = "2.9.8"
biometric = "1.1.0"
securityCrypto = "1.1.0"
documentfile = "1.0.1"
serializationJson = "1.8.1"
workRuntime = "2.9.0"
camerax = "1.6.1"
zxing = "3.5.3"
lazysodiumAndroid = "5.1.0"
lazysodiumJava = "5.1.4"
jna = "5.14.0"
slf4jNop = "2.0.13"

[libraries]
compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
compose-ui = { group = "androidx.compose.ui", name = "ui" }
compose-material3 = { group = "androidx.compose.material3", name = "material3" }
compose-icons-extended = { group = "androidx.compose.material", name = "material-icons-extended" }
activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
biometric = { group = "androidx.biometric", name = "biometric", version.ref = "biometric" }
security-crypto = { group = "androidx.security", name = "security-crypto", version.ref = "securityCrypto" }
documentfile = { group = "androidx.documentfile", name = "documentfile", version.ref = "documentfile" }
serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "serializationJson" }
work-runtime = { group = "androidx.work", name = "work-runtime-ktx", version.ref = "workRuntime" }
camera-camera2 = { group = "androidx.camera", name = "camera-camera2", version.ref = "camerax" }
camera-lifecycle = { group = "androidx.camera", name = "camera-lifecycle", version.ref = "camerax" }
camera-view = { group = "androidx.camera", name = "camera-view", version.ref = "camerax" }
zxing-core = { group = "com.google.zxing", name = "core", version.ref = "zxing" }
lazysodium-android = { group = "com.goterl", name = "lazysodium-android", version.ref = "lazysodiumAndroid" }
lazysodium-java = { group = "com.goterl", name = "lazysodium-java", version.ref = "lazysodiumJava" }
jna = { group = "net.java.dev.jna", name = "jna", version.ref = "jna" }
slf4j-nop = { group = "org.slf4j", name = "slf4j-nop", version.ref = "slf4jNop" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

**RULE:** These are the ONLY versions. Never bump, never add an entry without human review (R0.5). `kotlin-test-junit` in `:core` tests resolves via `kotlin("test-junit")` and stays version-less (bound to the Kotlin plugin).

## 1.3 File inventory & freeze status

FROZEN (modify only via explicit Phase 8 patches): every file under `core/src/main`, `core/src/test`, `app/src/main/java`, `app/src/main/res`, `app/src/main/assets`, `app/src/main/AndroidManifest.xml`, the three root Gradle files, `app/build.gradle.kts`, `core/build.gradle.kts`, `app/proguard-rules.pro`, everything under `spec/` and `fixtures/`.
NEW files are created only where a Phase 8 task gives the complete content.

## 1.4 Build types & variants

| Build type | Exists? | Definition |
|---|---|---|
| `debug` | yes (AGP default) | no changes; debuggable; no applicationIdSuffix |
| `release` | yes | `isMinifyEnabled = true`, `isShrinkResources = true`, proguard files as committed; signing per TASK-804 |
| `staging` or any flavor | **PROHIBITED** | There is no server, no environment, no API key — nothing to stage against (Master Spec R0.2 item 1). Product flavors are banned. |

**BuildConfig:** unused. There are zero `buildConfigField` entries — the app has no base URLs and no API keys, and MUST never gain them. The single external URL (`https://api.pwnedpasswords.com/range/`) is a compile-time constant inside `breach/BreachCheck.kt` per Master Spec §5.9.

**Signing (`release`)** — final state after TASK-804 (keystore itself is generated OFFLINE by the human owner, R0.5):

```kotlin
// app/build.gradle.kts — inside android { }
signingConfigs {
    create("release") {
        val propsFile = rootProject.file("keystore.properties")
        if (propsFile.exists()) {
            val props = java.util.Properties().apply { propsFile.inputStream().use { load(it) } }
            storeFile = rootProject.file(props.getProperty("storeFile"))
            storePassword = props.getProperty("storePassword")
            keyAlias = props.getProperty("keyAlias")
            keyPassword = props.getProperty("keyPassword")
        }
    }
}
```
`keystore.properties` and `*.jks` are gitignored (already). A missing properties file leaves release unsigned — CI builds still verify compilation.

**Reproducibility flags (§6.8, already partly present):** fixed `versionCode` per release, `dependenciesInfo.includeInApk = false`, `includeInBundle = false`, no build timestamps anywhere.

## 1.5 R8 / ProGuard

`app/proguard-rules.pro` is committed and FROZEN. It keeps Lazysodium + JNA (native reflection), kotlinx-serialization serializers for `org.zerokosh.core.**` and `org.zerokosh.app.data.**`, and strips `Log.v`/`Log.d` in release. There are deliberately NO Retrofit/Room/Gson keep rules — those libraries are prohibited (Rules §0.3). If R8 reports a missing-class warning for a new code path, the fix is a `-dontwarn` or `-keep` line appended to this file — never a dependency change.
