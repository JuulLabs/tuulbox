package com.juul.tuulbox.logging

internal class Metadata : ReadMetadata, WriteMetadata {
    private val storedData = mutableMapOf<Key<*>, Any>()

    @Suppress("UNCHECKED_CAST")
    public override operator fun <T : Any> get(key: Key<T>): T? =
        storedData[key] as? T

    public override operator fun <T : Any> set(key: Key<T>, value: T) {
        storedData[key] = value
    }

    public override fun copy(): Metadata = Metadata().also { copy ->
        copy.storedData += this.storedData
    }

    internal fun clear() {
        storedData.clear()
    }

    override fun equals(other: Any?): Boolean = other is Metadata && storedData == other.storedData

    override fun hashCode(): Int = storedData.hashCode()

    override fun toString(): String = storedData.toString()
}

/**
 * Additional data associated with a log. It's important that [Logger] instances do NOT hold onto references
 * to [ReadMetadata] arguments after the function returns. If a [ReadMetadata] reference must be kept after
 * function return, create a [copy].
 */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("ReadMetadata", "com.juul.khronicle.ReadMetadata"),
)
public interface ReadMetadata {
    public operator fun <T : Any> get(key: Key<T>): T?

    public fun copy(): ReadMetadata
}

/**
 * Additional data associated with a log. It's important that [Log] calls do NOT hold onto references
 * to [WriteMetadata] arguments after the lambda returns.
 */
@Deprecated(
    message = "Tuulbox Logging has been moved to a separate khronicle library.",
    ReplaceWith("WriteMetadata", "com.juul.khronicle.WriteMetadata"),
)
public interface WriteMetadata {
    public operator fun <T : Any> set(key: Key<T>, value: T)
}
