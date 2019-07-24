package com.example.moviedatabaseapp.controllers

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.example.moviedatabaseapp.model.ModelManager
import com.example.moviedatabaseapp.model.data.Movie

class MovieController {

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
    get() = _popularMovies

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
    get() = _movie

    init {
        _popularMovies.value = listOf()
        _movie.value = null
    }

    fun loadPopularMovies() {
        ModelManager.getPopularMovies { movies, error ->
            if (movies != null) {
                _popularMovies.value = movies
            } else {
                Log.d("TestMovieContr", "loadPopularMovies - There was an error: $error")
            }
        }
    }

    fun loadMovieDetais(movieId: Int) {
        Log.d("Test", "loadMovieDetails")
        ModelManager.getMovieDetails(
                movieId,
                onCompletion = { movie, error ->
                    Log.d("Test", "on completion")
                    if (movie != null) {
                        _movie.value = movie
                    } else {
                        Log.d("TestMovieContr", "loadMovieDetails - There was an error: $error")
                    }
                }
        )
    }
}