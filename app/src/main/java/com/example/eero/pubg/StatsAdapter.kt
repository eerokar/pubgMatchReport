package com.example.eero.pubg

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.eero.pubg.remote.Model
import android.util.Log


class StatsAdapter (context: Context, private val stats: MutableList<String>): BaseAdapter() {
    private val victims = Model.victimList
    private var timesHitCount = 0

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return stats.size
    }

    override fun getItem(position: Int): Any {
        return stats[position]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(position: Int, p1: View?, p2: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.item_stats, p2, false)
        val thisVictim = stats[position]

        val vicName = rowView.findViewById(R.id.victimNameTxtView) as TextView
        val timesHit = rowView.findViewById(R.id.timesHitTxtView) as TextView
        val hits = rowView.findViewById(R.id.hitsTxtView) as TextView



        vicName.text = thisVictim

        Log.d("Damage reasons: ", victims[thisVictim]!!.toString())
        val timesHitList = timesHit(thisVictim)

        timesHit.text = "Times hit: $timesHitCount"
        hits.text = columnSeparater(timesHitList)

        return rowView
    }

    fun refresh(name: String){
        Logic.sortPlayers(Model.playerDamagers, Model.playerD, name)
        notifyDataSetChanged()
    }

    fun timesHit(thisVictim: String): HashMap<String, String>{

        timesHitCount = 0
        val shots = HashMap<String, String>()
        var gunCount = 1

        for(i in victims[thisVictim]!!.damageCauserNames.indices){

            val gun = victims[thisVictim]!!.damageCauserNames[i]
            val dmgReason = victims[thisVictim]!!.damageReasons[i]

            val value = shots.get(dmgReason)

            if (value == null){
                shots.put(dmgReason, gun)
                timesHitCount++
            }else{
                val v = shots.get(dmgReason)
                if (v != null) {
                    gunCount ++
                    timesHitCount++
                    shots.put(dmgReason, "$gun x $gunCount")
                }
            }
        }

        return shots
    }

    fun columnSeparater(list: HashMap<String, String>) : String {
        val returnString = StringBuilder()

        for (name in list) {
            returnString.append("${name.key.replace("Shot", " shot")} with ${name.value.substring(4).replace("_C", "")}\n")
        }
        return returnString.toString()
    }

}