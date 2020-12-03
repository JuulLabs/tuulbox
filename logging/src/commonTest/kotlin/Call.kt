package com.juul.tuulbox.logging

data class Call(
    val tag: String,
    val message: String,
    val throwable: Throwable?
)
