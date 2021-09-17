import java.io.File
import java.io.InputStream


/** Read text from file [fileName] */
fun readText(fileName: String): Array<String> {
    if (!File(fileName).exists()) {
        if (OPTIONS["unidirectional-new"] == true) {
            OPTIONS["unidirectional-new"] = false
            return arrayOf()
        } else {
            throwError("$fileName (No such file or directory)")
        }
    }
    val inputStream: InputStream = File(fileName).inputStream()

    val text = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { text.add(it) }
    return text.toTypedArray()
}

fun changeUnifiedConstant(arg: String): Boolean {
    if (convertOptionToString(arg).startsWith("U=") ||
        convertOptionToString(arg).startsWith("u=") ||
        convertOptionToString(arg).startsWith("unified=")
    ) {

        OPTIONS["unified"] = true
        val n = arg.substringAfter('=').toIntOrNull()
        if (n == null) {
            throwError("Unknown number $n in $arg")
        } else {
            UNIFIED_N = n
            return true
        }
    }
    return false
}

/** Read text from run args [args] */
fun readArgs(args: Array<String>): Pair<String, String> {
    var changedName1 = false
    var changedName2 = false
    args.forEach { arg ->
        if (arg[0] == '-') {
            if (!changeUnifiedConstant(arg)) {
                if (!OPTIONS.containsKey(convertOptionToString(arg))) {
                    throwError("Unknown option $arg")
                }
                OPTIONS[convertOptionToString(arg)] = true
            }
        } else {
            if (!changedName1) {
                fileName1 = arg
                changedName1 = true
            } else if (!changedName2) {
                fileName2 = arg
                changedName2 = true
            } else {
                throwError("Too many files to diff")
            }
        }
    }
    if (changedName1.xor(changedName2)) {
        throwError("Missing file name")
    }
    return Pair(fileName1, fileName2)
}

/** Count len of the char [ch] on prefix in the string [str] */
fun countOnPrefix(str: String, ch: Char): Int {
    return str.takeWhile { it == ch }.length
}

/** Delete the symbol '-' from the beginning of the string [option] */
fun convertOptionToString(option: String): String {
    return option.substring(countOnPrefix(option, '-') until option.length)
}
