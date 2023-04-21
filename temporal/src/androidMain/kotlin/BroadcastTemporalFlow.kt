package com.juul.tuulbox.temporal

import android.content.Intent
import android.content.IntentFilter
import com.juul.tuulbox.coroutines.flow.broadcastReceiverFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@PublishedApi
internal val temporalEventFilter: IntentFilter = IntentFilter().apply {
    addAction(Intent.ACTION_DATE_CHANGED)
    addAction(Intent.ACTION_TIME_CHANGED)
    addAction(Intent.ACTION_TIME_TICK)
    addAction(Intent.ACTION_TIMEZONE_CHANGED)
}

internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T,
): Flow<T> = flow {
    emit(factory.invoke())
    emitAll(broadcastReceiverFlow(temporalEventFilter).map { factory.invoke() })
}.conflate()

public actual fun <T> temporalFlow(
    factory: () -> T,
): Flow<T> = flow {
    emit(factory.invoke())
    emitAll(broadcastReceiverFlow(temporalEventFilter).map { factory.invoke() })
}.conflate()
