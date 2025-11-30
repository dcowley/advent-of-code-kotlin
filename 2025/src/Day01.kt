import dev.dc.aoc.data.getInput

object Day01 {
    private fun parse(input: String) = input.lines()

    fun part1(input: String) = parse(input).let {

        Int.MIN_VALUE
    }

    fun part2(input: String) = parse(input).let {

        Int.MIN_VALUE
    }
}

suspend fun main() {
    val input = getInput(2025, 1)
    println(Day01.part1(input))
    println(Day01.part2(input))
}
