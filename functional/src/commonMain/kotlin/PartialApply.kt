package com.juul.tuulbox.functional

/** Binds the argument of this function with a constant [value]. */
public fun <I1, O> ((I1) -> O).partialApply(value: I1): () -> O =
    { this(value) }

/** Binds the first argument of this function with a constant [value]. */
public fun <I1, I2, O> ((I1, I2) -> O).partialApply(value: I1): (I2) -> O =
    { i2 -> this(value, i2) }

/** Binds the first argument of this function with a constant [value]. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).partialApply(value: I1): (I2, I3) -> O =
    { i2, i3 -> this(value, i2, i3) }

/** Binds the first argument of this function with a constant [value]. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).partialApply(value: I1): (I2, I3, I4) -> O =
    { i2, i3, i4 -> this(value, i2, i3, i4) }

/** Binds the first argument of this function with a constant [value]. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).partialApply(value: I1): (I2, I3, I4, I5) -> O =
    { i2, i3, i4, i5 -> this(value, i2, i3, i4, i5) }

/** Binds the first argument of this function with a constant [value]. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).partialApply(value: I1): (I2, I3, I4, I5, I6) -> O =
    { i2, i3, i4, i5, i6 -> this(value, i2, i3, i4, i5, i6) }
