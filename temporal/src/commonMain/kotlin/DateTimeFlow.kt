package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Creates a [temporalFlow] of [Instant]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun instantFlow(): Flow<Instant> =
    temporalFlow { Clock.System.now() }

/**
 * Creates a [temporalFlow] of [LocalDateTime]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun localDateTimeFlow(): Flow<LocalDateTime> =
    instantFlow().map { it.toLocalDateTime(TimeZone.currentSystemDefault()) }

/**
 * Creates a [temporalFlow] of [LocalDate]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute. The same date will not be
 * emitted twice if no change occurred.
 */
public fun localDateFlow(): Flow<LocalDate> =
    localDateTimeFlow().map { it.date }
        .distinctUntilChanged()
