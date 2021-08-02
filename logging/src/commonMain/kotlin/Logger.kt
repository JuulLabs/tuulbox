package com.juul.tuulbox.logging

/** Classes which implement [Logger] can write logs. */
public interface Logger {

    /** Minimum level for this logger. Defaults to [LogLevel.Verbose] (all logs) if not overwritten. */
    public val minimumLogLevel: LogLevel
        get() = LogLevel.Verbose

    /** Log at verbose-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at debug-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at info-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at warn-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at error-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)

    /** Log at assert-level. Do not store a reference to [metadata], create a [copy][ReadMetadata.copy] if you need to. */
    public fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?)
}
