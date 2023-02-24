package com.graphicless.cricketapp.Model

import java.util.*

data class Match(
    val id: Int,
    val team1: String = "",
    val team2: String = "",
    val venue: String? = null,
    val startTime: String = ""
)