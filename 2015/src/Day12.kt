fun main() {
    fun part1(input: String): Int {
        return "(-*\\d+)".toRegex().findAll(input).sumOf {
            it.groupValues.first().toInt()
        }
    }

    check(part1("[1,2,3]") == 6)
    check(part1("{\"a\":2,\"b\":4}") == 6)
    check(part1("[[[3]]]") == 3)
    check(part1("{\"a\":{\"b\":4},\"c\":-1}") == 3)
    check(part1("{\"a\":[-1,1]}") == 0)
    check(part1("[-1,{\"a\":1}]") == 0)
    check(part1("[]") == 0)
    check(part1("{}") == 0)
    part1(readInputText("Day12")).println()
}
