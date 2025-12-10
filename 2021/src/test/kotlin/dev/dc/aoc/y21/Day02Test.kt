package dev.dc.aoc.y21

import kotlin.test.Test

class Day02Test {
    private val testData = """
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent()

    @Test
    fun part1() {
        assert(Day02.part1(testData) == 150)
    }

    @Test
    fun part2() {
        assert(Day02.part2(testData) == 900)
    }
}
