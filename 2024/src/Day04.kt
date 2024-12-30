fun main() {
    fun part1(input: List<String>): Int {
        val padding = "...."
        val cols = input.first().length + padding.length
        val text = input.joinToString(separator = padding)

        val indices = listOf(
            text.indices.map { it until it + 4 }, // horizontal
            text.indices.map { it until it + 4 * cols step cols }, // vertical
            text.indices.map { it until it + 4 * (cols + 1) step cols + 1 }, // forward diagonal
            text.indices.map { it until it + 4 * (cols - 1) step cols - 1 }, // backward diagonal
        ).flatten()

        val regex = "XMAS|SAMX".toRegex()
        val count = indices
            .filterNot { it.last >= text.length }
            .map { text.slice(it) }
            .count { regex.containsMatchIn(it) }

        return count
    }

    fun part2(input: List<String>): Int {
        val columns = input.first().length
        val text = input.joinToString(separator = "")

        val indices = text.indices
            .filter { it > columns && it % columns in 1 until columns - 1 && it < text.length - columns }
            .map {
                Pair(
                    (it - columns - 1..it + (columns + 1) step columns + 1), // forward diagonal
                    (it - columns + 1..it + (columns - 1) step columns - 1) // backward diagonal
                )
            }

        val regex = "MAS|SAM".toRegex()
        val count = indices
            .count { (forward, backward) ->
                regex.containsMatchIn(text.slice(forward)) && regex.containsMatchIn(text.slice(backward))
            }

        return count
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
