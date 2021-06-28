package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T
): Flow<T> = ticker(1.minutes, factory)

@ExperimentalTime
public actual fun <T> temporalFlow(
    factory: () -> T
): Flow<T> = ticker(1.minutes, factory)
