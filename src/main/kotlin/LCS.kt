// Implementation of LCS(https://en.wikipedia.org/wiki/Longest_common_subsequence_problem)
// Takes two arrays of strings and returns their longest common subsequence
// LCS({"A", "B", "C", "D"}, {"D", "C", "D", "A"}) = {"C", "D"}


fun lcs(text1: Array<String>, text2: Array<String>): Array<String> {
    val n = text1.size
    val m = text2.size
    val lcsOnPrefixes = Array(n + 1) { Array(m + 1) { 0 } }
    val directionOnMatrix = Array(n + 1){Array(m + 1) { 0 }} //needs to return the answer array of strings (0 for left + up, -1 for left, 1 for up)
    for (i in 1..n) {
        for (j in 1..m) {
            if (text1[i - 1] == text2[j - 1]) {
                lcsOnPrefixes[i][j] = lcsOnPrefixes[i - 1][j - 1] + 1
                directionOnMatrix[i][j] = 0
            } else {
                if (lcsOnPrefixes[i - 1][j] >= lcsOnPrefixes[i][j - 1]) {
                    lcsOnPrefixes[i][j] = lcsOnPrefixes[i - 1][j]
                    directionOnMatrix[i][j] = -1
                } else {
                    lcsOnPrefixes[i][j] = lcsOnPrefixes[i][j - 1]
                    directionOnMatrix[i][j] = 1
                }
            }
        }
    }
    return calculateResultLCS(lcsOnPrefixes, n, m, directionOnMatrix, text1)
}

private fun calculateResultLCS(
    longestCommonSubsequenceOnPrefixes: Array<Array<Int>>,
    n: Int,
    m: Int,
    directionOnMatrix: Array<Array<Int>>,
    text1: Array<String>
): Array<String> {
    var answerLen = longestCommonSubsequenceOnPrefixes[n][m]
    //calculating the result array
    val answerArray = Array(answerLen) { "" }
    var lenText1 = n
    var lenText2 = m
    while (answerLen > 0) {
        if (directionOnMatrix[lenText1][lenText2] == 0) {
            answerLen--
            --lenText1
            --lenText2
            answerArray[answerLen] = text1[lenText1]
        } else if (directionOnMatrix[lenText1][lenText2] == -1) {
            --lenText1
        } else {
            --lenText2
        }
    }
    return answerArray
}

// Getting sequence of commands to convert text1 into text2
// Returns array of commands(+ to add, - to remove, = to keep)
// Example
// answer for text1 = {"a", "b"} and text2 = {"b", "c"}
// is {-, =, +}

fun getCommands(text1: Array<String>, text2: Array<String>): ArrayList<Char> {
    val lcs = lcs(text1, text2)

    val commands = ArrayList<Char>() // answer array
    var it1 = 0 // iterator on the first text
    var it2 = 0 // iterator on the second text
    var common = 0 //iterator on the LCS
    while((it1 < text1.size || it2 < text2.size) && common < lcs.size) {
        if (it1 < text1.size && it2 < text2.size && text1[it1] == text2[it2]) {
            commands.add('=')
            ++it1
            ++it2
            ++common
            continue
        }
        if (it1 == text1.size || text1[it1] == lcs[common]) {
            commands.add('+')
            it2++
        } else {
            commands.add('-')
            it1++
        }
    }
    for (i in it1 until text1.size) {
        commands.add('-')
    }
    for (i in it2 until text2.size) {
        commands.add('+')
    }
    return commands
}

// Compress commands to a short form
fun compressToPairs(commands : ArrayList<Char>) : ArrayList<Pair<Int, Char>> {
    val commandsPairs = ArrayList<Pair<Int, Char>>()
    for (ch in commands) {
        if (commandsPairs.isNotEmpty() && commandsPairs.last().second == ch) {
            commandsPairs[commandsPairs.size - 1] = Pair(commandsPairs.last().first + 1, ch)
        } else {
            commandsPairs.add(Pair(1, ch))
        }
    }
    return commandsPairs
}

// Returns the diff function results
fun outCommands(text1: Array<String>, text2: Array<String>) : ArrayList<String> {
    val commands = getCommands(text1, text2)
    val commandsPairs = compressToPairs(commands)
    var it1 = 0
    var it2 = 0
    var i = 0
    val result = ArrayList<String>()
    while (i < commandsPairs.size) {
        if (commandsPairs[i].second == '=') {
            it1 += commandsPairs[i].first
            it2 += commandsPairs[i].first
            ++i
            continue
        }
        var add = 0
        var remove = 0
        var j = i
        while(j < commandsPairs.size && commandsPairs[j].second != '=') {
            if (commandsPairs[j].second == '-') {
                remove += commandsPairs[j].first
            } else {
                add += commandsPairs[j].first
            }
            ++j
        }
        i = j
        if (add == 0) {
            result.add("${it1 + 1},${it1 + remove},d,0,0")
//            result.add("${outputRange(it1 + 1, it1 + remove)}d")
        } else if (remove == 0) {
            result.add("${it1},${it1},a,${it2 + 1},${it2 + add}")
//            result.add("${it1}a${outputRange(it2 + 1, it2 + add)}")
        } else {
            result.add("${it1 + 1},${it1 + remove},c,${it2 + 1},${it2 + add}")
//            result.add("${outputRange(it1 + 1, it1 + remove)}c${outputRange(it2 + 1, it2 + add)}")
        }
        it1 += remove
        it2 += add
    }
    return result
}

fun outputPair(leftBorder : Int, rightBorder : Int) : String {
    if (leftBorder == rightBorder) {
        return "$leftBorder"
    }
    return "$leftBorder,$rightBorder"
}

// Output range as "l,r" if l != r and "l" otherwise
fun outputRange(l1 : Int, r1 : Int, cmd : Char, l2 : Int, r2 : Int) {
    if (cmd != 'd') {
        println("${outputPair(l1, r1)}$cmd${outputPair(l2, r2)}")
    } else {
        println("${outputPair(l1, r1)}$cmd")
    }
}

fun outputResultOfDiff(text1: Array<String>, text2: Array<String>) {
    val diff = outCommands(text1, text2)
    for (i in diff) {
        val parts : List<String> = i.split(',')
        val l1 = parts[0].toInt()
        val r1 = parts[1].toInt()
        val l2 = parts[3].toInt()
        val r2 = parts[4].toInt()
        val cmd = parts[2][0]
        outputRange(l1, r1, cmd, l2, r2)
        if (cmd == 'd' || cmd == 'c') {
            for (it in l1..r1) {
                println("< ${text1[it - 1]}")
            }
        }
        if (cmd == 'c') {
            println("---")
        }
        if (cmd == 'a' || cmd == 'c') {
            for (it in l2..r2) {
                println("> ${text2[it - 1]}")
            }
        }
    }
}