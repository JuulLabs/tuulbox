package com.juul.tuulbox.encoding

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IntBitSetTests {
    @Test
    fun roundTripPreservesOriginalValue() {
        val values = intArrayOf(Int.MIN_VALUE, -3, -2, -1, 0, 1, 2, 3, Int.MAX_VALUE)
        for (value in values) {
            assertEquals(expected = value, actual = value.bits.asPrimitive())
        }
    }

    @Test
    fun getWorks() {
        var set = Int.MIN_VALUE.bits
        for (i in 0..30) assertFalse(set[i])
        assertTrue(set[31])

        set = Int.MAX_VALUE.bits
        for (i in 0..30) assertTrue(set[i])

        set = 0.bits
        for (i in 0..31) assertFalse(set[i])
    }

    @Test
    fun setWorks() {
        val set = 1.bits
        set[0] = false
        set[1] = true
        assertFalse(set[0])
        assertTrue(set[1])
        assertEquals(2, set.asPrimitive())
    }

    @Test
    fun boundsChecksEnforced() {
        val set = 0.bits
        assertFailsWith<IllegalArgumentException> { set[-1] }
        assertFailsWith<IllegalArgumentException> { set[-1] = true }
        assertFailsWith<IllegalArgumentException> { set[32] }
        assertFailsWith<IllegalArgumentException> { set[32] = true }
    }
}

class LongBitSetTests {
    @Test
    fun roundTripPreservesOriginalValue() {
        val values = longArrayOf(Long.MIN_VALUE, -3, -2, -1, 0, 1, 2, 3, Long.MAX_VALUE)
        for (value in values) {
            assertEquals(expected = value, actual = value.bits.asPrimitive())
        }
    }

    @Test
    fun getWorks() {
        var set = Long.MIN_VALUE.bits
        for (i in 0..62) assertFalse(set[i])
        assertTrue(set[63])

        set = Long.MAX_VALUE.bits
        for (i in 0..62) assertTrue(set[i])

        set = 0L.bits
        for (i in 0..63) assertFalse(set[i])
    }

    @Test
    fun setWorks() {
        val set = 1L.bits
        set[0] = false
        set[1] = true
        assertFalse(set[0])
        assertTrue(set[1])
        assertEquals(2L, set.asPrimitive())
    }

    @Test
    fun boundsChecksEnforced() {
        val set = 0.bits
        assertFailsWith<IllegalArgumentException> { set[-1] }
        assertFailsWith<IllegalArgumentException> { set[-1] = true }
        assertFailsWith<IllegalArgumentException> { set[64] }
        assertFailsWith<IllegalArgumentException> { set[64] = true }
    }
}
