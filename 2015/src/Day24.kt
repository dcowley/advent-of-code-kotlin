import kotlin.math.pow

fun main() {
    fun part1(): Int {
        var a = (2 * (3.0.pow(7) + 3.0.pow(4) + 3.0.pow(3)) + 1).toInt()
        var b = 0

        while (a != 1) {
            b += 1
            if (a % 2 == 0) {
                a /= 2
            } else {
                a *= 3
                a += 1
            }
        }
        return b
    }

    fun part2(): Int {
        var a = 113383L
        var b = 0

        while (a != 1L) {
            b += 1
            if (a % 2 == 0L) {
                a /= 2
            } else {
                a *= 3
                a += 1
            }
        }
        return b
    }

    part1().println()
    part2().println()
}
