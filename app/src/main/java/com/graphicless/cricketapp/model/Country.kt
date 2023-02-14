package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity(tableName = "Country", foreignKeys = [ForeignKey(entity = Continent::class, parentColumns = ["id"], childColumns = ["continent_id"] )]  )
@JsonClass(generateAdapter = true)
data class Country(
    val continent_id: Int,
    @PrimaryKey
    val id: Int,
    val image_path: String,
    val name: String,
    val resource: String,
    val updated_at: String?
)