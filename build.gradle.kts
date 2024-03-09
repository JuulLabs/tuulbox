buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.atomicfu) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.android.publish) apply false
    alias(libs.plugins.binary.compatibility.validator)
}

allprojects {
    group = "com.juul.tuulbox"

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
            toolVersion = libs.versions.jacoco.get()
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.fileProvider(layout.buildDirectory.file("gh-pages").map { it.asFile })
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
