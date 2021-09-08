
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
// Returns array of indexes and +, -(+ to add, - to remove)
// Indexes starts from 1
fun getCommands(text1: Array<String>, text2: Array<String>): MutableList<Int> {
    val lcs = lcs(text1, text2)

    val indexes = MutableList<Int>(0) {0}
    var it1 = 0 // iterator on the first text
    var it2 = 0 // iterator on the second text
    var common = 0 //iterator on the LCS
    while((it1 < text1.count() || it2 < text2.count()) && common < lcs.count()) {
        if (it1 < text1.count() && it2 < text2.count() && text1[it1] == text2[it2]) {
            ++it1
            ++it2
            ++common
            continue
        }
        if (it1 == text1.count() || text1[it1] == lcs[common]) {
            indexes.add(it2 + 1)
            it2++
        } else {
            indexes.add(-it1 - 1)
            it1++
        }
    }
    for (i in it1 until text1.count()) {
        indexes.add(-i - 1)
    }
    for (i in it2 until text2.count()) {
        indexes.add(i + 1)
    }
    return indexes
}

fun main(args: Array<String>) {
    println(lcs(arrayOf("A", "B", "C", "A", "B", "B", "A"), arrayOf("C", "B", "A", "B", "A", "C")))
}
