import java.io.File
import java.io.InputStream

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
    while((it1 < text1.count() || it2 < text2.count()) && common < lcs.count()) {
        if (it1 < text1.count() && it2 < text2.count() && text1[it1] == text2[it2]) {
            commands.add('=')
            ++it1
            ++it2
            ++common
            continue
        }
        if (it1 == text1.count() || text1[it1] == lcs[common]) {
            commands.add('+')
            it2++
        } else {
            commands.add('-')
            it1++
        }
    }
    for (i in it1 until text1.count()) {
        commands.add('-')
    }
    for (i in it2 until text2.count()) {
        commands.add('+')
    }
    return commands
}

// Compress commands to a short form
fun compressToPairs(commands : ArrayList<Char>) : ArrayList<Pair<Int, Char>> {
    val commandsPairs = ArrayList<Pair<Int, Char>>()
    for (ch in commands) {
        if (commandsPairs.isNotEmpty() && commandsPairs.last().second == ch) {
            commandsPairs[commandsPairs.count() - 1] = Pair(commandsPairs.last().first + 1, ch)
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
    while (i < commandsPairs.count()) {
        if (commandsPairs[i].second == '=') {
            it1 += commandsPairs[i].first
            it2 += commandsPairs[i].first
            ++i
            continue
        }
        var add = 0
        var remove = 0
        var j = i
        while(j < commandsPairs.count() && commandsPairs[j].second != '=') {
            if (commandsPairs[j].second == '-') {
                remove += commandsPairs[j].first
            } else {
                add += commandsPairs[j].first
            }
            ++j
        }
        i = j
        if (add == 0) {
            result.add("${outputRange(it1 + 1, it1 + remove)}d")
        } else if (remove == 0) {
            result.add("${it1}a${outputRange(it2 + 1, it2 + add)}")
        } else {
            result.add("${outputRange(it1 + 1, it1 + remove)}c${outputRange(it2 + 1, it2 + add)}")
        }
        it1 += remove
        it2 += add
    }
    return result
}

// Output range as "l,r" if l != r and "l" otherwise
fun outputRange(leftBorder : Int, rightBorder : Int) : String {
    if (leftBorder == rightBorder) {
        return leftBorder.toString()
    }
    return "$leftBorder,$rightBorder"
}

fun outputResultOfDiff(text1: Array<String>, text2: Array<String>) {
    val diff = outCommands(text1, text2)
    for (i in diff) {
        println(i)
    }
}

// Read text from file with name "$fileName"
fun readText(fileName : String): Array<String> {
    val inputStream: InputStream = File(fileName).inputStream()

    val text = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { text.add(it) }
    return text.toTypedArray()
}

fun main(args: Array<String>) {
    val text1 : Array<String> = readText("src/text1.txt")
    val text2 : Array<String> = readText("src/text2.txt")
    outputResultOfDiff(text1, text2)
}
