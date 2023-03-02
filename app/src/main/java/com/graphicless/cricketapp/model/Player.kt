package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class Player(
    @Json(name = "data")
    var `data`: Data?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "country_id")
        var countryId: Int?,
        @Json(name = "firstname")
        var firstname: String?,
        @Json(name = "lastname")
        var lastname: String?,
        @Json(name = "fullname")
        var fullname: String?,
        @Json(name = "image_path")
        var imagePath: String?,
        @Json(name = "dateofbirth")
        var dateofbirth: String?,
        @Json(name = "gender")
        var gender: String?,
        @Json(name = "battingstyle")
        var battingstyle: String?,
        @Json(name = "bowlingstyle")
        var bowlingstyle: Any?,
        @Json(name = "position")
        var position: Position?,
        @Json(name = "updated_at")
        var updatedAt: String?
    ) {
        data class Position(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "name")
            var name: String?
        )
    }
}