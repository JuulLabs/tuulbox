package com.juul.tuulbox.functional

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <O> (() -> O).memoize(): () -> O {
    val value by lazy { this() }
    return { value }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, O> ((I1) -> O).memoize(): (I1) -> O {
    val cache = mutableMapOf<I1, O>()
    return { i1 -> cache.getOrPut(i1) { this(i1) } }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, I2, O> ((I1, I2) -> O).memoize(): (I1, I2) -> O {
    val cache = mutableMapOf<Pair<I1, I2>, O>()
    return { i1, i2 -> cache.getOrPut(Pair(i1, i2)) { this(i1, i2) } }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, I2, I3, O> ((I1, I2, I3) -> O).memoize(): (I1, I2, I3) -> O {
    val cache = mutableMapOf<Triple<I1, I2, I3>, O>()
    return { i1, i2, i3 -> cache.getOrPut(Triple(i1, i2, i3)) { this(i1, i2, i3) } }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, I2, I3, I4, O> ((I1, I2, I3, I4) -> O).memoize(): (I1, I2, I3, I4) -> O {
    // TODO: This is slightly less efficient than a Tuple4 data class.
    val cache = mutableMapOf<List<Any?>, O>()
    return { i1, i2, i3, i4 -> cache.getOrPut(listOf(i1, i2, i3, i4)) { this(i1, i2, i3, i4) } }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, I2, I3, I4, I5, O> ((I1, I2, I3, I4, I5) -> O).memoize(): (I1, I2, I3, I4, I5) -> O {
    // TODO: This is slightly less efficient than a Tuple5 data class.
    val cache = mutableMapOf<List<Any?>, O>()
    return { i1, i2, i3, i4, i5 -> cache.getOrPut(listOf(i1, i2, i3, i4, i5)) { this(i1, i2, i3, i4, i5) } }
}

/** Returns a locally-memoized version of this function. Two separate invocations of [memoize] will have two separate caches. */
public fun <I1, I2, I3, I4, I5, I6, O> ((I1, I2, I3, I4, I5, I6) -> O).memoize(): (I1, I2, I3, I4, I5, I6) -> O {
    // TODO: This is slightly less efficient than a Tuple6 data class.
    val cache = mutableMapOf<List<Any?>, O>()
    return { i1, i2, i3, i4, i5, i6 -> cache.getOrPut(listOf(i1, i2, i3, i4, i5, i6)) { this(i1, i2, i3, i4, i5, i6) } }
}
