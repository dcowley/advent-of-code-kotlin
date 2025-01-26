import java.util.*

fun main() {
    fun parse(name: String) = readInput(name)
        .map(String::toInt)
        .toSet()

    fun combinations(input: IntArray, size: Int) = sequence {
        val result = IntArray(size)
        val stack = LinkedList<Int>().apply {
            push(0)
        }

        while (stack.isNotEmpty()) {
            var inputIndex = stack.pop()
            var resultIndex = stack.size

            while (inputIndex < input.size) {
                result[resultIndex++] = input[inputIndex++]
                stack.push(inputIndex)

                if (resultIndex == size) {
                    yield(result)
                    break
                }
            }
        }
    }

    fun part1(input: Set<Int>): Long {
        val target = input.sum() / 3

        var size = 1
        while (size < input.size) {
            val combinations = combinations(input.toIntArray(), size)
                .filter { it.sum() == target }

            if (combinations.count() > 0) {
                return combinations
                    .map { it.fold(1L) { acc, i -> acc * i } }
                    .min()
            }
            size++
        }

        error("No solution")
    }

    fun part2(input: Set<Int>): Long {
        val target = input.sum() / 4

        var size = 1
        while (size < input.size) {
            val combinations = combinations(input.toIntArray(), size)
                .filter { it.sum() == target }

            if (combinations.count() > 0) {
                return combinations
                    .map { it.fold(1L) { acc, i -> acc * i } }
                    .min()
            }
            size++
        }

        error("No solution")
    }

    check(part1(parse("Day24_test")) == 99L)
    part1(parse("Day24")).println()
    part2(parse("Day24")).println()
}
