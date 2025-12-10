package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun findTrails(input: List<String>, scoreFunction: (Collection<Pair<Int, Int>>) -> Int): Int {
        val cols = input.first().length

        val coordinates = Array(cols * input.size) { it % cols to it / cols }
        val startIndices = coordinates.filter { (x, y) -> input[y][x].digitToIntOrNull() == 0 }

        var score = 0
        startIndices.forEach { (x, y) ->
            val trailheads = mutableListOf<Pair<Int, Int>>()
            fun next(x: Int, y: Int, h: Int) {
                if (h == 9) {
                    trailheads.add(x to y)
                }

                val indices = setOf(x to y - 1, x to y + 1, x - 1 to y, x + 1 to y)
                    .filter { it in coordinates }

                indices.forEach { (x, y) ->
                    if (input[y][x].digitToIntOrNull() == h + 1) {
                        next(x, y, h + 1)
                    }
                }
            }
            next(x, y, 0)

            score += scoreFunction(trailheads)
        }

        return score
    }

    fun part1(input: List<String>) = findTrails(input) {
        it.toSet().size
    }

    fun part2(input: List<String>) = findTrails(input) {
        it.size
    }

    check(part1(readInput("Day10_test1")) == 2)
    check(part1(readInput("Day10_test2")) == 4)
    check(part1(readInput("Day10_test3")) == 3)
    check(part1(readInput("Day10_test4")) == 36)
    check(part2(readInput("Day10_test5")) == 3)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}
