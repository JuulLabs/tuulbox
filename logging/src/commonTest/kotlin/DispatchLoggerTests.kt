package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DispatchLoggerTests {

    @Test
    fun hasConsumersIsFalseForNoConsumers() {
        val dispatcher = DispatchLogger()
        assertFalse(dispatcher.hasConsumers)
    }

    @Test
    fun hasConsumersIsTrueAfterConsumerIsInstalled() {
        val dispatcher = DispatchLogger()
        dispatcher.install(CallListLogger())
        assertTrue(dispatcher.hasConsumers)
    }

    @Test
    fun allConsumersReceiveVerboseLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.verbose(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.verboseCalls.single())
        assertEquals(call, second.verboseCalls.single())
    }

    @Test
    fun allConsumersReceiveDebugLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.debug(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.debugCalls.single())
        assertEquals(call, second.debugCalls.single())
    }

    @Test
    fun allConsumersReceiveInfoLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.info(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.infoCalls.single())
        assertEquals(call, second.infoCalls.single())
    }

    @Test
    fun allConsumersReceiveWarnLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.warn(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.warnCalls.single())
        assertEquals(call, second.warnCalls.single())
    }

    @Test
    fun allConsumersReceiveErrorLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.error(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.errorCalls.single())
        assertEquals(call, second.errorCalls.single())
    }

    @Test
    fun allConsumersReceiveAssertLogs() {
        val dispatcher = DispatchLogger()
        val first = CallListLogger()
        val second = CallListLogger()
        dispatcher.install(first)
        dispatcher.install(second)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.assert(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, first.assertCalls.single())
        assertEquals(call, second.assertCalls.single())
    }

    @Test
    fun installingDuplicateConsumerDoesNotResultInDuplicateDispatch() {
        val dispatcher = DispatchLogger()
        val consumer = CallListLogger()
        dispatcher.install(consumer)
        dispatcher.install(consumer)
        val metadata = Metadata()
        val call = Call(tag = "test-tag", message = "test-message", Throwable(), metadata)
        dispatcher.verbose(call.tag, call.message, metadata, call.throwable)
        assertEquals(call, consumer.verboseCalls.single())
    }

    @Test
    fun consumers_withMinimumLogLevelVerbose_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Verbose
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(1, verboseLevel.verboseCalls.size)
        assertEquals(1, verboseLevel.debugCalls.size)
        assertEquals(1, verboseLevel.infoCalls.size)
        assertEquals(1, verboseLevel.warnCalls.size)
        assertEquals(1, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }

    @Test
    fun consumers_withMinimumLogLevelDebug_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Debug
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(0, verboseLevel.verboseCalls.size)
        assertEquals(1, verboseLevel.debugCalls.size)
        assertEquals(1, verboseLevel.infoCalls.size)
        assertEquals(1, verboseLevel.warnCalls.size)
        assertEquals(1, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }

    @Test
    fun consumers_withMinimumLogLevelInfo_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Info
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(0, verboseLevel.verboseCalls.size)
        assertEquals(0, verboseLevel.debugCalls.size)
        assertEquals(1, verboseLevel.infoCalls.size)
        assertEquals(1, verboseLevel.warnCalls.size)
        assertEquals(1, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }

    @Test
    fun consumers_withMinimumLogLevelWarn_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Warn
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(0, verboseLevel.verboseCalls.size)
        assertEquals(0, verboseLevel.debugCalls.size)
        assertEquals(0, verboseLevel.infoCalls.size)
        assertEquals(1, verboseLevel.warnCalls.size)
        assertEquals(1, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }

    @Test
    fun consumers_withMinimumLogLevelError_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Error
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(0, verboseLevel.verboseCalls.size)
        assertEquals(0, verboseLevel.debugCalls.size)
        assertEquals(0, verboseLevel.infoCalls.size)
        assertEquals(0, verboseLevel.warnCalls.size)
        assertEquals(1, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }

    @Test
    fun consumers_withMinimumLogLevelAssert_receiveAppropriateCalls() {
        val dispatcher = DispatchLogger()
        val verboseLevel = object : CallListLogger() {
            override val minimumLogLevel = LogLevel.Assert
        }
        dispatcher.install(verboseLevel)
        dispatcher.verbose("tag", "message", Metadata(), null)
        dispatcher.debug("tag", "message", Metadata(), null)
        dispatcher.info("tag", "message", Metadata(), null)
        dispatcher.warn("tag", "message", Metadata(), null)
        dispatcher.error("tag", "message", Metadata(), null)
        dispatcher.assert("tag", "message", Metadata(), null)
        assertEquals(0, verboseLevel.verboseCalls.size)
        assertEquals(0, verboseLevel.debugCalls.size)
        assertEquals(0, verboseLevel.infoCalls.size)
        assertEquals(0, verboseLevel.warnCalls.size)
        assertEquals(0, verboseLevel.errorCalls.size)
        assertEquals(1, verboseLevel.assertCalls.size)
    }
}
