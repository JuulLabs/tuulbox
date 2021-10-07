package com.juul.tuulbox.collections

import kotlinx.atomicfu.locks.ReentrantLock
import kotlinx.atomicfu.locks.reentrantLock
import kotlinx.atomicfu.locks.withLock

/** Returns an empty new [SynchronizedMap]. */
public fun <K, V> synchronizedMapOf(): SynchronizedMap<K, V> =
    SynchronizedMap(linkedMapOf())

/** Returns a new [SynchronizedMap] with the specified contents. */
public fun <K, V> synchronizedMapOf(vararg pairs: Pair<K, V>): SynchronizedMap<K, V> =
    SynchronizedMap(linkedMapOf(*pairs))

/** A [MutableMap] where all reads and writes are protected by a [ReentrantLock]. */
public class SynchronizedMap<K, V> internal constructor(
    private val inner: LinkedHashMap<K, V>,
) : MutableMap<K, V> {

    public constructor() : this(LinkedHashMap())
    public constructor(capacity: Int) : this(LinkedHashMap(capacity))
    public constructor(capacity: Int, loadFactor: Float) : this(LinkedHashMap(capacity, loadFactor))
    public constructor(original: Map<K, V>) : this(LinkedHashMap(original))

    private inner class Entry(
        val entry: MutableMap.MutableEntry<K, V>,
    ) : MutableMap.MutableEntry<K, V> {
        override val key: K get() = entry.key
        override val value: V get() = entry.value

        override fun setValue(newValue: V): V =
            lock.withLock { entry.setValue(newValue) }
    }

    @PublishedApi
    internal val lock: ReentrantLock = reentrantLock()

    override val size: Int
        get() = lock.withLock { inner.size }

    override fun containsKey(key: K): Boolean =
        lock.withLock { inner.containsKey(key) }

    override fun containsValue(value: V): Boolean =
        lock.withLock { inner.containsValue(value) }

    override fun get(key: K): V? =
        lock.withLock { inner[key] }

    override fun isEmpty(): Boolean =
        lock.withLock { inner.isEmpty() }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = lock.withLock {
            inner.entries.asSequence()
                .map { entry -> Entry(entry) as MutableMap.MutableEntry<K, V> }
                .toMutableSet()
                .readOnly()
        }

    override val keys: MutableSet<K>
        get() = lock.withLock { inner.keys.readOnly() }

    override val values: MutableCollection<V>
        get() = lock.withLock { inner.values.readOnly() }

    override fun clear() {
        lock.withLock { inner.clear() }
    }

    override fun put(key: K, value: V): V? =
        lock.withLock { inner.put(key, value) }

    override fun putAll(from: Map<out K, V>) {
        lock.withLock { inner.putAll(from) }
    }

    override fun remove(key: K): V? =
        lock.withLock { inner.remove(key) }

    /**
     * Performs [action] with this map's [ReentrantLock] locked. Useful for when correctness dictates that unlocks should not
     * occur between multiple calls, such as when calling [MutableMap.getOrPut].
     *
     * ```kotlin
     * // Extension functions on `MutableMap` that compose read-and-writes can be protected to work atomically
     * map.synchronized { getOrPut("key") { defaultValue } }
     * // Also useful when a value is updated based on its previous value.
     * map.synchronized {
     *     val previous = get("key")
     *     put("key", (previous ?: 0) + 1)
     * }
     * ```
     */
    public inline fun <T> synchronized(action: SynchronizedMap<K, V>.() -> T): T = lock.withLock { action(this) }
}

/**
 * While it might seem weird to return [MutableSet] and then crash on any mutation, this is actually the same behavior
 * you get from, for example `HashMap.entries`. This class exists to enable that same behavior for [SynchronizedMap].
 */
private fun <E> MutableSet<E>.readOnly(): MutableSet<E> {
    val parent = this
    return object : Set<E> by parent, MutableSet<E> {
        override fun iterator(): MutableIterator<E> =
            object : Iterator<E> by parent.iterator(), MutableIterator<E> {
                override fun remove() = throw UnsupportedOperationException()
            }

        override fun add(element: E): Boolean = throw UnsupportedOperationException()
        override fun addAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
        override fun clear() = throw UnsupportedOperationException()
        override fun remove(element: E): Boolean = throw UnsupportedOperationException()
        override fun removeAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
        override fun retainAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
    }
}

/**
 * While it might seem weird to return [MutableCollection] and then crash on any mutation, this is actually the same behavior
 * you get from, for example `HashMap.values`. This class exists to enable that same behavior for [SynchronizedMap].
 */
private fun <E> MutableCollection<E>.readOnly(): MutableCollection<E> {
    val parent = this
    return object : Collection<E> by parent, MutableCollection<E> {
        override fun iterator(): MutableIterator<E> =
            object : Iterator<E> by parent.iterator(), MutableIterator<E> {
                override fun remove() = throw UnsupportedOperationException()
            }

        override fun add(element: E): Boolean = throw UnsupportedOperationException()
        override fun addAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
        override fun clear() = throw UnsupportedOperationException()
        override fun remove(element: E): Boolean = throw UnsupportedOperationException()
        override fun removeAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
        override fun retainAll(elements: Collection<E>): Boolean = throw UnsupportedOperationException()
    }
}
