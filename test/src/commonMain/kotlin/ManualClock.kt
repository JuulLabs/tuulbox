package com.juul.tuulbox.test

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.Instant

public class ManualClock(
    private var time: Instant,
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
