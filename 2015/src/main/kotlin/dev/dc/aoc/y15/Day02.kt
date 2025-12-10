package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    data class Dimensions(val l: Int, val w: Int, val h: Int)

    fun parse(input: List<String>): List<Dimensions> = input.map { line ->
        val values = "\\d+".toRegex().findAll(line)
            .flatMap { it.groupValues }
            .toList()
        Dimensions(values[0].toInt(), values[1].toInt(), values[2].toInt())
    }

    fun part1(input: List<Dimensions>) = input.sumOf { (l, w, h) ->
        val areas = listOf(l * w, l * h, w * h)
        2 * areas.sum() + areas.min()
    }

    fun part2(input: List<Dimensions>) = input.sumOf { (l, w, h) ->
        val sides = listOf(l, w, h).sorted()
        2 * (sides[0] + sides[1]) + (l * w * h)
    }

    check(part1(parse(listOf("2x3x4"))) == 58)
    check(part1(parse(listOf("1x1x10"))) == 43)
    part1(parse(readInput("Day02"))).println()

    check(part2(parse(listOf("2x3x4"))) == 34)
    check(part2(parse(listOf("1x1x10"))) == 14)
    part2(parse(readInput("Day02"))).println()
}
