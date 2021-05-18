package com.juul.tuulbox.logging

internal actual val defaultTagGenerator: TagGenerator = StackTraceTagGenerator

internal object StackTraceTagGenerator : TagGenerator, HideFromStackTraceTag {

    /**
     * Matches a line of stacktrace output (except the first line).
     * Provides a capture group for the class name.
     *
     * FIXME: This could probably be much more robust, but at least it's not nothing.
     */
    private val stackTraceLineMatcher = Regex("""^\s{4}at (\w+)\..*$""")

    override fun getTag(): String {
        // FIXME: This should implement support for [HideFromStackTrace] instead of hard-coding a known depth
        val line = Throwable().stackTraceToString().lineSequence()
            .drop(3)
            .first()

        return when (val match = stackTraceLineMatcher.matchEntire(line)) {
            null -> line // not a good tag, but makes sure we don't lose information in the failure case
            else -> match.groupValues[1]
        }
    }
}
