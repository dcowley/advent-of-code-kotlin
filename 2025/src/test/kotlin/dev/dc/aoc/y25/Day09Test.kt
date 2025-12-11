package dev.dc.aoc.y25

import kotlin.test.Test
import kotlin.test.assertEquals

class Day09Test {
    private val testData = """
        7,1
        11,1
        11,7
        9,7
        9,5
        2,5
        2,3
        7,3
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(50, Day09.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(24, Day09.part2(testData))
    }
}
