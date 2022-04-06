package com.juul.tuulbox.functional

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class PartialApplyTests {

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun checkPartialApply() = runTest {
        val expected = stringConcat6("first", "second", "third", "fourth", "fifth", "sixth")
        val actual = stringConcat6
            .partialApply("first")
            .partialApply("second")
            .partialApply("third")
            .partialApply("fourth")
            .partialApply("fifth")
            .partialApply("sixth")
            .invoke()
        assertEquals(expected, actual)
    }
}
