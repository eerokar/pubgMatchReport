package com.example.eero.pubg

    data class Victim (
        var name: String,
        val damageReasons: MutableList<String> = mutableListOf(),
        val damages: MutableList<Double> = mutableListOf(),
        var damageCauserNames: MutableList<String> = mutableListOf(),
        var isKnocked: Boolean = false,
        val timeStamps: MutableList<String> = mutableListOf()
    )
