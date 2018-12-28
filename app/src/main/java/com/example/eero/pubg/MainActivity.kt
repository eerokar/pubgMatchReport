package com.example.eero.pubg


import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        val homeFragment = HomeActivity()
        transaction.replace(R.id.main, homeFragment)
        transaction.commit()
    }
}
