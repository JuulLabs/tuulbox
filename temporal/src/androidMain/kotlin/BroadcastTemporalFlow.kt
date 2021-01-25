package com.juul.tuulbox.temporal

import android.content.Intent
import android.content.IntentFilter
import com.juul.tuulbox.coroutines.flow.broadcastReceiverFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@PublishedApi
internal val temporalEventFilter = IntentFilter().apply {
    addAction(Intent.ACTION_DATE_CHANGED)
    addAction(Intent.ACTION_TIME_CHANGED)
    addAction(Intent.ACTION_TIME_TICK)
    addAction(Intent.ACTION_TIMEZONE_CHANGED)
}

public actual inline fun <T> temporalFlow(
    crossinline factory: () -> T
): Flow<T> = broadcastReceiverFlow(temporalEventFilter)
    .map { factory.invoke() }
