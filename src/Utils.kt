import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInputText(name: String) = Path("src/$name.txt").readText().trim()

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = readInputText(name).lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun String.toGrid(): Map<Pair<Int, Int>, Char> {
    val w = indexOf('\n')
    return replace("\n", "")
        .mapIndexed { i, c -> (i % w to i / w) to c }
        .toMap()
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

val Pair<Int, Int>.x
    get() = first
val Pair<Int, Int>.y
    get() = second

fun Pair<Int, Int>.move(direction: Direction) = when (direction) {
    Direction.EAST -> x + 1 to y
    Direction.SOUTH -> x to y + 1
    Direction.WEST -> x - 1 to y
    Direction.NORTH -> x to y - 1
}
