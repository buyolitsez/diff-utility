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
        for (i in text.indices) {
            text[i] = text[i].lowercase(Locale.getDefault())
        }
    }
    return text.toTypedArray()
}

// Read text from build args

fun readFromArgs(args: Array<String>): Pair<String, String> {
    var changedName1 = false
    var changedName2 = false
    for (i in args) {
        if (i[0] == '-') {
            if (!OPTIONS.containsKey(i.substring(1 until i.count()))) {
                throw Exception("Unknown option $i")
            }
            OPTIONS[i.substring(1 until i.count())] = true
        } else {
            if (!changedName1) {
                fileName1 = i
                changedName1 = true
            } else if (!changedName2) {
                fileName2 = i
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