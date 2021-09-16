# Курс основ программирования на МКН СПбГУ
## Проект 1: утилита diff

[Постановка задачи](./TASK.md)



# *User guide*
___

## *Using*
You can run the program with the command <br>
``` #bash
./gradlew run --args="[OPTIONS] FILES"
```

For example ```./gradlew run --args="-q src/files/text1.txt src/files/text2.txt"```
All options should start with at least one '-', otherwise it's the name of the file. <br>
If you don't write any file names, it would be "/src/text1.txt" and "/src/text2.txt" by default.

---

## DESCRIPTION

Compare FILES line by line.

Sometimes my diff program returns not the same as the diff utility, because they find different LCS.

I'm totally recommend you to use the utility with option --unified

---

## Output

The standard output for diff command in bash.

A char between two segments representing the operation.
+ "a" to Add/Insert operation
+ "c" to Change operation
+ "d" to Delete/Remove operation

The segments represent the number of lines in the first file and in the second file respectively.

---
## List of options
+ ```-q, --brief``` , report only when files differ <br>
+ ```-s, --report-identical-files``` report when two files are the same
+ ```-i, --ignore-case``` ignore case differences in file contents
+ ```--unidirectional-new``` treat absent first files as empty
+ ```-c, --no-color``` output diff result with no color
+ ```-u, --unified``` output diff result with unified form.
---
## Clarification

If multiplication of number of lines of files are huge my program crushes because of 

```"Exception in thread "main" java.lang.OutOfMemoryError: Java heap space" ```

But the count of line in each file equals to 10^4 is still working. But with count of 5 * 10^4 it doesn't work.

---

## Testing

There is some testcases in ```src/test/kotlin.Test.kt``` for some functions. 

And there is some testcases in ```src/test/files``` to check the whole program.

---
