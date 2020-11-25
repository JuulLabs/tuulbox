package com.juul.tuulbox.functional

import com.juul.tuulbox.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RepeatTests {

    @Test
    fun checkRepeatNegativeThrowsException() = runTest {
        assertFailsWith<IllegalArgumentException> { double.repeat(-1) }
        assertFailsWith<IllegalArgumentException> { double.repeat(Int.MIN_VALUE) }
    }

    @Test
    fun checkRepeatZeroReturnsIdentityFunction() = runTest {
        val repeated = double.repeat(0)
        for (input in listOf(0, 1, 2, 3, 5)) {
            assertEquals(identity(input), repeated(input))
        }
    }

    @Test
    fun checkRepeatOneReturnsOriginalFunction() = runTest {
        val repeated = double.repeat(1)
        for (input in listOf(0, 1, 2, 3, 5)) {
            assertEquals(double(input), repeated(input))
        }
    }

    @Test
    fun checkRepeatTenReturnsRepeatedFunction() = runTest {
        val repeated = double.repeat(10)
        assertEquals(1024, repeated(1))
    }
}
