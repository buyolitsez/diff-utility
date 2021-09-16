import kotlin.test.*
import SingleOperation.*

internal class TestGetCommands {
    @Test
    fun testAllEmpty() = assertContentEquals(
        listOf(),
        getCommands(
            arrayOf(),
            arrayOf()
        )
    )

    @Test
    fun testEqualSuffix() = assertContentEquals(
        listOf(REMOVE, KEEP, ADD),
        getCommands(
            arrayOf("a", "b"),
            arrayOf("b", "c")
        )
    )

    @Test
    fun testEqualStrings() = assertContentEquals(
        listOf(KEEP, KEEP, KEEP, KEEP, KEEP),
        getCommands(
            arrayOf("a", "b", "c", "d", "e"),
            arrayOf("a", "b", "c", "d", "e")
        )
    )

    @Test
    fun testExamples1() = assertContentEquals(
        listOf(REMOVE, ADD, KEEP, REMOVE, KEEP, KEEP, REMOVE, KEEP, ADD),
        getCommands(
            arrayOf("A", "B", "C", "A", "B", "B", "A"),
            arrayOf("C", "B", "A", "B", "A", "C")
        )
    )

    @Test
    fun testExamples2() = assertContentEquals(
        listOf(KEEP, KEEP, KEEP, KEEP, ADD, KEEP, KEEP, REMOVE, ADD, KEEP, REMOVE, ADD, ADD, ADD, ADD, KEEP),
        getCommands(
            arrayOf("a", "b", "c", "d", "f", "g", "h", "j", "q", "z"),
            arrayOf("a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "r", "x", "y", "z")
        )
    )

    @Test
    fun testExampeles3() = assertContentEquals(
        listOf(KEEP, KEEP, KEEP, KEEP, ADD, KEEP, KEEP, REMOVE, ADD, KEEP, REMOVE, ADD, ADD, ADD, ADD, KEEP),
        getCommands(
            arrayOf("a", "b", "c", "d", "f", "g", "h", "j", "q", "z"),
            arrayOf("a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "r", "x", "y", "z")
        )
    )

    @Test
    fun testExamples4() = assertContentEquals(
        listOf(ADD, KEEP, REMOVE, REMOVE, ADD, KEEP, REMOVE, ADD),
        getCommands(
            arrayOf("Gujarat", "Uttar Pradesh", "Kolkata", "Bihar", "Jammu and Kashmir"),
            arrayOf("Tamil Nadu", "Gujarat", "Andhra Pradesh", "Bihar", "Uttar pradesh")
        )
    )
}