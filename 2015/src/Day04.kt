fun main() {
    fun solve(input: String, numZeros: Int): Long {
        var i = -1L
        while (true) {
            if ("$input${++i}".md5().startsWith("0".repeat(numZeros))) {
                return i
            }
        }
    }

    fun part1(input: String) = solve(input, 5)
    fun part2(input: String) = solve(input, 6)

    check(part1("abcdef") == 609043L)
    check(part1("pqrstuv") == 1048970L)
    part1(readInputText("Day04")).println()

    part2(readInputText("Day04")).println()
}
