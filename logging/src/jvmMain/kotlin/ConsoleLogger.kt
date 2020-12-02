package com.juul.tuulbox.logging

import java.io.PrintStream

/**
 * Logger for [System.out] and [System.err].
 *
 * - Calls to [verbose], [debug], [info], and [warn] map to [System.out].
 * - Calls to [error] and [assert] map to [System.err]
 */
public actual object ConsoleLogger : Logger {

    private val simpleMessageFormat = "[%s/%s] %s\n"
    private val throwableMessageFormat = "[%s/%s] %s: %s\n"

    private fun print(stream: PrintStream, severity: String, tag: String, message: String, throwable: Throwable?) {
        if (throwable == null) {
            stream.printf(simpleMessageFormat, severity, tag, message)
        } else {
            stream.printf(throwableMessageFormat, severity, tag, message, throwable.stackTraceToString())
        }
    }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        print(System.out, "V", tag, message, throwable)
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        print(System.out, "D", tag, message, throwable)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        print(System.out, "I", tag, message, throwable)
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        print(System.out, "W", tag, message, throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        print(System.err, "E", tag, message, throwable)
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        print(System.err, "A", tag, message, throwable)
    }
}
