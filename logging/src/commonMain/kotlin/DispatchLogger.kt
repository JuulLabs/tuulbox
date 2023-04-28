package com.juul.tuulbox.logging

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update

private val EMPTY_STATE = DispatcherState(emptySet())

private data class DispatcherState(
    val consumers: Set<Logger>,
) {
    val logLevel: LogLevel by lazy {
        consumers.minOfOrNull { it.minimumLogLevel }
            ?: LogLevel.Assert
    }
}

/** Implementation of [Logger] which dispatches calls to consumer [Logger]s. */
public class DispatchLogger : Logger {

    private val state = atomic(EMPTY_STATE)

    override val minimumLogLevel: LogLevel
        get() = state.value.logLevel

    /** `false` if no consumers have been installed, `true` if at least one consumer has been installed. */
    internal val hasConsumers: Boolean
        get() = state.value.consumers.isNotEmpty()

    /** Add a consumer to receive future dispatch calls. */
    public fun install(consumer: Logger) {
        state.update { DispatcherState(it.consumers + consumer) }
    }

    /** Uninstall all installed consumers. */
    public fun clear() {
        state.value = EMPTY_STATE
    }

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Verbose }
            .forEach { it.verbose(tag, message, metadata, throwable) }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Debug }
            .forEach { it.debug(tag, message, metadata, throwable) }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Info }
            .forEach { it.info(tag, message, metadata, throwable) }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Warn }
            .forEach { it.warn(tag, message, metadata, throwable) }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Error }
            .forEach { it.error(tag, message, metadata, throwable) }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        state.value.consumers.asSequence()
            .filter { it.minimumLogLevel <= LogLevel.Assert }
            .forEach { it.assert(tag, message, metadata, throwable) }
    }
}
