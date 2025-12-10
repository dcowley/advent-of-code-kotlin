package dev.dc.aoc.y24

import dev.dc.aoc.shared.Direction
import dev.dc.aoc.shared.Node
import dev.dc.aoc.shared.Rotation
import dev.dc.aoc.shared.move
import dev.dc.aoc.shared.println
import dev.dc.aoc.shared.toGrid
import java.util.PriorityQueue

data class State(val node: Node, val direction: Direction)
data class Path(val state: State, val cost: Int, val previous: Set<State>)

fun main() {
    fun part1(input: String): Int {
        val grid = input.toGrid()

        val start = grid.keys.first { grid[it] == 'S' }
        val end = grid.keys.first { grid[it] == 'E' }

        val tiles = grid.filterValues { it != '#' }.keys.toMutableSet()
        val visited = mutableSetOf<State>()
        val queue = PriorityQueue<Pair<State, Int>>(compareBy { it.second })
        queue += State(start, Direction.EAST) to 0

        var bestCost = Int.MAX_VALUE
        while (queue.isNotEmpty()) {
            val (state, cost) = queue.poll()
                .also { visited += it.first }

            if (state.node == end) {
                if (cost <= bestCost) {
                    bestCost = cost
                }
            }

            val neighbours = setOf(
                State(state.node.move(state.direction), state.direction) to cost + 1,
                State(state.node, state.direction.turn(Rotation.CLOCKWISE)) to cost + 1000,
                State(state.node, state.direction.turn(Rotation.ANTICLOCKWISE)) to cost + 1000,
            ).filter { it.first.node in tiles && it.first !in visited }

            queue.addAll(neighbours)
        }

        return bestCost
    }

    fun part2(input: String): Int {
        val grid = input.toGrid()

        val start = grid.keys.first { grid[it] == 'S' }
        val end = grid.keys.first { grid[it] == 'E' }

        val tiles = grid.filterValues { it != '#' }.keys.toMutableSet()
        val visited = mutableSetOf<State>()
        val queue = PriorityQueue<Path>(compareBy { it.cost })
        queue += Path(State(start, Direction.EAST), 0, emptySet())

        var bestCost = Int.MAX_VALUE
        val bestNodes = mutableSetOf(start, end)
        while (queue.isNotEmpty()) {
            val (state, cost, previous) = queue.poll()
                .also { visited += it.state }

            if (state.node == end) {
                if (cost <= bestCost) {
                    bestCost = cost
                    bestNodes += previous.map { it.node }
                }
            }

            val neighbours = setOf(
                State(state.node.move(state.direction), state.direction) to cost + 1,
                State(state.node, state.direction.turn(Rotation.CLOCKWISE)) to cost + 1000,
                State(state.node, state.direction.turn(Rotation.ANTICLOCKWISE)) to cost + 1000,
            ).filter { it.first.node in tiles && it.first !in visited }

            queue.addAll(neighbours.map {
                Path(it.first, it.second, previous + state)
            })
        }

        return bestNodes.size
    }

    check(part1(readInputText("Day16_test1")) == 7036)
    check(part1(readInputText("Day16_test2")) == 11048)
    check(part2(readInputText("Day16_test1")) == 45)
    check(part2(readInputText("Day16_test2")) == 64)

    part1(readInputText("Day16")).println()
    part2(readInputText("Day16")).println()
}
