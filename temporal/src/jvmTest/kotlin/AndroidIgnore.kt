package com.juul.tuulbox.temporal

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
actual annotation class AndroidIgnore(
    actual val value: String,
)
