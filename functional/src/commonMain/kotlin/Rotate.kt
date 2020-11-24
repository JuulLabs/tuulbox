package com.juul.tuulbox.functional

/** Returns a function where the last argument has been moved to before the first argument. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).rotate(): (I3, I1, I2) -> O =
    { i3, i1, i2 -> this(i1, i2, i3) }

/** Returns a function where the last argument has been moved to before the first argument. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).rotate(): (I4, I1, I2, I3) -> O =
    { i4, i1, i2, i3 -> this(i1, i2, i3, i4) }

/** Returns a function where the last argument has been moved to before the first argument. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).rotate(): (I5, I1, I2, I3, I4) -> O =
    { i5, i1, i2, i3, i4 -> this(i1, i2, i3, i4, i5) }

/** Returns a function where the last argument has been moved to before the first argument. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).rotate(): (I6, I1, I2, I3, I4, I5) -> O =
    { i6, i1, i2, i3, i4, i5 -> this(i1, i2, i3, i4, i5, i6) }

/** Returns a function where the first argument has been moved to after the last argument. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).rotateBack(): (I2, I3, I1) -> O =
    { i2, i3, i1 -> this(i1, i2, i3) }

/** Returns a function where the first argument has been moved to after the last argument. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).rotateBack(): (I2, I3, I4, I1) -> O =
    { i2, i3, i4, i1 -> this(i1, i2, i3, i4) }

/** Returns a function where the first argument has been moved to after the last argument. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).rotateBack(): (I2, I3, I4, I5, I1) -> O =
    { i2, i3, i4, i5, i1 -> this(i1, i2, i3, i4, i5) }

/** Returns a function where the first argument has been moved to after the last argument. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).rotateBack(): (I2, I3, I4, I5, I6, I1) -> O =
    { i2, i3, i4, i5, i6, i1 -> this(i1, i2, i3, i4, i5, i6) }
