import kotlin.test.*
import SingleOperation.*


internal class TestCompressToPairs {
    @Test
    fun test1() {
        assertContentEquals(listOf(), compressToCommands(listOf()))
        assertContentEquals(listOf(Command(2, REMOVE), Command(3, ADD), Command(2, REMOVE)), compressToCommands(listOf(REMOVE, REMOVE, ADD, ADD, ADD, REMOVE, REMOVE)))
        assertContentEquals(listOf(Command(1, ADD), Command(2, REMOVE), Command(1, ADD), Command(1, REMOVE), Command(1, ADD)),
            compressToCommands(listOf(ADD, REMOVE, REMOVE, ADD, REMOVE, ADD)))
    }
}