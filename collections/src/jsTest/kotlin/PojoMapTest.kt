import com.juul.tuulbox.collections.toJsObject
import kotlin.test.Test
import kotlin.test.assertEquals

class PojoMapTest {

    @Test
    fun toJsObject_whenEmpty_isEmpty() {
        val pojo = emptyMap<String, Int>().toJsObject { it.key to it.value }
        val keys = js("Object.keys(pojo)") as Array<String>
        assertEquals(
            expected = 0,
            actual = keys.size,
        )
    }

    @Test
    fun toJsObject_withMultipleEntries_hasKeys() {
        val pojo = mapOf(
            "fortyTwo" to 42,
            "sixtyNine" to 69,
        ).toJsObject { it.key to it.value }
        val keys = js("Object.keys(pojo)") as Array<String>
        assertEquals(
            expected = listOf("fortyTwo", "sixtyNine"),
            actual = keys.toList(),
        )
    }

    @Test
    fun toJsObject_withMultipleEntries_hasValues() {
        val pojo = mapOf(
            "fortyTwo" to 42,
            "sixtyNine" to 69,
        ).toJsObject { it.key to it.value }
        val values = js("Object.values(pojo)") as Array<Int>
        assertEquals(
            expected = listOf(42, 69),
            actual = values.toList(),
        )
    }

    @Test
    fun toJsObject_withStringNumberPair_stringifiesToSimpleJson() {
        val pojo = mapOf(
            "fortyTwo" to 42,
        ).toJsObject { it.key to it.value }
        val json = js("JSON.stringify(pojo)") as String
        assertEquals(
            expected = "{\"fortyTwo\":42}",
            actual = json,
        )
    }

    @Test
    fun toJsObject_withNumberStringPair_stringifiesToSimpleJson() {
        val pojo = mapOf(
            42 to "fortyTwo",
        ).toJsObject { it.key.toString() to it.value }
        val json = js("JSON.stringify(pojo)") as String
        assertEquals(
            expected = "{\"42\":\"fortyTwo\"}",
            actual = json,
        )
    }

    @Test
    fun toJsObject_withArrayValue_stringifiesToSimpleJson() {
        val pojo = mapOf(
            "data" to listOf(1, 2, 3),
        ).toJsObject { it.key to it.value.toTypedArray() }
        val json = js("JSON.stringify(pojo)") as String
        assertEquals(
            expected = "{\"data\":[1,2,3]}",
            actual = json,
        )
    }
}
