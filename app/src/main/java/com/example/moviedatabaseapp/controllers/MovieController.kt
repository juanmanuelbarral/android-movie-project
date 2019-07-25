package com.example.moviedatabaseapp.controllers

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.moviedatabaseapp.model.ModelManager
import com.example.moviedatabaseapp.model.data.Movie

class MovieController {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    init {
        _movie.value = null
    }

    fun onSetMovie(movie: Movie) {
        _movie.value = movie
    }

    fun loadMovieDetais(movieId: Int) {
        ModelManager.getMovieDetails(
            movieId,
            onCompletion = { movie, error ->
                if (movie != null) {
                    _movie.value = movie
                } else {
                    Log.d("TestMovieContr", "loadMovieDetails - There was an error: $error")
                }
            }
        )
    }
}