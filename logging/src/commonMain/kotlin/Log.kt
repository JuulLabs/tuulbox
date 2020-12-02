package com.juul.tuulbox.logging

/** Global logging object. To receive logs, call [dispatcher].[install][DispatchLogger.install]. */
public object Log {

    /** Global log dispatcher. */
    public val dispatcher: DispatchLogger = DispatchLogger()

    /** Global tag generator for log calls without explicit tag. */
    public var tagGenerator: TagGenerator = defaultTagGenerator

    /** Send a verbose-level log message to the global dispatcher. */
    public fun verbose(throwable: Throwable? = null, tag: String? = null, message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.verbose(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }

    /** Send a debug-level log message to the global dispatcher. */
    public fun debug(throwable: Throwable? = null, tag: String? = null,  message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.debug(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }

    /** Send an info-level log message to the global dispatcher. */
    public fun info(throwable: Throwable? = null, tag: String? = null,  message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.info(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }

    /** Send an warn-level log message to the global dispatcher. */
    public fun warn(throwable: Throwable? = null, tag: String? = null,  message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.warn(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }

    /** Send an error-level log message to the global dispatcher. */
    public fun error(throwable: Throwable? = null, tag: String? = null,  message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.error(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }

    /** Send an assert-level log message to the global dispatcher. */
    public fun assert(throwable: Throwable? = null, tag: String? = null,  message: () -> String) {
        if (dispatcher.hasConsumers) {
            dispatcher.assert(tag ?: tagGenerator.getTag(), message.invoke(), throwable)
        }
    }
}
