package com.juul.tuulbox.logging

/** Classes which implement [Logger] can write logs. */
public interface Logger {
    /** Log at verbose-level. */
    public fun verbose(tag: String, message: String, throwable: Throwable?)
    /** Log at debug-level. */
    public fun debug(tag: String, message: String, throwable: Throwable?)
    /** Log at info-level. */
    public fun info(tag: String, message: String, throwable: Throwable?)
    /** Log at warn-level. */
    public fun warn(tag: String, message: String, throwable: Throwable?)
    /** Log at error-level. */
    public fun error(tag: String, message: String, throwable: Throwable?)
    /** Log at assert-level. */
    public fun assert(tag: String, message: String, throwable: Throwable?)
}
