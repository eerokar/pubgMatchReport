package com.example.eero.pubg

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.eero.pubg.remote.ApiService
import com.example.eero.pubg.remote.ApiSetit
import com.example.eero.pubg.remote.Model
import com.example.eero.pubg.remote.PlayerMatchesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.log


class HomeActivity : Fragment() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var tButton: Button
    private lateinit var eText: EditText

    private lateinit var urli: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tButton = view.findViewById(R.id.testbutton)
        eText = view.findViewById(R.id.editText)

        searchMatchDetails("712f84f2-7322-4c27-90ef-c629eaaa4261")


        tButton.setOnClickListener {
            //Logic.sortPlayers(Model.playerDamagers, Model.playerD, eText.text.toString())
            //Logic.sortPlayers(Model.playerKillers, Model.playerK, eText.text.toString())
            //Logic.sortPlayers(Model.playerKnockers, Model.playerG, eText.text.toString())
            openMatchesFragment(eText.text.toString())
        }
    }

    private val wikiApiServe by lazy {
        ApiService.create()
    }

    private val apiMatchDetails by lazy {
        ApiSetit.create()
    }

    private val apiPlayerMatches by lazy {
        PlayerMatchesApi.create()
    }

    fun beginSearch(url: String) {
        Log.d("AAAAAAAAAAAAAAA ", url)

        val apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI3MDQ4MmJmMC05MjQ0LTAxMzYtOWNjNi0xYjJmYzc1NTgwMmQiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTM2MDQ3MTM4LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Ii0zYzNkZWQ0Yi02ZWY0LTQxOTgtODNhMi02Zjk1MDg4N2MxMjUifQ.rffkvBLLGqfQpWJjwT4lWCTNJ-0-h7U0THecoNnBIqg"

        val disposable =
            wikiApiServe.getJson(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        Model.matchStartedAt = LocalDateTime.parse(result[0]._D, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'"))

                        for (i in result.indices) {

                            if (result[i]._T == "LogPlayerTakeDamage"){
                                Model.playerDamagers.add(result[i])
                                Log.d("Jeejee_ ", Model.playerDamagers.add(result[i]).toString() )
                            }
                            if (result[i]._T == "LogPlayerKill"){
                                Model.playerKillers.add(result[i])
                            }
                            if (result[i]._T == "LogPlayerMakeGroggy"){
                                Model.playerKnockers.add(result[i])
                            }
                        }
                    }
                    ,
                    { error -> Log.d("ErrorTää: ", error.message) }
                )

        if (disposable != null) compositeDisposable.add(disposable)
    }

    fun searchMatchDetails(matchId: String){

        val disposable =
            apiMatchDetails.getMatchId(matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        for (i in result.included.indices) {
                            if (result.included[i].type == "asset")
                                urli = result.included[i].attributes.url.substring(44)
                                Log.d("URLI: ", urli)
                        }
                    }
                    ,
                    { error -> Log.d("Error: ", error.message) }
                )
        if (disposable != null) compositeDisposable.add(disposable)
    }

    fun searchPlayerMatches(playerName: String){

        val disposable =
            apiPlayerMatches.getMatches(playerName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        for (i in result.data[0].relationships.matches.data.indices) {
                            Model.matchList.add(result.data[0].relationships.matches.data[i].id)
                        }
                    }
                    ,
                    { error -> Log.d("Error: ", error.message) }
                )
        if (disposable != null) compositeDisposable.add(disposable)
    }

    private fun openStatsFragment(name: String) {
        val statsFragment = StatsFragment()
        statsFragment.name = name
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, statsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun openMatchesFragment(name: String) {
        Logic.clearMatches()
        searchPlayerMatches(name)
        val matchesFragment = MatchesFragment()
        matchesFragment.name = name
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, matchesFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
