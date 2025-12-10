package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun part1(input: List<String>): Int {
        val text = input.joinToString("\n")

        val rules = "(\\d+)\\|(\\d+)".toRegex()
            .findAll(text)
            .map { it.groups[1]!!.value.toInt() to it.groups[2]!!.value.toInt() }
            .toMutableList()

        val updates = "\\d+(?:,+\\d+)+".toRegex()
            .findAll(text)
            .map {
                it.groups.first()!!.value.split(',').map(String::toInt).toIntArray()
            }
            .toList()

        val sum = updates.fold(0) { acc, update ->
            val isValid = update.all { i ->
                rules
                    .filter { it.first == i }
                    .none { it.second in update.slice(0..update.indexOf(i)) }
            }
            if (isValid) {
                acc + update[update.size / 2]
            } else {
                acc
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val text = input.joinToString("\n")

        val rules = "(\\d+)\\|(\\d+)".toRegex()
            .findAll(text)
            .map { it.groups[1]!!.value.toInt() to it.groups[2]!!.value.toInt() }
            .toMutableList()

        val updates = "\\d+(?:,+\\d+)+".toRegex()
            .findAll(text)
            .map {
                it.groups.first()!!.value.split(',').map(String::toInt).toIntArray()
            }
            .toList()

        val sum = updates.fold(0) { acc, update ->
            val isValid = update.all { i ->
                rules
                    .filter { it.first == i }
                    .none { it.second in update.slice(0..update.indexOf(i)) }
            }
            if (!isValid) {
                val sorted = update.sortedWith { l, r ->
                    val rulesForPage = rules.filter { it.first == l }
                    if (rulesForPage.any { it.second == r }) {
                        -1
                    } else {
                        1
                    }
                }
                acc + sorted[sorted.size / 2]
            } else {
                acc
            }
        }
        return sum
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
