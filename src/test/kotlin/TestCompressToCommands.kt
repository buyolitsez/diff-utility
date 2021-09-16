import kotlin.test.*
import SingleOperation.*


internal class TestCompressToCommands {
    @Test
    fun testAllEmpty() = assertContentEquals(
        listOf(),
        compressToCommands(listOf())
    )

    @Test
    fun testOneAddCommand() = assertContentEquals(
        listOf(Command(1, ADD)),
        compressToCommands(listOf(ADD))
    )

    @Test
    fun testExamples1() = assertContentEquals(
        listOf(Command(2, REMOVE), Command(3, ADD), Command(2, REMOVE)),
        compressToCommands(listOf(REMOVE, REMOVE, ADD, ADD, ADD, REMOVE, REMOVE))
    )

    @Test
    fun testExamples2() = assertContentEquals(
        listOf(Command(1, ADD), Command(2, REMOVE), Command(1, ADD), Command(1, REMOVE), Command(1, ADD)),
        compressToCommands(listOf(ADD, REMOVE, REMOVE, ADD, REMOVE, ADD))
    )
}