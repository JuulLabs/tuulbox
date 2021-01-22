object kotlinx {

    fun coroutines(
        module: String = "core",
        version: String = "1.4.0"
    ) = "org.jetbrains.kotlinx:kotlinx-coroutines-$module:$version"
}

object androidx {

    fun startup(
        version: String = "1.0.0"
    ) = "androidx.startup:startup-runtime:$version"
}
