fun main() {
    fun part1(input: List<String>): Long {
        val initialSecretNumbers = input.map { it.toLong() }
        return initialSecretNumbers.sumOf { num ->
            var next = num
            repeat(2000) {
                next = next
                    .let { ((it shl 6) xor it) % 16777216 }
                    .let { ((it shr 5) xor it) % 16777216 }
                    .let { ((it shl 11) xor it) % 16777216 }
            }
            next
        }
    }

    fun part2(input: List<String>): Long {
        val initialSecretNumbers = input.map { it.toLong() }
        val sequences = mutableSetOf<String>()

        val secrets = initialSecretNumbers.map { secret ->
            generateSequence(secret) { next ->
                next.let { ((it shl 6) xor it) % 16777216 }
                    .let { ((it shr 5) xor it) % 16777216 }
                    .let { ((it shl 11) xor it) % 16777216 }
            }.take(2001)
        }

        fun MutableMap<List<Long>, Long>.combine(oMap: Map<List<Long>, Long>) = apply {
            oMap.forEach { (key, value) -> merge(key, value, Long::plus) }
        }

        return secrets.map {
            it.map { it % 10 }
                .zipWithNext { a, b -> b to b - a }
                .windowed(4) { (a, b, c, d) -> listOf(a.second, b.second, c.second, d.second) to d.first }
                .groupingBy { (key, value) -> key }
                .fold({ _, value -> value.second }, { _, a, _ -> a })
        }.fold(mutableMapOf(), MutableMap<List<Long>, Long>::combine)
            .values
            .max()
//
//        val prices = initialSecretNumbers.map { num ->
//
//
//            val prices = mutableListOf<Pair<Int, String>>()
//            var next = num
//            repeat(2000) { i ->
//                next = next
//                    .let { ((it shl 6) xor it) % 16777216 }
//                    .let { ((it shr 5) xor it) % 16777216 }
//                    .let { ((it shl 11) xor it) % 16777216 }
//
//                prices.windowed(4, 1)
//
//                if (i > 4) {
//                    val change = prices
//                        .subList(i - 5, i)
//                        .zipWithNext { a, b -> b.first - a.first }
//                        .joinToString(",")
//
//                    sequences += change
//                    prices += (next % 10).toInt() to change
//                } else {
//                    prices += (next % 10).toInt() to ""
//                }
//            }
//            prices
//        }
//
//        val maxSequence = sequences.maxBy { sequence ->
//            prices.sumOf { price ->
//                price.find { it.second == sequence }?.first ?: 0
//            }
//        }
//
//        return -1L
    }

    check(part1(readInput("Day22_test")) == 37327623L)
    part1(readInput("Day22")).println()
    part2(readInput("Day22")).println()
}
