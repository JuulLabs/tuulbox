package com.juul.tuulbox.functional

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class CurryTests {

    @Test
    fun checkCurryWorksWithArity2() = runTest {
        val expected = stringConcat2("one", "two")
        val actualCurry = stringConcat2.curry()("one")("two")
        assertEquals(expected, actualCurry)
    }

    @Test
    fun checkCurryWorksWithArity3() = runTest {
        val expected = stringConcat3("one", "two", "three")
        val actualCurry = stringConcat3.curry()("one")("two")("three")
        assertEquals(expected, actualCurry)
        val actualCurryFirst = stringConcat3.curryFirst()("one")("two", "three")
        assertEquals(expected, actualCurryFirst)
    }

    @Test
    fun checkCurryWorksWithArity4() = runTest {
        val expected = stringConcat4("one", "two", "three", "four")
        val actualCurry = stringConcat4.curry()("one")("two")("three")("four")
        assertEquals(expected, actualCurry)
        val actualCurryFirst = stringConcat4.curryFirst()("one")("two", "three", "four")
        assertEquals(expected, actualCurryFirst)
    }

    @Test
    fun checkCurryWorksWithArity5() = runTest {
        val expected = stringConcat5("one", "two", "three", "four", "five")
        val actualCurry = stringConcat5.curry()("one")("two")("three")("four")("five")
        assertEquals(expected, actualCurry)
        val actualCurryFirst = stringConcat5.curryFirst()("one")("two", "three", "four", "five")
        assertEquals(expected, actualCurryFirst)
    }

    @Test
    fun checkCurryWorksWithArity6() = runTest {
        val expected = stringConcat6("one", "two", "three", "four", "five", "six")
        val actualCurry = stringConcat6.curry()("one")("two")("three")("four")("five")("six")
        assertEquals(expected, actualCurry)
        val actualCurryFirst = stringConcat6.curryFirst()("one")("two", "three", "four", "five", "six")
        assertEquals(expected, actualCurryFirst)
    }
}
