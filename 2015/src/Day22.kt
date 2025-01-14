data class State(
    val playerHp: Int,
    val playerMana: Int,
    val bossHp: Int,
    val bossDamage: Int,
    val spells: List<Spell> = emptyList(),
    val shieldTimer: Int = 0,
    val poisonTimer: Int = 0,
    val chargeTimer: Int = 0,
) {
    val cost: Int by lazy { spells.sumOf { it.cost } }

    val availableSpells = mutableListOf(Spell.Missile, Spell.Drain)
        .apply {
            if (shieldTimer <= 1) add(Spell.Shield)
            if (poisonTimer <= 1) add(Spell.Poison)
            if (chargeTimer <= 1) add(Spell.Charge)
        }
        .filter { it.cost <= playerMana }
}

enum class Spell(val cost: Int, val turns: Int = 0) {
    Missile(cost = 53) {
        override fun cast(state: State) = state.copy(
            bossHp = state.bossHp - 4
        )
    },
    Drain(cost = 73) {
        override fun cast(state: State) = state.copy(
            playerHp = state.playerHp + 2,
            bossHp = state.bossHp - 2
        )
    },
    Shield(cost = 113, turns = 6) {
        override fun cast(state: State) = state.copy(shieldTimer = turns)
    },
    Poison(cost = 173, turns = 6) {
        override fun cast(state: State) = state.copy(poisonTimer = turns)
    },
    Charge(cost = 229, turns = 5) {
        override fun cast(state: State) = state.copy(chargeTimer = turns)
    };

    abstract fun cast(state: State): State
}

fun main() {
    fun State.applyEffects() = copy(
        playerMana = if (chargeTimer > 0) playerMana + 101 else playerMana,
        bossHp = if (poisonTimer > 0) bossHp - 3 else bossHp,
        shieldTimer = (shieldTimer - 1).coerceAtLeast(0),
        poisonTimer = (poisonTimer - 1).coerceAtLeast(0),
        chargeTimer = (chargeTimer - 1).coerceAtLeast(0),
    )

    fun State.playerTurn(spell: Spell) = spell.cast(this).copy(
        playerMana = playerMana - spell.cost,
        spells = spells + spell
    )

    fun State.bossTurn() = when {
        bossHp > 0 -> copy(
            playerHp = playerHp - (bossDamage - if (shieldTimer > 0) 7 else 0).coerceAtLeast(1)
        )

        else -> this
    }

    fun State.playRound(spell: Spell): State {
        require(spell in availableSpells)

        return this
            .playerTurn(spell)
            .applyEffects()
            .bossTurn()
            .applyEffects()
    }

    fun minOfManaCost(a: State?, b: State?) = minOf(a, b, nullsLast(compareBy { it.cost }))

    fun solve(state: State, currentBest: State? = null): State? = when {
        state.playerHp <= 0 || state.availableSpells.isEmpty() -> null
        state.bossHp <= 0 -> minOfManaCost(state, currentBest)
        else -> state.availableSpells.fold(currentBest) { best, spell ->
            when {
                best != null && state.cost + spell.cost > best.cost -> best
                else -> minOfManaCost(best, solve(state.playRound(spell), best))
            }
        }
    }

    fun parse(name: String) = readInput(name).let {
        State(
            playerHp = 50,
            playerMana = 500,
            bossHp = it[0].substringAfter(": ").toInt(),
            bossDamage = it[1].substringAfter(": ").toInt(),
        )
    }

    fun part1(state: State): Int {
        return solve(state)!!.cost
    }

    part1(parse("Day22")).println()
}
