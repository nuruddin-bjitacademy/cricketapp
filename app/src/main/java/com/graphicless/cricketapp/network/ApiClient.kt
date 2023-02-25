package com.graphicless.cricketapp.network

import com.graphicless.cricketapp.Model.*
import com.graphicless.cricketapp.utils.ApiEnpoints
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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

    @GET(ApiEnpoints.COUNTRIES)
    suspend fun fetchCountries(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Countries

    @GET(ApiEnpoints.LEAGUES)
    suspend fun fetchLeagues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Leagues

    @GET(ApiEnpoints.PLAYERS)
    suspend fun fetchPlayers(
        @Query("filter[position_id]") position_id: Int,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): PlayerAll
   /* @GET("teams/{teamId}/squad/23")
    fun fetchCurrentPlayers(
        @Query("teamId") teamId: Int,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): CurrentPlayers*/
    @GET("teams/{teamId}/squad/23")
    suspend fun fetchCurrentPlayers(
        @Query("teamId") teamId: Int,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): CurrentPlayers

    @GET(ApiEnpoints.FIXTURES)
    suspend fun fetchFixtures(
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2020-01-01,2023-02-21",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEnpoints.FIXTURES)
    suspend fun fetchFixturesByPage(
        @Query(MyConstants.PAGE) page: Int,
        @Query("include") include: String = "runs",
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2020-01-01,2023-02-21",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEnpoints.FIXTURES)
    suspend fun fetchUpcomingFixtures(
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2023-02-21, 2023-08-21",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEnpoints.FIXTURES)
    suspend fun fetchUpcomingFixturesByPage(
        @Query(MyConstants.PAGE) page: Int,
        @Query("include") include: String = "runs",
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2023-02-21, 2023-08-21",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEnpoints.FIXTURES)
    suspend fun fetchRuns(
        @Query(MyConstants.PAGE) page: Int,
        @Query("include") include: String = "runs",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(ApiEnpoints.LIVE_SCORES)
    suspend fun fetchLiveScores(
        @Query("include") include: String = "runs",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): LiveScoresIncludeRuns

    @GET(ApiEnpoints.TEAMS)
    suspend fun fetchTeams(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Teams

    @GET("teams/{teamId}")
    suspend fun fetchTeamSquadByTeamId(
        @Path("teamId") teamId: Int,
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): TeamSquad

    @GET(ApiEnpoints.VENUES)
    suspend fun fetchVenues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Venues

    @GET(ApiEnpoints.STAGES)
    suspend fun fetchStages(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Stages

    @GET(ApiEnpoints.SEASONS)
    suspend fun fetchSeasons(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Seasons

    @GET(ApiEnpoints.OFFICIALS)
    suspend fun fetchOfficials(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Officials

    @GET("fixtures/{fixture_id}")
    fun getFixtureWithLineUp(
        @Path("fixture_id") fixtureId: Int,
        @Query("include") include: String = "lineup",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixtureWithLineUp>

    @GET("fixtures/{fixture_id}")
    fun getFixtureScoreCard(
        @Path("fixture_id") fixtureId: Int,
        @Query("include") include: String = "bowling,batting,runs,localteam,visitorteam",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixtureDetailsScoreCard>

    @GET("fixtures/{fixture_id}")
    fun getFixtureScoreCard526(
        @Path("fixture_id") fixtureId: Int,
        @Query("include") include: String = "bowling,batting,runs,localteam,visitorteam",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixtureDetailsScoreCard>

    @GET("fixtures/{fixture_id}")
    fun getFixtureOver(
        @Path("fixture_id") fixtureId: Int,
        @Query("include") include: String = "balls,runs",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixtureOver>

    @GET("players/{playerId}")
    fun getPlayerByPlayerId(
        @Path("playerId") playerId: Int, @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<Player>

    @GET("players/{playerId}")
    fun getPlayerByPlayerId2(
        @Path("playerId") playerId: Int, @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<Player>

    @GET("teams/{teamId}")
    fun getTeamByTeamId(
        @Path("teamId") teamId: Int, @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<TeamByTeamId>

    @GET("teams/{teamId}")
    fun getTeamByTeamId2(
        @Path("teamId") teamId: Int, @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<TeamByTeamId>

    @GET("venues/{venueId}")
    fun getVenueById(
        @Path("venueId") teamId: Int,
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<VenueLiveScore.Data>

    @GET("teams/{teamId}/squad/{seasonId}")
    fun getSquadByTeamAndSeason(
        @Path("teamId") teamId: Int,
        @Path("seasonId") seasonId: Int,
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<SquadByTeamAndSeason>

    @GET("teams/{teamId}/")
    fun getSquadByTeam(
        @Path("teamId") teamId: Int,
        @Query("include") include: String = "squad",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<TeamSquad>

    @GET("players/{playerId}/")
    fun getPlayerDetails(
        @Path("playerId") playerId: Int,
        @Query("include") include: String = "country,career.season,teams,currentteams",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<PlayerDetailsNew>

    @GET("seasons/{seasonId}/")
    fun getSeasonById(
        @Path("seasonId") seasonId: Int,
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<SeasonById>
    @GET("leagues/{leagueId}/")
    fun getLeagueById(
        @Path("leagueId") leagueId: Int,
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<LeagueById>
    @GET("teams/{teamId}/")
    fun getFixturesByTeamId(
        @Path("teamId") teamId: Int,
        @Query("include") include: String = "fixtures",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixturesByTeamId>

    @GET("team-rankings")
    fun getTeamRankings(
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<TeamRankings>

    @GET("fixtures/{fixtureId}")
    fun getLiveMatchInfo(
        @Path("fixtureId") fixtureId: Int,
        @Query("include") include: String = "localteam,visitorteam,stage,venue,referee,tosswon,firstumpire,secondumpire,tvumpire",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
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
    @GET("everything")
    fun fetchNews(
        @Query("q") q: String = "cricket",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 20,
        @Query("apiKey") apiKey: String = MyConstants.API_KEY_NEWS
    ): Call<News>
}

object NewsApi {
    val retrofitServiceNews: ApiServiceNews by lazy { retrofitNews.create(ApiServiceNews::class.java) }
}
