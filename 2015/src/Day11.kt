fun main() {
    fun part1(input: String): String {
        val alphabet = "abcdefghijklmnopqrstuvwxyz"
        val result = input.toCharArray()

        fun String.hasStraight() = alphabet.windowedSequence(3, 1).any { it in this }
        fun String.hasDoubles(num: Int = 2) = alphabet.count { "$it$it" in this } >= num
        fun String.hasIllegalChars() = contains("[iol]".toRegex())

        do {
            for (i in result.indices.reversed()) {
                val index = alphabet.indexOf(result[i])
                if (index == alphabet.lastIndex) {
                    result[i] = alphabet[0]
                } else {
                    result[i] = alphabet[index + 1]
                    break
                }
            }
        } while (String(result).let { it.hasIllegalChars() || !(it.hasStraight() && it.hasDoubles()) })

        return String(result)
    }

    check(part1("abcdefgh") == "abcdffaa")
    check(part1("ghijklmn") == "ghjaabcc")

    val password = part1(readInputText("Day11")).also(::println)
    part1(password).println()
}
