package com.graphicless.cricketapp.Model


import com.squareup.moshi.Json

data class LiveScoreDetails2(
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
        var tvUmpireId: Int?,
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
        @Json(name = "batting")
        var batting: List<Batting?>?,
        @Json(name = "bowling")
        var bowling: List<Bowling?>?,
        @Json(name = "runs")
        var runs: List<Run?>?,
        @Json(name = "lineup")
        var lineup: List<Lineup?>?,
        @Json(name = "balls")
        var balls: List<Ball?>?
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

        data class Batting(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "sort")
            var sort: Int?,
            @Json(name = "fixture_id")
            var fixtureId: Int?,
            @Json(name = "team_id")
            var teamId: Int?,
            @Json(name = "active")
            var active: Boolean?,
            @Json(name = "scoreboard")
            var scoreboard: String?,
            @Json(name = "player_id")
            var playerId: Int?,
            @Json(name = "ball")
            var ball: Int?,
            @Json(name = "score_id")
            var scoreId: Int?,
            @Json(name = "score")
            var score: Int?,
            @Json(name = "four_x")
            var fourX: Int?,
            @Json(name = "six_x")
            var sixX: Int?,
            @Json(name = "catch_stump_player_id")
            var catchStumpPlayerId: Int?,
            @Json(name = "runout_by_id")
            var runoutById: Any?,
            @Json(name = "batsmanout_id")
            var batsmanoutId: Any?,
            @Json(name = "bowling_player_id")
            var bowlingPlayerId: Int?,
            @Json(name = "fow_score")
            var fowScore: Int?,
            @Json(name = "fow_balls")
            var fowBalls: Double?,
            @Json(name = "rate")
            var rate: Int?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Bowling(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "sort")
            var sort: Int?,
            @Json(name = "fixture_id")
            var fixtureId: Int?,
            @Json(name = "team_id")
            var teamId: Int?,
            @Json(name = "active")
            var active: Boolean?,
            @Json(name = "scoreboard")
            var scoreboard: String?,
            @Json(name = "player_id")
            var playerId: Int?,
            @Json(name = "overs")
            var overs: Double?,
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
            @Json(name = "rate")
            var rate: Double?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Run(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "fixture_id")
            var fixtureId: Int?,
            @Json(name = "team_id")
            var teamId: Int?,
            @Json(name = "inning")
            var inning: Int?,
            @Json(name = "score")
            var score: Int?,
            @Json(name = "wickets")
            var wickets: Int?,
            @Json(name = "overs")
            var overs: Double?,
            @Json(name = "pp1")
            var pp1: Any?,
            @Json(name = "pp2")
            var pp2: Any?,
            @Json(name = "pp3")
            var pp3: Any?,
            @Json(name = "updated_at")
            var updatedAt: String?
        )

        data class Lineup(
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
            @Json(name = "updated_at")
            var updatedAt: String?,
            @Json(name = "lineup")
            var lineup: Lineup?
        ) {
            data class Position(
                @Json(name = "resource")
                var resource: String?,
                @Json(name = "id")
                var id: Int?,
                @Json(name = "name")
                var name: String?
            )

            data class Lineup(
                @Json(name = "team_id")
                var teamId: Int?,
                @Json(name = "captain")
                var captain: Boolean?,
                @Json(name = "wicketkeeper")
                var wicketkeeper: Boolean?,
                @Json(name = "substitution")
                var substitution: Boolean?
            )
        }

        data class Ball(
            @Json(name = "resource")
            var resource: String?,
            @Json(name = "id")
            var id: Int?,
            @Json(name = "fixture_id")
            var fixtureId: Int?,
            @Json(name = "team_id")
            var teamId: Int?,
            @Json(name = "ball")
            var ball: Double?,
            @Json(name = "scoreboard")
            var scoreboard: String?,
            @Json(name = "batsman_one_on_creeze_id")
            var batsmanOneOnCreezeId: Int?,
            @Json(name = "batsman_two_on_creeze_id")
            var batsmanTwoOnCreezeId: Int?,
            @Json(name = "batsman_id")
            var batsmanId: Int?,
            @Json(name = "bowler_id")
            var bowlerId: Int?,
            @Json(name = "batsmanout_id")
            var batsmanoutId: Int?,
            @Json(name = "catchstump_id")
            var catchstumpId: Int?,
            @Json(name = "runout_by_id")
            var runoutById: Any?,
            @Json(name = "score_id")
            var scoreId: Int?,
            @Json(name = "batsman")
            var batsman: Batsman?,
            @Json(name = "bowler")
            var bowler: Bowler?,
            @Json(name = "score")
            var score: Score?,
            @Json(name = "team")
            var team: Team?,
            @Json(name = "updated_at")
            var updatedAt: String?
        ) {
            data class Batsman(
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
                @Json(name = "updated_at")
                var updatedAt: String?
            ) {
                data class Position(
                    @Json(name = "resource")
                    var resource: String?,
                    @Json(name = "id")
                    var id: Int?,
                    @Json(name = "name")
                    var name: String?
                )
            }

            data class Bowler(
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
                @Json(name = "updated_at")
                var updatedAt: String?
            ) {
                data class Position(
                    @Json(name = "resource")
                    var resource: String?,
                    @Json(name = "id")
                    var id: Int?,
                    @Json(name = "name")
                    var name: String?
                )
            }

            data class Score(
                @Json(name = "resource")
                var resource: String?,
                @Json(name = "id")
                var id: Int?,
                @Json(name = "name")
                var name: String?,
                @Json(name = "runs")
                var runs: Int?,
                @Json(name = "four")
                var four: Boolean?,
                @Json(name = "six")
                var six: Boolean?,
                @Json(name = "bye")
                var bye: Int?,
                @Json(name = "leg_bye")
                var legBye: Int?,
                @Json(name = "noball")
                var noball: Int?,
                @Json(name = "noball_runs")
                var noballRuns: Int?,
                @Json(name = "is_wicket")
                var isWicket: Boolean?,
                @Json(name = "ball")
                var ball: Boolean?,
                @Json(name = "out")
                var `out`: Boolean?
            )

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
                var updatedAt: String?
            )
        }
    }
}