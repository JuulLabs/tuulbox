plugins {
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    jacoco
    `maven-publish`
}

apply(from = rootProject.file("gradle/jacoco.gradle.kts"))
apply(from = rootProject.file("gradle/publish.gradle.kts"))

kotlin {
    explicitApi()

    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinx.coroutines())
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(kotlinx.coroutines("debug"))
            }
        }
    }
}
