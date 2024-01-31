package com.juul.tuulbox.collections

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

public class AtomicMapTests {

    @Test
    public fun atomicMap_concurrentMutateBlocks_doesNotLoseWrites() = runTest {
        val actual = atomicMapOf<String, Int>()
        (1..10).map {
            launch(Dispatchers.Default) {
                for (i in 0 until 500) {
                    actual.mutate { this["count"] = (this["count"] ?: 0) + 1 }
                }
            }
        }.joinAll()

        assertEquals(mapOf("count" to 5_000), actual)
    }
}
