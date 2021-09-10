import java.io.File
import java.io.InputStream
import java.util.*


// Read text from file with name "$fileName"
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
