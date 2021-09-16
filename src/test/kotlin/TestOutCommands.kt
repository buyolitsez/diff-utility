import kotlin.test.*


internal class TestOutCommands {
    @Test
    fun testAllEmpty() = assertContentEquals(
        listOf(),
        outCommands(
            arrayOf(),
            arrayOf()
        )
    )

    @Test
    fun testOneChange() = assertContentEquals(
        listOf("1,3,c,1,3"),
        outCommands(
            arrayOf("dog", "mv", "CP", "comm"),
            arrayOf("DOG", "cp", "diff", "comm")
        )
    )

    @Test
    fun testOneAdd() = assertContentEquals(
        listOf("1,1,a,2,2"),
        outCommands(
            arrayOf("a", "c"),
            arrayOf("a", "b", "c")
        )
    )

    @Test
    fun testOneDelete() = assertContentEquals(
        listOf("2,2,d,1,1"),
        outCommands(
            arrayOf("a", "b", "c"),
            arrayOf("a", "c")
        )
    )

    @Test
    fun testAddChange() = assertContentEquals(
        listOf("0,0,a,1,1", "1,1,a,3,3", "3,4,c,5,5"),
        outCommands(
            arrayOf("Ubuntu", "Arch Linux", "Debian", "CentOS", "Fedora"),
            arrayOf("Kubuntu", "Ubuntu", "Debian", "Arch Linux", "Centos", "Fedora")
        ),
    )

    @Test
    fun testAddChangeDelete() = assertContentEquals(
        listOf("0,0,a,1,2", "2,2,c,4,4", "4,5,d,5,5"),
        outCommands(
            arrayOf("c", "a", "e", "d", "a"),
            arrayOf("e", "d", "c", "c", "e")
        )
    )

    @Test
    fun testDeleteChangeAddAdd() = assertContentEquals(
        listOf("1,1,d,0,0", "4,5,c,3,3", "9,9,a,8,8", "10,10,a,10,10"),
        outCommands(
            arrayOf("a", "b", "c", "b", "b", "a", "c", "c", "c", "a"),
            arrayOf("b", "c", "c", "a", "c", "c", "c", "c", "a", "c")
        )
    )

}
