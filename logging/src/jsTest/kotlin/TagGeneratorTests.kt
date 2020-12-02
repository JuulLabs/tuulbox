package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals

class TagGeneratorTests {

    @Test
    fun tagMatchesClassName() {
        val actual = TagGenerator.getTag()
        assertEquals("TagGeneratorTests", actual)
    }
}
