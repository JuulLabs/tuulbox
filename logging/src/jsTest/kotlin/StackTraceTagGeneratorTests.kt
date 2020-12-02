package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals

class StackTraceTagGeneratorTests {

    @Test
    fun tagMatchesClassName() {
        val actual = StackTraceTagGenerator.getTag()
        assertEquals("StackTraceTagGeneratorTests", actual)
    }
}
