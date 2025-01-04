import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

fun main() {
    val regex = "(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds.".toRegex()
    fun parse(name: String) = readInput(name).mapNotNull { line ->
        regex.find(line)?.groupValues?.let {
            it[1] to Triple(it[2].toInt(), it[3].toInt(), it[4].toInt())
        }
    }.toMap()

    fun part1(input: Map<String, Triple<Int, Int, Int>>, seconds: Int): Map<String, Int> = input.entries.associate {
        it.key to it.value.let { (speed, flySeconds, restSeconds) ->
            val fullCycles = floor(seconds / (flySeconds + restSeconds).toDouble()).toInt()
            val fullCyclesDistance = speed * flySeconds * fullCycles
            val lastCycleDistance = min(flySeconds, seconds - fullCycles * (flySeconds + restSeconds)) * speed
            fullCyclesDistance + lastCycleDistance
        }
    }

    fun part2(input: Map<String, Triple<Int, Int, Int>>, seconds: Int): Map<String, Int> {
        val points = input.keys
            .associateWith { 0 }
            .toMutableMap()

        repeat(seconds) { i ->
            val distances = input.entries.associate {
                it.key to it.value.let { (speed, flySeconds, restSeconds) ->
                    val fullCycles = floor((i + 1) / (flySeconds + restSeconds).toDouble()).toInt()
                    val fullCyclesDistance = speed * flySeconds * fullCycles
                    val lastCycleDistance = min(flySeconds, (i + 1) - fullCycles * (flySeconds + restSeconds)) * speed
                    fullCyclesDistance + lastCycleDistance
                }
            }

            val maxDistance = distances.maxOf { it.value }
            distances.filterValues { it == maxDistance }.keys.forEach {
                points[it] = points.getOrDefault(it, 0) + 1
            }
        }
        return points
    }

    part1(parse("Day14_test"), 1000).let {
        check(it["Comet"] == 1120)
        check(it["Dancer"] == 1056)
    }
    part1(parse("Day14"), 2503).maxOf { it.value }.println()

    part2(parse("Day14_test"), 1000).let {
        check(it["Comet"] == 312)
        check(it["Dancer"] == 689)
    }
    part2(parse("Day14"), 2503).maxOf { it.value }.println()
}
