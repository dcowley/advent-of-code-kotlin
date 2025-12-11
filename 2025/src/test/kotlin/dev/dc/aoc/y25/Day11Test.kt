package dev.dc.aoc.y25

import kotlin.test.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun part1() {
        val testData = """
        aaa: you hhh
        you: bbb ccc
        bbb: ddd eee
        ccc: ddd eee fff
        ddd: ggg
        eee: out
        fff: out
        ggg: out
        hhh: ccc fff iii
        iii: out
    """.trimIndent()

        assertEquals(5, Day11.part1(testData))
    }

    @Test
    fun part2() {
        val testData = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """.trimIndent()

        assertEquals(2, Day11.part2(testData))
    }
}
