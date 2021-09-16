import java.io.ByteArrayOutputStream
import kotlin.test.*
import java.io.PrintStream
import kotlin.test.BeforeTest
import kotlin.test.AfterTest

internal class TestReportIdenticalFilesOption {
    private val standardOut = System.out
    private val standardIn = System.`in`
    private val stream = ByteArrayOutputStream()

    @BeforeTest
    fun TurnOnOption() = run { OPTIONS["report-identical-files"] = true }

    @AfterTest
    fun TurnOffOption() = run { OPTIONS["report-identical-files"] = false}

    @BeforeTest
    fun setUp() {
        System.setOut(PrintStream(stream))
    }

    @AfterTest
    fun tearDown() {
        System.setOut(standardOut)
        System.setIn(standardIn)
    }

    @Test
    fun testAbsoluteDifferent() {
        printDiffResult(arrayOf("a", "b"), arrayOf("c, d"))
        assertEquals("1,2c1\n" +
                "< a\n" +
                "< b\n" +
                "---\n" +
                "> c, d", stream.toString().trim())
    }

    @Test
    fun testDifferent() {
        printDiffResult(arrayOf("a", "b", "c"), arrayOf("a", "c", "b"))
        assertEquals("1a2\n" +
                "> c\n" +
                "3d3\n" +
                "< c", stream.toString().trim())
    }

    @Test
    fun testEmptyInput() {
        printDiffResult(arrayOf(), arrayOf())
        assertEquals("Files src/files/text1.txt and src/files/text2.txt are identical", stream.toString().trim())
    }

    @Test
    fun testEqualInput() {
        printDiffResult(arrayOf("a", "b", "c"), arrayOf("a", "b", "c"))
        assertEquals("Files src/files/text1.txt and src/files/text2.txt are identical", stream.toString().trim())
    }
}