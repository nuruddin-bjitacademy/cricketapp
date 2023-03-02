package com.graphicless.cricketapp.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class FixturesIncludeRuns(
    @Json(name = "data")
    var `data`: List<Data>?,
    @Json(name = "links")
    var links: Links?,
    @Json(name = "meta")
    var meta: Meta?
) {
    @Entity(tableName = "Fixture")
    data class Data(
        @Ignore
        @Json(name = "resource")
        var resource: String? = "",
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int = 0,
        @Json(name = "league_id")
        var leagueId: Int? = 0,
        @Json(name = "season_id")
        var seasonId: Int? = 0,
        @Json(name = "stage_id")
        var stageId: Int? = 0,
        @Json(name = "round")
        var round: String? = "",
        @Json(name = "localteam_id")
        var localteamId: Int? = 0,
        @Json(name = "visitorteam_id")
        var visitorteamId: Int? = 0,
        @Json(name = "starting_at")
        var startingAt: String? = "",
        @Json(name = "type")
        var type: String? = "",
        @Json(name = "live")
        var live: Boolean? = false,
        @Json(name = "status")
        var status: String? = "",
        @Json(name = "last_period")
        @Ignore
        var lastPeriod: Any? = null,
        @Json(name = "note")
        var note: String? = "",
        @Json(name = "venue_id")
        var venueId: Int? = 0,
        @Json(name = "toss_won_team_id")
        var tossWonTeamId: Int? = 0,
        @Json(name = "winner_team_id")
        var winnerTeamId: Int? = 0,
        @Json(name = "draw_noresult")
        var drawNoresult: String? = "",
        @Json(name = "first_umpire_id")
        var firstUmpireId: Int? = 0,
        @Json(name = "second_umpire_id")
        var secondUmpireId: Int? = 0,
        @Json(name = "tv_umpire_id")
        var tvUmpireId: Int? = 0,
        @Json(name = "referee_id")
        var refereeId: Int? = 0,
        @Json(name = "man_of_match_id")
        var manOfMatchId: Int? = 0,
        @Json(name = "man_of_series_id")
        var manOfSeriesId: Int? = 0,
        @Json(name = "total_overs_played")
        var totalOversPlayed: Int? = 0,
        @Json(name = "elected")
        var elected: String? = "",
        @Ignore
        @Json(name = "super_over")
        var superOver: Boolean? =false,
        @Ignore
        @Json(name = "follow_on")
        var followOn: Boolean? = false,
        @Ignore
        @Json(name = "localteam_dl_data")
        var localteamDlData: LocalteamDlData? = null,
        @Ignore
        @Json(name = "visitorteam_dl_data")
        var visitorteamDlData: VisitorteamDlData? = null,
        @Ignore
        @Json(name = "rpc_overs")
        var rpcOvers: String? = "",
        @Ignore
        @Json(name = "rpc_target")
        var rpcTarget: String? = "",
        @Ignore
        @Json(name = "weather_report")
        var weatherReport: List<Any>? = null,
        @Ignore
        @Json(name = "runs")
//        @TypeConverters(TypeConverter::class)
        var runs: List<Run>? = null
    ) {
        data class LocalteamDlData(
            @Json(name = "score")
            var score: String?,
            @Json(name = "overs")
            var overs: String?,
            @Json(name = "wickets_out")
            var wicketsOut: String?,
            @Json(name = "rpc_overs")
            var rpcOvers: String?,
            @Json(name = "rpc_targets")
            var rpcTargets: String?
        )

        data class VisitorteamDlData(
            @Json(name = "score")
            var score: String?,
            @Json(name = "overs")
            var overs: String?,
            @Json(name = "wickets_out")
            var wicketsOut: String?,
            @Json(name = "total_overs_played")
            var totalOversPlayed: String?
        )
        @Entity(tableName = "Run")
        data class Run(
            @Ignore
            @Json(name = "resource")
            var resource: String? = "",
            @Json(name = "id")
            @PrimaryKey(autoGenerate = false)
            var id: Int = 0,
            @Json(name = "fixture_id")
            var fixtureId: Int? = 0,
            @Json(name = "team_id")
            var teamId: Int? = 0,
            @Json(name = "inning")
            var inning: Int? = 0,
            @Json(name = "score")
            var score: Int? = 0,
            @Json(name = "wickets")
            var wickets: Int? = 0,
            @Json(name = "overs")
            var overs: Double? = 0.0,
            @Json(name = "pp1")
            var pp1: String? = null,
            @Json(name = "pp2")
            var pp2: String? = null,
            @Json(name = "pp3")
            var pp3: String? = null,
            @Ignore
            @Json(name = "updated_at")
            var updatedAt: String? = ""
        )
    }

    data class Links(
        @Json(name = "first")
        var first: String?,
        @Json(name = "last")
        var last: String?,
        @Json(name = "prev")
        var prev: Any?,
        @Json(name = "next")
        var next: String?
    )

    data class Meta(
        @Json(name = "current_page")
        var currentPage: Int?,
        @Json(name = "from")
        var from: Int?,
        @Json(name = "last_page")
        var lastPage: Int?,
        @Json(name = "links")
        var links: List<Link>?,
        @Json(name = "path")
        var path: String?,
        @Json(name = "per_page")
        var perPage: Int?,
        @Json(name = "to")
        var to: Int?,
        @Json(name = "total")
        var total: Int?
    ) {
        data class Link(
            @Json(name = "url")
            var url: String?,
            @Json(name = "label")
            var label: String?,
            @Json(name = "active")
            var active: Boolean?
        )
    }
}