import kotlin.math.sqrt

fun main() {
    fun divisors(num: Int) = buildSet {
        for (i in 1..sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) {
                add(i)
                add(num / i)
            }
        }
    }

    fun part1(target: Int): Int {
        var num = 1
        while (true) {
            if (divisors(num).sumOf { 10 * it } >= target) {
                return num
            }
            num++
        }
    }

    check(part1(10) == 1)
    check(part1(30) == 2)
    check(part1(40) == 3)
    check(part1(70) == 4)

    part1(readInputText("Day20").toInt()).println()
}
