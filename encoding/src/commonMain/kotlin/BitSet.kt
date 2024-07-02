package com.juul.tuulbox.encoding

public interface BitSet<T> {
    public operator fun get(index: Int): Boolean

    public operator fun set(index: Int, value: Boolean): BitSet<T>

    public fun asPrimitive(): T

    /** Extracts [count] bits at [offset]. e.g. `0b00001100.extract(2, 2) = 0b11 = 0x03`. */
    public fun extract(offset: Int, count: Int): T
}

/** Extracts [range] bits. e.g. `0b00001100.extract(2..3) = 0b11 = 0x03`. */
public fun <T> BitSet<T>.extract(range: IntRange): T =
    extract(range.first, range.last - range.first + 1)

// Would kill for C++ templates right about now. Which is not something I can normally say.

public val Int.bits: BitSet<Int> get() = IntBitSet(this)
public val Long.bits: BitSet<Long> get() = LongBitSet(this)

public data class IntBitSet(
    private var buffer: Int,
) : BitSet<Int> {
    override operator fun get(index: Int): Boolean {
        require(index in 0 until 32)
        val mask = 1 shl index
        return (buffer and mask) == mask
    }

    override operator fun set(index: Int, value: Boolean): IntBitSet = this.apply {
        require(index in 0 until 32)
        buffer = when (value) {
            true -> buffer or (1 shl index)
            false -> buffer and (1 shl index).inv()
        }
    }

    override fun asPrimitive(): Int = buffer

    override fun extract(offset: Int, count: Int): Int {
        require(offset in 0 until 32) { "Offset expected 0-31 but got $offset" }
        require(count in 0 until 32 - offset + 1) { "count expected 0-${32 - offset + 1} but got $count" }
        return (buffer ushr offset) and flatBitMask(count)
    }
}

public data class LongBitSet(
    private var buffer: Long,
) : BitSet<Long> {
    override operator fun get(index: Int): Boolean {
        require(index in 0 until 64)
        val mask = 1L shl index
        return (buffer and mask) == mask
    }

    override operator fun set(index: Int, value: Boolean): LongBitSet = this.apply {
        require(index in 0 until 64)
        buffer = when (value) {
            true -> buffer or (1L shl index)
            false -> buffer and (1L shl index).inv()
        }
    }

    override fun asPrimitive(): Long = buffer

    override fun extract(offset: Int, count: Int): Long {
        require(offset in 0 until 64) { "Offset expected 0-63 but got $offset" }
        require(count in 0 until 64 - offset + 1) { "count expected 0-${64 - offset + 1} but got $count" }
        return (buffer ushr offset) and flatLongBitMask(count)
    }
}

/** Create an [Int] mask of `count` length e.g. flatBitMask(3) = 0b00000111`. */
public fun flatBitMask(count: Int): Int {
    require(count in 0..32) { "count expected 0-32 but got $count" }
    return if (count == 32) -1 else (1 shl count) - 1
}

/** Create a [Long] mask of `count` length e.g. flatLongBitMask(3) = 0b00000111L`. */
public fun flatLongBitMask(count: Int): Long {
    require(count in 0..64) { "count expected 0-64 but got $count" }
    return if (count == 64) -1L else (1L shl count) - 1L
}
