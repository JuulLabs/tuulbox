package com.juul.tuulbox.encoding

import kotlin.test.Test
import kotlin.test.assertEquals

/** See: https://www.rfc-editor.org/rfc/rfc4648#section-10 */
private val testVectors = listOf(
    "" to "",
    "f" to "Zg==",
    "fo" to "Zm8=",
    "foo" to "Zm9v",
    "foob" to "Zm9vYg==",
    "fooba" to "Zm9vYmE=",
    "foobar" to "Zm9vYmFy",
)

private val omittedPaddingTestVectors = listOf(
    "f" to "Zg",
    "fo" to "Zm8",
    "foob" to "Zm9vYg",
    "fooba" to "Zm9vYmE",
)

class Base64Tests {

    @Test
    fun decodeBase64_forRfcTestVectors_matchesRfc() {
        for ((expected, base64) in testVectors) {
            assertEquals(expected, base64.decodeBase64().decodeToString())
        }
    }

    @Test
    fun decodeBase64_withOmittedPadding_isRecoverable() {
        for ((expected, base64) in omittedPaddingTestVectors) {
            assertEquals(expected, base64.decodeBase64().decodeToString())
        }
    }

    @Test
    fun decodeBase64Sequence_forRfcTestVectors_matchesRfc() {
        for ((expected, base64) in testVectors) {
            assertEquals(expected, base64.decodeBase64Sequence().toList().toByteArray().decodeToString())
        }
    }

    @Test
    fun decodeBase64Sequence_withOmittedPadding_isRecoverable() {
        for ((expected, base64) in omittedPaddingTestVectors) {
            assertEquals(expected, base64.decodeBase64Sequence().toList().toByteArray().decodeToString())
        }
    }

    @Test
    fun encodeBase64_forRfcTestVectors_matchesRfc() {
        for ((input, expected) in testVectors) {
            println(input)
            assertEquals(expected, input.encodeToByteArray().encodeBase64())
        }
    }

    @Test
    fun encodeBase64_byteArrayWithMsbSet_encodesWithoutException() {
        // gzip of the string '["a","b","c"]'
        val data = byteArrayOf(31, -117, 8, 0, 0, 0, 0, 0, 0, 0, -117, 86, 74, 84, -46, 81, 74, 2, -30, 100, -91, 88, 0, -17, 71, -25, -80, 13, 0, 0, 0)
        assertEquals(
            expected = "H4sIAAAAAAAAAItWSlTSUUoC4mSlWADvR+ewDQAAAA==",
            actual = data.encodeBase64(),
        )
    }
}
