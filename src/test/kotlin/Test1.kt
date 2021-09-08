import kotlin.test.*

internal class testGetCommands {
    @Test
    fun testGroup1() {
        assertContentEquals(listOf(-1, 1, -3, -6, 6), getCommands(arrayOf("A", "B", "C", "A", "B", "B", "A"), arrayOf("C", "B", "A", "B", "A", "C")))
        assertContentEquals(listOf(5, -7, 8, -9, 10, 11, 12, 13), getCommands(arrayOf("a", "b", "c", "d", "f", "g", "h", "j", "q", "z"), arrayOf("a", "b", "c", "d", "e", "f", "g", "i", "j", "k", "r", "x", "y", "z")))
        assertContentEquals(listOf(), getCommands(arrayOf("a", "b", "c", "d", "e"), arrayOf("a", "b", "c", "d", "e")))
    }

    @Test
    fun testGroup2() {
        assertContentEquals(listOf(1, -2, -3, 3, -5, 5), getCommands(arrayOf("Gujarat", "Uttar Pradesh", "Kolkata", "Bihar", "Jammu and Kashmir"), arrayOf("Tamil Nadu", "Gujarat", "Andhra Pradesh", "Bihar", "Uttar pradesh")))
    }
}

internal class testLCS {
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
