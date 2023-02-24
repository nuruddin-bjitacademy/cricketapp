package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class TeamRankings(
    @Json(name = "data")
    var `data`: List<Data?>?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "type")
        var type: String?,
        @Json(name = "position")
        var position: Any?,
        @Json(name = "points")
        var points: Any?,
        @Json(name = "rating")
        var rating: Any?,
        @Json(name = "gender")
        var gender: String?,
        @Json(name = "updated_at")
        var updatedAt: String?,
        @Json(name = "team")
        var team: List<Team?>?
    ) {
        data class Team(
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
            @Json(name = "position")
            var position: Int?,
            @Json(name = "updated_at")
            var updatedAt: String?,
            @Json(name = "ranking")
            var ranking: Ranking?
        ) {
            data class Ranking(
                @Json(name = "position")
                var position: Int?,
                @Json(name = "matches")
                var matches: Int?,
                @Json(name = "points")
                var points: Int?,
                @Json(name = "rating")
                var rating: Int?
            )
        }
    }
}