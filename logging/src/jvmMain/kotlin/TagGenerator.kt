package com.juul.tuulbox.logging

internal actual object TagGenerator {
    private val anonymousClassPattern = Regex("""(\$\d+)$""").toPattern()

    private val ignoreClasses = setOf(
        Log::class.java.name,
        TagGenerator::class.java.name
    )

    actual fun getTag(): String {
        val tagCandidate = Throwable().stackTrace
            .first { it.className !in ignoreClasses }
            .className
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
