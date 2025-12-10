package dev.dc.aoc.y24

import dev.dc.aoc.shared.println

const val adv = 0
const val bxl = 1
const val bst = 2
const val jnz = 3
const val bxc = 4
const val out = 5
const val bdv = 6
const val cdv = 7

fun main() {
    fun solve(initial: Long, instructions: IntArray): IntArray {
        var registerA = initial
        var registerB = 0L
        var registerC = 0L

        var instructionPointer = 0
        var output = intArrayOf()

        while (instructionPointer < instructions.size) {
            val opcode = instructions[instructionPointer]
            val operand = instructions[instructionPointer + 1]

            fun combo(operand: Int): Long = when (operand) {
                in 0..3 -> operand.toLong()
                4 -> registerA
                5 -> registerB
                6 -> registerC
                else -> error("Invalid dev.dc.aoc.y24.adv operand!")
            }

            when (opcode) {
                // 0
                adv -> registerA = registerA shr combo(operand).toInt()
                // 1
                bxl -> registerB = registerB xor operand.toLong()
                // 2
                bst -> registerB = combo(operand) % 8
                // 3
                jnz -> if (registerA != 0L) {
                    instructionPointer = operand
                    continue
                }
                // 4
                bxc -> registerB = registerB xor registerC
                // 5
                out -> output += (combo(operand) % 8).toInt()
                //6
                bdv -> registerB = registerA shr combo(operand).toInt()
                //7
                cdv -> registerC = registerA shr combo(operand).toInt()
            }

            instructionPointer += 2
        }

        return output
    }

    fun part1(input: String): String {
        val registerRegex = "Register ([ABC]): (\\d+)".toRegex()
        val registerA = registerRegex.find(input)!!
            .groupValues[2]
            .toLong()

        val instructionRegex = "\\d+(?:,\\d)*".toRegex()
        val instructions = instructionRegex.find(input.substringAfter("Program: "))!!
            .groupValues.first()
            .split(',')
            .map(String::toInt)
            .toIntArray()

        return solve(registerA, instructions).joinToString(",")
    }

    fun program(input: String): String {
        var out = intArrayOf()
        var a = "\\d+".toRegex().find(input)!!.groupValues.first().toLong()
        while (a != 0L) {
            var b = (a % 8) xor 7
            b = (b xor (a shr b.toInt())) xor 7
            a = a shr 3

            out += (b % 8).toInt()
        }
        return out.joinToString(",")
    }

    fun part2(input: String): Long {
        val program = "\\d+".toRegex().findAll(input)
            .map { it.groupValues.first().toInt() }
            .drop(3)
            .toList()

        fun find(input: List<Int>, ans: Long): Long {
            if (input.isEmpty()) return ans

            (0..7L).forEach {
                val a = (ans shl 3) or it.toLong()

                var b = it xor 7
                b = (b xor (a shr b.toInt())) xor 7

                val out = (b % 8).toInt()
                if (out == input.last()) {
                    val sub = find(input.dropLast(1), a)
                    if (sub != -1L) return sub
                }
            }
            return -1L
        }

        return find(program, 0)
    }

    check(part1(readInputText("Day17_test1")) == "0,1,2")
    check(part1(readInputText("Day17_test2")) == "4,2,5,6,7,7,7,7,3,1,0")
    check(part1(readInputText("Day17_test3")) == "4,6,3,5,6,3,5,2,1,0")
    check(part1(readInputText("Day17_test4")) == "0,3,5,4,3,0")

    // check reversed engineered program:
    check(program(readInputText("Day17_test5")) == part1(readInputText("Day17_test5")))

    part1(readInputText("Day17")).println()
    program(readInputText("Day17")).println()
    part2(readInputText("Day17")).println()
}
