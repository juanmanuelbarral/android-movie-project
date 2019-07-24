package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
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
                val baseUrlPhoto = "https://image.tmdb.org/t/p/w500"
                val url = baseUrlPhoto + newMovie.posterPath
                Glide.with(this)
                    .load(url)
                    .into(moviePosterImage)
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
