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
    js().browser {
        testTask {
            useMocha {
                timeout = "6000ms"
            }
        }
    }
    android {
        namespace = "com.juul.tuulbox.temporal"
        compileSdk = libs.versions.android.compile.get().toInt()
        minSdk = 16

        withHostTest {}

        lint {
            abortOnError = true
            warningsAsErrors = true
            disable += "GradleDependency"
        }
    }
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
