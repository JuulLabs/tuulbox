package com.juul.tuulbox.encoding

private val UPPERCASE_LETTERS = 'A'..'Z'
private val LOWERCASE_LETTERS = 'a'..'z'
private val NUMBERS = '0'..'9'

/**
 * Decode a single character to an integer between `0` and `63` (6 bits).
 *
 * Padding characters decode to `0`, and illegal characters throw an exception.
 */
private fun Char.decode(): Int = when (this) {
    in UPPERCASE_LETTERS -> this - 'A'
    in LOWERCASE_LETTERS -> this - 'a' + 26
    in NUMBERS -> this - '0' + 52
    '+' -> 62
    '/' -> 63
    '=' -> 0
    else -> error("Illegal base64 character: `$this`.")
}

/**
 * Decodes a quartet of characters to a triplet of bytes, packed into the lower 24 bits of an integer.
 *
 * Note that the padding character `=` is packed as `000000`, and must be handled by the calling function.
 */
private fun CharSequence.decodeQuartet(index: Int): Int =
    (get(index).decode() shl 18) or (get(index + 1).decode() shl 12) or (get(index + 2).decode() shl 6) or (get(index + 3).decode())

/** Decodes a base64 character sequence to bytes. Decoding is done eagerly. */
public fun CharSequence.decodeBase64(): ByteArray {
    require(length % 4 == 0) { "Base64 strings must be an exact multiple of 4 characters long." }
    val numBytes = when {
        isEmpty() -> return byteArrayOf()
        endsWith("==") -> (length / 4 * 3) - 2
        endsWith('=') -> (length / 4 * 3) - 1
        else -> length / 4 * 3
    }

    val byteBuffer = ByteArray(numBytes)
    var index = 0
    while (index < length) {
        val buffer = decodeQuartet(index)
        val byteIndex = (index / 4) * 3
        byteBuffer[byteIndex] = (buffer shr 16).toByte()
        if (get(index + 2) != '=') {
            byteBuffer[byteIndex + 1] = (buffer shr 8).toByte()
            if (get(index + 3) != '=') {
                byteBuffer[byteIndex + 2] = buffer.toByte()
            }
        }
        index += 4
    }
    return byteBuffer
}

/** Decodes a base64 character sequence to bytes. Decoding is done lazily. */
public fun CharSequence.decodeBase64Sequence(): Sequence<Byte> = sequence {
    require(length % 4 == 0) { "Base64 strings must be an exact multiple of 4 characters long." }
    var index = 0
    while (index < length) {
        val buffer = decodeQuartet(index)
        yield((buffer shr 16).toByte())
        if (get(index + 2) != '=') {
            yield((buffer shr 8).toByte())
            if (get(index + 3) != '=') {
                yield(buffer.toByte())
            }
        }
        index += 4
    }
}
