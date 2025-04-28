fun main() {
    val directions = mapOf(
        'U' to Direction.NORTH,
        'D' to Direction.SOUTH,
        'L' to Direction.WEST,
        'R' to Direction.EAST
    )

    fun part1(input: List<String>) = buildString {
        val keypad = """
            123
            456
            789
        """.trimIndent().toGrid()

        var position = 1 to 1

        input.forEach { sequence ->
            sequence.forEach {
                val next = position.move(directions.getValue(it))
                if (next in keypad.keys) {
                    position = next
                }
            }

            append(keypad[position])
        }
    }

    fun part2(input: List<String>) = buildString {
        val keypad = """
            |  1  
            | 234 
            |56789
            | ABC 
            |  D  
        """.trimMargin()
            .toGrid()
            .filterValues { it.isLetterOrDigit() }

        var position = 0 to 2

        input.forEach { sequence ->
            sequence.forEach {
                val next = position.move(directions.getValue(it))
                if (next in keypad.keys) {
                    position = next
                }
            }

            append(keypad[position])
        }
    }

    check(part1(readInput("Day02_test")) == "1985")
    println(part1(readInput("Day02")))

    check(part2(readInput("Day02_test")) == "5DB3")
    println(part2(readInput("Day02")))
}
