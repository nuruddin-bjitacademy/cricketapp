package com.graphicless.cricketapp.Model.map

import androidx.room.Entity

@Entity
data class FixtureDetails(
    val fixtureId: Int,
    val seasonId: Int,
    val stageId: Int,
    val runId: Int,
    val stageName: String,
    val startingAT: String,
    val round: String,
    val venue: String,
    val teamOneId: Int,
    val teamTwoId: Int,
    val teamOneCode: String,
    val teamTwoCode: String,
    val teamOneFlag: String,
    val teamTwoFlag: String,
    val note: String,

    val localTeamName: String,
    val visitorTeamName: String,
    val tossWinTeamName: String,
    val elected: String,
    val stadiumName: String,
//    val venueCountry: String,
    val capacity: Int,
    val floodLight: String,
    val firstUmpire: String,
    val secondUmpire: String,
    val tvUmpire: String,
    val referee: String,
    val status: String,
    val manOfTheMatchId: Int
)

