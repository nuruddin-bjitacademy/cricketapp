package com.graphicless.cricketapp.temp.joined

import androidx.room.Entity

@Entity
data class FixtureAndTeam(
    val fixtureId: Int,
    val seasonId: Int,
    val stageId: Int,
    val runId: Int,
    val stageName: String,
    val startingAT: String,
    val round: String,
    val venue: String?,
    val teamOneId: Int,
    val teamTwoId: Int,
    val teamOneCode: String,
    val teamTwoCode: String,
    val teamOneFlag: String,
    val teamTwoFlag: String,
    val note: String
)
