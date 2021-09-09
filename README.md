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
All options should start with '-', otherwise it's the name of the file <br>
If you don't write any file names, it would be "/src/text1.txt" and "/src/text2.txt"

## List of options
+ ```-q, -brief``` , report only when files differ <br>
+ ```-s, -report-identical-files``` report when two files are the same
+ ```-i, -ignore-case``` ignore case differences in file contents
