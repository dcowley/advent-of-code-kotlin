package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

fun main() {
    data class Formula(val lhs: String, val operation: String, val rhs: String) {
        operator fun invoke(values: Map<String, Int>): Int {
            val lhs = values[lhs] ?: 0
            val rhs = values[rhs] ?: 0

            return when (operation) {
                "AND" -> lhs and rhs
                "OR" -> lhs or rhs
                "XOR" -> lhs xor rhs
                else -> error("Invalid operation: $operation")
            }
        }
    }

    fun part1(input: List<String>): Long {
        val values = input
            .mapNotNull { "([xy][0-9]{2}): ([01])".toRegex().find(it) }
            .associate { it.groupValues[1] to it.groupValues[2].toInt() }
            .toMutableMap()

        val gates = input
            .mapNotNull { "([a-z0-9]{3}) (AND|OR|XOR) ([a-z0-9]{3}) -> ([a-z0-9]{3})".toRegex().find(it) }
            .associate { it.groupValues.last() to Formula(it.groupValues[1], it.groupValues[2], it.groupValues[3]) }
            .toMutableMap()

        while (gates.isNotEmpty()) {
            val gate = gates.firstNotNullOf { if (it.value.lhs in values && it.value.rhs in values) it else null }
            values[gate.key] = gate.value(values)
            gates.remove(gate.key)
        }

        val number = buildString {
            values.filterKeys { it.startsWith('z') }.entries
                .sortedByDescending { it.key }
                .forEach {
                    append(it.value)
                }
        }

        return number.toLong(2)
    }

    fun part2(input: List<String>) {
        val values = input
            .mapNotNull { "([xy][0-9]{2}): ([01])".toRegex().find(it) }
            .associate { it.groupValues[1] to it.groupValues[2].toInt() }

        val formulas = input
            .mapNotNull { "([a-z0-9]{3}) (AND|OR|XOR) ([a-z0-9]{3}) -> ([a-z0-9]{3})".toRegex().find(it) }
            .associate { it.groupValues.last() to Formula(it.groupValues[1], it.groupValues[2], it.groupValues[3]) }

        class Verifications {
            fun wire(char: Char, num: Int): String {
                return char + "$num".padStart(2, '0')
            }

            fun verifyIntermediateXor(wire: String, num: Int): Boolean {
                println("vx: $wire $num")
                val (x, op, y) = formulas.getValue(wire)
                return op == "XOR" && setOf(x, y).sorted().let { it[0] == wire('x', num) && it[1] == wire('y', num) }
            }

            fun verifyDirectCarry(wire: String, num: Int): Boolean {
                println("vd: $wire $num")
                val (x, op, y) = formulas.getValue(wire)
                return op == "AND" && setOf(x, y).sorted().let { it[0] == wire('x', num) && it[1] == wire('y', num) }
            }

            fun verifyReCarry(wire: String, num: Int): Boolean {
                println("vr: $wire $num")
                val (x, op, y) = formulas.getValue(wire)
                return op == "AND" && (verifyIntermediateXor(x, num) && verifyCarryBit(
                    y,
                    num
                )) || (verifyIntermediateXor(y, num) && verifyCarryBit(x, num))
            }

            fun verifyCarryBit(wire: String, num: Int): Boolean {
                println("vc: $wire $num")
                val (x, op, y) = formulas.getValue(wire)
                if (num == 1) return op == "AND" && setOf(x, y).sorted().let { it[0] == "x00" && it[1] == "y00" }

                return op == "OR"
                        && (verifyDirectCarry(x, num - 1) && verifyReCarry(y, num - 1))
                        || (verifyDirectCarry(y, num - 1) && verifyReCarry(x, num - 1))
            }

            fun verifyZ(wire: String, num: Int): Boolean {
                println("vz: $wire $num")

                val (x, op, y) = formulas.getValue(wire)

                return when {
                    op != "XOR" -> false
                    num == 0 -> setOf(x, y).sorted().let { it.first() == "x00" && it.last() == "y00" }
                    else -> {
                        (verifyIntermediateXor(x, num) && verifyCarryBit(y, num))
                                || (verifyIntermediateXor(y, num) && verifyCarryBit(x, num))
                    }
                }
            }

            fun verify(num: Int) = verifyZ(wire('z', num), num)
        }

        val verifications = Verifications()


        var i = 0
        while (true) {
            if (!verifications.verify(i)) break
            i += 1
        }

        println("failed on ${verifications.wire('z', i)}")

        setOf("z17", "cmv", "z23", "rmj", "z30", "rdg", "mwp", "btb").sorted().joinToString(",").println()



        fun pp(wire: String, depth: Int = 0): String {
            if (wire.matches("^[xy]\\d{2}$".toRegex())) return "  ".repeat(depth) + wire

            val (x, op, y) = formulas.getValue(wire)

            return "  ".repeat(depth) + "$op ($wire)\n${pp(x, depth + 1)}\n${pp(y, depth + 1)}"
        }
    }

//    check(part1(dev.dc.aoc.y24.readInput("Day24_test1")) == 4L)
//    check(part1(dev.dc.aoc.y24.readInput("Day24_test2")) == 2024L)
//    part1(dev.dc.aoc.y24.readInput("Day24")).println()

    part2(readInput("Day24"))
}

// y17 XOR x17 -> wvj
// qwg AND wvj -> qhq
// wvj XOR qwg -> cmv // should point to z17
// z17 <-> cmv

// x23 XOR y23 -> pbw
// kkf AND pbw -> z23
// kkf XOR pbw -> rmj // should point to z23
// z23 <-> rmj

// y30 XOR x30 -> rvp
// knj AND rvp -> dqs // re-carry bit
// knj XOR rvp -> rdg // should point to z30
// z30 <-> rdg

// mwp is supposed to be xor between x38 and y38
// y38 AND x38 -> mwp // but it's actually the AND
// y38 XOR x38 -> btb
// mwp <-> btb