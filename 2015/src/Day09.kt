import java.util.PriorityQueue

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
        fun tsp(start: String): Int {
            val finalStateMask = (1 shl graph.keys.size) - 1
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

    check(part1(parseInput("Day09_test")) == 605)
    part1(parseInput("Day09")).println()
}
