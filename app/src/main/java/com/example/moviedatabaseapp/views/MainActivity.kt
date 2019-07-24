package com.example.moviedatabaseapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.controllers.MovieController

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Start main fragment
        supportFragmentManager.beginTransaction()
            .add(R.id.mainActivityContainer, MainFragment())
            .commit()
    }


}
