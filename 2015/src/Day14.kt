import kotlin.math.ceil
import kotlin.math.floor
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

    part1(parse("Day14_test"), 1000).let {
        check(it["Comet"] == 1120)
        check(it["Dancer"] == 1056)
    }
    part1(parse("Day14"), 2503).maxOf { it.value }.println()
}
