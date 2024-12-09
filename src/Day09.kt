import kotlin.math.min

fun main() {
    fun part1(input: String): Long {
        val files = input.slice(input.indices step 2)
        val space = input.slice(1 until input.length step 2) + "0"

        val system = buildList {
            files.zip(space).forEachIndexed { index, pair ->
                repeat(pair.first.digitToInt()) { add(index.toString()) }
                repeat(pair.second.digitToInt()) { add(".") }
            }
        }.toTypedArray()

        val freeSpace = system.count { it == "." }
        while (system.indexOf(".") < system.size - freeSpace) {
            val i1 = system.indexOfLast { it != "." }
            system[system.indexOf(".")] = system[i1]
            system[i1] = "."
        }

        val checksum = system.dropLast(freeSpace).foldIndexed(0L) { i, acc, num ->
            acc + (i * num.toLong())
        }
        return checksum
    }

    fun part2(input: String): Long {
        val files = input.slice(input.indices step 2)
        val space = input.slice(1 until input.length step 2) + "0"

        val system = buildList {
            files.zip(space).forEachIndexed { index, pair ->
                repeat(pair.first.digitToInt()) { add(index.toString()) }
                repeat(pair.second.digitToInt()) { add(".") }
            }
        }.toTypedArray()

        val fileSizes = system.reversed().groupingBy { it }.eachCount()
        fileSizes.forEach { (file, size) ->
            val fileIndex = system.indexOf(file)
            val freeSpaceIndex = system.slice(0..fileIndex - size).indices.find { index ->
                system.slice(index until index + size)
                    .all { it == "." }
            }
            freeSpaceIndex?.let {
                for (i in fileIndex until fileIndex + size) {
                    system[i] = "."
                }
                for (i in it until it + size) {
                    system[i] = file
                }
            }
        }

        val checksum = system.foldIndexed(0L) { i, acc, num ->
            if (num == ".") acc
            else acc + (i * num.toLong())
        }
        return checksum
    }

    val testInput = readInputText("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInputText("Day09")
    part1(input).println()
    part2(input).println()
}
