enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "binary-compatibility-validator" ->
                    useModule("org.jetbrains.kotlinx:binary-compatibility-validator:${requested.version}")

                else -> when (requested.id.namespace) {
                    "com.android" ->
                        useModule("com.android.tools.build:gradle:${requested.version}")
                }
            }
        }
    }
}

plugins {
    // Provides repositories for auto-downloading JVM toolchains.
    // https://github.com/gradle/foojay-toolchains
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(
    "collections",
    "coroutines",
    "logging",
    "logging-android",
    "logging-ktor-client",
    "functional",
    "temporal",
    "test",
    "encoding",
)
