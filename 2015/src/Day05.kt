fun main() {
    fun String.hasVowels() = "[aeiou]".toRegex().findAll(this).count() >= 3
    fun String.hasRepeats() = zipWithNext { a, b -> a == b }.any { it }
    fun String.isValid() = "ab|cd|pq|xy".toRegex().findAll(this).count() == 0
    fun String.isNice() = hasVowels() && hasRepeats() && isValid()
    fun String.isNaughty() = !isNice()

    fun part1(input: List<String>) = input.count(String::isNice)

    check("ugknbfddgicrmopn".isNice())
    check("aaa".isNice())
    check("jchzalrnumimnmhp".isNaughty())
    check("haegwjzuvuyypxyu".isNaughty())
    check("dvszwmarrgswjxmb".isNaughty())

    part1(readInput("Day05")).println()
}
