package com.example.eero.pubg.remote

import com.example.eero.pubg.Victim
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


object Model {
    data class Response(val results: Results,
                        @SerializedName("included") val included: List<Data>)

    data class Response2(val results: Results,
                        @SerializedName("included") val included: List<Data>,
                        @SerializedName("data") val data: List<Data>)

    data class Data(@SerializedName("type") val type: String,
                    @SerializedName("id") val id: String,
                    @SerializedName("attributes") val attributes: Attributes,
                    @SerializedName("relationships") val relationships: Relationships)

    data class Attributes(@SerializedName("URL") val url: String)

    data class Relationships( @SerializedName("matches") val matches: Matches)

    data class Matches ( @SerializedName("data") val data: List<MatchData>)

    data class MatchData ( @SerializedName("type") val type: String,
                           @SerializedName("id") val id: String)


    data class Results(@SerializedName("_D") val _D: String,
                       @SerializedName("_T") val _T: String,
                       @SerializedName("attacker") val attacker: Attacker,
                       @SerializedName("victim") val victim: Victim,
                       @SerializedName("damageReason") val damageReason: String,
                       @SerializedName("damage") val damage: Double,
                       @SerializedName("damageCauserName") var damageCauserName: String,

                       @SerializedName("killer") val killer: Killer,
                       @SerializedName("assistant") val assistant: Assistant)

    data class Attacker(
        @SerializedName("name") val name: String,
        @SerializedName("teamId") val teamId: Int,
        @SerializedName("health") val health: Double
    )

    data class Killer (
        @SerializedName("name") val name: String,
        val assistant: String
    )

    data class Assistant (
        @SerializedName("name") val name: String
    )

    data class Victim (
        @SerializedName("name") val name: String,
        @SerializedName("teamId") val teamId: Int,
        @SerializedName("health") val health: Double
    )


    lateinit var matchStartedAt: LocalDateTime
    val playerDamagers = mutableListOf<Results>()
    val playerKillers = mutableListOf<Results>()
    val playerKnockers = mutableListOf<Results>()

    val playerD = mutableListOf<Results>()
    val playerK = mutableListOf<Results>()
    val playerG = mutableListOf<Results>()

    val matchList = mutableListOf<String>()

    val victimList = hashMapOf<String, com.example.eero.pubg.Victim>()
    val victimNamesList = mutableListOf<String>()
}