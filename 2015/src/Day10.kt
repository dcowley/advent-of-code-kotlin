fun main() {
    fun part1(input: String, iterations: Int): String {
        var s = input
        repeat(iterations) {
            s = buildString {
                "(.)\\1*".toRegex().findAll(s)
                    .forEach {
                        append("${it.value.length}${it.groupValues.last()}")
                    }
            }
        }
        return s
    }

    check(part1("1", 1) == "11")
    check(part1("1", 2) == "21")
    check(part1("1", 3) == "1211")
    check(part1("1", 4) == "111221")
    check(part1("1", 5) == "312211")
    part1(readInputText("Day10"), 40).length.println()
}
