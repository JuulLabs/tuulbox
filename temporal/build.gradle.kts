import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

plugins {
    id("com.android.library")
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
    js {
        browser {
            testTask {
                useKarma {
                    timeout = 6.seconds.toJavaDuration()
                    useChromeHeadless()
                }
            }
        }
    }
    androidTarget().publishLibraryVariants("debug", "release")
    wasmJs().browser()

    applyDefaultHierarchyTemplate()

    sourceSets {
        all {
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }

        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.test)
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(projects.coroutines)
            }
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = 16

    namespace = "com.juul.tuulbox.temporal"

    lint {
        abortOnError = true
        warningsAsErrors = true
        disable += "GradleDependency"
    }
}
