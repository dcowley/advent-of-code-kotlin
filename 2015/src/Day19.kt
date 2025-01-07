fun main() {
    val regex = "(\\w+) => (\\w+)".toRegex()
    fun parse(name: String) = readInputText(name).let { input ->
        val molecule = input.substringAfter("\n\n")
        val replacements = input.substringBefore("\n\n")

        molecule to regex.findAll(replacements).map {
            it.groupValues[1] to it.groupValues[2]
        }
    }

    fun part1(molecule: String, replacements: Sequence<Pair<String, String>>) = molecule.indices.flatMap { i ->
        replacements
            .filter { molecule.substring(i).startsWith(it.first) }
            .map {
                molecule.replaceRange(i until i + it.first.length, it.second)
            }
    }.toSet().size

    check(parse("Day19_test").let { part1(it.first, it.second) } == 4)
    parse("Day19").let { part1(it.first, it.second) }.println()
}
