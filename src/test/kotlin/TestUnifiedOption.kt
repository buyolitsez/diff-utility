import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.*

internal class TestUnifiedOption {
    private val standardOut = System.out
    private val standardIn = System.`in`
    private val stream = ByteArrayOutputStream()

    @BeforeTest
    fun turnOnOption() = run {
        OPTIONS["unified"] = true
        OPTIONS["no-color"] = true
        OPTIONS["no-info"] = true
    }

    @AfterTest
    fun turnOffOption() = run {
        OPTIONS["unified"] = false
        OPTIONS["no-color"] = false
        OPTIONS["no-info"] = false
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
    fun testOneChange() {
        printDiffResult(arrayOf("dog", "mv", "CP", "comm"), arrayOf("DOG", "cp", "diff", "comm"))
        assertEquals(
            "@@ -1,4 +1,4 @@\n" +
                    "-dog\n" +
                    "-mv\n" +
                    "-CP\n" +
                    "+DOG\n" +
                    "+cp\n" +
                    "+diff\n" +
                    " comm\n",
            stream.toString()
        )
    }

    @Test
    fun testOneAdd() {
        printDiffResult(arrayOf("a", "c"), arrayOf("a", "b", "c"))
        assertEquals(
            "@@ -1,2 +1,3 @@\n" +
                    " a\n" +
                    "+b\n" +
                    " c\n",
            stream.toString()
        )
    }

    @Test
    fun testOneDelete() {
        printDiffResult(arrayOf("a", "b", "c"), arrayOf("a", "c"))
        assertEquals(
            "@@ -1,3 +1,2 @@\n" +
                    " a\n" +
                    "-b\n" +
                    " c\n",
            stream.toString()
        )
    }

    @Test
    fun testAddChange() {
        printDiffResult(arrayOf("Ubuntu", "Arch Linux", "Debian", "CentOS", "Fedora"),
            arrayOf("Kubuntu", "Ubuntu", "Debian", "Arch Linux", "Centos", "Fedora"))
        assertEquals(
            "@@ -1,5 +1,6 @@\n" +
                    "+Kubuntu\n" +
                    " Ubuntu\n" +
                    "+Debian\n" +
                    " Arch Linux\n" +
                    "-Debian\n" +
                    "-CentOS\n" +
                    "+Centos\n" +
                    " Fedora\n",
            stream.toString()
        )
    }

    @Test
    fun testN2AddChangeDelete() {
        UNIFIED_N = 2
        printDiffResult(arrayOf("c", "a", "e", "d", "a"),
            arrayOf("e", "d", "c", "c", "e"))
        assertEquals(
            "@@ -1,5 +1,5 @@\n" +
                    "+e\n" +
                    "+d\n" +
                    " c\n" +
                    "-a\n" +
                    "+c\n" +
                    " e\n" +
                    "-d\n" +
                    "-a\n",
            stream.toString()
        )
    }

    @Test
    fun testN2DeleteChangeAddAdd() {
        UNIFIED_N = 2
        printDiffResult(arrayOf("a", "b", "c", "b", "b", "a", "c", "c", "c", "a"),
            arrayOf("b", "c", "c", "a", "c", "c", "c", "c", "a", "c"))
        assertEquals(
            "@@ -1,7 +1,5 @@\n" +
                    "-a\n" +
                    " b\n" +
                    " c\n" +
                    "-b\n" +
                    "-b\n" +
                    "+c\n" +
                    " a\n" +
                    " c\n" +
                    "@@ -8,3 +6,5 @@\n" +
                    " c\n" +
                    " c\n" +
                    "+c\n" +
                    " a\n" +
                    "+c\n",
            stream.toString()
        )
    }
}