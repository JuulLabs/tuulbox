plugins {
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()

    jvm()
    js().browser()
    macosX64()
    iosX64()
    iosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test-common"))
                api(kotlinx.coroutines())
                api(kotlinx.datetime())
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

        val iosX64Main by getting {
            dependsOn(appleMain)
        }

        val iosArm64Main by getting {
            dependsOn(appleMain)
        }
    }
}
