package com.juul.tuulbox.functional

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class MemoizeTests {

    private class DuplicationChecker {
        private val visitedValues = mutableSetOf<List<*>>()

        fun visit(vararg values: Any?): Array<out Any?> {
            val list = values.toList()
            check(list !in visitedValues)
            visitedValues.add(list)
            return values
        }
    }

    /** Let's test our test utilities, too! */
    @Test
    fun checkDuplicationCheckerWorks() = runTest {
        val checker = DuplicationChecker()
        checker.visit()
        checker.visit(1)
        checker.visit(1, "two", 3.0)
        assertFailsWith<IllegalStateException> { checker.visit() }
        assertFailsWith<IllegalStateException> { checker.visit(1) }
        assertFailsWith<IllegalStateException> { checker.visit(1, "two", 3.0) }
    }

    @Test
    fun checkMemoizeWithArity0() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { duplicationChecker.visit() }.memoize()
        function()
        function()
    }

    @Test
    fun checkMemoizeWithArity1() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int -> duplicationChecker.visit(first) }.memoize()
        function(1)
        function(1)
    }

    @Test
    fun checkMemoizeWithArity2() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int, second: Int -> duplicationChecker.visit(first, second) }.memoize()
        function(1, 2)
        function(1, 2)
    }

    @Test
    fun checkMemoizeWithArity3() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int, second: Int, third: Int ->
            duplicationChecker.visit(first, second, third)
        }.memoize()
        function(1, 2, 3)
        function(1, 2, 3)
    }

    @Test
    fun checkMemoizeWithArity4() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int, second: Int, third: Int, fourth: Int ->
            duplicationChecker.visit(first, second, third, fourth)
        }.memoize()
        function(1, 2, 3, 4)
        function(1, 2, 3, 4)
    }

    @Test
    fun checkMemoizeWithArity5() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int, second: Int, third: Int, fourth: Int, fifth: Int ->
            duplicationChecker.visit(first, second, third, fourth, fifth)
        }.memoize()
        function(1, 2, 3, 4, 5)
        function(1, 2, 3, 4, 5)
    }

    @Test
    fun checkMemoizeWithArity6() = runTest {
        val duplicationChecker = DuplicationChecker()
        val function = { first: Int, second: Int, third: Int, fourth: Int, fifth: Int, sixth: Int ->
            duplicationChecker.visit(first, second, third, fourth, fifth, sixth)
        }.memoize()
        function(1, 2, 3, 4, 5, 6)
        function(1, 2, 3, 4, 5, 6)
    }
}
