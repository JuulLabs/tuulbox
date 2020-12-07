package com.juul.tuulbox.logging

import co.touchlab.stately.isolate.IsolateState

class CallListLogger : Logger {

    private val mutableVerboseCalls = IsolateState { mutableListOf<Call>() }
    val verboseCalls: List<Call> get() = mutableVerboseCalls.access { ArrayList(it) }

    private val mutableDebugCalls = IsolateState { mutableListOf<Call>() }
    val debugCalls: List<Call> get() = mutableDebugCalls.access { ArrayList(it) }

    private val mutableInfoCalls = IsolateState { mutableListOf<Call>() }
    val infoCalls: List<Call> get() = mutableInfoCalls.access { ArrayList(it) }

    private val mutableWarnCalls = IsolateState { mutableListOf<Call>() }
    val warnCalls: List<Call> get() = mutableWarnCalls.access { ArrayList(it) }

    private val mutableErrorCalls = IsolateState { mutableListOf<Call>() }
    val errorCalls: List<Call> get() = mutableErrorCalls.access { ArrayList(it) }

    private val mutableAssertCalls = IsolateState { mutableListOf<Call>() }
    val assertCalls: List<Call> get() = mutableAssertCalls.access { ArrayList(it) }

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        mutableVerboseCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        mutableDebugCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        mutableInfoCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        mutableWarnCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        mutableErrorCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        mutableAssertCalls.access { it += Call(tag = tag, message = message, throwable = throwable) }
    }
}
