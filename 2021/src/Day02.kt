import dev.dc.aoc.data.getInput

sealed class Command(val units: Int) {
    class Forward(units: Int) : Command(units)
    class Down(units: Int) : Command(units)
    class Up(units: Int) : Command(units)
}

object Day02 {
    private fun parse(input: String) = input.lines().map {
        val (direction, units) = it.split(" ")
        when (direction) {
            "forward" -> Command.Forward(units.toInt())
            "down" -> Command.Down(units.toInt())
            "up" -> Command.Up(units.toInt())
            else -> error("Invalid command!")
        }
    }

    fun part1(input: String): Int {
        var horizontal = 0
        var depth = 0

        parse(input).forEach {
            when (it) {
                is Command.Forward -> {
                    horizontal += it.units
                }

                is Command.Down -> depth += it.units
                is Command.Up -> depth -= it.units
            }
        }

        return horizontal * depth
    }

    fun part2(input: String): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0

        parse(input).forEach {
            when (it) {
                is Command.Forward -> {
                    horizontal += it.units
                    depth += it.units * aim
                }

                is Command.Down -> aim += it.units
                is Command.Up -> aim -= it.units
            }
        }

        return horizontal * depth
    }
}

suspend fun main() {
    println(Day02.part1(getInput(2021, 2)))
    println(Day02.part2(getInput(2021, 2)))
}
