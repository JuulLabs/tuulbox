package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T
): Flow<T> = ticker(Duration.minutes(1), factory)

@ExperimentalTime
public actual fun <T> temporalFlow(
    factory: () -> T
): Flow<T> = ticker(Duration.minutes(1), factory)
