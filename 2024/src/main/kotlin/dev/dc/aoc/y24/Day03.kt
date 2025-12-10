package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    val isMultiply = "mul\\((\\d+),(\\d+)\\)".toRegex()
    val isDisabled = "don't\\(\\)((.|\\n)*?)do\\(\\)".toRegex()

    fun part1(input: List<String>): Int {
        val text = input.joinToString()

        return isMultiply.findAll(text)
            .fold(0) { acc, match ->
                val l = match.groups[1]?.value?.toInt() ?: 0
                val r = match.groups[2]?.value?.toInt() ?: 0
                acc + (l * r)
            }
    }

    fun part2(input: List<String>): Int {
        val text = input.joinToString()

        return isMultiply.findAll(text.replace(isDisabled, ""))
            .fold(0) { acc, match ->
                val l = match.groups[1]?.value?.toInt() ?: 0
                val r = match.groups[2]?.value?.toInt() ?: 0
                acc + (l * r)
            }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
