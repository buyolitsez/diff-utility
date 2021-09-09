import java.io.File
import java.io.InputStream
import java.util.*

val OPTIONS = mutableMapOf("brief" to false, "q" to false,
                           "report-identical-files" to false, "s" to false,
                           "ignore-case" to false, "i" to false)
var fileName1 = "src/files/text1.txt"
var fileName2 = "src/files/text2.txt"

// Read text from file with name "$fileName"
fun readText(fileName : String): Array<String> {
    val inputStream: InputStream = File(fileName).inputStream()

    val text = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { text.add(it) }
    if (OPTIONS["ignore-case"] == true || OPTIONS["i"] == true) {
        for (it in text.indices) {
            text[it] = text[it].lowercase(Locale.getDefault())
        }
    }
    return text.toTypedArray()
}

// Read text from build args

fun readFromArgs(args: Array<String>): Pair<String, String> {
    var changedName1 = false
    var changedName2 = false
    for (arg in args) {
        if (arg[0] == '-') {
            if (!OPTIONS.containsKey(arg.substring(1 until arg.count()))) {
                throw Exception("Unknown option $arg")
            }
            OPTIONS[arg.substring(1 until arg.count())] = true
        } else {
            if (!changedName1) {
                fileName1 = arg
                changedName1 = true
            } else if (!changedName2) {
                fileName2 = arg
                changedName2 = true
            } else {
                throw Exception("Too many files to diff")
            }
        }
    }
    if (changedName1.xor(changedName2)) {
        throw Exception("Missing file name")
    }
    return Pair(fileName1, fileName2)
}