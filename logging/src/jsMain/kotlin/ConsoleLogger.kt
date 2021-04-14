package com.juul.tuulbox.logging

/**
 * Console logger for JS.
 *
 * - Calls to [verbose] and [debug] are mapped to `console.debug(...)`.
 * - Calls to [info] are mapped to `console.info(...)`.
 * - Calls to [warn] are mapped to `console.warn(...)`.
 * - Calls to [error] are mapped to `console.error(...)`.
 * - Calls to [assert] are mapped to `console.assert(false, ...)`
 */
public actual object ConsoleLogger : Logger {

    private val simpleMessageFormat = "[%s] %s"
    private val throwableMessageFormat = "[%s] %s: %o"

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.asDynamic().debug(simpleMessageFormat, tag, message)
        } else {
            console.asDynamic().debug(throwableMessageFormat, tag, message, throwable)
        }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.asDynamic().debug(simpleMessageFormat, tag, message)
        } else {
            console.asDynamic().debug(throwableMessageFormat, tag, message, throwable)
        }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.info(simpleMessageFormat, tag, message)
        } else {
            console.info(throwableMessageFormat, tag, message, throwable)
        }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.warn(simpleMessageFormat, tag, message)
        } else {
            console.warn(throwableMessageFormat, tag, message, throwable)
        }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.error(simpleMessageFormat, tag, message)
        } else {
            console.error(throwableMessageFormat, tag, message, throwable)
        }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        if (throwable == null) {
            console.asDynamic().assert(false, simpleMessageFormat, tag, message)
        } else {
            console.asDynamic().assert(false, throwableMessageFormat, tag, message, throwable)
        }
    }
}
