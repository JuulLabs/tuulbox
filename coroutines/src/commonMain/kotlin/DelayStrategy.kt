package com.juul.tuulbox.coroutines.delay

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.datetime.Clock
import kotlinx.datetime.asTimeSource
import kotlin.math.pow
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * Interface, using the strategy pattern, for an implementer to delay for periods of time.
 */
public interface DelayStrategy {
    public suspend fun await(iteration: Int, elapsedMillis: Long)
}

/**
 * [DelayStrategy] that, when [await] is called, will delay for a fixed amount of time, specified by [delayMillis].
 */
public class FixedDelay(private val delayMillis: Long) : DelayStrategy {
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        delay(delayMillis - elapsedMillis)
    }
}

private const val DEFAULT_BASE_TIME_MILLIS = 1_000L
private const val DEFAULT_MULTIPLIER = 2f
private const val DEFAULT_MINIMUM_DELAY = 0L
private const val DEFAULT_MAXIMUM_DELAY = Long.MAX_VALUE

/**
 * [DelayStrategy] with an exponentially increasing delay, calculated with [getExponentialBackoffMillis].
 *
 * [minimumDelayMillis] indicates the capped minimum delay (in milliseconds).
 * [maximumDelayMillis] indicates the capped maximum delay (in milliseconds).
 *
 * Delay is calculated using the following formula:
 *
 * ```
 * delay = base * multiplier ^ iteration
 * ```
 *
 * For example (using `base = 100` and `multiplier = 2`):
 *
 * | iteration | delay |
 * |-----------|-------|
 * |     0     |   100 |
 * |     1     |   200 |
 * |     2     |   400 |
 * |     3     |   800 |
 * |     4     |  1600 |
 * |    ...    |   ... |
 *
 * * Inspired by:
 * [Exponential Backoff And Jitter](https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter/)
 */
public class ExponentialBackoff(
    private val baseTimeMillis: Long = DEFAULT_BASE_TIME_MILLIS,
    private val multiplier: Float = DEFAULT_MULTIPLIER,
    private val minimumDelayMillis: Long = DEFAULT_MINIMUM_DELAY,
    private val maximumDelayMillis: Long = DEFAULT_MAXIMUM_DELAY
) : DelayStrategy {
    override suspend fun await(
        iteration: Int,
        elapsedMillis: Long
    ) {
        delay(
            getExponentialBackoffMillis(
                iteration,
                baseTimeMillis,
                multiplier,
                minimumDelayMillis,
                maximumDelayMillis
            )
        )
    }
}

internal fun getExponentialBackoffMillis(
    iteration: Int,
    baseTimeMillis: Long = DEFAULT_BASE_TIME_MILLIS,
    multiplier: Float = DEFAULT_MULTIPLIER,
    minimumDelayMillis: Long = DEFAULT_MINIMUM_DELAY,
    maximumDelayMillis: Long = DEFAULT_MAXIMUM_DELAY
) = (baseTimeMillis * multiplier.pow(iteration))
    .toLong()
    .coerceAtLeast(minimumDelayMillis)
    .coerceAtMost(maximumDelayMillis)

/**
 * Dynamic [DelayStrategy], allowing for switching between multiple other DelayStrategies utilizing a [trigger]
 * (to provide an input) and a [selector] function (to determine a resulting DelayStrategy from said input).
 *
 * The resulting [DelayStrategy]'s await will be called with an elapsedMillis determined by the amount
 * of time that has passed since the Dynamic DelayStrategy's await was called, allowing it to factor that into its
 * calculation (for the purposes of adjusting one DelayStrategy's delay based on the amount of time that passed in another
 * DelayStrategy's delay).
 */
@OptIn(ExperimentalTime::class)
public class Dynamic<T> : DelayStrategy {

    private val trigger: Flow<T>
    private val selector: (T) -> DelayStrategy
    private val timeSource: TimeSource

    public constructor(
        trigger: Flow<T>,
        selector: (T) -> DelayStrategy
    ) {
        this.trigger = trigger
        this.selector = selector
        timeSource = Clock.System.asTimeSource()
    }

    internal constructor(
        trigger: Flow<T>,
        selector: (T) -> DelayStrategy,
        timeSource: TimeSource
    ) {
        this.trigger = trigger
        this.selector = selector
        this.timeSource = timeSource
    }

    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        val mark = timeSource.markNow()
        trigger.transformLatest {
            selector(it).await(iteration, mark.elapsedNow().inWholeMilliseconds)
            emit(Unit)
        }.first()
    }
}
