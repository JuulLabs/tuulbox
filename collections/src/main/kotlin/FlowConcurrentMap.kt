package com.juul.tuulbox.collections

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

fun <K, V> ConcurrentMap<K, V>.withFlow() = FlowConcurrentMap(this)

/**
 * Wraps a [ConcurrentMap] to provide a [Flow] of [onChanged] events. The [onChanged] events emit
 * a copy of the [ConcurrentMap] at the time of the change event.
 */
class FlowConcurrentMap<K, V>(
    private val map: ConcurrentMap<K, V> = ConcurrentHashMap()
) : ConcurrentMap<K, V> by map {

    private val _onChanged = MutableStateFlow<Map<K, V>?>(null)
    val onChanged: Flow<Map<K, V>> = _onChanged.filterNotNull()

    /** @see ConcurrentMap.put */
    override fun put(
        key: K,
        value: V
    ): V? = map.emitChangedAfter { put(key, value) }

    /** @see ConcurrentMap.remove */
    override fun remove(
        key: K
    ): V? = map.emitChangedAfter { remove(key) }

    /**
     * Emits `onChanged` event when [remove] returns `true`.
     *
     * @see ConcurrentMap.remove
     */
    override fun remove(
        key: K,
        value: V
    ): Boolean = map.remove(key, value).also { didRemove ->
        if (didRemove) _onChanged.value = map.toMap()
    }

    /** @see ConcurrentMap.clear */
    override fun clear() = map.emitChangedAfter { clear() }

    /** @see ConcurrentMap.putAll */
    override fun putAll(
        from: Map<out K, V>
    ) = map.emitChangedAfter { putAll(from) }

    /**
     * Emits `onChanged` event when [putIfAbsent] returns `null`.
     *
     * @see ConcurrentMap.putIfAbsent
     */
    override fun putIfAbsent(
        key: K,
        value: V
    ): V? = map.putIfAbsent(key, value).also { previousValue ->
        if (previousValue == null) _onChanged.value = map.toMap()
    }

    /**
     * Emits `onChanged` event when [replace] returns `true`.
     *
     * @see ConcurrentMap.replace
     */
    override fun replace(
        key: K,
        oldValue: V,
        newValue: V
    ): Boolean = map.replace(key, oldValue, newValue).also { didReplace ->
        if (didReplace) _onChanged.value = map.toMap()
    }

    /**
     * Emits `onChanged` event when [replace] returns non-`null`.
     *
     * @see ConcurrentMap.replace
     */
    override fun replace(
        key: K,
        value: V
    ): V? = map.replace(key, value).also { previousValue ->
        if (previousValue != null) _onChanged.value = map.toMap()
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

    private inline fun <T> ConcurrentMap<K, V>.emitChangedAfter(
        action: ConcurrentMap<K, V>.() -> T
    ): T {
        val result = action.invoke(this)
        _onChanged.value = toMap()
        return result
    }
}
