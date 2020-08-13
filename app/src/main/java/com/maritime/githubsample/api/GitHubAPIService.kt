package com.maritime.githubsample.api

import com.maritime.githubsample.model.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubAPIService {

    companion object {
        fun create(): GitHubAPIService {
            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://github-trending-api.now.sh")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(GitHubAPIService::class.java)
        }
    }

    @GET("/developers")
    suspend fun getDevelopers(@Query("language") language: String, @Query("since") since: String): List<Repository>?


}