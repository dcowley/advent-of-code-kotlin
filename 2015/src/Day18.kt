fun main() {
    fun parse(name: String) = readInputText(name).toGrid()

    fun Pair<Int, Int>.neighbours() = (first - 1..first + 1).flatMap { x -> (second - 1..second + 1).map { y -> x to y } } - this

    fun part1(grid: Map<Pair<Int, Int>, Char>, steps: Int): Map<Pair<Int, Int>, Char> {
        var state = grid
        repeat(steps) {
            state = state.toMutableMap().apply {
                state.keys.forEach { key ->
                    val neighboursOn = key.neighbours()
                        .filter { it in state.keys }
                        .count { state[it] == '#' }
                    if (state[key] == '#') {
                        this[key] = if (neighboursOn in 2..3) '#' else '.'
                    } else {
                        this[key] = if (neighboursOn == 3) '#' else '.'
                    }
                }
            }
        }
        return state
    }

    fun part2(grid: Map<Pair<Int, Int>, Char>, steps: Int): Map<Pair<Int, Int>, Char> {
        val maxX = grid.maxOf { it.key.first }
        val maxY = grid.maxOf { it.key.second }
        val corners = setOf(0 to 0, maxX to 0, 0 to maxY, maxX to maxY)

        var state: Map<Pair<Int, Int>, Char> = grid.toMutableMap().apply {
            corners.forEach { this[it] = '#' }
        }
        repeat(steps) {
            state = state.toMutableMap().apply {
                (state.keys - corners).forEach { key ->
                    val neighboursOn = key.neighbours()
                        .filter { it in state.keys }
                        .count { state[it] == '#' }
                    if (state[key] == '#') {
                        this[key] = if (neighboursOn in 2..3) '#' else '.'
                    } else {
                        this[key] = if (neighboursOn == 3) '#' else '.'
                    }
                }
            }
        }
        return state
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

fun Map<Pair<Int, Int>, Char>.pp() {
    val cols = keys.minOf { it.first }..keys.maxOf { it.first }
    val rows = keys.minOf { it.second }..keys.maxOf { it.second }

    for (row in rows) {
        for (col in cols) {
            print(this[col to row])
        }
        print('\n')
    }
}
