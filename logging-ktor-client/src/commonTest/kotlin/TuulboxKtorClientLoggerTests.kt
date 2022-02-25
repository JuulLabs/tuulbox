package com.juul.tuulbox.logging

import com.juul.tuulbox.test.runTest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.logging.LogLevel.ALL
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertTrue

private const val URL = "https://localhost"
private const val RESPONSE = "Hello, world."

class TuulboxKtorClientLoggerTests {

    @AfterTest
    fun cleanup() {
        Log.dispatcher.clear()
    }

    private fun createClient(tuulboxLogger: TuulboxKtorClientLogger): HttpClient {
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
        val client = createClient(TuulboxKtorClientLogger())
        client.get(URL).body<String>()
        assertTrue { logger.verboseCalls.isNotEmpty() }
    }

    @Test
    fun tuulboxlogger_atSpecificLevel_logsAtLevel() = runTest {
        for (level in LogLevel.values()) {
            val logger = CallListLogger()
            Log.dispatcher.install(logger)
            val client = createClient(TuulboxKtorClientLogger(level))
            client.get(URL).body<String>()
            assertTrue { logger.allCalls.any { it.level == level } }
            Log.dispatcher.clear()
        }
    }

    @Test
    fun tuulboxlogger_withMetadataCallback_installsMetadata() = runTest {
        val logger = CallListLogger()
        Log.dispatcher.install(logger)
        val client = createClient(TuulboxKtorClientLogger { metadata -> metadata[Sensitivity] = Sensitivity.Sensitive })
        client.get(URL).body<String>()
        assertTrue { logger.allCalls.any { it.metadata[Sensitivity] == Sensitivity.Sensitive } }
    }
}
