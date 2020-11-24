package com.juul.tuulbox.functional

/** Returns a function where the argument order has been reversed. */
public fun <I1, I2, O> ((I1, I2) -> O).reverse(): (I2, I1) -> O =
    { i2, i1 -> this(i1, i2) }

/** Returns a function where the argument order has been reversed. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).reverse(): (I3, I2, I1) -> O =
    { i3, i2, i1 -> this(i1, i2, i3) }

/** Returns a function where the argument order has been reversed. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).reverse(): (I4, I3, I2, I1) -> O =
    { i4, i3, i2, i1 -> this(i1, i2, i3, i4) }

/** Returns a function where the argument order has been reversed. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).reverse(): (I5, I4, I3, I2, I1) -> O =
    { i5, i4, i3, i2, i1 -> this(i1, i2, i3, i4, i5) }

/** Returns a function where the argument order has been reversed. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).reverse(): (I6, I5, I4, I3, I2, I1) -> O =
    { i6, i5, i4, i3, i2, i1 -> this(i1, i2, i3, i4, i5, i6) }
