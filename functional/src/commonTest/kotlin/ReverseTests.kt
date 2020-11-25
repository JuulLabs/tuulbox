package com.juul.tuulbox.functional

import com.juul.tuulbox.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ReverseTests {

    @Test
    fun checkReverseWithArity2() = runTest {
        val expected = stringConcat2("first", "second")
        val actual = stringConcat2.reverse()("second", "first")
        assertEquals(expected, actual)
    }

    @Test
    fun checkReverseWithArity3() = runTest {
        val expected = stringConcat3("first", "second", "third")
        val actual = stringConcat3.reverse()("third", "second", "first")
        assertEquals(expected, actual)
    }

    @Test
    fun checkReverseWithArity4() = runTest {
        val expected = stringConcat4("first", "second", "third", "fourth")
        val actual = stringConcat4.reverse()("fourth", "third", "second", "first")
        assertEquals(expected, actual)
    }

    @Test
    fun checkReverseWithArity5() = runTest {
        val expected = stringConcat5("first", "second", "third", "fourth", "fifth")
        val actual = stringConcat5.reverse()("fifth", "fourth", "third", "second", "first")
        assertEquals(expected, actual)
    }

    @Test
    fun checkReverseWithArity6() = runTest {
        val expected = stringConcat6("first", "second", "third", "fourth", "fifth", "sixth")
        val actual = stringConcat6.reverse()("sixth", "fifth", "fourth", "third", "second", "first")
        assertEquals(expected, actual)
    }
}
