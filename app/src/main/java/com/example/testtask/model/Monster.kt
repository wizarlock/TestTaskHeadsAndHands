package com.example.testtask.model

class Monster(
    attack: Int,
    defense: Int,
    health: Int,
    damageRange: IntRange
) : Creature(attack, defense, health, damageRange)