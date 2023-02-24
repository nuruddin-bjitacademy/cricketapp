package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class LeagueById(
    @Json(name = "data")
    var `data`: Data?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "season_id")
        var seasonId: Int?,
        @Json(name = "country_id")
        var countryId: Int?,
        @Json(name = "name")
        var name: String?,
        @Json(name = "code")
        var code: String?,
        @Json(name = "image_path")
        var imagePath: String?,
        @Json(name = "type")
        var type: String?,
        @Json(name = "updated_at")
        var updatedAt: String?
    )
}