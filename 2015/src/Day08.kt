fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            var data = it.trim('"')
                .replace("\\\"", "\"")
                .replace("\\\\", "\\")

            "\\\\x([a-f0-9]{2})".toRegex().findAll(data).forEach { match ->
                data = data.replace(match.value, Char(match.groupValues[1].toInt(radix = 16)).toString())
            }
            it.length - data.length
        }
    }

    check(part1(readInput("Day08_test")) == 12)
    part1(readInput("Day08")).println()
}
