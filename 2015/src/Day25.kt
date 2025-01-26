fun main() {
    val input = readInputText("Day25")

    val (row, col) = "(\\d+)".toRegex().findAll(input)
        .map { it.groupValues.last().toInt() }
        .toList()

    val pos = (row + col - 2) * (row + col - 1) / 2 + col
    var code = 20151125L
    repeat(pos - 1) {
        code = (code * 252533) % 33554393
    }
    println(code)
}
