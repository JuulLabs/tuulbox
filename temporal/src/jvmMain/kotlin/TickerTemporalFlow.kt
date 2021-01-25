package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlin.time.minutes

public actual inline fun <T> temporalFlow(
    crossinline factory: () -> T
): Flow<T> = ticker(1.minutes, factory)
