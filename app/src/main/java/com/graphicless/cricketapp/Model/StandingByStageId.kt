package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class StandingByStageId(
    @Json(name = "data")
    var `data`: List<Data?>?
) {
    data class Data(
        @Json(name = "resource")
        var resource: String?,
        @Json(name = "legend_id")
        var legendId: Int?,
        @Json(name = "team_id")
        var teamId: Int?,
        @Json(name = "stage_id")
        var stageId: Int?,
        @Json(name = "season_id")
        var seasonId: Int?,
        @Json(name = "league_id")
        var leagueId: Int?,
        @Json(name = "position")
        var position: Int?,
        @Json(name = "points")
        var points: Int?,
        @Json(name = "played")
        var played: Int?,
        @Json(name = "won")
        var won: Int?,
        @Json(name = "lost")
        var lost: Int?,
        @Json(name = "draw")
        var draw: Int?,
        @Json(name = "noresult")
        var noresult: Int?,
        @Json(name = "runs_for")
        var runsFor: Int?,
        @Json(name = "overs_for")
        var oversFor: Double?,
        @Json(name = "runs_against")
        var runsAgainst: Int?,
        @Json(name = "overs_against")
        var oversAgainst: Double?,
        @Json(name = "netto_run_rate")
        var nettoRunRate: Double?,
        @Json(name = "recent_form")
        var recentForm: List<String?>?,
        @Json(name = "updated_at")
        var updatedAt: String?,
        @Json(name = "legend")
        var legend: Legend?
    ) {
        data class Legend(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "stage_id")
            var stageId: Int?,
            @Json(name = "season_id")
            var seasonId: Int?,
            @Json(name = "league_id")
            var leagueId: Int?,
            @Json(name = "position")
            var position: Int?,
            @Json(name = "description")
            var description: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )
    }
}