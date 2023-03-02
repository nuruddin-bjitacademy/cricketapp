package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Countries(
    @Json(name = "data")
    var `data`: List<Data>
) {
    @Entity(tableName = "Country")
    data class Data(
        @Json(name = "resource")
        var resource: String? = "",
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "continent_id")
        var continentId: Int?= 0,
        @Json(name = "name")
        var name: String?= "",
        @Json(name = "image_path")
        var imagePath: String?= "",
        @Json(name = "updated_at")
        var updatedAt: String?= ""
    )
}