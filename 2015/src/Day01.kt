fun main() {
    fun part1(input: String): Int {
        return input.count { it == '(' } - input.count { it == ')' }
    }

    fun part2(input: String): Int {
        var floor = 0
        input.forEachIndexed { i, c ->
            floor += if (c == '(') 1 else -1
            if (floor == -1) {
                return i + 1
            }
        }
        return -1
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
