package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun part1(input: String): Int {
        return input.split("\n\n").sumOf {
            val challenge = it.split('\n')
            val buttonA = Button(challenge[0])
            val buttonB = Button(challenge[1])
            val prize = Prize(challenge[2])

            var score = 0
            repeat(100) { num ->
                val x = (prize.x - buttonA.x * num) / buttonB.x.toDouble()
                val y = (prize.y - buttonA.y * num) / buttonB.y.toDouble()
                if (x == y && x.rem(1) == 0.0 && y.rem(1) == 0.0) {
                    score += 3 * num + x.toInt()
                }
            }
            score
        }
    }

    fun part2(input: String): Long {
        return input.split("\n\n").sumOf {
            val challenge = it.split('\n')

            val (ax, ay) = Button(challenge[0])
            val (bx, by) = Button(challenge[1])
            val (px, py) = Prize(challenge[2]) + 10000000000000L

            // solve using Cramer's rule
            val a = (px * by - py * bx) / (ax * by - ay * bx)
            val b = (py - ay * a) / by

            if (a * ax + b * bx == px && a * ay + b * by == py) {
                3 * a + b
            } else 0L
        }
    }

    check(part1(readInputText("Day13_test1")) == 480)

    val input = readInputText("Day13")
    part1(input).println()
    part2(input).println()
}

class Button(private val input: String) {
    val x: Long
        get() = "X\\+(\\d+)".toRegex().findAll(input).first().groups.last()!!.value.toLong()
    val y: Long
        get() = "Y\\+(\\d+)".toRegex().findAll(input).first().groups.last()!!.value.toLong()

    operator fun component1() = x
    operator fun component2() = y
}

data class Prize(val x: Long, val y: Long) {
    constructor(input: String) : this(
        "X=(\\d+)".toRegex().findAll(input).first().groups.last()!!.value.toLong(),
        "Y=(\\d+)".toRegex().findAll(input).first().groups.last()!!.value.toLong()
    )

    operator fun plus(translation: Long) = copy(
        x = x + translation,
        y = y + translation
    )
}
