package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "Continent")
@JsonClass(generateAdapter = true)
data class Continent(
    @PrimaryKey
    val id: Int,
    val name: String,
    val resource: String,
    val updated_at: String?
)