package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.controllers.MovieController
import com.example.moviedatabaseapp.model.data.Movie
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment: Fragment() {

    private val controller = MovieController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.movie.observe(this, Observer { movie ->

            val baseUrlPhoto = "https://image.tmdb.org/t/p/w500"
            val url = baseUrlPhoto + movie!!.posterPath
            Glide.with(this).load(url).into(moviePosterImage)
        })
    }

    fun setMovie(movie: Movie) {
        controller.onSetMovie(movie)
    }
}