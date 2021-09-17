import java.io.File
import java.io.InputStream

fun setSetting(name : String, value : String) {
    when(name) {
        "file-name1" -> fileName1 = value
        "file-name2" -> fileName2 = value
        else -> {
            val valueInt = value.toIntOrNull()
            if (valueInt == null) {
                throwError("Unknown number $value")
            } else {
                if (name == "unified_num") {
                    UNIFIED_N = valueInt
                } else if(OPTIONS.containsKey(name)) {
                    when(valueInt) {
                        0 -> OPTIONS[name] = false
                        1 -> OPTIONS[name] = true
                        else -> throwError("$valueInt not a boolean")
                    }
                } else {
                    throwError("Unknown option $name")
                }
            }
        }
    }
}

fun loadSettingFromFile(fileName: String) {
    for (str in readText(fileName)) {
        if (str.isBlank()) continue
        val arg = str.filter{!it.isWhitespace()}
        if (arg.count { it == '=' } != 1) {
            throwError("Unknown option\n$str")
        }
        val optionName = arg.substringBefore('=')
        val optionValue = arg.substringAfter('=')
        setSetting(optionName, optionValue)
    }
}