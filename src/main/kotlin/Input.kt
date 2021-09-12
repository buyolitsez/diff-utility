import java.io.File
import java.io.InputStream
import java.util.*


/** Read text from file [fileName] */
fun readText(fileName : String): Array<String> {
    if (!File(fileName).exists()) {
        if (OPTIONS["unidirectional-new"] == true) {
            OPTIONS["unidirectional-new"] = false
            return arrayOf()
        } else {
            throw java.io.FileNotFoundException("$fileName (No such file or directory)")
        }
    }
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


/** Read text from run args [args] */
fun readArgs(args: Array<String>): Pair<String, String> {
    var changedName1 = false
    var changedName2 = false
    for (arg in args) {
        if (arg[0] == '-') {
            if (!OPTIONS.containsKey(convertOptionToString(arg))) {
                throw Exception("Unknown option $arg")
            }
            OPTIONS[convertOptionToString(arg)] = true
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

/** Count len of the char [ch] on prefix in the string [str] */
fun countOnPrefix(str: String, ch: Char) :Int {
    var count = 0
    for (i in str) {
        if (i == ch) {
            ++count
        } else {
            break
        }
    }
    return count
}

/** Delete the symbol '-' from the beginning of the string [option] */
fun convertOptionToString(option: String): String {
    return option.substring(countOnPrefix(option, '-') until option.length)
}