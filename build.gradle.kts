buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // Workaround for:
        // > Incompatible version of Kotlin metadata.
        // > Maximal supported Kotlin metadata version: 1.5.1,
        // > com/juul/tuulbox/collections/SynchronizedMap Kotlin metadata version: 1.7.1.
        // > As a workaround, it is possible to manually update 'kotlinx-metadata-jvm' version in your project.
        //
        // todo: Remove when binary-compatibility-validator bundles support for metadata 1.7.x.
        classpath("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2")
    }
}

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.atomicfu) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.android.publish) apply false
    alias(libs.plugins.one.eight)
    alias(libs.plugins.binary.compatibility.validator)
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
            toolVersion = libs.versions.jacoco.get()
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
