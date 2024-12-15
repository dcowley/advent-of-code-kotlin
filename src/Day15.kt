fun main() {
    fun part1(input: String): Long {
        val map = (input.substringBeforeLast('#') + '#')
            .toGrid()
            .toMutableMap()

        val directions = "[\\^>v<]+".toRegex()
            .findAll(input.replace("\n", "")).first()
            .groupValues.first()
            .toCharArray()

        fun canPush(point: Pair<Int, Int>, direction: Char): Boolean = when (map[point]) {
            '.' -> true
            '#' -> false

            else -> {
                canPush(point.move(direction), direction)
            }
        }

        fun push(point: Pair<Int, Int>, direction: Char) {
            if (map[point] == '.') return

            val next = point.move(direction)
            push(next, direction)
            map[next] = map[point]!!
            map[point] = '.'
        }

        var position = map.keys.first { map[it] == '@' }
        map[position] = '.'
        directions.forEach { direction ->
            val next = position.move(direction)
            if (canPush(next, direction)) {
                push(next, direction)
                position = next
            }
        }

        return map.keys.filter { map[it] == 'O' }.sumOf { (x, y) ->
            x + 100L * y
        }
    }

    fun part2(input: String): Long {
        val scaled = input
            .replace("O", "[]")
            .replace("#", "##")
            .replace(".", "..")
            .replace("@", "@.")

        val map = (scaled.substringBeforeLast('#') + '#')
            .toGrid()
            .toMutableMap()

        val directions = "[\\^>v<]+".toRegex()
            .findAll(scaled.replace("\n", "")).first()
            .groupValues.first()
            .toCharArray()

        fun canPush(point: Pair<Int, Int>, direction: Char): Boolean = when (map[point]) {
            '.' -> true
            '#' -> false

            else -> {
                val next = point.move(direction)
                if (direction == '<' || direction == '>') {
                    canPush(next, direction)
                } else {
                    val otherBoxHalf = if (map[point] == '[') point.move('>') else point.move('<')
                    canPush(next, direction) && canPush(otherBoxHalf.move(direction), direction)
                }
            }
        }

        fun push(point: Pair<Int, Int>, direction: Char) {
            if (map[point] == '.') return

            val next = point.move(direction)

            when (direction) {
                '<', '>' -> {
                    push(next, direction)
                    map[next] = map[point]!!
                    map[point] = '.'
                }

                else -> {
                    val otherBoxHalf = if (map[point] == '[') point.move('>') else point.move('<')
                    next.let {
                        push(it, direction)
                        map[it] = map[point]!!
                        map[point] = '.'
                    }
                    otherBoxHalf.move(direction).let {
                        push(it, direction)
                        map[it] = map[otherBoxHalf]!!
                        map[otherBoxHalf] = '.'
                    }
                }
            }
        }

        var position = map.keys.first { map[it] == '@' }
        map[position] = '.'
        directions.forEach { direction ->
            val next = position.move(direction)
            if (canPush(next, direction)) {
                push(next, direction)
                position = next
            }
        }

        return map.filterValues { it == '[' }.keys.sumOf { (x, y) ->
            x + 100L * y
        }
    }

    check(part1(readInputText("Day15_test1")) == 2028L)
    check(part1(readInputText("Day15_test2")) == 10092L)
    check(part2(readInputText("Day15_test2")) == 9021L)

    part1(readInputText("Day15")).println()
    part2(readInputText("Day15")).println()
}

private fun Pair<Int, Int>.move(direction: Char) = let { (x, y) ->
    when (direction) {
        '^' -> x to y - 1
        '>' -> x + 1 to y
        'v' -> x to y + 1
        '<' -> x - 1 to y
        else -> this
    }
}
