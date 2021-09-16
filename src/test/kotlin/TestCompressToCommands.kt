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
    fun testOneRemoveCommand() = assertContentEquals(
        listOf(Command(1, REMOVE)),
        compressToCommands(listOf(REMOVE))
    )

    @Test
    fun testOneKeepCommand() = assertContentEquals(
        listOf(Command(1, KEEP)),
        compressToCommands(listOf(KEEP))
    )

    @Test
    fun testEachCommandOnce() = assertContentEquals(
        listOf(Command(1, ADD), Command(1, KEEP), Command(1, REMOVE)),
        compressToCommands(listOf(ADD, KEEP, REMOVE))
    )

    @Test
    fun testRemoveAddRemove() = assertContentEquals(
        listOf(Command(2, REMOVE), Command(3, ADD), Command(2, REMOVE)),
        compressToCommands(listOf(REMOVE, REMOVE, ADD, ADD, ADD, REMOVE, REMOVE))
    )

    @Test
    fun testOnlyRemoveAndAdd() = assertContentEquals(
        listOf(Command(1, ADD), Command(2, REMOVE), Command(1, ADD), Command(1, REMOVE), Command(1, ADD)),
        compressToCommands(listOf(ADD, REMOVE, REMOVE, ADD, REMOVE, ADD))
    )
}