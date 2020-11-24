package com.juul.tuulbox.functional

/** Returns a function that repeats the application of [this] [count] times. */
public fun <T> ((T) -> T).repeat(count: Int): (T) -> T {
    require(count >= 0) { "`times` must not be negative" }
    return { initialValue ->
        var value = initialValue
        kotlin.repeat(count) {
            value = this(value)
        }
        value
    }
}
