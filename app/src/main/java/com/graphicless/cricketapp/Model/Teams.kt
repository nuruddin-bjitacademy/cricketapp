package com.graphicless.cricketapp.Model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Teams(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Entity(tableName = "Team")
    data class Data(
        @Json(name = "resource")
        var resource: String?= null,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "name")
        var name: String?= null,
        @Json(name = "code")
        var code: String?= null,
        @Json(name = "image_path")
        var imagePath: String?= null,
        @Json(name = "country_id")
        var countryId: Int?= null,
        @Json(name = "national_team")
        var nationalTeam: Boolean?= null,
        @Json(name = "updated_at")
        var updatedAt: String?= null
    )
}