package com.juul.tuulbox.coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.asTimeSource
import kotlin.math.E
import kotlin.math.pow
import kotlin.time.ExperimentalTime

public interface DelayStrategy {
    public suspend fun await(iteration: Int, elapsedMillis: Long)
}

public class FixedDelay(private val delayMillis: Long) : DelayStrategy {
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        delay(delayMillis - elapsedMillis)
    }
}

public class ExponentialBackoff(
    private val minimumMillis: Long,
    private val maximumMillis: Long
) : DelayStrategy {
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        val delayMillis = (E.pow(iteration / E).toLong() * 1000L).coerceIn(minimumMillis..maximumMillis)
        delay(delayMillis - elapsedMillis)
    }
}

public class Dynamic<T>(
    private val trigger: Flow<T>,
    private val selector: (T) -> DelayStrategy,
) : DelayStrategy {
    @OptIn(ExperimentalTime::class)
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        val mark = Clock.System.asTimeSource().markNow()
        trigger.transformLatest {
            selector(it).await(iteration, mark.elapsedNow().inWholeMilliseconds)
            emit(Unit)
        }.first()
    }
}