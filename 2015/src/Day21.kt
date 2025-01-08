import kotlin.math.min

fun main() {
    val weapons = mapOf(
        "Dagger" to Triple(8, 4, 0),
        "Shortsword" to Triple(10, 5, 0),
        "Warhammer" to Triple(25, 6, 0),
        "Longsword" to Triple(40, 7, 0),
        "Greataxe" to Triple(74, 8, 0),
    )
    val armor = mapOf(
        "None" to Triple(0, 0, 0),
        "Leather" to Triple(13, 0, 1),
        "Chainmail" to Triple(31, 0, 2),
        "Splintmail" to Triple(53, 0, 3),
        "Bandedmail" to Triple(75, 0, 4),
        "Platemail" to Triple(102, 0, 5),
    )
    val rings = mapOf(
        "None" to Triple(0, 0, 0),
        "Damage +1" to Triple(25, 1, 0),
        "Damage +2" to Triple(50, 2, 0),
        "Damage +3" to Triple(100, 3, 0),
        "Defense +1" to Triple(20, 0, 1),
        "Defense +2" to Triple(40, 0, 2),
        "Defense +3" to Triple(80, 0, 3)
    )

    val regex = "(\\d+)".toRegex()
    fun parse(name: String) = readInputText(name).let { input ->
        val matches = regex.findAll(input)
            .map { it.groupValues.last().toInt() }
            .toList()
        Triple(matches[0], matches[1], matches[2])
    }

    fun part1(input: Triple<Int, Int, Int>) {
        var minCost = Int.MAX_VALUE
        weapons.forEach { weapon, (cost1, atk1, def1) ->
            armor.forEach { armor, (cost2, atk2, def2) ->
                rings.forEach { ring1, (cost3, atk3, def3) ->
                    (rings - ring1).forEach { ring2, (cost4, atk4, def4) ->
                        var hp = 100
                        val atk = atk1 + atk2 + atk3 + atk4
                        val def = def1 + def2 + def3 + def4

                        var (bossHp, bossAtk, bossDef) = input
                        var turn = 0
                        while (hp > 0 && bossHp > 0) {
                            if (turn++ % 2 == 0) {
                                bossHp -= atk - bossDef
                                println("The player deals $atk - $bossDef = ${atk - bossDef} damage; the boss goes down to $bossHp hit points.")
                            } else {
                                hp -= bossAtk - def
                                println("The boss deals $bossAtk - $def = ${bossAtk - def} damage; the player goes down to $hp hit points.")
                            }
                        }

                        if (hp > bossHp) {
                            val cost = cost1 + cost2 + cost3 + cost4
                            minCost = min(minCost, cost)
                        }
                    }
                }
            }
        }
    }

    part1(parse("Day21")).println()
}
