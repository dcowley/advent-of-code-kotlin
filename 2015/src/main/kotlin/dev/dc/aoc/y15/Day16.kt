package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    val things = """
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
    """.trimIndent()
        .lines()
        .map { it.split(": ") }
        .associate { it[0] to it[1].toInt() }

    val regex = "\\w+ (\\d+): (\\w+): (\\d+), (\\w+): (\\d+), (\\w+): (\\d+)".toRegex()
    fun parse(name: String) = readInput(name).associate { line ->
        val (i, k0, v0, k1, v1, k2, v2) = regex.find(line)!!.destructured
        i.toInt() to mapOf(
            k0 to v0.toInt(),
            k1 to v1.toInt(),
            k2 to v2.toInt()
        )
    }

    fun part1(input: Map<Int, Map<String, Int>>) = input.keys.first { key ->
        val aunt = input.getValue(key)
        things.all { !aunt.containsKey(it.key) || aunt.getValue(it.key) == it.value }
    }

    fun part2(input: Map<Int, Map<String, Int>>) = input.keys.first { key ->
        val aunt = input.getValue(key)
        things.all {
            !aunt.containsKey(it.key) || when (it.key) {
                "cats", "trees" -> aunt.getValue(it.key) > it.value
                "pomeranians", "goldfish" -> aunt.getValue(it.key) < it.value
                else -> aunt.getValue(it.key) == it.value
            }
        }
    }

    part1(parse("Day16")).println()
    part2(parse("Day16")).println()
}
