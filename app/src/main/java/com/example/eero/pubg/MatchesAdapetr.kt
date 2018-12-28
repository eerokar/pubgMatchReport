package com.example.eero.pubg

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MatchesAdapter(context: Context, private val matches: MutableList<String>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return matches.size
    }

    override fun getItem(position: Int): Any {
        return matches[position]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_match, p2, false)
        val thisMatch = matches[position]

        var tv = rowView.findViewById(R.id.matchTextView) as TextView
        tv.text = thisMatch

        notifyDataSetChanged()

        return rowView
    }

}