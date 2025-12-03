import dev.dc.aoc.data.getInput

object Day03 {
    private data class Bank(
        val batteries: String,
        val joltage: String = ""
    )

    private fun parse(input: String) = input.lines()

    fun part1(input: String) = parse(input)
        .sumOf {
            val battery1 = it.dropLast(1).max()
            val battery2 = it.substringAfter(battery1, "").max()

            "$battery1$battery2".toLong()
        }

    fun part2(input: String) = parse(input)
        .sumOf { batteries ->
            (12 downTo 1)
                .fold(Bank(batteries)) { bank, n ->
                    val nextBattery = bank.batteries.dropLast(n - 1).max()
                    Bank(
                        batteries = bank.batteries.substringAfter(nextBattery),
                        joltage = bank.joltage + nextBattery
                    )
                }
                .joltage
                .toLong()
        }
}

suspend fun main() {
    val input = getInput(2025, 3)
    println(Day03.part1(input))
    println(Day03.part2(input))
}
