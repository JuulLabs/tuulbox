package com.juul.tuulbox.functional

import com.juul.tuulbox.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RotateTests {

    @Test
    fun checkRotateWithArity3() = runTest {
        val expected = stringConcat3("first", "second", "third")
        val actualRotate = stringConcat3.rotate()("third", "first", "second")
        assertEquals(expected, actualRotate)
        val actualRotateBack = stringConcat3.rotateBack()("second", "third", "first")
        assertEquals(expected, actualRotateBack)
    }

    @Test
    fun checkRotateWithArity4() = runTest {
        val expected = stringConcat4("first", "second", "third", "fourth")
        val actualRotate = stringConcat4.rotate()("fourth", "first", "second", "third")
        assertEquals(expected, actualRotate)
        val actualRotateBack = stringConcat4.rotateBack()("second", "third", "fourth", "first")
        assertEquals(expected, actualRotateBack)
    }

    @Test
    fun checkRotateWithArity5() = runTest {
        val expected = stringConcat5("first", "second", "third", "fourth", "fifth")
        val actualRotate = stringConcat5.rotate()("fifth", "first", "second", "third", "fourth")
        assertEquals(expected, actualRotate)
        val actualRotateBack = stringConcat5.rotateBack()("second", "third", "fourth", "fifth", "first")
        assertEquals(expected, actualRotateBack)
    }

    @Test
    fun checkRotateWithArity6() = runTest {
        val expected = stringConcat6("first", "second", "third", "fourth", "fifth", "sixth")
        val actualRotate = stringConcat6.rotate()("sixth", "first", "second", "third", "fourth", "fifth")
        assertEquals(expected, actualRotate)
        val actualRotateBack = stringConcat6.rotateBack()("second", "third", "fourth", "fifth", "sixth", "first")
        assertEquals(expected, actualRotateBack)
    }
}
