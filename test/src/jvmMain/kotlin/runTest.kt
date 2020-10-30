package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

public actual fun runTest(
    action: suspend (scope: CoroutineScope) -> Unit
): Unit = runBlocking { action(this) }
