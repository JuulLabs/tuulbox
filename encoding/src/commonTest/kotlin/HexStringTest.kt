package com.juul.tuulbox.encoding

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

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

    @Test
    fun parseHex() {
        assertEquals(
            expected = byteArrayOf(1, 2, 3).asList(),
            actual = "010203".parseHex().asList(),
        )
    }

    @Test
    fun parseHex_lowercaseHexCharacters() {
        assertEquals(
            expected = byteArrayOf(-54, -2).asList(),
            actual = "cafe".parseHex().asList(),
        )
    }

    @Test
    fun parseHex_uppercaseHexCharacters() {
        assertEquals(
            expected = byteArrayOf(-54, -2).asList(),
            actual = "CAFE".parseHex().asList(),
        )
    }

    @Test
    fun parseHex_mixedCaseHexCharacters() {
        assertEquals(
            expected = byteArrayOf(-54, -2).asList(),
            actual = "CaFe".parseHex().asList(),
        )
    }

    @Test
    fun parseHex_containsNonHexCharacter_throwsIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            "hello".parseHex()
        }
    }

    @Test
    fun parseHex_incorrectLength_throwsIllegalArgumentException() {
        assertFailsWith<IllegalArgumentException> {
            "0102030".parseHex()
        }
    }
}
