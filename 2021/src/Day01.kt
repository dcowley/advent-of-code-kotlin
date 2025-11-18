fun main() {
    fun parse(input: List<String>) = input.map(String::toInt)
    fun part1(input: List<Int>): Int {
        return input
            .zipWithNext()
            .count { (a, b) -> a < b }
    }

    fun part2(input: List<Int>): Int {
        return input
            .windowed(3)
            .zipWithNext()
            .count { (l, r) -> l.sum() < r.sum() }
    }

    check(part1(parse(readInput("Day01_test"))) == 7)
    check(part2(parse(readInput("Day01_test"))) == 5)
    println(part1(parse(readInput("Day01"))))
    println(part2(parse(readInput("Day01"))))
}
