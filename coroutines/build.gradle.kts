plugins {
    id("com.android.kotlin.multiplatform.library")
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    jacoco
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

apply(from = rootProject.file("gradle/jacoco.gradle.kts"))

kotlin {
    explicitApi()
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    jvm()
    js().browser()
    android {
        namespace = "com.juul.tuulbox.coroutines"
        compileSdk = libs.versions.android.compile.get().toInt()
        minSdk = 16
        aarMetadata {
            minCompileSdk = 16
        }

        androidResources { enable = true }

        withHostTest {}

        lint {
            abortOnError = true
            warningsAsErrors = true
            disable += "GradleDependency"
        }
    }
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    wasmJs().browser()

    applyDefaultHierarchyTemplate()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
        }

        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.android)
                implementation(libs.androidx.core)
                implementation(libs.androidx.startup)
            }
        }
    }
}
