package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    fun part1(input: String): Int {
        val schemas = input
            .split("\n\n")
            .map {
                val grid = it.split('\n').map(String::toCharArray)
                val rows = grid.size
                val cols = grid.first().size

                List(cols) { row ->
                    List(rows) { col ->
                        grid[col][row]
                    }
                }
            }

        val locks = schemas.filter { it[0][0] == '#' }
            .map { schema ->
                schema.map { it.count('#'::equals) - 1 }
            }
        val keys = schemas.filter { it[0][0] == '.' }
            .map { schema ->
                schema.map { it.count('#'::equals) - 1 }
            }

        return locks.flatMap { lock ->
            keys.map { key ->
                lock.zip(key) { a, b -> a + b }.all { it <= 5 }
            }
        }.count { it }
    }

    check(part1(readInputText("Day25_test")) == 3)
    part1(readInputText("Day25")).println()
}
