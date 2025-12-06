import kotlin.test.Test
import kotlin.test.assertEquals

class Day06Test {
    private val testData = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
    """.trimIndent()

    @Test
    fun part1() {
        assertEquals(4277556L, Day06.part1(testData))
    }

    @Test
    fun part2() {
        assertEquals(3263827L, Day06.part2(testData))
    }
}
