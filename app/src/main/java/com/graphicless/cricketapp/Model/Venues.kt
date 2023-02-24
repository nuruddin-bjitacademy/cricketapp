package com.graphicless.cricketapp.Model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Venues(
    @Json(name = "data")
    val `data`: List<Data>?
) {
    @Entity(tableName = "Venue")
    data class Data(
        @Json(name = "resource")
        var resource: String?= null,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "country_id")
        var countryId: Int?= null,
        @Json(name = "name")
        var name: String?= null,
        @Json(name = "city")
        var city: String?= null,
        @Json(name = "image_path")
        var imagePath: String?= null,
        @Json(name = "capacity")
        var capacity: Int?= null,
        @Json(name = "floodlight")
        var floodlight: Boolean?= null,
        @Json(name = "updated_at")
        var updatedAt: String?= null
    )
}