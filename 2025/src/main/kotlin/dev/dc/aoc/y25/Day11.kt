package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput

object Day11 {
    private fun parse(input: String) = input.lines()
        .associate {
            val (node, neighbours) = it.split(": ")
            node to neighbours.split(" ")
        }

    fun part1(input: String): Long {
        val graph = parse(input)

        fun numPaths(u: String, v: String): Long = when {
            graph.containsKey(u) -> graph.getValue(u)
                .sumOf {
                    when (it) {
                        v -> 1
                        else -> numPaths(it, v)
                    }
                }

            else -> 0
        }

        return numPaths("you", "out")
    }

    fun part2(input: String): Long {
        val graph = parse(input)

        val memo = mutableMapOf<Pair<String, String>, Long>()

        fun numPaths(u: String, v: String, path: Pair<String, String> = u to v): Long = when {
            memo.containsKey(path) -> memo.getValue(path)
            graph.containsKey(u) -> graph.getValue(u)
                .sumOf {
                    when (it) {
                        v -> 1
                        else -> numPaths(it, v)
                    }
                }
                .also { memo[path] = it }

            else -> 0
        }

        val route1 = numPaths("svr", "fft") * numPaths("fft", "dac") * numPaths("dac", "out")
        val route2 = numPaths("svr", "dac") * numPaths("dac", "fft") * numPaths("fft", "out")

        return route1 + route2
    }
}

suspend fun main() {
    val input = getInput(2025, 11)
    println(Day11.part1(input))
    println(Day11.part2(input))
}
