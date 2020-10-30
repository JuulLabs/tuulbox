plugins {
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    `maven-publish`
}

apply(from = rootProject.file("gradle/publish.gradle.kts"))

kotlin {
    explicitApi()

    jvm()
    js().browser()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlinx.coroutines())
            }
        }
    }
}
