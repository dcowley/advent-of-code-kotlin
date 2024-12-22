import java.util.*
import kotlin.math.min

fun main() {
    val numericKeypad: Map<Char, Node> = mapOf(
        '7' to (0 to 0),
        '8' to (1 to 0),
        '9' to (2 to 0),
        '4' to (0 to 1),
        '5' to (1 to 1),
        '6' to (2 to 1),
        '1' to (0 to 2),
        '2' to (1 to 2),
        '3' to (2 to 2),
        '0' to (1 to 3),
        'A' to (2 to 3),
    )
    val directionKeypad: Map<Char, Node> = mapOf(
        '^' to (1 to 0),
        'A' to (2 to 0),
        '<' to (0 to 1),
        'v' to (1 to 1),
        '>' to (2 to 1),
    )

    data class Path(val node: Node, val steps: Set<Node> = setOf(node), val cost: Int = 0)

    fun dijkstra(grid: Set<Node>, start: Node, end: Node, costFunction: (Set<Node>) -> Int): Path {
        val queue = PriorityQueue(compareBy(Path::cost))
        queue += Path(start)

        val visited = mutableListOf<Node>()
        val paths = mutableSetOf<Path>()
        while (queue.isNotEmpty()) {
            val (node, steps, cost) = queue.poll()
            visited += node

            if (node == end) {
                paths += Path(node, steps, cost)
            }

            val moves = setOf(
                node.move(Direction.SOUTH),
                node.move(Direction.WEST),
                node.move(Direction.EAST),
                node.move(Direction.NORTH),
            )
            queue += moves
                .filter { it in grid && it !in visited }
                .map { Path(it, steps + it, costFunction(steps + it)) }
        }

        return paths.minBy { it.cost }
    }

    fun directions(steps: Set<Node>) = buildString {
        steps.zipWithNext { step, next ->
            when {
                next.x < step.x -> append('<')
                next.x > step.x -> append('>')
                next.y < step.y -> append('^')
                else -> append('v')
            }
        }
        append('A')
    }

    fun part1(input: List<String>): Long {
        fun cost(directions: String, depth: Int): Int {
            return directions.zipWithNext { a, b ->
                val s = directionKeypad.getValue(a)
                val e = directionKeypad.getValue(b)

                dijkstra(directionKeypad.values.toSet(), s, e) { steps ->
                    if (depth == 0) {
                        directions(steps).length
                    } else {
                        cost(directions(steps), depth - 1)
                    }
                }
            }.sumOf { it.cost }
        }

        return input.sumOf { code ->
            val sequence1 = "A$code".zipWithNext { a0, b0 ->
                val s0 = numericKeypad.getValue(a0)
                val e0 = numericKeypad.getValue(b0)

                val path = dijkstra(numericKeypad.values.toSet(), s0, e0) { steps ->
                    cost(directions(steps), 2)
                }
                directions(path.steps)
            }.joinToString("")
            println(sequence1)

            val sequence2 = "A$sequence1".zipWithNext { a0, b0 ->
                val s0 = directionKeypad.getValue(a0)
                val e0 = directionKeypad.getValue(b0)

                val path = dijkstra(directionKeypad.values.toSet(), s0, e0) { steps ->
                    cost(directions(steps), 1)
                }
                directions(path.steps)
            }.joinToString("")
            println(sequence2)

            val sequence3 = "A$sequence2".zipWithNext { a0, b0 ->
                val s0 = directionKeypad.getValue(a0)
                val e0 = directionKeypad.getValue(b0)

                val path = dijkstra(directionKeypad.values.toSet(), s0, e0) { steps ->
                    cost(directions(steps), 0)
                }
                directions(path.steps)
            }.joinToString("")
            println("${sequence3.length} $sequence3")

            code.replace("A", "").toLong() * sequence3.length
        }
    }

    fun part2(input: List<String>): Long {
        val memoization = mutableMapOf<Int, MutableMap<String, Int>>()
        fun cost(directions: String, depth: Int): Int {
            return memoization.getOrPut(depth) { mutableMapOf() }
                .getOrPut(directions) {
                    directions.zipWithNext { a, b ->
                        val s = directionKeypad.getValue(a)
                        val e = directionKeypad.getValue(b)
                        dijkstra(directionKeypad.values.toSet(), s, e) { steps ->
                            if (depth == 0) {
                                directions(steps).length
                            } else {
                                cost(directions(steps), depth - 1)
                            }
                        }.cost
                    }.sum()
                }
        }

        return input.sumOf { code ->
            var sequence = "A$code".zipWithNext { a0, b0 ->
                val s0 = numericKeypad.getValue(a0)
                val e0 = numericKeypad.getValue(b0)

                val path = dijkstra(numericKeypad.values.toSet(), s0, e0) { steps ->
                    cost(directions(steps), 25)
                }
                directions(path.steps)
            }.joinToString("")
            println(sequence)

            repeat(24) {
                sequence = "A$sequence".zipWithNext { a0, b0 ->
                    val s0 = directionKeypad.getValue(a0)
                    val e0 = directionKeypad.getValue(b0)

                    val path = dijkstra(directionKeypad.values.toSet(), s0, e0) { steps ->
                        cost(directions(steps), 24 - it)
                    }
                    directions(path.steps)
                }.joinToString("")
                println("$it ${sequence.length}")
            }

            code.replace("A", "").toLong() * sequence.length
        }
    }

    check(part1(readInput("Day21_test")) == 126384L)
    part1(readInput("Day21")).println()
    part2(readInput("Day21")).println()
}
