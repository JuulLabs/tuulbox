package com.juul.tuulbox.collections

/**
 * Convert a map to a Plain Old JavaScript Object by transforming the keys to strings and the values
 * to any of the JavaScript types.
 * See https://developer.mozilla.org/en-US/docs/Web/JavaScript/Data_structures#javascript_types
 *
 * @param transform called on each entry to effect a mapping to a JavaScript Object. This function
 *                  must convert the key to a string as Javascript Object keys are always strings.
 *                  The value may be converted to an appropriate type as required.
 */
public fun <K, V> Map<K, V>.toJsObject(
    transform: (Map.Entry<K, V>) -> Pair<String, dynamic>
): dynamic {
    val pojo = js("({})")
    for (entry in this) {
        val (key, value) = transform(entry)
        pojo[key] = value
    }
    return pojo
}
