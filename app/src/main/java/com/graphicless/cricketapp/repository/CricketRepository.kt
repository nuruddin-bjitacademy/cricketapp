package com.graphicless.cricketapp.repository

import androidx.lifecycle.LiveData
import com.graphicless.cricketapp.database.CricketDao
import com.graphicless.cricketapp.model.Continent
import com.graphicless.cricketapp.model.Country
import com.graphicless.cricketapp.model.CountryWithContinent
import com.graphicless.cricketapp.model.League
import com.graphicless.cricketapp.network.CricketApi
import com.graphicless.cricketapp.network.NewsApi
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.temp.map.FixtureDetails
import com.graphicless.cricketapp.temp.map.StageByLeague
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class CricketRepository(private val cricketDao: CricketDao) {

    val getContinents: LiveData<List<Continent>> = cricketDao.getContinents()
    val getCountries: LiveData<List<Country>> = cricketDao.getCountries()
    val getLeagues: LiveData<List<League>> = cricketDao.getLeagues()
    val getFixtures: LiveData<List<FixturesIncludeRuns.Data>> = cricketDao.getFixtures()

    suspend fun insertContinent(continent: Continent) {
        cricketDao.insertContinent(continent)
    }

    suspend fun insertCountry(country: Country) {
        cricketDao.insertCountry(country)
    }

    suspend fun insertLeagues(league: League) {
        cricketDao.insertLeague(league)
    }

    suspend fun insertFixture(fixtures: List<FixturesIncludeRuns.Data>) {
        cricketDao.insertFixture(fixtures)
    }

    suspend fun insertRun(runs: List<FixturesIncludeRuns.Data.Run>) {
        cricketDao.insertRun(runs)
    }

    suspend fun insertTeam(teams: List<Teams.Data>) {
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

    fun getContinentName(id: Int): LiveData<String> {
        return cricketDao.getContinentName(id)
    }

    fun getCountryWithContinent(): LiveData<List<CountryWithContinent>> {
        return cricketDao.getCountryWithContinent()
    }

    /*fun getFixtureAndTeam(): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getFixtureAndTeam()
    }*/

    /*fun getFixtureAndTeam2(): LiveData<List<test>> {
        return cricketDao.getFixtureAndTeam2()
    }*/

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

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getPreviousMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return cricketDao.getPreviousMatchesByDate(leagueId, startingAt)
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

    suspend fun getFixtureScoreCard(fixtureId: Int): FixtureScoreCard {
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
}