package com.juul.tuulbox.logging

import kotlin.RequiresOptIn.Level.ERROR
import kotlin.annotation.AnnotationRetention.BINARY

/**
 * Marks a class as internal to Tuulbox. These APIs are left `public` so they may be used by other
 * modules, but should not be used outside (by consumers) of Tuulbox.
 */
@MustBeDocumented
@Retention(value = BINARY)
@RequiresOptIn(level = ERROR)
public annotation class TuulboxInternal
