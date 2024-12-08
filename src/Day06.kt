fun main() {
    fun part1(input: List<String>): Int {
        val width = input.first().length
        val map = input.joinToString("")

        var direction = Direction.UP
        var position = map.indexOf('^')

        fun nextPosition(position: Int, direction: Direction) = when (direction) {
            Direction.UP -> position - width
            Direction.RIGHT -> position + 1
            Direction.DOWN -> position + width
            Direction.LEFT -> position - 1
        }

        val path = mutableListOf<Pair<Int, Direction>>()
        while (true) {
            val nextPosition = nextPosition(position, direction)
            if (nextPosition !in map.indices) {
                break
            }

            if (map[nextPosition] == '#') {
                direction = direction.next()
                path.add(position to direction)
            } else {
                position = nextPosition
                path.add(nextPosition to direction)
            }
        }

        return path.distinctBy { it.first }.size
    }

    fun part2(input: List<String>): Int {
        val width = input.first().length
        val map = input.joinToString("")

        var direction = Direction.UP
        var position = map.indexOf('^')

        fun nextPosition(position: Int, direction: Direction) = when (direction) {
            Direction.UP -> position - width
            Direction.RIGHT -> position + 1
            Direction.DOWN -> position + width
            Direction.LEFT -> position - 1
        }

        val path = mutableListOf<Pair<Int, Direction>>()
        while (true) {
            val nextPosition = nextPosition(position, direction)
            if (nextPosition !in map.indices) {
                break
            }

            if (map[nextPosition] == '#') {
                direction = direction.next()
                path.add(position to direction)
            } else {
                position = nextPosition
                path.add(nextPosition to direction)
            }
        }

        val startingPosition = map.indexOf('^')
        val options = path.map { it.first }.toSet() - startingPosition
        var obstacles = 0

        options.forEach { obstacle ->
            position = startingPosition
            direction = Direction.UP
            path.clear()

            while (true) {
                val nextPosition = nextPosition(position, direction)
                if (nextPosition !in map.indices || (direction == Direction.RIGHT) && nextPosition % width == 0 || (direction == Direction.LEFT) && (nextPosition + 1) % width == 0) {
                    break
                }

                when {
                    nextPosition to direction in path -> {
                        obstacles+=1
                        break
                    }

                    map[nextPosition] == '#' || nextPosition == obstacle -> {
                        direction = direction.next()
                        path.add(position to direction)
                    }

                    else -> {
                        position = nextPosition
                        path.add(nextPosition to direction)
                    }
                }
            }
        }
        return obstacles
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    fun next() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}
