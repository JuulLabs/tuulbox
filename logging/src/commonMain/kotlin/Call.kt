package com.juul.tuulbox.logging

@TuulboxInternal
public data class Call(
    val level: LogLevel,
    val tag: String,
    val message: String,
    val throwable: Throwable?,
    val metadata: ReadMetadata,
)
