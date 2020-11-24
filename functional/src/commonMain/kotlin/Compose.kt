package com.juul.tuulbox.functional

/** Compose two functions. [this] is called first, and then [second] is called with the result. */
public fun <I1, I2, O> ((I1) -> I2).then(second: (I2) -> O): (I1) -> O =
    { i1 -> second(this(i1)) }

/** Compose two functions. [first] is called first, and then [this] is called with the result. */
public fun <I1, I2, O> ((I2) -> O).of(first: (I1) -> I2): (I1) -> O =
    { i1 -> this(first(i1)) }
