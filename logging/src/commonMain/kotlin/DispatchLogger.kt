package com.juul.tuulbox.logging

import co.touchlab.stately.isolate.IsolateState

/** Implementation of [Logger] which dispatches calls to consumer [Logger]s. */
public class DispatchLogger : Logger {
    private val consumers = IsolateState { mutableSetOf<Logger>() }

    /** `false` if no consumers have been installed, `true` if at least one consumer has been installed. */
    internal val hasConsumers: Boolean
        get() = consumers.access { it.isNotEmpty() }

    /** Add a consumer to receive future dispatch calls. */
    public fun install(consumer: Logger) {
        consumers.access { it.add(consumer) }
    }

    /** Uninstall all installed consumers. */
    public fun clear() {
        consumers.access { it.clear() }
    }

    /** Dispose of this dispatch logger when it is no longer needed. */
    public fun dispose() {
        consumers.dispose()
    }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.verbose(tag, message, throwable) } }
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.debug(tag, message, throwable) } }
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.info(tag, message, throwable) } }
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.warn(tag, message, throwable) } }
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.error(tag, message, throwable) } }
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        consumers.access { it.forEach { it.assert(tag, message, throwable) } }
    }
}
