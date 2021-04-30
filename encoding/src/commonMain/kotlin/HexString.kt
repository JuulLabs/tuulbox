package com.juul.tuulbox.encoding

private const val hexCode = "0123456789ABCDEF"

/**
 * Provides basic [ByteArray] to hex [String] conversion. Modified version of kotlinx.serialization's `HexConverter`:
 * https://github.com/Kotlin/kotlinx.serialization/blob/43d5f7841fc744b072a636b712e194081456b5ba/formats/cbor/commonTest/src/kotlinx/serialization/HexConverter.kt
 *
 * For more robust (and actively maintained) solutions we may consider [Okio](https://github.com/square/okio) or
 * [Krypto](https://github.com/korlibs/krypto).
 */
public fun ByteArray.toHexString(
    separator: String? = " ",
    prefix: String? = null,
    lowerCase: Boolean = false
): String {
    val r = StringBuilder(size * 2)
    for (b in this) {
        if (separator != null && r.isNotEmpty()) r.append(separator)
        if (prefix != null) r.append(prefix)
        r.append(hexCode[b.toInt() shr 4 and 0xF])
        r.append(hexCode[b.toInt() and 0xF])
    }
    return if (lowerCase) r.toString().toLowerCase() else r.toString()
}
