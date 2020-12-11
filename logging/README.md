![badge-js]
![badge-jvm]
![badge-mac]

# Logging

Tool box for a simple multiplatform logging API.

## Setup

### Gradle

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.juul.tuulbox/logging/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.juul.tuulbox/logging)

Logging toolbox can be configured via Gradle Kotlin DSL as follows:

#### Multiplatform

```kotlin
plugins {
    id("com.android.application") // or id("com.android.library")
    kotlin("multiplatform")
}

repositories {
    jcenter() // or mavenCentral()
}

kotlin {
    android()
    js().browser() // and/or js().node()
    macosX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.juul.tuulbox:logging:$version")
            }
        }
    }
}

android {
    // ...
}
```

#### Platform-specific

```kotlin
repositories {
    jcenter() // or mavenCentral()
}

dependencies {
    implementation("com.juul.tuulbox:logging-$platform:$version")
}
```

_Where `$platform` represents (should be replaced with) the desired platform dependency (e.g. `android`)._


[badge-android]: http://img.shields.io/badge/platform-android-6EDB8D.svg?style=flat
[badge-ios]: http://img.shields.io/badge/platform-ios-CDCDCD.svg?style=flat
[badge-js]: http://img.shields.io/badge/platform-js-F8DB5D.svg?style=flat
[badge-jvm]: http://img.shields.io/badge/platform-jvm-DB413D.svg?style=flat
[badge-linux]: http://img.shields.io/badge/platform-linux-2D3F6C.svg?style=flat
[badge-windows]: http://img.shields.io/badge/platform-windows-4D76CD.svg?style=flat
[badge-mac]: http://img.shields.io/badge/platform-macos-111111.svg?style=flat
[badge-watchos]: http://img.shields.io/badge/platform-watchos-C0C0C0.svg?style=flat
[badge-tvos]: http://img.shields.io/badge/platform-tvos-808080.svg?style=flat
[badge-wasm]: https://img.shields.io/badge/platform-wasm-624FE8.svg?style=flat
