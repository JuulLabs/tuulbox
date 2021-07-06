package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T
): Flow<T> = ticker(Duration.minutes(1), factory)

public actual fun <T> temporalFlow(
    factory: () -> T
): Flow<T> = ticker(Duration.minutes(1), factory)
