package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.controllers.MovieController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var controller: MovieController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        controller = MovieController()
        controller.loadMovieDetais(320288)

        controller.movie.observe(this, Observer { newMovie ->
            if (newMovie != null) {
                movieTitleLabel.text = newMovie.title
                // The poster
            }
        })

        controller.popularMovies.observe(this, Observer {
            // Work with the recycler view
        })

        darkPhoenixButton.setOnClickListener {
            Log.d("Test", "button")
            controller.loadMovieDetais(320288)
        }

        endgameButton.setOnClickListener {
            Log.d("Test", "button")
            controller.loadMovieDetais(299534)
        }

        detectivePikachuButton.setOnClickListener {
            Log.d("Test", "button")
            controller.loadMovieDetais(447404)
        }
    }


}
