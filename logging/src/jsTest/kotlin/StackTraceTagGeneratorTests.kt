package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals

class StackTraceTagGeneratorTests {

    @Test
    fun tagMatchesClassName() {
        val generatedTag = StackTraceTagGenerator.getTag()
        assertEquals("StackTraceTagGeneratorTests", generatedTag)
    }
}
