package com.graphicless.cricketapp.temp


import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

data class Fix(
    @Json(name = "data")
    val `data`: List<Data>,
    @Json(name = "links")
    val links: Links,
    @Json(name = "meta")
    val meta: Meta
) {
    @Entity(tableName = "Fixture")
    data class Data(
        @Ignore
        @Json(name = "resource")
        var resource: String?,
        @PrimaryKey(autoGenerate = false)
        @Json(name = "id")
        var id: Int,
        @Json(name = "league_id")
        var leagueId: Int,
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
        @Ignore
        var live: Boolean?,
        @Json(name = "status")
        var status: String?,
        @Json(name = "last_period")
        @Ignore
        var lastPeriod: String?,
        @Json(name = "note")
        var note: String?,
        @Json(name = "venue_id")
        var venueId: Int?,
        @Json(name = "toss_won_team_id")
        var tossWonTeamId: Int?,
        @Json(name = "winner_team_id")
        var winnerTeamId: Int?,
        @Json(name = "draw_noresult")
        var drawNoresult: String?,
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
        var manOfSeriesId: Int?,
        @Json(name = "total_overs_played")
        var totalOversPlayed: Int?,
        @Json(name = "elected")
        var elected: String?,
        @Ignore
        @Json(name = "super_over")
        var superOver: Boolean?,
        @Ignore
        @Json(name = "follow_on")
        var followOn: Boolean?,
        @Ignore
        @Json(name = "localteam_dl_data")
        var localteamDlData: LocalteamDlData?,
        @Ignore
        @Json(name = "visitorteam_dl_data")
        var visitorteamDlData: VisitorteamDlData?,
        @Ignore
        @Json(name = "rpc_overs")
        var rpcOvers: String?,
        @Ignore
        @Json(name = "rpc_target")
        var rpcTarget: String?,
        @Ignore
        @Json(name = "weather_report")
        var weatherReport: List<Any>?
    ) {
        constructor() : this(
            resource = "",
            id = 0,
            leagueId = 0,
            seasonId = 0,
            stageId = 0,
            round = "",
            localteamId = 0,
            visitorteamId = 0,
            startingAt = "",
            type = "",
            live = false,
            status = "",
            lastPeriod = null,
            note = "",
            venueId = 0,
            tossWonTeamId = null,
            winnerTeamId = null,
            drawNoresult = null,
            firstUmpireId = null,
            secondUmpireId = null,
            tvUmpireId = null,
            refereeId = null,
            manOfMatchId = null,
            manOfSeriesId = null,
            totalOversPlayed = null,
            elected = null,
            superOver = false,
            followOn = false,
            localteamDlData = LocalteamDlData(score = null, overs = null, wicketsOut = null, rpcOvers = null, rpcTargets = null),
            visitorteamDlData = VisitorteamDlData(score = null, overs = null, wicketsOut = null, totalOversPlayed = null),
            rpcOvers = null,
            rpcTarget = null,
            weatherReport = emptyList()
        )
        data class LocalteamDlData(
            @Json(name = "score")
            val score: String?,
            @Json(name = "overs")
            val overs: String?,
            @Json(name = "wickets_out")
            val wicketsOut: String?,
            @Json(name = "rpc_overs")
            val rpcOvers: String?,
            @Json(name = "rpc_targets")
            val rpcTargets: String?
        )

        data class VisitorteamDlData(
            @Json(name = "score")
            val score: String?,
            @Json(name = "overs")
            val overs: String?,
            @Json(name = "wickets_out")
            val wicketsOut: String?,
            @Json(name = "total_overs_played")
            val totalOversPlayed: String?
        )
    }

    data class Links(
        @Json(name = "first")
        val first: String,
        @Json(name = "last")
        val last: String,
        @Json(name = "prev")
        val prev: Any?,
        @Json(name = "next")
        val next: String?
    )

    data class Meta(
        @Json(name = "current_page")
        val currentPage: Int,
        @Json(name = "from")
        val from: Int,
        @Json(name = "last_page")
        val lastPage: Int,
        @Json(name = "links")
        val links: List<Link>,
        @Json(name = "path")
        val path: String,
        @Json(name = "per_page")
        val perPage: Int,
        @Json(name = "to")
        val to: Int,
        @Json(name = "total")
        val total: Int
    ) {
        data class Link(
            @Json(name = "url")
            val url: String?,
            @Json(name = "label")
            val label: String,
            @Json(name = "active")
            val active: Boolean
        )
    }
}