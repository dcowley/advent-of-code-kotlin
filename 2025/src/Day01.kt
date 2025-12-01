import dev.dc.aoc.data.getInput

private data class Rotation(val direction: Char, val clicks: Int, val revolutions: Int) {
    val distance: Int
        get() = when (direction) {
            'L' -> 100 - clicks
            else -> clicks
        }
}

private data class Dial(
    val value: Int = 50,
    val counter: Int = 0
)

object Day01 {
    private fun parse(input: String) = input.lines()

    fun part1(input: String) = parse(input).let {
        var position = 50

        it.count { instruction ->
            val next = instruction.drop(1).toInt()
            when (instruction.first()) {
                'L' -> position -= next
                'R' -> position += next
            }

            position % 100 == 0
        }
    }

    fun part2(input: String): Int {
        val rotations = parse(input)
            .map {
                Rotation(
                    direction = it.first(),
                    clicks = it.drop(1).toInt() % 100,
                    revolutions = it.drop(1).toInt() / 100
                )
            }

        return rotations.fold(Dial()) { dial, rotation ->
            dial.copy(
                value = (dial.value + rotation.distance) % 100,
                counter = dial.counter + rotation.revolutions
                        + if ((dial.value + rotation.distance) % 100 == 0) 1 else 0
                        + when (rotation.direction) {
                            'L' if dial.value in 1 until rotation.clicks -> 1
                            'R' if dial.value + rotation.clicks > 100 -> 1
                            else -> 0
                        }
            )
        }.counter
    }
}

suspend fun main() {
    val input = getInput(2025, 1)
    println(Day01.part1(input))
    println(Day01.part2(input))
}
