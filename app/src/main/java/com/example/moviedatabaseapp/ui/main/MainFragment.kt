package com.example.moviedatabaseapp.ui.main

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.databinding.FragmentMainBinding
import com.example.moviedatabaseapp.ui.popular.PopularFragment

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val controller = MainFragmentController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.loadMovieDetais(320288)

        controller.movie.observe(this, Observer { newMovie ->
            if (newMovie != null) {
                binding.movieTitleLabel.text = newMovie.title
                val url = newMovie.posterUrl()
                Glide.with(this)
                    .load(url)
                    .into(binding.moviePosterImage)
            }
        })

        binding.darkPhoenixButton.setOnClickListener {
            controller.loadMovieDetais(320288)
        }

        binding.endgameButton.setOnClickListener {
            controller.loadMovieDetais(299534)
        }

        binding.detectivePikachuButton.setOnClickListener {
            controller.loadMovieDetais(447404)
        }

        binding.popularMoviesButton.setOnClickListener {
            // Navigation to PopularFragment
            fragmentManager!!.beginTransaction()
                .replace(R.id.mainActivityContainer, PopularFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}