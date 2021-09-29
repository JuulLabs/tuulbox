pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        jcenter()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "binary-compatibility-validator" ->
                    useModule("org.jetbrains.kotlinx:binary-compatibility-validator:${requested.version}")
                "kotlinx-atomicfu" ->
                    useModule("org.jetbrains.kotlinx:atomicfu-gradle-plugin:${requested.version}")
                else -> when (requested.id.namespace) {
                    "com.android" ->
                        useModule("com.android.tools.build:gradle:${requested.version}")
                }
            }
        }
    }
}

include(
    "coroutines",
    "logging",
    "logging-android",
    "logging-ktor-client",
    "logging-test",
    "functional",
    "temporal",
    "test",
    "encoding"
)
