plugins {
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    jvm()
    js().browser()
    macosX64()
    macosArm64()
    iosX64()
    iosArm32()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        val commonMain by getting {
            dependencies {
                api(kotlin("test-common"))
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsMain by getting {
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

        val iosArm32Main by getting {
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
