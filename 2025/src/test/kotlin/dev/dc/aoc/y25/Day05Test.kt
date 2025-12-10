package dev.dc.aoc.y25

import kotlin.test.Test
import kotlin.test.assertEquals

class Day05Test {
    private val testData = """
        3-5
        10-14
        16-20
        12-18

        1
        5
        8
        11
        17
        32
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(3, Day05.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(14, Day05.part2(testData))
    }
}
