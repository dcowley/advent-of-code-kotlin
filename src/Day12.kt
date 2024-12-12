fun main() {
    fun part1(input: List<String>): Int {
        val garden = input.joinToString("")
        val plots = garden.indices.map { it % input.first().length to it / input.first().length }

        fun buildRegion(plot: Pair<Int, Int>, type: Char, region: MutableSet<Pair<Int, Int>> = mutableSetOf()): Set<Pair<Int, Int>> {
            val (x, y) = plot

            if (plot in plots && plot !in region && input[y][x] == type) {
                region.add(plot)

                buildRegion(x + 1 to y, type, region)
                buildRegion(x - 1 to y, type, region)
                buildRegion(x to y + 1, type, region)
                buildRegion(x to y - 1, type, region)
            }

            return region
        }

        val regions = mutableSetOf<Set<Pair<Int, Int>>>()
        plots.forEach { plot ->
            if (regions.none { it.contains(plot) }) {
                val region = buildRegion(plot, input[plot.second][plot.first])
                regions.add(region)
            }
        }

        val totalPrice = regions.sumOf { region ->
            fun countFences(plot: Pair<Int, Int>, region: Set<Pair<Int, Int>>): Int {
                var fences = 0
                plot.let { (x, y) ->
                    if (x - 1 to y !in region) fences++
                    if (x + 1 to y !in region) fences++
                    if (x to y + 1 !in region) fences++
                    if (x to y - 1 !in region) fences++
                }
                return fences
            }

            val totalFences = region.sumOf {
                countFences(it, region)
            }
            totalFences * region.size
        }
        return totalPrice
    }

    fun part2(input: List<String>): Int {
        val grid = hashMapOf<Plot, Plant>()
        input.forEachIndexed { y, line ->
            line.toCharArray().forEachIndexed { x, c ->
                grid[x to y] = c
            }
        }

        val regionMapping = mutableMapOf<Plot, Int>()
        var regionId = -1

        grid.forEach { (plot, plant) ->
            if (plot !in regionMapping) {
                regionId++

                val toVisit = mutableListOf(plot)
                while (toVisit.isNotEmpty()) {
                    val current = toVisit.removeFirst()
                    if (grid[current] == plant) {
                        regionMapping[current] = regionId
                        toVisit += current.getNeighbours().filter { !(it in toVisit || it in regionMapping) }
                    }
                }
            }
        }

        val totalCost = (0..regionId).sumOf { id ->
            val plots = regionMapping.filter { it.value == id }.keys
            val area = plots.size

            val borders = Direction.entries.sumOf { direction ->
                var borderCount = 0

                val visitedPlots = mutableSetOf<Plot>()
                plots.forEach { plot ->
                    if (plot !in visitedPlots) {
                        val isBorder = regionMapping[plot.move(direction)] != id
                        if (isBorder) {
                            borderCount++
                            visitedPlots += plot
                            listOf(direction.previous(), direction.next()).forEach { sideDirection ->
                                var current = plot
                                do {
                                    current = current.move(sideDirection)
                                    visitedPlots += current
                                } while (regionMapping[current] == id && regionMapping[current.move(direction)] != id)
                            }
                        }
                    }
                }
                borderCount
            }

            area * borders
        }
        return totalCost
    }

    check(part1(readInput("Day12_test1")) == 140)
    check(part1(readInput("Day12_test2")) == 1930)
    check(part2(readInput("Day12_test1")) == 80)
    check(part2(readInput("Day12_test3")) == 236)
    check(part2(readInput("Day12_test4")) == 368)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}

fun Direction.previous() = when (this) {
    Direction.DOWN -> Direction.RIGHT
    Direction.LEFT -> Direction.DOWN
    Direction.UP -> Direction.LEFT
    Direction.RIGHT -> Direction.UP
}

fun part2(input: List<String>): Int {
    val garden = input.joinToString("")
    val plots = garden.indices.map { it % input.first().length to it / input.first().length }

    fun buildRegion(plot: Pair<Int, Int>, type: Char, region: MutableSet<Pair<Int, Int>> = mutableSetOf()): Set<Pair<Int, Int>> {
        val (x, y) = plot

        if (plot in plots && plot !in region && input[y][x] == type) {
            region.add(plot)

            buildRegion(x + 1 to y, type, region)
            buildRegion(x - 1 to y, type, region)
            buildRegion(x to y + 1, type, region)
            buildRegion(x to y - 1, type, region)
        }

        return region
    }

    val regions = mutableSetOf<Set<Pair<Int, Int>>>()
    plots.forEach { plot ->
        if (regions.none { it.contains(plot) }) {
            if (input[plot.second][plot.first] != '.') {
                val region = buildRegion(plot, input[plot.second][plot.first])
                regions.add(region)
            }
        }
    }

    val totalPrice = regions.sumOf { region ->
        val path = region.flatMap { (x, y) ->
            setOf(
                x to y,
                x + 1 to y,
                x to y + 1,
                x + 1 to y + 1
            )
        }.toSet()
        val visited = mutableSetOf<Pair<Int, Int>>()

        fun Pair<Int, Int>.nextDirection(current: Direction): Direction {
            val (x, y) = this

            val canTurnBack = when (current.previous()) {
                Direction.RIGHT -> {
                    x + 1 to y in path && x + 1 to y !in visited
                }

                Direction.DOWN -> {
                    x to y + 1 in path && x to y + 1 !in visited
                }

                Direction.LEFT -> {
                    x - 1 to y in path && x - 1 to y !in visited
                }

                Direction.UP -> {
                    x to y - 1 in path && x to y - 1 !in visited
                }
            }
            if (canTurnBack) {
                return current.previous()
            }

            val canGoStraight = when (current) {
                Direction.RIGHT -> {
                    x + 1 to y in path && x + 1 to y !in visited
                }

                Direction.DOWN -> {
                    x to y + 1 in path && x to y + 1 !in visited
                }

                Direction.LEFT -> {
                    x - 1 to y in path && x - 1 to y !in visited
                }

                Direction.UP -> {
                    x to y - 1 in path && x to y - 1 !in visited
                }
            }
            if (canGoStraight) {
                return current
            }

            return when {
                x + 1 to y in path && x + 1 to y !in visited -> Direction.RIGHT
                x to y + 1 in path && x to y + 1 !in visited -> Direction.DOWN
                x - 1 to y in path && x - 1 to y !in visited -> Direction.LEFT
                else -> Direction.UP
            }
        }

        var (x, y) = path.first()
        var direction = Direction.UP
        var corners = 0

        while (true) {
            val nextDirection = (x to y).nextDirection(direction)
            if (nextDirection != direction) {
                corners++
            }
            direction = nextDirection

            when (direction) {
                Direction.RIGHT -> x++
                Direction.DOWN -> y++
                Direction.LEFT -> x--
                Direction.UP -> y--
            }

            visited.add(x to y)
            if (x to y == path.first()) {
                break
            }
        }

        corners * region.size
    }

    return totalPrice
}

private typealias Plant = Char
private typealias Plot = Pair<Int, Int>

private val Plot.x
    get() = first
private val Plot.y
    get() = second

private fun Plot.getNeighbours() = setOf(
    x + 1 to y,
    x - 1 to y,
    x to y + 1,
    x to y - 1
)

fun Plot.move(direction: Direction) = when (direction) {
    Direction.RIGHT -> x + 1 to y
    Direction.DOWN -> x to y + 1
    Direction.LEFT -> x - 1 to y
    Direction.UP -> x to y - 1
}
