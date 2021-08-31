package com.juul.tuulbox.logging

import android.util.Log

/** [Logger] backed by Android's [Log], for output to Logcat. */
public object AndroidLogger : Logger {

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.v(tag, message, throwable)
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.d(tag, message, throwable)
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.i(tag, message, throwable)
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.w(tag, message, throwable)
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        Log.wtf(tag, message, throwable)
    }
}
