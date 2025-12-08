import dev.dc.aoc.data.getInput
import java.util.PriorityQueue
import kotlin.math.pow
import kotlin.math.sqrt

private typealias Node = Triple<Int, Int, Int>

private data class Edge(val p: Node, val q: Node, val distance: Double) {
    override fun equals(other: Any?): Boolean {
        return when {
            this === other -> true
            other !is Edge -> false
            p == other.p && q == other.q || p == other.q && q == other.p -> true
            else -> false
        }
    }

    override fun hashCode(): Int {
        return p.hashCode() + q.hashCode()
    }
}

private fun Node.euclideanDistance(that: Node): Double {
    val (p1, p2, p3) = this
    val (q1, q2, q3) = that

    return sqrt(
        (p1 - q1).toDouble().pow(2)
                + (p2 - q2).toDouble().pow(2)
                + (p3 - q3).toDouble().pow(2)
    )
}

object Day08 {
    private fun parse(input: String) = input.lines()
        .map {
            it.split(",")
                .map(String::toInt)
                .let { (x, y, z) -> Node(x, y, z) }
        }
        .toSet()

    fun part1(input: String, iterations: Int): Int {
        val junctionBoxes = parse(input)

        val edges = junctionBoxes.flatMap { node ->
            (junctionBoxes - node).map {
                Edge(
                    p = node,
                    q = it,
                    distance = node.euclideanDistance(it)
                )
            }
        }.toSet()

        val uf = UnionFind(junctionBoxes)

        val sortedEdges: List<Edge> = edges.sortedBy { it.distance }.take(iterations)
        val mst = mutableSetOf<Edge>()

        sortedEdges.forEach {
            if (uf.find(it.p) != uf.find(it.q)) {
                uf.union(it.p, it.q)
                mst.add(it)
            }
        }

        return uf.size.values.sortedDescending().take(3).reduce { a, b -> a * b }
    }

    fun part2(input: String): Long {
        val junctionBoxes = parse(input)

        val edges = junctionBoxes.flatMap { node ->
            (junctionBoxes - node).map {
                Edge(
                    p = node,
                    q = it,
                    distance = node.euclideanDistance(it)
                )
            }
        }.toSet()

        val queue = PriorityQueue(edges.size, compareBy<Edge> {
            it.distance
        })
        queue.addAll(edges)

        val uf = UnionFind(junctionBoxes)
        var lastEdge: Edge = queue.peek()
        while (uf.size.count() > 1) {
            val edge = queue.poll()
            if (uf.find(edge.p) != uf.find(edge.q)) {
                uf.union(edge.p, edge.q)
            }
            lastEdge = edge
        }

        return lastEdge.p.first.toLong() * lastEdge.q.first
    }
}

suspend fun main() {
    val input = getInput(2025, 8)
    println(Day08.part1(input, iterations = 1000))
    println(Day08.part2(input))
}

class UnionFind(nodes: Set<Node>) {
    private val parents = nodes.associateWith { it }.toMutableMap()
    val size = nodes.associateWith { 1 }.toMutableMap()

    fun find(node: Node): Node {
        return when {
            parents[node] == node -> node
            else -> {
                val root = find(parents.getValue(node))
                parents[node] = root
                root
            }
        }
    }

    fun union(nodeA: Node, nodeB: Node): Boolean {
        val rootA = find(nodeA)
        val rootB = find(nodeB)

        if (rootA == rootB) return false

        val sizeA = size[rootA] ?: 0
        val sizeB = size[rootB] ?: 0

        if (sizeA < sizeB) {
            parents[rootA] = rootB
            size[rootB] = sizeA + sizeB
            size.remove(rootA)
        } else {
            parents[rootB] = rootA
            size[rootA] = sizeA + sizeB
            size.remove(rootB)
        }

        return true
    }
}
