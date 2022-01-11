package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope

/**
 * Deprecated in favor of [official support provided by Coroutines library](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/kotlinx.coroutines.test/run-test.html).
 *
 * Originally adapted from: https://github.com/Kotlin/kotlinx.coroutines/issues/885#issuecomment-446586161
 */
@Deprecated("Use official Coroutines `runTest`")
public expect fun runTest(
    action: suspend CoroutineScope.() -> Unit
)
