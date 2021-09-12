
/** Return segment as "l,r" if l != r and "l" otherwise */
fun getPairAsString(leftBorder : Int, rightBorder : Int) : String {
    if (leftBorder == rightBorder) {
        return "$leftBorder"
    }
    return "$leftBorder,$rightBorder"
}

/** Return the whole command */
fun getRangeAsString(l1 : Int, r1 : Int, cmd : Char, l2 : Int, r2 : Int) : String {
    return "${getPairAsString(l1, r1)}$cmd${getPairAsString(l2, r2)}"
}

/** Return diff result [ArrayList] in correct format */
fun getDiffResult(text1: Array<String>, text2: Array<String>) : ArrayList<String> {
    val diff = outCommands(text1, text2)
    val answerDiff = ArrayList<String>()
    if (OPTIONS["brief"] == true || OPTIONS["q"] == true) {
        if (diff.isEmpty()) {
            return answerDiff
        }
        answerDiff.add("Files $fileName1 and $fileName2 differ")
        return answerDiff
    }
    if (OPTIONS["report-identical-files"] == true || OPTIONS["s"] == true) {
        if (diff.isEmpty()) {
            answerDiff.add("Files $fileName1 and $fileName2 are identical")
            return answerDiff
        }
    }
    for (str in diff) {
        val parts : List<String> = str.split(',')
        val l1 = parts[0].toInt()
        val r1 = parts[1].toInt()
        val l2 = parts[3].toInt()
        val r2 = parts[4].toInt()
        val cmd = parts[2][0]
        answerDiff.add(getRangeAsString(l1, r1, cmd, l2, r2))
        if (cmd == 'd' || cmd == 'c') {
            for (it in l1..r1) {
                answerDiff.add("< ${text1[it - 1]}")
            }
        }
        if (cmd == 'c') {
            answerDiff.add("---")
        }
        if (cmd == 'a' || cmd == 'c') {
            for (it in l2..r2) {
                answerDiff.add("> ${text2[it - 1]}")
            }
        }
    }
    return answerDiff
}

/** Print the diff of two texts */
fun printDiffResult(text1: Array<String>, text2: Array<String>) {
    val answerDiff = getDiffResult(text1, text2)
    for (str in answerDiff) {
        println(str)
    }
}