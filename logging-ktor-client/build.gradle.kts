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
        }

        val commonMain by getting {
            dependencies {
                api(projects.logging)
                api(libs.ktor.core)
                api(libs.ktor.logging)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.ktor.mock)
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

        val appleTest by creating {
            dependsOn(commonTest)
        }

        val macosX64Main by getting {
            dependsOn(appleMain)
        }

        val macosX64Test by getting {
            dependsOn(appleTest)
        }

        val macosArm64Main by getting {
            dependsOn(appleMain)
        }

        val macosArm64Test by getting {
            dependsOn(appleTest)
        }

        val iosX64Main by getting {
            dependsOn(appleMain)
        }

        val iosX64Test by getting {
            dependsOn(appleTest)
        }

        val iosArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosArm64Test by getting {
            dependsOn(appleTest)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosSimulatorArm64Test by getting {
            dependsOn(appleTest)
        }
    }
}
