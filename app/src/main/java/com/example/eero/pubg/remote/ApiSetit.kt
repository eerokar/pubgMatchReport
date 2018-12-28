package com.example.eero.pubg.remote

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiSetit {

    var match: String

    @Headers("Accept: application/vnd.api+json")
    @GET("shards/eu/matches/{id}")
    fun getMatchId(@Path("id") id: String):
            Observable<Model.Response>


    companion object {
        fun create(): ApiSetit {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://api.pubg.com/")
                .build()

            return retrofit.create(ApiSetit::class.java)
        }
    }

}