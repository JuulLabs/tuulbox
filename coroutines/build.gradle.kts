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
    js().browser()
    androidTarget().publishLibraryVariants("debug", "release")
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.android)
                implementation(libs.androidx.core)
                implementation(libs.androidx.startup)
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }

        val appleMain by creating {
            dependsOn(commonMain)
        }

        val macosX64Main by getting {
            dependsOn(appleMain)
        }

        val macosArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosX64Main by getting {
            dependsOn(appleMain)
        }

        val iosArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(appleMain)
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = 16
    
    namespace = "com.juul.tuulbox.coroutines"

    lint {
        abortOnError = true
        warningsAsErrors = true
    }
}
