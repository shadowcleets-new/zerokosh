/**
 * @file build.gradle.kts (:baselineprofile)
 * @description Records which code Zerokosh runs at startup and on the way into
 *              the vault, so release builds ship it precompiled.
 *
 * Without a profile, ART interprets and JIT-compiles that code on every cold
 * start until the phone gets round to compiling it in the background — which
 * Play eventually helps with through cloud profiles, and F-Droid and GitHub
 * installs never get at all.
 *
 * Generation runs ONLY on the Gradle-managed emulator below, never on a
 * connected phone (useConnectedDevices = false). A second guard sits behind
 * that one: the profiled build has its own applicationId, so even on a phone
 * it would install beside a real Zerokosh, and the uninstall that follows
 * every test run would remove only itself. Uninstalling a vault app destroys
 * the vault; neither guard alone is worth trusting with that.
 *
 *   ./gradlew :app:generateBaselineProfile
 *
 * writes app/src/release/generated/baselineProfiles/, which is committed. A
 * plain release build only reads those files, so F-Droid never runs this.
 */
plugins {
    id("com.android.test")
    id("androidx.baselineprofile")
}

android {
    namespace = "org.zerokosh.baselineprofile"
    compileSdk = 37

    defaultConfig {
        // 28 is where the profile rule can run at all; the managed device is 34.
        minSdk = 28
        targetSdk = 36
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    targetProjectPath = ":app"
    // The generator drives the app from its own process, so the two APKs need
    // not share a signature.
    experimentalProperties["android.experimental.self-instrumenting"] = true

    testOptions.managedDevices.localDevices {
        create("pixel6Api34") {
            device = "Pixel 6"
            apiLevel = 34
            // Plain AOSP rather than the stripped-down test image, which drops
            // services the profile rule relies on.
            systemImageSource = "aosp"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

baselineProfile {
    managedDevices += "pixel6Api34"
    useConnectedDevices = false
}

dependencies {
    implementation("androidx.test.ext:junit:1.3.0")
    implementation("androidx.benchmark:benchmark-macro-junit4:1.5.0")
}
