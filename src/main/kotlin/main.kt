
//Implementation of LCS(https://en.wikipedia.org/wiki/Longest_common_subsequence_problem)
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
    if (answerArray.contentEquals(arrayOf("a", "b"))) {
        println("all is ok")
    }
    return answerArray
}

fun main(args: Array<String>) {
    TODO()
}
