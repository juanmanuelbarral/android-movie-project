package com.example.moviedatabaseapp.ui.movie

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.databinding.FragmentMovieBinding
import com.example.moviedatabaseapp.model.data.Movie

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_movie,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        viewModel.movie.observe(this, Observer { movie ->
            if (movie != null)
                configMovieData(movie)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun setMovie(movie: Movie) {
        viewModel.onSetMovie(movie)
    }

    private fun configMovieData(movie: Movie) {
        val url = movie.posterUrl()
        Glide.with(this.context).load(url).into(binding.moviePosterImage)
    }
}