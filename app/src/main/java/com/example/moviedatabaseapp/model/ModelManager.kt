package com.example.moviedatabaseapp.model

import android.util.Log
import com.example.moviedatabaseapp.model.api.RetrofitFactory
import com.example.moviedatabaseapp.model.data.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

object ModelManager {

    private val movieDatabaseService = RetrofitFactory.makeMovieDatabaseService()

    fun getPopularMovies(onCompletion: (movies: List<Movie>?, error: String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieDatabaseService.getPopularMovies()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        onCompletion(response.body(), null)
                    } else {
                        onCompletion(null, "Get popular movies request was unsuccessful")
                    }
                } catch (e: HttpException) {
                    Log.d("TestModelManager", "getPopularMovies - HttpException - ${e.message()}")
                } catch (e: Throwable) {
                    Log.d("TestModelManager", "getPopularMovies - Throwable exception - ${e.message}")
                }
            }
        }
    }

    fun getMovieDetails(movieId: Int, onCompletion: (movie: Movie?, error: String?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = movieDatabaseService.getMovieDetails(movieId)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        onCompletion(response.body(), null)
                    } else {
                        onCompletion(null, "Get movie details request was unsuccessful")
                    }
                } catch (e: HttpException) {
                    Log.d("TestModelManager", "getMovieDetails - HttpException - ${e.message()}")
                } catch (e: Throwable) {
                    Log.d("TestModelManager", "getMovieDetails - Throwable exception - ${e.message}")
                }
            }
        }
    }
}