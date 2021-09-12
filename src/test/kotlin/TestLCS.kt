import kotlin.test.*

internal class TestLCS {
    @Test
    fun testAllEmpty() = assertContentEquals(arrayOf(),
        lcs(arrayOf(),
            arrayOf()))

    @Test
    fun testDifferentCases() = assertContentEquals(arrayOf(),
        lcs(arrayOf("x", "m", "J", "Y", "A", "U", "Z"),
            arrayOf("X", "M", "j", "a", "a", "t", "z")))

    @Test
    fun testRandom1() = assertContentEquals(arrayOf(),
                                        lcs(arrayOf("aqwe", "bzx"),
                                            arrayOf("abasd", "bdqwd")))

    @Test
    fun testRandom2() = assertContentEquals(arrayOf("a", "b"),
                                        lcs(arrayOf("a", "b"),
                                            arrayOf("a", "b")))

    @Test
    fun testRandom3() = assertContentEquals(arrayOf("b", "a"),
                                        lcs(arrayOf("a", "b", "a"),
                                            arrayOf("b", "a")))

    @Test
    fun testRandom4() = assertContentEquals(arrayOf("a"),
                                        lcs(arrayOf("a", "b"),
                                            arrayOf("ba", "a")))


    @Test
    fun testRandom5() = assertContentEquals(arrayOf("0", "2", "56"),
                                        lcs(arrayOf("0", "1", "2", "3", "4", "56"),
                                            arrayOf("0", "2", "56", "1", "1", "5", "7")))

    @Test
    fun testRandom6() = assertContentEquals(arrayOf("a", "c", "a"),
                                        lcs(arrayOf("a", "b", "c", "a"),
                                            arrayOf("a", "c", "e", "a")))

    @Test
    fun testRandom7() = assertContentEquals(arrayOf("e"),
                                        lcs(arrayOf("b", "e"),
                                            arrayOf("f", "f", "g", "e")))

    @Test
    fun testRandom8() = assertContentEquals(arrayOf("aqwe", "sadasd"),
                                        lcs(arrayOf("aqwe", "bzx", "sadasd", "bzx"),
                                            arrayOf("bzx", "bzy", "aqwe", "bdqwd", "sadasd")))

    @Test
    fun testRandom10() = assertContentEquals(arrayOf("ANcb-%3q23"),
                                         lcs(arrayOf("ANcb-%3q23"),
                                             arrayOf("ANcb-%3q23")))

    @Test
    fun testRandom11() = assertContentEquals(arrayOf("-1"),
                                         lcs(arrayOf("1", "-1"),
                                             arrayOf("-1", "0")))

    @Test
    fun testRandom12() = assertContentEquals(arrayOf("X", "M", "J", "A", "Z"),
                                        lcs(arrayOf("X", "M", "J", "Y", "A", "U", "Z"),
                                            arrayOf("X", "M", "J", "A", "A", "T", "Z")))

    @Test
    fun testGroup13() = assertContentEquals(arrayOf(),
                                        lcs(arrayOf("1", "2"),
                                            arrayOf()))

    @Test
    fun testRandom14() = assertContentEquals(arrayOf(),
                                         lcs(arrayOf(),
                                             arrayOf("abc", "asd")))
}
