/** Map of exists options */
val OPTIONS = mutableMapOf(
    "brief" to false, "q" to false,
    "report-identical-files" to false, "s" to false,
    "ignore-case" to false, "i" to false,
    "unidirectional-new" to false,
    "no-color" to false, "c" to false
)

var fileName1 = "src/files/text1.txt"
var fileName2 = "src/files/text2.txt"

const val ANSI_RESET = "\u001B[0m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_PURPLE = "\u001B[35m"