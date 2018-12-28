package com.example.eero.pubg

import android.util.Log
import com.example.eero.pubg.remote.Model
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object Logic {



    private val df = DecimalFormat("#.#")


    fun sortPlayers(from: List<Model.Results>, to: MutableList<Model.Results>, name: String){
        var i = 0
        if(from == Model.playerKillers) {
            while (i < from.size) {
                if (from[i].killer != null && from[i].killer.name == name) {
                    to.add(from[i])
                    if (from[i].damageCauserName == "PlayerMale_A_C" || from[i].damageCauserName == "PlayerFemale_A_C"){
                        from[i].damageCauserName = "Down and out"
                    }
                }
                i++
            }
        }

        else{
            while(i < from.size) {
                if (from[i].attacker != null && from[i].attacker.name == name) {
                    to.add(from[i])
                    val v = from[i].victim.name
                    if(from == Model.playerKnockers) {
                        putToVictimList(v, i, from, true)
                    }else{
                        putToVictimList(v, i, from, false)
                    }
                }
                i++
            }
        }
    }


    fun showStats(name: String): String {

        val returnString = StringBuilder()
        returnString.setLength(0)

        var i = 0

        var vicName: String


        returnString.append(
            "Pelaajan $name statsit: \n\n"+

                    "Haavoitetut: \n\n\n")

            while (i < Model.victimNamesList.size) {

                vicName = Model.victimNamesList[i]

                if(Model.victimList[vicName]!!.isKnocked){

                }

                if(Model.victimList[vicName]!!.damageCauserNames.contains("PlayerMale_A_C") ||
                    Model.victimList[vicName]!!.damageCauserNames.contains("PlayerFemale_A_C")){
                    }
                else {
                    returnString.append(
                        "Pelaaja: ${Model.victimList[vicName]!!.name} \n" +
                                "Osuttu: ${Model.victimList[vicName]!!.damageCauserNames.size} kertaa. \n" +
                                "Ase: ${Model.victimList[vicName]!!.damageCauserNames} \n" +
                                "Osumakohta: ${Model.victimList[vicName]!!.damageReasons} \n" +
                                "Damage: ${Model.victimList[vicName]!!.damages} pistettä. \n\n"
                                //"Aika: ${convertTimeStamp(victimList[vicName]!!.timeStamps[i])} \n \n"
                    )
                }
            i++
        }




            /*
            + "Osumat: \n")



while (i < Model.playerD.size) {
    if(playerD[i].damageCauserName == "PlayerMale_A_C"){
    }
    else {
        returnString.append(
                    "Pelaaja: ${playerD[i].victim.name}\n" +
                    "Ase: ${playerD[i].damageCauserName} \n" +
                    "Osumakohta: ${playerD[i].damageReason} \n" +
                    "Damage: ${df.format(playerD[i].damage)} pistettä. \n" +
                            "Aika: ${convertTimeStamp(playerD[i]._D)} \n \n"
        )
    }
    i++
}

returnString.append("\n \n"
            + "Tapot: \n")
var a = 0
while (a < Model.playerK.size) {

        returnString.append(
                    "Pelaaja: ${playerK[a].victim.name} \n" +
                    "Ase: ${playerK[a].damageCauserName} \n" +
                    "Osumakohta: ${playerK[a].damageReason} \n" +
                            "Aika: ${convertTimeStamp(playerK[a]._D)} \n \n"
        )
    a++
}

returnString.append("\n \n"
        + "Knockit: \n")
var n = 0
while (n < Model.playerG.size) {

    returnString.append(
        "Pelaaja: ${playerG[n].victim.name} \n" +
                "Ase: ${playerG[n].damageCauserName} \n" +
                "Osumakohta: ${playerG[n].damageReason} \n" +
                "Aika: ${convertTimeStamp(playerG[n]._D)} \n \n"
    )
    n++
} */
        return returnString.toString()
    }


    private fun convertTimeStamp(dateAsString: String): String{
        val eventHappened = LocalDateTime.parse(dateAsString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
        val secondsInBetween = Model.matchStartedAt.until(eventHappened, ChronoUnit.SECONDS)
        val decimalFormat = DecimalFormat("00")
        val minutes = secondsInBetween / 60
        val seconds = decimalFormat.format(secondsInBetween % 60)

        return "$minutes minuuttia, $seconds sekunttia."
    }


    fun clearAll(){
        Model.playerKnockers.clear()
        Model.playerKillers.clear()
        Model.playerDamagers.clear()
        Model.playerG.clear()
        Model.playerD.clear()
        Model.playerK.clear()
        Model.victimNamesList.clear()
        Model.victimList.clear()
    }

    fun clearMatches(){
        Model.matchList.clear()
    }


    private fun putToVictimList(v: String, i: Int, from: List<Model.Results>, isKnocked: Boolean){
        if (!Model.victimList.containsKey(v)) {
            Model.victimList.put(v, Victim(v))
            Model.victimNamesList.add(v)
        }
            sortVictimsList(v,i,from,isKnocked)
    }

    private fun sortVictimsList(v: String, i: Int, from: List<Model.Results>, isKnocked: Boolean){
        Model.victimList[v]!!.name = v
        Model.victimList[v]!!.damages.add(from[i].damage)
        Model.victimList[v]!!.damageCauserNames.add(from[i].damageCauserName)
        Model.victimList[v]!!.isKnocked = isKnocked
        Model.victimList[v]!!.timeStamps.add(from[i]._D)

        if (isKnocked){
            Model.victimList[v]!!.damageReasons.add("${from[i].damageReason} KNOCKED!")
        }else{
            Model.victimList[v]!!.damageReasons.add(from[i].damageReason)
        }
    }
}