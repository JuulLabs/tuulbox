buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version "1.5.10" apply false
    id("com.android.library") version "4.1.3" apply false
    id("kotlinx-atomicfu") version "0.16.1" apply false
    id("org.jmailen.kotlinter") version "3.4.4" apply false
    id("binary-compatibility-validator") version "0.2.3"
    id("org.jetbrains.dokka") version "1.4.32"
    id("com.vanniktech.maven.publish") version "0.15.1" apply false
    id("net.mbonnin.one.eight") version "0.2"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    tasks.withType<Test>().configureEach {
        testLogging {
            events("started", "passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            showExceptions = true
            showStackTraces = true
            showCauses = true
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("gh-pages"))
}
