package com.graphicless.cricketapp.repository

import androidx.lifecycle.LiveData
import com.graphicless.cricketapp.database.CricketDao
import com.graphicless.cricketapp.model.Continent
import com.graphicless.cricketapp.model.Country
import com.graphicless.cricketapp.model.CountryWithContinent
import com.graphicless.cricketapp.model.League
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.temp.map.FixtureDetails
import com.graphicless.cricketapp.temp.map.StageByLeague

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

    fun getFixtureDetails(fixtureId: Int): LiveData<FixtureDetails> {
        return cricketDao.getFixtureDetails(fixtureId)
    }

    fun getRunByRunId(runId: Int): LiveData<List<FixturesIncludeRuns.Data.Run>> {
        return cricketDao.getRunByRunId(runId)
    }

    fun getStageNameById(stageId: Int): LiveData<String> {
        return cricketDao.getStageNameById(stageId)
    }

    fun getTeamNameByFixtureId(teamId: Int): LiveData<Teams.Data> {
        return cricketDao.getTeamNameByFixtureId(teamId)
    }

    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getStageByLeagueId(leagueId)
    }

    fun getUpcomingMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getUpcomingMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return cricketDao.getPreviousMatchSummaryByLeagueId(leagueId)
    }

    fun getDistinctStageName(): LiveData<List<StageName>> {
        return cricketDao.getDistinctStageName()
    }

    fun getDistinctStages(): LiveData<List<DistinctStages>> {
        return cricketDao.getDistinctStages()
    }
}