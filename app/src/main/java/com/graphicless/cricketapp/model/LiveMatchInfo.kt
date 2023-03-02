package com.graphicless.cricketapp.model

import com.squareup.moshi.Json

data class LiveMatchInfo(
    @Json(name = "data")
    var `data`: Data?
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
        var tvUmpireId: Any?,
        @Json(name = "referee_id")
        var refereeId: Int?,
        @Json(name = "man_of_match_id")
        var manOfMatchId: Any?,
        @Json(name = "man_of_series_id")
        var manOfSeriesId: Any?,
        @Json(name = "total_overs_played")
        var totalOversPlayed: Any?,
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
        var weatherReport: List<Any?>?,
        @Json(name = "localteam")
        var localteam: Localteam?,
        @Json(name = "visitorteam")
        var visitorteam: Visitorteam?,
        @Json(name = "referee")
        var referee: Referee?,
        @Json(name = "firstumpire")
        var firstumpire: Firstumpire?,
        @Json(name = "secondumpire")
        var secondumpire: Secondumpire?,
        @Json(name = "tvumpire")
        var tvumpire: Tvumpire?,
        @Json(name = "tosswon")
        var tosswon: Tosswon?,
        @Json(name = "venue")
        var venue: Venue?,
        @Json(name = "stage")
        var stage: Stage?
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

        data class Localteam(
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
            var updatedAt: String?
        )

        data class Visitorteam(
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
            var updatedAt: String?
        )

        data class Referee(
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
            @Json(name = "dateofbirth")
            var dateofbirth: String?,
            @Json(name = "gender")
            var gender: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Firstumpire(
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
            @Json(name = "dateofbirth")
            var dateofbirth: String?,
            @Json(name = "gender")
            var gender: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Secondumpire(
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
            @Json(name = "dateofbirth")
            var dateofbirth: String?,
            @Json(name = "gender")
            var gender: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Tvumpire(
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
            @Json(name = "dateofbirth")
            var dateofbirth: String?,
            @Json(name = "gender")
            var gender: String?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Tosswon(
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
            var updatedAt: String?
        )

        data class Venue(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "country_id")
            var countryId: Int?,
            @Json(name = "name")
            var name: String?,
            @Json(name = "city")
            var city: String?,
            @Json(name = "image_path")
            var imagePath: String?,
            @Json(name = "capacity")
            var capacity: Int?,
            @Json(name = "floodlight")
            var floodlight: Boolean?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Stage(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "league_id")
            var leagueId: Int?,
            @Json(name = "season_id")
            var seasonId: Int?,
            @Json(name = "name")
            var name: String?,
            @Json(name = "code")
            var code: String?,
            @Json(name = "type")
            var type: String?,
            @Json(name = "standings")
            var standings: Boolean?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )
    }
}