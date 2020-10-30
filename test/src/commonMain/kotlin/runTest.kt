package com.juul.tuulbox.test

import kotlinx.coroutines.CoroutineScope

// Adapted from:
// https://github.com/Kotlin/kotlinx.coroutines/issues/885#issuecomment-446586161
public expect fun runTest(
    action: suspend (scope: CoroutineScope) -> Unit
)
