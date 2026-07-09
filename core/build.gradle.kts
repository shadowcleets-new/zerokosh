// :core is pure Kotlin/JVM (§6.1): model, crypto wrapper, vault file, merge.
// NO Android imports. Runs the §3.6 / §11.2 conformance suite on the JVM.
// NOT a Kotlin Multiplatform module; MUST NOT gain KMP targets (R0.2 item 8).
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    // Test-only (excluded from the R0.7 budget — see DECISIONS.md D-001):
    // desktop libsodium binding to run the §3.6 conformance vectors on the JVM.
    testImplementation(kotlin("test-junit"))
    testImplementation("com.goterl:lazysodium-java:5.1.4")
    testImplementation("net.java.dev.jna:jna:5.14.0")
    testRuntimeOnly("org.slf4j:slf4j-nop:2.0.13")
}
