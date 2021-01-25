package com.juul.tuulbox.temporal

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/** Emits values from a [factory], with a [delay] between each emission. */
@ExperimentalTime
@PublishedApi
internal inline fun <T> ticker(
    delay: Duration,
    crossinline factory: () -> T
): Flow<T> = flow {
    val marker = TimeSource.Monotonic
    while (true) {
        val before = marker.markNow()
        emit(factory.invoke())
        val adjustedDelay = delay - before.elapsedNow()
        if (adjustedDelay > delay) {
            // Something strange happened and the monotonic clock failed. In
            // such a case, don't delay and re-emit immediately. Usually this
            // will be a one-time thing like a timezone change.
        } else {
            // Negative delays are ignored, so no need to check if the clock
            // did something weird in the other direction.
            delay(adjustedDelay)
        }
    }
}
