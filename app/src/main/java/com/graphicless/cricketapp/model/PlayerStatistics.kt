package com.graphicless.cricketapp.model
data class PlayerStatistics(
    val name: String,
    val runs: Int,
    val balls: Int,
    val sixes: Int,
    val fours: Int,
    val strikeRate: Double
)
