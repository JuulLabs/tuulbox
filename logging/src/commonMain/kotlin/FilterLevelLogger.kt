package com.juul.tuulbox.logging

@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("withMinimumLogLevel", "com.juul.khronicle.withMinimumLogLevel"),
)
public fun Logger.withMinimumLogLevel(
    minimum: LogLevel,
): Logger = FilterLevelLogger(minimum, this)

internal class FilterLevelLogger(
    override val minimumLogLevel: LogLevel,
    private val inner: Logger,
) : Logger {

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (minimumLogLevel == LogLevel.Verbose) {
            inner.verbose(tag, message, metadata, throwable)
        }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (minimumLogLevel <= LogLevel.Debug) {
            inner.debug(tag, message, metadata, throwable)
        }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (minimumLogLevel <= LogLevel.Info) {
            inner.info(tag, message, metadata, throwable)
        }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (minimumLogLevel <= LogLevel.Warn) {
            inner.warn(tag, message, metadata, throwable)
        }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (minimumLogLevel <= LogLevel.Error) {
            inner.error(tag, message, metadata, throwable)
        }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        // Since there is no log level higher than assert we can skip the conditional check here.
        inner.assert(tag, message, metadata, throwable)
    }
}
