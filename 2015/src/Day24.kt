fun main() {
    fun parse(name: String) = readInput(name)
        .map(String::toInt)
        .toSet()

    fun solve(input: Set<Int>, target: Int): Long {
        var size = 1
        while (size < input.size) {
            val combinations = combinations(input.toIntArray(), size)
                .filter { it.sum() == target }

            if (combinations.isNotEmpty()) {
                return combinations
                    .minOf { it.fold(1L) { acc, i -> acc * i } }
            }
            size++
        }
        error("No solution")
    }

    fun part1(input: Set<Int>) = solve(input, input.sum() / 3)

    fun part2(input: Set<Int>) = solve(input, input.sum() / 4)

    check(part1(parse("Day24_test")) == 99L)
    part1(parse("Day24")).println()
    part2(parse("Day24")).println()
}
