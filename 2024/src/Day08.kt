fun main() {
    fun part1(input: List<String>): Int {
        val cols = input.first().length
        val rows = input.size

        val map = input.joinToString("")
        val frequencies = map.toSet() - '.'

        val antinodes = mutableSetOf<Pair<Int, Int>>()
        frequencies.forEach { frequency ->
            val indices = map.indices
                .filter { map[it] == frequency }
                .map { it % cols to it / cols }

            indices.forEachIndexed { i, (x0, y0) ->
                indices.drop(i + 1).forEach { (x1, y1) ->
                    val a0 = (2 * x1 - x0 to 2 * y1 - y0)
                    val a1 = (2 * x0 - x1 to 2 * y0 - y1)

                    if (a0.first in 0 until cols && a0.second in 0 until rows) antinodes.add(a0)
                    if (a1.first in 0 until cols && a1.second in 0 until rows) antinodes.add(a1)
                }
            }
        }
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val cols = input.first().length
        val rows = input.size

        val map = input.joinToString("")
        val frequencies = map.toSet() - '.'

        val antinodes = mutableSetOf<Pair<Int, Int>>()
        frequencies.forEach { frequency ->
            val indices = map.indices
                .filter { map[it] == frequency }
                .map { it % cols to it / cols }

            indices.forEachIndexed { i, (x0, y0) ->
                indices.drop(i + 1).forEach { (x1, y1) ->
                    var t = 0
                    do {
                        t += 1
                        val a0 = ((1 - t) * x0 + t * x1 to (1 - t) * y0 + t * y1)
                        val a1 = ((1 - t) * x1 + t * x0 to (1 - t) * y1 + t * y0)

                        if (a0.first in 0 until cols && a0.second in 0 until rows) antinodes.add(a0)
                        if (a1.first in 0 until cols && a1.second in 0 until rows) antinodes.add(a1)
                    } while (a0 in antinodes || a1 in antinodes)
                }
            }
        }
        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}
