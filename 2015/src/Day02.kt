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

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val dimensions = "\\d+".toRegex().findAll(line)
                .flatMap(MatchResult::groupValues)
                .map(String::toInt)
                .toList()

            dimensions.sorted().let {
                2 * (it[0] + it[1]) + it[0] * it[1] * it[2]
            }
        }
    }

    check(part1(listOf("2x3x4")) == 58)
    check(part1(listOf("1x1x10")) == 43)
    part1(readInput("Day02")).println()

    check(part2(listOf("2x3x4")) == 34)
    check(part2(listOf("1x1x10")) == 14)
    part2(readInput("Day02")).println()
}
