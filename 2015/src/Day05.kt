fun main() {
    fun part1(input: List<String>): Int {
        fun String.hasVowels() = "[aeiou]".toRegex().findAll(this).count() >= 3
        fun String.hasRepeats() = zipWithNext { a, b -> a == b }.any { it }
        fun String.isValid() = "ab|cd|pq|xy".toRegex().findAll(this).count() == 0
        fun String.isNice() = hasVowels() && hasRepeats() && isValid()

        return input.count(String::isNice)
    }

    fun part2(input: List<String>): Int {
        return input.count {
            val repeats = it.zipWithNext { a, b -> "$a$b".toRegex().findAll(it).count() > 1 }.any { it }
            val surrounds = it.zipWithNext { a, b -> "$a$b$a".toRegex().findAll(it).count() >= 1 }.any { it }
            repeats && surrounds
        }
    }

    check(part1(listOf("ugknbfddgicrmopn")) == 1)
    check(part1(listOf("aaa")) == 1)
    check(part1(listOf("jchzalrnumimnmhp")) == 0)
    check(part1(listOf("haegwjzuvuyypxyu")) == 0)
    check(part1(listOf("dvszwmarrgswjxmb")) == 0)
    part1(readInput("Day05")).println()

    check(part2(listOf("qjhvhtzxzqqjkmpb")) == 1)
    check(part2(listOf("xxyxx")) == 1)
    check(part2(listOf("uurcxstgmygtbstg")) == 0)
    check(part2(listOf("ieodomkazucvgmuy")) == 0)
    part2(readInput("Day05")).println()
}
