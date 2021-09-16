import java.util.*

/**
 * Implementation of LCS(https://en.wikipedia.org/wiki/Longest_common_subsequence_problem)
 * Takes two arrays text1, text2 of strings and returns their longest common subsequence
 * LCS({"A", "B", "C", "D"}, {"D", "C", "D", "A"}) = {"C", "D"}
 */

enum class Direction {
    LEFT_UP, LEFT, UP
}

/** Compare two strings, case-sensitive or not(depending on option) */
fun isStringsEqual(str1: String, str2: String): Boolean {
    if (OPTIONS["ignore-case"] == true || OPTIONS["i"] == true) {
        return str1.lowercase(Locale.getDefault()) == str2.lowercase(Locale.getDefault())
    }
    return str1 == str2
}

/** Return the largest common subsequence of two texts [text1] [text2] */

fun lcs(text1: Array<String>, text2: Array<String>): Array<String> {
    val text1Size = text1.size
    val text2Size = text2.size
    val lcsOnPrefixes = Array(text1Size + 1) { Array(text2Size + 1) { 0 } }
    val directionOnMatrix =
        Array(text1Size + 1) { Array(text2Size + 1) { Direction.LEFT_UP } } //needs to return the answer array of strings (0 for left + up, -1 for left, 1 for up)
    for (len1 in 1..text1Size) {
        for (len2 in 1..text2Size) {
            if (isStringsEqual(text1[len1 - 1], text2[len2 - 1])) {
                lcsOnPrefixes[len1][len2] = lcsOnPrefixes[len1 - 1][len2 - 1] + 1
                directionOnMatrix[len1][len2] = Direction.LEFT_UP
            } else {
                if (lcsOnPrefixes[len1 - 1][len2] >= lcsOnPrefixes[len1][len2 - 1]) {
                    lcsOnPrefixes[len1][len2] = lcsOnPrefixes[len1 - 1][len2]
                    directionOnMatrix[len1][len2] = Direction.LEFT
                } else {
                    lcsOnPrefixes[len1][len2] = lcsOnPrefixes[len1][len2 - 1]
                    directionOnMatrix[len1][len2] = Direction.UP
                }
            }
        }
    }
    return calculateResultLCS(lcsOnPrefixes, text1Size, text2Size, directionOnMatrix, text1)
}

/** Calculate the resulting string from matrix of directions */
private fun calculateResultLCS(
    longestCommonSubsequenceOnPrefixes: Array<Array<Int>>,
    text1Size: Int,
    text2Size: Int,
    directionOnMatrix: Array<Array<Direction>>,
    text1: Array<String>
): Array<String> {
    var answerLen = longestCommonSubsequenceOnPrefixes[text1Size][text2Size]
    //calculating the result array
    val answerArray = Array(answerLen) { "" }
    var lenText1 = text1Size
    var lenText2 = text2Size
    while (answerLen > 0) {
        when (directionOnMatrix[lenText1][lenText2]) {
            Direction.LEFT_UP -> {
                answerLen--
                --lenText1
                --lenText2
                answerArray[answerLen] = text1[lenText1]
            }
            Direction.LEFT -> --lenText1
            else -> --lenText2
        }
    }
    return answerArray
}

