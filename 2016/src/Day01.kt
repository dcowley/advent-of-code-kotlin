import kotlin.math.absoluteValue

fun main() {
    val regex = Regex("([R|L])(\\d+)")
    fun part1(input: String): Int {
        val route = regex.findAll(input)
            .map { it.groupValues[1] to it.groupValues[2].toInt() }

        var direction = Direction.NORTH
        var point = 0 to 0
        route.forEach { (rotation, steps) ->
            direction = direction.turn(if (rotation == "R") Rotation.CLOCKWISE else Rotation.ANTICLOCKWISE)
            repeat(steps) {
                point = point.move(direction)
            }
        }
        return point.x.absoluteValue + point.y.absoluteValue
    }

    check(part1("R2, L3") == 5)
    check(part1("R2, R2, R2") == 2)
    check(part1("R5, L5, R5, R3") == 12)
    println(part1(readInputText("Day01")))
}
