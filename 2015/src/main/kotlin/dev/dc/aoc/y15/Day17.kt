package dev.dc.aoc.y15

import dev.dc.aoc.shared.powerSet
import dev.dc.aoc.shared.println

fun main() {
    fun parse(name: String) = readInput(name).map(String::toInt)

    fun solve(input: List<Int>, litres: Int) = input.powerSet().filter { it.sum() == litres }

    fun part1(input: List<Int>, litres: Int) = solve(input, litres)

    fun part2(input: List<Int>, litres: Int) = solve(input, litres).let { combos ->
        val minSize = combos.minOf(List<Int>::size)
        combos.filter { it.size == minSize }
    }

    part1(parse("Day17_test"), 25).let { result ->
        check(result.count { it.sorted() == listOf(10, 15) } == 1)
        check(result.count { it.sorted() == listOf(5, 5, 15) } == 1)
        check(result.count { it == listOf(5, 20) } == 2)
    }
    part1(parse("Day17"), 150).size.println()

    check(part2(parse("Day17_test"), 25).size == 3)
    part2(parse("Day17"), 150).size.println()
}
