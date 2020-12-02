package com.juul.tuulbox.logging

class CallListLogger : Logger {

    private val mVerboseCalls = mutableListOf<Call>()
    val verboseCalls: List<Call> = mVerboseCalls

    private val mDebugCalls = mutableListOf<Call>()
    val debugCalls: List<Call> = mDebugCalls

    private val mInfoCalls = mutableListOf<Call>()
    val infoCalls: List<Call> = mInfoCalls

    private val mWarnCalls = mutableListOf<Call>()
    val warnCalls: List<Call> = mWarnCalls

    private val mErrorCalls = mutableListOf<Call>()
    val errorCalls: List<Call> = mErrorCalls

    private val mAssertCalls = mutableListOf<Call>()
    val assertCalls: List<Call> = mAssertCalls

    override fun verbose(tag: String, message: String, throwable: Throwable?) {
        mVerboseCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun debug(tag: String, message: String, throwable: Throwable?) {
        mDebugCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun info(tag: String, message: String, throwable: Throwable?) {
        mInfoCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun warn(tag: String, message: String, throwable: Throwable?) {
        mWarnCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun error(tag: String, message: String, throwable: Throwable?) {
        mErrorCalls += Call(tag = tag, message = message, throwable = throwable)
    }

    override fun assert(tag: String, message: String, throwable: Throwable?) {
        mAssertCalls += Call(tag = tag, message = message, throwable = throwable)
    }
}
