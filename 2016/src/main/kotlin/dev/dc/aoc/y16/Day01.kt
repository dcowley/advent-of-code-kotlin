package dev.dc.aoc.y16

import dev.dc.aoc.shared.Direction
import dev.dc.aoc.shared.Rotation
import dev.dc.aoc.shared.move
import dev.dc.aoc.shared.x
import dev.dc.aoc.shared.y
import kotlin.math.abs

fun main() {
    val regex = Regex("([R|L])(\\d+)")

    fun parse(input: String) = regex.findAll(input)
        .map { (if (it.groupValues[1] == "R") Rotation.CLOCKWISE else Rotation.ANTICLOCKWISE) to it.groupValues[2].toInt() }

    fun part1(input: String): Int {
        val instructions = parse(input)
        var direction = Direction.NORTH
        var point = 0 to 0

        instructions.forEach { (rotation, steps) ->
            direction = direction.turn(rotation)
            repeat(steps) {
                point = point.move(direction)
            }
        }

        return abs(point.x) + abs(point.y)
    }

    fun part2(input: String): Int {
        val instructions = parse(input)
        var direction = Direction.NORTH
        var point = 0 to 0

        val visitedPoints = mutableSetOf<Pair<Int, Int>>()
        instructions.forEach { (rotation, steps) ->
            direction = direction.turn(rotation)
            repeat(steps) {
                point = point.move(direction)
                if (point in visitedPoints) {
                    return abs(point.x) + abs(point.y)
                }
                visitedPoints += point
            }
        }

        return abs(point.x) + abs(point.y)
    }

    check(part1("R2, L3") == 5)
    check(part1("R2, R2, R2") == 2)
    check(part1("R5, L5, R5, R3") == 12)
    println(part1(readInputText("Day01")))

    check(part2("R8, R4, R4, R8") == 4)
    println(part2(readInputText("Day01")))
}
