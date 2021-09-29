object kotlinx {

    fun coroutines(
        module: String = "core",
        version: String = "1.5.2"
    ) = "org.jetbrains.kotlinx:kotlinx-coroutines-$module:$version"

    fun datetime(
        version: String = "0.2.1"
    ) = "org.jetbrains.kotlinx:kotlinx-datetime:$version"
}

object androidx {

    fun startup(
        version: String = "1.0.0"
    ) = "androidx.startup:startup-runtime:$version"
}

object ktor {
    private const val VERSION = "1.6.3"

    fun client(
        module: String,
        version: String = VERSION
    ) = "io.ktor:ktor-client-$module:$version"

    fun server(
        module: String,
        version: String = VERSION
    ) = "io.ktor:ktor-server-$module:$version"
}
