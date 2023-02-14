package com.graphicless.cricketapp.temp


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Teams(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Entity(tableName = "Team")
    data class Data(
        @Ignore
        @Json(name = "resource")
        var resource: String,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "name")
        var name: String,
        @Json(name = "code")
        var code: String,
        @Json(name = "image_path")
        var imagePath: String,
        @Json(name = "country_id")
        var countryId: Int,
        @Json(name = "national_team")
        var nationalTeam: Boolean,
        @Ignore
        @Json(name = "updated_at")
        var updatedAt: String
    ) {
        constructor() : this(
            resource = "",
            id = 0,
            name = "",
            code = "",
            imagePath = "",
            countryId = 0,
            nationalTeam = false,
            updatedAt = ""
        )
    }
}