package com.graphicless.cricketapp.Model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Seasons(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Entity(tableName = "Season")
    data class Data(
        @Json(name = "resource")
        var resource: String? = "",
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "league_id")
        var leagueId: Int? = 0,
        @Json(name = "name")
        var name: String? = "",
        @Json(name = "code")
        var code: String? = "",
        @Json(name = "updated_at")
        var updatedAt: String? = ""
    )
}