package com.graphicless.cricketapp.Model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Officials(
    @Json(name = "data")
    var `data`: List<Data>?
) {
    @Entity(tableName = "Official")
    data class Data(
        @Json(name = "resource")
        var resource: String? = "",
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "country_id")
        var countryId: Int? = 0,
        @Json(name = "firstname")
        var firstname: String? = "",
        @Json(name = "lastname")
        var lastname: String? = "",
        @Json(name = "fullname")
        var fullname: String? = "",
        @Json(name = "dateofbirth")
        var dateofbirth: String? = "",
        @Json(name = "gender")
        var gender: String? = "",
        @Json(name = "updated_at")
        var updatedAt: String? = ""
    )
}