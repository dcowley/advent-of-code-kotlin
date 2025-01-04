import java.math.BigInteger
import java.security.MessageDigest

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

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

fun <T> permutations(list: List<T>): List<List<T>> = when {
    list.size == 1 -> listOf(list)
    else ->
        permutations(list.drop(1))
            .flatMap {
                list.indices.map { i ->
                    it.toMutableList().apply { add(i, list.first()) }
                }
            }
}
