fun main() {
    fun part1(input: String): Long {
        var i = -1L
        while (true) {
            if ("$input${++i}".md5().startsWith("0".repeat(5))) {
                return i
            }
        }
    }

    check(part1("abcdef") == 609043L)
    check(part1("pqrstuv") == 1048970L)
    part1(readInputText("Day04")).println()
}
