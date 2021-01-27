package com.juul.tuulbox.temporal

import com.juul.tuulbox.test.assertSimilar
import com.juul.tuulbox.test.runTest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.seconds

@ExperimentalTime
private val EPSILON = 5.seconds

@ExperimentalTime
// @AndroidIgnore("Cannot use Android classes BroadcastReceiver or IntentFilter.")
class TemporalFlowTests {

    @Test
    fun instantFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now()
        val actual = instantFlow().first()
        assertSimilar(expected, EPSILON, actual)
    }

    @Test
    fun localDateTimeFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val actual = localDateTimeFlow().first()
        assertSimilar(expected, EPSILON, actual)
    }

    @Test
    fun localDateFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val actual = localDateFlow().first()
        assertEquals(expected, actual)
    }
}
