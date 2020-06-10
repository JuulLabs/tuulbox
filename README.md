## Modules

Library is organized into the following modules:

| Type                            | Module                       | Artifact ID     | Description                        |
|:-------------------------------:|:----------------------------:|:---------------:|------------------------------------|
| ![Kotlin](artwork/kotlin.png)   | [`collections`](collections) | [`collections`] | Tools/utilities for [Collections]. |
| ![Kotlin](artwork/kotlin.png)   | [`coroutines`](coroutines) | [`coroutines`] | Tools/utilities for [Coroutines]. |


All artifacts have the Maven group ID: `com.juul.tuulbox`

## Installation

### Gradle

Artifacts are hosted on GitHub packages, which can be configured as follows:

```groovy
repositories {
    maven {
        url = "https://maven.pkg.github.com/juullabs/android-github-packages"
        credentials {
            username = findProperty('github.packages.username')
            password = findProperty('github.packages.password')
        }
    }
}
```

Then the needed artifact(s) can be defined as dependencies:

```groovy
dependencies {
    implementation 'com.juul.tuulbox:$artifactId:$version'
}
```

_Replace_ `$artifactId` _with the desired **Artifact ID** (as listed in the [Modules](#modules)
table above) and_ `$version` _with the desired version (can be found by following the corresponding
**Artifact ID** link in the [Modules](#modules) table above)._


[`collections`]: https://github.com/JuulLabs/android-github-packages/packages/215143
[Collections]: https://kotlinlang.org/docs/reference/collections-overview.html
[`collections`]: https://github.com/JuulLabs/android-github-packages/packages/215143
[`coroutines`]: https://github.com/JuulLabs/android-github-packages/packages/215143
[Coroutines]: https://kotlinlang.org/docs/reference/coroutines-overview.html

