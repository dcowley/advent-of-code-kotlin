import dev.dc.aoc.data.getInput

object Day01 {
    private fun parse(input: String) = input
        .lines()
        .map(String::toInt)

    fun part1(input: String) = parse(input)
        .zipWithNext()
        .count { (a, b) -> a < b }

    fun part2(input: String) = parse(input)
        .windowed(3)
        .zipWithNext()
        .count { (l, r) -> l.sum() < r.sum() }
}

suspend fun main() {
    val input = getInput(2021, 1)

    println(Day01.part1(input))
    println(Day01.part2(input))
}
