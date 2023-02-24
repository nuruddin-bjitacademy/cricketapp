package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class FixtureWithLineUp(
    @Json(name = "data")
    var `data`: Data
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "id")
        var id: Int?,
        @Json(name = "league_id")
        var leagueId: Int?,
        @Json(name = "season_id")
        var seasonId: Int?,
        @Json(name = "stage_id")
        var stageId: Int?,
        @Json(name = "round")
        var round: String?,
        @Json(name = "localteam_id")
        var localteamId: Int?,
        @Json(name = "visitorteam_id")
        var visitorteamId: Int?,
        @Json(name = "starting_at")
        var startingAt: String?,
        @Json(name = "type")
        var type: String?,
        @Json(name = "live")
        var live: Boolean?,
        @Json(name = "status")
        var status: String?,
        @Json(name = "last_period")
        var lastPeriod: Any?,
        @Json(name = "note")
        var note: String?,
        @Json(name = "venue_id")
        var venueId: Int?,
        @Json(name = "toss_won_team_id")
        var tossWonTeamId: Int?,
        @Json(name = "winner_team_id")
        var winnerTeamId: Int?,
        @Json(name = "draw_noresult")
        var drawNoresult: Any?,
        @Json(name = "first_umpire_id")
        var firstUmpireId: Int?,
        @Json(name = "second_umpire_id")
        var secondUmpireId: Int?,
        @Json(name = "tv_umpire_id")
        var tvUmpireId: Int?,
        @Json(name = "referee_id")
        var refereeId: Int?,
        @Json(name = "man_of_match_id")
        var manOfMatchId: Int?,
        @Json(name = "man_of_series_id")
        var manOfSeriesId: Any?,
        @Json(name = "total_overs_played")
        var totalOversPlayed: Int?,
        @Json(name = "elected")
        var elected: String?,
        @Json(name = "super_over")
        var superOver: Boolean?,
        @Json(name = "follow_on")
        var followOn: Boolean?,
        @Json(name = "localteam_dl_data")
        var localteamDlData: LocalteamDlData?,
        @Json(name = "visitorteam_dl_data")
        var visitorteamDlData: VisitorteamDlData?,
        @Json(name = "rpc_overs")
        var rpcOvers: String?,
        @Json(name = "rpc_target")
        var rpcTarget: String?,
        @Json(name = "weather_report")
        var weatherReport: List<Any>?,
        @Json(name = "lineup")
        var lineup: List<Lineup>?
    ) {
        data class LocalteamDlData(
            @Json(name = "score")
            var score: String?,
            @Json(name = "overs")
            var overs: String?,
            @Json(name = "wickets_out")
            var wicketsOut: String?
        )

        data class VisitorteamDlData(
            @Json(name = "score")
            var score: String?,
            @Json(name = "overs")
            var overs: String?,
            @Json(name = "wickets_out")
            var wicketsOut: String?
        )

        data class Lineup(
            @Json(name = "resource")
            var resource: String,
            @Json(name = "id")
            var id: Int,
            @Json(name = "country_id")
            var countryId: Int,
            @Json(name = "firstname")
            var firstname: String,
            @Json(name = "lastname")
            var lastname: String,
            @Json(name = "fullname")
            var fullname: String,
            @Json(name = "image_path")
            var imagePath: String,
            @Json(name = "dateofbirth")
            var dateofbirth: String,
            @Json(name = "gender")
            var gender: String,
            @Json(name = "battingstyle")
            var battingstyle: String?,
            @Json(name = "bowlingstyle")
            var bowlingstyle: String?,
            @Json(name = "position")
            var position: Position,
            @Json(name = "updated_at")
            var updatedAt: String,
            @Json(name = "lineup")
            var lineup: Lineup
        ) {
            data class Position(
                @Json(name = "resource")
                var resource: String,
                @Json(name = "id")
                var id: Int,
                @Json(name = "name")
                var name: String
            )

            data class Lineup(
                @Json(name = "team_id")
                var teamId: Int,
                @Json(name = "captain")
                var captain: Boolean,
                @Json(name = "wicketkeeper")
                var wicketkeeper: Boolean,
                @Json(name = "substitution")
                var substitution: Boolean
            )
        }
    }
}