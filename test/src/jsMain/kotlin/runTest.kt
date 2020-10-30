package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

public actual fun runTest(
    action: suspend (scope: CoroutineScope) -> Unit
): dynamic = GlobalScope.promise { action(this) }
