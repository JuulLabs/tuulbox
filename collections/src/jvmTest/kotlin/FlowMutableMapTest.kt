package com.juul.tuulbox.collections

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.debug.junit4.CoroutinesTimeout
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FlowMutableMapTest {

    @get:Rule
    val timeoutRule = CoroutinesTimeout.seconds(1)

    @Test
    fun `Adding produces onChanged events`() = runBlocking {
        val map = mutableMapOf<String, Int>().withFlow()
        val events = Channel<Map<String, Int>>()
        val job = map.onChanged.onEach(events::send).launchIn(this)

        assertEquals(
            expected = 0,
            actual = map.size
        )

        map["1"] = 1
        events.receiveAndAssert(mapOf("1" to 1))
        assertEquals(
            expected = 1,
            actual = map.size
        )

        map["2"] = 2
        events.receiveAndAssert(mapOf("1" to 1, "2" to 2))
        assertEquals(
            expected = 2,
            actual = map.size
        )

        job.cancelAndJoin()
    }

    @Test
    fun `Replacing produces onChanged events`() = runBlocking {
        val map = mutableMapOf<String, Int>().withFlow()
        val events = Channel<Map<String, Int>>()
        val job = map.onChanged.onEach(events::send).launchIn(this)

        map.putAll(mapOf("1" to 1, "2" to 2))
        events.receiveAndAssert(mapOf("1" to 1, "2" to 2))

        map["1"] = 100
        events.receiveAndAssert(mapOf("1" to 100, "2" to 2))

        assertNull(map.replace("3", -300))
        // Skip `receiveAndAssert` because the `replace` failed (didn't trigger onChanged).

        assertEquals(
            expected = 2, // Previous value (indicates that value was successfully replaced).
            actual = map.replace("2", -200)
        )
        events.receiveAndAssert(mapOf("1" to 100, "2" to -200))

        assertFalse(map.replace("1", 50, 150))
        // Skip `receiveAndAssert` because the `replace` failed (didn't trigger onChanged).

        assertTrue(map.replace("1", 100, 250))
        events.receiveAndAssert(mapOf("1" to 250, "2" to -200))

        job.cancelAndJoin()
    }

    @Test
    fun `Removing produces onChanged events`() = runBlocking {
        val map = mutableMapOf("A" to 123, "B" to 987).withFlow()
        val events = Channel<Map<String, Int>>()
        val job = map.onChanged.onEach(events::send).launchIn(this)

        assertFalse(map.remove("B", -1))
        // Skip `receiveAndAssert` because the `remove` failed (didn't trigger onChanged).

        assertTrue(map.remove("B", 987))
        events.receiveAndAssert(mapOf("A" to 123))

        map.remove("A")
        events.receiveAndAssert(emptyMap())

        job.cancelAndJoin()
    }

    @Test
    fun `putIfAbsent produces onChanged events`() = runBlocking {
        val map = mutableMapOf("A" to 123, "B" to 987).withFlow()
        val events = Channel<Map<String, Int>>()
        val job = map.onChanged.onEach(events::send).launchIn(this)

        assertEquals(
            expected = 987, // Previous value (indicates we didn't perform a "put").
            actual = map.putIfAbsent("B", -1)
        )
        // Skip `receiveAndAssert` because the `putIfAbsent` "failed" (didn't trigger onChanged).

        assertNull(map.putIfAbsent("C", -128))
        events.receiveAndAssert(mapOf("A" to 123, "B" to 987, "C" to -128))

        job.cancelAndJoin()
    }

    @Test
    fun `Clearing produces onChanged event`() = runBlocking {
        val map = mutableMapOf("A" to 123, "B" to 987).withFlow()
        val events = Channel<Map<String, Int>>()
        val job = map.onChanged.onEach(events::send).launchIn(this)

        map["C"] = 1337
        events.receiveAndAssert(mapOf("A" to 123, "B" to 987, "C" to 1337))

        map.clear()
        events.receiveAndAssert(emptyMap())

        job.cancelAndJoin()
    }

    @Test
    fun `FlowConcurrentMap 'entries' throws UnsupportedOperationException`() {
        assertFailsWith<UnsupportedOperationException> {
            mutableMapOf<String, Int>().withFlow().entries
        }
    }

    @Test
    fun `FlowConcurrentMap 'keys' throws UnsupportedOperationException`() {
        assertFailsWith<UnsupportedOperationException> {
            mutableMapOf<String, Int>().withFlow().keys
        }
    }

    @Test
    fun `FlowConcurrentMap 'values' throws UnsupportedOperationException`() {
        assertFailsWith<UnsupportedOperationException> {
            mutableMapOf<String, Int>().withFlow().values
        }
    }
}
