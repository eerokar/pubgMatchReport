package com.example.eero.pubg

import android.util.Log
import com.example.eero.pubg.remote.ApiService
import com.example.eero.pubg.remote.ApiSetit
import com.example.eero.pubg.remote.Model
import com.example.eero.pubg.remote.PlayerMatchesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchFunctions {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    lateinit var urli: String


    //Services
    private val wikiApiServe by lazy {
        ApiService.create()
    }
    private val apiMatchDetails by lazy {
        ApiSetit.create()
    }
    private val apiPlayerMatches by lazy {
        PlayerMatchesApi.create()
    }

    fun searchMatchDetails(matchId: String, name: String){

        var url = ""
        val disposable =
            apiMatchDetails.getMatchId(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        for (i in result.included.indices) {
                            if (result.included[i].type == "asset")
                                url = result.included[i].attributes.url.substring(44)
                        }
                        beginSearch(url, name)
                    }
                    ,
                    { error -> Log.d("Errorrr: ", error.message) }
                )
        if (disposable != null) compositeDisposable.add(disposable)
    }

    fun beginSearch(url: String, name: String) {
        val disposable =
            wikiApiServe.getJson(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Model.matchStartedAt = LocalDateTime.parse(result[0]._D, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"))

                        for (i in result.indices) {

                            if (result[i].attacker != null) {
                                if (result[i].victim != null) {
                                    if (result[i].damageCauserName != "PlayerMale_A_C") {
                                        if (result[i].damageCauserName != "PlayerFemale_A_C") {
                                            if(result[i].damage != 0.0) {

                                                if (result[i].victim.name != name) {
                                                    if (result[i]._T == "LogPlayerTakeDamage" && result[i].attacker.name == name) {
                                                        Model.playerDamagers.add(result[i])
                                                    }
                                                    if (result[i]._T == "LogPlayerMakeGroggy" && result[i].attacker.name == name) {
                                                        Model.playerKnockers.add(result[i])
                                                    }
                                                    if (result[i]._T == "LogPlayerKill" && result[i].killer.name == name) {
                                                        Model.playerKillers.add(result[i])
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    ,
                    { error -> Log.d("ErrorTää: ", error.message) }
                )
        if (disposable != null) compositeDisposable.add(disposable)
    }
}