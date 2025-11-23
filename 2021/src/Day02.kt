sealed class Command(val units: Int) {
    class Forward(units: Int) : Command(units)
    class Down(units: Int) : Command(units)
    class Up(units: Int) : Command(units)
}

fun main() {
    fun parse(input: List<String>) = input.map {
        val (direction, units) = it.split(" ")
        when (direction) {
            "forward" -> Command.Forward(units.toInt())
            "down" -> Command.Down(units.toInt())
            "up" -> Command.Up(units.toInt())
            else -> error("Invalid command!")
        }
    }

    fun part1(input: List<Command>): Int {
        val horizontal = input.filterIsInstance<Command.Forward>().sumOf { it.units }
        val unitsDown = input.filterIsInstance<Command.Down>().sumOf { it.units }
        val unitsUp = input.filterIsInstance<Command.Up>().sumOf { it.units }

        return horizontal * (unitsDown - unitsUp)
    }

    fun part2(input: List<Command>): Int {
        var aim = 0
        var horizontal = 0
        var depth = 0

        input.forEach {
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

    check(part1(parse(readInput("Day02_test"))) == 150)
    println(part1(parse(readInput("Day02"))))

    val part2 = part2(parse(readInput("Day02_test")))
    check(part2 == 900)
    println(part2(parse(readInput("Day02"))))
}
