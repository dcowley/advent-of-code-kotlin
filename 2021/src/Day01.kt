fun main() {
    fun part1(input: List<String>): Int {
        return input.map(String::toInt)
            .zipWithNext()
            .count { (a, b) -> a < b }
    }

    check(part1(readInput("Day01_test")) == 7)
    println(part1(readInput("Day01")))
}
