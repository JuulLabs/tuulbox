package com.juul.tuulbox.logging

import com.juul.tuulbox.logging.LogLevel.Verbose
import io.ktor.client.HttpClient
import io.ktor.client.features.logging.Logger as KtorLogger

/**
 * A logger for use with a Ktor [HttpClient].
 *
 * ```kotlin
 * HttpClient {
 *     install(Logging) {
 *         logger = TuulboxLogger()
 *     }
 * }
 * ```
 */
public class TuulboxLogger(
    private val level: LogLevel = Verbose,
    private val writeMetadata: (WriteMetadata) -> Unit = {},
) : KtorLogger {

    override fun log(message: String) {
        Log.dynamic(level) { metadata ->
            writeMetadata.invoke(metadata)
            message
        }
    }
}
