package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

@Deprecated("Use official Coroutines `runTest`")
public actual fun runTest(
    action: suspend CoroutineScope.() -> Unit
): dynamic = GlobalScope.promise { action.invoke(this) }
