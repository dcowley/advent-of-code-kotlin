fun main() {
    val keypad = """
        123
        456
        789
    """.trimIndent().toGrid()

    val directions = mapOf(
        'U' to Direction.NORTH,
        'D' to Direction.SOUTH,
        'L' to Direction.WEST,
        'R' to Direction.EAST
    )

    fun part1(input: List<String>) = buildString {
        var position = 1 to 1

        input.forEach { sequence ->
            sequence.forEach {
                val next = position.move(directions.getValue(it))
                position = next.copy(
                    next.x.coerceIn(0, 2),
                    next.y.coerceIn(0, 2)
                )
            }

            append(keypad[position])
        }
    }

    check(part1(readInput("Day02_test")) == "1985")
    println(part1(readInput("Day02")))
}
