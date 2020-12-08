package com.juul.tuulbox.logging

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.getAndUpdate

class CallListLogger : Logger {

    private val atomicVerboseCalls = atomic(emptyList<Call>())
    val verboseCalls: List<Call> get() = atomicVerboseCalls.value

    private val atomicDebugCalls = atomic(emptyList<Call>())
    val debugCalls: List<Call> get() = atomicDebugCalls.value

    private val atomicInfoCalls = atomic(emptyList<Call>())
    val infoCalls: List<Call> get() = atomicInfoCalls.value

    private val atomicWarnCalls = atomic(emptyList<Call>())
    val warnCalls: List<Call> get() = atomicWarnCalls.value

    private val atomicErrorCalls = atomic(emptyList<Call>())
    val errorCalls: List<Call> get() = atomicErrorCalls.value

    private val atomicAssertCalls = atomic(emptyList<Call>())
    val assertCalls: List<Call> get() = atomicAssertCalls.value

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        atomicVerboseCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        atomicDebugCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        atomicInfoCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        atomicWarnCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        atomicErrorCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        atomicAssertCalls.getAndUpdate { it + Call(tag = tag, message = message, throwable = throwable) }
    }
}
