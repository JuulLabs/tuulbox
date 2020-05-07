package com.juul.tuulbox.collections.test

import kotlin.test.assertEquals
import kotlinx.coroutines.channels.ReceiveChannel

suspend fun <E> ReceiveChannel<E>.receiveAndAssert(
    expected: E,
    message: String? = null
) {
    assertEquals(
        message = message,
        expected = expected,
        actual = receive()
    )
}
