package com.juul.tuulbox.collections

import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.test.assertEquals

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
