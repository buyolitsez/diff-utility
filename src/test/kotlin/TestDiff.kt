import kotlin.test.*


internal class TestDiff {
    @Test
    fun testAllEmpty() = assertContentEquals(
        listOf(),
        getDiffResult(
            arrayOf(),
            arrayOf()
        )
    )

    @Test
    fun testOneChange() = assertContentEquals(
        listOf("1,3c1,3", "< dog", "< mv", "< CP", "---", "> DOG", "> cp", "> diff"),
        getDiffResult(
            arrayOf("dog", "mv", "CP", "comm"),
            arrayOf("DOG", "cp", "diff", "comm")
        )
    )

    @Test
    fun testOneAdd() = assertContentEquals(
        listOf("1a2", "> b"),
        getDiffResult(
            arrayOf("a", "c"),
            arrayOf("a", "b", "c")
        )
    )

    @Test
    fun testOneDelete() = assertContentEquals(
        listOf("2d1", "< b"),
        getDiffResult(
            arrayOf("a", "b", "c"),
            arrayOf("a", "c")
        )
    )

    @Test
    fun testAddChange() = assertContentEquals(
        listOf("0a1", "> Kubuntu", "1a3", "> Debian", "3,4c5", "< Debian", "< CentOS", "---", "> Centos"),
        getDiffResult(
            arrayOf("Ubuntu", "Arch Linux", "Debian", "CentOS", "Fedora"),
            arrayOf("Kubuntu", "Ubuntu", "Debian", "Arch Linux", "Centos", "Fedora")
        ),
    )

    @Test
    fun testAddChangeDelete() = assertContentEquals(
        listOf("0a1,2", "> e", "> d", "2c4", "< a", "---", "> c", "4,5d5", "< d", "< a"),
        getDiffResult(
            arrayOf("c", "a", "e", "d", "a"),
            arrayOf("e", "d", "c", "c", "e")
        )
    )

    @Test
    fun testDeleteChangeAddAdd() = assertContentEquals(
        listOf("1d0", "< a", "4,5c3", "< b", "< b", "---", "> c", "9a8", "> c", "10a10", "> c"),
        getDiffResult(
            arrayOf("a", "b", "c", "b", "b", "a", "c", "c", "c", "a"),
            arrayOf("b", "c", "c", "a", "c", "c", "c", "c", "a", "c")
        )
    )

    @Test
    fun testBigText() = assertContentEquals(
        readText("files/smallTest15/myDiff.txt").toList(),
        getDiffResult(
            readText("files/smallTest15/smallText1.txt"),
            readText("files/smallTest15/smallText2.txt")
        )
    )

}
