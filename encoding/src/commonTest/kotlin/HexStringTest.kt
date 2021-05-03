package com.juul.tuulbox.encoding

import kotlin.test.Test
import kotlin.test.assertEquals

class HexStringTest {

    private val testBytes = byteArrayOf(1, 32, 160.toByte(), 255.toByte())

    @Test
    fun toHexString_uppercaseDefault() {
        assertEquals(
            expected = "0120A0FF",
            actual = testBytes.toHexString()
        )
    }

    @Test
    fun toHexString_lowercase() {
        assertEquals(
            expected = "0120a0ff",
            actual = testBytes.toHexString(lowerCase = true)
        )
    }

    @Test
    fun toHexString_withCustomSeparator() {
        assertEquals(
            expected = "01, 20, A0, FF",
            actual = testBytes.toHexString(separator = ", ")
        )
    }

    @Test
    fun toHexString_withCustomSeparatorAndPrefix() {
        assertEquals(
            expected = "0x01 0x20 0xA0 0xFF",
            actual = testBytes.toHexString(prefix = "0x", separator = " ")
        )
    }

    @Test
    fun toHexString_withCustomSeparatorAndPrefixLowercase() {
        assertEquals(
            expected = "0x01, 0x20, 0xa0, 0xff",
            actual = testBytes.toHexString(separator = ", ", prefix = "0x", lowerCase = true)
        )
    }
}
