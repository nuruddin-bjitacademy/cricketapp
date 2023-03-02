package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class SeasonById(
    @Json(name = "data")
    var `data`: Data?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "league_id")
        var leagueId: Int?,
        @Json(name = "name")
        var name: String?,
        @Json(name = "code")
        var code: String?,
        @Json(name = "updated_at")
        var updatedAt: String?
    )
}