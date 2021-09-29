package com.juul.tuulbox.logging

import com.juul.tuulbox.test.runTest
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.features.logging.LogLevel.ALL
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

private const val URL = "https://example.com"
private const val RESPONSE = "Hello, world."

class TuulboxLoggerTests {

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
    }

    private fun createClient(tuulboxLogger: TuulboxLogger): HttpClient {
        val engine = MockEngine { request -> respond(RESPONSE) }
        return HttpClient(engine) {
            install(Logging) {
                level = ALL
                logger = tuulboxLogger
            }
        }
    }

    @Test
    fun tuulboxlogger_atDefaultLevel_logsVerbose() = runTest {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        val client = createClient(TuulboxLogger())
        client.get<String>(URL)
        assertTrue { logger.verboseCalls.isNotEmpty() }
    }

    @Test
    fun tuulboxlogger_atSpecificLevel_logsAtLevel() = runTest {
        for (level in LogLevel.values()) {
            val logger = CallListLogger()
            Log.dispatcher.install(logger)
            val client = createClient(TuulboxLogger(level))
            client.get<String>(URL)
            assertTrue { logger.allCalls.any { it.level == level } }
            Log.dispatcher.clear()
        }
    }
}
