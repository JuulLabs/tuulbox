package com.juul.tuulbox.logging

@TuulboxInternal
@Deprecated("Tuulbox Logging has been moved to a separate khronicle library.")
public data class Call(
    val level: LogLevel,
    val tag: String,
    val message: String,
    val throwable: Throwable?,
    val metadata: ReadMetadata,
)
