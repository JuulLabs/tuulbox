import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import java.net.URI

buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    kotlin("multiplatform") version "1.4.10" apply false
    id("org.jmailen.kotlinter") version "3.2.0" apply false
    id("binary-compatibility-validator") version "0.2.3"
    id("net.mbonnin.one.eight") version "0.1"
}

subprojects {
    repositories {
        jcenter()
        maven {
            url = URI("https://maven.pkg.github.com/juullabs/android-github-packages")
            credentials {
                username = findProperty("github.packages.username") as String
                password = findProperty("github.packages.password") as String
            }
        }
    }

    tasks.withType<Test>().configureEach {
        testLogging {
            events("started", "passed", "skipped", "failed", "standardOut", "standardError")
            exceptionFormat = FULL
            showExceptions = true
            showStackTraces = true
            showCauses = true
        }
    }
}

// Prevent publishing if `version` property is not set (e.g. via `-Pversion` command line argument).
gradle.taskGraph.whenReady {
    allTasks
        .filter { task -> task.name.startsWith("publish") }
        .forEach { task ->
            task.doFirst {
                if (!project.hasProperty("version") || project.findProperty("version") == "unspecified") {
                    throw GradleException("Unable to publish without version property")
                }
            }
        }
}
