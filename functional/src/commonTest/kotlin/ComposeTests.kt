package com.juul.tuulbox.functional

import com.juul.tuulbox.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ComposeTests {
    @Test
    fun checkOfOrderIsCorrect() = runTest {
        assertEquals(202, double.of(increment).invoke(100))
        assertEquals(201, increment.of(double).invoke(100))
    }

    @Test
    fun checkThenOrderIsCorrect() = runTest {
        assertEquals(201, double.then(increment).invoke(100))
        assertEquals(202, increment.then(double).invoke(100))
    }
}
