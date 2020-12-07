object kotlinx {

    fun coroutines(
        module: String = "core",
        version: String = "1.4.0"
    ) = "org.jetbrains.kotlinx:kotlinx-coroutines-$module:$version"
}

fun stately(
    module: String,
    version: String = "1.1.0-a1"
) = "co.touchlab:stately-$module:$version"
