package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput

object Day06 {
    fun part1(input: String): Long {
        val lines = input.lines().map { line ->
            line.trim().split("\\s+".toRegex())
        }

        return lines.first().indices.sumOf { col ->
            val operator = lines.last()[col]
            val initialValue = if (operator == "*") 1L else 0L

            lines.dropLast(1).indices.fold(initialValue) { acc, row ->
                when (operator) {
                    "*" -> acc * lines[row][col].toLong()
                    else -> acc + lines[row][col].toLong()
                }
            }
        }
    }

    fun part2(input: String): Long {
        val lines = input.lines()
        val operatorsLine = lines.last()
        val columnIndices = operatorsLine.let { line ->
            (line.indices.filterNot { line[it] == ' ' } + (line.length + 1))
                .zipWithNext { first, last -> first until (last - 1) }
        }

        return columnIndices.sumOf {
            val operator = operatorsLine[it.first]

            val numbers = it.map { i ->
                lines.dropLast(1)
                    .fold("") { acc, line ->
                        acc + line[i]
                    }
                    .trim()
                    .toLong()
            }

            val initialValue = if (operator == '*') 1L else 0L
            numbers.fold(initialValue) { acc, num ->
                when (operator) {
                    '*' -> acc * num
                    else -> acc + num
                }
            }
        }
    }
}

suspend fun main() {
    val input = getInput(2025, 6)
    println(Day06.part1(input))
    println(Day06.part2(input))
}
