import java.util.ArrayList

enum class SingleOperation {
    ADD, REMOVE, KEEP
}

/**
 * Getting sequence of commands to convert text1 into text2
 * Return array of SingleOperations
 * Example
 * answer for text1 = {"a", "b"} and text2 = {"b", "c"}
 * is {REMOVE, KEEP, ADD}
 */

fun getCommands(text1: Array<String>, text2: Array<String>): ArrayList<SingleOperation> {
    val lcs = lcs(text1, text2)

    val commands = ArrayList<SingleOperation>() // answer array
    var it1 = 0 // iterator on the first text
    var it2 = 0 // iterator on the second text
    var common = 0 //iterator on the LCS
    while ((it1 < text1.size || it2 < text2.size) && common < lcs.size) {
        if (it1 < text1.size && it2 < text2.size && text1[it1] == text2[it2]) {
            commands.add(SingleOperation.KEEP)
            ++it1
            ++it2
            ++common
            continue
        }
        if (it1 == text1.size || text1[it1] == lcs[common]) {
            commands.add(SingleOperation.ADD)
            it2++
        } else {
            commands.add(SingleOperation.REMOVE)
            it1++
        }
    }
    for (it in it1 until text1.size) {
        commands.add(SingleOperation.REMOVE)
    }
    for (it in it2 until text2.size) {
        commands.add(SingleOperation.ADD)
    }
    return commands
}

/** Compress a row of equals SingleOperations into form (count, SingleOperation) */
data class Command(var count: Int, val cmd: SingleOperation)

/**
 * Compress SingleOperations to a short form Commands
 * Example: {ADD, ADD, REMOVE, ADD, REMOVE, REMOVE, REMOVE}
 * return {{2, ADD}, {1, REMOVE}, {1, ADD}, {3, REMOVE}}
 */
fun compressToCommands(commands: Collection<SingleOperation>): ArrayList<Command> {
    val commandsPairs = ArrayList<Command>()
    for (ch in commands) {
        if (commandsPairs.isNotEmpty() && commandsPairs.last().cmd == ch) {
            commandsPairs[commandsPairs.size - 1].count++
        } else {
            commandsPairs.add(Command(1, ch))
        }
    }
    return commandsPairs
}

/** Return the diff function results */
fun outCommands(text1: Array<String>, text2: Array<String>): ArrayList<String> {
    val commands = getCommands(text1, text2)
    val commandsPairs = compressToCommands(commands)
    var it1 = 0
    var it2 = 0
    var it = 0
    val result = ArrayList<String>()
    while (it < commandsPairs.size) {
        if (commandsPairs[it].cmd == SingleOperation.KEEP) {
            it1 += commandsPairs[it].count
            it2 += commandsPairs[it].count
            ++it
            continue
        }
        var add = 0
        var remove = 0
        var j = it
        while (j < commandsPairs.size && commandsPairs[j].cmd != SingleOperation.KEEP) {
            if (commandsPairs[j].cmd == SingleOperation.REMOVE) {
                remove += commandsPairs[j].count
            } else {
                add += commandsPairs[j].count
            }
            ++j
        }
        it = j
        when {
            add == 0 -> result.add("${it1 + 1},${it1 + remove},d,${it2},${it2}")
            remove == 0 -> result.add("${it1},${it1},a,${it2 + 1},${it2 + add}")
            else -> result.add("${it1 + 1},${it1 + remove},c,${it2 + 1},${it2 + add}")
        }
        it1 += remove
        it2 += add
    }
    return result
}
