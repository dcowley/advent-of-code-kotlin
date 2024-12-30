fun main() {
    fun part1(input: String): Int {
        return input.sumOf { (if (it == '(') 1 else -1).toInt() }
    }

    fun part2(input: String): Int {
        var floor = 0
        var index = 0

        while (floor != -1) {
            floor += if (input[index++] == '(') 1 else -1
        }
        return index
    }

    check(part1("(())") == 0)
    check(part1("()()") == 0)
    check(part1("(((") == 3)
    check(part1("(()(()(") == 3)
    check(part1("))(((((") == 3)
    check(part1("())") == -1)
    check(part1("))(") == -1)
    check(part1(")))") == -3)
    check(part1(")())())") == -3)
    part1(readInputText("Day01")).println()

    check(part2(")") == 1)
    check(part2("()())") == 5)
    part2(readInputText("Day01")).println()
}
