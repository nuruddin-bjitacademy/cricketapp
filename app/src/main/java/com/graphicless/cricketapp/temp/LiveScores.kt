package com.graphicless.cricketapp.temp


import com.squareup.moshi.Json

data class LiveScores(
    @Json(name = "data")
    var `data`: List<Data?>?
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
        var winnerTeamId: Any?,
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
        var manOfMatchId: Any?,
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
        var rpcOvers: Any?,
        @Json(name = "rpc_target")
        var rpcTarget: Any?,
        @Json(name = "weather_report")
        var weatherReport: List<Any?>?
    ) {
        data class LocalteamDlData(
            @Json(name = "score")
            var score: Any?,
            @Json(name = "overs")
            var overs: Any?,
            @Json(name = "wickets_out")
            var wicketsOut: Any?
        )

        data class VisitorteamDlData(
            @Json(name = "score")
            var score: Any?,
            @Json(name = "overs")
            var overs: Any?,
            @Json(name = "wickets_out")
            var wicketsOut: Any?
        )
    }
}