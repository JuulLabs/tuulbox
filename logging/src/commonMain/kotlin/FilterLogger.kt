package com.juul.tuulbox.logging

public fun interface LogFilter {
    public fun canLog(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?): Boolean
}

public fun Logger.withFilter(
    filter: LogFilter
): Logger = FilterLogger(filter, this)

private class FilterLogger(
    private val filter: LogFilter,
    private val inner: Logger,
) : Logger {

    override val minimumLogLevel: LogLevel
        get() = inner.minimumLogLevel

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.verbose(tag, message, metadata, throwable)
        }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.debug(tag, message, metadata, throwable)
        }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.info(tag, message, metadata, throwable)
        }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.warn(tag, message, metadata, throwable)
        }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.error(tag, message, metadata, throwable)
        }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (filter.canLog(tag, message, metadata, throwable)) {
            inner.assert(tag, message, metadata, throwable)
        }
    }
}
