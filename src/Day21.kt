import java.util.*

fun main() {
    data class Path(val node: Node, val steps: Set<Node> = setOf(node), val cost: Int = 0)

    fun dijkstra(grid: Set<Node>, start: Node, end: Node, costFunction: (Set<Node>) -> Int = { it.size }): Set<Path> {
        val queue = PriorityQueue(compareBy(Path::cost))
        queue += Path(start)

        val visited = mutableListOf<Node>()
        val paths = mutableSetOf<Path>()

        var minCost = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val (node, steps, cost) = queue.poll()
            visited += node

            if (node == end) {
                if (cost < minCost) {
                    minCost = cost
                }
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

        return paths.filter { it.cost == minCost }.toSet()
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

    fun computeSequences(keypad: Map<Char, Node>) = buildMap {
        for (x in keypad.keys) {
            for (y in keypad.keys) {
                this[x to y] = if (x == y) setOf("A") else {
                    dijkstra(keypad.values.toSet(), keypad.getValue(x), keypad.getValue(y))
                        .map { directions(it.steps) }
                        .toSet()
                }
            }
        }
    }

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
    val numericSequences = computeSequences(numericKeypad)

    val directionKeypad: Map<Char, Node> = mapOf(
        '^' to (1 to 0),
        'A' to (2 to 0),
        '<' to (0 to 1),
        'v' to (1 to 1),
        '>' to (2 to 1),
    )
    val directionSequences = computeSequences(directionKeypad)

    val cache = mutableMapOf<Pair<String, Int>, Long>()
    fun computeLength(sequence: String, depth: Int = 2): Long {
        return cache.getOrPut(sequence to depth) {
            if (depth == 1) {
                "A$sequence".zipWithNext { a, b ->
                    directionSequences.getValue(a to b).first().length.toLong()
                }.sum()
            } else {
                "A$sequence".zipWithNext { a, b ->
                    directionSequences.getValue(a to b).minOf {
                        computeLength(it, depth - 1)
                    }
                }.sum()
            }
        }
    }

    fun product(current: Set<String>, remaining: List<Set<String>>): List<String> {
        return if (remaining.size == 1) {
            current.flatMap { l -> remaining.first().map { "$l$it" } }
        } else {
            current.flatMap { l -> product(remaining.first(), remaining.drop(1)).map { "$l$it" } }
        }
    }

    fun solve(sequence: String, sequences: Map<Pair<Char, Char>, Set<String>>): List<String> {
        val options = "A$sequence".zipWithNext { a, b -> sequences.getValue(a to b) }

        return product(options.first(), options.drop(1))
    }

    fun part1(input: List<String>) = input.sumOf { line ->
        val minLength = solve(line, numericSequences).minOf { computeLength(it, depth = 2) }
        minLength * line.dropLast(1).toLong()
    }

    fun part2(input: List<String>) = input.sumOf { line ->
        val minLength = solve(line, numericSequences).minOf { computeLength(it, depth = 25) }
        minLength * line.dropLast(1).toLong()
    }

    check(part1(readInput("Day21_test")) == 126384L)
    part1(readInput("Day21")).println()
    part2(readInput("Day21")).println()
}
