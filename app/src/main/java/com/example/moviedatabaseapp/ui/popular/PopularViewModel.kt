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

    private val _navigateToMovie = MutableLiveData<Boolean>()
    val navigateToMovie: LiveData<Boolean>
        get() = _navigateToMovie

    var movieForNavigation: Movie? = null

    init {
        _popularMovies.value = listOf()
        _navigateToMovie.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("PopularViewModel", "onCleared called")
    }

    override fun onClickMovie(movie: Movie) {
        ModelManager.getMovieDetails(movie.movieId, onCompletion = { movieDetails, error ->
            if (movieDetails != null) {
                Log.d("TestPopularCon", "onClickMovie - movie success")
                // Trigger navigation and pass this movie to the movie module (fragment, controller)
                movieForNavigation = movieDetails
                _navigateToMovie.value = true

            } else {
                Log.d("TestPopularCon", "onClickMovie - There was an error: $error")
            }
        })

        // La otra opción es pasar el movie id por bundle y que se busque la película después en el movie controller
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
        _navigateToMovie.value = false
    }
}