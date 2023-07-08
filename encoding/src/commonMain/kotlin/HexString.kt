package com.juul.tuulbox.encoding

/**
 * Provides basic [ByteArray] to hex [String] conversion. Modified version of kotlinx.serialization's `HexConverter`:
 * https://github.com/Kotlin/kotlinx.serialization/blob/43d5f7841fc744b072a636b712e194081456b5ba/formats/cbor/commonTest/src/kotlinx/serialization/HexConverter.kt
 */
@Deprecated(
    message = """Use Kotlin stdlib HexFormat support (available since 1.9.0), e.g. this.toHexString(HexFormat { bytes.byteSeparator = " "; bytes.bytePrefix = "0x"; upperCase = false } )""",
    replaceWith = ReplaceWith(
        expression = "this.toHexString(HexFormat.Default)",
        imports = [
            "kotlin.text.toHexString",
            "kotlin.text.HexFormat",
        ],
    ),
)
public fun ByteArray.toHexString(
    separator: String? = null,
    prefix: String? = null,
    lowerCase: Boolean = false,
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

/**
 * Provides basic hex [CharSequence] to [ByteArray] conversion. Modified version of kotlinx.serialization's `HexConverter`:
 * https://github.com/Kotlin/kotlinx.serialization/blob/43d5f7841fc744b072a636b712e194081456b5ba/formats/cbor/commonTest/src/kotlinx/serialization/HexConverter.kt
 */
@Deprecated(
    message = "Use Kotlin stdlib HexFormat support (available since 1.9.0).",
    replaceWith = ReplaceWith(
        expression = "this.hexToByteArray(HexFormat.Default)",
        imports = [
            "kotlin.text.hexToByteArray",
            "kotlin.text.HexFormat",
        ],
    ),
)
public fun CharSequence.parseHex(): ByteArray {
    if (isEmpty()) return byteArrayOf()
    require(length % 2 == 0) { "Hex sequence must contain an even number of characters" }
    val bytes = ByteArray(length / 2)
    for (i in 0 until length step 2) {
        val h = hexToInt(this[i])
        val l = hexToInt(this[i + 1])
        require(!(h == -1 || l == -1)) { "Invalid hex characters '${this[i]}${this[i + 1]}' at $i" }
        bytes[i / 2] = ((h shl 4) + l).toByte()
    }
    return bytes
}

private fun hexToInt(ch: Char): Int = when (ch) {
    in '0'..'9' -> ch - '0'
    in 'A'..'F' -> ch - 'A' + 10
    in 'a'..'f' -> ch - 'a' + 10
    else -> -1
}
