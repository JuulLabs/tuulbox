package com.juul.tuulbox.logging

import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

/** While a [Pool] is logically thread safe, Kotlin/Native's memory model requires @ThreadLocal on instances of this. */
internal class Pool<T>(
    private val factory: () -> T,
    private val refurbish: (T) -> Unit,
) {
    private val lock = reentrantLock()
    private val cache = ArrayDeque<T>()

    fun borrow(): T = lock.withLock { cache.removeLastOrNull() } ?: factory()
    fun recycle(value: T) {
        refurbish(value)
        lock.withLock { cache.addLast(value) }
    }
}
