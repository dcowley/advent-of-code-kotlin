import kotlin.test.Test

class Day01Test {
    private val testData = """
        199
        200
        208
        210
        200
        207
        240
        269
        260
        263
    """.trimIndent()

    @Test
    fun part1() {
        assert(Day01.part1(testData) == 7)
    }

    @Test
    fun part2() {
        assert(Day01.part2(testData) == 5)
    }
}
