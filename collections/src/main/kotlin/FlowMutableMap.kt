package com.juul.tuulbox.collections

import java.util.Collections
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

fun <K, V> MutableMap<K, V>.withFlow() = FlowMutableMap(this)

/**
 * Wraps a [MutableMap] to provide a [Flow] of [onChanged] events. The [onChanged] events emit a
 * copy of the [MutableMap] at the time of the change event.
 */
class FlowMutableMap<K, V> internal constructor(
    private val map: MutableMap<K, V>
) : MutableMap<K, V> by map {

    private val _onChanged = MutableStateFlow<Map<K, V>?>(null)
    val onChanged: Flow<Map<K, V>> = _onChanged.filterNotNull()

    /** @see MutableMap.put */
    override fun put(
        key: K,
        value: V
    ): V? = map.emitChangedAfter { put(key, value) }

    /** @see MutableMap.remove */
    override fun remove(
        key: K
    ): V? = map.emitChangedAfter { remove(key) }

    /**
     * Emits `onChanged` event when [remove] returns `true`.
     *
     * @see MutableMap.remove
     */
    override fun remove(
        key: K,
        value: V
    ): Boolean = map.remove(key, value).also { didRemove ->
        if (didRemove) _onChanged.value = map.unmodifiableCopy()
    }

    /** @see MutableMap.clear */
    override fun clear() = map.emitChangedAfter { clear() }

    /** @see MutableMap.putAll */
    override fun putAll(
        from: Map<out K, V>
    ) = map.emitChangedAfter { putAll(from) }

    /**
     * Emits `onChanged` event when [putIfAbsent] returns `null`.
     *
     * @see MutableMap.putIfAbsent
     */
    override fun putIfAbsent(
        key: K,
        value: V
    ): V? = map.putIfAbsent(key, value).also { previousValue ->
        if (previousValue == null) _onChanged.value = map.unmodifiableCopy()
    }

    /**
     * Emits `onChanged` event when [replace] returns `true`.
     *
     * @see MutableMap.replace
     */
    override fun replace(
        key: K,
        oldValue: V,
        newValue: V
    ): Boolean = map.replace(key, oldValue, newValue).also { didReplace ->
        if (didReplace) _onChanged.value = map.unmodifiableCopy()
    }

    /**
     * Emits `onChanged` event when [replace] returns non-`null`.
     *
     * @see MutableMap.replace
     */
    override fun replace(
        key: K,
        value: V
    ): V? = map.replace(key, value).also { previousValue ->
        if (previousValue != null) _onChanged.value = map.unmodifiableCopy()
    }

    /** @throws UnsupportedOperationException */
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = throw UnsupportedOperationException()

    /** @throws UnsupportedOperationException */
    override val keys: MutableSet<K>
        get() = throw UnsupportedOperationException()

    /** @throws UnsupportedOperationException */
    override val values: MutableCollection<V>
        get() = throw UnsupportedOperationException()

    private inline fun <T> MutableMap<K, V>.emitChangedAfter(
        action: MutableMap<K, V>.() -> T
    ): T {
        val result = action.invoke(this)
        _onChanged.value = unmodifiableCopy()
        return result
    }
}

private fun <K, V> MutableMap<K, V>.unmodifiableCopy() =
    Collections.unmodifiableMap(toMap())
