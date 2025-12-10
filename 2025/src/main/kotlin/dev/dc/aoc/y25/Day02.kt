package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput

object Day02 {
    private fun parse(input: String) = input.split(",")
        .map { it.split("-").map(String::toLong) }
        .map { (id1, id2) -> id1..id2 }
        .flatten()

    private fun solve(input: String, matcher: (Long) -> Regex) = parse(input)
        .sumOf {
            if (matcher(it).matches("$it")) it else 0
        }

    fun part1(input: String) = solve(input) {
        "^(.{${"$it".length / 2}})\\1$".toRegex()
    }

    fun part2(input: String) = solve(input) {
        val maxLength = ("$it".length / 2).coerceAtLeast(1)
        "^(.{1,$maxLength})\\1+$".toRegex()
    }
}

suspend fun main() {
    val input = getInput(2025, 2)
    println(Day02.part1(input))
    println(Day02.part2(input))
}
