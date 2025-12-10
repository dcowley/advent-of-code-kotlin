package dev.dc.aoc.shared

// Adapted from [Rosetta Code: Combinations](https://rosettacode.org/wiki/Combinations#Recursion_2)
fun combinations(input: IntArray, size: Int) = buildSet {
    val combination = IntArray(size)

    fun generate(index: Int = 0) {
        if (index == size) {
            add(combination.copyOf())
        } else {
            input.forEach {
                if (index == 0 || it > combination[index - 1]) {
                    combination[index] = it
                    generate(index + 1)
                }
            }
        }
    }

    generate()
}
