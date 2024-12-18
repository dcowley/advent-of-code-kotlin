import java.util.*

fun main() {
    data class State(val node: Node, val path: Set<Node> = emptySet(), val cost: Int = 0)

    fun dijkstra(grid: Set<Node>, start: Node, end: Node): State {
        val visited = mutableSetOf<Node>()
        val queue = PriorityQueue<State>(compareBy { it.cost })
            .also { it += State(start) }

        while (queue.isNotEmpty()) {
            val state = queue.poll().also { visited += it.node }
            val (node, path, cost) = state

            if (node == end) {
                return state
            }

            queue += setOf(
                State(node.move(Direction.EAST), path + node, cost + 1),
                State(node.move(Direction.SOUTH), path + node, cost + 1),
                State(node.move(Direction.NORTH), path + node, cost + 1),
                State(node.move(Direction.WEST), path + node, cost + 1),
            ).filter { it.node in grid && it.node !in visited && !queue.any { (node) -> node == it.node } }
        }
        error("Node $end unreachable")
    }

    fun part1(input: List<String>, width: Int, height: Int, nanoseconds: Int): Int {
        val bytes: List<Node> = input.map {
            it.substringBefore(',').toInt() to it.substringAfter(',').toInt()
        }

        val space = buildSet<Pair<Int, Int>> {
            repeat(width * height) {
                val node = it % width to it / width
                if (node !in bytes.take(nanoseconds))
                    this += node
            }
        }

        return dijkstra(space, 0 to 0, width - 1 to height - 1).cost
    }

    fun part2(input: List<String>, width: Int, height: Int): Node {
        val bytes: List<Node> = input.map {
            it.substringBefore(',').toInt() to it.substringAfter(',').toInt()
        }

        val invertedInsertionPoint = bytes.binarySearch { byte ->
            val byteCount = bytes.indexOf(byte) + 1
            try {
                val space = buildSet {
                    repeat(width * height) {
                        val node = it % width to it / width
                        if (node !in bytes.take(byteCount))
                            this += node
                    }
                }
                dijkstra(space, 0 to 0, width - 1 to height - 1)
                -1
            } catch (e: Exception) {
                1
            }
        }

        val insertionPoint = -invertedInsertionPoint - 1
        return bytes[insertionPoint]
    }

    check(part1(readInput("Day18_test"), width = 7, height = 7, nanoseconds = 12) == 22)
    check(part2(readInput("Day18_test"), width = 7, height = 7) == 6 to 1)

    part1(readInput("Day18"), width = 71, height = 71, nanoseconds = 1024).println()
    part2(readInput("Day18"), width = 71, height = 71).println()
}
