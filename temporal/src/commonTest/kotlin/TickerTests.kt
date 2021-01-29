package com.juul.tuulbox.temporal

import com.juul.tuulbox.test.runTest
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.toList
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource
import kotlin.time.seconds

private const val NUM_TICKS = 5

private const val ERROR_TOO_FAST = "Flow collected too quickly."
private const val ERROR_TOO_SLOW = "Flow collected too slowly."

@ExperimentalTime
private val TIME_TICK = 0.5.seconds

@ExperimentalTime
private val TIME_EXPECTED = TIME_TICK * (NUM_TICKS - 1)

@ExperimentalTime
private val TIME_EPSILON = 0.125.seconds

@ExperimentalTime
class TickerTests {

    /** Creates a [ticker] which returns the index of the tick. */
    private fun createTicker(): Flow<Int> {
        var count = 0
        return ticker(TIME_TICK) { count++ }
    }

    @Test
    @Ignore // Elapsed time changes depending on test order, likely due to some heavy class loading on first flow creation.
    fun testTickerTimingsWork() = runTest {
        val mark = TimeSource.Monotonic.markNow()
        val actual = createTicker().take(NUM_TICKS).toList()
        val elapsed = mark.elapsedNow()
        assertTrue(elapsed > TIME_EXPECTED - TIME_EPSILON, ERROR_TOO_FAST)
        assertTrue(elapsed < TIME_EXPECTED + TIME_EPSILON, ERROR_TOO_SLOW)
        val expected = (0 until NUM_TICKS).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun testTickerConflatesSlowCollectors() = runTest {
        val actual = createTicker()
            // Delay would be to 2.0 to skip every element exactly (works on JVM) but the dispatcher for JS
            // isn't very precise. Add a little extra to make sure this collector loses potential races.
            .onEach { delay(TIME_TICK * 2.25) }
            .takeWhile { it < NUM_TICKS }
            .toList()
        val expected = (0 until NUM_TICKS step 2).toList()
        assertEquals(expected, actual)
    }
}
