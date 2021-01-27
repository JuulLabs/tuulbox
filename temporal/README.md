![badge-android]
![badge-js]
![badge-jvm]

# Coroutines

Toolbox of utilities for dates and times, building on [KotlinX DateTime].

## Setup

### Gradle

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.juul.tuulbox/temporal/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.juul.tuulbox/temporal)

Coroutines toolbox can be configured via Gradle Kotlin DSL as follows:

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
    js().browser() // and/or js().node()
    jvm() // or android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.juul.tuulbox:temporal:$version")
            }
        }
    }
}

android {
    // If this is an application module and your minimum API version is below 26, enable core library
    // desugaring. See https://developer.android.com/studio/write/java8-support#library-desugaring
}
```

#### Platform-specific

```kotlin
repositories {
    jcenter() // or mavenCentral()
}

dependencies {
    implementation("com.juul.tuulbox:temporal-$platform:$version")
}
```

_Where `$platform` represents (should be replaced with) the desired platform dependency (e.g. `jvm`)._

#### Android Notes

Because this is built on top of KotlinX DateTime, [core library desugaring] must be enabled for your project 

[core library desugaring]: https://developer.android.com/studio/write/java8-support#library-desugaring
[KotlinX DateTime]: https://github.com/Kotlin/kotlinx-datetime

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
