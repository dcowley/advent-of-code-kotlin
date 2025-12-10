package dev.dc.aoc.y16

fun main() {
    fun part1(input: String): Int {
        val sides = "\\d+".toRegex().findAll(input)
            .map { it.value.toInt() }
            .windowed(3, 3)
            .map { it.sorted() }

        return sides.count { it[0] + it[1] > it[2] }
    }

    fun part2(input: String): Int {
        val numbers = "\\d+".toRegex()
            .findAll(input)
            .map { it.value.toInt() }
            .toList()

        var count = 0
        repeat(3) { col ->
            val indices = generateSequence(col) { i -> (i + 3).takeIf { it in numbers.indices } }

            count += numbers.slice(indices.asIterable())
                .windowed(3, 3)
                .map { it.sorted() }
                .count { it[0] + it[1] > it[2] }
        }

        return count
    }

    println(part1(readInputText("Day03")))

    check(part2(readInputText("Day03_test")) == 6)
    println(part2(readInputText("Day03")))
}
