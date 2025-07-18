package com.juul.tuulbox.coroutines.delay

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transformLatest
import kotlinx.datetime.asTimeSource
import kotlin.math.pow
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

/**
 * Interface, using the strategy pattern, for an implementer to delay for periods of time.
 */
@Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
public interface DelayStrategy {

    @Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
    public suspend fun await(iteration: Int, elapsedMillis: Long)
}

/**
 * [DelayStrategy] that, when [await] is called, will delay for a fixed amount of time, specified by [delayMillis].
 */
@Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
public class FixedDelay(
    private val delayMillis: Long,
) : DelayStrategy {

    @Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
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
 * Inspired by:
 * [Exponential Backoff And Jitter](https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter/)
 */
@Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
public class ExponentialBackoff(
    private val baseTimeMillis: Long = DEFAULT_BASE_TIME_MILLIS,
    private val multiplier: Float = DEFAULT_MULTIPLIER,
    private val minimumDelayMillis: Long = DEFAULT_MINIMUM_DELAY,
    private val maximumDelayMillis: Long = DEFAULT_MAXIMUM_DELAY,
) : DelayStrategy {
    override suspend fun await(
        iteration: Int,
        elapsedMillis: Long,
    ) {
        delay(
            getExponentialBackoffMillis(
                iteration,
                baseTimeMillis,
                multiplier,
                minimumDelayMillis,
                maximumDelayMillis,
                elapsedMillis,
            ),
        )
    }
}

internal fun getExponentialBackoffMillis(
    iteration: Int,
    baseTimeMillis: Long = DEFAULT_BASE_TIME_MILLIS,
    multiplier: Float = DEFAULT_MULTIPLIER,
    minimumDelayMillis: Long = DEFAULT_MINIMUM_DELAY,
    maximumDelayMillis: Long = DEFAULT_MAXIMUM_DELAY,
    elapsedMillis: Long = 0L,
) = (baseTimeMillis * multiplier.pow(iteration))
    .toLong()
    .coerceAtLeast(minimumDelayMillis)
    .coerceAtMost(maximumDelayMillis) - elapsedMillis

/**
 * Dynamic [DelayStrategy], allowing for switching between multiple other DelayStrategies utilizing a [trigger]
 * (to provide an input) and a [selector] function (to determine a resulting DelayStrategy from said input).
 *
 * The resulting [DelayStrategy]'s await will be called with an elapsedMillis determined by the amount
 * of time that has passed since the Dynamic DelayStrategy's await was called, allowing it to factor that into its
 * calculation (for the purposes of adjusting one DelayStrategy's delay based on the amount of time that passed in another
 * DelayStrategy's delay).
 */
@Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
public class Dynamic<T> internal constructor(
    private val trigger: Flow<T>,
    private val timeSource: TimeSource,
    private val selector: (T) -> DelayStrategy,
) : DelayStrategy {
    @OptIn(ExperimentalTime::class)
    @Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
    public constructor(
        trigger: Flow<T>,
        selector: (T) -> DelayStrategy,
    ) : this(trigger, Clock.System.asTimeSource(), selector)

    @Deprecated("Use https://github.com/michaelbull/kotlin-retry instead.")
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        val mark = timeSource.markNow()
        trigger
            .transformLatest {
                selector(it).await(iteration, mark.elapsedNow().inWholeMilliseconds)
                emit(Unit)
            }.first()
    }
}
