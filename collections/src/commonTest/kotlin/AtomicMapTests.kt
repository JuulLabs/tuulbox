package com.juul.tuulbox.collections

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

public class AtomicMapTests {

    @Test
    public fun atomicMap_concurrentMutateBlocks_doesNotLoseWrites() = runTest {
        val actual = AtomicMap<String, Int>(persistentMapOf())
        (1..10)
            .map {
                launch(Dispatchers.Default) {
                    for (i in 0 until 500) {
                        actual.mutate { this["count"] = (this["count"] ?: 0) + 1 }
                    }
                }
            }.joinAll()

        assertEquals(mapOf("count" to 5_000), actual)
    }

    @Test
    public fun atomicMap_snapshotAndMutate_returnsPreviousSnapshot() = runTest {
        val atomic = AtomicMap<Int, Int>(persistentMapOf())
        val actual = atomic.snapshotAndMutate {
            put(0, 0)
        }
        assertEquals(emptyMap(), actual)
    }

    @Test
    public fun atomicMap_mutateAndSnapshot_returnsNewSnapshot() = runTest {
        val atomic = AtomicMap<Int, Int>(persistentMapOf())
        val actual = atomic.mutateAndSnapshot {
            put(0, 0)
        }
        assertEquals(mapOf(0 to 0), actual)
    }
}
