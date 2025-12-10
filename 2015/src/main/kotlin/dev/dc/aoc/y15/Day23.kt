package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    fun solve(initial: Long): Int {
        var a = initial
        var b = 0

        while (true) {
            b += 1
            a = if (a % 2 == 0L) a / 2 else 3 * a + 1
            if (a == 1L) return b
        }
    }

    fun part1() = solve(4591L)
    fun part2() = solve(113383L)

    part1().println()
    part2().println()
}
