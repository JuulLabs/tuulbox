package com.juul.tuulbox.collections

import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

/** Returns an [AtomicMap] that guarantees preservation of iteration order, but may be slower. */
private fun <K, V> atomicMapOf(
    vararg pairs: Pair<K, V>,
): AtomicMap<K, V> = AtomicMap(persistentMapOf(*pairs))

/** Returns an [AtomicMap] that does not guarantee preservation of iteration order, but may be faster. */
private fun <K, V> atomicHashMapOf(
    vararg pairs: Pair<K, V>,
): AtomicMap<K, V> = AtomicMap(persistentHashMapOf(*pairs))

/**
 * A [Map] that allows for thread safe, atomic mutation. Returned collections such as [entries] and
 * [iterator] reference a [snapshot] of when they were accessed, and are not mutated when the map is.
 *
 * Provides methods similar to [MutableMap], although intentionally does not implement that interface
 * to discourage accidental use of methods that are not thread-safe (such as [MutableMap.getOrPut]).
 */
public class AtomicMap<K, V> private constructor(
    private val state: MutableStateFlow<PersistentMap<K, V>>,
) : Map<K, V> {

    internal constructor(initial: PersistentMap<K, V>) : this(MutableStateFlow(initial))

    /** Returns this map as a [StateFlow]. Each call to [put], [mutate], etc will cause a new emit on this flow. */
    public val flow: StateFlow<ImmutableMap<K, V>> = state.asStateFlow()

    /**
     * Returns the current value of this map as an [immutable][ImmutableMap] snapshot.
     *
     * This operation is non-copying and efficient.
     */
    public val snapshot: ImmutableMap<K, V> get() = state.value

    override val size: Int
        get() = snapshot.size

    override val entries: ImmutableSet<Map.Entry<K, V>>
        get() = snapshot.entries

    override val keys: ImmutableSet<K>
        get() = snapshot.keys

    override val values: ImmutableCollection<V>
        get() = snapshot.values

    override fun containsKey(key: K): Boolean = snapshot.containsKey(key)

    override fun containsValue(value: V): Boolean = snapshot.containsValue(value)

    override fun get(key: K): V? = snapshot[key]

    override fun isEmpty(): Boolean = snapshot.isEmpty()

    /** Equivalent to [MutableMap.clear]. */
    public fun clear() {
        state.update { it.clear() }
    }

    /** Equivalent to [MutableMap.remove]. */
    public fun remove(key: K): V? =
        state.getAndUpdate { it.remove(key) }[key]

    /** Equivalent to [MutableMap.putAll]. */
    public fun putAll(from: Map<out K, V>) {
        state.update { it.putAll(from) }
    }

    /** Equivalent to [MutableMap.put]. */
    public fun put(key: K, value: V): V? =
        state.getAndUpdate { it.put(key, value) }[key]

    /** Equivalent to [MutableMap.set]. */
    public operator fun set(key: K, value: V) {
        state.update { it.put(key, value) }
    }

    /** Atomic version of [MutableMap.getOrPut]. [defaultValue] can be evaluated multiple times if a concurrent edit occurs. */
    public fun getOrPut(key: K, defaultValue: () -> V): V {
        val value = state.updateAndGet { map ->
            if (key in map) {
                map
            } else {
                map.put(key, defaultValue())
            }
        }[key]
        // `as V` is used instead of `!!` or [checkNotNull] because it upcasts away from `null` when V is not nullable,
        // but does not throw an exception when used with nullable V.
        @Suppress("UNCHECKED_CAST")
        return value as V
    }

    /** Mutates this map atomically. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public fun mutate(mutator: MutableMap<K, V>.() -> Unit) {
        state.update { it.mutate(mutator) }
    }

    /** Mutates this map atomically and returns the previous [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public fun getAndMutate(mutator: MutableMap<K, V>.() -> Unit): ImmutableMap<K, V> =
        state.getAndUpdate { it.mutate(mutator) }

    /** Mutates this map atomically and returns the new [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public fun mutateAndGet(mutator: MutableMap<K, V>.() -> Unit): ImmutableMap<K, V> =
        state.updateAndGet { it.mutate(mutator) }
}
