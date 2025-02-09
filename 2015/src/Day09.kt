import java.util.*
import kotlin.math.max

fun main() {
    fun parseInput(name: String) = buildMap<String, MutableSet<Pair<String, Int>>> {
        val input = readInput(name).map { line ->
            line.split(' ').let {
                it[0] to (it[2] to it[4].toInt())
            }
        }

        input.forEach { (city, neighbour) ->
            getOrPut(city, ::mutableSetOf) += neighbour
            getOrPut(neighbour.first, ::mutableSetOf) += city to neighbour.second
        }
    }

    fun part1(graph: Map<String, Set<Pair<String, Int>>>): Int {
        val finalStateMask = (1 shl graph.keys.size) - 1

        fun tsp(start: String): Int {
            val visited = mutableSetOf<Int>()
            val queue = PriorityQueue<Triple<String, Int, Int>>(compareBy { it.second })
            queue += Triple(start, 0, 1 shl graph.keys.indexOf(start))

            while (queue.isNotEmpty()) {
                val (city, distance, mask) = queue.poll()

                if (mask == finalStateMask) {
                    return distance
                }

                graph.getValue(city).forEach { neighbour ->
                    val nextMask = mask or (1 shl graph.keys.indexOf(neighbour.first))
                    if (nextMask !in visited) {
                        visited += nextMask
                        queue.add(Triple(neighbour.first, neighbour.second + distance, nextMask))
                    }
                }
            }

            error("No solution")
        }

        return graph.keys.minOf(::tsp)
    }

    fun part2(graph: Map<String, Set<Pair<String, Int>>>): Int {
        val finalStateMask = (1 shl graph.keys.size) - 1

        fun dfs(city: String, distance: Int = 0, state: Int = 1 shl graph.keys.indexOf(city), visited: MutableSet<Int> = mutableSetOf()): Int {
            visited += state

            if (state == finalStateMask) {
                return distance
            }

            var maxDistance = Int.MIN_VALUE
            graph.getValue(city).forEach { neighbour ->
                val nextMask = state or (1 shl graph.keys.indexOf(neighbour.first))
                if (nextMask !in visited) {
                    maxDistance = max(dfs(neighbour.first, distance + neighbour.second, nextMask, visited), maxDistance)
                }
            }

            return maxDistance
        }

        return graph.keys.maxOf(::dfs)
    }

    check(part1(parseInput("Day09_test")) == 605)
    part1(parseInput("Day09")).println()

    check(part2(parseInput("Day09_test")) == 982)
    part2(parseInput("Day09")).println()
}
