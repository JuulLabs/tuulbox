package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@ExperimentalTime
internal actual inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T,
): Flow<T> = ticker(1.minutes, factory)

@ExperimentalTime
public actual fun <T> temporalFlow(
    factory: () -> T,
): Flow<T> = ticker(1.minutes, factory)
