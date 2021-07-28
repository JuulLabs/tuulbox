package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals

class FilterLevelLoggerTests {

    @Test
    fun filter_withMinimumLevelVerbose_allowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(1, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelVerbose_allowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(1, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelVerbose_allowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(1, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelVerbose_allowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(1, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelVerbose_allowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(1, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelVerbose_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Verbose)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_disallowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(0, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_allowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(1, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_allowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(1, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_allowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(1, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_allowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(1, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelDebug_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Debug)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_disallowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(0, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_disallowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(0, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_allowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(1, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_allowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(1, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_allowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(1, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelInfo_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Info)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_disallowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(0, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_disallowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(0, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_disallowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(0, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_allowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(1, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_allowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(1, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelWarn_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Warn)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_disallowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(0, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_disallowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(0, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_disallowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(0, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_disallowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(0, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_allowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(1, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelError_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Error)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_disallowsVerboseLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.verbose("tag", "message", Metadata(), null)
        assertEquals(0, inner.verboseCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_disallowsDebugLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.debug("tag", "message", Metadata(), null)
        assertEquals(0, inner.debugCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_disallowsInfoLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.info("tag", "message", Metadata(), null)
        assertEquals(0, inner.infoCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_disallowsWarnLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.warn("tag", "message", Metadata(), null)
        assertEquals(0, inner.warnCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_disallowsErrorLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.error("tag", "message", Metadata(), null)
        assertEquals(0, inner.errorCalls.size)
    }

    @Test
    fun filter_withMinimumLevelAssert_allowsAssertLogs() {
        val inner = CallListLogger()
        val outer = inner.withMinimumLogLevel(LogLevel.Assert)
        outer.assert("tag", "message", Metadata(), null)
        assertEquals(1, inner.assertCalls.size)
    }
}
