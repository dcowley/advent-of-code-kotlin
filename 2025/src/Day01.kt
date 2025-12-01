import dev.dc.aoc.data.getInput

object Day01 {
    private fun parse(input: String) = input.lines()

    fun part1(input: String) = parse(input).let {
        var position = 50

        it.count { instruction ->
            val next = instruction.drop(1).toInt()
            when (instruction.first()) {
                'L' -> position -= next
                'R' -> position += next
            }

            position % 100 == 0
        }
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
