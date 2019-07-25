package com.example.moviedatabaseapp.model.data

import com.squareup.moshi.Json

data class Movie (
    @field:Json(name = "id") val movieId: Int,
    val title: String,
    @field:Json(name = "poster_path") private var posterPath: String = ""
) {
    fun posterUrl(): String {
        return if (posterPath != "") {
            POSTER_BASE_URL + posterPath
        } else {
            posterPath
        }
    }

    companion object {
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}

data class ResponsePopularMovies(
    val results: List<Movie>
)