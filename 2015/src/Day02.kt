fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val dimensions = "\\d+".toRegex().findAll(line)
                .flatMap(MatchResult::groupValues)
                .map(String::toInt)
                .toList()

            val areas = listOf(dimensions[0] * dimensions[1], dimensions[0] * dimensions[2], dimensions[1] * dimensions[2])
            2 * areas.sum() + areas.min()
        }
    }

    check(part1(listOf("2x3x4")) == 58)
    check(part1(listOf("1x1x10")) == 43)
    part1(readInput("Day02")).println()
}
