package com.example.testtask.vm

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import com.example.testtask.model.Creature.Constants.isValidAttack
import com.example.testtask.model.Creature.Constants.isValidDamage
import com.example.testtask.model.Creature.Constants.isValidDefense
import com.example.testtask.model.Creature.Constants.isValidHealth
import com.example.testtask.model.Monster
import com.example.testtask.model.Player


class MainViewModel : ViewModel() {
    private lateinit var mPlayer: Player
    private lateinit var mMonster: Monster
    private val buffList = listOf(
        0.8,
        0.9,
        1.0,
        1.1,
        1.2,
        1.3,
        1.4,
        1.5,
        1.6,
        1.7,
        1.8,
        1.9,
        2.0,
        2.1,
        2.2,
        2.3,
        2.4,
        2.5
    )


    fun createPlayer(attack: Int, defense: Int, health: Int, damageRange: IntRange) {
        mPlayer = Player(attack, defense, health, damageRange)
    }

    fun getPlayerAttack(): Int = mPlayer.attack
    fun getPlayerDefense(): Int = mPlayer.defense
    fun getPlayerHealth(): State<Int> = mPlayer.health
    fun getPlayerDamageRange(): IntRange = mPlayer.damageRange

    fun isValidInputData(attack: Int, defense: Int, health: Int, damage: IntRange): Boolean =
        (isValidAttack(attack) && isValidDefense(defense) && isValidHealth(health) && isValidDamage(
            damage
        ))

    fun createMonster() {
        val monsterAttack = calculateMonsterCharacteristic(mPlayer.attack, 30)
        val monsterDefense = calculateMonsterCharacteristic(mPlayer.defense, 30)
        val monsterHealth = calculateMonsterCharacteristic(mPlayer.health.value, Int.MAX_VALUE)
        val monsterDamageRange = calculateMonsterDamage(mPlayer.damageRange)
        mMonster = Monster(monsterAttack, monsterDefense, monsterHealth, monsterDamageRange)
    }

    fun getMonsterAttack(): Int = mMonster.attack
    fun getMonsterDefense(): Int = mMonster.defense
    fun getMonsterHealth(): State<Int> = mMonster.health
    fun getMonsterDamageRange(): IntRange = mMonster.damageRange

    private fun getMonsterBuff(): Double = buffList.random()

    private fun calculateMonsterCharacteristic(
        playerCharacteristic: Int,
        maxCharacteristic: Int
    ): Int =
        (getMonsterBuff() * playerCharacteristic).toInt().coerceIn(1, maxCharacteristic)

    private fun calculateMonsterDamage(playerDamage: IntRange): IntRange {
        val monsterDebuff = getMonsterBuff()
        val frDamage = (monsterDebuff * playerDamage.first).toInt().coerceIn(1, Int.MAX_VALUE - 1)
        val scDamage =
            (monsterDebuff * playerDamage.last).toInt().coerceIn(frDamage + 1, Int.MAX_VALUE)
        return IntRange(frDamage, scDamage)
    }

    fun isRegenAvailable(): Boolean = mPlayer.isRegenAvailable()

    fun isPlayerDied(): Boolean = mPlayer.isDied()

    fun isMonsterDied(): Boolean = mMonster.isDied()

    fun regenPlayerHP(): Int = mPlayer.regenHealth()

    fun attackTheMonster(): Int = mPlayer.performAttack(mMonster)

    fun protectFromMonster(): Int = mMonster.performAttack(mPlayer)
}