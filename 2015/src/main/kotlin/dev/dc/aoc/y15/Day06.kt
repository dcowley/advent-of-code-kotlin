package dev.dc.aoc.y15

import dev.dc.aoc.shared.println
import kotlin.math.max

fun main() {
    data class Instruction(val action: String, val start: Pair<Int, Int>, val end: Pair<Int, Int>)

    val regex = "(turn on|turn off|toggle) (\\d+,\\d+) through (\\d+,\\d+)".toRegex()
    fun parse(input: List<String>) = input.map {
        val matches = regex.findAll(it).flatMap(MatchResult::groupValues).toList()
        val action = matches[1]
        val start = matches[2].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }
        val end = matches[3].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }

        Instruction(action, start, end)
    }

    fun part1(instructions: List<Instruction>): Int {
        val lights = mutableMapOf<Pair<Int, Int>, Boolean>()
        instructions.forEach {
            for (x in (it.start.first..it.end.first)) {
                for (y in it.start.second..it.end.second) {
                    when (it.action) {
                        "turn on" -> lights[x to y] = true
                        "turn off" -> lights[x to y] = false
                        "toggle" -> lights[x to y] = !lights.getOrDefault(x to y, false)
                    }
                }
            }
        }
        return lights.count { it.value }
    }

    fun part2(instructions: List<Instruction>): Int {
        val lights = mutableMapOf<Pair<Int, Int>, Int>()
        instructions.forEach {
            for (x in (it.start.first..it.end.first)) {
                for (y in it.start.second..it.end.second) {
                    when (it.action) {
                        "turn on" -> lights[x to y] = lights.getOrDefault(x to y, 0) + 1
                        "turn off" -> lights[x to y] = max(0, lights.getOrDefault(x to y, 0) - 1)
                        "toggle" -> lights[x to y] = lights.getOrDefault(x to y, 0) + 2
                    }
                }
            }
        }
        return lights.values.sum()
    }

    check(part1(parse(listOf("turn on 0,0 through 999,999"))) == 1000000)
    check(part1(parse(listOf("toggle 0,0 through 999,0"))) == 1000)
    check(part1(parse(listOf("turn off 499,499 through 500,500"))) == 0)
    part1(parse(readInput("Day06"))).println()

    check(part2(parse(listOf("turn on 0,0 through 0,0"))) == 1)
    check(part2(parse(listOf("toggle 0,0 through 999,999"))) == 2000000)
    part2(parse(readInput("Day06"))).println()
}
