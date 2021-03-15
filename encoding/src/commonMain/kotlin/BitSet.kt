package com.juul.tuulbox.encoding

public interface BitSet<T> {
    public operator fun get(index: Int): Boolean
    public operator fun set(index: Int, value: Boolean): BitSet<T>
    public fun asPrimitive(): T
}

// Would kill for C++ templates right about now. Which is not something I can normally say.

public val Int.bits: BitSet<Int> get() = IntBitSet(this)
public val Long.bits: BitSet<Long> get() = LongBitSet(this)

public data class IntBitSet(private var buffer: Int) : BitSet<Int> {
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
}

public data class LongBitSet(private var buffer: Long) : BitSet<Long> {
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
}
