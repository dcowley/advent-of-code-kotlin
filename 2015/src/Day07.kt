fun main() {
    fun parse(input: List<String>): Map<String, String> = input.associate { line ->
        line.substringAfter(" -> ") to line.substringBefore(" -> ")
    }

    fun part1(input: Map<String, String>, wire: String): UShort {
        val memo = mutableMapOf<String, UShort>()

        fun emulate(wire: String): UShort {
            return memo.getOrPut(wire) {
                if (wire.matches("^\\d+$".toRegex())) {
                    wire.toUShort()
                } else {
                    val instruction = input.getValue(wire).split(' ')
                    when {
                        "AND" in instruction -> emulate(instruction[0]) and emulate(instruction[2])
                        "OR" in instruction -> emulate(instruction[0]) or emulate(instruction[2])
                        "LSHIFT" in instruction -> (emulate(instruction[0]).toInt() shl instruction[2].toInt()).toUShort()
                        "RSHIFT" in instruction -> (emulate(instruction[0]).toInt() shr instruction[2].toInt()).toUShort()
                        "NOT" in instruction -> emulate(instruction[1]).inv()
                        else -> emulate(instruction[0])
                    }
                }
            }
        }

        return emulate(wire)
    }

    val testInput = parse(readInput("Day07_test"))
    check(part1(testInput, "d") == 72.toUShort())
    check(part1(testInput, "e") == 507.toUShort())
    check(part1(testInput, "f") == 492.toUShort())
    check(part1(testInput, "g") == 114.toUShort())
    check(part1(testInput, "h") == 65412.toUShort())
    check(part1(testInput, "i") == 65079.toUShort())
    check(part1(testInput, "x") == 123.toUShort())
    check(part1(testInput, "y") == 456.toUShort())
    part1(parse(readInput("Day07")), "a").println()
}
