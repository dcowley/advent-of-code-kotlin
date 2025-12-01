import kotlin.test.Test

class Day01Test {
    private val testData = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
    """.trimIndent()

    @Test
    fun part1() {
        assert(Day01.part1(testData) == 3)
    }

    @Test
    fun part2() {
        assert(Day01.part2(testData) == 0)
    }
}
