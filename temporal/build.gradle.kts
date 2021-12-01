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
                implementation(project(":test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(project(":coroutines"))
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
    compileSdkVersion(libs.versions.android.compile.get())

    defaultConfig {
        minSdkVersion(libs.versions.android.min.get())
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
