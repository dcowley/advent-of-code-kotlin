fun main() {
    val directions = mapOf(
        'U' to Direction.NORTH,
        'D' to Direction.SOUTH,
        'L' to Direction.WEST,
        'R' to Direction.EAST
    )

    fun solve(input: List<String>, keypad: Map<Pair<Int, Int>, Char>, initialPosition: Pair<Int, Int>) = buildString {
        var position = initialPosition

        input.forEach { sequence ->
            sequence.forEach {
                val nextPosition = position.move(directions.getValue(it))
                if (nextPosition in keypad.keys) {
                    position = nextPosition
                }
            }

            append(keypad[position])
        }
    }

    fun part1(input: List<String>): String {
        val keypad = """
            123
            456
            789
        """.trimIndent().toGrid()

        return solve(input, keypad, 1 to 1)
    }

    fun part2(input: List<String>): String {
        val keypad = """
            |  1  
            | 234 
            |56789
            | ABC 
            |  D  
        """.trimMargin()
            .toGrid()
            .filterValues { it.isLetterOrDigit() }

        return solve(input, keypad, 0 to 2)
    }

    check(part1(readInput("Day02_test")) == "1985")
    println(part1(readInput("Day02")))

    check(part2(readInput("Day02_test")) == "5DB3")
    println(part2(readInput("Day02")))
}
