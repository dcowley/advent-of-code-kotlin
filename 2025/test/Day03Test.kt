import kotlin.test.Test
import kotlin.test.assertEquals

class Day03Test {
    private val testData = """
        987654321111111
        811111111111119
        234234234234278
        818181911112111
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(357, Day03.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(3121910778619, Day03.part2(testData))
    }
}
