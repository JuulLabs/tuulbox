package com.juul.tuulbox.coroutines.delay

import com.juul.tuulbox.test.runTest
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource
import kotlin.time.toDuration

class DelayStrategyTest {
    @Test
    fun defaultsWithMaximumDelayMillis_largeRetryNumber_adheresToMaximum() {
        assertEquals(1_000L, getExponentialBackoffMillis(baseTimeMillis = 100, maximumDelayMillis = 1_000L, iteration = 20))
    }

    @Test
    fun calculatedDelayResultsInInfinity_returnsMaximumDelayMillis() {
        assertEquals(1_000L, getExponentialBackoffMillis(baseTimeMillis = 100_000L, maximumDelayMillis = 1_000L, iteration = 100_000))
    }

    @Test
    fun defaultsWithMinimumDelayMillis_numberBelowMinimum() {
        assertEquals(1_000L, getExponentialBackoffMillis(baseTimeMillis = 10, minimumDelayMillis = 1_000L, iteration = 0))
    }

    @Test
    fun firstIteration_hasDelayEqualToBaseTimeMillis() {
        assertEquals(100, getExponentialBackoffMillis(baseTimeMillis = 100, iteration = 0))
    }

    @Test
    fun firstIteration_hasDelayEqualToBaseTimeMillisMinusElapsed() {
        assertEquals(50, getExponentialBackoffMillis(baseTimeMillis = 100, iteration = 0, elapsedMillis = 50L))
    }

    @Test
    fun multipleRetries_increaseExponentially() {
        val baseTimeMillis = 100L
        var delay = baseTimeMillis
        val iterations = 0..10
        iterations.forEach { iteration ->
            assertEquals(delay, getExponentialBackoffMillis(baseTimeMillis = 100L, iteration = iteration), "Backoff retry #$iteration")
            delay *= 2
        }
    }

    @Test
    @OptIn(ExperimentalTime::class)
    fun dynamicDelayStrategy_switchesBetweenStrategies_usingTrigger() = runTest {
        val trigger = MutableStateFlow(true)
        val timeMachine = TimeMachine()
        val delayA = TimeMachineDelayStrategy(timeMachine, delayUntilMilliseconds = 1_000L)
        val delayB = TimeMachineDelayStrategy(timeMachine, delayUntilMilliseconds = 2_000L)
        val dynamic = Dynamic(trigger, timeMachine) { if (it) delayA else delayB }

        val job = launch {
            dynamic.await(iteration = 0, elapsedMillis = 0L)
        }
        assertEquals(
            expected = Invocation.Await(iteration = 0, elapsedMilliseconds = 0L),
            actual = delayA.invocation.receive(),
        )
        assertFalse(job.isCompleted)

        timeMachine.advanceBy(milliseconds = 500L)
        trigger.value = false
        assertEquals(
            expected = Invocation.Await(iteration = 0, elapsedMilliseconds = 500L),
            actual = delayB.invocation.receive(),
        )
        assertFalse(job.isCompleted)

        timeMachine.advanceBy(milliseconds = 3_000L)
    }
}

@OptIn(ExperimentalTime::class)
private class TimeMachine : TimeSource {
    private val elapsedMillis = MutableStateFlow(0L)
    fun advanceBy(milliseconds: Long) { elapsedMillis.update { value -> value + milliseconds } }
    suspend fun delayUntil(milliseconds: Long) { elapsedMillis.first { it >= milliseconds } }
    private class TimeMachineMark(private val parent: TimeMachine) : TimeMark() {
        override fun elapsedNow(): Duration = parent.elapsedMillis.value.toDuration(DurationUnit.MILLISECONDS)
    }
    override fun markNow(): TimeMark = TimeMachineMark(this)
}

private sealed class Invocation {
    data class Await(val iteration: Int, val elapsedMilliseconds: Long) : Invocation()
}

private class TimeMachineDelayStrategy(
    private val timeMachine: TimeMachine,
    private val delayUntilMilliseconds: Long,
) : DelayStrategy {
    val invocation = Channel<Invocation>()
    override suspend fun await(iteration: Int, elapsedMillis: Long) {
        invocation.send(Invocation.Await(iteration, elapsedMillis))
        timeMachine.delayUntil(milliseconds = delayUntilMilliseconds)
    }
}
