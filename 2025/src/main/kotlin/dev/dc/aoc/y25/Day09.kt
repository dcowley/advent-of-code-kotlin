package dev.dc.aoc.y25

import dev.dc.aoc.data.getInput
import kotlin.math.absoluteValue
import kotlin.math.max
import kotlin.math.min

data class TilePair(
    private val p: Pair<Long, Long>,
    private val q: Pair<Long, Long>
) {
    val minX = min(p.first, q.first)
    val minY = min(p.second, q.second)
    val maxX = max(p.first, q.first)
    val maxY = max(p.second, q.second)

    val topLeft = minX to minY
    val topRight = maxX to minY
    val bottomLeft = minX to maxY
    val bottomRight = maxX to maxY

    val corners = setOf(topLeft, topRight, bottomLeft, bottomRight)

    override fun equals(other: Any?) = when {
        this === other -> true
        other !is TilePair -> false
        p == other.p && q == other.q || p == other.q && q == other.p -> true
        else -> false
    }

    override fun hashCode(): Int {
        return p.hashCode() + q.hashCode()
    }
}

object Day09 {
    private fun parse(input: String) = input.lines()
        .map { it.split(",") }
        .map { (x, y) -> x.toLong() to y.toLong() }

    fun part1(input: String): Long {
        val locations: List<Pair<Long, Long>> = parse(input)

        val pairs = locations.flatMap { point ->
            (locations - point).map {
                it to point
            }
        }

        return pairs.maxOf { (p1, p2) ->
            ((p1.first - p2.first + 1) * (p1.second - p2.second + 1)).absoluteValue
        }
    }

    data class Point(val x: Int, val y: Int)

    fun part2(input: String): Long {
        val points = parse(input).map { Point(it.first.toInt(), it.second.toInt()) }

        val uniqueX = points.map(Point::x).toSortedSet()
        val uniqueY = points.map(Point::y).toSortedSet()

        val xMap = uniqueX.indices.associateBy { i -> uniqueX.elementAt(i) }
        val yMap = uniqueY.indices.associateBy { i -> uniqueY.elementAt(i) }

        val grid = Array(uniqueY.size) {
            CharArray(uniqueX.size) {
                '.'
            }
        }

        val zPoints = points.map {
            val x = xMap.getValue(it.x)
            val y = yMap.getValue(it.y)

            grid[y][x] = '#'

            Point(x, y)
        }

        for (i in 0 until zPoints.size) {
            val a = zPoints[i]
            val b = zPoints[(i + 1) % zPoints.size]

            if (a.x == b.x) {
                val y0 = min(a.y, b.y)
                val y1 = max(a.y, b.y)

                for (y in y0..y1) {
                    grid[y][a.x] = '#'
                }
            } else if (a.y == b.y) {
                val x0 = min(a.x, b.x)
                val x1 = max(a.x, b.x)

                for (x in x0..x1) {
                    grid[a.y][x] = '#'
                }
            }
        }

        fun insidePoint(): Point {
            for (y in 0 until uniqueY.size) {
                for (x in 0 until uniqueX.size) {
                    if (grid[y][x] != '.') continue

                    var hitsLeft = 0
                    var previous = '.'

                    for (i in x downTo 0) {
                        val current = grid[y][i]
                        if (current != previous) {
                            hitsLeft += 1
                        }
                        previous = current
                    }

                    if (hitsLeft % 2 == 1) {
                        return Point(x, y)
                    }
                }
            }
            error("no inside point")
        }

        fun floodFill() {
            val stack = ArrayDeque(setOf(insidePoint()))
            val directions = setOf(
                0 to 1, 0 to -1, 1 to 0, -1 to 0
            )

            while (stack.isNotEmpty()) {
                val (x, y) = stack.removeLast()
                if (grid[y][x] != '.') continue
                grid[y][x] = '#'

                directions.forEach { (dx, dy) ->
                    val nx = x + dx
                    val ny = y + dy

                    if (ny in 0 until uniqueY.size && nx in 0 until uniqueX.size) {
                        if (grid[ny][nx] == '.') {
                            stack.addLast(Point(nx, ny))
                        }
                    }
                }
            }
        }

        fun isEnclosed(a: Point, b: Point): Boolean {
            val x1 = xMap.getValue(a.x)
            val x2 = xMap.getValue(b.x)

            val y1 = yMap.getValue(a.y)
            val y2 = yMap.getValue(b.y)

            for (x in min(x1, x2)..max(x1, x2)) {
                if (grid[y1][x] == '.' || grid[y2][x] == '.') return false
            }

            for (y in min(y1, y2)..max(y1, y2)) {
                if (grid[y][x1] == '.' || grid[y][x2] == '.') return false
            }

            return true
        }

        floodFill()

        val combinations = points.flatMap { point ->
            (points - point).map {
                point to it
            }
        }

        val max = combinations
            .filter { (a, b) -> isEnclosed(a, b) }
            .maxOf { (a, b) -> ((a.x - b.x).absoluteValue + 1).toLong() * ((a.y - b.y).absoluteValue + 1).toLong() }

        return max
    }
}

suspend fun main() {
    val input = getInput(2025, 9)
    println(Day09.part1(input))
    println(Day09.part2(input))
}
