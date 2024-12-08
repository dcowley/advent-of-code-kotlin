fun main() {
    fun part1(input: List<String>): Long {
        val results = input.map { it.substringBefore(':').toLong() }
        val numbers = input.map { it.substringAfter(": ").split(' ').map(String::toLong) }

        var solution = 0L
        numbers.forEachIndexed { i, ints ->
            val operatorPermutations = permutations(charArrayOf('+', '*'), ints.size - 1)
            val hasSolution = operatorPermutations.any { permutation ->
                val result = ints.reduceIndexed { index, acc, next ->
                    when (permutation[index - 1]) {
                        '+' -> acc + next
                        '*' -> acc * next
                        else -> throw UnsupportedOperationException("Invalid operator ${permutation[index]}")
                    }
                }
                results[i] == result
            }
            if (hasSolution) {
                solution += results[i]
            }
        }
        return solution
    }

    fun part2(input: List<String>): Long {
        val results = input.map { it.substringBefore(':').toLong() }
        val numbers = input.map { it.substringAfter(": ").split(' ').map(String::toLong) }

        var solution = 0L
        numbers.forEachIndexed { i, ints ->
            val operatorPermutations = permutations(charArrayOf('+', '*', '|'), ints.size - 1)
            val hasSolution = operatorPermutations.any { permutation ->
                val result = ints.reduceIndexed { index, acc, next ->
                    when (permutation[index - 1]) {
                        '+' -> acc + next
                        '*' -> acc * next
                        '|' -> "$acc$next".toLong()
                        else -> throw UnsupportedOperationException("Invalid operator ${permutation[index]}")
                    }
                }
                results[i] == result
            }
            if (hasSolution) {
                solution += results[i]
            }
        }
        return solution
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}

fun permutations(chars: CharArray, length: Int): List<CharArray> {
    return when (length) {
        1 -> chars.map { charArrayOf(it) }
        else -> chars.flatMap { char ->
            permutations(chars, length - 1).map { it + char }
        }
    }
}
