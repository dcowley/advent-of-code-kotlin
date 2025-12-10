package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun part1(input: List<String>): Int {
        val patterns = input.first().split(", ")
        val designs = input.drop(2)
        val cache = mutableMapOf<String, Boolean>()

        fun check(patterns: List<String>, design: String): Boolean =
            cache.getOrPut(design) {
                when {
                    design.isEmpty() -> true
                    else -> {
                        patterns.any {
                            design.startsWith(it) && check(patterns, design.drop(it.length))
                        }
                    }
                }
            }

        return designs.count { check(patterns, it) }
    }

    fun part2(input: List<String>): Long {
        val patterns = input.first().split(", ").toSet()
        val designs = input.drop(2)

        val cache = mutableMapOf<String, Long>()
        fun count(patterns: Set<String>, design: String): Long = cache.getOrPut(design) {
            when {
                design.isEmpty() -> 1
                else -> {
                    patterns.filter { design.startsWith(it) }.sumOf {
                        count(patterns, design.drop(it.length))
                    }
                }
            }
        }

        return designs.sumOf { count(patterns, it) }
    }

    check(part1(readInput("Day19_test")) == 6)
    check(part2(readInput("Day19_test")) == 16L)
    part1(readInput("Day19")).println()
    part2(readInput("Day19")).println()
}
