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
    fun testExamples1() = assertContentEquals(
        listOf("1,3c1,3", "< dog", "< mv", "< CP", "---", "> DOG", "> cp", "> diff"),
        getDiffResult(
            arrayOf("dog", "mv", "CP", "comm"),
            arrayOf("DOG", "cp", "diff", "comm")
        )
    )

    @Test
    fun testExamples2() = assertContentEquals(
        listOf("0a1", "> Kubuntu", "1a3", "> Debian", "3,4c5", "< Debian", "< CentOS", "---", "> Centos"),
        getDiffResult(
            arrayOf("Ubuntu", "Arch Linux", "Debian", "CentOS", "Fedora"),
            arrayOf("Kubuntu", "Ubuntu", "Debian", "Arch Linux", "Centos", "Fedora")
        ),
    )

    @Test
    fun testExamples3() = assertContentEquals(
        listOf("0a1,2", "> e", "> d", "2c4", "< a", "---", "> c", "4,5d5", "< d", "< a"),
        getDiffResult(
            arrayOf("c", "a", "e", "d", "a"),
            arrayOf("e", "d", "c", "c", "e")
        )
    )

}
