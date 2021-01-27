package com.juul.tuulbox.temporal

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
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
public fun instantFlow(): Flow<Instant> = inlineTemporalFlow(::createInstant).distinctUntilChanged()

/**
 * Creates a [temporalFlow] of [LocalDateTime]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun localDateTimeFlow(): Flow<LocalDateTime> = inlineTemporalFlow(::createLocalDateTime).distinctUntilChanged()

/**
 * Creates a [temporalFlow] of [LocalDate]s. Frequency of updates is platform dependent, but
 * should not be more infrequent than approximately once a minute.
 */
public fun localDateFlow(): Flow<LocalDate> = inlineTemporalFlow(::createLocalDate).distinctUntilChanged()
