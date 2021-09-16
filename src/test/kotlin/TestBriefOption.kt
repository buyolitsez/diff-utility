import java.io.ByteArrayOutputStream
import kotlin.test.*
import java.io.PrintStream
import kotlin.test.BeforeTest
import kotlin.test.AfterTest

internal class TestBriefOption {
    private val standardOut = System.out
    private val standardIn = System.`in`
    private val stream = ByteArrayOutputStream()

    @BeforeTest
    fun TurnOnOption() = run {
        OPTIONS["brief"] = true
        OPTIONS["no-color"] = true
    }

    @AfterTest
    fun TurnOffOption() = run {
        OPTIONS["brief"] = false
        OPTIONS["no-color"] = false
    }

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
        assertEquals("Files src/files/text1.txt and src/files/text2.txt differ", stream.toString().trim())
    }

    @Test
    fun testDifferent() {
        printDiffResult(arrayOf("a", "b", "c"), arrayOf("a", "c", "b"))
        assertEquals("Files src/files/text1.txt and src/files/text2.txt differ", stream.toString().trim())
    }

    @Test
    fun testEmptyInput() {
        printDiffResult(arrayOf(), arrayOf())
        assertEquals("", stream.toString().trim())
    }

    @Test
    fun testEqualInput() {
        printDiffResult(arrayOf("a", "b", "c"), arrayOf("a", "b", "c"))
        assertEquals("", stream.toString().trim())
    }
}