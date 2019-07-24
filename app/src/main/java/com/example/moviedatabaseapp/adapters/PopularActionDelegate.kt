package com.example.moviedatabaseapp.adapters

import com.example.moviedatabaseapp.model.data.Movie

interface PopularActionDelegate {

    fun onClickMovie(movie: Movie)
}