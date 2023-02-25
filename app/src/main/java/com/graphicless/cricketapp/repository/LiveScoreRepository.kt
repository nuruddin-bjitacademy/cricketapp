package com.graphicless.cricketapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.graphicless.cricketapp.Model.LiveScoreDetails
import com.graphicless.cricketapp.Model.LiveScoresIncludeRuns
import com.graphicless.cricketapp.Model.StandingByStageId
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.IOException
import java.util.concurrent.TimeUnit

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
            val response = apiService.getLiveScoreDetails(fixtureId, include = "bowling,batting,runs,lineup,balls,localteam,visitorteam", apiToken)
            emit(response.data)
        }
    }

    fun getStandingByStageId(stageId: Int): LiveData<StandingByStageId> {
        return liveData {
            val response = apiService.getStandingByStageId(stageId)
            emit(response)
        }
    }
}

object CricketApiClient {
    private const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
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
        @Query("include") include: String = "bowling,batting,runs,lineup,balls,localteam,visitorteam",
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): LiveScoreDetails

    @GET("standings/stage/{stageId}")
    suspend fun getStandingByStageId(
        @Path("stageId") stageId: Int,
        @Query("api_token") apiToken: String = MyConstants.API_KEY
    ): StandingByStageId

}
