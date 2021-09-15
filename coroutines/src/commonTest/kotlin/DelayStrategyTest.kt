package com.juul.tuulbox.coroutines.flow

import DelayStrategy
import Dynamic
import calculateExponentialBackoffDelay
import com.juul.tuulbox.test.runTest
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark
import kotlin.time.TimeSource
import kotlin.time.toDuration

class DelayStrategyTest {

    @Test
    fun testExponentialBackoff() {
        val bounceOffMinimumBoundDelay = calculateExponentialBackoffDelay(
            0, 0, 2_000, 20_000
        )
        assertEquals(2_000, bounceOffMinimumBoundDelay)

        val fiveIterationsDelay = calculateExponentialBackoffDelay(
            5, 0, 2_000, 20_000
        )
        assertEquals(6_000, fiveIterationsDelay)

        val eightIterationsDelay = calculateExponentialBackoffDelay(
            8, 0, 2_000, 20_000
        )
        assertEquals(18_000, eightIterationsDelay)

        val bounceOffMaximumBoundDelay = calculateExponentialBackoffDelay(
            30, 0, 2_000, 20_000
        )
        assertEquals(20_000, bounceOffMaximumBoundDelay)

        val minusInitialElapsedDelay = calculateExponentialBackoffDelay(
            0, 1_000, 2_000, 20_000
        )
        assertEquals(1_000, minusInitialElapsedDelay)
    }

    @Test
    @OptIn(ExperimentalTime::class)
    fun testDynamicBackoff() = runTest {
        val trigger = MutableStateFlow(true)
        val outputFlow: MutableStateFlow<Triple<Char, Int, Long>?> = MutableStateFlow(null)
        // TestDelay that will push an 'A' test value to the output and suspend indefinitely.
        val testDelayA = TestDelay { (iteration, elapsedMillis) ->
            outputFlow.value = Triple('A', iteration, elapsedMillis)
        }
        // TestDelay that will push a 'B' test value to the output and suspend indefinitely.
        val testDelayB = TestDelay { (iteration, elapsedMillis) ->
            outputFlow.value = Triple('B', iteration, elapsedMillis)
        }
        // Switch between testDelayA and testDelayB, based on the trigger.
        val selector: (Boolean) -> DelayStrategy = { if (it) testDelayA else testDelayB }

        // TimeSource that will increment by a second every time ElapsedNow() is called.
        val timeSource = object : TimeSource {
            private var fakedTime = 1

            override fun markNow() = object : TimeMark() {
                override fun elapsedNow() = fakedTime++.toDuration(DurationUnit.SECONDS)
            }
        }

        val delayJob = launch {
            val dynamic = Dynamic(
                trigger,
                selector,
                timeSource
            )
            dynamic.await(0, 0)
        }

        withTimeout(10_000) {
            delay(100L)
            assertEquals(Triple('A', 0, 1_000L), outputFlow.value)
            trigger.value = false
            delay(100L)
            assertEquals(Triple('B', 0, 2_000L), outputFlow.value)
            delayJob.cancelAndJoin()
        }
    }

    private class TestDelay(
        private val onAwait: (Pair<Int, Long>) -> Unit
    ) : DelayStrategy {
        override suspend fun await(iteration: Int, elapsedMillis: Long) {
            onAwait(iteration to elapsedMillis)
            awaitCancellation()
        }
    }
}
