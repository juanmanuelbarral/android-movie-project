package com.example.moviedatabaseapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviedatabaseapp.R

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
