import kotlin.test.*

internal class TestIgnoreCaseOption {
    @BeforeTest
    fun TurnOnOption() = run {
        OPTIONS["ignore-case"] = true
        OPTIONS["no-color"] = true
    }

    @AfterTest
    fun TurnOffOption() = run {
        OPTIONS["ignore-case"] = false
        OPTIONS["no-color"] = false
    }

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
        listOf(
            "2d1",
            "< mv",
            "3a3",
            "> diff"
        ),
        getDiffResult(
            arrayOf("dog", "mv", "CP", "comm"),
            arrayOf("DOG", "cp", "diff", "comm")
        )
    )

    @Test
    fun testExamples2() = assertContentEquals(
        listOf(
            "0a1",
            "> Kubuntu",
            "1a3",
            "> Debian",
            "3d4",
            "< Debian"
        ),
        getDiffResult(
            arrayOf("UbuNtu", "Arch LinUX", "Debian", "CentOS", "FEDORA"),
            arrayOf("Kubuntu", "UbuntU", "Debian", "ARCH Linux", "Centos", "Fedora")
        ),
    )

    @Test
    fun testExamples3() = assertContentEquals(
        listOf(
            "0a1,2",
            "> e",
            "> d",
            "2c4",
            "< A",
            "---",
            "> c",
            "4,5d5",
            "< E",
            "< a"
        ),
        getDiffResult(
            arrayOf("c", "A", "e", "E", "a"),
            arrayOf("e", "d", "C", "c", "E")
        )
    )
}