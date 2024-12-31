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

    check(part1(">") == 2)
    check(part1("^>v<") == 4)
    check(part1("^v^v^v^v^v") == 2)
    part1(readInputText("Day03")).println()
}
