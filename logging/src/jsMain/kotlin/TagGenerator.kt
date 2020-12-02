package com.juul.tuulbox.logging

internal actual object TagGenerator {
    // FIXME: This could probably be much more robust, but at least it's not nothing.
    private val regex = Regex("""^\s{4}at (\w+)\..*$""")

    actual fun getTag(): String {
        val line = Throwable().stackTraceToString().lineSequence()
            .drop(3)
            .first()

        return when (val match = regex.matchEntire(line)) {
            null -> line // WAY
            else -> match.groupValues[1]
        }
    }
}
