package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.minutes

internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T,
): Flow<T> = ticker(1.minutes, factory)

public actual fun <T> temporalFlow(
    factory: () -> T,
): Flow<T> = ticker(1.minutes, factory)
