package com.example.moviedatabaseapp.ui.popular

import com.example.moviedatabaseapp.model.data.Movie

interface PopularActionDelegate {

    fun onClickMovie(movie: Movie)
}