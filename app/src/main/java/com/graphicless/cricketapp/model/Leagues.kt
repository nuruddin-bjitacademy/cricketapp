package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Leagues(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Entity(tableName = "League")
    data class Data(
        @Json(name = "resource")
        var resource: String?= null,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "season_id")
        var seasonId: Int?= null,
        @Json(name = "country_id")
        var countryId: Int?= null,
        @Json(name = "name")
        var name: String?= null,
        @Json(name = "code")
        var code: String?= null,
        @Json(name = "image_path")
        var imagePath: String?= null,
        @Json(name = "type")
        var type: String?= null,
        @Json(name = "updated_at")
        var updatedAt: String?= null
    )
}