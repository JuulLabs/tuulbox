pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "binary-compatibility-validator") {
                useModule("org.jetbrains.kotlinx:binary-compatibility-validator:${requested.version}")
            } else if (requested.id.id == "kotlinx-atomicfu") {
                useModule("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${requested.version}")
            }
        }
    }
}

include(
    "collections",
    "coroutines",
    "logging",
    "functional",
    "test"
)
