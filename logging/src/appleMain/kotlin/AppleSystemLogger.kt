package com.juul.tuulbox.logging

import platform.Foundation.NSLog

public object AppleSystemLogger : Logger {

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("V", tag, message, throwable)
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("D", tag, message, throwable)
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("I", tag, message, throwable)
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("W", tag, message, throwable)
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("E", tag, message, throwable)
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        log("A", tag, message, throwable)
    }

    private fun log(level: String, tag: String, message: String, throwable: Throwable?) {
        if (throwable == null) {
            NSLog("%s/%s: %s", level, tag, message)
        } else {
            NSLog("%s/%s: %s\n%s", level, tag, message, throwable.stackTraceToString())
        }
    }
}
