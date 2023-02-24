package com.graphicless.cricketapp.Model


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Stages(
    @Json(name = "data")
    val `data`: List<Data>
) {
    @Entity(tableName = "Stage")
    data class Data(
        @Json(name = "resource")
        var resource: String? = "",
        @Json(name = "id")
        @PrimaryKey(autoGenerate = false)
        var id: Int = 0,
        @Json(name = "league_id")
        var leagueId: Int? = 0,
        @Json(name = "season_id")
        var seasonId: Int? = 0,
        @Json(name = "name")
        var name: String? = "",
        @Json(name = "code")
        var code: String? = "",
        @Json(name = "type")
        var type: String? = "",
        @Json(name = "standings")
        var standings: Boolean? = false,
        @Json(name = "updated_at")
        var updatedAt: String? = ""
    )
}