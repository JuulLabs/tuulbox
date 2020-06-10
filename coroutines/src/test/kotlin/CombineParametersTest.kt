import com.juul.tuulbox.coroutines.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class CombineParametersTest {

    @Test
    fun testSixParameters() = runBlocking {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6")) { a, b, c, d, e, f ->
            a + b + c + d + e + f
        }
        assertEquals("1234null6", flow.single())
    }

    @Test
    fun testSevenParameters() = runBlocking {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7)) { a, b, c, d, e, f, g ->
            a + b + c + d + e + f + g
        }
        assertEquals("1234null67", flow.single())
    }

    @Test
    fun testEightParameters() = runBlocking {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8")) { a, b, c, d, e, f, g, h ->
            a + b + c + d + e + f + g + h
        }
        assertEquals("1234null678", flow.single())
    }

    @Test
    fun testNineParameters() = runBlocking {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8"),
            flowOf(9.toByte())) { a, b, c, d, e, f, g, h, i ->
            a + b + c + d + e + f + g + h + i
        }
        assertEquals("1234null6789", flow.single())
    }

    @Test
    fun testTenParameters() = runBlocking {
        val flow = combine(
            flowOf("1"),
            flowOf(2),
            flowOf("3"),
            flowOf(4.toByte()),
            flowOf(null),
            flowOf("6"),
            flowOf(7),
            flowOf("8"),
            flowOf(9.toByte()),
            flowOf(null)) { a, b, c, d, e, f, g, h, i, j ->
            a + b + c + d + e + f + g + h + i + j
        }
        assertEquals("1234null6789null", flow.single())
    }
}
