package com.juul.tuulbox.logging

internal actual val defaultTagGenerator: TagGenerator = StackTraceTagGenerator

internal object StackTraceTagGenerator : TagGenerator, HideFromStackTraceTag {
    // Pattern without string escaping (as would work w/ tools such as https://regexr.com/): (\$\$Lambda)?\$([0-9\/]+)$
    private val anonymousClassPattern = Regex("(\\\$\\\$Lambda)?\\\$([0-9\\/]+)\\\$").toPattern()

    private val ignoreSubclassesOf = HideFromStackTraceTag::class.java

    override fun getTag(): String {
        val tagCandidate = Throwable().stackTrace.asSequence()
            .map { Class.forName(it.className) }
            .first { !ignoreSubclassesOf.isAssignableFrom(it) }
            .name
            .substringAfterLast('.')
        // This bit of logic is stolen from Timber, so use of Java's Pattern/Matcher
        // instead of Kotlin's Regex is to guarantee exact behavior match.
        val matcher = anonymousClassPattern.matcher(tagCandidate)
        return if (matcher.find()) {
            matcher.replaceAll("")
        } else {
            tagCandidate
        }
    }
}
