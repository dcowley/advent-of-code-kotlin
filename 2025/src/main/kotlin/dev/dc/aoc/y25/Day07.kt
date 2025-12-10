package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput
import dev.dc.aoc.shared.toGrid

object Day07 {
    fun part1(input: String): Int {
        val manifold = input.toGrid()
        val start = input.indexOf('S') to 0
        val splitters = mutableSetOf<Pair<Int, Int>>()

        fun dfs(point: Pair<Int, Int>) {
            if (point in splitters || point !in manifold) return

            when (manifold[point]) {
                '^' -> {
                    splitters += point
                    dfs(point.first - 1 to point.second)
                    dfs(point.first + 1 to point.second)
                }

                else -> dfs(point.first to point.second + 1)
            }
        }

        dfs(start)

        return splitters.size
    }

    fun part2(input: String): Long {
        val manifold = input.toGrid()
        val start = input.indexOf('S') to 0
        val memo = mutableMapOf<Pair<Int, Int>, Long>()

        fun dfs(point: Pair<Int, Int>): Long {
            if (point !in manifold) return 1

            return memo.getOrPut(point) {
                when (manifold[point]) {
                    '^' -> dfs(point.first - 1 to point.second) + dfs(point.first + 1 to point.second)
                    else -> dfs(point.first to point.second + 1)
                }
            }
        }

        return dfs(start)
    }
}

suspend fun main() {
    val input = getInput(2025, 7)
    println(Day07.part1(input))
    println(Day07.part2(input))
}
