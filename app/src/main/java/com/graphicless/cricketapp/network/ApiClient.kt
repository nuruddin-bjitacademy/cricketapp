package com.graphicless.cricketapp.network

import com.graphicless.cricketapp.model.Continents
import com.graphicless.cricketapp.model.Countries
import com.graphicless.cricketapp.model.Leagues
import com.graphicless.cricketapp.temp.*
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit: Retrofit by lazy {
    Retrofit.Builder().baseUrl(MyConstants.BASE_URL).addConverterFactory(
        MoshiConverterFactory.create(
            moshi
        )
    ).build()
}

interface ApiService {

    @GET(MyConstants.CONTINENTS)
    suspend fun fetchContinents(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Continents

    @GET(MyConstants.COUNTRIES)
    suspend fun fetchCountries(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Countries

    @GET(MyConstants.LEAGUES)
    suspend fun fetchLeagues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Leagues

    @GET(MyConstants.FIXTURES)
    suspend fun fetchFixtures(
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2023-01-01,2023-02-20",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(MyConstants.FIXTURES)
    suspend fun fetchFixturesByPage(
        @Query(MyConstants.PAGE) page: Int, @Query("include") include: String = "runs",
//        @Query("filter[league_id]") league_id: Int ,
        @Query("filter[starts_between]") starts_between: String = "2023-01-01,2023-02-20",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(MyConstants.FIXTURES)
    suspend fun fetchRuns(
        @Query(MyConstants.PAGE) page: Int,
        @Query("include") include: String = "runs",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): FixturesIncludeRuns

    @GET(MyConstants.LIVE_SCORES)
    suspend fun fetchLiveScores(
        @Query("include") include: String = "runs",
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): LiveScoresIncludeRuns

    @GET(MyConstants.TEAMS)
    suspend fun fetchTeams(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Teams

    @GET(MyConstants.VENUES)
    suspend fun fetchVenues(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Venues

    @GET(MyConstants.STAGES)
    suspend fun fetchStages(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Stages

    @GET(MyConstants.SEASONS)
    suspend fun fetchSeasons(
        @Query(MyConstants.API_TOKEN) api_token: String = MyConstants.API_KEY
    ): Seasons

    @GET(MyConstants.OFFICIALS)
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
        @Query("include") include: String = "bowling,batting,runs",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): Call<FixtureScoreCard>

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
