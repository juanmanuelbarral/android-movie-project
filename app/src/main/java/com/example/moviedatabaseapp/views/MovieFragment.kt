package com.example.moviedatabaseapp.views

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.controllers.MovieController
import com.example.moviedatabaseapp.databinding.FragmentMovieBinding
import com.example.moviedatabaseapp.model.data.Movie

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val controller = MovieController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controller.movie.observe(this, Observer { movie ->
            val url = movie!!.posterUrl()
            Glide.with(this).load(url).into(binding.moviePosterImage)
        })
    }

    fun setMovie(movie: Movie) {
        controller.onSetMovie(movie)
    }
}