import kotlin.math.max

fun main() {
    fun part1(input: List<String>): Int {
        val lights = buildMap {
            repeat(1000) { row ->
                repeat(1000) { col ->
                    this[col to row] = false
                }
            }
        }.toMutableMap()

        val regex = "(turn on|turn off|toggle) (\\d+,\\d+) through (\\d+,\\d+)".toRegex()
        input.forEach {
            val matches = regex.findAll(it).flatMap(MatchResult::groupValues).toList()
            val instruction = matches[1]
            val startNode = matches[2].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }
            val endNode = matches[3].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }

            for (x in (startNode.first..endNode.first)) {
                for (y in startNode.second..endNode.second) {
                    when (instruction) {
                        "turn on" -> lights[x to y] = true
                        "turn off" -> lights[x to y] = false
                        "toggle" -> lights[x to y] = !lights.getValue(x to y)
                    }
                }
            }
        }
        return lights.count { it.value }
    }

    fun part2(input: List<String>): Int {
        val lights = buildMap {
            repeat(1000) { row ->
                repeat(1000) { col ->
                    this[col to row] = 0
                }
            }
        }.toMutableMap()

        val regex = "(turn on|turn off|toggle) (\\d+,\\d+) through (\\d+,\\d+)".toRegex()
        input.forEach {
            val matches = regex.findAll(it).flatMap(MatchResult::groupValues).toList()
            val instruction = matches[1]
            val startNode = matches[2].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }
            val endNode = matches[3].split(',').let { pair -> pair[0].toInt() to pair[1].toInt() }

            for (x in (startNode.first..endNode.first)) {
                for (y in startNode.second..endNode.second) {
                    when (instruction) {
                        "turn on" -> lights[x to y] = lights.getValue(x to y) + 1
                        "turn off" -> lights[x to y] = max(0, lights.getValue(x to y) - 1)
                        "toggle" -> lights[x to y] = lights.getValue(x to y) + 2
                    }
                }
            }
        }

        return lights.values.sum()
    }

    check(part1(listOf("turn on 0,0 through 999,999")) == 1000000)
    check(part1(listOf("toggle 0,0 through 999,0")) == 1000)
    check(part1(listOf("turn off 499,499 through 500,500")) == 0)
    part1(readInput("Day06")).println()

    check(part2(listOf("turn on 0,0 through 0,0")) == 1)
    check(part2(listOf("toggle 0,0 through 999,999")) == 2000000)
    part2(readInput("Day06")).println()
}
