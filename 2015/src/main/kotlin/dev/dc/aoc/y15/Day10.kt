package dev.dc.aoc.y15

import dev.dc.aoc.shared.println

fun main() {
    fun solve(input: String, iterations: Int): String {
        var s = input
        repeat(iterations) {
            s = "(.)\\1*".toRegex().findAll(s)
                .map { "${it.value.length}${it.groupValues.last()}" }
                .joinToString("")
        }
        return s
    }

    check(solve("1", 1) == "11")
    check(solve("1", 2) == "21")
    check(solve("1", 3) == "1211")
    check(solve("1", 4) == "111221")
    check(solve("1", 5) == "312211")
    solve(readInputText("Day10"), 40).length.println()
    solve(readInputText("Day10"), 50).length.println()
}
