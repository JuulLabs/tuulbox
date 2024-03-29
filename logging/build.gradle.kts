plugins {
    kotlin("multiplatform")
    id("kotlinx-atomicfu")
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
    macosX64()
    macosArm64()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        all {
            languageSettings.optIn("com.juul.tuulbox.logging.TuulboxInternal")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }

        val commonMain by getting

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
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
