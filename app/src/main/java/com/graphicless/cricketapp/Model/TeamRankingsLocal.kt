package com.graphicless.cricketapp.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TeamRanking")
data class TeamRankingsLocal(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val format: String? = null,
    val gender: String? = null,
    val teamId: Int? = null,
    val teamName: String? = null,
    val flag: String? = null,
    val position: Int? = null,
    val matches: Int? = null,
    val points: Int? = null,
    val ratings: Int? = null
)