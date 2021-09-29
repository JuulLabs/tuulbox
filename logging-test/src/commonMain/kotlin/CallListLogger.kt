package com.juul.tuulbox.logging

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.getAndUpdate

public open class CallListLogger : Logger {

    private val atomicAllCalls = atomic(emptyList<Call>())
    public val allCalls: List<Call> get() = atomicAllCalls.value
    public val verboseCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Verbose }
    public val debugCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Debug }
    public val infoCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Info }
    public val warnCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Warn }
    public val errorCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Error }
    public val assertCalls: List<Call> get() = allCalls.filter { it.level == LogLevel.Assert }

    override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Verbose, tag = tag, message = message, throwable, metadata.copy()) }
    }

    override fun debug(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Debug, tag = tag, message = message, throwable, metadata.copy()) }
    }

    override fun info(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Info, tag = tag, message = message, throwable, metadata.copy()) }
    }

    override fun warn(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Warn, tag = tag, message = message, throwable, metadata.copy()) }
    }

    override fun error(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Error, tag = tag, message = message, throwable, metadata.copy()) }
    }

    override fun assert(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
        atomicAllCalls.getAndUpdate { it + Call(LogLevel.Assert, tag = tag, message = message, throwable, metadata.copy()) }
    }
}
