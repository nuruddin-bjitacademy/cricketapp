package com.graphicless.cricketapp.temp.joined

data class FixtureAndStage(
    val stageId: Long,
    val stageName: String,
    val fixtures: List<FixtureAndTeam>
)
