import dev.dc.aoc.data.getInput

typealias Grid = Map<Pair<Int, Int>, Char>

object Day04 {
    private fun parse(input: String) = input.toGrid()

    fun Grid.neighboursAt(point: Pair<Int, Int>, limit: Int = 1) = buildList {
        for (r in -limit..limit) {
            for (c in -limit..limit) {
                if (r != 0 || c != 0) {
                    add((point.first + c) to (point.second + r))
                }
            }
        }
    }.filter { it in keys && it != point }

    fun part1(input: String): Int {
        val grid = parse(input)

        return grid.keys
            .filter { grid[it] == '@' }
            .count { point ->
                val neighbours = grid.neighboursAt(point)
                neighbours.count { grid[it] == '@' } < 4
            }
    }

    fun part2(input: String): Int {
        val rolls = input.toGrid()
            .filterValues { it == '@' }
            .toMutableMap()

        val totalRolls = rolls.size
        do {
            val remainingRolls = rolls.size
            rolls.remove(
                rolls.keys.firstOrNull { point ->
                    rolls.neighboursAt(point)
                        .count { rolls[it] == '@' } < 4
                }
            )
        } while (rolls.size != remainingRolls)

        return totalRolls - rolls.size
    }
}

suspend fun main() {
    val input = getInput(2025, 4)
    println(Day04.part1(input))
    println(Day04.part2(input))
}
