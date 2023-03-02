package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class FixturesByTeamId(
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
        @Json(name = "fixtures")
        var fixtures: List<Fixture?>?
    ) {
        data class Fixture(
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
            var venueId: Any?,
            @Json(name = "toss_won_team_id")
            var tossWonTeamId: Any?,
            @Json(name = "winner_team_id")
            var winnerTeamId: Any?,
            @Json(name = "draw_noresult")
            var drawNoresult: Any?,
            @Json(name = "first_umpire_id")
            var firstUmpireId: Any?,
            @Json(name = "second_umpire_id")
            var secondUmpireId: Any?,
            @Json(name = "tv_umpire_id")
            var tvUmpireId: Any?,
            @Json(name = "referee_id")
            var refereeId: Any?,
            @Json(name = "man_of_match_id")
            var manOfMatchId: Any?,
            @Json(name = "man_of_series_id")
            var manOfSeriesId: Any?,
            @Json(name = "total_overs_played")
            var totalOversPlayed: Any?,
            @Json(name = "elected")
            var elected: Any?,
            @Json(name = "super_over")
            var superOver: Boolean?,
            @Json(name = "follow_on")
            var followOn: Boolean?,
            @Json(name = "localteam_dl_data")
            var localteamDlData: Any?,
            @Json(name = "visitorteam_dl_data")
            var visitorteamDlData: Any?,
            @Json(name = "rpc_overs")
            var rpcOvers: Any?,
            @Json(name = "rpc_target")
            var rpcTarget: Any?,
            @Json(name = "weather_report")
            var weatherReport: List<Any?>?
        )
    }
}