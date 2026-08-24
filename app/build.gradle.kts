// :app — Android UI + platform glue (§6). Dependency list is LOCKED to §6.2;
// adding anything else requires human review (R0.5/R0.7).
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
}

// #region Release signing
// Key material never enters the repository. keystore.properties is gitignored
// and CI supplies the same four values as environment variables instead.
//
// When neither is present the release build stays UNSIGNED on purpose: that is
// what F-Droid needs, since it builds from source and signs with its own key.
// A missing keystore must not be a build failure for anyone but us.
private val keystoreProperties = Properties().apply {
    val file = rootProject.file("keystore.properties")
    if (file.exists()) file.inputStream().use(::load)
}

private fun signingValue(key: String, env: String): String? =
    keystoreProperties.getProperty(key)?.takeIf(String::isNotBlank)
        ?: System.getenv(env)?.takeIf(String::isNotBlank)

android {
    namespace = "org.zerokosh.app"
    compileSdk = 37

    defaultConfig {
        applicationId = "org.zerokosh.app"
        minSdk = 26
        targetSdk = 36
        versionCode = 1 // fixed per release — reproducible builds (§6.8)
        versionName = "0.1.0"
    }

    signingConfigs {
        create("upload") {
            val store = signingValue("storeFile", "ZEROKOSH_STORE_FILE")
            if (store != null) {
                storeFile = rootProject.file(store)
                storePassword = signingValue("storePassword", "ZEROKOSH_STORE_PASSWORD")
                keyAlias = signingValue("keyAlias", "ZEROKOSH_KEY_ALIAS")
                keyPassword = signingValue("keyPassword", "ZEROKOSH_KEY_PASSWORD")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            // Null when no keystore is configured, which leaves the bundle
            // unsigned rather than failing the build.
            signingConfig = signingConfigs.getByName("upload").takeIf { it.storeFile != null }
        }

        // The shipping R8 configuration, installable. Minification is where a
        // release build breaks — lazysodium and the serializers are both
        // reflection-driven — and that cannot be found by building alone.
        //
        // A different applicationId so it sits BESIDE an installed Zerokosh:
        // replacing one signed with a different key means uninstalling first,
        // and uninstalling a vault app destroys the vault.
        create("releaseCheck") {
            initWith(getByName("release"))
            applicationIdSuffix = ".releasecheck"
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks += "release"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources.excludes += setOf("META-INF/AL2.0", "META-INF/LGPL2.1")
    }

    dependenciesInfo {
        // F-Droid reproducibility: no Google-signed dependency metadata blob
        includeInApk = false
        includeInBundle = false
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(project(":core"))

    // BV-24: androidx.biometric 1.1.0 pins androidx.fragment 1.2.5, whose
    // FragmentActivity.startActivityForResult still enforces the legacy
    // "request code must fit in 16 bits" rule. ActivityResultRegistry always
    // generates codes above 65535, so every rememberLauncherForActivityResult
    // call crashed the process. This is a version floor on a library already on
    // the classpath transitively, not a new entry on the §6.2 list.
    constraints {
        implementation("androidx.fragment:fragment:1.8.6") {
            because("FragmentActivity must accept ActivityResultRegistry request codes (BV-24)")
        }
    }

    // §6.2 allowed list — nothing else without R0.5 review.
    // BV-21: 5.1.0 shipped a libsodium.so whose LOAD segment is not 16 KB
    // aligned, which Play now rejects for new releases on Android 15+.
    implementation("com.goterl:lazysodium-android:5.2.0") {
        exclude(group = "net.java.dev.jna", module = "jna") // pulls the desktop jar; we ship the @aar below
    }
    // BV-21: JNA gained 16 KB page alignment in 5.15.
    implementation("net.java.dev.jna:jna:5.17.0@aar")

    val composeBom = platform("androidx.compose:compose-bom:2026.03.01")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    // Material 3 Expressive. The BOM pins material3 1.4.0, which carries the
    // Expressive *tokens* but not the components that consume them — ButtonGroup,
    // ToggleButton, FloatingActionButtonMenu, FloatingToolbar, LoadingIndicator,
    // the wavy progress indicators and MaterialShapes all land in 1.5.0.
    implementation("androidx.compose.material3:material3:1.5.0-alpha25")
    // MaterialShapes / Morph are backed by the shapes library.
    implementation("androidx.graphics:graphics-shapes:1.0.1")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.activity:activity-compose:1.10.1")
    implementation("androidx.navigation:navigation-compose:2.9.8")

    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.security:security-crypto:1.1.0") // prefs only, never the vault (§6.2)
    implementation("androidx.documentfile:documentfile:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("androidx.work:work-runtime-ktx:2.9.0") // reminders + clipboard-clear fallback

    // TOTP QR scanning only (§6.2)
    implementation("androidx.camera:camera-camera2:1.6.1")
    implementation("androidx.camera:camera-lifecycle:1.6.1")
    implementation("androidx.camera:camera-view:1.6.1")
    implementation("com.google.zxing:core:3.5.3")
}

// #region Dead-composable guard
// Nothing in the standard toolchain catches an unreferenced @Composable, which
// is how eight of them accumulated here unnoticed:
//   - "public but unused" is never a compiler warning, because public means a
//     caller outside the module may exist. This is an application module, so no
//     such caller can exist and the compiler's caution is simply wrong here.
//   - unused *private* composables are not flagged either: the Compose compiler
//     plugin rewrites them, so the unused-symbol analysis never fires.
// The check therefore has to live at project level. It adds no dependency,
// which matters because the §6.2 list is locked.
//
// Deliberately conservative: a composable counts as dead only when the sole
// occurrence of its name in the whole tree is its own declaration. Kotlin calls
// take several shapes — Name(...), Name { } with no parens, ::Name — and
// matching only "Name(" reports live code as dead. Failing a build on a false
// positive is worse than missing one, so it counts bare names.
//
// Everything the action needs is captured at configuration time; referencing
// the project from inside doLast breaks the configuration cache.
val composableSources = fileTree("src/main/java") { include("**/*.kt") }
val moduleDir = projectDir

val deadComposables by tasks.registering {
    group = "verification"
    description = "Fails if a @Composable is declared and never referenced."
    val sources = composableSources
    val base = moduleDir
    inputs.files(sources)
    outputs.upToDateWhen { false }
    doLast {
        val decl = Regex(
            """@Composable[^\n]*\n(?:\s*@[\w.]+(?:\([^)]*\))?\s*\n)*""" +
                """\s*(?:internal\s+|private\s+|public\s+)?fun\s+([A-Z][A-Za-z0-9_]*)\s*\(""",
        )
        val texts = sources.files.associateWith { it.readText() }
        val declared = mutableMapOf<String, MutableList<java.io.File>>()
        texts.forEach { (file, src) ->
            decl.findAll(src).forEach { m ->
                declared.getOrPut(m.groupValues[1]) { mutableListOf() }.add(file)
            }
        }
        val dead = declared.filter { (name, sites) ->
            val bare = Regex("""(?<![A-Za-z0-9_])${Regex.escape(name)}(?![A-Za-z0-9_])""")
            texts.values.sumOf { bare.findAll(it).count() } <= sites.size
        }
        if (dead.isNotEmpty()) {
            val listing = dead.entries.sortedBy { it.key }.joinToString("\n") { (name, sites) ->
                "  $name  —  ${sites.first().relativeTo(base)}"
            }
            throw GradleException(
                "${dead.size} @Composable(s) declared and never referenced:\n$listing\n\n" +
                    "Delete them, or call them. An unreferenced composable still ships in the APK.",
            )
        }
        println("deadComposables: none (${declared.size} composables checked)")
    }
}
tasks.named("check") { dependsOn(deadComposables) }
// #endregion
