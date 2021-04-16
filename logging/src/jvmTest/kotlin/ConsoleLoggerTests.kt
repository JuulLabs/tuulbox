package com.juul.tuulbox.logging

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

private val UTF8 = StandardCharsets.UTF_8.name()
private const val TAG = "sample-tag"
private const val MESSAGE = "A longer sample message"

class ConsoleLoggerTests {

    private val systemOut = System.out
    private val systemErr = System.err

    private lateinit var testOutBuffer: ByteArrayOutputStream
    private lateinit var testErrBuffer: ByteArrayOutputStream
    private lateinit var testOut: PrintStream
    private lateinit var testErr: PrintStream

    @BeforeTest
    fun setup() {
        testOutBuffer = ByteArrayOutputStream()
        testErrBuffer = ByteArrayOutputStream()
        testOut = PrintStream(testOutBuffer, true, UTF8)
        testErr = PrintStream(testErrBuffer, true, UTF8)
        System.setOut(testOut)
        System.setErr(testErr)
    }

    @AfterTest
    fun teardown() {
        System.setOut(systemOut)
        System.setErr(systemErr)
        testOut.close()
        testErr.close()
    }

    @Test
    fun checkVerboseWithoutThrowable() {
        ConsoleLogger.verbose(TAG, MESSAGE, Metadata(), null)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkVerboseWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.verbose(TAG, MESSAGE, Metadata(), throwable)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkDebugWithoutThrowable() {
        ConsoleLogger.debug(TAG, MESSAGE, Metadata(), null)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkDebugWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.debug(TAG, MESSAGE, Metadata(), throwable)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkInfoWithoutThrowable() {
        ConsoleLogger.info(TAG, MESSAGE, Metadata(), null)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkInfoWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.info(TAG, MESSAGE, Metadata(), throwable)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkWarnWithoutThrowable() {
        ConsoleLogger.warn(TAG, MESSAGE, Metadata(), null)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkWarnWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.warn(TAG, MESSAGE, Metadata(), throwable)
        val logString = testOutBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkErrorWithoutThrowable() {
        ConsoleLogger.error(TAG, MESSAGE, Metadata(), null)
        val logString = testErrBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkErrorWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.error(TAG, MESSAGE, Metadata(), throwable)
        val logString = testErrBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }

    @Test
    fun checkAssertWithoutThrowable() {
        ConsoleLogger.assert(TAG, MESSAGE, Metadata(), null)
        val logString = testErrBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
    }

    @Test
    fun checkAssertWithThrowable() {
        val throwable = Throwable()
        ConsoleLogger.assert(TAG, MESSAGE, Metadata(), throwable)
        val logString = testErrBuffer.toString(UTF8)
        assertTrue(TAG in logString)
        assertTrue(MESSAGE in logString)
        assertTrue(throwable.stackTraceToString() in logString)
    }
}
