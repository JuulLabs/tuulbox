package com.juul.tuulbox.collections

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

/** Returns an empty [AtomicList]. */
public fun <E> atomicListOf(): AtomicList<E> = AtomicList(persistentListOf())

/** Returns an [AtomicList]. */
public fun <E> atomicListOf(
    vararg elements: E,
): AtomicList<E> = AtomicList(persistentListOf(*elements))

/**
 * A [List] that allows for thread safe, atomic mutation. Returned collections such as [iterator] and
 * [subList] reference a [snapshot] of when they were accessed, and are not mutated when the list is.
 *
 * Although mutable, this class intentionally does not implement [MutableList]. Mutation must use
 * designated mutator functions ([mutate], [snapshotAndMutate], [mutateAndSnapshot]).
 */
public class AtomicList<E> private constructor(
    @PublishedApi
    internal val state: MutableStateFlow<PersistentList<E>>,
) : List<E> {

    /** Construct an [AtomicList] with [initial] list. */
    public constructor(initial: PersistentList<E>) : this(MutableStateFlow(initial))

    /** Returns this list as a [StateFlow]. Each mutation will cause a new emission on this flow. */
    public val snapshots: StateFlow<ImmutableList<E>> = state.asStateFlow()

    /**
     * Returns the current value of this List as an [immutable][ImmutableList] snapshot.
     *
     * This operation is non-copying and efficient.
     */
    public val snapshot: ImmutableList<E>
        get() = snapshots.value

    override val size: Int
        get() = snapshot.size

    override fun get(index: Int): E = snapshot[index]

    override fun isEmpty(): Boolean = snapshot.isEmpty()

    override fun iterator(): Iterator<E> = snapshot.iterator()

    override fun listIterator(): ListIterator<E> = snapshot.listIterator()

    override fun listIterator(index: Int): ListIterator<E> = snapshot.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): ImmutableList<E> = snapshot.subList(fromIndex, toIndex)

    override fun lastIndexOf(element: E): Int = snapshot.lastIndexOf(element)

    override fun indexOf(element: E): Int = snapshot.indexOf(element)

    override fun containsAll(elements: Collection<E>): Boolean = snapshot.containsAll(elements)

    override fun contains(element: E): Boolean = snapshot.contains(element)

    override fun equals(other: Any?): Boolean = snapshot == other

    override fun hashCode(): Int = snapshot.hashCode()

    override fun toString(): String = snapshot.toString()

    /** Mutates this List atomically. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun mutate(mutator: MutableList<E>.() -> Unit) {
        state.update { it.mutate(mutator) }
    }

    /** Mutates this List atomically and returns the previous [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun snapshotAndMutate(mutator: MutableList<E>.() -> Unit): ImmutableList<E> =
        state.getAndUpdate { it.mutate(mutator) }

    /** Mutates this List atomically and returns the new [snapshot]. [mutator] can be evaluated multiple times if a concurrent edit occurs. */
    public inline fun mutateAndSnapshot(mutator: MutableList<E>.() -> Unit): ImmutableList<E> =
        state.updateAndGet { it.mutate(mutator) }
}
