package com.graphicless.cricketapp.Model.joined

data class FixtureAndStage(
    val stageId: Long,
    val stageName: String,
    val fixtures: List<FixtureAndTeam>
)
