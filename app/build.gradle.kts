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
        // The identity Play Console reserved. It is deliberately NOT the
        // namespace above: namespace only names the generated R/BuildConfig and
        // resolves the manifest's relative class names, so renaming it would
        // move every source file for no gain. Resources still resolve, because
        // the merged manifest's package — and therefore context.packageName —
        // follows applicationId, which is exactly how applicationIdSuffix works.
        applicationId = "com.zerokosh.app"
        minSdk = 26
        targetSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        versionCode = 4 // fixed per release — reproducible builds (§6.8)
        versionName = "0.4.0"

        // JNA's aar still carries armeabi, mips and mips64. Android dropped
        // MIPS at NDK r17 and armeabi at r16; no device Play can reach runs
        // them, so they were 398 KB of libsodium and libjnidispatch that
        // nothing could ever load. Play splits the bundle per ABI so users were
        // spared the download, but the direct APK — F-Droid, GitHub — was not.
        ndk {
            abiFilters += setOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
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
            // Shrinking, obfuscation and resource shrinking all stay on — this
            // variant exists to test the shape that ships, and the monogram
            // fallback bug only ever appeared with R8 running. Debuggable is
            // the one difference: without it there is no run-as, so the vault
            // file cannot be made unwritable and the save-failure path cannot
            // be exercised on a real device at all. Never published.
            isDebuggable = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        // So the About row and the welcome screen can read the real version
        // instead of each carrying its own hand-typed copy.
        buildConfig = true
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

    // §6.2 addition: inline autofill suggestions — the row that appears in the
    // keyboard strip, which is the whole visible difference between this and
    // Google Password Manager. The platform takes an InlinePresentation backed
    // by a Slice whose layout is defined by androidx.autofill.inline; building
    // that Slice by hand means hard-coding an internal, versioned contract, so
    // this is the one case where the library IS the supported API rather than a
    // convenience over it. ~40 KB, no transitive weight.
    implementation("androidx.autofill:autofill:1.3.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.security:security-crypto:1.1.0") // prefs only, never the vault (§6.2)
    implementation("androidx.documentfile:documentfile:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("androidx.work:work-runtime-ktx:2.9.0") // reminders + clipboard-clear fallback

    // §6.2 addition (approved): process-level lifecycle for auto-lock.
    // "Lock when I leave the app" was bookkept by hand in MainActivity, which
    // could only ever see its own Activity. An adversarial review of the
    // session work found the hole that leaves: nothing re-locks a process that
    // has no Activity in it, and the autofill service is exactly that process.
    // ProcessLifecycleOwner is the supported way to ask whether the app — not
    // one screen of it — is in the foreground, and it debounces the Activity
    // hand-offs the hand-rolled version had to special-case.
    implementation("androidx.lifecycle:lifecycle-process:2.11.0")

    // §6.2 addition (approved): Credential Manager, as a *provider*.
    // On Android 14+ an app that signs users in through CredentialManager never
    // consults AutofillService at all, so Zerokosh was simply absent from those
    // flows — and that share only grows as apps move to passkeys. This is the
    // provider half of the library (androidx.credentials.provider), which is
    // API 34+; autofill remains the path for 26–33 and both work side by side
    // above that.
    //
    // Deliberately NOT credentials-play-services-auth: that is the client-side
    // Google Sign-In helper, it pulls in Play Services, and it would make the
    // F-Droid build non-free. Nothing here needs it.
    implementation("androidx.credentials:credentials:1.6.0")

    // §6.2 addition (approved), instrumented tests. Test-only configurations,
    // so none of this reaches the APK.
    //
    // Every defect worth finding this month was only findable on a device: the
    // input-type test that read every email field as the password, an unlock
    // prompt that was invisible on any phone with a modern keyboard, an
    // authentication Intent that could not return a result. Unit tests could
    // not have caught any of them, and none of them needed a person to find —
    // only a device.
    androidTestImplementation(platform("androidx.compose:compose-bom:2026.03.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test:runner:1.7.0")
    androidTestImplementation("androidx.test:rules:1.7.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    // Supplies the empty activity the Compose test rule launches into. Debug
    // only, which is why it is debugImplementation and not androidTest*.
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Test only — never in the shipped APK, so outside the §6.2 runtime list.
    // The app module had no test source set at all, which is why every defect
    // this month was found by looking at the app rather than by a build.
    testImplementation(kotlin("test-junit"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // TOTP QR scanning only (§6.2)
    implementation("androidx.camera:camera-camera2:1.6.1")
    implementation("androidx.camera:camera-lifecycle:1.6.1")
    implementation("androidx.camera:camera-view:1.6.1")
    implementation("com.google.zxing:core:3.5.3")
}

// #region Dynamic-resource guard
// A resource reached only through Resources.getIdentifier is invisible to the
// resource shrinker: it reads R.<type>.<name> in code and @type/name in XML, and
// a composed string is neither. It therefore deleted every logo drawable and 107
// of 119 template label strings from the release build — 0 of 593 logos survived
// — while debug, which never shrinks, looked perfect. That shipped.
//
// keep.xml fixes the instance. This fixes the class: the shrinker already writes
// down what it removed, so read its own report back and fail the build rather
// than let a green build produce a wrong artifact again. Same reasoning as
// deadComposables below — when the toolchain is silent, the check has to live
// at project level.
val dynamicResourcePrefixes = listOf("drawable:logo_", "string:tpl_")

// One guard per shrinking variant, each reading only its own report: a single
// task walking every report would fail on a stale one left by a variant this
// invocation never built.
fun registerResourceGuard(variant: String) {
    val report = layout.buildDirectory.file("outputs/mapping/$variant/resources.txt")
    val prefixes = dynamicResourcePrefixes
    val guard = tasks.register("verify${variant.replaceFirstChar(Char::titlecase)}DynamicResources") {
        group = "verification"
        description = "Fails if the shrinker removed a $variant resource only reached by name."
        outputs.upToDateWhen { false }
        doLast {
            val file = report.get().asFile
            if (!file.exists()) return@doLast
            val dropped = file.readLines().filter { line ->
                line.endsWith("is not reachable.") && prefixes.any(line::startsWith)
            }
            if (dropped.isNotEmpty()) {
                throw GradleException(
                    "Resource shrinker removed ${dropped.size} $variant resource(s) reached only " +
                        "by name at runtime. They go missing with no error at all — see " +
                        "app/src/main/res/raw/keep.xml." + System.lineSeparator() +
                        dropped.take(8).joinToString(System.lineSeparator()) { "  $it" },
                )
            }
            logger.lifecycle("verifyDynamicResources($variant): ok")
        }
    }
    tasks.matching { it.name in setOf("assemble${variant.replaceFirstChar(Char::titlecase)}", "bundle${variant.replaceFirstChar(Char::titlecase)}") }
        .configureEach { finalizedBy(guard) }
}

registerResourceGuard("release")
registerResourceGuard("releaseCheck")
// #endregion

// #region Template catalogue guard
// The catalogue is spread over five files that must agree, and nothing checks
// that they do. Written as a throwaway script during the field audit, this
// found on its first run that all six Gov ID templates — PAN, Aadhaar,
// Passport, Driving Licence, Voter ID, DigiLocker — had no label strings at
// all: 71 fields had been rendering as humanised ids ("Dob", "Epic number")
// since they were written. Nothing failed, nothing warned.
//
// Same reasoning as deadComposables and verifyDynamicResources below and above:
// when the toolchain is silent, the check has to live in the build.
val templatesJson = file("src/main/assets/templates.json")
val pickersJson = file("src/main/assets/pickers.json")
val templateStrings = file("src/main/res/values/strings_templates.xml")
val baseStrings = file("src/main/res/values/strings.xml")
val resDir = file("src/main/res")
val galleryKt = file("src/main/java/org/zerokosh/app/ui/gallery/TemplateGalleryScreen.kt")
val categoryKt = file("src/main/java/org/zerokosh/app/ui/common/RecordCategory.kt")

val verifyTemplates by tasks.registering {
    group = "verification"
    description = "Fails if templates, labels, categories and pickers disagree."
    val files = listOf(templatesJson, pickersJson, templateStrings, galleryKt, categoryKt, baseStrings)
    inputs.files(rootProject.fileTree(resDir) { include("values-*/strings*.xml") })
    inputs.files(files)
    outputs.upToDateWhen { false }
    // Captured at configuration time: referencing the script's own properties
    // from inside doLast breaks the configuration cache.
    val jsonFile = templatesJson
    val pickerFile = pickersJson
    val stringsFile = templateStrings
    // English is generated from, not into, so it never appeared in localePairs
    // — which is how a bare apostrophe in values/ reached aapt unchecked.
    val baseFiles = listOf(baseStrings, templateStrings)
    val galleryFile = galleryKt
    val categoryFile = categoryKt
    // Every values-XX directory is held to the same standard. Discovered rather
    // than listed so adding a language cannot also mean remembering to add it
    // here — the locale that gets forgotten is exactly the one that ships half
    // translated.
    val localeDirs = resDir.listFiles()
        .orEmpty()
        .filter { it.isDirectory && it.name.startsWith("values-") && it.name != "values-night" }
        .sortedBy { it.name }
    val localePairs = localeDirs.flatMap { dir ->
        listOf(
            baseStrings to File(dir, "strings.xml"),
            templateStrings to File(dir, "strings_templates.xml"),
        )
    }
    doLast {
        // A translation is offered in the picker and applied by attachBaseContext,
        // so a key present only in values/ renders as English inside an otherwise
        // translated screen. The template generator writes the English file alone,
        // which means nothing but this stops a new template from quietly shipping
        // half translated in every language at once.
        val stringName = Regex("""<string name="([^"]+)"""")
        fun namesIn(f: File) = stringName.findAll(f.readText()).map { it.groupValues[1] }.toSet()
        val localeProblems = localePairs.flatMap { (base, hi) ->
            if (!hi.exists()) listOf("missing translation file: ${hi.name}")
            else {
                val locale = hi.parentFile.name.removePrefix("values-")
                (namesIn(base) - namesIn(hi)).sorted().map { "not translated into $locale: $it" }
            }
        }

        // aapt trims leading and trailing whitespace from a <string> value unless
        // the value is wrapped in double quotes. Headlines here are split into a
        // lead and an emphasised tail and appended, so a stripped trailing space
        // renders as "Choose yourlanguage." — which is exactly what shipped until
        // it was caught on a device. Cheap to check, invisible until someone reads
        // the screen.
        val spaceRe = Regex("""<string name="([^"]+)">(.*?)</string>""", RegexOption.DOT_MATCHES_ALL)
        val whitespaceProblems = localePairs.flatMap { listOf(it.first, it.second) }
            .filter { it.exists() }
            .flatMap { f ->
                spaceRe.findAll(f.readText()).mapNotNull { m ->
                    val value = m.groupValues[2]
                    val quoted = value.startsWith("\"") && value.endsWith("\"")
                    val trimmed = value != value.trim()
                    if (trimmed && !quoted) {
                        "${f.parentFile.name}/${m.groupValues[1]} has edge whitespace and is not quoted " +
                            "— aapt will strip it"
                    } else {
                        null
                    }
                }.toList()
            }

        // An unescaped apostrophe makes aapt fail with "Can not extract resource
        // from ParsedResource@<hash>" and nothing else — no file, no line, no key.
        // It is not only an English contraction problem: Assamese writes হ'ল and
        // Punjabi writes 'ਤੇ, so a whole locale can fail on ordinary words.
        // values/ included deliberately: it was excluded, and English is the file
        // every translation is generated from, so a bare apostrophe there breaks
        // the build with an error that names neither key nor character.
        val apostropheProblems = (localePairs.flatMap { listOf(it.first, it.second) } + baseFiles)
            .filter { it.exists() }
            .flatMap { f ->
                spaceRe.findAll(f.readText()).mapNotNull { m ->
                    val value = m.groupValues[2]
                    val bare = Regex("""(?<!\\)'""").containsMatchIn(value)
                    if (bare && !(value.startsWith("\"") && value.endsWith("\""))) {
                        "${f.parentFile.name}/${m.groupValues[1]} has an unescaped apostrophe " +
                            "— write \' or aapt fails with an unreadable error"
                    } else {
                        null
                    }
                }.toList()
            }

        @Suppress("UNCHECKED_CAST")
        val catalogue = groovy.json.JsonSlurper().parse(jsonFile) as Map<String, Any>
        val templates = catalogue["templates"] as List<Map<String, Any>>
        @Suppress("UNCHECKED_CAST")
        val pickers = (groovy.json.JsonSlurper().parse(pickerFile) as Map<String, Any>)["pickers"] as Map<String, Any>
        val strings = stringsFile.readText()
        val category = categoryFile.readText()
        val referenced = Regex("""GalleryItem\(\s*"[^"]*",\s*"[^"]*",\s*"([^"]*)"""")
            .findAll(galleryFile.readText()).map { it.groupValues[1] }.toSet()

        val problems = mutableListOf<String>()
        problems += localeProblems
        problems += whitespaceProblems
        problems += apostropheProblems
        val ids = templates.map { it["id"] as String }.toSet()

        (referenced - ids).forEach { problems += "gallery points at unknown template: $it" }
        templates.forEach { t ->
            val id = t["id"] as String
            if (!strings.contains("name=\"tpl_$id\"")) problems += "no title string: tpl_$id"
            @Suppress("UNCHECKED_CAST")
            (t["fields"] as List<Map<String, Any>>).forEach { f ->
                val k = f["k"] as String
                if (!strings.contains("name=\"tpl_${id}_$k\"")) problems += "no label: tpl_${id}_$k"
                val type = f["t"] as String
                if (type.startsWith("PICKER:") && !pickers.containsKey(type.removePrefix("PICKER:"))) {
                    problems += "$id.$k names a picker list that does not exist: $type"
                }
            }
        }
        referenced.forEach { id ->
            if (!category.contains("\"$id\" to RecordCategory")) problems += "not in category map: $id"
        }

        if (problems.isNotEmpty()) {
            throw GradleException(
                "Template catalogue is inconsistent (${problems.size} problem(s)):" +
                    System.lineSeparator() +
                    problems.take(15).joinToString(System.lineSeparator()) { "  $it" },
            )
        }
        logger.lifecycle(
            "verifyTemplates: ok (${templates.size} templates, " +
                "${templates.sumOf { (it["fields"] as List<*>).size }} fields, " +
                "${referenced.size} referenced by the gallery)",
        )
    }
}

tasks.named("check") { dependsOn(verifyTemplates) }
tasks.matching { it.name == "assembleRelease" || it.name == "bundleRelease" }
    .configureEach { dependsOn(verifyTemplates) }
// #endregion

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
