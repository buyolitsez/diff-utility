import kotlin.test.*

internal class TestLCS {
    @Test
    fun testSomeIsEmpty() {
        assertContentEquals(
            arrayOf(),
            lcs(
                arrayOf(),
                arrayOf()
            )
        )
        assertContentEquals(
            arrayOf(),
            lcs(
                arrayOf("1", "2"),
                arrayOf()
            )
        )
        assertContentEquals(
            arrayOf(),
            lcs(
                arrayOf(),
                arrayOf("abc", "asd")
            )
        )
    }

    @Test
    fun testDifferentCases() = assertContentEquals(
        arrayOf(),
        lcs(
            arrayOf("x", "m", "J", "Y", "A", "U", "Z"),
            arrayOf("X", "M", "j", "a", "a", "t", "z")
        )
    )

    @Test
    fun testEmptyLCS() = assertContentEquals(
        arrayOf(),
        lcs(
            arrayOf("aqwe", "bzx"),
            arrayOf("abasd", "bdqwd")
        )
    )

    @Test
    fun testEqualStrings() = assertContentEquals(
        arrayOf("a", "b"),
        lcs(
            arrayOf("a", "b"),
            arrayOf("a", "b")
        )
    )

    @Test
    fun testEqualStringsSpecialSymbols() = assertContentEquals(
        arrayOf("ANcb-%3q23"),
        lcs(
            arrayOf("ANcb-%3q23"),
            arrayOf("ANcb-%3q23")
        )
    )

    @Test
    fun testEqualSuffix() = assertContentEquals(
        arrayOf("b", "a"),
        lcs(
            arrayOf("a", "b", "a"),
            arrayOf("c", "b", "a")
        )
    )

    @Test
    fun testFirstPrefixAndSecondSuffix() = assertContentEquals(
        arrayOf("a"),
        lcs(
            arrayOf("a", "b"),
            arrayOf("ba", "a")
        )
    )

    @Test
    fun testFirstSuffixAndSecondPrefix() = assertContentEquals(
        arrayOf("-1"),
        lcs(
            arrayOf("1", "-1"),
            arrayOf("-1", "0")
        )
    )

    @Test
    fun testOnlyLastEquals() = assertContentEquals(
        arrayOf("e"),
        lcs(
            arrayOf("b", "e"),
            arrayOf("f", "f", "g", "e")
        )
    )

    @Test
    fun testNumbers() = assertContentEquals(
        arrayOf("0", "2", "56"),
        lcs(
            arrayOf("0", "1", "2", "3", "4", "56"),
            arrayOf("0", "2", "56", "1", "1", "5", "7")
        )
    )

    @Test
    fun testLowerCaseSmall() = assertContentEquals(
        arrayOf("a", "c", "a"),
        lcs(
            arrayOf("a", "b", "c", "a"),
            arrayOf("a", "c", "e", "a")
        )
    )

    @Test
    fun testWords() = assertContentEquals(
        arrayOf("aqwe", "sadasd"),
        lcs(
            arrayOf("aqwe", "bzx", "sadasd", "bzx"),
            arrayOf("bzx", "bzy", "aqwe", "bdqwd", "sadasd")
        )
    )

    @Test
    fun testUpperCase() = assertContentEquals(
        arrayOf("X", "M", "J", "A", "Z"),
        lcs(
            arrayOf("X", "M", "J", "Y", "A", "U", "Z"),
            arrayOf("X", "M", "J", "A", "A", "T", "Z")
        )
    )

}
