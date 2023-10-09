package com.example.testtask.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import kotlin.random.Random

open class Creature(
    var attack: Int,
    var defense: Int,
    health: Int,
    var damageRange: IntRange
) {
    companion object Constants {
        private const val MIN_ATTACK = 1
        private const val MAX_ATTACK = 30
        private const val MIN_DEFENSE = 1
        private const val MAX_DEFENSE = 30
        private const val MIN_HEALTH = 1
        private const val MAX_HEALTH = Int.MAX_VALUE
        private const val MIN_DAMAGE = 1
        private const val MAX_DAMAGE = Int.MAX_VALUE
        fun isValidDefense(value: Int): Boolean = value in MIN_DEFENSE..MAX_DEFENSE
        fun isValidAttack(value: Int): Boolean = value in MIN_ATTACK..MAX_ATTACK
        fun isValidHealth(value: Int): Boolean = value in MIN_HEALTH..MAX_HEALTH
        fun isValidDamage(value: IntRange): Boolean =
            value.first in MIN_DAMAGE until MAX_DAMAGE && value.last in MIN_DAMAGE + 1..MAX_DAMAGE && value.last > value.first
    }

    init { //for testing model, app never crash
        require(isValidAttack(attack))
        require(isValidDefense(defense))
        require(isValidHealth(health))
        require(isValidDamage(damageRange))
    }

    protected var _health = mutableIntStateOf(health)

    val health: State<Int>
        get() = _health

    fun isDied(): Boolean = health.value <= 0

    private fun isAttackSuccessful(defender: Creature): Boolean {
        val modifier = attack - defender.defense + 1
        val diceCount = if (modifier > 0) modifier else 1
        for (i in 0 until diceCount) {
            val diceRoll = Random.nextInt(1, 7)
            if (diceRoll == 5 || diceRoll == 6) return true
        }
        return false
    }

    private fun takingDamage(dmg: Int) {
        _health.intValue -= dmg
    }

    fun performAttack(defender: Creature): Int {
        val damage = if (!isAttackSuccessful(defender)) 0
        else Random.nextInt(damageRange.first, damageRange.last + 1)
        defender.takingDamage(damage)
        return damage
    }
}

