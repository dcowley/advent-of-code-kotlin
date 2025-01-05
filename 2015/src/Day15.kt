fun main() {
    data class Qualities(
        val capacity: Int,
        val durability: Int,
        val flavor: Int,
        val texture: Int,
        val calories: Int
    )

    val regex = "(\\w+): \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+), \\w* (-?\\d+)".toRegex()
    fun parse(name: String) = readInput(name).associate { line ->
        regex.find(line)!!.let {
            val (ingredient, capacity, durability, flavor, texture, calories) = it.destructured
            ingredient to Qualities(capacity.toInt(), durability.toInt(), flavor.toInt(), texture.toInt(), calories.toInt())
        }
    }

    fun permutations(total: Int, num: Int): List<List<Int>> = when {
        num == 0 -> emptyList()
        num == 1 -> listOf(listOf(total))
        else -> {
            buildList {
                repeat(total + 1) { i ->
                    permutations(total - i, num - 1).forEach {
                        add(listOf(i) + it)
                    }
                }
            }
        }
    }

    fun part1(input: Map<String, Qualities>, teaspoons: Int = 100): Long {
        return permutations(teaspoons, input.keys.size).maxOf {
            val capacity = input.keys.zip(it)
                .sumOf { (ingredient, quantity) -> input.getValue(ingredient).capacity * quantity }
                .coerceAtLeast(0)
            val durability = input.keys.zip(it)
                .sumOf { (ingredient, quantity) -> input.getValue(ingredient).durability * quantity }
                .coerceAtLeast(0)
            val flavor = input.keys.zip(it)
                .sumOf { (ingredient, quantity) -> input.getValue(ingredient).flavor * quantity }
                .coerceAtLeast(0)
            val texture = input.keys.zip(it)
                .sumOf { (ingredient, quantity) -> input.getValue(ingredient).texture * quantity }
                .coerceAtLeast(0)

            val result = (capacity * durability * flavor * texture.toLong())
            result
        }
    }

    fun part2(input: Map<String, Qualities>, teaspoons: Int = 100): Long {
        return permutations(teaspoons, input.keys.size).maxOf {
            val calories = input.keys.zip(it)
                .sumOf { (ingredient, quantity) -> input.getValue(ingredient).calories * quantity }
                .coerceAtLeast(0)
            if (calories != 500) {
                0L
            } else {
                val capacity = input.keys.zip(it)
                    .sumOf { (ingredient, quantity) -> input.getValue(ingredient).capacity * quantity }
                    .coerceAtLeast(0)
                val durability = input.keys.zip(it)
                    .sumOf { (ingredient, quantity) -> input.getValue(ingredient).durability * quantity }
                    .coerceAtLeast(0)
                val flavor = input.keys.zip(it)
                    .sumOf { (ingredient, quantity) -> input.getValue(ingredient).flavor * quantity }
                    .coerceAtLeast(0)
                val texture = input.keys.zip(it)
                    .sumOf { (ingredient, quantity) -> input.getValue(ingredient).texture * quantity }
                    .coerceAtLeast(0)

                (capacity * durability * flavor * texture.toLong())
            }
        }
    }

    check(part1(parse("Day15_test")) == 62_842_880L)
    part1(parse("Day15")).println()

    check(part2(parse("Day15_test")) == 57_600_000L)
    part2(parse("Day15")).println()
}
