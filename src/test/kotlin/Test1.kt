import kotlin.test.*

internal class TestLCS {
    @Test
    fun testGroup1() {
        assertContentEquals(arrayOf("a", "b"), lcs(arrayOf("a", "b"), arrayOf("a", "b")))
        assertContentEquals(arrayOf("b", "a"), lcs(arrayOf("a", "b", "a"), arrayOf("b", "a")))
        assertContentEquals(arrayOf("a"), lcs(arrayOf("a", "b"), arrayOf("ba", "a")))
        assertContentEquals(arrayOf(), lcs(arrayOf("aqwe", "bzx"), arrayOf("abasd", "bdqwd")))
    }

    @Test
    fun testGroup2() {
        assertContentEquals(arrayOf("a", "c", "a"), lcs(arrayOf("a", "b", "c", "a"), arrayOf("a", "c", "e", "a")))
        assertContentEquals(arrayOf("e"), lcs(arrayOf("b", "e"), arrayOf("f", "f", "g", "e")))
        assertContentEquals(arrayOf("aqwe", "sadasd"), lcs(arrayOf("aqwe", "bzx", "sadasd", "bzx"), arrayOf("bzx", "bzy", "aqwe", "bdqwd", "sadasd")))
        assertContentEquals(arrayOf("0", "2", "56"), lcs(arrayOf("0", "1", "2", "3", "4", "56"), arrayOf("0", "2", "56", "1", "1", "5", "7")))
    }

    @Test
    fun testGroup3() {
        assertContentEquals(arrayOf("X", "M", "J", "A", "Z"), lcs(arrayOf("X", "M", "J", "Y", "A", "U", "Z"), arrayOf("X", "M", "J", "A", "A", "T", "Z")))
        assertContentEquals(arrayOf(), lcs(arrayOf("x", "m", "J", "Y", "A", "U", "Z"), arrayOf("X", "M", "j", "a", "a", "t", "z")))
        assertContentEquals(arrayOf("-1"), lcs(arrayOf("1", "-1"), arrayOf("-1", "0")))
        assertContentEquals(arrayOf("ANcb-%3q23"), lcs(arrayOf("ANcb-%3q23"), arrayOf("ANcb-%3q23")))
    }

    @Test
    fun testGroup4() {
        assertContentEquals(arrayOf(), lcs(arrayOf(), arrayOf("abc", "asd")))
        assertContentEquals(arrayOf(), lcs(arrayOf("1", "2"), arrayOf()))
        assertContentEquals(arrayOf(), lcs(arrayOf(), arrayOf()))
    }
}

internal class TestGetCommands {
    @Test
    fun testGroup1() {
        assertContentEquals(listOf('-', '+', '=', '-', '=', '=', '-', '=', '+'), getCommands(arrayOf("A", "B", "C", "A", "B", "B", "A"), arrayOf("C", "B", "A", "B", "A", "C")))
        assertContentEquals(listOf('=', '=', '=', '=', '+', '=', '=', '-', '+', '=', '-', '+', '+', '+', '+', '='), getCommands(arrayOf("a", "b", "c", "d", "f", "g", "h", "j", "q", "z"), arrayOf("a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "r", "x", "y", "z")))
        assertContentEquals(listOf('=', '=', '=', '=', '+', '=', '=', '-', '+', '=', '-', '+', '+', '+', '+', '='), getCommands(arrayOf("a", "b", "c", "d", "f", "g", "h", "j", "q", "z"), arrayOf("a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "r", "x", "y", "z")))
        assertContentEquals(listOf('=', '=', '=', '=', '='), getCommands(arrayOf("a", "b", "c", "d", "e"), arrayOf("a", "b", "c", "d", "e")))
    }

    @Test
    fun testGroup2() {
        assertContentEquals(listOf('+', '=', '-', '-', '+', '=', '-', '+'), getCommands(arrayOf("Gujarat", "Uttar Pradesh", "Kolkata", "Bihar", "Jammu and Kashmir"), arrayOf("Tamil Nadu", "Gujarat", "Andhra Pradesh", "Bihar", "Uttar pradesh")))
        assertContentEquals(listOf('-', '=', '+'), getCommands(arrayOf("a", "b"), arrayOf("b", "c")))
    }
}

internal class TestCompressToPairs {
    @Test
    fun test1() {
        assertContentEquals(listOf(), compressToPairs(listOf()))
        assertContentEquals(listOf(Command(2, '-'), Command(3, '+'), Command(2, '-')), compressToPairs(listOf('-', '-', '+', '+', '+', '-', '-')))
        assertContentEquals(listOf(Command(1, '+'), Command(2, '-'), Command(1, '+'), Command(1, '-'), Command(1, '+')),
            compressToPairs(listOf('+', '-', '-', '+', '-', '+')))
    }
}

internal class TestDiff {
    @Test
    fun test1() {
        assertContentEquals(listOf("1,3c1,3", "< dog", "< mv", "< CP", "---", "> DOG", "> cp", "> diff"),
            outputResultOfDiff(arrayOf("dog", "mv", "CP", "comm"),
                               arrayOf("DOG", "cp", "diff", "comm")))
    }

    @Test
    fun test2() {
        assertContentEquals(listOf("0a1", "> Kubuntu", "1a3", "> Debian", "3,4c5", "< Debian", "< CentOS", "---", "> Centos"),
            outputResultOfDiff(arrayOf("Ubuntu", "Arch Linux", "Debian", "CentOS", "Fedora"),
                               arrayOf("Kubuntu", "Ubuntu", "Debian", "Arch Linux", "Centos", "Fedora")),
        )
    }

    @Test
    fun test3() {
        assertContentEquals(listOf("0a1,2", "> e", "> d", "2c4", "< a", "---", "> c", "4,5d5", "< d", "< a"),
        outputResultOfDiff(arrayOf("c", "a", "e", "d", "a"), arrayOf("e", "d", "c", "c", "e")))
    }
}
