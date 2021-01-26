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
@AndroidIgnore("Cannot use Android classes BroadcastReceiver or IntentFilter.")
class TemporalFlowTests {

    @Test
    fun instantFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now()
        val actual = instantFlow().first()
        assertSimilar(expected, EPSILON, actual)
    }

    @Test
    fun instantStateFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now()
        val actual = instantFlow(this, SharingStarted.WhileSubscribed()).value
        assertSimilar(expected, EPSILON, actual)
        closeChildrenInJob()
    }

    @Test
    fun localDateTimeFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val actual = localDateTimeFlow().first()
        assertSimilar(expected, EPSILON, actual)
    }

    @Test
    fun localDateTimeStateFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val actual = localDateTimeFlow(this, SharingStarted.WhileSubscribed()).value
        assertSimilar(expected, EPSILON, actual)
        closeChildrenInJob()
    }

    @Test
    fun localDateFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val actual = localDateFlow().first()
        assertEquals(expected, actual)
    }

    @Test
    fun localDateStateFlowInitialValueIsCorrect() = runTest {
        val expected = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val actual = localDateFlow(this, SharingStarted.WhileSubscribed()).value
        assertEquals(expected, actual)
        closeChildrenInJob()
    }

    /** Close children of this job. This is important because a state flow never completes on its own. */
    private fun CoroutineScope.closeChildrenInJob() {
        coroutineContext[Job]?.cancelChildren()
    }
}
