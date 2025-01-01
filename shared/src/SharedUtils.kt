/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

typealias Node = Pair<Int, Int>

val Node.x
    get() = first
val Node.y
    get() = second

fun Node.move(direction: Direction) = when (direction) {
    Direction.EAST -> x + 1 to y
    Direction.SOUTH -> x to y + 1
    Direction.WEST -> x - 1 to y
    Direction.NORTH -> x to y - 1
}

enum class Direction {
    NORTH, EAST, SOUTH, WEST;

    fun turn(rotation: Rotation) = when (this) {
        EAST -> if (rotation == Rotation.CLOCKWISE) SOUTH else NORTH
        SOUTH -> if (rotation == Rotation.CLOCKWISE) WEST else EAST
        WEST -> if (rotation == Rotation.CLOCKWISE) NORTH else SOUTH
        NORTH -> if (rotation == Rotation.CLOCKWISE) EAST else WEST
    }
}

enum class Rotation {
    CLOCKWISE,
    ANTICLOCKWISE
}
