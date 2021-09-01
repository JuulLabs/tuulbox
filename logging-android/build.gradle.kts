plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

kotlin {
    explicitApi()

    android {
        publishAllLibraryVariants()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":logging"))
            }
        }
    }
}

android {
    compileSdkVersion(31)

    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(31)
    }

    lintOptions {
        isAbortOnError = true
        isWarningsAsErrors = true
    }

    sourceSets {
        val main by getting {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
    }
}
