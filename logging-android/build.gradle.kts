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
                api(projects.logging)
            }
        }
    }
}

android {
    compileSdk = libs.versions.android.compile.get().toInt()
    defaultConfig.minSdk = 16

    lint {
        abortOnError = true
        warningsAsErrors = true
    }

    sourceSets {
        getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
    }
}
