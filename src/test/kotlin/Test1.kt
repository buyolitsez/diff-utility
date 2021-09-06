import kotlin.test.*

internal class Test1 {
    @Test
    fun test1() {
        assert(true)
    }
}

internal class TestLCS {
    @Test
    fun testGroup1() {
        assertContentEquals(arrayOf("a", "b"), LCS(arrayOf("a", "b"), arrayOf("a", "b")))
        assertContentEquals(arrayOf("b", "a"), LCS(arrayOf("a", "b", "a"), arrayOf("b", "a")))
        assertContentEquals(arrayOf("a"), LCS(arrayOf("a", "b"), arrayOf("ba", "a")))
        assertContentEquals(arrayOf(), LCS(arrayOf("aqwe", "bzx"), arrayOf("abasd", "bdqwd")))
    }

    @Test
    fun testGroup2() {
        assertContentEquals(arrayOf("a", "c", "a"), LCS(arrayOf("a", "b", "c", "a"), arrayOf("a", "c", "e", "a")))
        assertContentEquals(arrayOf("e"), LCS(arrayOf("b", "e"), arrayOf("f", "f", "g", "e")))
        assertContentEquals(arrayOf("aqwe", "sadasd"), LCS(arrayOf("aqwe", "bzx", "sadasd", "bzx"), arrayOf("bzx", "bzy", "aqwe", "bdqwd", "sadasd")))
        assertContentEquals(arrayOf("0", "2", "56"), LCS(arrayOf("0", "1", "2", "3", "4", "56"), arrayOf("0", "2", "56", "1", "1", "5", "7")))
    }

    @Test
    fun testGroup3() {
        assertContentEquals(arrayOf("X", "M", "J", "A", "Z"), LCS(arrayOf("X", "M", "J", "Y", "A", "U", "Z"), arrayOf("X", "M", "J", "A", "A", "T", "Z")))
        assertContentEquals(arrayOf(), LCS(arrayOf("x", "m", "J", "Y", "A", "U", "Z"), arrayOf("X", "M", "j", "a", "a", "t", "z")))
        assertContentEquals(arrayOf("-1"), LCS(arrayOf("1", "-1"), arrayOf("-1", "0")))
        assertContentEquals(arrayOf("ANcb-%3q23"), LCS(arrayOf("ANcb-%3q23"), arrayOf("ANcb-%3q23")))
    }

    @Test
    fun testGroup4() {
        assertContentEquals(arrayOf(), LCS(arrayOf(), arrayOf("abc", "asd")))
        assertContentEquals(arrayOf(), LCS(arrayOf("1", "2"), arrayOf()))
        assertContentEquals(arrayOf(), LCS(arrayOf(), arrayOf()))
    }
}
