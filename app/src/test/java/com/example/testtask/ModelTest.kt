package com.example.testtask

import com.example.testtask.model.Creature
import com.example.testtask.model.Monster
import com.example.testtask.model.Player
import org.junit.Test

import org.junit.Assert.*

class ModelTest {

    // Tests For Creature

    @Test
    fun testIsValidAttack() {
        assertTrue(Creature.isValidAttack(15))
        assertTrue(Creature.isValidAttack(1))
        assertTrue(Creature.isValidAttack(30))

        assertFalse(Creature.isValidAttack(0))
        assertFalse(Creature.isValidAttack(50))
        assertFalse(Creature.isValidAttack(100))
    }

    @Test
    fun testIsValidDefense() {
        assertTrue(Creature.isValidDefense(15))
        assertTrue(Creature.isValidDefense(1))
        assertTrue(Creature.isValidDefense(30))

        assertFalse(Creature.isValidDefense(0))
        assertFalse(Creature.isValidDefense(50))
        assertFalse(Creature.isValidDefense(100))
    }

    @Test
    fun testIsValidHealth() {
        assertTrue(Creature.isValidHealth(500))
        assertTrue(Creature.isValidHealth(1))
        assertTrue(Creature.isValidHealth(Int.MAX_VALUE))

        assertFalse(Creature.isValidHealth(0))
        assertFalse(Creature.isValidHealth(-100))
        assertFalse(Creature.isValidHealth(Int.MAX_VALUE + 1))
    }

    @Test
    fun testIsValidDamage() {
        assertTrue(Creature.isValidDamage(1..10))
        assertTrue(Creature.isValidDamage(1..Int.MAX_VALUE))
        assertTrue(Creature.isValidDamage(Int.MAX_VALUE - 1..Int.MAX_VALUE))
        assertTrue(Creature.isValidDamage(1..2))

        assertFalse(Creature.isValidDamage(0..10))
        assertFalse(Creature.isValidDamage(10..1))
        assertFalse(Creature.isValidDamage(10..10))
        assertFalse(Creature.isValidDamage(2..2))
        assertFalse(Creature.isValidDamage(Int.MAX_VALUE..Int.MAX_VALUE + 1))
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidAttackInitialization() {
        Creature(31, 20, 100, 1..10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidDefenseInitialization() {
        Creature(25, 31, 100, 1..10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidHealthInitialization() {
        Creature(20, 15, -1, 1..10)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidDamageInitialization() {
        Creature(20, 15, 100, 10..10)
    }

    @Test
    fun testValidCreatureInitialization() {
        val creature = Creature(20, 15, 100, 1..10)
        assertEquals(20, creature.attack)
        assertEquals(15, creature.defense)
        assertEquals(100, creature.health.value)
        assertEquals(1..10, creature.damageRange)
    }

    @Test
    fun testPerformAttack() {
        var attacker = Creature(20, 10, 100, 5..15)
        var defender = Creature(15, 8, 100, 3..10)
        var damage = attacker.performAttack(defender)
        assertTrue(damage in 5..15 || damage == 0)
        if (damage != 0) {
            assertEquals(100 - damage, defender.health.value)
        } else {
            assertEquals(100, defender.health.value)
        }
        attacker = Creature(30, 1, 100, 1..3)
        defender = Creature(15, 1, 100, 3..10)
        damage = attacker.performAttack(defender)
        assertTrue(damage in 0..3)
        if (damage != 0) {
            assertEquals(100 - damage, defender.health.value)
        } else {
            assertEquals(100, defender.health.value)
        }
    }

    @Test
    fun testIsDied() {
        val defender = Creature(30, 1, 1, 1..10)
        assertFalse(defender.isDied())
        val attacker = Creature(30, 1, 1, 1..100)
        while (defender.health.value == 1) attacker.performAttack(defender)
        assertTrue(defender.isDied())
    }

    //Test For Player

    @Test
    fun testRegeneration() {
        val player = Player(30, 1, Int.MAX_VALUE, 1..3)
        val attacker = Monster(30, 1, Int.MAX_VALUE, 1..3)
        while (player.health.value == Int.MAX_VALUE) attacker.performAttack(player)
        assertTrue(player.isRegenAvailable())
        player.regenHealth()
        assertEquals(player.health.value, Int.MAX_VALUE)
        assertTrue(player.isRegenAvailable())
        player.regenHealth()
        assertTrue(player.isRegenAvailable())
        player.regenHealth()
        assertTrue(player.isRegenAvailable())
        player.regenHealth()
        while (player.health.value == Int.MAX_VALUE) attacker.performAttack(player)
        assertFalse(player.isRegenAvailable())
        player.regenHealth()
        assertNotEquals(player.health.value, Int.MAX_VALUE)

        val pl2 = Player(30, 1, 100, 1..3)
        val att2 = Monster(30, 1, 100, 40..50)
        var damage = 0
        while (pl2.health.value == 100) damage = att2.performAttack(pl2)
        pl2.regenHealth()
        assertEquals(100 - damage + 30, pl2.health.value)
    }
}