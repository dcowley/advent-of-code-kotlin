fun main() {
    val regex = "p=(\\d+),(\\d+) v=(-*\\d+),(-*\\d+)".toRegex()

    fun part1(input: List<String>, size: Pair<Int, Int>, seconds: Int = 100): Long {
        val (w, h) = size
        val (q1x, q1y) = (0 until w / 2) to (0 until h / 2)
        val (q3x, q3y) = (0 until w / 2) to (h / 2 + 1 until h)
        val (q2x, q2y) = (w / 2 + 1 until w) to (0 until h / 2)
        val (q4x, q4y) = (w / 2 + 1 until w) to (h / 2 + 1 until h)

        val robots = regex.findAll(input.joinToString())
            .map { it.groupValues }
            .map { it.takeLast(4).map(String::toInt) }

        val quadrants = hashMapOf<Int, Long>()
        robots.forEach { (px, py, vx, vy) ->
            val x = ((px + seconds * vx) % w).let { if (it < 0) it + w else it }
            val y = ((py + seconds * vy) % h).let { if (it < 0) it + h else it }

            val quadrant = when {
                x in q1x && y in q1y -> 1
                x in q2x && y in q2y -> 2
                x in q3x && y in q3y -> 3
                x in q4x && y in q4y -> 4
                else -> Int.MIN_VALUE
            }
            if (quadrant > 0) {
                quadrants[quadrant] = quadrants.getOrDefault(quadrant, 0) + 1L
            }
        }

        return quadrants.values.reduce { acc, num ->
            acc * num
        }
    }

    fun part2(input: List<String>, size: Pair<Int, Int>): Int {
        val (w, h) = size

        val robots = regex.findAll(input.joinToString())
            .map { it.groupValues }
            .map { it.takeLast(4).map(String::toInt) }

        var seconds = 1
        while (seconds <= w * h) { // robots return to starting points after (w x h) seconds
            val visited = hashSetOf<Pair<Int, Int>>()
            robots.forEach { (px, py, vx, vy) ->
                val x = ((px + seconds * vx) % w).let { if (it < 0) it + w else it }
                val y = ((py + seconds * vy) % h).let { if (it < 0) it + h else it }

                visited.add(x to y)
            }

            if (visited.size == robots.count()) {
                break
            }
            seconds += 1
        }

        return seconds
    }

    check(part1(readInput("Day14_test1"), size = 11 to 7) == 12L)

    val input = readInput("Day14")
    part1(input, size = 101 to 103).println()
    part2(input, size = 101 to 103).println()
}
