package com.juul.tuulbox.temporal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/** Create an instance of [Instant] for the current time on the system clock. */
private fun createInstant() = Clock.System.now()

/** Create an instance of [LocalDateTime] for the current time on the system clock. */
private fun createLocalDateTime() = createInstant().toLocalDateTime(TimeZone.currentSystemDefault())

/** Create an instance of [LocalDate] for the current time on the system clock. */
private fun createLocalDate() = createLocalDateTime().date

/**
 * Creates a [temporalFlow] of [Instant]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun instantFlow(): Flow<Instant> = temporalFlow(::createInstant)

/**
 * Creates a [temporalFlow] of [Instant]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun instantFlow(
    scope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<Instant> = temporalFlow(scope, sharingStarted, ::createInstant)

/**
 * Creates a [temporalFlow] of [LocalDateTime]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun localDateTimeFlow(): Flow<LocalDateTime> = temporalFlow(::createLocalDateTime)

/**
 * Creates a [temporalFlow] of [LocalDateTime]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun localDateTimeFlow(
    scope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<LocalDateTime> = temporalFlow(scope, sharingStarted, ::createLocalDateTime)

/**
 * Creates a [temporalFlow] of [LocalDate]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute. The same date will not be
 * emitted twice if no change occurred.
 */
public fun localDateFlow(): Flow<LocalDate> = temporalFlow(::createLocalDate)

/**
 * Creates a [temporalFlow] of [LocalDate]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute. The same date will not be
 * emitted twice if no change occurred.
 */
public fun localDateFlow(
    scope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<LocalDate> = temporalFlow(scope, sharingStarted, ::createLocalDate)
