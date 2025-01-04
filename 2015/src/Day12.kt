import com.google.gson.Gson
import com.google.gson.JsonElement

fun main() {
    fun part1(input: String): Int {
        return "(-*\\d+)".toRegex().findAll(input).sumOf {
            it.groupValues.first().toInt()
        }
    }

    fun part2(input: String): Int {
        val fromJson = Gson().fromJson(input, JsonElement::class.java)

        fun solve(json: JsonElement): Int {
            return when {
                json.isJsonPrimitive -> {
                    return if (json.asJsonPrimitive.isNumber) {
                        json.asJsonPrimitive.asInt
                    } else 0
                }

                json.isJsonObject -> {
                    val entries = json.asJsonObject.entrySet()
                    return if (entries.any { it.value.isJsonPrimitive && it.value.asString == "red" }) {
                        0
                    } else {
                        entries.sumOf {
                            solve(it.value)
                        }
                    }
                }

                json.isJsonArray -> {
                    json.asJsonArray.sumOf {
                        solve(it)
                    }
                }

                else -> error("Invalid JsonElement type: $json")
            }
        }

        return solve(fromJson)
    }

    check(part1("[1,2,3]") == 6)
    check(part1("{\"a\":2,\"b\":4}") == 6)
    check(part1("[[[3]]]") == 3)
    check(part1("{\"a\":{\"b\":4},\"c\":-1}") == 3)
    check(part1("{\"a\":[-1,1]}") == 0)
    check(part1("[-1,{\"a\":1}]") == 0)
    check(part1("[]") == 0)
    check(part1("{}") == 0)
    part1(readInputText("Day12")).println()

    check(part2("[1,2,3]") == 6)
    check(part2("[1,{\"c\":\"red\",\"b\":2},3]") == 4)
    check(part2("{\"d\":\"red\",\"e\":[1,2,3,4],\"f\":5}") == 0)
    check(part2("[1,\"red\",5]") == 6)
    part2(readInputText("Day12")).println()
}
