package com.juul.tuulbox.logging

import com.juul.tuulbox.logging.Log.dispatcher
import kotlinx.atomicfu.atomic
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal // Thread local pool means that metadata returned from it are safe to mutate on that same thread.
private val metadataPool = Pool(factory = ::Metadata, refurbish = Metadata::clear)

/** Global logging object. To receive logs, call [dispatcher].[install][DispatchLogger.install]. */
public object Log {

    /** Global log dispatcher. */
    public val dispatcher: DispatchLogger = DispatchLogger()

    private val atomicTagGenerator = atomic(defaultTagGenerator)

    /** Global tag generator for log calls without explicit tag. */
    public var tagGenerator: TagGenerator
        get() = atomicTagGenerator.value
        set(value) {
            atomicTagGenerator.value = value
        }

    /** Send a verbose-level log message to the global dispatcher. */
    public fun verbose(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.verbose(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send a debug-level log message to the global dispatcher. */
    public fun debug(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.debug(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an info-level log message to the global dispatcher. */
    public fun info(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.info(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an warn-level log message to the global dispatcher. */
    public fun warn(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.warn(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an error-level log message to the global dispatcher. */
    public fun error(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.error(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }

    /** Send an assert-level log message to the global dispatcher. */
    public fun assert(throwable: Throwable? = null, tag: String? = null, message: (WriteMetadata) -> String) {
        if (dispatcher.hasConsumers) {
            val metadata = metadataPool.borrow()
            val body = message(metadata)
            dispatcher.assert(tag ?: tagGenerator.getTag(), body, metadata, throwable)
            metadataPool.recycle(metadata)
        }
    }
}
