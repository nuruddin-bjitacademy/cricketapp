package com.graphicless.cricketapp.network

import com.graphicless.cricketapp.model.*
import com.graphicless.cricketapp.utils.ApiEndpoints
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import java.util.concurrent.TimeUnit

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(60, TimeUnit.SECONDS)
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .build()

private val retrofit: Retrofit by lazy {
    Retrofit.Builder().baseUrl(MyConstants.BASE_URL).addConverterFactory(
        MoshiConverterFactory.create(
            moshi
        )
    )
        .client(okHttpClient)
        .build()
}

interface ApiService {

    @GET(ApiEndpoints.COUNTRIES)
    suspend fun fetchCountries(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Countries

    @GET(ApiEndpoints.LEAGUES)
    suspend fun fetchLeagues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Leagues

    @GET(ApiEndpoints.PLAYERS)
    suspend fun fetchPlayers(
        @Query(ApiEndpoints.FILTER_POSITION_ID) position_id: Int,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): PlayerAll

    @GET(ApiEndpoints.FIXTURES)
    suspend fun fetchFixtures(
        @Query(ApiEndpoints.FILTER_STARTS_BETWEEN) starts_between: String = MyConstants.FIXTURE_START_TO_END,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEndpoints.FIXTURES)
    suspend fun fetchFixturesByPage(
        @Query(MyConstants.PAGE) page: Int,
        @Query(MyConstants.INCLUDE) include: String = MyConstants.RUNS,
        @Query(ApiEndpoints.FILTER_STARTS_BETWEEN) starts_between: String = MyConstants.FIXTURE_START_TO_END,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEndpoints.LIVE_SCORES)
    suspend fun fetchLiveScores(
        @Query(MyConstants.INCLUDE) include: String = MyConstants.RUNS,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): LiveScoresIncludeRuns

    @GET(ApiEndpoints.TEAMS)
    suspend fun fetchTeams(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Teams

    @GET(ApiEndpoints.VENUES)
    suspend fun fetchVenues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Venues

    @GET(ApiEndpoints.STAGES)
    suspend fun fetchStages(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Stages

    @GET(ApiEndpoints.SEASONS)
    suspend fun fetchSeasons(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Seasons

    @GET(ApiEndpoints.OFFICIALS)
    suspend fun fetchOfficials(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Officials

    @GET(ApiEndpoints.FIXTURES_BY_FIXTURE_ID)
    fun getFixtureWithLineUp(
        @Path(ApiEndpoints.FIXTURE_ID) fixtureId: Int,
        @Query(MyConstants.INCLUDE) include: String = MyConstants.LINE_UP,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<FixtureWithLineUp>

    @GET(ApiEndpoints.FIXTURES_BY_FIXTURE_ID)
    fun getFixtureScoreCard(
        @Path(ApiEndpoints.FIXTURE_ID) fixtureId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.FIXTURE_DETAILS_SCORE_CARD_INCLUDE,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<FixtureDetailsScoreCard>

    @GET(ApiEndpoints.FIXTURES_BY_FIXTURE_ID)
    fun getFixtureOver(
        @Path(ApiEndpoints.FIXTURE_ID) fixtureId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.FIXTURE_OVER_INCLUDE,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<FixtureOver>

    @GET(ApiEndpoints.PLAYER_BY_PLAYER_ID)
    fun getPlayerByPlayerId(
        @Path(ApiEndpoints.PLAYER_ID) playerId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<Player>

    @GET(ApiEndpoints.PLAYER_BY_PLAYER_ID)
    fun getPlayerByPlayerId2(
        @Path(ApiEndpoints.PLAYER_ID) playerId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<Player>

    @GET(ApiEndpoints.TEAMS_BY_TEAM_ID)
    fun getTeamByTeamId(
        @Path(ApiEndpoints.TEAM_ID) teamId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<TeamByTeamId>

    @GET(ApiEndpoints.TEAMS_BY_TEAM_ID)
    fun getTeamByTeamId2(
        @Path(ApiEndpoints.TEAM_ID) teamId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<TeamByTeamId>

    @GET(ApiEndpoints.VENUES_BY_VENUE_ID)
    fun getVenueById(
        @Path(ApiEndpoints.VENUE_ID) teamId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<VenueLiveScore.Data>

    @GET(ApiEndpoints.TEAMS_BY_TEAM_ID_BY_SQUAD_BY_SEASON_ID)
    fun getSquadByTeamAndSeason(
        @Path(ApiEndpoints.TEAM_ID) teamId: Int,
        @Path(ApiEndpoints.SEASON_ID) seasonId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<SquadByTeamAndSeason>

    @GET(ApiEndpoints.TEAMS_BY_TEAM_ID)
    fun getSquadByTeam(
        @Path(ApiEndpoints.TEAM_ID) teamId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.SQUAD,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<TeamSquad>

    @GET(ApiEndpoints.PLAYER_BY_PLAYER_ID)
    fun getPlayerDetails(
        @Path(ApiEndpoints.PLAYER_ID) playerId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.PLAYER_DETAILS_NEW_INCLUDE,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<PlayerDetailsNew>

    @GET(ApiEndpoints.SEASONS_BY_SEASON_ID)
    fun getSeasonById(
        @Path(ApiEndpoints.SEASON_ID) seasonId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<SeasonById>

    @GET(ApiEndpoints.LEAGUES_BY_LEAGUE_ID)
    fun getLeagueById(
        @Path(ApiEndpoints.LEAGUE_ID) leagueId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<LeagueById>

    @GET(ApiEndpoints.TEAMS_BY_TEAM_ID)
    fun getFixturesByTeamId(
        @Path(ApiEndpoints.TEAM_ID) teamId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.FIXTURES,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<FixturesByTeamId>

    @GET(ApiEndpoints.TEAM_RANKINGS)
    fun getTeamRankings(
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<TeamRankings>

    @GET(ApiEndpoints.FIXTURES_BY_FIXTURE_ID)
    fun getLiveMatchInfo(
        @Path(ApiEndpoints.FIXTURE_ID) fixtureId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.CALL_LIVE_MATCH_INFO_INCLUDE,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): Call<LiveMatchInfo>

}

object CricketApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}

private val retrofitNews: Retrofit by lazy {
    Retrofit.Builder().baseUrl(MyConstants.BASE_URL_NEWS).addConverterFactory(
        MoshiConverterFactory.create(
            moshi
        )
    ).build()
}

interface ApiServiceNews {
    @GET(ApiEndpoints.EVERYTHING)
    fun fetchNews(
        @Query(ApiEndpoints.Q) q: String = ApiEndpoints.CRICKET,
        @Query(ApiEndpoints.SORT_BY) sortBy: String = ApiEndpoints.PUBLISH_AT,
        @Query(ApiEndpoints.PAGE_SIZE) pageSize: Int = 20,
        @Query(ApiEndpoints.NEWS_API_KEY) apiKey: String = MyConstants.API_KEY_NEWS
    ): Call<News>
}

object NewsApi {
    val retrofitServiceNews: ApiServiceNews by lazy { retrofitNews.create(ApiServiceNews::class.java) }
}
