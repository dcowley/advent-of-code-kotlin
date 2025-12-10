package dev.dc.aoc.y25

import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import com.google.ortools.linearsolver.MPVariable
import dev.dc.aoc.data.getInput
import java.util.*

object Day10 {
    data class Machine(
        val targetMask: Int,
        val buttons: List<Set<Int>>,
        val joltage: List<Int>
    ) {
        val toggleMasks by lazy {
            buttons.map {
                it.fold(0) { acc, i ->
                    acc or (1 shl i)
                }
            }
        }
    }

    data class State(val mask: Int = 0, val presses: Int = 0)

    private fun parse(input: String) = input.lines()
        .map { machine ->
            val (lights, buttons, joltage) = "^\\[([.#]+)] (.+) \\{(.+)\\}$".toRegex()
                .find(machine)!!
                .destructured

            Machine(
                targetMask = lights.foldRight(0) { char, acc ->
                    (acc shl 1) or if (char == '#') 1 else 0
                },
                buttons = buttons.split(" ").map {
                    "\\d+".toRegex().findAll(it)
                        .map(MatchResult::value)
                        .map(String::toInt)
                        .toSet()
                },
                joltage = joltage.split(",").map {
                    it.toInt()
                }
            )
        }

    fun part1(input: String): Int {
        val machines = parse(input)

        return machines.sumOf { machine ->
            val queue = PriorityQueue<State>(compareBy { it.presses })
            queue.add(State())

            while (queue.isNotEmpty()) {
                val state = queue.poll()

                machine.toggleMasks.forEach {
                    val nextMask = state.mask xor it
                    if (nextMask == machine.targetMask) {
                        return@sumOf state.presses + 1
                    } else {
                        queue.add(
                            state.copy(
                                mask = nextMask,
                                presses = state.presses + 1
                            )
                        )
                    }
                }
            }

            error("No solution")
        }
    }

    fun part2(input: String): Int {
        Loader.loadNativeLibraries()

        val machines = parse(input)

        return machines.sumOf { machine ->
            val solver = MPSolver.createSolver("SCIP")

            // ILP: minimise the sum of x_j subject to Ax = b, where b = machine.joltage
            val A = matrix(machine)
            val x = Array<MPVariable>(machine.buttons.size) {
                solver.makeIntVar(0.0, MPSolver.infinity(), "x$it")
            }

            machine.joltage.forEachIndexed { i, bi ->
                solver.makeConstraint(bi.toDouble(), bi.toDouble(), "c$i").let {
                    machine.buttons.indices.forEach { j ->
                        it.setCoefficient(x[j], A[i][j].toDouble())
                    }
                }
            }

            solver.objective().let {
                machine.buttons.indices.forEach { i ->
                    it.setCoefficient(x[i], 1.0)
                }
                it.setMinimization()
            }

            val status = solver.solve()
            if (status == MPSolver.ResultStatus.OPTIMAL) {
                solver.objective().value().toInt()
            } else {
                error("No optimal solution [resultStatus=$status]")
            }
        }
    }

    private fun matrix(machine: Machine): Array<IntArray> {
        val matrix = Array(machine.joltage.size) {
            IntArray(machine.buttons.size) { 0 }
        }

        machine.buttons.forEachIndexed { c, wiring ->
            wiring.forEach { r ->
                matrix[r][c] = 1
            }
        }

        return matrix
    }
}

suspend fun main() {
    val input = getInput(2025, 10)
    println(Day10.part1(input))
    println(Day10.part2(input))
}
