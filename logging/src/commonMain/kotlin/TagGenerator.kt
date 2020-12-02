package com.juul.tuulbox.logging

/** Creates tags to associate with calls to [Log] functions. */
internal expect object TagGenerator {

    /**
     * Return a useful tag. For example, on the JVM this analyzes the
     * stack trace and uses the name of the class making the log call.
     */
    fun getTag(): String
}
