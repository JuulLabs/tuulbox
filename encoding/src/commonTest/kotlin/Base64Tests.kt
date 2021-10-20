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
    "foobar" to "Zm9vYmFy"
)

class Base64Tests {

    @Test
    fun decodeBase64_forRfcTestVectors_matchesRfc() {
        for ((expected, base64) in testVectors) {
            assertEquals(expected, base64.decodeBase64().decodeToString())
        }
    }

    @Test
    fun decodeBase64Sequence_forRfcTestVectors_matchesRfc() {
        for ((expected, base64) in testVectors) {
            assertEquals(expected, base64.decodeBase64Sequence().toList().toByteArray().decodeToString())
        }
    }
}
