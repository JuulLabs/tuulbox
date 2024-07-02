package com.juul.tuulbox.temporal

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
expect annotation class AndroidIgnore(
    val value: String,
)
