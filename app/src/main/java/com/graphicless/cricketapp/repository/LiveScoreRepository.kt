package com.graphicless.cricketapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.graphicless.cricketapp.model.LiveScoreDetails
import com.graphicless.cricketapp.model.LiveScoresIncludeRuns
import com.graphicless.cricketapp.model.StandingByStageId
import com.graphicless.cricketapp.utils.ApiEndpoints
import com.graphicless.cricketapp.utils.MyConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class LiveScoreRepository {
    private val apiService = CricketApiClient.service

    fun getLiveScores(apiToken: String): LiveData<List<LiveScoresIncludeRuns.Data?>?> {
        return liveData {
            val response = apiService.getLiveScores(include = ApiEndpoints.RUNS, apiToken)
            emit(response.data)
        }
    }

    fun getLiveScoreDetails(fixtureId: Int, apiToken: String): LiveData<LiveScoreDetails.Data?> {
        return liveData {
            val response = apiService.getLiveScoreDetails(
                fixtureId,
                include = ApiEndpoints.GET_LIVE_SCORE_DETAILS_INCLUDE,
                apiToken
            )
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
    private const val BASE_URL = MyConstants.BASE_URL

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
    @GET(ApiEndpoints.LIVE_SCORES)
    suspend fun getLiveScores(
        @Query(MyConstants.INCLUDE) include: String,
        @Query(MyConstants.API_TOKEN) apiToken: String
    ): LiveScoresIncludeRuns

    @GET(ApiEndpoints.FIXTURES_BY_FIXTURE_ID)
    suspend fun getLiveScoreDetails(
        @Path(ApiEndpoints.FIXTURE_ID) fixtureId: Int,
        @Query(MyConstants.INCLUDE) include: String = ApiEndpoints.GET_LIVE_SCORE_DETAILS_INCLUDE,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): LiveScoreDetails

    @GET(ApiEndpoints.STANDINGS_BY_STAGE_BY_STAGE_ID)
    suspend fun getStandingByStageId(
        @Path(ApiEndpoints.STAGE_ID) stageId: Int,
        @Query(MyConstants.API_TOKEN) apiToken: String = MyConstants.API_KEY
    ): StandingByStageId

}
