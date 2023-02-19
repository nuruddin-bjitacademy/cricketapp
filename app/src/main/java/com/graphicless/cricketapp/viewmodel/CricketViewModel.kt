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
import com.graphicless.cricketapp.network.NewsApi
import com.graphicless.cricketapp.repository.CricketRepository
import com.graphicless.cricketapp.repository.LiveScoreRepository
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.temp.joined.FixtureAndTeam
import com.graphicless.cricketapp.temp.map.FixtureDetails
import com.graphicless.cricketapp.temp.map.StageByLeague
import com.graphicless.cricketapp.utils.MyConstants
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

    // Check internet
    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> get() = _isNetworkAvailable

    fun networkAvailable() {
        _isNetworkAvailable.postValue(true)
    }

    fun networkLost() {
        _isNetworkAvailable.postValue(false)
    }
    // End check internet

    fun live(): LiveData<List<LiveScoresIncludeRuns.Data?>?> {
        val liveScores: LiveData<List<LiveScoresIncludeRuns.Data?>?> =
            LiveScoreRepository().getLiveScores(MyConstants.API_KEY)
        return liveScores
    }

    fun liveDetails(fixtureId: Int): LiveData<LiveScoreDetails.Data?> {
        val liveDetails: LiveData<LiveScoreDetails.Data?> =
            LiveScoreRepository().getLiveScoreDetails(fixtureId, MyConstants.API_KEY)
        return liveDetails
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
                val totalPage = CricketApi.retrofitService.fetchFixtures().meta?.lastPage
                Log.d(TAG, "total page: $totalPage")

                if (totalPage != null) {
                    for (page in totalPage downTo 1) {
                        Log.d(TAG, "page: $page")
                        val fixtures: List<FixturesIncludeRuns.Data>? =
                            CricketApi.retrofitService.fetchFixturesByPage(page).data
                        if (fixtures != null) {
                            repository.insertFixture(fixtures)
                        }
                        if (fixtures != null) {
                            for (data in fixtures.size - 1 downTo 0) {
                                val runs: List<FixturesIncludeRuns.Data.Run>? = fixtures[data].runs
                                if (runs != null) {
                                    repository.insertRun(runs)
                                }
                            }
                        }
                    }
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertFixtures: $exception")
            }
        }
    }

    /*private val _liveScores = MutableLiveData<List<LiveScoresIncludeRuns.Data?>?>()
    val liveScores: LiveData<List<LiveScoresIncludeRuns.Data?>?>
        get() = _liveScores

    fun launchLiveScores() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val liveScores = getLiveScores()
                _liveScores.postValue(liveScores)
            }

        }
    }
    private suspend fun getLiveScores(): List<LiveScoresIncludeRuns.Data?>? {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.fetchLiveScores().data
        }
    }*/

    private val _liveScores = MutableLiveData<List<LiveScoresIncludeRuns.Data?>?>()
    val liveScores: LiveData<List<LiveScoresIncludeRuns.Data?>?>
        get() = _liveScores

    fun launchLiveScores() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val liveScores = getLiveScores()
                _liveScores.postValue(liveScores)
            }

        }
    }

    private suspend fun getLiveScores(): List<LiveScoresIncludeRuns.Data?>? {
        return withContext(Dispatchers.IO) {
            CricketApi.retrofitService.fetchLiveScores().data
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
                val venues: List<Venues.Data>? = CricketApi.retrofitService.fetchVenues().data
                if (venues != null) {
                    repository.insertVenue(venues)
                }
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

    // Get fixture with line up from api
    private val _fixtureWithLineUp = MutableLiveData<FixtureWithLineUp>()
    val fixtureWithLineUp: LiveData<FixtureWithLineUp>
        get() = _fixtureWithLineUp

    fun launchFixtureWithLineUp(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureWithLineUp = repository.getFixtureWithLineUp(fixtureId)
                _fixtureWithLineUp.postValue(fixtureWithLineUp)
            }

        }
    }
    // [End] Get fixture with line up from api

    private val _fixtureScoreCard = MutableLiveData<FixtureScoreCard>()
    val fixtureScoreCard: LiveData<FixtureScoreCard>
        get() = _fixtureScoreCard

    fun launchFixtureScoreCard(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureScoreCard = repository.getFixtureScoreCard(fixtureId)
                _fixtureScoreCard.postValue(fixtureScoreCard)
            }
        }
    }

    private val _fixtureOver = MutableLiveData<FixtureOver>()
    val fixtureOver: LiveData<FixtureOver>
        get() = _fixtureOver

    fun launchFixtureOver(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureOver = repository.getFixtureOver(fixtureId)
                _fixtureOver.postValue(fixtureOver)
            }
        }
    }

    private val _player = MutableLiveData<Player>()
    val player: LiveData<Player>
        get() = _player

    fun launchPlayer(playerId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val player = repository.getPlayer(playerId)
                _player.postValue(player)
            }
        }
    }

    private val _player2 = MutableLiveData<Player>()
    val player2: LiveData<Player>
        get() = _player2

    fun launchPlayer2(playerId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val player2 = repository.getPlayer2(playerId)
                _player2.postValue(player2)
            }
        }
    }

    private val _venue = MutableLiveData<VenueLiveScore.Data?>()
    val venue: LiveData<VenueLiveScore.Data?>
        get() = _venue

    fun launchVenue(venueId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val venue = repository.getVenueById(venueId)
                _venue.postValue(venue)
            }
        }
    }

    private val _squad = MutableLiveData<List<SquadByTeamAndSeason.Data.Squad?>?>()
    val squad: LiveData<List<SquadByTeamAndSeason.Data.Squad?>?>
        get() = _squad

    fun launchSquad(teamId: Int,seasonId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val squad = repository.getSquadByTeamAndSeason(teamId,seasonId)
                _squad.postValue(squad)
            }
        }
    }

    private val _news = MutableLiveData<List<News.Article?>?>()
    val news: LiveData<List<News.Article?>?>
        get() = _news

    fun launchNews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val news = repository.getNews()
                _news.postValue(news)
            }
        }
    }

    /*fun fetchNews(): News {
        return withContext(Dispatchers.IO) {
            NewsApi.retrofitServiceNews.fetchNews("cricket", "publishedAT", MyConstants.API_KEY_NEWS)
        }
    }

    fun insertNews():Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            val articles: List<News.Article?>? =
                NewsApi.retrofitServiceNews.fetchNews().articles
            if (articles != null) {
                if(articles.isNotEmpty()){
//                    repository.removeNewsByCategory(country)
                    for (article in articles) {
                        repository.insertNews(LocalNews(article, country, false))
                    }
                }
            }
        }
        return true
    }*/

    // Team by team Id 1
    private val _team = MutableLiveData<TeamByTeamId>()
    val team: LiveData<TeamByTeamId>
        get() = _team

    fun launchTeamByTeamId(teamId: Int) {
        viewModelScope.launch {
            val team = repository.getTeamByTeamId(teamId)
            _team.value = team
        }
    }
    // end Team by team id 1

    // Team by team Id 2
    private val _team2 = MutableLiveData<TeamByTeamId>()
    val team2: LiveData<TeamByTeamId>
        get() = _team2

    fun launchTeamByTeamId2(teamId: Int) {
        viewModelScope.launch {
            val team2 = repository.getTeamByTeamId2(teamId)
            _team2.value = team2
        }
    }
    // end Team by team id 2


    fun getFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>> {
        return repository.getFixturesByStageId(stageId)
    }

    fun getUpcomingFixturesByStageId(stageId: Int): LiveData<List<FixtureAndTeam>> {
        return repository.getUpcomingFixturesByStageId(stageId)
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

    fun getTeamById(teamId: Int): LiveData<Teams.Data> {
        return repository.getTeamById(teamId)
    }

    fun getStageByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getStageByLeagueId(leagueId)
    }

    fun getVenueNameByFixtureId(venueId: Int): LiveData<String> {
        return repository.getVenueNameByFixtureId(venueId)
    }

    fun getLocalTeamById(localTeamId: Int): LiveData<Teams.Data> {
        return repository.getLocalTeamById(localTeamId)
    }

    fun getVisitorTeamById(visitorTeamId: Int): LiveData<Teams.Data> {
        return repository.getVisitorTeamById(visitorTeamId)
    }

    fun getUpcomingMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getUpcomingMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getPreviousMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return repository.getPreviousMatchesByDate(leagueId, startingAt)
    }

    fun getAllPreviousMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return repository.getAllPreviousMatchDateByType(leagueId)
    }

    fun getAllUpcomingMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return repository.getAllUpcomingMatchDateByType(leagueId)
    }

    fun getAllTeam(national: Int): LiveData<List<Teams.Data>>{
        return repository.getAllTeam(national)
    }
    fun getAllTeamByQuery(national: Int, query: String): LiveData<List<Teams.Data>>{
        return repository.getAllTeamByQuery(national, query)
    }
    fun getAllSeasonId(year: String): LiveData<List<Int>>{
        return repository.getAllSeasonId(year)
    }

}