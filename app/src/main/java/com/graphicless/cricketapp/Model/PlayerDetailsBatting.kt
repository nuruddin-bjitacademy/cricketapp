package com.graphicless.cricketapp.Model

data class PlayerDetailsBatting(
    val matches: Int,
    val runs: Int,
    val innings: Int,
    val balls: Int,
    val highScore: Int,
    val s100: Int,
    val s50: Int,
    val average: Double,
    val notOut: Int,
    val s4: Int,
    val s6: Int,
    val strikeRate: Double
)