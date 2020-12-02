package com.juul.tuulbox.logging

/** Implementation of [Logger] which dispatches calls to consumer [Logger]s. */
public class DispatchLogger : Logger {
    private val consumers = mutableSetOf<Logger>()

    /** `false` if no consumers have been installed, `true` if at least one consumer has been installed. */
    internal val hasConsumers: Boolean
        get() = consumers.isNotEmpty()

    /** Add a consumer to receive future dispatch calls. */
    public fun install(consumer: Logger) {
        consumers.add(consumer)
    }

    /** Uninstall all installed consumers. */
    public fun clear() {
        consumers.clear()
    }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.verbose(tag, message, throwable) }
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.debug(tag, message, throwable) }
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.info(tag, message, throwable) }
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.warn(tag, message, throwable) }
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.error(tag, message, throwable) }
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        consumers.forEach { it.assert(tag, message, throwable) }
    }
}
