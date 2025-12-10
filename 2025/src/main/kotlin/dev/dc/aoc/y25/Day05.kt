package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput
import kotlin.math.max

data class Inventory(
    val freshIngredients: List<LongRange>,
    val availableIngredients: List<Long>
)

private fun String.toLongRange() = this
    .split("-")
    .map(String::toLong)
    .let { it.first()..it.last() }

object Day05 {
    private fun parse(input: String) = input.lines().let { lines ->
        Inventory(
            freshIngredients = lines
                .takeWhile(CharSequence::isNotEmpty)
                .map(String::toLongRange),
            availableIngredients = lines
                .takeLastWhile(CharSequence::isNotEmpty)
                .map(String::toLong)
        )
    }

    fun part1(input: String): Int {
        val inventory = parse(input)

        return inventory.availableIngredients.count { id ->
            inventory.freshIngredients.any {
                id in it
            }
        }
    }

    fun part2(input: String): Long {
        val inventory = parse(input)

        val sorted = inventory.freshIngredients.sortedBy(LongRange::first)

        val nonOverlappingRanges = mutableListOf(sorted.first())
        sorted.drop(1).forEach { nextRange ->
            val prevRange = nonOverlappingRanges.last()

            if (nextRange.first <= prevRange.last) {
                nonOverlappingRanges.remove(prevRange)
                nonOverlappingRanges.add(prevRange.first..max(nextRange.last, prevRange.last))
            } else {
                nonOverlappingRanges.add(nextRange)
            }
        }

        return nonOverlappingRanges.sumOf {
            it.last - it.first + 1
        }
    }
}

suspend fun main() {
    val input = getInput(2025, 5)
    println(Day05.part1(input))
    println(Day05.part2(input))
}
