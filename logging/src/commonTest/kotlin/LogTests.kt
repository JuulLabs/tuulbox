package com.juul.tuulbox.logging

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.fail

class LogTests {

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
    }

    @Test
    fun verboseWithNoConsumerDoesntCreateMessage() {
        Log.verbose { fail("Lambda should not be called") }
    }

    @Test
    fun debugWithNoConsumerDoesntCreateMessage() {
        Log.debug { fail("Lambda should not be called") }
    }

    @Test
    fun infoWithNoConsumerDoesntCreateMessage() {
        Log.info { fail("Lambda should not be called") }
    }

    @Test
    fun warnWithNoConsumerDoesntCreateMessage() {
        Log.warn { fail("Lambda should not be called") }
    }

    @Test
    fun errorWithNoConsumerDoesntCreateMessage() {
        Log.error { fail("Lambda should not be called") }
    }

    @Test
    fun assertWithNoConsumerDoesntCreateMessage() {
        Log.assert { fail("Lambda should not be called") }
    }

    @Test
    fun verboseWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.verbose { "message" }
        assertTrue(logger.verboseCalls.isNotEmpty())
    }

    @Test
    fun debugWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.debug { "message" }
        assertTrue(logger.debugCalls.isNotEmpty())
    }

    @Test
    fun infoWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.info { "message" }
        assertTrue(logger.infoCalls.isNotEmpty())
    }

    @Test
    fun warnWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.warn { "message" }
        assertTrue(logger.warnCalls.isNotEmpty())
    }

    @Test
    fun errorWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.error { "message" }
        assertTrue(logger.errorCalls.isNotEmpty())
    }

    @Test
    fun assertWithConsumerCreatesMessage() {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.assert { "message" }
        assertTrue(logger.assertCalls.isNotEmpty())
    }
}
