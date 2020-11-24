![badge-js]
![badge-jvm]

# Test

Tool box with utilities for manipulating functions.
For a full functional ecosystem, complete with `Monad` and the like, prefer [Arrow](https://arrow-kt.io/).

## Installation

### Gradle

Artifacts are hosted on GitHub packages, which can be configured as follows:

```kotlin
import java.net.URI

repositories {
    maven {
        url = URI("https://maven.pkg.github.com/juullabs/android-github-packages")
        credentials {
            username = findProperty("github.packages.username") as String
            password = findProperty("github.packages.password") as String
        }
    }
}
```

Then the needed artifact(s) can be defined as dependencies.

**Multiplatform projects**

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            implementation("com.juul.tuulbox:functional:$version")
        }
    }
}
```

**Platform-specific projects**

```kotlin
dependencies {
    implementation("com.juul.tuulbox:functional-$platform:$version")
}
```


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
