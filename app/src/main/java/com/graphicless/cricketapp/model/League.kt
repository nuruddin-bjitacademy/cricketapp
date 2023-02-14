package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "League")
@JsonClass(generateAdapter = true)
data class League(
    val code: String,
    val country_id: Int,
    @PrimaryKey
    val id: Int,
    val image_path: String,
    val name: String,
    val resource: String,
    val season_id: Int,
    val type: String,
    val updated_at: String
)