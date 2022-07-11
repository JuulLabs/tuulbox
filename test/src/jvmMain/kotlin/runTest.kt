package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

@Deprecated("Use official Coroutines `runTest`")
public actual fun runTest(
    action: suspend CoroutineScope.() -> Unit,
): Unit = runBlocking { action.invoke(this) }
