package com.juul.tuulbox.coroutines.flow

import com.juul.tuulbox.test.runTest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlin.test.Test
import kotlin.test.assertEquals

class CombineParametersTest {

    @Test
    fun testSixParameters() = runTest {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6")
        ) { a, b, c, d, e, f ->
            a + b + c + d + e + f
        }
        assertEquals("1234null6", flow.single())
    }

    @Test
    fun testSevenParameters() = runTest {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7)
        ) { a, b, c, d, e, f, g ->
            a + b + c + d + e + f + g
        }
        assertEquals("1234null67", flow.single())
    }

    @Test
    fun testEightParameters() = runTest {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8")
        ) { a, b, c, d, e, f, g, h ->
            a + b + c + d + e + f + g + h
        }
        assertEquals("1234null678", flow.single())
    }

    @Test
    fun testNineParameters() = runTest {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8"),
            flowOf(9.toByte())
        ) { a, b, c, d, e, f, g, h, i ->
            a + b + c + d + e + f + g + h + i
        }
        assertEquals("1234null6789", flow.single())
    }

    @Test
    fun testTenParameters() = runTest {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8"),
            flowOf(9.toByte()),
            flowOf(null)
        ) { a, b, c, d, e, f, g, h, i, j ->
            a + b + c + d + e + f + g + h + i + j
        }
        assertEquals("1234null6789null", flow.single())
    }
}
