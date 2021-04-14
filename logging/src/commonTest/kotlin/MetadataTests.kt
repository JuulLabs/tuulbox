package com.juul.tuulbox.logging

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame

class MetadataTests {

    @Test
    fun copy_isShallow() {
        val expected = Any()
        val metadata = Metadata().also {
            it[AnyKey] = expected
        }
        val actual = metadata.copy()[AnyKey]
        assertSame(expected, actual)
    }

    @Test
    fun get_respectsKeys() {
        val metadata = Metadata().also {
            it[StringKey] = "test"
            it[BooleanKey] = false
        }
        assertEquals("test", metadata[StringKey])
        assertEquals(false, metadata[BooleanKey])
    }

    @Test
    fun get_withNoSet_returnsNull() {
        val metadata = Metadata()
        assertNull(metadata[StringKey])
    }

    @Test
    fun get_afterReset_returnsNull() {
        val metadata = Metadata().also {
            it[StringKey] = "test"
        }
        metadata.clear()
        assertNull(metadata[StringKey])
    }
}
