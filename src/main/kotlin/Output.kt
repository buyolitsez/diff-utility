import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/** Return segment as "l,r" if l != r and "l" otherwise */
fun getPairAsString(leftBorder: Int, rightBorder: Int): String {
    if (leftBorder == rightBorder) {
        return "$leftBorder"
    }
    return "$leftBorder,$rightBorder"
}

/** Return the whole command */
fun getRangeAsString(l1: Int, r1: Int, cmd: Char, l2: Int, r2: Int): String {
    return "${getPairAsString(l1, r1)}$cmd${getPairAsString(l2, r2)}"
}

/** Return diff result [ArrayList] in correct format */
fun getDiffResult(text1: Array<String>, text2: Array<String>): ArrayList<String> {
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
        val parts: List<String> = str.split(',')
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
    if (OPTIONS["unified"] == true || OPTIONS["u"] == true) {
        printDiffResultUnified(text1, text2)
        return
    }
    val answerDiff = getDiffResult(text1, text2)
    for (str in answerDiff) {
        if (str.first() == '>') {
            outputStringWithColor(ANSI_GREEN, str)
        } else if (str.first() == '<') {
            outputStringWithColor(ANSI_RED, str)
        } else if (str.first() == '-') {
            outputStringWithColor(ANSI_YELLOW, str)
        } else {
            outputStringWithColor(ANSI_PURPLE, str)
        }
    }
}

fun isAllKeepOnSegment(l : Int, r : Int, diff : Array<SingleOperation>) : Boolean {
    for (i in l..r) {
        if (r < diff.size && diff[i] != SingleOperation.KEEP) {
            return false
        }
    }
    return true
}

/** Print the diff of two texts with unified format */
fun printDiffResultUnified(text1: Array<String>, text2: Array<String>) {
    outputStringWithColor(ANSI_RED, "--- $fileName1 " + SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
    outputStringWithColor(ANSI_GREEN, "+++ $fileName2 " + SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date()))
    val diff = getCommands(text1, text2).toTypedArray()
    var it1 = 0 // iterator on first text
    var it2 = 0 // iterator on second text
    var it3 = 0 // iterator on commands in diff
    while(it3 < diff.size) {
        //значит команды только KEEP, иду дальше
        if (isAllKeepOnSegment(it3, it3 + UNIFIED_N, diff)) {
            ++it1
            ++it2
            it3++
            continue
        }
        var countRemove = 0
        var countKeep = 0
        var countAdd = 0
        // вывожу начиная с it3
        var r = it3 // до куда, включительно
        //ищу префикс длины <=UNIFIED_N не измененных строк
        while(diff[r] == SingleOperation.KEEP) {
            ++countKeep
            r++
        }
        var countKeepRow = 0
        while(r < diff.size) {
            if (diff[r] == SingleOperation.KEEP) {
                if (countKeepRow == UNIFIED_N && isAllKeepOnSegment(r, r + UNIFIED_N - 1, diff)) {
                    --r
                    break
                }
                ++countKeep
                ++countKeepRow
            } else {
                when(diff[r]) {
                    SingleOperation.ADD -> countAdd++
                    else -> countRemove++
                }
                countKeepRow = 0
            }
            ++r
        }
        if (r == diff.size) {
            --r
        }
        outputStringWithColor(ANSI_PURPLE, "@@ -${it1 + 1},${countKeep + countRemove} +${it2 + 1},${countKeep + countAdd} @@")
        for (iterator in it3..r) {
            when (diff[iterator]) {
                SingleOperation.ADD -> outputStringWithColor(ANSI_GREEN, "+" + text2[it2++])
                SingleOperation.REMOVE -> outputStringWithColor(ANSI_RED, "-" + text1[it1++])
                else -> {
                    println(" " + text1[it1])
                    it1++
                    it2++
                }
            }
        }
        it3 = r + 1
    }
}


fun outputStringWithColor(color: String, string: String) {
    if (OPTIONS["no-color"] == true || OPTIONS["c"] == true) {
        println(string)
    } else {
        println(color + string + ANSI_RESET)
    }
}
