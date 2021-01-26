package com.juul.tuulbox.temporal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * Creates a flow that updates on temporal events. Frequency of updates is platform
 * dependent, but should not be more infrequent than approximately once a minute.
 *
 * The flow returned by this is [conflated][kotlinx.coroutines.flow.conflate].
 */
public expect inline fun <T> temporalFlow(
    crossinline factory: () -> T
): Flow<T>

/**
 * Creates a state flow that updates on temporal events. The initial value is the current
 * time. Frequency of updates is platform dependent, but should not be more infrequent than
 * approximately once a minute.
 */
public inline fun <T> temporalFlow(
    scope: CoroutineScope,
    sharingStarted: SharingStarted,
    crossinline factory: () -> T
): StateFlow<T> = temporalFlow(factory)
    .stateIn(scope, sharingStarted, factory.invoke())
