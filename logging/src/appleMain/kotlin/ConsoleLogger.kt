package com.juul.tuulbox.logging

import com.juul.tuulbox.logging.ConsoleLogger.assert
import com.juul.tuulbox.logging.ConsoleLogger.debug
import com.juul.tuulbox.logging.ConsoleLogger.error
import com.juul.tuulbox.logging.ConsoleLogger.info
import com.juul.tuulbox.logging.ConsoleLogger.verbose
import com.juul.tuulbox.logging.ConsoleLogger.warn
import kotlinx.cinterop.CPointer
import platform.posix.FILE
import platform.posix.fflush
import platform.posix.fprintf
import platform.posix.stderr
import platform.posix.stdout

/**
 * Logger for standard-out and standard-error.
 *
 * - Calls to [verbose], [debug], [info], and [warn] map to standard-out.
 * - Calls to [error] and [assert] map to standard-error.
 */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("ConsoleLogger", "com.juul.khronicle.ConsoleLogger"),
)
public actual object ConsoleLogger : Logger {

    private fun print(stream: CPointer<FILE>?, severity: String, tag: String, message: String, throwable: Throwable?) {
        val formattedString = if (throwable == null) {
            "[$severity/$tag] $message\n"
        } else {
            "[$severity/$tag] $message: ${throwable.stackTraceToString()}\n"
        }

        fprintf(stream, formattedString)
        fflush(stream)
    }

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "V", tag, message, throwable)
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "D", tag, message, throwable)
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "I", tag, message, throwable)
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stdout, "W", tag, message, throwable)
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stderr, "E", tag, message, throwable)
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        print(stderr, "A", tag, message, throwable)
    }
}
