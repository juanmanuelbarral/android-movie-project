package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.controllers.MainFragmentController
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    private val controller = MainFragmentController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.loadMovieDetais(320288)

        controller.movie.observe(this, Observer { newMovie ->
            if (newMovie != null) {
                movieTitleLabel.text = newMovie.title
                val url = newMovie.posterUrl()
                Glide.with(this)
                    .load(url)
                    .into(moviePosterImage)
            }
        })

        darkPhoenixButton.setOnClickListener {
            controller.loadMovieDetais(320288)
        }

        endgameButton.setOnClickListener {
            controller.loadMovieDetais(299534)
        }

        detectivePikachuButton.setOnClickListener {
            controller.loadMovieDetais(447404)
        }

        popularMoviesButton.setOnClickListener {
            // Navigation to PopularFragment
            fragmentManager!!.beginTransaction()
                .replace(R.id.mainActivityContainer, PopularFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}