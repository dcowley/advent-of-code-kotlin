package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    val regex = "\\w+: \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+)".toRegex()
    fun parse(name: String) = readInput(name).map { line ->
        regex.find(line)!!.destructured.toList().map(String::toInt)
    }

    fun permutations(total: Int, num: Int): List<List<Int>> = when {
        num == 0 -> emptyList()
        num == 1 -> listOf(listOf(total))
        else -> {
            buildList {
                repeat(total + 1) { i ->
                    permutations(total - i, num - 1).forEach {
                        add(listOf(i) + it)
                    }
                }
            }
        }
    }

    fun solve(input: List<List<Int>>, teaspoons: Int = 100) = permutations(teaspoons, input.size).map {
        val totals = input.foldIndexed(emptyList<Int>()) { ingredient, acc, qualities ->
            qualities.mapIndexed { index, quality ->
                quality * it[ingredient] + acc.getOrElse(index) { 0 }
            }
        }

        totals.last() to totals.dropLast(1).reduce { acc, total ->
            acc.coerceAtLeast(0) * total
        }
    }

    fun part1(input: List<List<Int>>, teaspoons: Int = 100) = solve(input, teaspoons).maxOf { it.second }

    fun part2(input: List<List<Int>>, teaspoons: Int = 100) = solve(input, teaspoons)
        .filter { it.first == 500 }
        .maxOf { it.second }

    check(part1(parse("Day15_test")) == 62_842_880)
    part1(parse("Day15")).println()

    check(part2(parse("Day15_test")) == 57_600_000)
    part2(parse("Day15")).println()
}
