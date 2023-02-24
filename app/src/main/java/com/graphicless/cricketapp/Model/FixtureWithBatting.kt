package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class FixtureWithBatting(
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
        @Json(name = "batting")
        var batting: List<Batting>?
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

        data class Batting(
            @Json(name = "resource")
            var resource: String,
            @Json(name = "id")
            var id: Int,
            @Json(name = "sort")
            var sort: Int,
            @Json(name = "fixture_id")
            var fixtureId: Int,
            @Json(name = "team_id")
            var teamId: Int,
            @Json(name = "active")
            var active: Boolean,
            @Json(name = "scoreboard")
            var scoreboard: String,
            @Json(name = "player_id")
            var playerId: Int,
            @Json(name = "ball")
            var ball: Int,
            @Json(name = "score_id")
            var scoreId: Int,
            @Json(name = "score")
            var score: Int,
            @Json(name = "four_x")
            var fourX: Int,
            @Json(name = "six_x")
            var sixX: Int,
            @Json(name = "catch_stump_player_id")
            var catchStumpPlayerId: Int?,
            @Json(name = "runout_by_id")
            var runoutById: Any?,
            @Json(name = "batsmanout_id")
            var batsmanoutId: Any?,
            @Json(name = "bowling_player_id")
            var bowlingPlayerId: Int?,
            @Json(name = "fow_score")
            var fowScore: Int,
            @Json(name = "fow_balls")
            var fowBalls: Double,
            @Json(name = "rate")
            var rate: Double,
            @Json(name = "updated_at")
            var updatedAt: String
        )
    }
}