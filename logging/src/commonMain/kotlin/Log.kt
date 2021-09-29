package com.juul.tuulbox.logging

import com.juul.tuulbox.logging.Log.dispatcher
import kotlin.native.concurrent.ThreadLocal
import kotlinx.atomicfu.atomic

@ThreadLocal // Thread local pool means that metadata returned from it are safe to mutate on that same thread.
private val metadataPool = Pool(factory = ::Metadata, refurbish = Metadata::clear)

/** Global logging object. To receive logs, call [dispatcher].[install][DispatchLogger.install]. */
public object Log : HideFromStackTraceTag {

    /** Global log dispatcher. */
    public val dispatcher: DispatchLogger = DispatchLogger()

    private val atomicTagGenerator = atomic(defaultTagGenerator)

    /** Global tag generator for log calls without explicit tag. */
    public var tagGenerator: TagGenerator
        get() = atomicTagGenerator.value
        set(value) {
            atomicTagGenerator.value = value
        }

    /** Send a log message at a dynamic [level] to the global dispatcher. */
    public fun dynamic(level: LogLevel, throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        when (level) {
            LogLevel.Verbose -> verbose(throwable, tag, message)
            LogLevel.Debug -> debug(throwable, tag, message)
            LogLevel.Info -> info(throwable, tag, message)
            LogLevel.Warn -> warn(throwable, tag, message)
            LogLevel.Error -> error(throwable, tag, message)
            LogLevel.Assert -> assert(throwable, tag, message)
        }
    }

    /** Send a verbose-level log message to the global dispatcher. */
    public fun verbose(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Verbose)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.verbose(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send a debug-level log message to the global dispatcher. */
    public fun debug(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Debug)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.debug(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an info-level log message to the global dispatcher. */
    public fun info(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Info)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.info(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an warn-level log message to the global dispatcher. */
    public fun warn(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Warn)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.warn(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an error-level log message to the global dispatcher. */
    public fun error(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Error)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.error(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an assert-level log message to the global dispatcher. */
    public fun assert(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.canLog(LogLevel.Assert)) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.assert(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    private fun DispatchLogger.canLog(level: LogLevel): Boolean =
        hasConsumers && level >= minimumLogLevel
}
