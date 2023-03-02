package com.graphicless.cricketapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.graphicless.cricketapp.database.CricketDao
import com.graphicless.cricketapp.network.CricketApi
import com.graphicless.cricketapp.network.NewsApi
import com.graphicless.cricketapp.model.*
import com.graphicless.cricketapp.model.map.FixtureAndTeam
import com.graphicless.cricketapp.model.map.FixtureDetails
import com.graphicless.cricketapp.model.map.StageByLeague
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

private const val TAG = "CricketRepository"
class CricketRepository(private val cricketDao: CricketDao) {
    val getFixtures: LiveData<List<FixturesIncludeRuns.Data>> = cricketDao.getFixtures()

    suspend fun insertCountry(countries: List<Countries.Data>) {
        cricketDao.insertCountry(countries)
    }

    suspend fun insertLeagues(leagues: List<Leagues.Data>) {
        cricketDao.insertLeague(leagues)
    }

    suspend fun insertPlayers(players: List<PlayerAll.Data?>?) {
        cricketDao.insertPlayers(players)
    }

    suspend fun insertTeamRankings(teamRanking: TeamRankingsLocal) {
        cricketDao.insertTeamRankings(teamRanking)
    }

    suspend fun insertFixture(fixtures: List<FixturesIncludeRuns.Data>) {
        cricketDao.insertFixture(fixtures)
    }

    suspend fun insertRun(runs: List<FixturesIncludeRuns.Data.Run>) {
        cricketDao.insertRun(runs)
    }

    suspend fun insertTeam(teams: List<Teams.Data>) {
        Log.d("TAG", "insertTeam: called")
        cricketDao.insertTeam(teams)
    }

    suspend fun insertVenue(venues: List<Venues.Data>) {
        cricketDao.insertVenue(venues)
    }

    suspend fun insertStage(stages: List<Stages.Data>) {
        cricketDao.insertStage(stages)
    }

    suspend fun insertSeason(seasons: List<Seasons.Data>) {
        cricketDao.insertSeason(seasons)
    }

    suspend fun insertOfficials(officials: List<Officials.Data>) {
        cricketDao.insertOfficials(officials)
    }

    fun deleteTeamRanking() {
        cricketDao.deleteTeamRanking()
    }
    fun deleteFixture() {
        cricketDao.deleteFixture()
    }
    fun deleteRun() {
        cricketDao.deleteRun()
    }

    fun getFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getFixturesByStageId(stageId)
    }

    fun getUpcomingFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getUpcomingFixturesByStageId(stageId)
    }

    fun getFixtureDetails(fixtureId: Int): LiveData<FixtureDetails> {
        return cricketDao.getFixtureDetails(fixtureId)
    }

    fun getRunByRunId(runId: Int): LiveData<List<FixturesIncludeRuns.Data.Run>> {
        return cricketDao.getRunByRunId(runId)
    }

    fun getStageNameById(stageId: Int): LiveData<String> {
        return cricketDao.getStageNameById(stageId)
    }

    fun getTeamById(teamId: Int): LiveData<Teams.Data> {
        return cricketDao.getTeamById(teamId)
    }

    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getStageByLeagueId(leagueId)
    }

    fun getVenueNameByFixtureId(venueId: Int): LiveData<String> {
        return cricketDao.getVenueNameByFixtureId(venueId)
    }

    fun getLocalTeamById(localTeamId: Int): LiveData<Teams.Data> {
        return cricketDao.getLocalTeamById(localTeamId)
    }

    fun getVisitorTeamById(visitorTeamId: Int): LiveData<Teams.Data> {
        return cricketDao.getVisitorTeamById(visitorTeamId)
    }

    fun getUpcomingMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getUpcomingMatchSummaryByLeagueId(leagueId)
    }
    fun getRecentMatchSummaryByLeagueId(leagueId: Int): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getRecentMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getPreviousMatchSummaryByLeagueId(leagueId)
    }

    fun getAllUpcomingFixture(): LiveData<List<Match>> {
        return cricketDao.getAllUpcomingFixture()
    }

    fun getPreviousMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getPreviousMatchesByDate(leagueId, startingAt)
    }

    fun getUpcomingMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getUpcomingMatchesByDate(leagueId, startingAt)
    }

    fun getAllPreviousMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return cricketDao.getAllPreviousMatchDateByType(leagueId)
    }

    fun getAllUpcomingMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return cricketDao.getAllUpcomingMatchDateByType(leagueId)
    }

    fun getDistinctStageName(): LiveData<List<StageName>> {
        return cricketDao.getDistinctStageName()
    }

    fun getDistinctStages(): LiveData<List<DistinctStages>> {
        return cricketDao.getDistinctStages()
    }

    suspend fun getVenueById(venueId: Int): VenueLiveScore.Data {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getVenueById(venueId).await()
        }
    }

    suspend fun getSquadByTeamAndSeason(teamId: Int,seasonId: Int): List<SquadByTeamAndSeason.Data.Squad?>? {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getSquadByTeamAndSeason(teamId,seasonId).await().data?.squad
        }
    }

    suspend fun getSquadByTeam(teamId: Int): List<TeamSquad.Data.Squad?>? {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getSquadByTeam(teamId).await().data?.squad
        }
    }

    suspend fun getPlayerDetails(playerId: Int): PlayerDetailsNew.Data? {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getPlayerDetails(playerId).await().data
        }
    }
    suspend fun getSeasonById(seasonId: Int): SeasonById {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getSeasonById(seasonId).await()
        }
    }
    suspend fun getLeagueById(leagueId: Int): LeagueById {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getLeagueById(leagueId).await()
        }
    }
    suspend fun getFixturesByTeamId(teamId: Int): FixturesByTeamId {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixturesByTeamId(teamId).await()
        }
    }

    suspend fun getPlayer2(playerId: Int): Player {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getPlayerByPlayerId2(playerId).await()
        }
    }

    suspend fun getPlayer(playerId: Int): Player {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getPlayerByPlayerId(playerId).await()
        }
    }

    suspend fun getFixtureOver(fixtureId: Int): FixtureOver {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureOver(fixtureId).await()
        }
    }

    suspend fun getFixtureScoreCard(fixtureId: Int): FixtureDetailsScoreCard {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureScoreCard(fixtureId).await()
        }
    }

    suspend fun getFixtureWithLineUp(fixtureId: Int): FixtureWithLineUp {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureWithLineUp(fixtureId).await()
        }
    }

    suspend fun getTeamByTeamId2(teamId: Int): TeamByTeamId {
        return CricketApi.retrofitService.getTeamByTeamId2(teamId).await()
    }

    suspend fun getTeamByTeamId(teamId: Int): TeamByTeamId {
        return CricketApi.retrofitService.getTeamByTeamId(teamId).await()
    }

    suspend fun getNews(): List<News.Article?>? {
        return withContext(Dispatchers.IO) {
            NewsApi.retrofitServiceNews.fetchNews().await().articles
        }
    }

    fun getAllTeam(national: Int): LiveData<List<Teams.Data>> {
        return cricketDao.getAllTeam(national)
    }
    fun getAllTeamByQuery(national: Int, query: String): LiveData<List<Teams.Data>> {
        return cricketDao.getAllTeamByQuery(national, query)
    }
    fun getAllSeasonId(year: String): LiveData<List<Int>> {
        return cricketDao.getAllSeasonId(year)
    }
    fun getSeasonNameById(seasonId: Int): LiveData<String> {
        return cricketDao.getSeasonNameById(seasonId)
    }
    fun getLeagueNameById(leagueId: Int): LiveData<String> {
        return cricketDao.getLeagueNameById(leagueId)
    }
    fun getCountryNameById(countryId: Int): LiveData<String> {
        return cricketDao.getCountryNameById(countryId)
    }
    fun getTeamRankings(format: String, gender: String): LiveData<List<TeamRankingsLocal>> {
        return cricketDao.getTeamRankings(format, gender)
    }
    fun getAllPlayer(): LiveData<List<PlayerAll.Data>> {
        return cricketDao.getAllPlayer()
    }
    fun getPlayerByQuery(query: String): LiveData<List<PlayerAll.Data>> {
        return cricketDao.getPlayerByQuery(query)
    }
    fun getPlayerById(playerId: Int): LiveData<PlayerAll.Data> {
        return cricketDao.getPlayerById(playerId)
    }

    fun getTeamRankings(callback: (List<TeamRankings.Data?>?) -> Unit) {
        CricketApi.retrofitService.getTeamRankings().enqueue(object : Callback<TeamRankings> {
            override fun onResponse(call: Call<TeamRankings>, response: Response<TeamRankings>) {
                if (response.isSuccessful) {
                    val teamRankings: List<TeamRankings.Data?>? = response.body()?.data
                    callback(teamRankings)
                } else {
                    onFailure(call, Throwable("Error: ${response.code()}"))
                }
            }
            override fun onFailure(call: Call<TeamRankings>, t: Throwable) {
                Log.e(TAG, "onFailure: getTeamRankings", )
                callback(null)
            }
        })
    }

}