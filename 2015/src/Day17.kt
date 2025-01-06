fun main() {
    fun parse(name: String) = readInput(name).map(String::toInt)

    fun part1(input: List<Int>, litres: Int): List<Collection<Int>> {
        val powerSet = input.powerSet()
        val filter = powerSet.filter { it.sum() == litres }
        return filter
    }

    part1(parse("Day17_test"), 25).let { result ->
        check(result.count { it.sorted() == listOf(10, 15) } == 1)
        check(result.count { it.sorted() == listOf(5, 5, 15) } == 1)
        check(result.count { it == listOf(5, 20) } == 2)
    }

    part1(parse("Day17"), 150).size.println()
}
