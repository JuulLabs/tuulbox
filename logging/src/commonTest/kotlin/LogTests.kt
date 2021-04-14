package com.juul.tuulbox.logging

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue
import kotlin.test.fail

class LogTests {

    private val failTestTagGenerator = object : TagGenerator {
        override fun getTag(): String {
            fail("Should not be called.")
        }
    }

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
        Log.tagGenerator = defaultTagGenerator
    }

    @Test
    fun tagGeneratorCanBeSet() {
        assertSame(defaultTagGenerator, Log.tagGenerator)
        Log.tagGenerator = failTestTagGenerator
        assertSame(failTestTagGenerator, Log.tagGenerator)
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

    @Test
    fun verboseExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.verbose(tag = "explicit tag") { "Test message" }
        assertTrue { logger.verboseCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun debugExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.debug(tag = "explicit tag") { "Test message" }
        assertTrue { logger.debugCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun infoExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.info(tag = "explicit tag") { "Test message" }
        assertTrue { logger.infoCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun warnExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.warn(tag = "explicit tag") { "Test message" }
        assertTrue { logger.warnCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun errorExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.error(tag = "explicit tag") { "Test message" }
        assertTrue { logger.errorCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun assertExplicitTagIsUsed() {
        Log.tagGenerator = failTestTagGenerator
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        Log.assert(tag = "explicit tag") { "Test message" }
        assertTrue { logger.assertCalls.all { it.tag == "explicit tag" } }
    }

    @Test
    fun verbose_withMetadata_canReadInLogger() {
        Log.tagGenerator = failTestTagGenerator
        val logger = object : CallListLogger() {
            override fun verbose(tag: String, message: String, metadata: ReadMetadata, throwable: Throwable?) {
                super.verbose(tag, message, metadata, throwable)
                assertEquals("test", metadata[StringKey])
            }
        }
        Log.dispatcher.install(logger)
        Log.verbose(tag = "explicit tag") { metadata ->
            metadata[StringKey] = "test"
            "Test message"
        }
    }
}
