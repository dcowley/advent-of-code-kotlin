package dev.dc.aoc.y25

import kotlin.test.Test
import kotlin.test.assertEquals

class Day10Test {
    private val testData = """
        [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
        [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
        [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(7, Day10.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(33, Day10.part2(testData))
    }
}
