package com.graphicless.cricketapp.model

data class Match(
    val id: Int,
    val team1: String = "",
    val team2: String = "",
    val venue: String? = null,
    val startTime: String = ""
)