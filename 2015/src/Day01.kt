fun main() {
    fun part1(input: String): Int {
        return input.count { it == '(' } - input.count { it == ')' }
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
}
