package com.graphicless.cricketapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.graphicless.cricketapp.database.CricketDao
import com.graphicless.cricketapp.database.LocalDatabase
import com.graphicless.cricketapp.network.CricketApi
import com.graphicless.cricketapp.repository.CricketRepository
import com.graphicless.cricketapp.repository.LiveScoreRepository
import com.graphicless.cricketapp.Model.*
import com.graphicless.cricketapp.Model.joined.FixtureAndTeam
import com.graphicless.cricketapp.Model.map.FixtureDetails
import com.graphicless.cricketapp.Model.map.StageByLeague
import com.graphicless.cricketapp.utils.MyConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await

private const val TAG = "CricketViewModel"

class CricketViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CricketRepository
    private var cricketDao: CricketDao

    val fixtures: LiveData<List<FixturesIncludeRuns.Data>>
    private val getDistinctStageName: LiveData<List<StageName>>
    private val getDistinctStages: LiveData<List<DistinctStages>>

    init {
        cricketDao = LocalDatabase.instance(application).cricketDao()
        repository = CricketRepository(cricketDao)

        fixtures = repository.getFixtures
        getDistinctStageName = repository.getDistinctStageName()
        getDistinctStages = repository.getDistinctStages()
    }


    fun live(): LiveData<List<LiveScoresIncludeRuns.Data?>?> {
        return LiveScoreRepository().getLiveScores(MyConstants.API_KEY)
    }

    fun liveDetails(fixtureId: Int): LiveData<LiveScoreDetails.Data?> {
        return LiveScoreRepository().getLiveScoreDetails(fixtureId, MyConstants.API_KEY)
    }

    fun getStandingByStageId(stageId: Int): LiveData<StandingByStageId> {
        return LiveScoreRepository().getStandingByStageId(stageId)
    }

    fun insertCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val countries: List<Countries.Data> =
                    CricketApi.retrofitService.fetchCountries().data
                repository.insertCountry(countries)
            } catch (exception: Exception) {
                Log.e(TAG, "insertCountries: $exception")
            }
        }
    }

    fun insertLeagues() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val leagues: List<Leagues.Data> = CricketApi.retrofitService.fetchLeagues().data
                repository.insertLeagues(leagues)
            } catch (exception: Exception) {
                Log.e(TAG, "insertLeagues: $exception")
            }
        }
    }
    fun insertPlayers() {
        Log.d(TAG, "insertPlayers: called")
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                var retryCount = 0
//                var players: List<PlayerAll.Data?>? = null
                /*while (retryCount < 5) {
                    try {
                        players = CricketApi.retrofitService.fetchPlayers().data
                        Log.d(TAG, "insertPlayers: players: $players")
                        break
                    } catch (exception: Exception) {
                        if (exception is SocketTimeoutException && retryCount < 5) {
                            retryCount++
                            delay(2000)
                        } else {
                            Log.e(TAG, "insertPlayers: $exception")
                            break
                        }
                    }
                }*/
                for(i in 1..4){
                    val players: List<PlayerAll.Data?>? = CricketApi.retrofitService.fetchPlayers(i).data
                    repository.insertPlayers(players)
                }
            } catch (exception: Exception) {
                Log.e(TAG, "insertPlayers: $exception")
            }
        }
    }

    /*fun insertCurrentPlayers(teamId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val players = CricketApi.retrofitService.fetchCurrentPlayers(teamId).data?.squad
                Log.d(TAG, "insertCurrentPlayers: $players")
                repository.insertCurrentPlayer(players)
            } catch (exception: Exception) {
                Log.e(TAG, "insertCurrentPlayers: $exception")
            }
        }
    }*/


    fun insertTeamRankings(){
        Log.d(TAG, "insertTeamRankings: called")
        viewModelScope.launch(Dispatchers.IO) {

            repository.getTeamRankings {teamRankings->
                if (teamRankings != null) {
                    for(rankings in teamRankings.indices){
                        val format = teamRankings[rankings]!!.type
                        val gender = teamRankings[rankings]!!.gender
                        for(team in teamRankings[rankings]!!.team?.indices!!){
                            val teamId = teamRankings[rankings]!!.team?.get(team)?.id
                            val teamName = teamRankings[rankings]!!.team?.get(team)?.name
                            val flag = teamRankings[rankings]!!.team?.get(team)?.imagePath
                            val position  = teamRankings[rankings]!!.team?.get(team)?.position
                            val matches = teamRankings[rankings]!!.team?.get(team)?.ranking?.matches
                            val points = teamRankings[rankings]!!.team?.get(team)?.ranking?.points
                            val ratings = teamRankings[rankings]!!.team?.get(team)?.ranking?.rating
                            val teamRanking = TeamRankingsLocal(0, format, gender, teamId,teamName, flag, position, matches, points, ratings)
                            viewModelScope.launch(Dispatchers.IO) {
                                repository.insertTeamRankings(teamRanking)
                            }
                        }
                    }
                }
            }
        }
    }

    fun insertFixtures() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalPage = CricketApi.retrofitService.fetchFixtures().meta?.lastPage
                Log.d(TAG, "total page: $totalPage")

                if (totalPage != null) {
                    repository.deleteFixture()
                    repository.deleteRun()
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

    /*fun insertPreviousFixtures() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalPage = CricketApi.retrofitService.fetchFixtures().meta?.lastPage
                Log.d(TAG, "total page: $totalPage")

                if (totalPage != null) {
                    repository.deleteFixture()
                    for (page in totalPage downTo 1) {
                        Log.d(TAG, "page: $page")
                        val fixtures: List<FixturesIncludeRuns.Data>? =
                            CricketApi.retrofitService.fetchFixturesByPage(page).data
                        if (fixtures != null) {
                            repository.insertFixture(fixtures)
                        }
                        if (fixtures != null) {
                            repository.deleteRun()
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

    fun insertUpcomingFixtures() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val totalPage = CricketApi.retrofitService.fetchUpcomingFixtures().meta?.lastPage
                Log.d(TAG, "total page: $totalPage")

                if (totalPage != null) {
//                    repository.deleteFixture()
                    for (page in totalPage downTo 1) {
                        Log.d(TAG, "page: $page")
                        val fixtures: List<FixturesIncludeRuns.Data>? =
                            CricketApi.retrofitService.fetchUpcomingFixturesByPage(page).data
                        if (fixtures != null) {
                            repository.insertFixture(fixtures)
                        }
                        if (fixtures != null) {
//                            repository.deleteRun()
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

    private val _liveMatchInfo = MutableLiveData<LiveMatchInfo>()
    val liveMatchInfo: LiveData<LiveMatchInfo>
        get() = _liveMatchInfo

    fun launchLiveMatchInfo(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val liveMatchInfo = getLiveMatchInfo(fixtureId)
                _liveMatchInfo.postValue(liveMatchInfo)
            }

        }
    }

    private suspend fun getLiveMatchInfo(fixtureId: Int): LiveMatchInfo {
        return CricketApi.retrofitService.getLiveMatchInfo(fixtureId).await()
    }

    /*private val _liveMatchInfo = MutableLiveData<LiveMatchInfo?>()
    val liveMatchInfo: LiveData<LiveMatchInfo?> = _liveMatchInfo

    fun launchLiveMatchInfo(fixtureId: Int) {
        viewModelScope.launch {
            try {
                val call = CricketApi.retrofitService.getLiveMatchInfo(fixtureId)
                val response = call.execute()
                if (response.isSuccessful) {
                    val liveMatchInfo: LiveMatchInfo? = response.body()
                    _liveMatchInfo.value = liveMatchInfo
                } else {
                    Log.e(TAG, "Error loading live match info: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading live match info: $e")
            }
        }
    }*/



    fun insertTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val teams: List<Teams.Data> = CricketApi.retrofitService.fetchTeams().data
                repository.insertTeam(teams)
                /*for(i in teams.indices){
                    insertCurrentPlayers(teams[i].id)
                }*/
            } catch (exception: Exception) {
                Log.e(TAG, "insertTeams: $exception")
            }
        }
    }

    fun insertOfficials() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val officials: List<Officials.Data>? =
                    CricketApi.retrofitService.fetchOfficials().data
                if (officials != null) {
                    repository.insertOfficials(officials)
                }
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

    private val _fixtureScoreCard = MutableLiveData<FixtureDetailsScoreCard>()
    val fixtureScoreCard: LiveData<FixtureDetailsScoreCard>
        get() = _fixtureScoreCard

    fun launchFixtureScoreCard(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixtureScoreCard = repository.getFixtureScoreCard(fixtureId)
                _fixtureScoreCard.postValue(fixtureScoreCard)
            }
        }
    }
    fun launchFixtureScoreCard256(fixtureId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                val fixtureScoreCard = repository.getFixtureScoreCard526(fixtureId)
//                _fixtureScoreCard.postValue(fixtureScoreCard)

                val call = CricketApi.retrofitService.getFixtureScoreCard526(fixtureId)
                call.enqueue(object : Callback<FixtureDetailsScoreCard> {
                    override fun onResponse(call: Call<FixtureDetailsScoreCard>, response: Response<FixtureDetailsScoreCard>) {
                        if (response.isSuccessful) {
                            val fixtureDetails = response.body()
                            // Handle the fixture details here
                            Log.d(TAG, "onResponse: $fixtureDetails")
                        } else {
                            // Handle the error here
                        }
                    }

                    override fun onFailure(call: Call<FixtureDetailsScoreCard>, t: Throwable) {
                        // Handle the failure here
                    }
                })

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

    fun launchSquad(teamId: Int, seasonId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val squad = repository.getSquadByTeamAndSeason(teamId, seasonId)
                _squad.postValue(squad)
            }
        }
    }

    private val _squadAll = MutableLiveData<List<TeamSquad.Data.Squad?>?>()
    val squadAll: LiveData<List<TeamSquad.Data.Squad?>?>
        get() = _squadAll

    fun launchSquadAll(teamId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val squadAll = repository.getSquadByTeam(teamId)
                _squadAll.postValue(squadAll)
            }
        }
    }

    private val _playerDetails = MutableLiveData<PlayerDetailsNew.Data?>()
    val playerDetails: LiveData<PlayerDetailsNew.Data?>
        get() = _playerDetails

    fun launchPlayerDetails(teamId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val playerDetails = repository.getPlayerDetails(teamId)
                _playerDetails.postValue(playerDetails)
            }
        }
    }

    private val _seasonById = MutableLiveData<SeasonById>()
    val seasonById: LiveData<SeasonById>
        get() = _seasonById

    fun launchSeasonById(seasonId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val seasonById = repository.getSeasonById(seasonId)
                _seasonById.postValue(seasonById)
            }
        }
    }

    private val _leagueById = MutableLiveData<LeagueById>()
    val leagueById: LiveData<LeagueById>
        get() = _leagueById

    fun launchLeagueById(leagueId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val leagueById = repository.getLeagueById(leagueId)
                _leagueById.postValue(leagueById)
            }
        }
    }

    private val _fixturesByTeamId = MutableLiveData<FixturesByTeamId>()
    val fixturesByTeamId: LiveData<FixturesByTeamId>
        get() = _fixturesByTeamId

    fun launchFixturesByTeamId(teamId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val fixturesByTeamId = repository.getFixturesByTeamId(teamId)
                _fixturesByTeamId.postValue(fixturesByTeamId)
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
    fun getRecentMatchSummaryByLeagueId(leagueId: Int): LiveData<List<FixtureAndTeam>> {
        return repository.getRecentMatchSummaryByLeagueId(leagueId)
    }

    fun getPreviousMatchSummaryByLeagueId(leagueId: Int): LiveData<List<StageByLeague>> {
        return repository.getPreviousMatchSummaryByLeagueId(leagueId)
    }

    fun getAllUpcomingFixture(): LiveData<List<Match>> {
        return repository.getAllUpcomingFixture()
    }

    fun getPreviousMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return repository.getPreviousMatchesByDate(leagueId, startingAt)
    }

    fun getUpcomingMatchesByDate(
        leagueId: Int,
        startingAt: String
    ): LiveData<List<FixtureAndTeam>> {
        return repository.getUpcomingMatchesByDate(leagueId, startingAt)
    }

    fun getAllPreviousMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return repository.getAllPreviousMatchDateByType(leagueId)
    }

    fun getAllUpcomingMatchDateByType(leagueId: Int): LiveData<List<String>> {
        return repository.getAllUpcomingMatchDateByType(leagueId)
    }

    fun getAllTeam(national: Int): LiveData<List<Teams.Data>> {
        return repository.getAllTeam(national)
    }

    fun getAllTeamByQuery(national: Int, query: String): LiveData<List<Teams.Data>> {
        return repository.getAllTeamByQuery(national, query)
    }

    fun getAllSeasonId(year: String): LiveData<List<Int>> {
        return repository.getAllSeasonId(year)
    }

    fun getSeasonNameById(seasonId: Int): LiveData<String> {
        return repository.getSeasonNameById(seasonId)
    }

    fun getLeagueNameById(leagueId: Int): LiveData<String> {
        return repository.getLeagueNameById(leagueId)
    }

    fun getCountryNameById(countryId: Int): LiveData<String> {
        return repository.getCountryNameById(countryId)
    }

    fun getTeamRankings(format: String, gender: String): LiveData<List<TeamRankingsLocal>> {
        return repository.getTeamRankings(format, gender)
    }
    fun getAllPlayer(): LiveData<List<PlayerAll.Data>> {
        return repository.getAllPlayer()
    }
    fun getPlayerByQuery(query: String): LiveData<List<PlayerAll.Data>> {
        return repository.getPlayerByQuery(query)
    }
    fun getPlayerById(playerId: Int): LiveData<PlayerAll.Data> {
        return repository.getPlayerById(playerId)
    }

}