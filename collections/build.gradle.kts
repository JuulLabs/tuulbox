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
    wasmJs().browser()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.collections.immutable)
                api(libs.kotlinx.coroutines.core)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(kotlin("test"))
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
