package com.juul.tuulbox.logging

import com.juul.tuulbox.logging.ConsoleLogger.assert
import com.juul.tuulbox.logging.ConsoleLogger.debug
import com.juul.tuulbox.logging.ConsoleLogger.error
import com.juul.tuulbox.logging.ConsoleLogger.info
import com.juul.tuulbox.logging.ConsoleLogger.verbose
import com.juul.tuulbox.logging.ConsoleLogger.warn
import platform.posix.STDERR_FILENO
import platform.posix.STDOUT_FILENO
import platform.posix.fclose
import platform.posix.fdopen
import platform.posix.fflush
import platform.posix.fprintf

/**
 * Logger for standard-out and standard-error.
 *
 * - Calls to [verbose], [debug], [info], and [warn] map to standard-out.
 * - Calls to [error] and [assert] map to standard-error.
 */
public actual object ConsoleLogger : Logger {

    private fun print(fileDescriptor: Int, severity: String, tag: String, message: String, throwable: Throwable?) {
        val formattedString = if (throwable == null) {
            "[$severity/$tag] $message\n"
        } else {
            "[$severity/$tag] $message: ${throwable.stackTraceToString()}\n"
        }

        // TODO: Opening the stream every log statement is slow.
        val stream = checkNotNull(fdopen(fileDescriptor, "w")) { "Unable to open file descriptor $fileDescriptor" }
        fprintf(stream, formattedString)
        fflush(stream)
        fclose(stream)
    }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        print(STDOUT_FILENO, "V", tag, message, throwable)
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        print(STDOUT_FILENO, "D", tag, message, throwable)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        print(STDOUT_FILENO, "I", tag, message, throwable)
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        print(STDOUT_FILENO, "W", tag, message, throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        print(STDERR_FILENO, "E", tag, message, throwable)
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        print(STDERR_FILENO, "A", tag, message, throwable)
    }
}
