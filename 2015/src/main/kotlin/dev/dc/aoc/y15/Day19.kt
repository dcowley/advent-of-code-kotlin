package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    val regex = "(\\w+) => (\\w+)".toRegex()
    fun parse(name: String) = readInputText(name).let { input ->
        val molecule = input.substringAfter("\n\n")
        val replacements = input.substringBefore("\n\n")

        molecule to regex.findAll(replacements).map {
            it.groupValues[1] to it.groupValues[2]
        }
    }

    fun part1(molecule: String, replacements: Sequence<Pair<String, String>>) = molecule.indices.flatMap { i ->
        replacements
            .filter { molecule.substring(i).startsWith(it.first) }
            .map {
                molecule.replaceRange(i until i + it.first.length, it.second)
            }
    }.toSet().size

    // https://www.reddit.com/r/adventofcode/comments/3xflz8/comment/cy4etju/
    fun part2(molecule: String): Int {
        val elements = molecule.count { it.isUpperCase() }
        val brackets = molecule.windowed(2).count { it == "Rn" || it == "Ar" }
        val commas = molecule.count { it == 'Y' }
        return elements - brackets - 2 * commas - 1
    }

    check(parse("Day19_test1").let { part1(it.first, it.second) } == 4)
    parse("Day19").let { part1(it.first, it.second) }.println()
    part2(readInput("Day19").last()).println()
}
