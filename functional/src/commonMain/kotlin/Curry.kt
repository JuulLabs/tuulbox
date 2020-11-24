package com.juul.tuulbox.functional

/** Curry a two-argument function. */
public fun <I1, I2, O> ((I1, I2) -> O).curry(): (I1) -> (I2) -> O =
    { i1 -> { i2 -> this(i1, i2) } }

/** Curry a three-argument function. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).curry(): (I1) -> (I2) -> (I3) -> O =
    { i1 -> { i2 -> { i3 -> this(i1, i2, i3) } } }

/** Curry a four-argument function. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).curry(): (I1) -> (I2) -> (I3) -> (I4) -> O =
    { i1 -> { i2 -> { i3 -> { i4 -> this(i1, i2, i3, i4) } } } }

/** Curry a five-argument function. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).curry(): (I1) -> (I2) -> (I3) -> (I4) -> (I5) -> O =
    { i1 -> { i2 -> { i3 -> { i4 -> { i5 -> this(i1, i2, i3, i4, i5) } } } } }

/** Curry a six-argument function. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).curry(): (I1) -> (I2) -> (I3) -> (I4) -> (I5) -> (I6) -> O =
    { i1 -> { i2 -> { i3 -> { i4 -> { i5 -> { i6 -> this(i1, i2, i3, i4, i5, i6) } } } } } }

/** Curry the first argument of a three-argument function. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).curryFirst(): (I1) -> (I2, I3) -> O =
    { i1 -> { i2, i3 -> this(i1, i2, i3) } }

/** Curry the first argument of a four-argument function. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).curryFirst(): (I1) -> (I2, I3, I4) -> O =
    { i1 -> { i2, i3, i4 -> this(i1, i2, i3, i4) } }

/** Curry the first argument of a five-argument function. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).curryFirst(): (I1) -> (I2, I3, I4, I5) -> O =
    { i1 -> { i2, i3, i4, i5 -> this(i1, i2, i3, i4, i5) } }

/** Curry the first argument of a six-argument function. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).curryFirst(): (I1) -> (I2, I3, I4, I5, I6) -> O =
    { i1 -> { i2, i3, i4, i5, i6 -> this(i1, i2, i3, i4, i5, i6) } }
