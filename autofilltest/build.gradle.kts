/**
 * @file build.gradle.kts (:autofilltest)
 * @description A throwaway app whose only job is to have a login form that
 *              belongs to somebody else.
 *
 * Autofill cannot be tested from inside the app that provides the service:
 * Android does not offer a service its own package, so a form living in
 * :app is never filled no matter what the service returns. Chrome is no good
 * either — it answers web forms with its own password manager unless the user
 * goes and changes a Chrome setting.
 *
 * Hence a second applicationId. Debug only, never published, not part of the
 * F-Droid or Play build, which only ever build :app.
 */
plugins {
    // AGP 9 brings its own Kotlin support, so this is the whole list — the
    // same one :app uses. Version comes from the root build script.
    id("com.android.application")
}

android {
    namespace = "org.zerokosh.autofilltest"
    compileSdk = 37

    defaultConfig {
        applicationId = "org.zerokosh.autofilltest"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // The client half of Credential Manager, so this throwaway app can ask the
    // system for a credential the way a real sign-in screen would — which is
    // the only way to exercise Zerokosh's provider from outside.
    implementation("androidx.credentials:credentials:1.6.0")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}
