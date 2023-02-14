package com.graphicless.cricketapp.temp.joined

import androidx.room.Embedded

data class test(
    val name: String,
    val fixtures: List<FixtureAndTeam>
)
