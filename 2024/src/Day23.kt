import kotlin.time.measureTime

fun main() {
    fun parseInput(name: String): List<Pair<String, String>> {
        return readInput(name).map {
            it.substringBefore('-') to it.substringAfter('-')
        }
    }

    fun findCycles(graph: Map<String, Set<String>>, limit: Int = 3): Set<Set<String>> {
        val cycles = mutableSetOf<Set<String>>()

        fun dfs(
            node: String,
            visited: MutableSet<String> = mutableSetOf(),
            path: MutableList<String> = mutableListOf()
        ) {
            if (node in visited) {
                if (path.size == limit && path.first() == node) {
                    cycles.add(path.toSet())
                }
                return
            }

            if (path.size < limit) {
                visited += node
                path += node

                graph.getValue(node).forEach { neighbour ->
                    dfs(neighbour, visited, path)
                }

                visited -= node
                path.removeLastOrNull()
            }
        }

        graph.keys.forEach { root ->
            dfs(root)
        }
        return cycles
    }

    fun findMaximalCliques(graph: Map<String, Set<String>>): List<Set<String>> {
        val cliques = mutableListOf<Set<String>>()

        @Suppress("LocalVariableName")
        fun bronKerbosch(
            R: Set<String> = mutableSetOf(),
            X: MutableSet<String> = mutableSetOf(),
            P: MutableSet<String> = graph.keys.toMutableSet(),
        ) {
            if (P.isEmpty() && X.isEmpty()) {
                cliques += R
            } else {
                val u = (P union X).first()

                (P - graph.getValue(u)).forEach { v ->
                    bronKerbosch(
                        R = (R union setOf(v)),
                        P = (P intersect graph.getValue(v)).toMutableSet(),
                        X = (X intersect graph.getValue(v)).toMutableSet()
                    )

                    P -= v
                    X += v
                }
            }
        }

        bronKerbosch()
        return cliques
    }

    fun part1(edges: List<Pair<String, String>>): Int {
        val graph = mutableMapOf<String, MutableSet<String>>()
        edges.forEach { (x, y) ->
            graph.getOrPut(x, ::mutableSetOf) += y
            graph.getOrPut(y, ::mutableSetOf) += x
        }

        val cycles = findCycles(graph)
        return cycles.count { cycle -> cycle.any { it.startsWith('t') } }
    }

    fun part2(edges: List<Pair<String, String>>): String {
        val graph = mutableMapOf<String, MutableSet<String>>()
        edges.forEach { (x, y) ->
            graph.getOrPut(x, ::mutableSetOf) += y
            graph.getOrPut(y, ::mutableSetOf) += x
        }

        val cliques = findMaximalCliques(graph)
        return cliques
            .maxBy { it.size }
            .sorted()
            .joinToString(",")
    }

    check(part1(parseInput("Day23_test")) == 7)
    part1(parseInput("Day23")).println()

    check(part2(parseInput("Day23_test")) == "co,de,ka,ta")
    part2(parseInput("Day23")).println()
}
