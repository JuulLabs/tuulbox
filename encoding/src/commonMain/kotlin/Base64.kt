package com.juul.tuulbox.encoding

/**
 * Decode a single character to an integer between `0` and `63` (6 bits).
 *
 * Padding characters decode to `0`, and illegal characters throw an exception.
 */
private fun Char.decode(): Int = when (this) {
    in 'A'..'Z' -> this - 'A'
    in 'a'..'z' -> this - 'a' + 26
    in '0'..'9' -> this - '0' + 52
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

/** Encodes an integer between `0` and `63` (6 bits) to a single base 64 character. */
private fun Int.encode(): Char = when (this) {
    in 0..25 -> 'A' + this
    in 26..51 -> 'a' + this - 26
    in 52..61 -> '0' + this - 52
    62 -> '+'
    63 -> '/'
    else -> error("Cannot encode more than 6 bits. Received `${this.toString(radix = 2)}`.")
}

public fun ByteArray.encodeBase64(): String {
    // TODO: On JVM only, we can supply a `capacity` as an optimization.
    val builder = StringBuilder()
    var index = 0
    var buffer = 0
    var bufferBits = 0
    while (index < size) {
        if (bufferBits < 6) {
            buffer = (buffer shl 8) or get(index).toInt()
            index += 1
            bufferBits += 8
        }
        while (bufferBits >= 6) {
            val remainingBits = bufferBits - 6
            builder.append((buffer shr remainingBits).encode())
            buffer = when (val wipeBits = 32 - remainingBits) {
                32 -> 0
                else -> (buffer shl wipeBits) ushr wipeBits
            }
            bufferBits = remainingBits
        }
    }
    if (bufferBits != 0) {
        builder.append((buffer shl (6 - bufferBits)).encode())
    }
    when (size % 3) {
        1 -> builder.append("==")
        2 -> builder.append("=")
    }
    return builder.toString()
}
