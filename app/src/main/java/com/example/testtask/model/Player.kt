package com.example.testtask.model

class Player(
    attack: Int,
    defense: Int,
    health: Int,
    damageRange: IntRange
) : Creature(attack, defense, health, damageRange) {
    private val maxHealth = health
    private var countRegen = 4

    fun isRegenAvailable(): Boolean = countRegen > 0

    fun regenHealth(): Int {
        if (isRegenAvailable()) {
            val newHealth = (_health.intValue + maxHealth * 0.3)
            _health.intValue = if (newHealth > maxHealth) maxHealth
            else newHealth.toInt()
            countRegen--
        }
        return _health.intValue
    }
}