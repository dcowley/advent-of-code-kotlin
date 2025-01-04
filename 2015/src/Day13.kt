fun main() {
    fun parse(name: String) = buildMap<String, MutableSet<Pair<String, Int>>> {
        val input = readInput(name).map {
            val matches = "(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+).".toRegex().find(it)!!.groupValues
            matches[1] to (matches[4] to matches[3].toInt() * if (matches[2] == "gain") 1 else -1)
        }

        input.forEach { (guest, neighbour) ->
            val gtn = input.first { it.first == neighbour.first && it.second.first == guest }
            getOrPut(guest, ::mutableSetOf) += (neighbour.first to neighbour.second + gtn.second.second)
        }
    }

    fun <T> permutations(list: List<T>): List<List<T>> = when {
        list.size == 1 -> listOf(list)
        else ->
            permutations(list.drop(1))
                .flatMap { perm ->
                    (list.indices).map { i ->
                        perm.subList(0, i) + list.first() + perm.drop(i)
                    }
                }
    }

    fun part1(graph: Map<String, Set<Pair<String, Int>>>): Int {
        val permutations = permutations(graph.keys.toList())
        return permutations.maxOf { seating ->
            (seating + seating.first())
                .zipWithNext { guest1, guest2 -> graph.getValue(guest1).first { it.first == guest2 }.second }
                .sum()
        }
    }

    fun part2(graph: Map<String, Set<Pair<String, Int>>>): Int {
        return part1(graph.toMutableMap().apply {
            this["Dean"] = graph.keys.map { it to 0 }.toSet()
            graph.forEach { (guest, seating) ->
                this[guest] = seating + ("Dean" to 0)
            }
        })
    }

    check(part1(parse("Day13_test")) == 330)
    part1(parse("Day13")).println()
    part2(parse("Day13")).println()
}
