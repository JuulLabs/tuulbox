plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())

    android().publishAllLibraryVariants()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(projects.logging)
            }
        }
    }
}

android {
    // Workaround (for `jvmToolchain` not being honored) needed until AGP 8.1.0-alpha09.
    // https://kotlinlang.org/docs/gradle-configure-project.html#gradle-java-toolchains-support
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = 16

    namespace = "com.juul.tuulbox.logging"

    lint {
        abortOnError = true
        warningsAsErrors = true
    }
}
