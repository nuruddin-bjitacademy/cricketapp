package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class PlayerDetailsNew(
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
        var bowlingstyle: String?,
        @Json(name = "position")
        var position: Position?,
        @Json(name = "country")
        var country: Country?,
        @Json(name = "updated_at")
        var updatedAt: String?,
        @Json(name = "career")
        var career: List<Career?>?,
        @Json(name = "teams")
        var teams: List<Team?>?,
        @Json(name = "currentteams")
        var currentteams: List<Currentteam?>?
    ) {
        data class Position(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "name")
            var name: String?
        )

        data class Country(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "continent_id")
            var continentId: Int?,
            @Json(name = "name")
            var name: String?,
            @Json(name = "image_path")
            var imagePath: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Career(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "type")
            var type: String?,
            @Json(name = "season_id")
            var seasonId: Int?,
            @Json(name = "player_id")
            var playerId: Int?,
            @Json(name = "bowling")
            var bowling: Bowling?,
            @Json(name = "batting")
            var batting: Batting?,
            @Json(name = "updated_at")
            var updatedAt: String?,
            @Json(name = "season")
            var season: Season?
        ) {
            data class Bowling(
                @Json(name = "matches")
                var matches: Int?,
                @Json(name = "overs")
                var overs: Double?,
                @Json(name = "innings")
                var innings: Int?,
                @Json(name = "average")
                var average: Double?,
                @Json(name = "econ_rate")
                var econRate: Double?,
                @Json(name = "medians")
                var medians: Int?,
                @Json(name = "runs")
                var runs: Int?,
                @Json(name = "wickets")
                var wickets: Int?,
                @Json(name = "wide")
                var wide: Int?,
                @Json(name = "noball")
                var noball: Int?,
                @Json(name = "strike_rate")
                var strikeRate: Double?,
                @Json(name = "four_wickets")
                var fourWickets: Int?,
                @Json(name = "five_wickets")
                var fiveWickets: Int?,
                @Json(name = "ten_wickets")
                var tenWickets: Int?,
                @Json(name = "rate")
                var rate: Double?
            )

            data class Batting(
                @Json(name = "matches")
                var matches: Int?,
                @Json(name = "innings")
                var innings: Int?,
                @Json(name = "runs_scored")
                var runsScored: Int?,
                @Json(name = "not_outs")
                var notOuts: Int?,
                @Json(name = "highest_inning_score")
                var highestInningScore: Int?,
                @Json(name = "strike_rate")
                var strikeRate: Double?,
                @Json(name = "balls_faced")
                var ballsFaced: Int?,
                @Json(name = "average")
                var average: Double?,
                @Json(name = "four_x")
                var fourX: Int?,
                @Json(name = "six_x")
                var sixX: Int?,
                @Json(name = "fow_score")
                var fowScore: Int?,
                @Json(name = "fow_balls")
                var fowBalls: Double?,
                @Json(name = "hundreds")
                var hundreds: Int?,
                @Json(name = "fifties")
                var fifties: Int?
            )

            data class Season(
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
            @Json(name = "updated_at")
            var updatedAt: String?,
            @Json(name = "in_squad")
            var inSquad: InSquad?
        ) {
            data class InSquad(
                @Json(name = "season_id")
                var seasonId: Int?,
                @Json(name = "league_id")
                var leagueId: Int?
            )
        }

        data class Currentteam(
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
            @Json(name = "in_squad")
            var inSquad: InSquad?
        ) {
            data class InSquad(
                @Json(name = "season_id")
                var seasonId: Int?,
                @Json(name = "league_id")
                var leagueId: Int?
            )
        }
    }
}