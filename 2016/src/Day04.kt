fun main() {
    fun part1(input: List<String>): Int {
        val regex = "([a-z-]+)+-(\\d+)\\[(\\w+)]".toRegex()

        return input.sumOf {
            val (name, id, checksum) = regex.find(it)!!.destructured

            val counts = name
                .replace("-", "")
                .associateWith { char -> Regex("$char").findAll(name).count() }

            val actualChecksum = counts.keys.sorted()
                .sortedByDescending(counts::get)
                .take(5)
                .joinToString("")

            if (actualChecksum == checksum) {
                id.toInt()
            } else {
                0
            }
        }
    }

    fun part2(input: List<String>) = input.map {
        val regex = "([a-z-]+)+-(\\d+)\\[(\\w+)]".toRegex()

        val (name, id, _) = regex.find(it)!!.destructured

        val decrypted = name
            .split("-")
            .joinToString(" ") { word ->
                word.map { c -> Char(((c.code - 'a'.code) + id.toInt()) % 26 + 'a'.code) }
                    .joinToString("")
            }

        "$decrypted: $id"
    }.find { it.contains("northpole object storage") }

    check(part1(listOf("aaaaa-bbb-z-y-x-123[abxyz]")) == 123)
    check(part1(listOf("a-b-c-d-e-f-g-h-987[abcde]")) == 987)
    check(part1(listOf("not-a-real-room-404[oarel]")) == 404)
    check(part1(listOf("totally-real-room-200[decoy]")) == 0)
    println(part1(readInput("Day04")))

    println(part2(readInput("Day04")))
}