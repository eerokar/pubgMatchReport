package com.example.eero.pubg

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import com.example.eero.pubg.remote.Model

class StatsFragment : Fragment() {

    private lateinit var statsText: TextView
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_stats, container, false)
        // Inflate the layout for this fragment
        val listView = view!!.findViewById<ListView>(R.id.statsList)
        val adapter = StatsAdapter(this.context!!, Model.victimNamesList)
        listView.adapter = adapter

        val refreshButton = view.findViewById<View>(R.id.refreshBtn)
        refreshButton.setOnClickListener { adapter.refresh(name) }

        return view
    }

    override fun onPause() {
        super.onPause()
        Logic.clearAll()
    }
}