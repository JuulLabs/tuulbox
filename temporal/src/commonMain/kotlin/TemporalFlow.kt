package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow

/**
 * Creates a flow that updates on temporal events. Frequency of updates is platform
 * dependent, but should not be more infrequent than approximately once a minute.
 *
 * The flow returned by this is [conflated][kotlinx.coroutines.flow.conflate].
 *
 * Prefer this over [temporalFlow] when providing pre-built temporal flows, as this
 * should be slightly more efficient due to inlining.
 */
internal expect inline fun <T> inlineTemporalFlow(
    crossinline factory: () -> T,
): Flow<T>

/**
 * Creates a flow that updates on temporal events. Frequency of updates is platform
 * dependent, but should not be more infrequent than approximately once a minute.
 *
 * The flow returned by this is [conflated][kotlinx.coroutines.flow.conflate].
 */
public expect fun <T> temporalFlow(
    factory: () -> T,
): Flow<T>
