fun main() {
    val regex = "(\\d+)\\s+(\\d+)\\s+(\\d+)".toRegex()

    fun part1(input: List<String>) = input.count {
        val sides = regex.find(it.trim())!!.groupValues
            .takeLast(3)
            .map(String::toInt)
            .sorted()

        sides[0] + sides[1] > sides[2]
    }

    fun part2(input: String): Int {
        val numbers = Regex("\\d+")
            .findAll(input)
            .map { it.value.toInt() }
            .toList()

        var count = 0
        repeat(3) { col ->
            val indices = generateSequence(col) { i -> (i + 3).takeIf { it in numbers.indices } }

            count += numbers.slice(indices.asIterable())
                .windowed(3, 3)
                .map { it.sorted() }
                .count { it[0] + it[1] > it[2] }
        }

        return count
    }

    println(part1(readInput("Day03")))

    check(part2(readInputText("Day03_test")) == 6)
    println(part2(readInputText("Day03")))
}
