buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    kotlin("multiplatform") version "1.5.30" apply false
    id("com.android.library") version "4.2.2" apply false
    id("kotlinx-atomicfu") version "0.16.3" apply false
    id("org.jmailen.kotlinter") version "3.4.4" apply false
    id("org.jetbrains.dokka") version "1.4.32"
    id("com.vanniktech.maven.publish") version "0.15.1" apply false
    id("net.mbonnin.one.eight") version "0.2"
    id("binary-compatibility-validator") version "0.8.0"
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

    withPluginWhenEvaluated("jacoco") {
        configure<JacocoPluginExtension> {
            toolVersion = "0.8.7"
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("gh-pages"))
}

fun Project.withPluginWhenEvaluated(plugin: String, action: Project.() -> Unit) {
    pluginManager.withPlugin(plugin) { whenEvaluated(action) }
}

// `afterEvaluate` does nothing when the project is already in executed state, so we need a special check for this case.
fun <T> Project.whenEvaluated(action: Project.() -> T) {
    if (state.executed) {
        action()
    } else {
        afterEvaluate { action() }
    }
}
