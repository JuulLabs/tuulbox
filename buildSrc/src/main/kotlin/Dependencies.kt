object kotlinx {

    fun coroutines(
        module: String = "core",
        version: String = "1.4.0"
    ) = "org.jetbrains.kotlinx:kotlinx-coroutines-$module:$version"

    fun datetime(
        version: String = "0.1.1"
    ) = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
}

object androidx {

    fun startup(
        version: String = "1.0.0"
    ) = "androidx.startup:startup-runtime:$version"
}
