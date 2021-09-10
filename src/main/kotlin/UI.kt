
// Count len of the char(ch) on prefix in the string(str)
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

// Deletes the '-' from the beginning of the file
fun convertOptionToString(option: String): String {
    return option.substring(countOnPrefix(option, '-') until option.length)
}

// Read text from run args
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