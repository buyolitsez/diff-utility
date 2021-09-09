
fun main(args: Array<String>) {
    val (fileName1, fileName2) = readFromArgs(args)
    val text1 : Array<String> = readText(fileName1)
    val text2 : Array<String> = readText(fileName2)
    printAnswerDiff(text1, text2)
}
