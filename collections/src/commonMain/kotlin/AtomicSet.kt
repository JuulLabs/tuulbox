package com.juul.tuulbox.collections

import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentHashSetOf
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

/** Returns an empty [AtomicSet] that guarantees preservation of iteration order, but may be slower. */
public fun <E> atomicSetOf(): AtomicSet<E> = AtomicSet(persistentSetOf())

/** Returns an [AtomicSet] that guarantees preservation of iteration order, but may be slower. */
public fun <E> atomicSetOf(
    vararg elements: E,
): AtomicSet<E> = AtomicSet(persistentSetOf(*elements))

/** Returns an empty [AtomicSet] that does not guarantee preservation of iteration order, but may be faster. */
public fun <E> atomicHashSetOf(): AtomicSet<E> = AtomicSet(persistentHashSetOf())

/** Returns an [AtomicSet] that does not guarantee preservation of iteration order, but may be faster. */
public fun <E> atomicHashSetOf(
    vararg elements: E,
): AtomicSet<E> = AtomicSet(persistentHashSetOf(*elements))

/**
 * A [Set] that allows for thread safe, atomic mutation. Returned [iterator] references a [snapshot]
 * of when this was accessed, and is not mutated when the set is.
 *
 * Although mutable, this class intentionally does not implement [MutableSet]. Mutation must use
 * designated mutator functions ([mutate], [snapshotAndMutate], [mutateAndSnapshot]).
 */
public class AtomicSet<E> private constructor(
    @PublishedApi
    internal val state: MutableStateFlow<PersistentSet<E>>,
) : Set<E> {

    /** Construct an [AtomicSet] with [initial] set. */
    public constructor(initial: PersistentSet<E>) : this(MutableStateFlow(initial))

    /** Returns this set as a [StateFlow]. Each mutation will cause a new emission on this flow. */
    public val snapshots: StateFlow<ImmutableSet<E>> = state.asStateFlow()

    /**
     * Returns the current value of this set as an [immutable][ImmutableSet] snapshot.
     *
     * This operation is non-copying and efficient.
     */
    public val snapshot: ImmutableSet<E>
        get() = snapshots.value

    override val size: Int
        get() = snapshot.size

    override fun containsAll(elements: Collection<E>): Boolean = snapshot.containsAll(elements)

    override fun contains(element: E): Boolean = snapshot.contains(element)

    override fun isEmpty(): Boolean = snapshot.isEmpty()

    override fun iterator(): Iterator<E> = snapshot.iterator()

    override fun equals(other: Any?): Boolean = snapshot == other

    override fun hashCode(): Int = snapshot.hashCode()

    override fun toString(): String = snapshot.toString()

    /** Mutates this set atomically. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun mutate(mutator: MutableSet<E>.() -> Unit) {
        state.update { it.mutate(mutator) }
    }

    /** Mutates this set atomically and returns the previous [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun snapshotAndMutate(mutator: MutableSet<E>.() -> Unit): ImmutableSet<E> =
        state.getAndUpdate { it.mutate(mutator) }

    /** Mutates this set atomically and returns the new [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun mutateAndSnapshot(mutator: MutableSet<E>.() -> Unit): ImmutableSet<E> =
        state.updateAndGet { it.mutate(mutator) }
}
