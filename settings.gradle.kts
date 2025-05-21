enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "tuulbox"

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }
}

plugins {
    // Provides repositories for auto-downloading JVM toolchains.
    // https://github.com/gradle/foojay-toolchains
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(
    "collections",
    "coroutines",
    "functional",
    "temporal",
    "test",
    "encoding",
)
