import java.util.*
import kotlin.math.abs

fun main() {
    data class Path(val node: Node, val steps: Set<Node> = setOf(node), val cost: Int = 0)

    fun dijkstra(track: Set<Node>, walls: Set<Node>, start: Node, end: Node): Path {
        val queue = PriorityQueue(compareBy(Path::cost))
        queue += Path(start)

        val visited = mutableListOf<Node>()
        while (queue.isNotEmpty()) {
            val (node, steps, cost) = queue.poll()
            visited += node

            if (node == end) {
                return Path(node, steps, cost)
            }

            val moves = setOf(
                node.move(Direction.NORTH),
                node.move(Direction.EAST),
                node.move(Direction.SOUTH),
                node.move(Direction.WEST)
            )
            queue += moves
                .filter { it in track && it !in visited && it !in walls }
                .map { Path(it, steps + it, cost + 1) }
        }

        error("No path")
    }

    fun part1(input: String, timeCondition: (Int) -> Boolean): Int {
        val track = input.toGrid()
        val walls = track.filterValues { it == '#' }.keys

        val start = track.keys.find { track[it] == 'S' }!!
        val end = track.keys.find { track[it] == 'E' }!!

        val path = dijkstra(track.keys, walls, start, end)
        return path.steps.withIndex().sumOf { (i0, n0) ->
            path.steps.drop(i0).withIndex().count { (i1, n1) ->
                val manhattanDistance = abs(n1.x - n0.x) + abs(n1.y - n0.y)
                manhattanDistance == 2 && timeCondition(i1 - manhattanDistance)
            }
        }
    }

    fun part2(input: String, cheatDistance: Int, cheatTime: (Int) -> Boolean): Int {
        val track = input.toGrid()
        val walls = track.filterValues { it == '#' }.keys

        val start = track.keys.find { track[it] == 'S' }!!
        val end = track.keys.find { track[it] == 'E' }!!

        val path = dijkstra(track.keys, walls, start, end)
        return path.steps.withIndex().sumOf { (i0, n0) ->
            path.steps.drop(i0).withIndex().count { (i1, n1) ->
                val manhattanDistance = abs(n1.x - n0.x) + abs(n1.y - n0.y)
                manhattanDistance in 2..cheatDistance && cheatTime(i1 - manhattanDistance)
            }
        }
    }

    check(part1(readInputText("Day20_test"), 2::equals) == 14)
    check(part1(readInputText("Day20_test"), 4::equals) == 14)
    check(part1(readInputText("Day20_test"), 6::equals) == 2)
    check(part1(readInputText("Day20_test"), 8::equals) == 4)
    check(part1(readInputText("Day20_test"), 10::equals) == 2)
    check(part1(readInputText("Day20_test"), 12::equals) == 3)
    check(part1(readInputText("Day20_test"), 20::equals) == 1)
    check(part1(readInputText("Day20_test"), 36::equals) == 1)
    check(part1(readInputText("Day20_test"), 38::equals) == 1)
    check(part1(readInputText("Day20_test"), 40::equals) == 1)
    check(part1(readInputText("Day20_test"), 64::equals) == 1)
    println(part1(readInputText("Day20")) { it >= 100 })

    check(part2(readInputText("Day20_test"), 6, 76::equals) == 1)
    check(part2(readInputText("Day20_test"), 20, 50::equals) == 32)
    check(part2(readInputText("Day20_test"), 20, 52::equals) == 31)
    check(part2(readInputText("Day20_test"), 20, 54::equals) == 29)
    check(part2(readInputText("Day20_test"), 20, 56::equals) == 39)
    check(part2(readInputText("Day20_test"), 20, 58::equals) == 25)
    check(part2(readInputText("Day20_test"), 20, 60::equals) == 23)
    check(part2(readInputText("Day20_test"), 20, 62::equals) == 20)
    check(part2(readInputText("Day20_test"), 20, 64::equals) == 19)
    check(part2(readInputText("Day20_test"), 20, 66::equals) == 12)
    check(part2(readInputText("Day20_test"), 20, 68::equals) == 14)
    check(part2(readInputText("Day20_test"), 20, 70::equals) == 12)
    check(part2(readInputText("Day20_test"), 20, 72::equals) == 22)
    check(part2(readInputText("Day20_test"), 20, 74::equals) == 4)
    check(part2(readInputText("Day20_test"), 20, 76::equals) == 3)
    println(part2(readInputText("Day20"), 20) { it >= 100 })
}
