import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        val regex = "\\d+".toRegex()
        input.forEach {
            val (id1, id2) = regex.findAll(it)
                .map(MatchResult::value)
                .map(String::toInt)
                .toList()

            with(list1) {
                binarySearch(id1)
                    .let { i -> if (i >= 0) i else -(i + 1) }
                    .also { i -> add(i, id1) }
            }
            with(list2) {
                binarySearch(id2)
                    .let { i -> if (i >= 0) i else -(i + 1) }
                    .also { i -> add(i, id2) }
            }
        }

        val totalDistance = list1.zip(list2)
            .fold(initial = 0) { acc, pair ->
                acc + abs(pair.first - pair.second)
            }

        return totalDistance
    }

    fun part2(input: List<String>): Int {
        val locationIds = mutableListOf<Int>()
        val frequencies = mutableMapOf<Int, Int>()

        val regex = "\\d+".toRegex()
        input.forEach {
            val (id1, id2) = regex.findAll(it)
                .map(MatchResult::value)
                .map(String::toInt)
                .toList()

            locationIds += id1
            frequencies[id2] = frequencies.getOrDefault(id2, 0) + 1
        }

        val similarityScore = locationIds.fold(initial = 0) { acc, i ->
            acc + (i * frequencies.getOrDefault(i, 0))
        }
        return similarityScore
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
