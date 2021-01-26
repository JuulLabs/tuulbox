package com.juul.tuulbox.test

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

/** Asserts that [value] is an element in [array]. */
public fun <T> assertContains(array: Array<T>, value: T) {
    assertTrue(value in array, "Value `$value` not in `$array`.")
}

/** Asserts that [value] is within [range]. */
public fun <T : Comparable<T>> assertContains(range: ClosedRange<T>, value: T) {
    assertTrue(value in range, "Value `$value` not in range `$range`.")
}

/** Asserts that [value] is an element in [collection]. */
public fun <T> assertContains(collection: Collection<T>, value: T) {
    assertTrue(value in collection, "Value `$value` not in `$collection`.")
}

/** Asserts that [value] is an element in [iterable]. */
public fun <T> assertContains(iterable: Iterable<T>, value: T) {
    assertTrue(value in iterable, "Value `$value` not in `$iterable`.")
}

/** Asserts that [value] is an element in [sequence]. */
public fun <T> assertContains(sequence: Sequence<T>, value: T) {
    assertTrue(value in sequence, "Value `$value` not in `$sequence`.")
}

/** Asserts that [value] is equal to [target], plus or minus some [epsilon]. */
public fun assertSimilar(target: Int, epsilon: Int, value: Int) {
    assertContains((target - epsilon..target + epsilon) as ClosedRange<Int>, value)
}

/** Asserts that [value] is equal to [target], plus or minus some [epsilon]. */
public fun assertSimilar(target: Long, epsilon: Long, value: Long) {
    assertContains((target - epsilon..target + epsilon) as ClosedRange<Long>, value)
}

/** Asserts that [value] is equal to [target], plus or minus some [epsilon]. */
public fun assertSimilar(target: Float, epsilon: Float, value: Float) {
    assertContains(target - epsilon..target + epsilon, value)
}

/** Asserts that [value] is equal to [target], plus or minus some [epsilon]. */
public fun assertSimilar(target: Double, epsilon: Double, value: Double) {
    assertContains(target - epsilon..target + epsilon, value)
}

/** Asserts that [value] is equal to [target], plus or minus some [epsilon]. */
@ExperimentalTime
public fun assertSimilar(target: Instant, epsilon: Duration, value: Instant) {
    assertContains(target - epsilon..target + epsilon, value)
}

/**
 * Asserts that [value] is equal to [target], plus or minus some [epsilon]. If an explicit
 * [timeZone] is not specified, the [system default][TimeZone.currentSystemDefault] is used.
 */
@ExperimentalTime
public fun assertSimilar(
    target: LocalDateTime,
    epsilon: Duration,
    value: LocalDateTime,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
) {
    assertSimilar(target.toInstant(timeZone), epsilon, value.toInstant(timeZone))
}
