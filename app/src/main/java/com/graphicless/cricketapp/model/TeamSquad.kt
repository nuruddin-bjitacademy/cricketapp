package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class TeamSquad(
    @Json(name = "data")
    var `data`: Data?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "name")
        var name: String?,
        @Json(name = "code")
        var code: String?,
        @Json(name = "image_path")
        var imagePath: String?,
        @Json(name = "country_id")
        var countryId: Int?,
        @Json(name = "national_team")
        var nationalTeam: Boolean?,
        @Json(name = "updated_at")
        var updatedAt: String?,
        @Json(name = "squad")
        var squad: List<Squad?>?
    ) {
        data class Squad(
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
            var bowlingstyle: String?,
            @Json(name = "position")
            var position: Position?,
            @Json(name = "updated_at")
            var updatedAt: String?,
            @Json(name = "squad")
            var squad: Squad?
        ) {
            data class Position(
                @Json(name = "resource")
                var resource: String?,
                @Json(name = "id")
                var id: Int?,
                @Json(name = "name")
                var name: String?
            )

            data class Squad(
                @Json(name = "season_id")
                var seasonId: Int?
            )
        }
    }
}