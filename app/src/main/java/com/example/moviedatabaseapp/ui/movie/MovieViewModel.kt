package com.example.moviedatabaseapp.ui.movie

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.moviedatabaseapp.model.ModelManager
import com.example.moviedatabaseapp.model.data.Movie

class MovieViewModel: ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie>
        get() = _movie

    init {
        _movie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("MovieViewModel", "onCleared called")
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