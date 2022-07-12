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

    @Test
    fun extract_nothing_isZero() {
        val sut: BitSet<Int> = 0.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(0, 0),
        )
    }

    @Test
    fun extract_oneFromLowestBit_isOne() {
        val sut: BitSet<Int> = 1.bits
        assertEquals(
            expected = 1,
            actual = sut.extract(0, 1),
        )
    }

    @Test
    fun extract_zeroFromLowestBit_isZero() {
        val sut: BitSet<Int> = 0.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(0, 1),
        )
    }

    @Test
    fun extract_oneFromHighestBit_isOne() {
        val sut: BitSet<Int> = 0x80000000u.toInt().bits
        assertEquals(
            expected = 1,
            actual = sut.extract(31, 1),
        )
    }

    @Test
    fun extract_zeroFromHighestBit_isZero() {
        val sut: BitSet<Int> = 0.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(31, 1),
        )
    }

    @Test
    fun extract_twoOneBitsFromSecondNibble_isThree() {
        val sut: BitSet<Int> = 0b00110000.bits
        assertEquals(
            expected = 3,
            actual = sut.extract(4, 2),
        )
    }

    @Test
    fun extract_twoZeroBitsFromSecondNibble_isZero() {
        val sut: BitSet<Int> = 0xFFFFFFCFu.toInt().bits
        assertEquals(
            expected = 0,
            actual = sut.extract(4, 2),
        )
    }

    @Test
    fun extract_rangeOfOneFromLowestBit_isOne() {
        val sut: BitSet<Int> = 1.bits
        assertEquals(
            expected = 1,
            actual = sut.extract(0..0),
        )
    }

    @Test
    fun extract_rangeOfOneFromHighestBit_isOne() {
        val sut: BitSet<Int> = 0x80000000u.toInt().bits
        assertEquals(
            expected = 1,
            actual = sut.extract(31..31),
        )
    }

    @Test
    fun extract_rangeOfTwoOneBitsFromSecondNibble_isThree() {
        val sut: BitSet<Int> = 0b00110000.bits
        assertEquals(
            expected = 3,
            actual = sut.extract(4..5),
        )
    }

    @Test
    fun extract_offset_boundsChecksEnforced() {
        val sut: BitSet<Int> = 0.bits
        assertFailsWith<IllegalArgumentException> { sut.extract(-1, 0) }
        assertFailsWith<IllegalArgumentException> { sut.extract(32, 0) }
    }

    @Test
    fun extract_count_boundsChecksEnforced() {
        val sut: BitSet<Int> = 0.bits
        assertFailsWith<IllegalArgumentException> { sut.extract(0, 33) }
        assertFailsWith<IllegalArgumentException> { sut.extract(31, 2) }
    }

    @Test
    fun flatBitMask_oneCount_isOneBit() {
        assertEquals(
            expected = 0b0000_0001,
            actual = flatBitMask(1),
        )
    }

    @Test
    fun flatBitMask_eightCount_isEightBits() {
        assertEquals(
            expected = 0b1111_1111,
            actual = flatBitMask(8),
        )
    }

    @Test
    fun flatBitMask_thirtyTwoCount_isAllOnes() {
        assertEquals(
            expected = 0xFFFFFFFFu.toInt(),
            actual = flatBitMask(32),
        )
    }

    @Test
    fun flatBitMask_boundsChecksEnforced() {
        assertFailsWith<IllegalArgumentException> { flatBitMask(-1) }
        assertFailsWith<IllegalArgumentException> { flatBitMask(33) }
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
        val set = 0L.bits
        assertFailsWith<IllegalArgumentException> { set[-1] }
        assertFailsWith<IllegalArgumentException> { set[-1] = true }
        assertFailsWith<IllegalArgumentException> { set[64] }
        assertFailsWith<IllegalArgumentException> { set[64] = true }
    }

    @Test
    fun extract_nothing_isZero() {
        val sut: BitSet<Long> = 0L.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(0, 0),
        )
    }

    @Test
    fun extract_oneFromLowestBit_isOne() {
        val sut: BitSet<Long> = 1L.bits
        assertEquals(
            expected = 1,
            actual = sut.extract(0, 1),
        )
    }

    @Test
    fun extract_zeroFromLowestBit_isZero() {
        val sut: BitSet<Long> = 0L.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(0, 1),
        )
    }

    @Test
    fun extract_oneFromHighestBit_isOne() {
        val sut: BitSet<Long> = 0x8000_0000_0000_0000uL.toLong().bits
        assertEquals(
            expected = 1,
            actual = sut.extract(63, 1),
        )
    }

    @Test
    fun extract_zeroFromHighestBit_isZero() {
        val sut: BitSet<Long> = 0L.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(63, 1),
        )
    }

    @Test
    fun extract_twoOneBitsFromSecondNibble_isThree() {
        val sut: BitSet<Long> = 0b00110000L.bits
        assertEquals(
            expected = 3,
            actual = sut.extract(4, 2),
        )
    }

    @Test
    fun extract_twoZeroBitsFromSecondNibble_isZero() {
        val sut: BitSet<Long> = 0xFFFFFFCFL.bits
        assertEquals(
            expected = 0,
            actual = sut.extract(4, 2),
        )
    }

    @Test
    fun extract_rangeOfOneFromLowestBit_isOne() {
        val sut: BitSet<Long> = 1L.bits
        assertEquals(
            expected = 1,
            actual = sut.extract(0..0),
        )
    }

    @Test
    fun extract_rangeOfOneFromHighestBit_isOne() {
        val sut: BitSet<Long> = 0x8000_0000_0000_0000uL.toLong().bits
        assertEquals(
            expected = 1,
            actual = sut.extract(63..63),
        )
    }

    @Test
    fun extract_rangeOfTwoOneBitsFromSecondNibble_isThree() {
        val sut: BitSet<Long> = 0b00110000L.bits
        assertEquals(
            expected = 3,
            actual = sut.extract(4..5),
        )
    }

    @Test
    fun extract_offset_boundsChecksEnforced() {
        val sut: BitSet<Long> = 0L.bits
        assertFailsWith<IllegalArgumentException> { sut.extract(-1, 0) }
        assertFailsWith<IllegalArgumentException> { sut.extract(64, 0) }
    }

    @Test
    fun extract_count_boundsChecksEnforced() {
        val sut: BitSet<Long> = 0L.bits
        assertFailsWith<IllegalArgumentException> { sut.extract(0, 65) }
        assertFailsWith<IllegalArgumentException> { sut.extract(64, 2) }
    }

    @Test
    fun flatLongBitMask_oneCount_isOneBit() {
        assertEquals(
            expected = 0b0000_0001L,
            actual = flatLongBitMask(1),
        )
    }

    @Test
    fun flatLongBitMask_eightCount_isEightBits() {
        assertEquals(
            expected = 0b1111_1111L,
            actual = flatLongBitMask(8),
        )
    }

    @Test
    fun flatLongBitMask_sixtyFourCount_isAllOnes() {
        assertEquals(
            expected = 0xFFFFFFFF_FFFFFFFFuL.toLong(),
            actual = flatLongBitMask(64),
        )
    }

    @Test
    fun flatLongBitMask_boundsChecksEnforced() {
        assertFailsWith<IllegalArgumentException> { flatLongBitMask(-1) }
        assertFailsWith<IllegalArgumentException> { flatLongBitMask(65) }
    }
}
