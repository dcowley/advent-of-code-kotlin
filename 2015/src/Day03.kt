fun main() {
    fun part1(input: String): Int {
        val visited = mutableSetOf(0 to 0)

        var position = visited.first()
        input.forEach {
            when (it) {
                '>' -> position = position.first + 1 to position.second
                'v' -> position = position.first to position.second + 1
                '<' -> position = position.first - 1 to position.second
                '^' -> position = position.first to position.second - 1
            }
            visited += position
        }
        return visited.size
    }

    fun part2(input: String): Int {
        val visited = mutableSetOf(0 to 0)

        var santaPosition = visited.first()
        var robotPosition = visited.first()
        input.forEachIndexed { i, it ->
            var position = if (i % 2 == 0) santaPosition else robotPosition
            when (it) {
                '>' -> position = position.first + 1 to position.second
                'v' -> position = position.first to position.second + 1
                '<' -> position = position.first - 1 to position.second
                '^' -> position = position.first to position.second - 1
            }
            visited += position

            if (i % 2 == 0) {
                santaPosition = position
            } else {
                robotPosition = position
            }
        }
        return visited.size
    }

    check(part1(">") == 2)
    check(part1("^>v<") == 4)
    check(part1("^v^v^v^v^v") == 2)
    part1(readInputText("Day03")).println()

    check(part2("^v") == 3)
    check(part2("^>v<") == 3)
    check(part2("^v^v^v^v^v") == 11)
    part2(readInputText("Day03")).println()
}
