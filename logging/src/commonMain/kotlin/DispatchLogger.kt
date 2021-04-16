package com.juul.tuulbox.logging

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

/** Implementation of [Logger] which dispatches calls to consumer [Logger]s. */
public class DispatchLogger : Logger {
    private val consumers = atomic(emptySet<Logger>())

    /** `false` if no consumers have been installed, `true` if at least one consumer has been installed. */
    internal val hasConsumers: Boolean
        get() = consumers.value.isNotEmpty()

    /** Add a consumer to receive future dispatch calls. */
    public fun install(consumer: Logger) {
        consumers.update { it + consumer }
    }

    /** Uninstall all installed consumers. */
    public fun clear() {
        consumers.value = emptySet()
    }

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.verbose(tag, message, metadata, throwable) }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.debug(tag, message, metadata, throwable) }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.info(tag, message, metadata, throwable) }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.warn(tag, message, metadata, throwable) }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.error(tag, message, metadata, throwable) }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        consumers.value.forEach { it.assert(tag, message, metadata, throwable) }
    }
}
