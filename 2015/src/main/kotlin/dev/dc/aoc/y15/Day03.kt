package dev.dc.aoc.y15

import dev.dc.aoc.shared.Direction
import dev.dc.aoc.shared.move
import dev.dc.aoc.shared.println

fun main() {
    val directions = mapOf(
        '>' to Direction.EAST,
        'v' to Direction.SOUTH,
        '<' to Direction.WEST,
        '^' to Direction.NORTH,
    )

    fun part1(input: String): Int {
        val visited = mutableSetOf(0 to 0)

        var position = visited.first()
        input.forEach {
            position = position.move(directions.getValue(it))
            visited += position
        }
        return visited.size
    }

    fun part2(input: String): Int {
        val visited = mutableSetOf(0 to 0)

        var santaPosition = visited.first()
        var robotPosition = visited.first()
        input.forEachIndexed { i, it ->
            var position = if (i % 2 == 0) santaPosition else robotPosition
            position = position.move(directions.getValue(it))
            visited += position

            if (i % 2 == 0) {
                santaPosition = position
            } else {
                robotPosition = position
            }
        }
        return visited.size
    }

    check(part1(">") == 2)
    check(part1("^>v<") == 4)
    check(part1("^v^v^v^v^v") == 2)
    part1(readInputText("Day03")).println()

    check(part2("^v") == 3)
    check(part2("^>v<") == 3)
    check(part2("^v^v^v^v^v") == 11)
    part2(readInputText("Day03")).println()
}
