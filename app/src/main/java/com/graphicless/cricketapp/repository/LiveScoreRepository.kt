package com.graphicless.cricketapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.graphicless.cricketapp.temp.LiveScoreDetails
import com.graphicless.cricketapp.temp.LiveScores
import com.graphicless.cricketapp.temp.LiveScoresIncludeRuns
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class LiveScoreRepository {
    private val apiService = CricketApiClient.service

    fun getLiveScores(apiToken: String): LiveData<List<LiveScoresIncludeRuns.Data?>?> {
        return liveData {
            val response = apiService.getLiveScores(include = "runs", apiToken)
            emit(response.data)
        }
    }

    fun getLiveScoreDetails(fixtureId: Int, apiToken: String): LiveData<LiveScoreDetails.Data?> {
        return liveData {
            val response = apiService.getLiveScoreDetails(fixtureId, include = "bowling,batting,runs,lineup,balls", apiToken)
            emit(response.data)
        }
    }
}

object CricketApiClient {
    private const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val service: CricketApiService = retrofit.create(CricketApiService::class.java)
}

interface CricketApiService {
    @GET("livescores")
    suspend fun getLiveScores(
        @Query("include") include: String,
        @Query("api_token") apiToken: String
    ): LiveScoresIncludeRuns

    @GET("fixtures/{fixture_id}")
    suspend fun getLiveScoreDetails(
        @Path("fixture_id") fixtureId: Int,
        @Query("include") include: String = "bowling,batting,runs,lineup,balls",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): LiveScoreDetails
}
