package com.example.eero.pubg.remote

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerMatchesApi {

    var match: String

    @Headers("Authorization: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3MDQ4MmJmMC05MjQ0LTAxMzYtOWNjNi0xYjJmYzc1NTgwMmQiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTM2MDQ3MTM4LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Ii0zYzNkZWQ0Yi02ZWY0LTQxOTgtODNhMi02Zjk1MDg4N2MxMjUifQ.rffkvBLLGqfQpWJjwT4lWCTNJ-0-h7U0THecoNnBIqg",
        "Accept: application/vnd.api+json")
    @GET("/shards/steam/players")
    fun getMatches(@Query("filter[playerNames]") name: String):
            Observable<Model.Response2>


    companion object {
        fun create(): PlayerMatchesApi {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.pubg.com/")
                .build()

            return retrofit.create(PlayerMatchesApi::class.java)
        }
    }

}