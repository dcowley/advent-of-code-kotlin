fun main() {
    val regex = "(\\d+)\\s+(\\d+)\\s+(\\d+)".toRegex()

    fun part1(input: List<String>) = input.count {
        val sides = regex.find(it.trim())!!.groupValues
            .takeLast(3)
            .map(String::toInt)
            .sorted()

        sides[0] + sides[1] > sides[2]
    }

    println(part1(readInput("Day03")))
}
