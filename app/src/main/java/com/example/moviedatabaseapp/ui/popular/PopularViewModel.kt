package com.example.moviedatabaseapp.ui.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.example.moviedatabaseapp.model.ModelManager
import com.example.moviedatabaseapp.model.data.Movie

class PopularViewModel: ViewModel(), PopularActionDelegate {

    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>>
        get() = _popularMovies

    private val _navigateToMovie = MutableLiveData<Int>()
    val navigateToMovie: LiveData<Int>
        get() = _navigateToMovie


    init {
        _popularMovies.value = listOf()
        _navigateToMovie.value = null
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("PopularViewModel", "onCleared called")
    }

    override fun onClickMovie(movie: Movie) {
        ModelManager.getMovieDetails(movie.movieId, onCompletion = { movieDetails, error ->
            if (movieDetails != null) {
                // Trigger navigation and pass this movie to the movie module (fragment, controller)
                _navigateToMovie.value = movieDetails.movieId

            } else {
                Log.d("TestPopularCon", "onClickMovie - There was an error: $error")
            }
        })
    }

    fun loadPopularMovies() {
        ModelManager.getPopularMovies { movies, error ->
            if (movies != null) {
                _popularMovies.value = movies
            } else {
                Log.d("TestPopularCon", "loadPopularMovies - There was an error: $error")
            }
        }
    }

    fun onNavigationToMovieDone() {
        _navigateToMovie.value = null
    }
}