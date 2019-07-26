package com.example.moviedatabaseapp.ui.main

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
import com.example.moviedatabaseapp.databinding.FragmentMainBinding
import com.example.moviedatabaseapp.ui.popular.PopularFragment

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )
        viewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)

        viewModel.movie.observe(this, Observer { newMovie ->
            if (newMovie != null) {
                binding.movieTitleLabel.text = newMovie.title
                val url = newMovie.posterUrl()
                Glide.with(this.context)
                    .load(url)
                    .into(binding.moviePosterImage)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadMovieDetais(320288)

        binding.darkPhoenixButton.setOnClickListener {
            viewModel.loadMovieDetais(320288)
        }

        binding.endgameButton.setOnClickListener {
            viewModel.loadMovieDetais(299534)
        }

        binding.detectivePikachuButton.setOnClickListener {
            viewModel.loadMovieDetais(447404)
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