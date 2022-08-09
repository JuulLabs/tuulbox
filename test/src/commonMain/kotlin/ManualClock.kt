package com.juul.tuulbox.test

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

private val defaultTime: Instant = LocalDateTime(
    year = 2022,
    monthNumber = 3,
    dayOfMonth = 8,
    hour = 8,
    minute = 15,
).toInstant(TimeZone.UTC)

public class ManualClock(
    private var time: Instant = defaultTime
) : Clock {

    public constructor(time: LocalDateTime, timeZone: TimeZone) : this(time.toInstant(timeZone))

    override fun now(): Instant = time

    public fun set(to: Instant) {
        time = to
    }

    public fun set(to: LocalDateTime, timeZone: TimeZone) {
        time = to.toInstant(timeZone)
    }
}
