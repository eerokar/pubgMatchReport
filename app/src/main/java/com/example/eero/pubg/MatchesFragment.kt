package com.example.eero.pubg

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.eero.pubg.remote.Model

class MatchesFragment : Fragment() {

    private lateinit var statsText: TextView
    lateinit var name: String

    val searchFunctions = SearchFunctions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_matches, container, false)

        val listView = view.findViewById<ListView>(R.id.matchList)

        listView.adapter = MatchesAdapter(this.context!!, Model.matchList)

        listView.setOnItemClickListener{ _, _, position, _ ->
            val selectedMatch = listView.getItemAtPosition(position) as String
            openStatsFragment(name, selectedMatch)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun openStatsFragment(name: String, matchId: String) {
        searchFunctions.searchMatchDetails(matchId, name)
        val statsFragment = StatsFragment()
        statsFragment.name = name
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.container, statsFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}