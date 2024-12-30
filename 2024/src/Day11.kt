fun main() {
    val cache = mutableMapOf<Int, MutableMap<Long, Long>>()

    fun count(stone: Long, iteration: Int): Long {
        return if (iteration == 0) {
            1
        } else {
            cache
                .getOrPut(iteration, ::mutableMapOf)
                .getOrPut(stone) {
                    val nextIteration = iteration - 1
                    when {
                        stone == 0L -> count(1, nextIteration)

                        "$stone".length % 2 == 0 -> {
                            val stone1 = "$stone".drop("$stone".length / 2).toLong()
                            val stone2 = "$stone".dropLast("$stone".length / 2).toLong()
                            count(stone1, nextIteration) + count(stone2, nextIteration)
                        }

                        else -> count(stone * 2024, nextIteration)
                    }
                }
        }
    }

    fun part1(input: String): Long {
        return input.split(' ')
            .map(String::toLong)
            .sumOf { count(it, 25) }
    }

    fun part2(input: String): Long {
        return input.split(' ')
            .map(String::toLong)
            .sumOf { count(it, 75) }
    }

    check(part1("125 17") == 55312L)

    val input = "30 71441 3784 580926 2 8122942 0 291"
    part1(input).println()
    part2(input).println()
}
