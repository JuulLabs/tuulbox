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

    jvm()
    js().browser()
    macosX64()
    iosX64()
    iosArm32()
    iosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":logging"))
                api(libs.ktor.core)
                api(libs.ktor.logging)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":logging-test"))
                implementation(project(":test"))
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
            dependencies {
                implementation(libs.kotlinx.coroutines.native)
            }
        }

        val macosX64Main by getting {
            dependsOn(appleMain)
        }

        val macosX64Test by getting {
            dependsOn(appleTest)
        }

        val iosX64Main by getting {
            dependsOn(appleMain)
        }

        val iosX64Test by getting {
            dependsOn(appleTest)
        }

        val iosArm32Main by getting {
            dependsOn(appleMain)
        }

        val iosArm32Test by getting {
            dependsOn(appleTest)
        }

        val iosArm64Main by getting {
            dependsOn(appleMain)
        }

        val iosArm64Test by getting {
            dependsOn(appleTest)
        }
    }
}
