package com.juul.tuulbox.logging

class CallListLogger : Logger {

    private val mutableVerboseCalls = mutableListOf<Call>()
    val verboseCalls: List<Call> = mutableVerboseCalls

    private val mutableDebugCalls = mutableListOf<Call>()
    val debugCalls: List<Call> = mutableDebugCalls

    private val mutableInfoCalls = mutableListOf<Call>()
    val infoCalls: List<Call> = mutableInfoCalls

    private val mutableWarnCalls = mutableListOf<Call>()
    val warnCalls: List<Call> = mutableWarnCalls

    private val mutableErrorCalls = mutableListOf<Call>()
    val errorCalls: List<Call> = mutableErrorCalls

    private val mutableAssertCalls = mutableListOf<Call>()
    val assertCalls: List<Call> = mutableAssertCalls

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        mutableVerboseCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        mutableDebugCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        mutableInfoCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        mutableWarnCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        mutableErrorCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        mutableAssertCalls += Call(tag = tag, message = message, throwable = throwable)
    }
}
