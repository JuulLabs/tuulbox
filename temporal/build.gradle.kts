plugins {
    id("com.android.library")
    kotlin("multiplatform")
    id("org.jmailen.kotlinter")
    jacoco
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish")
}

apply(from = rootProject.file("gradle/jacoco.gradle.kts"))

kotlin {
    explicitApi()

    jvm()
    js().browser {
        testTask {
            useMocha {
                timeout = "6000ms"
            }
        }
    }
    android {
        publishAllLibraryVariants()
    }

    targets.all {
        compilations.all {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.time.ExperimentalTime"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(projects.test)
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(projects.coroutines)
            }
        }

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }

        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
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
