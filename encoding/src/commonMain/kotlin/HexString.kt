package com.juul.tuulbox.encoding

/**
 * Provides basic [ByteArray] to hex [String] conversion. Modified version of kotlinx.serialization's `HexConverter`:
 * https://github.com/Kotlin/kotlinx.serialization/blob/43d5f7841fc744b072a636b712e194081456b5ba/formats/cbor/commonTest/src/kotlinx/serialization/HexConverter.kt
 */
public fun ByteArray.toHexString(
    separator: String? = null,
    prefix: String? = null,
    lowerCase: Boolean = false
): String {
    if (size == 0) return ""
    val hexCode = if (lowerCase) "0123456789abcdef" else "0123456789ABCDEF"
    val capacity = size * (2 + (prefix?.length ?: 0)) + (size - 1) * (separator?.length ?: 0)
    val r = StringBuilder(capacity)
    for (b in this) {
        if (separator != null && r.isNotEmpty()) r.append(separator)
        if (prefix != null) r.append(prefix)
        r.append(hexCode[b.toInt() shr 4 and 0xF])
        r.append(hexCode[b.toInt() and 0xF])
    }
    return r.toString()
}
