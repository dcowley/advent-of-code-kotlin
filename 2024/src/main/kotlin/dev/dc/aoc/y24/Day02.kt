package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun part1(input: List<String>): Int {
        val levels = input.map { it.split(' ').map(String::toInt) }
        return levels.count {
            isSafe(it) || isSafe(it.reversed())
        }
    }

    fun part2(input: List<String>): Int {
        val levels = input.map { it.split(' ').map(String::toInt) }
        return levels.count {
            it.indices.any { i ->
                val dampened = it.toMutableList()
                dampened.removeAt(i)
                isSafe(dampened) || isSafe(dampened.reversed())
            }
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}

fun isSafe(levels: List<Int>): Boolean {
    val diff = IntArray(levels.size - 1) { i ->
        levels[i + 1] - levels[i]
    }
    return diff.all { it in 1..3 }
}
