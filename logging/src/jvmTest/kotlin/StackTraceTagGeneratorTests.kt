package com.juul.tuulbox.logging

import java.util.function.Supplier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StackTraceTagGeneratorTests {

    @Test
    fun tagMatchesClassName() {
        val generatedTag = StackTraceTagGenerator.getTag()
        assertEquals(StackTraceTagGeneratorTests::class.java.simpleName, generatedTag)
    }

    @Test
    fun tagStripsAnonymousClassNumber() {
        val supplier = Supplier<String> { StackTraceTagGenerator.getTag() }

        // Double check that the way we've written this actually generates
        // an anonymous class, instead of being optimized by the compiler.
        assertTrue(supplier::class.java.name.endsWith("$1"))

        val expected = supplier::class.java.name
            .substringAfterLast('.')
            .substringBefore("$1")
        assertEquals(expected, supplier.get())
    }

    @Test
    fun tagIgnoreClassesMarkedWithHide() {
        class HiddenClass : HideFromStackTraceTag {
            fun getTag() = StackTraceTagGenerator.getTag()
        }
        assertEquals(StackTraceTagGeneratorTests::class.java.simpleName, HiddenClass().getTag())
    }
}
