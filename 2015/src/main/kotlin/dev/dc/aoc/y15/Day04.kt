package dev.dc.aoc.y15

import dev.dc.aoc.shared.md5
import dev.dc.aoc.shared.println

fun main() {
    fun solve(input: String, numZeros: Int): Long {
        val zeros = "0".repeat(numZeros)
        var i = 0L
        while (!"$input$i".md5().startsWith(zeros))
            i++
        return i
    }

    fun part1(input: String) = solve(input, 5)
    fun part2(input: String) = solve(input, 6)

    check(part1("abcdef") == 609043L)
    check(part1("pqrstuv") == 1048970L)
    part1(readInputText("Day04")).println()

    part2(readInputText("Day04")).println()
}
