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

    check(part1(listOf("aaaaa-bbb-z-y-x-123[abxyz]")) == 123)
    check(part1(listOf("a-b-c-d-e-f-g-h-987[abcde]")) == 987)
    check(part1(listOf("not-a-real-room-404[oarel]")) == 404)
    check(part1(listOf("totally-real-room-200[decoy]")) == 0)
    println(part1(readInput("Day04")))
}