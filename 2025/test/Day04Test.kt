import kotlin.test.Test
import kotlin.test.assertEquals

class Day04Test {
    private val testData = """
        ..@@.@@@@.
        @@@.@.@.@@
        @@@@@.@.@@
        @.@@@@..@.
        @@.@@@@.@@
        .@@@@@@@.@
        .@.@.@.@@@
        @.@@@.@@@@
        .@@@@@@@@.
        @.@.@@@.@.
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(13, Day04.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(43, Day04.part2(testData))
    }
}
