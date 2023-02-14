package com.graphicless.cricketapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graphicless.cricketapp.database.CricketDao
import com.graphicless.cricketapp.database.LocalDatabase
import com.graphicless.cricketapp.model.*
import com.graphicless.cricketapp.network.CricketApi
import com.graphicless.cricketapp.repository.CricketRepository
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.temp.map.FixtureDetails
import com.graphicless.cricketapp.temp.map.StageByLeague
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

private const val TAG = "CricketViewModel"

class CricketViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CricketRepository
    private var cricketDao: CricketDao
    val continents: LiveData<List<Continent>>
    val countries: LiveData<List<Country>>
    val leagues: LiveData<List<League>>
    val fixtures: LiveData<List<FixturesIncludeRuns.Data>>


    val getCountryWithContinent: LiveData<List<CountryWithContinent>>

    //    val getFixtureAndTeam: LiveData<List<FixtureAndTeam>>
//    val getFixtureAndTeam2: LiveData<List<test>>
    val getDistinctStageName: LiveData<List<StageName>>
    val getDistinctStages: LiveData<List<DistinctStages>>

    init {
        cricketDao = LocalDatabase.instance(application).cricketDao()
        repository = CricketRepository(cricketDao)

        continents = repository.getContinents
        countries = repository.getCountries
        leagues = repository.getLeagues
        fixtures = repository.getFixtures


        getCountryWithContinent = repository.getCountryWithContinent()
//        getFixtureAndTeam = repository.getFixtureAndTeam()
//        getFixtureAndTeam2 = repository.getFixtureAndTeam2()
        getDistinctStageName = repository.getDistinctStageName()
        getDistinctStages = repository.getDistinctStages()
    }

    fun insertContinents() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val continents: List<Continent> = CricketApi.retrofitService.fetchContinents().data
                for (continent in continents) {
                    repository.insertContinent(continent)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertContinents: $exception")
            }
        }
    }

    fun insertCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries: List<Country> = CricketApi.retrofitService.fetchCountries().data
                for (country in countries) {
                    repository.insertCountry(country)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertCountries: $exception")
            }
        }
    }

    fun insertLeagues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val leagues: List<League> = CricketApi.retrofitService.fetchLeagues().data
                for (league in leagues) {
                    repository.insertLeagues(league)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertLeagues: $exception")
            }
        }
    }

    fun getContinentName(id: Int): LiveData<String> {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.getContinentName(id)
//        }
        return repository.getContinentName(id)
    }

    fun insertFixtures() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalPage = CricketApi.retrofitService.fetchFixtures().meta.lastPage
                Log.d(TAG, "total page: $totalPage")

                for (page in totalPage downTo 1) {
                    Log.d(TAG, "page: $page")
                    val fixtures: List<FixturesIncludeRuns.Data> =
                        CricketApi.retrofitService.fetchFixturesByPage(page).data
                    repository.insertFixture(fixtures)
                    for (data in fixtures.size - 1 downTo 0) {
                        val runs: List<FixturesIncludeRuns.Data.Run>? = fixtures[data].runs
                        if (runs != null) {
                            repository.insertRun(runs)
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertFixtures: $exception")
            }
        }
    }

    fun insertTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teams: List<Teams.Data> = CricketApi.retrofitService.fetchTeams().data
                repository.insertTeam(teams)
            } catch (exception: Exception) {
                Log.e(TAG, "insertTeams: $exception")
            }
        }
    }

    fun insertOfficials() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val officials: List<Officials.Data> =
                    CricketApi.retrofitService.fetchOfficials().data
                repository.insertOfficials(officials)
            } catch (exception: Exception) {
                Log.e(TAG, "insertOfficials: $exception")
            }
        }
    }

    fun insertVenues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val venues: List<Venues.Data> = CricketApi.retrofitService.fetchVenues().data
                repository.insertVenue(venues)
            } catch (exception: Exception) {
                Log.e(TAG, "insertVenues: $exception")
            }
        }
    }

    fun insertStages() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val stages: List<Stages.Data> = CricketApi.retrofitService.fetchStages().data
                repository.insertStage(stages)
            } catch (exception: Exception) {
                Log.e(TAG, "insertStages: $exception")
            }
        }
    }

    fun insertSeasons() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val seasons: List<Seasons.Data> = CricketApi.retrofitService.fetchSeasons().data
                repository.insertSeason(seasons)
            } catch (exception: Exception) {
                Log.e(TAG, "insertStages: $exception")
            }
        }
    }

//    private val _fixtureWithLineUp: MutableLiveData<FixtureWithLineUp> = MutableLiveData(null)
//    private val fixtureWithLineUp: LiveData<FixtureWithLineUp>
//        get() = _fixtureWithLineUp
    /*fun getFixtureWithLineUp(fixtureId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                    val response =
                        CricketApi.retrofitService.getFixtureWithLineUp(fixtureId)
                Log.d(TAG, "response: $response")
            } catch (exception: Exception) {
                Log.e(TAG, "getFixtureWithLineUp: $exception")
            }
        }
    }*/

    /*private suspend fun getFixtureWithLineUp(fixtureId: Int): FixtureWithLineUp {
        return CricketApi.retrofitService.getFixtureWithLineUp(fixtureId).await()
    }

    fun launchDataLoad(fixtureId: Int) {
        viewModelScope.launch {
            val fixtureWithLineUp = getFixtureWithLineUp(fixtureId)
            // Do something with the fixtureWithLineUp object
        }
    }
*/


    // Get fixture with line up from api
    private val _fixtureWithLineUp = MutableLiveData<FixtureWithLineUp>()
    val fixtureWithLineUp: LiveData<FixtureWithLineUp>
        get() = _fixtureWithLineUp

    fun launchFixtureWithLineUp(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureWithLineUp = getFixtureWithLineUp(fixtureId)
                _fixtureWithLineUp.postValue(fixtureWithLineUp)
            }

        }
    }

    private suspend fun getFixtureWithLineUp(fixtureId: Int): FixtureWithLineUp {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureWithLineUp(fixtureId).await()
        }
    }
    // [End] Get fixture with line up from api

    private val _fixtureScoreCard = MutableLiveData<FixtureScoreCard>()
    val fixtureScoreCard: LiveData<FixtureScoreCard>
        get() = _fixtureScoreCard

    fun launchFixtureScoreCard(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureScoreCard = getFixtureScoreCard(fixtureId)
                _fixtureScoreCard.postValue(fixtureScoreCard)
            }
        }
    }

    private suspend fun getFixtureScoreCard(fixtureId: Int): FixtureScoreCard {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureScoreCard(fixtureId).await()
        }
    }

    private val _fixtureOver = MutableLiveData<FixtureOver>()
    val fixtureOver: LiveData<FixtureOver>
        get() = _fixtureOver

    fun launchFixtureOver(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureOver = getFixtureOver(fixtureId)
                _fixtureOver.postValue(fixtureOver)
            }
        }
    }

    private suspend fun getFixtureOver(fixtureId: Int): FixtureOver {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getFixtureOver(fixtureId).await()
        }
    }

    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player>
        get() = _player

    fun launchPlayer(playerId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val player = getPlayer(playerId)
                _player.postValue(player)
            }
        }
    }

    private suspend fun getPlayer(playerId: Int): Player {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.getPlayerByPlayerId(playerId).await()
        }
    }

    private val _team = MutableLiveData<TeamByTeamId>()
    val team: LiveData<TeamByTeamId>
        get() = _team

    private suspend fun getTeamByTeamId(teamId: Int): TeamByTeamId {
        return CricketApi.retrofitService.getTeamByTeamId(teamId).await()
    }

    fun launchTeamByTeamId(teamId: Int) {
        viewModelScope.launch {
            val team = getTeamByTeamId(teamId)
            _team.value = team
        }
    }


    fun getFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>> {
        return repository.getFixturesByStageId(stageId)
    }

    fun getFixtureDetails(fixtureId: Int): LiveData<FixtureDetails> {
        return repository.getFixtureDetails(fixtureId)
    }

    fun getRunByRunId(runId: Int): LiveData<List<FixturesIncludeRuns.Data.Run>> {
        return repository.getRunByRunId(runId)
    }

    fun getStageNameById(stageId: Int): LiveData<String> {
        return repository.getStageNameById(stageId)
    }

    fun getTeamNameByFixtureId(teamId: Int): LiveData<Teams.Data> {
        return repository.getTeamNameByFixtureId(teamId)
    }

    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getStageByLeagueId(leagueId)
    }

    fun getUpcomingMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getUpcomingMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getPreviousMatchSummaryByLeagueId(leagueId)
    }

}