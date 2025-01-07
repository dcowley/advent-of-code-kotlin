fun main() {
    fun parse(name: String) = readInputText(name).toGrid()

    fun Pair<Int, Int>.neighbours() = (first - 1..first + 1).flatMap { x -> (second - 1..second + 1).map { y -> x to y } } - this

    fun solve(grid: Map<Pair<Int, Int>, Char>, region: Set<Pair<Int, Int>>) = grid.toMutableMap().apply {
        region.forEach { key ->
            val neighboursOn = key.neighbours()
                .filter { it in grid.keys }
                .count { grid[it] == '#' }

            this[key] = when (grid[key]) {
                '#' -> if (neighboursOn in 2..3) '#' else '.'
                else -> if (neighboursOn == 3) '#' else '.'
            }
        }
    }

    fun part1(grid: Map<Pair<Int, Int>, Char>, steps: Int): Map<Pair<Int, Int>, Char> = grid.toMutableMap().apply {
        repeat(steps) {
            putAll(solve(this, this.keys))
        }
    }

    fun part2(grid: Map<Pair<Int, Int>, Char>, steps: Int): Map<Pair<Int, Int>, Char> {
        val maxX = grid.maxOf { it.key.first }
        val maxY = grid.maxOf { it.key.second }
        val corners = setOf(0 to 0, maxX to 0, 0 to maxY, maxX to maxY)

        return grid.toMutableMap().apply {
            putAll(corners.map { it to '#' })
            repeat(steps) {
                putAll(solve(this, keys - corners))
            }
        }
    }

    part1(parse("Day18_test"), 4).let {
        val state = """
            ......
            ......
            ..##..
            ..##..
            ......
            ......
        """.trimIndent().toGrid()
        check(it == state)
    }
    part1(parse("Day18"), 100).values.count { it == '#' }.println()

    part2(parse("Day18_test"), 5).let {
        val state = """
            ##.###
            .##..#
            .##...
            .##...
            #.#...
            ##...#
        """.trimIndent().toGrid()
        check(it == state)
    }
    part2(parse("Day18"), 100).values.count { it == '#' }.println()
}
